package taskmanagementsystem.controller;

import taskmanagementsystem.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTaskEndpoint_ShouldReturnTaskDetails() throws Exception {
        Task task = new Task("New Task", "Description", false);

        mockMvc.perform(post("/api/v1/tasks/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("New Task"));
    }

    @Test
    void deleteTaskEndpoint_ShouldReturnSuccessMessage() throws Exception {
        mockMvc.perform(delete("/api/v1/tasks/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message").value("Task deleted successfully"));
    }
}
