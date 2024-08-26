package com.rev_connect_api.services;

import com.rev_connect_api.entity.ConnectionRequest;
import com.rev_connect_api.entity.RequestStatus;
import com.rev_connect_api.entity.UserConnection;
import com.rev_connect_api.repositories.ConnectionRequestRepository;
import com.rev_connect_api.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ConnectionRequestServiceTest {

    @Mock
    private ConnectionRequestRepository connectionRequestRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ConnectionRequestService connectionRequestService;

    private UserConnection requester;
    private UserConnection recipient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        requester = new UserConnection();
        requester.setAccountId(1L);
        requester.setUsername("requester");

        recipient = new UserConnection();
        recipient.setAccountId(2L);
        recipient.setUsername("recipient");
    }

    @Test
    void testSendRequestSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(requester));
        when(userRepository.findById(2L)).thenReturn(Optional.of(recipient));

        ConnectionRequest connectionRequest = new ConnectionRequest();
        connectionRequest.setRequester(requester);
        connectionRequest.setRecipient(recipient);
        connectionRequest.setStatus(RequestStatus.PENDING);

        when(connectionRequestRepository.save(connectionRequest)).thenReturn(connectionRequest);

        ConnectionRequest result = connectionRequestService.sendRequest(1L, 2L);

        assertEquals(RequestStatus.PENDING, result.getStatus());
        assertEquals(requester, result.getRequester());
        assertEquals(recipient, result.getRecipient());
    }

    @Test
    void testSendRequestRecipientNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(requester));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> connectionRequestService.sendRequest(1L, 2L));
    }
}
