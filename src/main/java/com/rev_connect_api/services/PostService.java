package com.rev_connect_api.services;

import com.rev_connect_api.models.Post;
import com.rev_connect_api.repositories.PostRepository;
import com.rev_connect_api.utils.TimestampUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PostService {

    private static final int MAX_POST_PER_PAGE = 5;

    private final PostRepository postRepository;
    private final MediaService mediaService;
    
    public PostService(PostRepository postRepository, MediaService mediaService) {
        this.postRepository = postRepository;
        this.mediaService = mediaService;
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new  ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
    }

    public List<Post> getPostsByAuthorId(Long authorId) {
        return postRepository.findByAuthorId(authorId);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post savePost(Post post) {
        return postRepository.saveAndFlush(post);
    }

    public Post updatePost(Long id, Post postDetails) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
        
        if (postDetails.getTitle() != null) post.setTitle(postDetails.getTitle());
        if (postDetails.getContent() != null) post.setContent(postDetails.getContent());

        return postRepository.saveAndFlush(post);
    }

    public boolean deletePost(Long id) {
        if(!postRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        postRepository.deleteById(id);
        return true;
    }

    public List<Post> getRecentPosts(int page) {
        Pageable pageable = PageRequest.of(page, MAX_POST_PER_PAGE);
        return postRepository.findAllByOrderByCreatedAtDesc(pageable);
    } 
    
    @Transactional
    public Post savePost(Post post, MultipartFile file) {
        Post response = postRepository.save(post);
        mediaService.saveMedia(file, response.getPostId(), response.getCreatedAt());
        return response;
    }

    @Transactional
    public boolean deletePostById(Long id) {
        if(!postRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        mediaService.deleteMediaByPostId(id);
        postRepository.deleteById(id);
        return true;
    }
}
