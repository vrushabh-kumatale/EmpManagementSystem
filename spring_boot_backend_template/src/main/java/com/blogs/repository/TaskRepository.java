package com.blogs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogs.pojos.Task;
import com.blogs.pojos.TaskStatus;

public interface TaskRepository extends JpaRepository<Task, Long>{

	List<Task> findByEmployee_Id(Long employeeId);

	List<Task> findByManagerId(Long manId);

	List<Task> findByManagerIdAndStatus(Long manId, TaskStatus status);
}
