package com.rev_connect_api.dto;

public class UserSearchResultDTO {
    private Long id;
    private String username;
    private boolean isSameUser;
    private boolean hasPendingRequest;

    public UserSearchResultDTO(Long id, String username, boolean isSameUser, boolean hasPendingRequest) {
        this.id = id;
        this.username = username;
        this.isSameUser = isSameUser;
        this.hasPendingRequest = hasPendingRequest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isSameUser() {
        return isSameUser;
    }

    public void setSameUser(boolean sameUser) {
        isSameUser = sameUser;
    }

    public boolean isHasPendingRequest() {
        return hasPendingRequest;
    }

    public void setHasPendingRequest(boolean hasPendingRequest) {
        this.hasPendingRequest = hasPendingRequest;
    }
}
