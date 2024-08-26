package com.rev_connect_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rev_connect_api.entity.UserConnection;
import com.rev_connect_api.models.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    public List<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username LIKE CONCAT(:query, '%')")
    List<UserConnection> searchUsersByUsernameStartingWith(@Param("query") String query);
}
