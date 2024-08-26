package com.rev_connect_api.security;

public class Principal {
    // The object to be passed to auth token for JWT Authentication Filter
    private Long userId;
    private String username;

    public Principal(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
