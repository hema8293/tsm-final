package taskmanagementsystem.service;

import taskmanagementsystem.model.Task;
import taskmanagementsystem.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

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

        assertNotNull(savedTask);
        assertEquals("Test Task", savedTask.getName());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void getTaskById_ShouldReturnTask_WhenTaskExists() {
        Task task = new Task(1L, "Test Task", "Description", false);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task retrievedTask = taskService.getTaskById(1L);

        assertNotNull(retrievedTask);
        assertEquals(1L, retrievedTask.getId());
        assertEquals("Test Task", retrievedTask.getName());
    }

    @Test
    void getTaskById_ShouldThrowException_WhenTaskDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> taskService.getTaskById(1L));
    }

    @Test
    void updateTask_ShouldModifyAndReturnUpdatedTask() {
        Task existingTask = new Task(1L, "Old Task", "Old Description", false);
        Task updatedTask = new Task(1L, "Updated Task", "Updated Description", true);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        Task result = taskService.updateTask(1L, updatedTask);

        assertNotNull(result);
        assertEquals("Updated Task", result.getName());
        assertEquals("Updated Description", result.getDescription());
        assertTrue(result.isCompleted());
    }

    @Test
    void deleteTask_ShouldRemoveTask_WhenTaskExists() {
        Task task = new Task(1L, "Task to Delete", "Description", false);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).delete(task);
    }
}
