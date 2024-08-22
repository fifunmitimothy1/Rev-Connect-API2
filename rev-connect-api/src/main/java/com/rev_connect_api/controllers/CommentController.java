package com.rev_connect_api.controllers;

import com.rev_connect_api.dto.CommentResponse;
import com.rev_connect_api.models.Comment;
import com.rev_connect_api.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CommentController {
    private static final Logger logger = Logger.getLogger(CommentController.class.getName());
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{userId}/post/{postId}/comments")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<CommentResponse>> getCommentsForPost(@PathVariable long userId, @PathVariable long postId) {
        List<Comment> commentsForPost = commentService.getCommentForPost(userId, postId);
        List<CommentResponse> responses = new ArrayList<>();
        for (Comment comment : commentsForPost) {
            long likesCount = commentService.getLikesCountForComment(comment.getCommentId());
            responses.add(new CommentResponse(comment, likesCount));
        }
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }


    @PostMapping("/comments")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        try {
            commentService.createComment(comment);
            return ResponseEntity.ok().body(comment);
        } catch (Exception e) {
            logger.severe("An error occurred, could not create comment.");
            logger.severe(e.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
