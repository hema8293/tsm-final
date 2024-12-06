package taskmanagementsystem.repository;

import taskmanagementsystem.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void saveTask_ShouldPersistAndReturnTask() {
        Task task = new Task("Repository Task", "Repository Description", false);
        Task savedTask = taskRepository.save(task);

        assertNotNull(savedTask.getId());
        assertEquals("Repository Task", savedTask.getName());
    }

    @Test
    void findById_ShouldReturnTask_WhenTaskExists() {
        Task task = new Task("Find Task", "Find Description", false);
        Task savedTask = taskRepository.save(task);

        Optional<Task> retrievedTask = taskRepository.findById(savedTask.getId());
        assertTrue(retrievedTask.isPresent());
        assertEquals("Find Task", retrievedTask.get().getName());
    }

    @Test
    void findById_ShouldReturnEmptyOptional_WhenTaskDoesNotExist() {
        Optional<Task> retrievedTask = taskRepository.findById(999L);
        assertTrue(retrievedTask.isEmpty());
    }

    @Test
    void deleteTask_ShouldRemoveTask_WhenTaskExists() {
        Task task = new Task("Delete Task", "Delete Description", false);
        Task savedTask = taskRepository.save(task);

        taskRepository.deleteById(savedTask.getId());

        Optional<Task> retrievedTask = taskRepository.findById(savedTask.getId());
        assertTrue(retrievedTask.isEmpty());
    }
}
