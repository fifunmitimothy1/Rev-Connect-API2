package com.rev_connect_api.repositories;

import com.rev_connect_api.models.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Optional<Post> getPostByPostId(Long id);

    void deletePostByPostId(Long id);

    List<Post> findByAuthorId(Long authorId);
}
