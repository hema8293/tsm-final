package taskmanagementsystem.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorizationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void accessOtherUserTasks_ShouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/tasks/user/2")
                .header("Authorization", "Bearer user1_valid_token"))
               .andExpect(status().isForbidden());
    }
}
