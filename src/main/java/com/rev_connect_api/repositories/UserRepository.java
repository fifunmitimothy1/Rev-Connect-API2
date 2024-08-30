package com.rev_connect_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rev_connect_api.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public List<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username LIKE CONCAT(:query, '%')")
    List<User> searchUsersByUsernameStartingWith(@Param("query") String query);

    // Query to find users with similar connections
    @Query("SELECT DISTINCT u FROM User u " +
            "JOIN ConnectionRequest cr1 ON u.userId = cr1.recipientId " +
            "JOIN ConnectionRequest cr2 ON cr1.requesterId = cr2.recipientId " +
            "WHERE cr2.requesterId = :userId AND u.userId != :userId " +
            "AND u.userId NOT IN (SELECT recipientId FROM ConnectionRequest WHERE requesterId = :userId " +
            "UNION SELECT requesterId FROM ConnectionRequest WHERE recipientId = :userId)")
    List<User> findUsersWithSimilarConnections(@Param("userId") Long userId);

    // Query to find users with similar posts
    @Query("SELECT DISTINCT u FROM User u " +
            "JOIN Post p ON u.userId = p.authorId " +
            "WHERE p.content LIKE %:content% AND u.userId != :userId " +
            "AND u.userId NOT IN (SELECT recipientId FROM ConnectionRequest WHERE requesterId = :userId " +
            "UNION SELECT requesterId FROM ConnectionRequest WHERE recipientId = :userId)")
    List<User> findUsersWithSimilarPosts(@Param("userId") Long userId, @Param("content") String content);
}
