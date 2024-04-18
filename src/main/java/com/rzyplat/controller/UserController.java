package com.rzyplat.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rzyplat.entity.User;
import com.rzyplat.param.UserSearchParam;
import com.rzyplat.service.UserService;
import com.rzyplat.util.ResponseModel;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
	
	private UserService service;

	public UserController(UserService service) {
		this.service=service;
	} 
	
	
	@PostMapping
	public ResponseEntity<ResponseModel> add(@RequestBody User userData) {		
		User user = service.save(userData);
		
		return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder("user added successfully")
				.setData("user", user) 
				.build(), HttpStatus.OK);
		}
	
	@PostMapping("/multiple")
	public ResponseEntity<ResponseModel> addMultiple(@RequestBody List<User> users) {	
		User user;
		List<User> userList = new ArrayList<>();
		for(User newUser : users) {
		   user = service.save(newUser);
		   userList.add(user);
		}
		
		return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder("user added successfully")
				.setData("list", userList)
				.build(), HttpStatus.OK);
		}
	
	@PostMapping("/filter")
	public ResponseEntity<ResponseModel> filter(@RequestBody UserSearchParam param) {
		List<User> result=service.search(param);
		Long count = service.count(param);
		return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder("resource fetched successfully")
				.setData("list", result)
				.setData("totalRecords", count)
				.build(), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseModel> delete(@PathVariable String id) throws Exception {
		Optional<User> resourceOptional=service.getById(id);
		if(resourceOptional.isPresent()) {
			service.deleteById(id);		
			return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder("user deleted successfully")
					.build(), HttpStatus.OK);
		} 
		return new ResponseEntity<ResponseModel>(new ResponseModel.ResponseModelBuilder("user not exist").build(), HttpStatus.BAD_REQUEST);
		
	}
	
	
}
