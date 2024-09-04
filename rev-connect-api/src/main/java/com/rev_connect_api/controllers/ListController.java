package com.rev_connect_api.controllers;

import com.rev_connect_api.models.Lists;
import com.rev_connect_api.services.ListService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/lists")
public class ListController {

    @Autowired
    private ListService listService;

    private static final Logger logger = LoggerFactory.getLogger(ListController.class);
    @PostMapping
    public ResponseEntity<Lists> createList(@RequestBody Lists list) {
        // Log the incoming request
        logger.info("Received request to create list: {}", list);

        // Log details about the user object
        if (list.getUser() == null) {
            logger.error("User is null in the request");
        } else {
            logger.info("User in request: userId = {}", list.getUser().getUserId());
        }

        // Ensure that the user is not null and the userId is present
        if (list.getUser() == null || list.getUser().getUserId() == null) {
            logger.error("Bad request: user or userId is missing");
            return ResponseEntity.badRequest().build(); // Handle missing user ID with 400 response
        }

        try {
            Lists createdList = listService.createList(list);
            return ResponseEntity.ok(createdList);
        } catch (Exception e) {
            logger.error("Error creating list: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{listId}/posts/{postId}")
    public ResponseEntity<Lists> addPostToList(@PathVariable Long listId, @PathVariable Long postId) {
        return ResponseEntity.ok(listService.addPostToList(listId, postId));
    }

    @DeleteMapping("/{listId}/posts/{postId}")
    public ResponseEntity<Lists> removePostFromList(@PathVariable Long listId, @PathVariable Long postId) {
        return ResponseEntity.ok(listService.removePostFromList(listId, postId));
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<Void> deleteList(@PathVariable Long listId) {
        listService.deleteList(listId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Lists>> getAllListsByUserId(@PathVariable Long userId) {
        List<Lists> lists = listService.getListsByUserId(userId);
        return new ResponseEntity<>(lists, HttpStatus.OK);
    }
}
