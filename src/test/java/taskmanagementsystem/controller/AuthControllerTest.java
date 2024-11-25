package taskmanagementsystem.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import taskmanagementsystem.dto.Login;
import taskmanagementsystem.dto.ApiResponse;
import taskmanagementsystem.security.CustomUserDetailsService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccess() {
        // Mock login request
        Login loginRequest = new Login();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("password");

        // Mock the behavior of the service
        when(userDetailsService.authenticate(loginRequest.getUsername(), loginRequest.getPassword()))
                .thenReturn("mocked-jwt-token");

        // Call the login method
        ResponseEntity<ApiResponse> response = authController.login(loginRequest);

        // Verify the response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("mocked-jwt-token", response.getBody().getMessage());
        verify(userDetailsService, times(1))
                .authenticate(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @Test
    void testLoginFailure() {
        // Mock login request with invalid credentials
        Login loginRequest = new Login();
        loginRequest.setUsername("invaliduser");
        loginRequest.setPassword("wrongpassword");

        // Mock the behavior of the service to throw an exception
        when(userDetailsService.authenticate(loginRequest.getUsername(), loginRequest.getPassword()))
                .thenThrow(new RuntimeException("Invalid credentials"));

        // Call the login method
        ResponseEntity<ApiResponse> response = authController.login(loginRequest);

        // Verify the response
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid credentials", response.getBody().getMessage());
        verify(userDetailsService, times(1))
                .authenticate(loginRequest.getUsername(), loginRequest.getPassword());
    }
}
