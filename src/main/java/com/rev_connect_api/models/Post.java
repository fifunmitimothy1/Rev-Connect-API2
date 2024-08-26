package com.rev_connect_api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigInteger postId;
    private BigInteger userId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String title;
    private String content;

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