package com.rev_connect_api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public void register(User user){
		userRepository.save(user);
	}
	
	public User getUser(String username){
		Optional<User> user = userRepository.findByUsername(username);
		if(user.isPresent())
			return user.get();
		else
			return null;
	}

}
