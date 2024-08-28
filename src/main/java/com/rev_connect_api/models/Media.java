package com.rev_connect_api.models;

import com.rev_connect_api.enums.MediaType;
import jakarta.persistence.*;

import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigInteger mediaId;
    private BigInteger postId;
    private String mediaUrl;
    @Enumerated(EnumType.STRING)
    private MediaType mediaType;
    private Timestamp createdAt;

    public Media() {}

    private Media(Builder builder) {
        setMediaId(builder.mediaId);
        setPostId(builder.postId);
        setMediaUrl(builder.mediaUrl);
        setMediaType(builder.mediaType);
        setCreatedAt(builder.createdAt);
    }

    public static Builder builder() {
        return new Builder();
    }

    public BigInteger getMediaId() {
        return mediaId;
    }

    public void setMediaId(BigInteger mediaId) {
        this.mediaId = mediaId;
    }

    public BigInteger getPostId() {
        return postId;
    }

    public void setPostId(BigInteger postId) {
        this.postId = postId;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public static final class Builder {
        private BigInteger mediaId;
        private BigInteger postId;
        private String mediaUrl;
        private MediaType mediaType;
        private Timestamp createdAt;

        private Builder() {
        }

        public Builder mediaId(BigInteger val) {
            mediaId = val;
            return this;
        }

        public Builder postId(BigInteger val) {
            postId = val;
            return this;
        }

        public Builder mediaUrl(String val) {
            mediaUrl = val;
            return this;
        }

        public Builder mediaType(MediaType val) {
            mediaType = val;
            return this;
        }

        public Builder createdAt(Timestamp val) {
            createdAt = val;
            return this;
        }

        public Media build() {
            return new Media(this);
        }
    }
}
