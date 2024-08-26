package com.rev_connect_api.models;

import com.rev_connect_api.enums.MediaType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private BigInteger mediaId;
    private BigInteger postId;
    private String mediaUrl;
    @Enumerated(EnumType.STRING)
    private MediaType mediaType;
    private Timestamp createdAt;

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
