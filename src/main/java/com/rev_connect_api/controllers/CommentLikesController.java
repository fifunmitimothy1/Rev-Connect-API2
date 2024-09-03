package com.rev_connect_api.controllers;

import com.rev_connect_api.dto.CommentResponse;
import com.rev_connect_api.models.Comment;
import com.rev_connect_api.services.CommentLikesService;
import com.rev_connect_api.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class CommentLikesController {

    @Autowired
    private CommentLikesService commentLikesService;

    @Autowired
    private CommentService commentService;

    /**
     * Handles the process of liking a comment.
     * @param userId The ID of the user who is liking the comment.
     * @param commentId The ID of the comment being liked.
     * @return A ResponseEntity containing the updated CommentResponse object and HTTP status.
     */
    @PutMapping("/comment/{commentId}/like")
    @CrossOrigin(origins = "*") // Enable CORS for this endpoint
    public ResponseEntity<CommentResponse> likeComment(@RequestParam long userId, @PathVariable long commentId) {
        // Check if the comment exists
        if (commentService.doesCommentExist(commentId)) {
            // Process the like action
            commentLikesService.like(commentId, userId);

            // Retrieve the updated comment and its likes count
            Comment updatedComment = commentService.getCommentById(commentId);
            long likesCount = commentLikesService.countLikesForComment(commentId);

            // Create a CommentResponse object to return
            CommentResponse commentResponse = new CommentResponse(updatedComment, likesCount);

            // Return the updated comment with HTTP status OK
            return new ResponseEntity<>(commentResponse, HttpStatus.OK);
        } else {
            // Return NOT_FOUND status if the comment does not exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
