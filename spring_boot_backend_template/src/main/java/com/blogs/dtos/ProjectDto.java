package com.blogs.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDto {

	@NotBlank(message = "Project ID is required")
    private Long id;
    
    @NotBlank(message = "Project Name is required")
    private String name;
}
