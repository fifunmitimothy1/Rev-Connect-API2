package com.rev_connect_api.services;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.rev_connect_api.exceptions.InvalidUserException;
import com.rev_connect_api.models.Follow;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.FollowRepository;
import com.rev_connect_api.repositories.UserRepository;

@Service
public class FollowService {
  private UserRepository userRepository;
  private FollowRepository followRepository;
  public FollowService(UserRepository userRepository, FollowRepository followRepository) {
    this.userRepository = userRepository;
    this.followRepository = followRepository;
  }

  public void followUser(long followingId) throws InvalidUserException {
    Optional<User> optionalFollower = userRepository.findByUsername((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    if(!optionalFollower.isPresent()) {
      throw new InvalidUserException("Authenticated user does not exist");
    }
    User follower = optionalFollower.get();
    Optional<User> optionalFollowing = userRepository.findById(followingId);
    if(!optionalFollowing.isPresent()) {
      throw new InvalidUserException("User not found");
    }
    User following = optionalFollowing.get();
    Follow newFollowRelationship = new Follow(follower, following);
    followRepository.save(newFollowRelationship);

  }

  public void unfollowUser(long followingId) throws InvalidUserException {
    Optional<User> optionalFollower = userRepository.findByUsername((String)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    if(!optionalFollower.isPresent()) {
      throw new InvalidUserException("Authenticated user does not exist");
    }
    User follower = optionalFollower.get();
    Optional<User> optionalFollowing = userRepository.findById(followingId);
    if(!optionalFollowing.isPresent()) {
      throw new InvalidUserException("User not found");
    }
    User following = optionalFollowing.get();
    if(!follower.getFollowing().contains(following)) {
      return;
    }
    Follow followRelationshipToRemove = followRepository.findByFollowerAndFollowed(follower,following).get();
    followRepository.delete(followRelationshipToRemove);
  }
}
