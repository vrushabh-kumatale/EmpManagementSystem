package com.blogs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogs.pojos.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

	List<Project> findByManagerId(Long manid);
}
