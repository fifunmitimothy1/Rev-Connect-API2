package com.rev_connect_api.repositories;

import com.rev_connect_api.models.Post;
import com.rev_connect_api.models.User;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, BigInteger> {

    /**
     * Finds posts visible to the user given by targetUserID with the given userConnections.
     * This includes posts which are public, and posts which are private and posted by connected
     * users.
     * 
     * @param followingList the other users which this user follows
     * @return a list of posts which could be seen within the user's feed
     */
    @Query("SELECT p FROM Post p WHERE " +
           "(p.isPrivate = false) OR " +
           "(p.isPrivate = true AND p.userId IN :followingList)")
    List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable, @Param("followingList") List<BigInteger> followingList);

    Optional<Post> getPostByPostId(BigInteger id);

    void deletePostByPostId(BigInteger id);
}
