package com.rev_connect_api.controllers;

import com.rev_connect_api.dto.UserSearchResult;
import com.rev_connect_api.entity.ConnectionRequest;
import com.rev_connect_api.services.ConnectionRequestService;
import com.rev_connect_api.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/connection")
public class ConnectionRequestController {

    private final ConnectionRequestService connectionRequestService;
    private final UserService userService;

    public ConnectionRequestController(ConnectionRequestService connectionRequestService, UserService userService) {
        this.connectionRequestService = connectionRequestService;
        this.userService = userService;
    }

    @PostMapping("/send")
    public ResponseEntity<ConnectionRequest> sendRequest(@RequestParam Long requesterId,
            @RequestParam Long recipientId) {
        try {
            ConnectionRequest request = connectionRequestService.sendRequest(requesterId, recipientId);
            return new ResponseEntity<>(request, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/pending/{userId}")
    public ResponseEntity<List<ConnectionRequest>> getPendingRequestsForUser(@PathVariable Long userId) {
        List<ConnectionRequest> pendingRequests = connectionRequestService.getPendingRequestsForUser(userId);
        return new ResponseEntity<>(pendingRequests, HttpStatus.OK);
    }

    @PostMapping("/accept/{requestId}")
    public ResponseEntity<Void> acceptRequest(@PathVariable Long requestId) {
        try {
            connectionRequestService.acceptRequest(requestId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reject/{requestId}")
    public ResponseEntity<Void> rejectRequest(@PathVariable Long requestId) {
        try {
            connectionRequestService.rejectRequest(requestId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/remove/{connectionId}")
    public ResponseEntity<Void> removeConnection(@PathVariable Long connectionId) {
        try {
            connectionRequestService.removeConnection(connectionId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/connections/{userId}")
    public ResponseEntity<List<ConnectionRequest>> findConnectionsByUserId(@PathVariable Long userId) {
        List<ConnectionRequest> connections = connectionRequestService.findConnectionsByUserId(userId);
        return new ResponseEntity<>(connections, HttpStatus.OK);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<List<UserSearchResult>> searchUsers(@PathVariable String query,
            @RequestParam Long currentUserId) {
        List<UserSearchResult> results = userService.searchUsersWithConditions(query, currentUserId);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

}
