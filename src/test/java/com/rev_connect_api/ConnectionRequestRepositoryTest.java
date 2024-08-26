package com.rev_connect_api.repositories;

import com.rev_connect_api.entity.ConnectionRequest;
import com.rev_connect_api.entity.RequestStatus;
import com.rev_connect_api.entity.UserConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ConnectionRequestRepositoryTest {

    @Autowired
    private ConnectionRequestRepository connectionRequestRepository;

    @Autowired
    private UserRepository userRepository;

    private UserConnection requester;
    private UserConnection recipient;
    private ConnectionRequest pendingRequest;
    private ConnectionRequest acceptedRequest;

    @BeforeEach
    void setUp() {
        requester = new UserConnection();
        requester.setUsername("requester");
        requester.setEmail("requester@example.com");
        userRepository.save(requester);

        recipient = new UserConnection();
        recipient.setUsername("recipient");
        recipient.setEmail("recipient@example.com");
        userRepository.save(recipient);

        pendingRequest = new ConnectionRequest();
        pendingRequest.setRequester(requester);
        pendingRequest.setRecipient(recipient);
        pendingRequest.setStatus(RequestStatus.PENDING);
        connectionRequestRepository.save(pendingRequest);

        acceptedRequest = new ConnectionRequest();
        acceptedRequest.setRequester(requester);
        acceptedRequest.setRecipient(recipient);
        acceptedRequest.setStatus(RequestStatus.ACCEPTED);
        connectionRequestRepository.save(acceptedRequest);
    }

    @Test
    void testFindPendingRequestsForUser() {
        List<ConnectionRequest> pendingRequests = connectionRequestRepository.findPendingRequestsForUser(recipient.getAccountId());
        assertEquals(1, pendingRequests.size());
        assertEquals(RequestStatus.PENDING, pendingRequests.get(0).getStatus());
    }

    @Test
    void testFindAcceptedConnectionsForUser() {
        List<ConnectionRequest> acceptedRequests = connectionRequestRepository.findAcceptedConnectionsForUser(recipient.getAccountId());
        assertEquals(1, acceptedRequests.size());
        assertEquals(RequestStatus.ACCEPTED, acceptedRequests.get(0).getStatus());
    }

    @Test
    void testFindConnectionsByUserId() {
        List<ConnectionRequest> connections = connectionRequestRepository.findConnectionsByUserId(requester.getAccountId());
        assertEquals(2, connections.size());
    }

    @Test
    void testExistsByRequesterAccountIdAndRecipientAccountIdAndStatus() {
        boolean exists = connectionRequestRepository.existsByRequesterAccountIdAndRecipientAccountIdAndStatus(
                requester.getAccountId(), recipient.getAccountId(), RequestStatus.PENDING);
        assertTrue(exists);
    }
}
