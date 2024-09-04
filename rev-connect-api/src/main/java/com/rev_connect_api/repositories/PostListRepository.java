package com.rev_connect_api.repositories;

import com.rev_connect_api.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostListRepository extends JpaRepository<Post,Long> {
    //we can add custom Queries

}
