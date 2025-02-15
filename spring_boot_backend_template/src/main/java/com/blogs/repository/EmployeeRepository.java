package com.blogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogs.pojos.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
