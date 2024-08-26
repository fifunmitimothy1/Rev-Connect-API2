package com.rev_connect_api.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.rev_connect_api.exceptions.InvalidUserException;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.UserRepository;

@Service
public class FollowService {
  private UserRepository userRepository;
  public FollowService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void followUser(long userId) throws InvalidUserException {
    Optional<User> optionalFollower = userRepository.findByUsername((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    Optional<User> optionalToFollow = userRepository.findById(userId);
    if(!optionalFollower.isPresent()) {
      throw new InvalidUserException("Authenticated user does not exist");
    }
    User follower = optionalFollower.get();
    if(!optionalToFollow.isPresent()) {
      throw new InvalidUserException("User not found");
    }
    User toFollow = optionalToFollow.get();
    Set<User> newFollowing = follower.getFollowing();
    newFollowing.add(toFollow);
    follower.setFollowing(newFollowing);
    userRepository.save(follower);
  }

  public void unfollowUser(long userId) throws InvalidUserException {
    Optional<User> optionalFollower = userRepository.findByUsername((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    Optional<User> optionalToUnfollow = userRepository.findById(userId);
    if(!optionalFollower.isPresent()) {
      throw new InvalidUserException("Authenticated user does not exist");
    }
    User follower = optionalFollower.get();
    if(!optionalToUnfollow.isPresent()) {
      throw new InvalidUserException("User not found");
    }
    User toUnfollow = optionalToUnfollow.get();
    if(!follower.getFollowing().contains(toUnfollow)) {
      throw new InvalidUserException("User is not currently followed");
    }
    Set<User> newFollowing = follower.getFollowing();
    newFollowing.remove(toUnfollow);
    follower.setFollowing(newFollowing);
    userRepository.save(follower);
  }
}
