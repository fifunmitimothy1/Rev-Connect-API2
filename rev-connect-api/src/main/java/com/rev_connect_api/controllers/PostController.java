package com.rev_connect_api.controllers;

import com.rev_connect_api.dto.PostCreateRequest;
import com.rev_connect_api.dto.PostResponse;
import com.rev_connect_api.models.Media;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.services.MediaService;
import com.rev_connect_api.services.PostService;
import com.rev_connect_api.util.TimestampUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private final TimestampUtil timestampUtil;
    private final PostService postService;
    private final MediaService mediaService;

    public PostController(TimestampUtil timestampUtil, PostService postService, MediaService mediaService) {
        this.timestampUtil = timestampUtil;
        this.postService = postService;
        this.mediaService = mediaService;
    }

    // Could not get ModelAttribute working, so I used this solution which is not the best
    @PostMapping
    public ResponseEntity<Post> CreatePost(@RequestParam("title") String title,
                                           @RequestParam("content") String content,
                                           @RequestParam(value = "file", required = false) MultipartFile file) {
        Post post = postService.postDtoToPost(new PostCreateRequest(title, content));
        post.setUserId(new BigInteger("1"));
        post.setCreatedAt(timestampUtil.getCurrentTimestamp());
        Post response;
        if(file != null) {
            response = postService.savePost(post, file);
        } else {
            response = postService.savePost(post);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> GetPostById(@PathVariable BigInteger id) {
        Post post = postService.getPostById(id);
        long likesCount = postService.countLikesForPost(id.longValue());
        PostResponse response = new PostResponse(post, likesCount);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<PostResponse>> GetRecentPosts(@RequestParam int page) {
        List<Post> posts = postService.getRecentPosts(page);
        List<PostResponse> responses = posts.stream()
                .map(post -> new PostResponse(post, postService.countLikesForPost(post.getPostId().longValue())))
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/media/{postId}")
    public ResponseEntity<List<Media>> getMediaByPostId(@PathVariable BigInteger postId) {
    List<Media> mediaList =  mediaService.getMediaByPostId(postId);
    return ResponseEntity.ok(mediaList);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> DeletePostById(@PathVariable BigInteger id) {
        boolean deleted = postService.deletePostById(id);
        return ResponseEntity.ok(deleted);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostResponse> UpdatePostById(@RequestBody PostCreateRequest postCreateRequest,
                                                       @PathVariable BigInteger id) {
        Post post = postService.postDtoToPost(postCreateRequest);
        post.setPostId(id);
        post = postService.updatePost(post);
        long likesCount = postService.countLikesForPost(id.longValue());
        PostResponse response = new PostResponse(post, likesCount);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/media")
    public ResponseEntity<Media> GetMediaByPostId(@PathVariable BigInteger id) {
        Media media = (Media) mediaService.getMediaByPostId(id);
        return ResponseEntity.ok(media);
    }
}