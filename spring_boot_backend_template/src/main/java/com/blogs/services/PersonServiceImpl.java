package com.blogs.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogs.custom_exceptions.ApiException;
import com.blogs.custom_exceptions.ResourceNotFoundException;
import com.blogs.dtos.ApiResponse;
import com.blogs.dtos.EmployeeDto;
import com.blogs.dtos.ManagerRespDto;
import com.blogs.dtos.PersonDto;
import com.blogs.dtos.PersonRespDto;
import com.blogs.pojos.Admin;
import com.blogs.pojos.Employee;
import com.blogs.pojos.Manager;
import com.blogs.pojos.Person;
import com.blogs.pojos.PersonStatus;
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

	@Override
	public List<PersonRespDto> getAllRequest() {
		
		return personRepository.findByStatus(PersonStatus.WAITING_APPROVAL).stream()
				.map(person->modelMapper.map(person, PersonRespDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<PersonRespDto> getAllWorkers() {
		// TODO Auto-generated method stub
		return personRepository.findByStatus(PersonStatus.ACTIVE).stream()
				.map(person->modelMapper.map(person, PersonRespDto.class)).collect(Collectors.toList());
	}

	@Override
	public ApiResponse approveRequest(Long id) {
		Person person=personRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("invalid  id not present"));
		person.setStatus(PersonStatus.ACTIVE);
		personRepository.save(person);
		return new ApiResponse("Approved Worker Succesfully");
	}

	@Override
	public ApiResponse rejectRequest(Long id) {
		Person person=personRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("invalid  id not present"));
		person.setStatus(PersonStatus.REJECTED);
		personRepository.save(person);
		return new ApiResponse("Rejected Worker Succesfully");
	}

	@Override
	public ApiResponse deletePerson(Long id) {
		Person person=personRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("invalid  id not present"));
		person.setStatus(PersonStatus.DEACTIVE);
		personRepository.save(person);
		return new ApiResponse("Deleted Worker Succesfully");
	}

	@Override
	public List<ManagerRespDto> getAllManagers() {
		List<Person> managers = personRepository.findByRole("MANAGER"); // Changed to "MANAGER"
        return managers.stream()
                .map(person -> modelMapper.map(person, ManagerRespDto.class))
                .collect(Collectors.toList());
	}

	@Override
	public List<EmployeeDto> getAllEmployeesbyRole() {
		 List<EmployeeDto> employees = personRepository.findByRole("EMPLOYEE").stream()
				 .map(person -> modelMapper.map(person,EmployeeDto.class)).collect(Collectors.toList());
			
		 return employees;
	}

}
