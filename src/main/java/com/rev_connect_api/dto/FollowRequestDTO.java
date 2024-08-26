package com.rev_connect_api.dto;

public class FollowRequestDTO {
  private long userId; // Target User

  private String mode; // FOLLOW, UNFOLLOW, BLOCK

  public FollowRequestDTO(long userId, String mode) {
    this.userId = userId;
    this.mode = mode;
  }

  public long getUserId() {
    return userId;
  }

  public String getMode() {
    return mode;
  }

}
