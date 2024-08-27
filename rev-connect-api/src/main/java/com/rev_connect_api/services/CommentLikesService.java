package com.rev_connect_api.services;

import com.rev_connect_api.models.CommentLikes;
import com.rev_connect_api.repositories.CommentLikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentLikesService {

    private final CommentLikesRepository commentLikesRepository;

    @Autowired
    CommentLikesService(CommentLikesRepository commentLikesRepository) {
        this.commentLikesRepository = commentLikesRepository;
    }


public void like(long commentId, long userId) {
    // Check if the user already liked the comment or post
    Optional<CommentLikes> existingLike = commentLikesRepository.findByUserIdAndCommentId(
            userId, commentId);
    if (existingLike.isEmpty()) {
        CommentLikes newLike = new CommentLikes(commentId, userId, LocalDateTime.now());
        commentLikesRepository.save(newLike);
    }else{
        commentLikesRepository.delete(existingLike.get());
    }
}


    public long countLikesForComment(long commentId) {
        return commentLikesRepository.countByCommentId(commentId);
    }
}
