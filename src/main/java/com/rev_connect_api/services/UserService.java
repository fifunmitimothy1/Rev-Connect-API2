package com.rev_connect_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rev_connect_api.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
}
