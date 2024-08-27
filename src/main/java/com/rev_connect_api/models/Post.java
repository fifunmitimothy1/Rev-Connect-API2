package com.rev_connect_api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
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

    @Column(name ="posted_by")
    private BigInteger userId;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", updatable = true, nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    private Post(Builder builder) {
        postId = builder.postId;
        userId = builder.userId;
        createdAt = builder.createdAt;
        updatedAt = builder.updatedAt;
        title = builder.title;
        content = builder.content;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private BigInteger postId;
        private BigInteger userId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String title;
        private String content;

        private Builder() {
        }

        public Builder postId(BigInteger val) {
            postId = val;
            return this;
        }

        public Builder userId(BigInteger val) {
            userId = val;
            return this;
        }

        public Builder createdAt(LocalDateTime val) {
            createdAt = val;
            return this;
        }

        public Builder updatedAt(LocalDateTime val) {
            updatedAt = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder content(String val) {
            content = val;
            return this;
        }

        public Post build() {
            return new Post(this);
        }
    }
}
