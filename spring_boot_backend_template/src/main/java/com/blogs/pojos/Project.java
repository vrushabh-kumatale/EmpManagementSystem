package com.blogs.pojos;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="projects")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "manager")
public class Project {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @Column(name="project_name",nullable = false,length = 30)
	 private String name;
	 
	 @Column(length=100)
	 private String description;
	 
	 @ManyToOne
	 @JoinColumn(name="manager_id",nullable = false)
	 private Manager manager;
	    

	 @CreationTimestamp
 	 @Column(name="created_on")
	 private LocalDate createdOn;
	 
	 @Column(name="due_date")
	 private LocalDate dueDate;
}

