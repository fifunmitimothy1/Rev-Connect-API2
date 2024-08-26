package com.rev_connect_api.controller;

import com.rev_connect_api.controllers.ConnectionRequestController;
import com.rev_connect_api.dto.UserSearchResult;
import com.rev_connect_api.entity.ConnectionRequest;
import com.rev_connect_api.services.ConnectionRequestService;
import com.rev_connect_api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

class ConnectionRequestControllerTest {

    @Mock
    private ConnectionRequestService connectionRequestService;

    @Mock
    private UserService userService;

    @InjectMocks
    private ConnectionRequestController connectionRequestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendRequestSuccess() {
        ConnectionRequest mockRequest = new ConnectionRequest();
        when(connectionRequestService.sendRequest(anyLong(), anyLong())).thenReturn(mockRequest);

        ResponseEntity<ConnectionRequest> response = connectionRequestController.sendRequest(1L, 2L);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockRequest, response.getBody());
    }

    @Test
    void testSendRequestFailure() {
        when(connectionRequestService.sendRequest(anyLong(), anyLong())).thenThrow(new IllegalArgumentException("Requester not found"));

        ResponseEntity<ConnectionRequest> response = connectionRequestController.sendRequest(1L, 2L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetPendingRequests() {
        List<ConnectionRequest> mockRequests = Arrays.asList(new ConnectionRequest());
        when(connectionRequestService.getPendingRequestsForUser(anyLong())).thenReturn(mockRequests);

        ResponseEntity<List<ConnectionRequest>> response = connectionRequestController.getPendingRequestsForUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockRequests, response.getBody());
    }

    @Test
    void testAcceptRequestSuccess() {
        // Test success without exception being thrown
        ResponseEntity<Void> response = connectionRequestController.acceptRequest(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testAcceptRequestFailure() {
        // Stubbing the void method using doThrow
        doThrow(new IllegalArgumentException("Request not found")).when(connectionRequestService).acceptRequest(anyLong());

        ResponseEntity<Void> response = connectionRequestController.acceptRequest(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testRejectRequestSuccess() {
        // Test success without exception being thrown
        ResponseEntity<Void> response = connectionRequestController.rejectRequest(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRejectRequestFailure() {
        // Stubbing the void method using doThrow
        doThrow(new IllegalArgumentException("Request not found")).when(connectionRequestService).rejectRequest(anyLong());

        ResponseEntity<Void> response = connectionRequestController.rejectRequest(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testRemoveConnectionSuccess() {
        // Test success without exception being thrown
        ResponseEntity<Void> response = connectionRequestController.removeConnection(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRemoveConnectionFailure() {
        // Stubbing the void method using doThrow
        doThrow(new IllegalArgumentException("Request not found")).when(connectionRequestService).removeConnection(anyLong());

        ResponseEntity<Void> response = connectionRequestController.removeConnection(1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testFindConnectionsByUserId() {
        List<ConnectionRequest> mockConnections = Arrays.asList(new ConnectionRequest());
        when(connectionRequestService.findConnectionsByUserId(anyLong())).thenReturn(mockConnections);

        ResponseEntity<List<ConnectionRequest>> response = connectionRequestController.findConnectionsByUserId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockConnections, response.getBody());
    }

    @Test
    void testSearchUsers() {
        List<UserSearchResult> mockResults = Arrays.asList(new UserSearchResult(1L, "user1", false, false));
        when(userService.searchUsersWithConditions(anyString(), anyLong())).thenReturn(mockResults);

        ResponseEntity<List<UserSearchResult>> response = connectionRequestController.searchUsers("user", 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockResults, response.getBody());
    }
}
