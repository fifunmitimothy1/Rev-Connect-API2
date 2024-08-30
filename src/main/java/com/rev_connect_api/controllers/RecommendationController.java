package main.java.com.rev_connect_api.controllers;

import com.rev_connect_api.dto.UserSearchResultDTO;
import com.rev_connect_api.models.User;
import com.rev_connect_api.security.Principal;
import com.rev_connect_api.services.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public ResponseEntity<List<UserSearchResultDTO>> getRecommendedUsers(Authentication authentication) {
        Principal principal = (Principal) authentication.getPrincipal();
        Long userId = principal.getUserId();

        List<User> recommendedUsers = recommendationService.getRecommendedUsers(userId);
        List<UserSearchResultDTO> result = recommendedUsers.stream()
            .map(user -> new UserSearchResultDTO(user.getUserId(), user.getUsername(), false, false))
            .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }
}
