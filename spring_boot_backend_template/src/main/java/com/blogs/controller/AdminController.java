package com.blogs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogs.dtos.ProjectAddReqDto;
import com.blogs.services.PersonService;
import com.blogs.services.ProjectService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private ProjectService projectService;
	
	@GetMapping("/viewRequests")
	public ResponseEntity<?> getAllEmployees() {
		return ResponseEntity.ok(personService.getAllRequest());
	}
	
	@GetMapping("/viewWorkers")
	public ResponseEntity<?> getAllWorkers(){
		return ResponseEntity.ok(personService.getAllWorkers());
	}

	@PostMapping("/addProject")
	public ResponseEntity<?> addProject(@RequestBody ProjectAddReqDto projectAddDto){
		System.out.println("in admin controller"+projectAddDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(projectService.addProject(projectAddDto));
	}

	
	//get managers name and id
	@GetMapping("/viewManagers")
	public ResponseEntity<?> getAllmanagers(){
		return ResponseEntity.ok(personService.getAllManagers());
	}
	
	@GetMapping("/getProjects")
	public ResponseEntity<?> getAllProjects(){
		return ResponseEntity.ok(projectService.getAllProjects());
	}
	
//Method to approve request
	@PatchMapping("/approveRequests/{id}")
	public ResponseEntity<?> approveRequests(@PathVariable Long id){
		return ResponseEntity.ok(personService.approveRequest(id));
	}
	
//Method to reject request
	@PatchMapping("/rejectRequests/{id}")
	public ResponseEntity<?> rejectRequests(@PathVariable Long id){
		return ResponseEntity.ok(personService.rejectRequest(id));
	}
//Method to delete Person
	@PatchMapping("/deleteWorker/{id}")
	public ResponseEntity<?> deleteWorker(@PathVariable Long id){
		return ResponseEntity.ok(personService.deletePerson(id));
	}
}
