package com.rev_connect_api.controllers;

import com.rev_connect_api.dto.PostCreateRequest;
import com.rev_connect_api.dto.PostResponse;
import com.rev_connect_api.models.Media;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.services.MediaService;
import com.rev_connect_api.services.PostService;
import com.rev_connect_api.util.TimestampUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final TimestampUtil timestampUtil;
    private final PostService postService;
    private final MediaService mediaService;

    /**
     * Constructs a PostController with the necessary services and utilities.
     * @param timestampUtil Utility for handling timestamps.
     * @param postService Service for managing posts.
     * @param mediaService Service for managing media associated with posts.
     */
    public PostController(TimestampUtil timestampUtil, PostService postService, MediaService mediaService) {
        this.timestampUtil = timestampUtil;
        this.postService = postService;
        this.mediaService = mediaService;
    }

    /**
     * Creates a new post with optional media file.
     * @param title The title of the post.
     * @param content The content of the post.
     * @param file Optional media file to be associated with the post.
     * @return A ResponseEntity containing the created Post and HTTP status.
     */
    @PostMapping
    public ResponseEntity<Post> CreatePost(@RequestParam("title") String title,
                                           @RequestParam("content") String content,
                                           @RequestParam(value = "file", required = false) MultipartFile file) {
        Post post = postService.postDtoToPost(new PostCreateRequest(title, content));
        post.setUserId(new BigInteger("1")); // Set a dummy user ID (for demonstration purposes)
        post.setCreatedAt(timestampUtil.getCurrentTimestamp()); // Set the creation timestamp

        Post response;
        if (file != null) {
            response = postService.savePost(post, file);
        } else {
            response = postService.savePost(post);
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a post by its ID.
     * @param id The ID of the post to retrieve.
     * @return A ResponseEntity containing the PostResponse object with post details and like count.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> GetPostById(@PathVariable BigInteger id) {
        Post post = postService.getPostById(id);
        long likesCount = postService.countLikesForPost(id.longValue());
        PostResponse response = new PostResponse(post, likesCount);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves recent posts with pagination.
     * @param page The page number for pagination.
     * @return A ResponseEntity containing a list of PostResponse objects.
     */
    @GetMapping()
    public ResponseEntity<List<PostResponse>> GetRecentPosts(@RequestParam int page) {
        List<Post> posts = postService.getRecentPosts(page);
        List<PostResponse> responses = posts.stream()
                .map(post -> new PostResponse(post, postService.countLikesForPost(post.getPostId().longValue())))
                .toList();
        return ResponseEntity.ok(responses);
    }

    /**
     * Retrieves media associated with a specific post.
     * @param postId The ID of the post whose media is to be retrieved.
     * @return A ResponseEntity containing a list of Media objects associated with the post.
     */
    @GetMapping("/media/{postId}")
    public ResponseEntity<List<Media>> getMediaByPostId(@PathVariable BigInteger postId) {
        List<Media> mediaList = mediaService.getMediaByPostId(postId);
        return ResponseEntity.ok(mediaList);
    }

    /**
     * Deletes a post by its ID.
     * @param id The ID of the post to be deleted.
     * @return A ResponseEntity containing a boolean indicating the success of the deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> DeletePostById(@PathVariable BigInteger id) {
        boolean deleted = postService.deletePostById(id);
        return ResponseEntity.ok(deleted);
    }

    /**
     * Updates an existing post.
     * @param postCreateRequest The request object containing updated post details.
     * @param id The ID of the post to be updated.
     * @return A ResponseEntity containing the updated PostResponse object with post details and like count.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<PostResponse> UpdatePostById(@RequestBody PostCreateRequest postCreateRequest,
                                                       @PathVariable BigInteger id) {
        Post post = postService.postDtoToPost(postCreateRequest);
        post.setPostId(id); // Set the ID of the post to be updated
        post = postService.updatePost(post);
        long likesCount = postService.countLikesForPost(id.longValue());
        PostResponse response = new PostResponse(post, likesCount);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a media object associated with a specific post.
     * @param id The ID of the post whose media is to be retrieved.
     * @return A ResponseEntity containing the Media object associated with the post.
     */
    @GetMapping("/{id}/media")
    public ResponseEntity<Media> GetMediaByPostId(@PathVariable BigInteger id) {
        Media media = (Media) mediaService.getMediaByPostId(id);
        return ResponseEntity.ok(media);
    }
}
