package com.rev_connect_api.models;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentTest {
    @Test
    public void testCommentGettersAndSetters() {
        Comment comment = new Comment();


        comment.setCommentId(1L);
        comment.setUserId(100L);
        comment.setPostId(10L);
        comment.setText("This is a comment.");
        comment.setTimePosted("2024-08-15 10:00:00 AM");


        assertThat(comment.getCommentId()).isNotNull();
        assertEquals(1L, comment.getCommentId());
        assertEquals(100L, comment.getUserId());
        assertEquals(10L, comment.getPostId());
        assertEquals("This is a comment.", comment.getText());
        assertEquals("2024-08-15 10:00:00 AM", comment.getTimePosted());
    }

    @Test
    public void testCommentConstructor() {
        Comment comment = new Comment(100L, 10L, "This is a comment.", "2024-08-15 10:00:00 AM");

        assertEquals(100L, comment.getUserId());
        assertEquals(10L, comment.getPostId());
        assertEquals("This is a comment.", comment.getText());
        assertEquals("2024-08-15 10:00:00 AM", comment.getTimePosted());
    }

    @Test
    public void testCommentToString() {
        Comment comment = new Comment(100L, 10L, "This is a comment.", "2024-08-15 10:00:00 AM");

        String expectedToString = "Comment{" +
                "commentId=0" +
                ", userId=100" +
                ", postId=10" +
                ", text='This is a comment.'" +
                ", timePosted='2024-08-15 10:00:00 AM'" +
                '}';

        assertEquals(expectedToString, comment.toString());
    }
}
