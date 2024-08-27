package com.rev_connect_api.services;

import com.rev_connect_api.models.Post;
import com.rev_connect_api.models.PostLikes;

import com.rev_connect_api.repositories.PostLikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostLikesService {

    private final PostLikesRepository postLikesRepository;

    @Autowired
    public PostLikesService(PostLikesRepository postLikesRepository) {
        this.postLikesRepository = postLikesRepository;
    }

    public void like(long postId, long userId) {
        // Check if the user already liked the post
        Optional<PostLikes> existingLike = postLikesRepository.findByUserIdAndPostId(
                userId, postId);
        System.out.println(existingLike.toString());

        if (existingLike.isEmpty()) {
            PostLikes newLike = new PostLikes(postId, userId, LocalDateTime.now());
            postLikesRepository.save(newLike);
            System.out.println("Liked post ID: " + postId + " by user ID: " + userId);
        } else {
            postLikesRepository.delete(existingLike.get());
            System.out.println("IN DELETE: Liked post ID: " + postId + " by user ID: " + userId);
        }
    }

    public long countLikesForPost(long postId) {
        return postLikesRepository.countByPostId(postId);
    }
}
