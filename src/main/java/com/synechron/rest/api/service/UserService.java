package com.synechron.rest.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.synechron.rest.api.entity.User;
import com.synechron.rest.api.exception.UserExistException;
import com.synechron.rest.api.exception.UserNotFoundException;
import com.synechron.rest.api.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAllUsers(){
		List<User> users = userRepository.findAll();
		return users;
	}
	
	public User createuser(User user) throws UserExistException {
		
		User isExist = userRepository.findByUsername(user.getUsername());
		if(isExist != null) {
			throw new UserExistException("Already Exist with name " +user.getUsername());
		}
			
		return userRepository.save(user);
	}
	
	public Optional<User> getUserById(Long id) throws UserNotFoundException{
		 Optional<User> user = userRepository.findById(id);
		 if(!user.isPresent()) {
			 throw new UserNotFoundException("User Not Found In repository with id "+id+"");
		 }
		 
		 return user;
	}
	
	public User updateUserById(Long id, User user) throws UserNotFoundException{
		 Optional<User> optionalUser = userRepository.findById(id);
		 if(!optionalUser.isPresent()) {
			 throw new UserNotFoundException("User Not Found In repository with id "+id+"");
		 }
		user.setId(id);
		return userRepository.save(user);
	}
	
	public void deleteUserById(Long id) {
		 Optional<User> optionalUser = userRepository.findById(id);
		 if(!optionalUser.isPresent()) {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User Not Found In repository with id "+id+"");
		 }
		userRepository.deleteById(id);
	}
	
	public User getUserByUserName(String username) {
		return userRepository.findByUsername(username);
	}
	
	
	
}
