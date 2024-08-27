package com.rev_connect_api.controllers;

import com.rev_connect_api.dto.CommentResponse;
import com.rev_connect_api.models.Comment;
import com.rev_connect_api.models.CommentLikes;
import com.rev_connect_api.services.CommentLikesService;
import com.rev_connect_api.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/post")
public class CommentLikesController {
    @Autowired
    private CommentLikesService commentLikesService;
    @Autowired
    private CommentService commentService;

    @PutMapping("/comment/{commentId}/like")
    @CrossOrigin(origins = "*")
    public ResponseEntity<CommentResponse> likeComment(@RequestParam long userId, @PathVariable long commentId) {
        if (commentService.doesCommentExist(commentId)) {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
//            LocalDateTime now = LocalDateTime.now();
////            String dateTimeString = now.format(formatter);
//            CommentLikes like = new CommentLikes(userId, commentId, now);
//            commentLikesService.like(like);

            commentLikesService.like(commentId, userId);
            Comment updatedComment = commentService.getCommentById(commentId);
            long likesCount = commentLikesService.countLikesForComment(commentId);

            CommentResponse commentResponse = new CommentResponse(updatedComment, likesCount);

            return new ResponseEntity<>(commentResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
