package com.rev_connect_api.mapper;

import com.rev_connect_api.dto.TagDTO;
import com.rev_connect_api.models.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TagMapperTest {

    @Autowired
    private TagMapper tagMapper;

    @Test
    void testToTagEntity() {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setTagId(1L);
        tagDTO.setTagName("SampleTag");

        Tag tag = tagMapper.toTagEntity(tagDTO);
        assertNotNull(tag, "Tag should not be null after mapping");
        assertEquals(1L, tag.getTagId(), "Tag ID should match");
        assertEquals("SampleTag", tag.getTagName(), "Tag name should match");
    }

    @Test
    void testToTagDTO() {
        Tag tag = new Tag();
        tag.setTagId(1L);
        tag.setTagName("SampleTag");

        TagDTO tagDTO = tagMapper.toTagDTO(tag);
        assertNotNull(tagDTO, "TagDTO should not be null after mapping");
        assertEquals(1L, tagDTO.getTagId(), "Tag ID should match");
        assertEquals("SampleTag", tagDTO.getTagName(), "Tag name should match");
    }
}