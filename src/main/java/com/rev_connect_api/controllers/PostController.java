package com.rev_connect_api.controllers;

import com.rev_connect_api.models.Media;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.services.MediaService;
import com.rev_connect_api.services.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final MediaService mediaService;

    public PostController(PostService postService, MediaService mediaService) {
        this.postService = postService;
        this.mediaService = mediaService;
    }

    @PostMapping()
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
       Post createdPost = postService.savePost(post);
       return new ResponseEntity<>(createdPost, HttpStatus.CREATED); 
    }

    // Could not get ModelAttribute working, so I used this solution which is not the best
    // @PostMapping()
    // public ResponseEntity<Post> CreatePost(@RequestParam("title") String title,
    //                                        @RequestParam("content") String content,
    //                                        @RequestParam(value = "file", required = false) MultipartFile file) {
    //     SecurityContext context = SecurityContextHolder.getContext();
    //     Authentication auth = context.getAuthentication();
    //     Principal principal = (Principal) auth.getPrincipal();

    //     Post post = postService.postDtoToPost(new PostCreateRequest(title, content));
    //     post.setUserId(new BigInteger(principal.getUserId().toString()));
    //     post.setCreatedAt(timestampUtil.getCurrentTimestamp());

    //     Post response;
    //     if(file != null) {
    //         response = postService.savePost(post, file);
    //     } else {
    //         response = postService.savePost(post);
    //     }
    //     return ResponseEntity.ok(response);
    // }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Post>> getAllPosts() {
       List<Post> posts = postService.getAllPosts();
       return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Post>> getRecentPosts(@RequestParam int page) {
        List<Post> posts = postService.getRecentPosts(page);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Post>> getPostsByAuthorId(@PathVariable Long authorId) {
    List<Post> posts = postService.getPostsByAuthorId(authorId);
    return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    
    @GetMapping("/media/{postId}")
    public ResponseEntity<List<Media>> getMediaByPostId(@PathVariable Long postId) {
        List<Media> mediaList =  mediaService.getMediaByPostId(postId);
        return new ResponseEntity<>(mediaList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@RequestBody @Valid Post postDetails, @PathVariable Long id) {
        Post updatedPost = postService.updatePost(id, postDetails);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePostById(@PathVariable Long id) {
        boolean deleted = postService.deletePostById(id);
        return new ResponseEntity<>(deleted, HttpStatus.NO_CONTENT);
    }
}