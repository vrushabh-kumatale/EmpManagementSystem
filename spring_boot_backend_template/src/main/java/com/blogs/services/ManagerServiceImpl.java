package com.blogs.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogs.custom_exceptions.ResourceNotFoundException;
import com.blogs.dtos.ApiResponse;
import com.blogs.dtos.PedingTaskResponseDto;
import com.blogs.dtos.TaskRequestDto;
import com.blogs.dtos.TaskResponseDto;
import com.blogs.pojos.Employee;
import com.blogs.pojos.Manager;
import com.blogs.pojos.Project;
import com.blogs.pojos.Task;
import com.blogs.pojos.TaskStatus;
import com.blogs.repository.EmployeeRepository;
import com.blogs.repository.ManagerRepository;
import com.blogs.repository.ProjectRepository;
import com.blogs.repository.TaskRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService{
	
	@Autowired
	private ManagerRepository managerRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	
//	@Autowired
//	private FileRepository fileRepository;
	
	@Autowired 
	private ProjectRepository projectRepository;
	
	@Autowired
	private TaskRepository taskrepository;
	
	
	@Override
	public List<TaskResponseDto> getAllTask(Long manId) {
		 List<Task> tasks = taskrepository.findByManagerId(manId);

		    if (tasks.isEmpty()) {
		        throw new ResourceNotFoundException("No tasks found for manager ID: " + manId);
		    }

		    return tasks.stream().map(TaskResponseDto::new).collect(Collectors.toList());

	}

	@Override
	public TaskResponseDto createTask(TaskRequestDto taskdto) {
		if (taskdto.getManId() == null || taskdto.getProjId() == null) {
	        throw new IllegalArgumentException("Manager ID and Project ID must be provided");
	    }

	    Manager manager = managerRepository.findById(taskdto.getManId())
	            .orElseThrow(() -> new ResourceNotFoundException("Manager not found with ID: " + taskdto.getManId()));

	    Project project = projectRepository.findById(taskdto.getProjId())
	            .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + taskdto.getProjId()));

	    Task task = new Task();
	    task.setName(taskdto.getName());
	    task.setDescription(taskdto.getDescription());
	    task.setDueDate(taskdto.getDueDate());
	    task.setPriority(taskdto.getPriority());
	    task.setManager(manager);
	    task.setProject(project);
	    Task savedTask = taskrepository.save(task);
          assignTask(savedTask.getId(),taskdto.getEmpId());
	    return new TaskResponseDto(savedTask);
	}

	@Override
	public ApiResponse assignTask(Long taskId, Long empId) {
		Task task = taskrepository.findById(taskId)
	            .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskId));

	    Employee employee = employeeRepository.findById(empId)
	            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + empId));

	    task.setStatus(TaskStatus.IN_PROGRESS);
	    task.setEmployee(employee);
	    taskrepository.save(task);

	    return new ApiResponse("Employee assigned successfully");
	}

	@Override
	public TaskResponseDto updateTask(Long taskId, TaskRequestDto updatedTaskDto) {
		Task existingTask = taskrepository.findById(taskId).orElse(null);

	    // Check if the task exists
	    if (existingTask == null) {
	        throw new ResourceNotFoundException("Task with ID " + taskId + " not found");
	}
	    existingTask.setName(updatedTaskDto.getName());
	    existingTask.setDescription(updatedTaskDto.getDescription());
	    existingTask.setDueDate(updatedTaskDto.getDueDate());
	    existingTask.setPriority(updatedTaskDto.getPriority());
	    
	    // Assuming manager and project can be fetched similarly if required
	    if (updatedTaskDto.getManId() != null) {
	        Manager manager = managerRepository.findById(updatedTaskDto.getManId()).orElse(null);
	        if (manager != null) {
	            existingTask.setManager(manager);
	        }
	    }

	    if (updatedTaskDto.getProjId() != null) {
	        Project project = projectRepository.findById(updatedTaskDto.getProjId()).orElse(null);
	        if (project != null) {
	            existingTask.setProject(project);
	        }
	    }

	    // Save the updated task
	    Task updatedTask = taskrepository.save(existingTask);

	    // Return the updated task in the response DTO format
	    return new TaskResponseDto(updatedTask);
	}

	@Override
	public List<PedingTaskResponseDto> getAllPendingTask(Long manId) {
		List<Task> tasks = taskrepository.findByManagerIdAndStatus(manId,TaskStatus.PENDING_APPROVAL);
		 
		 return tasks.stream()
	                .map(task -> {
	                    PedingTaskResponseDto dto = new PedingTaskResponseDto();
	                    dto.setId(task.getId());
	                    dto.setName(task.getName());
	                    dto.setDescription(task.getDescription());
	                    dto.setDueDate(task.getDueDate());
	                    dto.setEmpId(task.getEmployee().getId());
	                    dto.setPriority(task.getPriority());
	                    dto.setMId(task.getManager().getId());
	                    dto.setProjId(task.getProject().getId());
	                     if (task.getFile() != null) {
	                         dto.setFId(task.getFile().getId());
	                     }
	                     return dto;
	                })
	                .collect(Collectors.toList());
	    }
	}


