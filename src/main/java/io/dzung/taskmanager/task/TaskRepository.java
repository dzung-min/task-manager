package io.dzung.taskmanager.task;


import org.springframework.data.repository.ListCrudRepository;
import io.dzung.taskmanager.user.User;
import java.util.List;

public interface TaskRepository extends ListCrudRepository<Task, Long> {
    List<Task> findByUser(User user);

    List<Task> findByUserAndStatus(User user, TaskStatus status);

    List<Task> findByUserAndPriority(User user, TaskPriority priority);
}
