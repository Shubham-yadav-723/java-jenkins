package com.practise.SpringBootProject.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.practise.SpringBootProject.Entities.User;
import com.practise.SpringBootProject.dao.UserRepository;

@Controller
public class MainController {
	
	@Autowired
	UserRepository userRepo;

	@RequestMapping("/")
	public String home() {
		System.out.println("this is my page");
		return "home";
	}
	
	@GetMapping("/test")
	@ResponseBody
	public List<User> test() {
		System.out.println("this is test");
		
		
		
//		User user=new User();
	
//		user.setName("shubham");
//		user.setCity("unr");
//		user.setStatus("king");
//		
//		userRepo.save(user);
	 
//		Optional<User> optional=userRepo.findById(3);
//		
//		User user=optional.get();
//		
//		user.setName("Rahul Dravid");
//		
//		userRepo.save(user);
		
		
		
		
		return userRepo.findByStatus("king");
		
		
	}
	
	
	
	
}
