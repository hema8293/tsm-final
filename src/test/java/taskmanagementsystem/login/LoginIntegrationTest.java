@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthenticationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
        userRepository.deleteAll();
        User user = new User("testuser", "testpassword");
        userRepository.save(user);
    }

    @Test
    public void testLoginSuccess() throws Exception {
        String loginJson = "{\"username\":\"testuser\",\"password\":\"testpassword\"}";

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void testLoginFailure() throws Exception {
        String loginJson = "{\"username\":\"wronguser\",\"password\":\"wrongpassword\"}";

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Invalid credentials"));
    }
}
