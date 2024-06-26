package com.zosh.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.config.JwtProvider;

import com.zosh.exception.UserException;
import com.zosh.model.Cart;
import com.zosh.model.User;
import com.zosh.repository.UserRepository;
import com.zosh.request.LoginRequest;
import com.zosh.response.AuthResponse;
import com.zosh.service.CartService;
import com.zosh.service.CustomUserServiceImplementation;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")

public class AuthController {
	
	@Autowired
	private final UserRepository userRepository;
	private final  PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	private  final CustomUserServiceImplementation customUserService;
	private final CartService cartService;
	
	
	
	
  
	public AuthController(UserRepository userRepository , CustomUserServiceImplementation customUserService,PasswordEncoder passwordEncoder,JwtProvider jwtProvider,CartService cartService) {
		
		
		this.customUserService = customUserService;
		this.passwordEncoder = passwordEncoder;
		this.jwtProvider = jwtProvider;
	     this.userRepository=userRepository;
	     this.cartService=cartService;
	     System.out.println(userRepository);
	}

		
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@Valid @RequestBody User user) throws UserException{
		
		  	String email = user.getEmail();
	        String password = user.getPassword();
	        String firstName=user.getFirstName();
	        String lastName=user.getLastName();
	        
	        
	        User isEmailExist=userRepository.findByEmail(email);

	        // Check if user with the given email already exists
	        if (isEmailExist!=null) {
	        	
	            throw new UserException("Email Is Already Used With Another Account");
	        }

	        // Create new user
			User createdUser= new User();
			createdUser.setEmail(email);
			createdUser.setFirstName(firstName);
			createdUser.setLastName(lastName);
	        createdUser.setPassword(passwordEncoder.encode(password));
	       
	        
	        
	        User savedUser= userRepository.save(createdUser);
	        Cart cart= cartService.createCart(savedUser);
	        
	      

	        Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);;
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        
	        String token = jwtProvider.generateToken(authentication);

	        AuthResponse authResponse = new AuthResponse();
	        authResponse.setJwt(token);
	        authResponse.setMessage("Signup Success");
			
	        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
		
	        
	     
	        	
	        }
	
	@PostMapping("/signin")
	
 public ResponseEntity<AuthResponse>loginUserHandler(@RequestBody LoginRequest loginRequest){
	String username=loginRequest.getEmail();
	String password=loginRequest.getPassword();
	
//	 User isEmailExist=userRepository.findByEmail(username);
	 
	System.out.println(username +" ----- "+password);
	
	Authentication authentication= authenticate(username, password);
	SecurityContextHolder.getContext().setAuthentication(authentication);
	
	 String token = jwtProvider.generateToken(authentication);

	  AuthResponse authResponse = new AuthResponse();
	  authResponse.setMessage("Signin Success");
      authResponse.setJwt(token);
     
		
     return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
	
	
}
	


private Authentication authenticate(String username,String password) {
	UserDetails userDetails = customUserService.loadUserByUsername(username);
	
	if(!passwordEncoder.matches(password, userDetails.getPassword() )) {
		System.out.println("sign in userDetails - password not match " + userDetails);
		throw new BadCredentialsException("Invalid Password");
	}


	return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities() );
}

	
}

