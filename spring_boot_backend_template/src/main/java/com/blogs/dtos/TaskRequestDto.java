package com.blogs.dtos;

import java.time.LocalDate;

import com.blogs.pojos.TaskPriority;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
public class TaskRequestDto {

	private String name;
	private	String description;
	private	LocalDate dueDate;
	private Long empId;
	private Long projId;
	private Long manId;
	private TaskPriority priority;

	   public TaskRequestDto(Long empId,String name, String description, LocalDate dueDate, Long projId, Long manId, TaskPriority priority) {
		   this.name = name;
		   this.empId = empId;
		   this.description = description;
		   this.dueDate = dueDate;
		   this.projId = projId;
		   this.manId = manId;
		   this.priority = priority;
	   }
}
