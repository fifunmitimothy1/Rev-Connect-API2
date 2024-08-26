package com.rev_connect_api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.rev_connect_api.dto.FollowRequestDTO;
import com.rev_connect_api.exceptions.InvalidUserException;
import com.rev_connect_api.services.FollowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/follow")
public class FollowController {

  private FollowService followService;


  @Autowired
  public FollowController(FollowService followService) {
    this.followService = followService;
  }
  

  @PostMapping()
  public ResponseEntity<String> followUser(@RequestBody FollowRequestDTO dto) {
    try {
      switch (dto.getMode()) {
        case "FOLLOW" -> {
          followService.followUser(dto.getUserId());
          return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        case "UNFOLLOW" ->{
          followService.unfollowUser(dto.getUserId());
          return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        default -> {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid mode");
        }
      }
    } catch (InvalidUserException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }
  
}
