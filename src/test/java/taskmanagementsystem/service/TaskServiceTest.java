package taskmanagementsystem.service;

import taskmanagementsystem.model.Task;
import taskmanagementsystem.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    public TaskServiceTest() {
        MockitoAnnotations.openMocks(this); // Initialize Mocks
    }

    @Test
    void createTask_ShouldSaveAndReturnTask() {
        Task task = new Task("Test Task", "Description", false);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task savedTask = taskService.createTask(task);

        assertEquals("Test Task", savedTask.getName());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void getTaskById_ShouldReturnTask_WhenTaskExists() {
        Task task = new Task(1L, "Test Task", "Description", false);
        when(taskRepository.findById(1L)).thenReturn(java.util.Optional.of(task));

        Task retrievedTask = taskService.getTaskById(1L);

        assertNotNull(retrievedTask);
        assertEquals(1L, retrievedTask.getId());
    }
}
