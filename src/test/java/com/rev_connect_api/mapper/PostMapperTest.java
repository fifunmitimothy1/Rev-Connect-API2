package com.rev_connect_api.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rev_connect_api.dto.PostRequestDTO;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.models.Tag;
import com.rev_connect_api.models.User;

@SpringBootTest
public class PostMapperTest {

    @Autowired
    private PostMapper postMapper;

    @Test
    void testPostMapper() {
        // Create a PostRequestDTO with tags and tagged users
        PostRequestDTO dto = new PostRequestDTO();
        dto.setAuthorId(1L);
        dto.setTitle("Test Title");
        dto.setContent("Test Content");
        dto.setTagNames(Set.of("Tag1", "Tag2"));
        dto.setTaggedUserIds(Set.of(2L, 3L));

        // Map the DTO to an entity
        Post post = postMapper.toPostEntity(dto);

        // Assertions
        assertNotNull(post, "Post should not be null after mapping");
        assertEquals("Test Title", post.getTitle(), "Title should match");
        assertEquals("Test Content", post.getContent(), "Content should match");

        // Tags assertions
        assertNotNull(post.getTags(), "Tags should not be null");
        assertEquals(2, post.getTags().size(), "There should be 2 tags");
        assertTrue(post.getTags().stream().map(Tag::getTagName).anyMatch(tag -> tag.equals("Tag1")), "Tag1 should be present");
        assertTrue(post.getTags().stream().map(Tag::getTagName).anyMatch(tag -> tag.equals("Tag2")), "Tag2 should be present");

        // Tagged users assertions
        assertNotNull(post.getTaggedUsers(), "Tagged users should not be null");
        assertEquals(2, post.getTaggedUsers().size(), "There should be 2 tagged users");
        assertTrue(post.getTaggedUsers().stream().map(User::getUserId).anyMatch(id -> id.equals(2L)), "User with ID 2 should be tagged");
        assertTrue(post.getTaggedUsers().stream().map(User::getUserId).anyMatch(id -> id.equals(3L)), "User with ID 3 should be tagged");
    }
}