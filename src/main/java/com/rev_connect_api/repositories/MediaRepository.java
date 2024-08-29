package com.rev_connect_api.repositories;

import com.rev_connect_api.models.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface MediaRepository extends JpaRepository<Media, Long> {

    void deleteMediaByPostId(Long postId);

    List<Media> findAllByPostId(Long postId);

}
