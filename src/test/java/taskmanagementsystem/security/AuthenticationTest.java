package taskmanagementsystem.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void accessWithoutToken_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/tasks/user/1"))
               .andExpect(status().isUnauthorized());
    }

    @Test
    void accessOtherUserTasks_ShouldReturnForbidden() throws Exception {
        mockMvc.perform(get("/api/v1/tasks/user/2")
                .header("Authorization", "Bearer invalid_token"))
               .andExpect(status().isForbidden());
    }
}
