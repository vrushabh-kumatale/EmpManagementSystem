package com.blogs.services;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.blogs.dtos.ApiResponse;
import com.blogs.dtos.TaskRespDto;
import com.blogs.dtos.TaskResponseDto;
import com.blogs.pojos.Task;

import jakarta.validation.Valid;

public interface TaskService {

	List<TaskRespDto> getAllTask(Long id);
	
	Task getTaskById(Long taskId);
	
	ApiResponse updateTask(Long taskId, @Valid MultipartFile file) throws IOException;

	ApiResponse approveRequest(Long id);

	ApiResponse rejectRequest(Long id);

	Resource load(Long taskId);
}
