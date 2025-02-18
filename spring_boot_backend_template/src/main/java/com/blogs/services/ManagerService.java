package com.blogs.services;

import java.util.List;

import com.blogs.dtos.ApiResponse;
import com.blogs.dtos.PedingTaskResponseDto;
import com.blogs.dtos.TaskRequestDto;
import com.blogs.dtos.TaskResponseDto;

public interface ManagerService {
	
	List<TaskResponseDto> getAllTask(Long manId);
	
	TaskResponseDto createTask(TaskRequestDto taskdto);
	
	ApiResponse assignTask(Long TaskId, Long empId);
	
	TaskResponseDto updateTask(Long TaskId, TaskRequestDto updatedTaskDto);
	
	List<PedingTaskResponseDto> getAllPendingTask(Long manId);


}

