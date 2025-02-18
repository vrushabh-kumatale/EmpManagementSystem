package com.blogs.dtos;

import java.time.LocalDate;

import com.blogs.pojos.Task;
import com.blogs.pojos.TaskPriority;
import com.blogs.pojos.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskResponseDto {

	private String name;
	private Long id;
	private	String description;
	private	LocalDate dueDate;
	private Long empId;
	private Long projId;
	private Long taskId;
	private Long mId;
	private TaskPriority priority;
	private TaskStatus status;
	
	 public TaskResponseDto(Task task) {
		    
		     this.id = task.getId();
	        this.name = task.getName();
	        this.description = task.getDescription();
	        this.dueDate = task.getDueDate();
	        this.empId = task.getEmployee().getId();
	        this.priority = task.getPriority();
	        this.mId = task.getManager().getId();
	        this.projId = task.getProject().getId();
	        this.taskId = task.getId();
	        this.status = task.getStatus();
	    }
}
