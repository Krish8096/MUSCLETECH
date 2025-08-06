package com.example.GYMsupplement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.GYMsupplement.entity.User;



public interface UserRepository extends  JpaRepository<User,Integer>{

	User findByUsernameAndPassword(String username,String password);
	User findByUsername(String username);

}
