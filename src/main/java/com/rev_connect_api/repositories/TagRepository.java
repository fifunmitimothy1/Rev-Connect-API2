package com.rev_connect_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rev_connect_api.models.Tag;

import java.util.Optional;


public interface TagRepository extends JpaRepository<Tag, Long> {

        Optional<Tag> findByTagName(String tagName);
}
