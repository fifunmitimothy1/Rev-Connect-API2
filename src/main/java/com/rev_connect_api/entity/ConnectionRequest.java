package com.rev_connect_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private UserConnection requester;

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    @JsonIgnoreProperties({"sentRequests", "receivedRequests", "password"}) // Ignore unnecessary fields
    private UserConnection recipient;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestStatus status;

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

    public UserConnection getRequester() {
        return requester;
    }

    public void setRequester(UserConnection requester) {
        this.requester = requester;
    }

    public UserConnection getRecipient() {
        return recipient;
    }

    public void setRecipient(UserConnection recipient) {
        this.recipient = recipient;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
