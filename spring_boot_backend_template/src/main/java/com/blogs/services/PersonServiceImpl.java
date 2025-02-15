package com.blogs.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogs.custom_exceptions.ApiException;
import com.blogs.dtos.ApiResponse;
import com.blogs.dtos.PersonDto;
import com.blogs.pojos.Admin;
import com.blogs.pojos.Employee;
import com.blogs.pojos.Manager;
import com.blogs.pojos.Person;
import com.blogs.repository.AdminRepository;
import com.blogs.repository.EmployeeRepository;
import com.blogs.repository.ManagerRepository;
import com.blogs.repository.PersonRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PersonServiceImpl implements PersonService{

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ManagerRepository managerRepository;
	
    @Autowired
    private AdminRepository adminRepository;
	
    @Autowired
	private ModelMapper modelMapper;
	
    @Autowired
	private PasswordEncoder passwordEncoder;
    
	@Override
	public ApiResponse registerNewUser(PersonDto dto) {
		 // chk if user alrdy exists
		 // chk if user alrdy exists
        Optional<Person> optionalPerson = personRepository.findByEmail(dto.getEmail());
        if (optionalPerson.isPresent()) {
            throw new ApiException("User with email " + dto.getEmail() + " already exists");
        }
        // map dto -> entity
        Person person = modelMapper.map(dto, Person.class);
        person.setPassword(passwordEncoder.encode(dto.getPassword()));
        //4. Save user in respective tables based on its type
       if(dto.getRole()!=null && dto.getRole().equals("ADMIN")){
            Admin admin=modelMapper.map(person, Admin.class);
            Admin savedAdmin =  adminRepository.save(admin);
            return new ApiResponse("User registered with ID " + savedAdmin.getId());
        }
        if (dto.getRole()!=null && dto.getRole().equals("EMPLOYEE")) {
            Employee employee=modelMapper.map(person, Employee.class);
            Employee savedEmployee = employeeRepository.save(employee);
            return new ApiResponse("User registered with ID " + savedEmployee.getId());
        }
        if (dto.getRole()!=null && dto.getRole().equals("MANAGER")) {
            Manager manager=modelMapper.map(person, Manager.class);
             Manager savedManager = managerRepository.save(manager);
             return new ApiResponse("User registered with ID " + savedManager.getId());
        }
        Person savedPerson =  personRepository.save(person);
        return new ApiResponse("User registered with ID " + savedPerson.getId());
    }

}
