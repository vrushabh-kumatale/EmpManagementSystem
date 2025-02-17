package com.blogs.services;

import java.util.List;

import com.blogs.dtos.ApiResponse;
import com.blogs.dtos.EmployeeDto;
import com.blogs.dtos.ManagerRespDto;
import com.blogs.dtos.PersonDto;
import com.blogs.dtos.PersonRespDto;

public interface PersonService {
	
	ApiResponse registerNewUser(PersonDto dto);
	
	List<PersonRespDto> getAllRequest();
	
	List<PersonRespDto> getAllWorkers();
	
	ApiResponse approveRequest(Long id);
	
	ApiResponse rejectRequest(Long id);
	
	ApiResponse deletePerson(Long id);
	
	List<ManagerRespDto> getAllManagers();
	
	List<EmployeeDto> getAllEmployeesbyRole();

}
