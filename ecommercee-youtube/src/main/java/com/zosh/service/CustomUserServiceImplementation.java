package com.zosh.service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zosh.model.User;
import com.zosh.repository.UserRepository;

@Service
public class CustomUserServiceImplementation implements UserDetailsService{
	
	private UserRepository userRepository;
	@Autowired
	 public void CustomerUserServiceImplementation(UserRepository userRepository){
		this.userRepository=userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user= userRepository.findByEmail(userName);
		if(user==null) {
			throw new UsernameNotFoundException("user not found with email-"+userName);
		}
		
		List<GrantedAuthority>authorities= new ArrayList<>();
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
	}

	
}
