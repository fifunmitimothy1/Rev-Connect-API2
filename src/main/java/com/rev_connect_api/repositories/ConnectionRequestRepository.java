package com.rev_connect_api.repositories;

import com.rev_connect_api.dto.RequestStatusDTO;
import com.rev_connect_api.models.ConnectionRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Long> {

    @Query("SELECT cr FROM ConnectionRequest cr WHERE cr.recipient.userId = :userId AND cr.status = 'PENDING'")
    List<ConnectionRequest> findPendingRequestsForUser(@Param("userId") Long userId);

    @Query("SELECT cr FROM ConnectionRequest cr WHERE cr.recipient.userId = :userId AND cr.status = 'ACCEPTED'")
    List<ConnectionRequest> findAcceptedConnectionsForUser(@Param("userId") Long userId);

    @Query("SELECT cr FROM ConnectionRequest cr WHERE (cr.requester.userId = :userId OR cr.recipient.userId = :userId) AND cr.status = 'ACCEPTED'")
    List<ConnectionRequest> findConnectionsByUserId(@Param("userId") Long userId);

    boolean existsByRequesterUserIdAndRecipientUserIdAndStatus(Long requesterId, Long recipientId, RequestStatusDTO status);
}
