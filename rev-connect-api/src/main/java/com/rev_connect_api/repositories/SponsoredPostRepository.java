package com.rev_connect_api.repositories;

import com.rev_connect_api.models.SponsoredPost;

import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigInteger;

public interface SponsoredPostRepository extends JpaRepository<SponsoredPost, BigInteger>{
    
}
