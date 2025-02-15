package com.blogs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogs.pojos.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

}
