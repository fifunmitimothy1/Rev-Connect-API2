package com.rev_connect_api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rev_connect_api.dto.RequestStatusDTO;

import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "connection_requests")
public class ConnectionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "connection_id")
    private Long connectionId;

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    @JsonIgnoreProperties({"sentRequests", "receivedRequests", "password"}) // Ignore unnecessary fields
    private User requester;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    @JsonIgnoreProperties({"sentRequests", "receivedRequests", "password"}) // Ignore unnecessary fields
    private User recipient;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestStatusDTO status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Getters and Setters
    public Long getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(Long connectionId) {
        this.connectionId = connectionId;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public RequestStatusDTO getStatus() {
        return status;
    }

    public void setStatus(RequestStatusDTO status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
