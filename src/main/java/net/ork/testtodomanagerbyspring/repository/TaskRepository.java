package net.ork.testtodomanagerbyspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import net.ork.testtodomanagerbyspring.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{
}
