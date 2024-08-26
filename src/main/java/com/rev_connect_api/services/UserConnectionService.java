package main.java.com.rev_connect_api.services;

import com.rev_connect_api.dto.UserSearchResultDTO;
import com.rev_connect_api.entity.UserConnection;
import com.rev_connect_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
public class UserConnectionService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConnectionRequestService connectionRequestService;

    public UserConnection getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public List<UserConnection> searchUsersByUsernameStartingWith(String query) {
        return userRepository.searchUsersByUsernameStartingWith(query);
    }

    public List<UserSearchResultDTO> searchUsersWithConditions(String query, Long currentUserId) {
        List<UserConnection> users = userRepository.searchUsersByUsernameStartingWith(query);

        return users.stream().map(user -> {
            boolean isSameUser = user.getAccountId().equals(currentUserId);
            boolean hasPendingRequest = connectionRequestService.hasPendingRequest(currentUserId, user.getAccountId())
                    || connectionRequestService.hasPendingRequest(user.getAccountId(), currentUserId);

            return new UserSearchResultDTO(user.getAccountId(), user.getUsername(), isSameUser, hasPendingRequest);
        }).collect(Collectors.toList());
    }

}
