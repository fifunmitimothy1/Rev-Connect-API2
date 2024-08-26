package com.rev_connect_api.services;

import com.rev_connect_api.entity.ConnectionRequest;
import com.rev_connect_api.entity.RequestStatus;
import com.rev_connect_api.entity.UserConnection;
import com.rev_connect_api.repositories.ConnectionRequestRepository;
import com.rev_connect_api.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConnectionRequestService {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionRequestService.class);

    private final ConnectionRequestRepository connectionRequestRepository;
    private final UserRepository userRepository;

    public ConnectionRequestService(ConnectionRequestRepository connectionRequestRepository,
            UserRepository userRepository) {
        this.connectionRequestRepository = connectionRequestRepository;
        this.userRepository = userRepository;
    }

    public ConnectionRequest sendRequest(Long requesterId, Long recipientId) {
        logger.info("Attempting to send connection request from user {} to user {}", requesterId, recipientId);

        if (requesterId.equals(recipientId)) {
            throw new IllegalArgumentException("You cannot send a connection request to yourself.");
        }

        Optional<UserConnection> requester = userRepository.findById(requesterId);
        Optional<UserConnection> recipient = userRepository.findById(recipientId);

        if (!requester.isPresent()) {
            logger.error("Requester with ID {} not found", requesterId);
            throw new IllegalArgumentException("Requester not found");
        }

        if (!recipient.isPresent()) {
            logger.error("Recipient with ID {} not found", recipientId);
            throw new IllegalArgumentException("Recipient not found");
        }

        if (hasPendingRequest(requesterId, recipientId)) {
            logger.error("A connection request already exists between user {} and user {}", requesterId, recipientId);
            throw new IllegalArgumentException("A connection request already exists.");
        }

        ConnectionRequest request = new ConnectionRequest();
        request.setRequester(requester.get());
        request.setRecipient(recipient.get());
        request.setStatus(RequestStatus.PENDING);

        return connectionRequestRepository.save(request);
    }

    public List<ConnectionRequest> getPendingRequestsForUser(Long userId) {
        logger.info("Fetching pending connection requests for user {}", userId);
        return connectionRequestRepository.findPendingRequestsForUser(userId);
    }

    public void acceptRequest(Long requestId) {
        logger.info("Accepting connection request with ID {}", requestId);

        ConnectionRequest request = connectionRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        if (request.getStatus() == RequestStatus.ACCEPTED) {
            logger.warn("Connection request with ID {} has already been accepted", requestId);
            throw new IllegalStateException("Request already accepted");
        }

        request.setStatus(RequestStatus.ACCEPTED);
        connectionRequestRepository.save(request);
    }

    public void rejectRequest(Long requestId) {
        logger.info("Rejecting connection request with ID {}", requestId);

        ConnectionRequest request = connectionRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        if (request.getStatus() == RequestStatus.REJECTED) {
            logger.warn("Connection request with ID {} has already been rejected", requestId);
            throw new IllegalStateException("Request already rejected");
        }

        request.setStatus(RequestStatus.REJECTED);
        connectionRequestRepository.save(request);
    }

    public void removeConnection(Long requestId) {
        logger.info("Removing connection request with ID {}", requestId);

        ConnectionRequest request = connectionRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        if (request.getStatus() != RequestStatus.ACCEPTED) {
            logger.warn("Only accepted connections can be removed. Request ID: {}", requestId);
            throw new IllegalStateException("Only accepted connections can be removed");
        }

        connectionRequestRepository.delete(request);
    }

    public List<ConnectionRequest> getAcceptedConnectionsForUser(Long userId) {
        logger.info("Fetching accepted connection requests for user {}", userId);
        return connectionRequestRepository.findAcceptedConnectionsForUser(userId);
    }

    public List<ConnectionRequest> findConnectionsByUserId(Long userId) {
        logger.info("Fetching all connections for user {}", userId);
        return connectionRequestRepository.findConnectionsByUserId(userId);
    }

    public boolean hasPendingRequest(Long requesterId, Long recipientId) {
        return connectionRequestRepository.existsByRequesterAccountIdAndRecipientAccountIdAndStatus(requesterId,
                recipientId, RequestStatus.PENDING);
    }
}
