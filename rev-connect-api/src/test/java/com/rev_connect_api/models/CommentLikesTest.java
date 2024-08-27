package com.rev_connect_api.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class CommentLikesTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");

    @Test
    public void testCommentLikesGettersAndSetters() {
        CommentLikes commentLikes = new CommentLikes();


        commentLikes.setCommentLikeId(1L);
        commentLikes.setUserId(100L);
        commentLikes.setCommentId(10L);
        commentLikes.setTimePosted(LocalDateTime.now());

        assertEquals(1L, commentLikes.getCommentLikeId());
        assertEquals(100L, commentLikes.getUserId());
        assertEquals(10L, commentLikes.getCommentId());

        assertEquals(LocalDateTime.now().format(FORMATTER), commentLikes.getTimePosted().format(FORMATTER));
    }

    @Test
    public void testCommentLikesConstructor() {
        CommentLikes commentLikes = new CommentLikes(10L, 100L, LocalDateTime.now());

        assertEquals(100L, commentLikes.getUserId());
        assertEquals(10L, commentLikes.getCommentId());
        assertEquals(LocalDateTime.now().format(FORMATTER), commentLikes.getTimePosted().format(FORMATTER));
    }
}