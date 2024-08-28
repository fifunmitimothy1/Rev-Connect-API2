package com.rev_connect_api.repositories;

import com.rev_connect_api.models.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, BigInteger> {

    void deleteMediaByPostId(BigInteger postId);

    List<Media> findAllByPostId(BigInteger postId);

}
