package com.rev_connect_api.models;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigInteger postId;
    private BigInteger userId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String title;
    private String content;

    public Post() {}

    public BigInteger getPostId() {
        return postId;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setPostId(BigInteger postId) {
        this.postId = postId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", content=" + content + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt=" + updatedAt + '\'' +
                '}';
    }

    public static final class Builder {
        private BigInteger postId;
        private BigInteger userId;
        private Timestamp createdAt;
        private Timestamp updatedAt;
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

        public Builder createdAt(Timestamp val) {
            createdAt = val;
            return this;
        }

        public Builder updatedAt(Timestamp val) {
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