package com.blogs.dtos;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectListResDto {

	private String name;
	private String description;
	private LocalDate dueDate;
	private Long manager_id;
	private String managerName;
}
