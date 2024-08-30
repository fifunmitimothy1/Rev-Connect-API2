package main.java.com.rev_connect_api.services;

import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecommendationService {

    private final UserRepository userRepository;

    @Autowired
    public RecommendationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getRecommendedUsers(Long userId) {
        // Fetch users based on similar connections
        List<User> usersByConnections = userRepository.findUsersWithSimilarConnections(userId);

        // Combine results ensuring uniqueness
        Set<User> recommendedUsers = new HashSet<>(usersByConnections);

        return List.copyOf(recommendedUsers);
    }
}
