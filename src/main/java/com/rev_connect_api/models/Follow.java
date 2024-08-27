package com.rev_connect_api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="followers")
public class Follow {
  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  private Long id;

  public Follow() {

  }

  public Follow(User follower, User followed) {
    this.follower = follower;
    this.followed = followed;
  }

  @ManyToOne
  @JoinColumn(name = "follower_id", nullable = false)
  private User follower;

  @ManyToOne
  @JoinColumn(name = "following_id", nullable = false)
  private User followed;

  public User getFollower() {
    return follower;
  }

  public User getFollowed() {
    return followed;
  }

  

}
