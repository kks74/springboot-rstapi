package com.synechron.rest.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.synechron.rest.api.entity.User;
import com.synechron.rest.api.exception.UserExistException;
import com.synechron.rest.api.exception.UserNameNotFoundException;
import com.synechron.rest.api.exception.UserNotFoundException;
import com.synechron.rest.api.service.UserService;

@RestController
@Validated
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		List<User> users = userService.getAllUsers();
		return users;
	}
	
	@PostMapping("/users")
	public ResponseEntity<Void> createuser(@Valid @RequestBody User user, UriComponentsBuilder builder) {
		try {
		userService.createuser(user);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
		}catch(UserExistException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
		
	}
	
	@GetMapping("/users/{id}")
	public Optional<User> getUserById(@PathVariable("id") @Min(1) Long id) {
		try {
			return userService.getUserById(id);
		}catch(UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}
	
	@PutMapping("/users/{id}")
	public User updateUserById(@PathVariable("id")Long id, @RequestBody User user) {
		
		try {
			return userService.updateUserById(id, user);
		}
		catch(UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
		
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable("id") Long id) {
		userService.deleteUserById(id);
	}
	
	@GetMapping("/users/byusername/{name}")
	public User getUserByUserName(@PathVariable("name") String username) throws UserNameNotFoundException {
		User user = userService.getUserByUserName(username);
		if(user == null) {
			throw new UserNameNotFoundException(" " +username +" UserNameNotFoundException in Repository");
		}
		return user;
	}

}
