package com.rev_connect_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rev_connect_api.models.Follow;
import com.rev_connect_api.models.User;

import java.util.Set;
import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
  Optional<Follow> findByFollowerAndFollowed(User follower, User followed);
  @Query("SELECT followed FROM Follow where follower = :user")
  List<User> findUsersWhoUserFollows(@Param("user") User user);
  @Query("SELECT followed FROM Follow where followed = :user")
  List<User> findUsersWhoFollowUser(@Param("user") User user);
}

