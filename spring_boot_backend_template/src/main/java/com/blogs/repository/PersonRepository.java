package com.blogs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogs.pojos.Person;
import com.blogs.pojos.PersonStatus;

public interface PersonRepository extends JpaRepository<Person, Long>{

	 Optional<Person> findByEmail(String email);
	  boolean existsByEmail(String email);
	  List<Person> findByStatus(PersonStatus status);
	List<Person> findByRole(String string);
}
