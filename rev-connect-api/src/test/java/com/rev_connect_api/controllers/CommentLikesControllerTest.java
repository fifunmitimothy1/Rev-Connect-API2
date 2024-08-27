package com.rev_connect_api.controllers;

import com.rev_connect_api.dto.CommentResponse;
import com.rev_connect_api.models.Comment;
import com.rev_connect_api.services.CommentLikesService;
import com.rev_connect_api.services.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the CommentLikesController class.
 *
 * This class contains tests to verify the behavior of the likeComment method
 * in different scenarios, using Mockito for mocking dependencies and JUnit for assertions.
 */
@ExtendWith(SpringExtension.class)
public class CommentLikesControllerTest {

    @InjectMocks
    private CommentLikesController commentLikesController; // Injects mock dependencies into the controller

    @Mock
    private CommentService commentService; // Mocks the CommentService dependency

    @Mock
    private CommentLikesService commentLikesService; // Mocks the CommentLikesService dependency

    /**
     * Tests the likeComment method when the comment exists and the like operation succeeds.
     *
     * This test verifies that:
     * - The HTTP status is OK (200).
     * - The response body contains the expected Comment and the like count.
     * - The correct service methods are called.
     */
    @Test
    public void testLikesComment_Success() {
        long userId = 1L;
        long commentId = 2L;

        Comment comment1 = new Comment();
        comment1.setCommentId(1L);
        Comment comment2 = new Comment();
        comment2.setCommentId(2L);

        when(commentService.doesCommentExist(commentId)).thenReturn(true);
        when(commentService.getCommentById(commentId)).thenReturn(comment2);
        when(commentLikesService.countLikesForComment(commentId)).thenReturn(0L);

        ResponseEntity<CommentResponse> response = commentLikesController.likeComment(userId, commentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        CommentResponse commentResponse = response.getBody();

        assert commentResponse != null;
        assertEquals(comment2, commentResponse.getComment());
        assertEquals(0L, commentResponse.getLikesCount());

        verify(commentService).doesCommentExist(commentId);
        verify(commentService).getCommentById(commentId);
        verify(commentLikesService).countLikesForComment(commentId);
    }

    /**
     * Tests the likeComment method when the comment does not exist.
     *
     * This test verifies that:
     * - The HTTP status is NOT FOUND (404).
     * - No interactions with the CommentLikesService occur if the comment is not found.
     */
    @Test
    public void testLikesComment_Failure() {
        long userId = 1L;
        long commentId = 2L;


        when(commentService.doesCommentExist(commentId)).thenReturn(false);

        ResponseEntity<CommentResponse> response = commentLikesController.likeComment(userId, commentId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(commentService).doesCommentExist(commentId);
        verifyNoInteractions(commentLikesService);
    }
}
