package com.rev_connect_api.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.Set;

@Entity(name = "hashtags")
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the hashtag

    private String tag; // The actual hashtag text, e.g., "#coding"

    @ManyToMany(mappedBy = "followedHashtags")
    private Set<User> followedBy; // Users who follow this hashtag

    @ManyToMany(mappedBy = "hashtags")
    private Set<Post> posts; // Posts that are associated with this hashtag

    // Getter for 'id'
    public Long getId() {
        return id;
    }

    // Setter for 'id'
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for 'tag'
    public String getTag() {
        return tag;
    }

    // Setter for 'tag'
    public void setTag(String tag) {
        this.tag = tag;
    }

    // Getter for 'followedBy'
    public Set<User> getFollowedBy() {
        return followedBy;
    }

    // Setter for 'followedBy'
    public void setFollowedBy(Set<User> followedBy) {
        this.followedBy = followedBy;
    }

    // Getter for 'posts'
    public Set<Post> getPosts() {
        return posts;
    }

    // Setter for 'posts'
    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}

