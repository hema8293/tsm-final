package taskmanagementsystem.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import taskmanagementsystem.controller.TaskController;
import taskmanagementsystem.dto.Login;
import taskmanagementsystem.service.TaskService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void testAccessProtectedEndpointWithoutAuth() throws Exception {
        mockMvc.perform(get("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testAccessProtectedEndpointWithAuth() throws Exception {
        // Mock JWT token
        String jwtToken = "mocked-jwt-token";

        // Mock the behavior of user details service
        Login loginRequest = new Login();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password");

        when(customUserDetailsService.authenticate("testuser", "password")).thenReturn(jwtToken);

        // Perform the request with an Authorization header
        mockMvc.perform(get("/api/tasks")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
