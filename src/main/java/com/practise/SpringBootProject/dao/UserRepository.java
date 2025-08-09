package com.practise.SpringBootProject.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.practise.SpringBootProject.Entities.User;

public interface UserRepository extends CrudRepository<User, Integer>{

	

	List<User> findByStatus(String status);
	
}
