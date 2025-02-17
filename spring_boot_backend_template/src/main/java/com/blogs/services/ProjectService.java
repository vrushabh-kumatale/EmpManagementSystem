package com.blogs.services;

import java.util.List;

import com.blogs.dtos.ApiResponse;
import com.blogs.dtos.ProjectAddReqDto;
import com.blogs.dtos.ProjectDto;
import com.blogs.dtos.ProjectListResDto;

public interface ProjectService {

	ApiResponse addProject(ProjectAddReqDto projectAddDto);
	
	List<ProjectListResDto> getAllProjects();
	
	List<ProjectDto> getAllProjectsByManagerId(Long managerId);
}
