package com.blogs.dtos;

import java.time.LocalDateTime;

public class ApiResponse {
	private LocalDateTime timeStamp;
	private String message;
	public ApiResponse(String message) {
		
		this.message = message;
		this.timeStamp=LocalDateTime.now();
	}
}
