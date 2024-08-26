package com.rev_connect_api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rev_connect_api.exceptions.InvalidUserException;
import com.rev_connect_api.exceptions.UnauthorizedUserException;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.PostRepository;
import com.rev_connect_api.repositories.UserRepository;

@Service
public class PostService {
    private PostRepository postRepository;
    private UserRepository userRepository;
    private ConnectionService connectionService;
    
    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, ConnectionService connectionService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.connectionService = connectionService;
    }

  public List<Post> GetFeedForUser(String authenticatedUsername, Long targetUserId) throws UnauthorizedUserException, InvalidUserException {
    Optional<User> targetUser = userRepository.findById(targetUserId);
    
    if(!targetUser.get().getUsername().equals(authenticatedUsername)) {
      throw new UnauthorizedUserException();
    }

    List<Long> userConnections = connectionService.getConnectedUserIds(targetUserId);
    return postRepository.findPosts(targetUserId, userConnections);
  }
  
}
