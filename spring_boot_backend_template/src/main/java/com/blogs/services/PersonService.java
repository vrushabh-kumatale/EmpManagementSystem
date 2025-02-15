package com.blogs.services;

import com.blogs.dtos.ApiResponse;
import com.blogs.dtos.PersonDto;

public interface PersonService {
	
	ApiResponse registerNewUser(PersonDto dto);

}
