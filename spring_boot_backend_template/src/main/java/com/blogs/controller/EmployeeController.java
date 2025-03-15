package com.blogs.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogs.services.TaskService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private TaskService taskService;
	
	@GetMapping("/getTasks/{id}")
	public ResponseEntity<?> getTasks(@PathVariable Long id) {
		return ResponseEntity.ok(taskService.getAllTask(id));
		
	}

	
	@PostMapping("/taskId")
	public ResponseEntity<?> updateTask(
			@PathVariable Long taskId,
			@RequestParam(value = "file", required = false) MultipartFile file ) throws IOException {
				System.out.println(file.getName());
				return ResponseEntity.ok(taskService.updateTask(taskId, file));
				
			}

}
