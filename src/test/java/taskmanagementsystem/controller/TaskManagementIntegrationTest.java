@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TaskManagementIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Before
    public void setup() {
        taskRepository.deleteAll(); // Clean up the database before tests
    }

    @Test
    public void testCreateTask() throws Exception {
        String taskJson = "{\"title\":\"Test Task\",\"description\":\"Test Description\"}";

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    public void testGetAllTasks() throws Exception {
        // Create a sample task in the database
        Task task = new Task("Sample Task", "Sample Description");
        taskRepository.save(task);

        mockMvc.perform(get("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Sample Task"))
                .andExpect(jsonPath("$[0].description").value("Sample Description"));
    }

    @Test
    public void testUpdateTask() throws Exception {
        Task task = new Task("Old Task", "Old Description");
        task = taskRepository.save(task);

        String updatedTaskJson = "{\"title\":\"Updated Task\",\"description\":\"Updated Description\"}";

        mockMvc.perform(put("/api/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedTaskJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @Test
    public void testDeleteTask() throws Exception {
        Task task = new Task("Task to Delete", "Description");
        task = taskRepository.save(task);

        mockMvc.perform(delete("/api/tasks/" + task.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertFalse(taskRepository.findById(task.getId()).isPresent());
    }
}
