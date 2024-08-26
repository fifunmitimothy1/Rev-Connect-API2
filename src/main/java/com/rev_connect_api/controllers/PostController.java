package com.rev_connect_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rev_connect_api.exceptions.UnauthorizedUserException;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.services.PostService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/post")
public class PostController {
  private PostService postService;

  @Autowired
  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping("/feed/{id}")
  public ResponseEntity<List<Post>> retrieveFeed(@PathVariable("id") Long targetUserId) {
    try {
        String currentUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(postService.GetFeedForUser(currentUsername, targetUserId));
    } catch (UnauthorizedUserException e) { // target user's ID != authenticated user's ID
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    } 
  }
}
