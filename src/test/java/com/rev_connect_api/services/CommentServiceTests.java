package com.rev_connect_api.services;

import com.rev_connect_api.models.Comment;
import com.rev_connect_api.repositories.CommentLikesRepository;
import com.rev_connect_api.repositories.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentServiceTests {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentLikesRepository commentLikesRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateComment() {
        Comment comment = new Comment();
        comment.setCommentId(1L);
        comment.setPostId(1L);
        comment.setUserId(1L);

        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> {
            Comment savedComment = invocation.getArgument(0);
            savedComment.setCommentId(1L); // Simulate auto-generated ID
            return savedComment;
        });

        Comment createdComment = commentService.createComment(comment);

        assertNotNull(createdComment);
        assertEquals(1L, createdComment.getCommentId());
        assertNotNull(createdComment.getTimePosted());

        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    public void testGetCommentForPost() {
        long userId = 1L;
        long postId = 1L;

        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        comment1.setCommentId(1L);
        comment2.setCommentId(2L);

        when(commentRepository.findByUserIdAndPostId(userId, postId)).thenReturn(Arrays.asList(comment1, comment2));

        List<Comment> comments = commentService.getCommentForPost(userId, postId);

        assertEquals(2, comments.size());
        assertEquals(1L, comments.get(0).getCommentId());
        assertEquals(2L, comments.get(1).getCommentId());

        verify(commentRepository, times(1)).findByUserIdAndPostId(userId, postId);
    }

    @Test
    public void testGetCommentById() {
        long commentId = 1L;
        Comment comment = new Comment();
        comment.setCommentId(commentId);

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        Comment foundComment = commentService.getCommentById(commentId);

        assertNotNull(foundComment);
        assertEquals(commentId, foundComment.getCommentId());

        verify(commentRepository, times(1)).findById(commentId);
    }

    @Test
    public void testGetCommentById_NotFound() {
        long commentId = 1L;

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            commentService.getCommentById(commentId);
        });

        assertEquals("Comment not found with id: " + commentId, exception.getMessage());

        verify(commentRepository, times(1)).findById(commentId);
    }

    @Test
    public void testDoesCommentExist() {
        long commentId = 1L;

        when(commentRepository.existsByCommentId(commentId)).thenReturn(true);

        boolean exists = commentService.doesCommentExist(commentId);

        assertTrue(exists);

        verify(commentRepository, times(1)).existsByCommentId(commentId);
    }

    @Test
    public void testGetLikesCountForComment() {
        long commentId = 1L;
        long expectedCount = 10L;

        when(commentLikesRepository.countByCommentId(commentId)).thenReturn(expectedCount);

        long actualCount = commentService.getLikesCountForComment(commentId);

        assertEquals(expectedCount, actualCount);

        verify(commentLikesRepository, times(1)).countByCommentId(commentId);
    }

    @Test
    public void testGetAllCommentsByPostId() {
        long postId = 1L;

        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        comment1.setCommentId(1L);
        comment2.setCommentId(2L);

        when(commentRepository.findByPostId(postId)).thenReturn(Arrays.asList(comment1, comment2));

        List<Comment> comments = commentService.getAllCommentsByPostId(postId);

        assertEquals(2, comments.size());
        assertEquals(1L, comments.get(0).getCommentId());
        assertEquals(2L, comments.get(1).getCommentId());

        verify(commentRepository, times(1)).findByPostId(postId);
    }
}
