package com.blogs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogs.pojos.Person;
import com.blogs.repository.PersonRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomUserDetailsServiceImpl implements UserDetailsService{

	//depcy
	  @Autowired
	  private PersonRepository personRepository;


	  @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    Person person = personRepository.findByEmail(email)
	    .orElseThrow(() -> new UsernameNotFoundException("Email not found !!!!!"));
	        return new CustomUserDetailsImpl(person);
	   }
}
