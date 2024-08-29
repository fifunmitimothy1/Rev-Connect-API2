package com.rev_connect_api.services;

import com.rev_connect_api.dto.UserSearchResult;
import com.rev_connect_api.entity.User;
import com.rev_connect_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConnectionRequestService connectionRequestService;

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public List<User> searchUsersByUsernameStartingWith(String query) {
        return userRepository.searchUsersByUsernameStartingWith(query);
    }

    public List<UserSearchResult> searchUsersWithConditions(String query, Long currentUserId) {
        List<User> users = userRepository.searchUsersByUsernameStartingWith(query);

        return users.stream().map(user -> {
            boolean isSameUser = user.getUserId().equals(currentUserId);
            boolean hasPendingRequest = connectionRequestService.hasPendingRequest(currentUserId, user.getUserId())
                    || connectionRequestService.hasPendingRequest(user.getUserId(), currentUserId);

            return new UserSearchResult(user.getUserId(), user.getUsername(), isSameUser, hasPendingRequest);
        }).collect(Collectors.toList());
    }
}
