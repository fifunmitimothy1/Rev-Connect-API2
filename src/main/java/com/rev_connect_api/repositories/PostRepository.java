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

    @Query(value = "Select p From posts p join tags t on p.postId = t.postId where t.tagString = :tag")
    List<Post> findAllByTag(@Param("tag")String tag, Pageable pageable);

    Optional<Post> getPostByPostId(BigInteger id);

    void deletePostByPostId(BigInteger id);
}
