package com.blogs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogs.dtos.AuthRequest;
import com.blogs.dtos.AuthResponse;
import com.blogs.dtos.PersonDto;
import com.blogs.security.CustomUserDetailsServiceImpl;
import com.blogs.security.JwtUtils;
import com.blogs.services.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	  private AuthenticationManager authenticationManager;
	  
	  @Autowired
	  private PersonService personService;
	
	  @Autowired
	   private CustomUserDetailsServiceImpl userDetailsService;
	  
	  @Autowired
	  private JwtUtils jwtUtils;
	  
	  @PostMapping("/signup")
		@Operation(description = "User signup")
		public ResponseEntity<?> registerUser(@RequestBody @Valid PersonDto dto) {
			System.out.println("register user "+dto);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(personService.registerNewUser(dto));
			
		}
	  
	  @PostMapping("/login")
	    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest){
	       // 1. Here we call authentication manager to authenticate the user
	       // We pass UsernamePasswordAuthenticationToken to authenticate the user.
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword())
	        );
	        if (authentication.isAuthenticated()){
	           // 2. We use userDetails service to load the details using the email
	            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
	           // 3. We use jwtUtil to create token using userDetails
	            String token = jwtUtils.generateJwtToken(authentication);
	            return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse("Succesful auth!",token));
	        }else{
	            return ResponseEntity.status(401).build();
	        }
	    }
}
