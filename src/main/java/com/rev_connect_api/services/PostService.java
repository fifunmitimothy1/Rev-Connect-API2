package com.rev_connect_api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rev_connect_api.models.Post;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.PostRepository;
import com.rev_connect_api.repositories.UserRepository;

/**
 * Service class that provides operations for retrieving posts.
 */
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

    /**
     * Returns a list of posts that are visible to the authenticated user: public posts
     * from any account and private posts from connected accounts.
     * 
     * @param authenticatedUsername the username of the user who is making the request.
     * @return a list of visible posts.
     */
    public List<Post> GetFeedForUser(String authenticatedUsername) {
      Optional<User> user = userRepository.findByUsername(authenticatedUsername);
      Long id = user.isPresent() ? user.get().getUserId() : null;
      List<Long> userConnections = connectionService.getConnectedUserIds(id);
      return postRepository.findVisiblePosts(id, userConnections);
    }
  
}
