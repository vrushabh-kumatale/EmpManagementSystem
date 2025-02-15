package com.blogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogs.pojos.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long>{

}
