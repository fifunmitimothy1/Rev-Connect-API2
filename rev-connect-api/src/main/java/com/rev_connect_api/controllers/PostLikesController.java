package com.rev_connect_api.controllers;

import com.rev_connect_api.dto.PostCreateRequest;
import com.rev_connect_api.dto.PostResponse;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.repositories.PostLikesRepository;
import com.rev_connect_api.services.PostLikesService;
import com.rev_connect_api.services.PostService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/post")
public class PostLikesController {

    private final PostLikesService postLikesService;
    private final PostService postService;

    @Autowired
    public PostLikesController(PostLikesService postLikesService, PostService postService, PostLikesRepository postLikesRepository) {
        this.postLikesService = postLikesService;
        this.postService = postService;
    }

    @PutMapping("/{postId}/like")
    public ResponseEntity<PostResponse> likePost(@PathVariable long postId, @RequestParam long userId) {
        if(postService.doesPostExist(postId)){
            postLikesService.like(postId, userId);
            Post post = postService.getPostById(BigInteger.valueOf(postId));
            long likesCount = postLikesService.countLikesForPost(postId);
            PostResponse postResponse = new PostResponse(post, likesCount);
            System.out.println(postResponse.toString());
            return new ResponseEntity<>(postResponse, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
