package com.blogs.pojos;



import jakarta.persistence.Entity;


import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="admins")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Admin extends Person {
	
	  @Override
	    public String getRole(){
	        return "ADMIN";
	    }
}
