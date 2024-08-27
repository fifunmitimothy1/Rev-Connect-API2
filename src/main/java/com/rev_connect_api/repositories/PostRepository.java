package com.rev_connect_api.repositories;

import com.rev_connect_api.models.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, BigInteger> {

    List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    //Selects all posts matching a given tag, accepting a pageable object to control sorting and 
    @Query(value = "Select p From posts p join tags t on p.postId = t.postId where t.tagString = :tag")
    List<Post> findAllByTag(@Param("tag")String tag, Pageable pageable);

    // //The following implementation depends on how follows are implemented, and makes some assumptions based on a likely "follows" table
    // //assume follows.followerId is current user, and follows.followingId
    // // TODO: refactor query to match follower implementation once it's merged
    // @Query(value = "Select p from posts p join follows f on p.postId = f.followingId where f.followerId = :userId")
    // List<Post> findAllByUsersFollows(@Param("userId")BigInteger userId, Pageable pageable);

    Optional<Post> getPostByPostId(BigInteger id);

    void deletePostByPostId(BigInteger id);

}
