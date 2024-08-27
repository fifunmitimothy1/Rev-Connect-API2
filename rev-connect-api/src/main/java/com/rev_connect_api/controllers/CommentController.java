package com.rev_connect_api.controllers;

import com.rev_connect_api.dto.CommentResponse;
import com.rev_connect_api.models.Comment;
import com.rev_connect_api.services.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@CrossOrigin(origins = "*") // Apply CORS configuration at the class level
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{postId}/comment")
    public ResponseEntity<List<CommentResponse>> getCommentsForPost(
            @PathVariable long postId,
            @RequestParam(required = false) Long userId // Optional query parameter
    ) {
        try {
            List<Comment> commentsForPost;
            if (userId != null) {
                commentsForPost = commentService.getCommentForPost(userId, postId);
            } else {
                commentsForPost = commentService.getAllCommentsByPostId(postId);
            }
            List<CommentResponse> responses = new ArrayList<>();
            for (Comment comment : commentsForPost) {
                long likesCount = commentService.getLikesCountForComment(comment.getCommentId());
                responses.add(new CommentResponse(comment, likesCount));
            }
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while fetching comments for post {}", postId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/comment")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        try {
            Comment createdComment = commentService.createComment(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
        } catch (Exception e) {
            logger.error("An error occurred while creating comment", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
