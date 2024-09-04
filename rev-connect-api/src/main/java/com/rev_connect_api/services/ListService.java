package com.rev_connect_api.services;



import com.rev_connect_api.exceptions.ResourceNotFoundException;
import com.rev_connect_api.models.Lists;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.models.User;
import com.rev_connect_api.repositories.ListRepository;
import com.rev_connect_api.repositories.PostRepository;
import com.rev_connect_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ListService {

    @Autowired
    private ListRepository listRepository;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public Lists createList(Lists list) {
        // Fetch the user to ensure it exists
        User user = userRepository.findById(list.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + list.getUser().getUserId()));
        list.setUser(user);
        return listRepository.save(list);
    }

    public Lists addPostToList(Long listId, Long postId) {
        Lists list = listRepository.findById(listId)
                .orElseThrow(() -> new ResourceNotFoundException("List not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        list.getPosts().add(post);
        return listRepository.save(list);
    }

    public Lists removePostFromList(Long listId, Long postId) {
        Lists list = listRepository.findById(listId)
                .orElseThrow(() -> new ResourceNotFoundException("List not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        list.getPosts().remove(post);
        return listRepository.save(list);
    }

    public void deleteList(Long listId) {
        listRepository.deleteById(listId);
    }
    public List<Lists> getListsByUserId(Long userId) {
        return listRepository.findByUserUserId(userId); // Custom query method
    }
}
