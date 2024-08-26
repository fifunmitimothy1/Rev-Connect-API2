package com.rev_connect_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rev_connect_api.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{
    @Query("SELECT p FROM Post p WHERE " +
           "(p.isPrivate = false AND p.postedBy != :targetUserId) OR " +
           "(p.isPrivate = true AND p.postedBy IN :userConnections)")
    List<Post> findPosts(@Param("targetUserId") Long targetUserId, @Param("userConnections") List<Long> userConnections);
}
