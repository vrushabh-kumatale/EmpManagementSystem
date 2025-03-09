package com.blogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogs.pojos.File;

public interface FileRepository extends JpaRepository<File, Long>{

}
