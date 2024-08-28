package com.rev_connect_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rev_connect_api.models.Post;
import com.rev_connect_api.services.PostService;

/**
 * Controller class that handles HTTP requests related to posts.
 */
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/post")
public class PostController {
  private PostService postService;

  /**
   * Constructs a PostController with the specified PostService.
   * 
   * @param postService the service used for post operations
   */
  @Autowired
  public PostController(PostService postService) {
    this.postService = postService;
  }

  /**
   * Retrieves the posts visible to the authenticated user.
   * 
   * @return ResponseEntity containing the posts that the user can see.
   */
  @GetMapping("/feed")
  public ResponseEntity<List<Post>> retrieveFeed() {
    try {
        String currentUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(postService.GetFeedForUser(currentUsername));
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    } 
  }
}
