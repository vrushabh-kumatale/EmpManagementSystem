package com.blogs.dtos;

import java.time.LocalDate;

import com.blogs.pojos.Task;
import com.blogs.pojos.TaskPriority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PedingTaskResponseDto {

	private Long id;
	private String name;
	private	String description;
	private	LocalDate dueDate;
	private Long empId;
	private Long projId;
	private Long mId;
	private Long fId;
	private TaskPriority priority;
	
	public PedingTaskResponseDto(Task task) {
	    this.id = task.getId();
	    this.name = task.getName();
	    this.description = task.getDescription();
	    this.dueDate = task.getDueDate();
	    this.empId = task.getEmployee().getId();
	    this.priority = task.getPriority();
	    this.mId = task.getManager().getId();
	    this.projId = task.getProject().getId();
	    
	  
	}
}
