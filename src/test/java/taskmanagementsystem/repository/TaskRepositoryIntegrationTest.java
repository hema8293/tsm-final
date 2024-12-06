package taskmanagementsystem.repository;

import taskmanagementsystem.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class TaskRepositoryIntegrationTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void databaseIntegration_ShouldSaveAndRetrieveTask() {
        Task task = new Task("DB Task", "Test Description", false);
        Task savedTask = taskRepository.save(task);

        assertTrue(taskRepository.findById(savedTask.getId()).isPresent());
    }
}
