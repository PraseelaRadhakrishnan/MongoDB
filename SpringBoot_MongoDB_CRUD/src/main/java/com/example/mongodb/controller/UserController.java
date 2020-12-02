package com.example.mongodb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mongodb.model.User;
import com.example.mongodb.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	UserRepository userrepo;
	
	@PostMapping("/create")
	public User create(@RequestBody User newUserObject) {
		return userrepo.save(newUserObject);
	}
	
	@GetMapping("/read")
	public List<User> read(){
		return userrepo.findAll();
	}
	
	@GetMapping("/read/{id}")
	public User read(@PathVariable Long id) {
		Optional<User> UserObj = userrepo.findById(id);
		if(UserObj.isPresent()) {
			return UserObj.get();
		}else {
			throw new RuntimeException("User not found with id "+id);
		}
	}
	
	@PutMapping("/update")
	public User update(@RequestBody User modifiedUserObject) {
		return userrepo.save(modifiedUserObject);
	}
	
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		Optional<User> UserObj = userrepo.findById(id);
		if(UserObj.isPresent()) {
			userrepo.delete(UserObj.get());
			return "User deleted with id "+id;
		}else {
			throw new RuntimeException("User not found for id "+id);
		}
	}
	
}
