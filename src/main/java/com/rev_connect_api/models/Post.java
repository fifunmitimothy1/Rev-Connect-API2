package com.rev_connect_api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
 
@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq")
    @SequenceGenerator(name = "post_seq", sequenceName = "post_sequence", allocationSize = 1)
    @Column(name = "post_id")
    private BigInteger postId;

    @Column(name ="author_id", nullable = false)
    private Long authorId;

    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
        name = "post_hashtags",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private Set<Hashtag> hashtags; // Hashtags associated with this post


    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Static builder() method to create an instance of Builder
    public static Builder builder() {
        return new Builder();
    }

    // Constructor using the builder
    public Post(Builder builder) {
        this.postId = builder.postId;
        this.authorId = builder.authorId;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.content = builder.content;
        this.title = builder.title;
    }

    // Define the Builder class
    public static class Builder {
        private BigInteger postId;
        private Long authorId;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder postId(BigInteger postId) {
            this.postId = postId;
            return this;
        }

        public Builder authorId(Long authorId) {
            this.authorId = authorId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Post build() {
            return new Post(this);
        }
    }


    public void setPostedBy(User user) {
        this.authorId = user.getUserId();
    }

    public void setCreatedAt(LocalDateTime currentTimestamp) {
        this.createdAt = currentTimestamp;
    }

    public void setPostId(BigInteger id) {
        this.postId = id;
    }

    public BigInteger getPostId() {
        return postId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(LocalDateTime currentTimestamp) {
        this.updatedAt = currentTimestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String updatedContent) {
        this.content = updatedContent;
    }

    public Object getTitle() {
        return title;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public Object getUserId() {
        return authorId;
    }
}
