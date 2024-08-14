package com.rev_connect_api.services;

import com.rev_connect_api.dto.PostCreateRequest;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.repositories.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post savePost(Post post) {
        Post response = postRepository.save(post);
        return post;
    }

    public Post getPostById(int id) {
        Optional<Post> post = postRepository.getPostByPostId(id);
        if(post.isEmpty()) {
            return null;
        }
        return post.get();
    }

    @Transactional
    public String deletePostById(int id) {
        Post post = getPostById(id);
        if(post == null) {
            return "Post of id " + id + " does not exist in database";
        }
        postRepository.deletePostByPostId(id);
        return "Successfully deleted post of id " + id;
    }

    @Transactional
    public Post updatePost(Post post) {
        Post response = getPostById(post.getPostId());
        if(post == null) {
            return null;
        }
        response = savePost(post);
        return response;
    }
}
