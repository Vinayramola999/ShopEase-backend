package com.zosh.repository;





import org.springframework.data.jpa.repository.JpaRepository;

//import org.springframework.stereotype.Repository;

import com.zosh.model.User;



public interface UserRepository extends JpaRepository<User, Long> {

   public  User findByEmail(String email);
 
 
 
	
 
	

}


