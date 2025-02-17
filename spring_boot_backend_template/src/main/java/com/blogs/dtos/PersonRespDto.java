package com.blogs.dtos;

import java.time.LocalDate;

import com.blogs.pojos.Department;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PersonRespDto {

	private Long  id;
    private String firstName;

 
    private String lastName;

   
  
    private String email;

   

   
    private LocalDate dob;

  
    private Department department;
}
