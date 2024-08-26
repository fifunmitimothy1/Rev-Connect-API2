package com.rev_connect_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")  // Updated to reflect the new table name
public class UserConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "user_password", nullable = false)  // Assuming this is mapped to `user_password` now
    private String password;

    @OneToMany(mappedBy = "requester")
    @JsonIgnore
    private List<ConnectionRequest> sentRequests;

    @OneToMany(mappedBy = "recipient")
    @JsonIgnore
    private List<ConnectionRequest> receivedRequests;

    // Getter and Setter for userId
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Getter and Setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and Setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and Setter for sentRequests
    public List<ConnectionRequest> getSentRequests() {
        return sentRequests;
    }

    public void setSentRequests(List<ConnectionRequest> sentRequests) {
        this.sentRequests = sentRequests;
    }

    // Getter and Setter for receivedRequests
    public List<ConnectionRequest> getReceivedRequests() {
        return receivedRequests;
    }

    public void setReceivedRequests(List<ConnectionRequest> receivedRequests) {
        this.receivedRequests = receivedRequests;
    }
}
