package com.rev_connect_api.services;

import com.rev_connect_api.dto.TagDTO;
import com.rev_connect_api.mapper.TagMapper;
import com.rev_connect_api.models.Tag;
import com.rev_connect_api.repositories.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private TagService tagService;

    private Tag tag;
    private TagDTO tagDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tag = new Tag();
        tag.setTagId(1L);
        tag.setTagName("SampleTag");

        tagDTO = new TagDTO();
        tagDTO.setTagId(1L);
        tagDTO.setTagName("SampleTag");
    }

    @Test
    void testFindOrCreateByName_TagExists() {
        when(tagRepository.findByTagName("SampleTag")).thenReturn(Optional.of(tag));

        Tag result = tagService.findOrCreateByName("SampleTag");

        assertNotNull(result);
        assertEquals("SampleTag", result.getTagName());
        verify(tagRepository, never()).saveAndFlush(any(Tag.class));
    }

    @Test
    void testFindOrCreateByName_TagDoesNotExist() {
        when(tagRepository.findByTagName("SampleTag")).thenReturn(Optional.empty());
        when(tagRepository.saveAndFlush(any(Tag.class))).thenReturn(tag);

        Tag result = tagService.findOrCreateByName("SampleTag");

        assertNotNull(result);
        assertEquals("SampleTag", result.getTagName());
        verify(tagRepository).saveAndFlush(any(Tag.class));
    }

    @Test
    void testGetAllTags() {
        when(tagRepository.findAll()).thenReturn(List.of(tag));
        when(tagMapper.toTagDTO(tag)).thenReturn(tagDTO);

        List<TagDTO> result = tagService.getAllTags();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("SampleTag", result.get(0).getTagName());
    }

    @Test
    void testGetTagById_TagExists() {
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));
        when(tagMapper.toTagDTO(tag)).thenReturn(tagDTO);

        TagDTO result = tagService.getTagById(1L);

        assertNotNull(result);
        assertEquals("SampleTag", result.getTagName());
    }

    @Test
    void testGetTagById_TagNotFound() {
        when(tagRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> tagService.getTagById(1L));
    }

    @Test
    void testDeleteTag_TagExists() {
        when(tagRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> tagService.deleteTag(1L));
        verify(tagRepository).deleteById(1L);
    }

    @Test
    void testDeleteTag_TagNotFound() {
        when(tagRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> tagService.deleteTag(1L));
        verify(tagRepository, never()).deleteById(1L);
    }
}