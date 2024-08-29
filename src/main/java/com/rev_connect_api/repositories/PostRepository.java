package com.rev_connect_api.repositories;

import com.rev_connect_api.models.Post;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, BigInteger> {
    public final static String UPDATE_PIN = "UPDATE posts SET isPinned = :isPinned WHERE postId = :id";

    List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Optional<Post> getPostByPostId(BigInteger id);

    void deletePostByPostId(BigInteger id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE posts p SET p.isPinned = :isPinned WHERE postId = :id", nativeQuery = true)
    void updatePin(BigInteger id,boolean isPinned);
    
}
