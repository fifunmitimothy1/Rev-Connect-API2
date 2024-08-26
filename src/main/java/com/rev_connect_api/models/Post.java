package com.rev_connect_api.models;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="posts")
public class Post {
    @Column(name="postId")
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer postId;
    @Column(name="postedBy")
    private Long postedBy;
    @Column(name="postText")
    private String postText;
    @Column(name="timePostedEpoch")
    private Long timePostedEpoch;
    @Column(name="isPrivate")
    private Boolean isPrivate;

    @Autowired
    public Post(){
        this.postId = 9001;
        this.postedBy = 9001L;
        this.postText = "default post";
        this.timePostedEpoch = 123456789L;
    }
    public Post(Integer postId, Long postedBy, String postText, Long timePostedEpoch, Boolean isPrivate) {
        this.postId = postId;
        this.postedBy = postedBy;
        this.postText = postText;
        this.timePostedEpoch = timePostedEpoch;
        this.isPrivate = isPrivate;
    }
    public Post(Integer postId, Long postedBy, String postText, Long timePostedEpoch) {
        this.postId = postId;
        this.postedBy = postedBy;
        this.postText = postText;
        this.timePostedEpoch = timePostedEpoch;
    }
    public Post(Long postedBy, String postText, Long timePostedEpoch) {
        this.postedBy = postedBy;
        this.postText = postText;
        this.timePostedEpoch = timePostedEpoch;
    }
    public Integer getPostId() {
        return postId;
    }
    public void setPostId(Integer postId) {
        this.postId = postId;
    }
    public Long getPostedBy() {
        return postedBy;
    }
    public void setPostedBy(Long postedBy) {
        this.postedBy = postedBy;
    }
    public String getPostText() {
        return postText;
    }
    public void setPostText(String postText) {
        this.postText = postText;
    }
    public Long getTimePostedEpoch() {
        return timePostedEpoch;
    }
    public void setTimePostedEpoch(Long timePostedEpoch) {
        this.timePostedEpoch = timePostedEpoch;
    }
    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (postId == null) {
			if (other.postId != null)
				return false;
		} else if (!postId.equals(other.postId))
			return false;
		if (postText == null) {
			if (other.postText != null)
				return false;
		} else if (!postText.equals(other.postText))
			return false;
		if (postedBy == null) {
			if (other.postedBy != null)
				return false;
		} else if (!postedBy.equals(other.postedBy))
			return false;
		if (timePostedEpoch == null) {
			if (other.timePostedEpoch != null)
				return false;
		} else if (!timePostedEpoch.equals(other.timePostedEpoch))
			return false;
		return true;
	}
	
    /**
     * Overriding the default toString() method allows for easy debugging.
     * @return a String representation of this class.
     */
    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", postedBy=" + postedBy +
                ", postText='" + postText + '\'' +
                ", timePostedEpoch=" + timePostedEpoch +
                '}';
    }
}