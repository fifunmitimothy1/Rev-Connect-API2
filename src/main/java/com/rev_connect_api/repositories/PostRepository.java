package com.rev_connect_api.repositories;

import com.rev_connect_api.models.Post;
import com.rev_connect_api.models.Tag;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    //Selects all posts matching a given tag, accepting a pageable object to control sorting and 
    @Query("SELECT p FROM Post p JOIN p.tags t WHERE t.name = :tagName")
    List<Post> findAllByTagName(@Param("tagName") String tagName, Pageable pageRequest);

    // //The following implementation depends on how follows are implemented, and makes some assumptions based on a likely "follows" table
    // //assume follows.followerId is current user, and follows.followingId
    // // TODO: refactor query to match follower implementation once it's merged
    // @Query(value = "Select p from posts p join follows f on p.postId = f.followingId where f.followerId = :userId")
    // List<Post> findAllByUsersFollows(@Param("userId")BigInteger userId, Pageable pageable);


    Optional<Post> getPostByPostId(Long id);

    List<Post> findByAuthorId(Long authorId);
}
