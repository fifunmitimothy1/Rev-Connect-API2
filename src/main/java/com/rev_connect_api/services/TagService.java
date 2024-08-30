package com.rev_connect_api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.rev_connect_api.dto.TagDTO;
import com.rev_connect_api.mapper.TagMapper;
import com.rev_connect_api.models.Tag;
import com.rev_connect_api.repositories.TagRepository;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper; 

    public TagService(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }
    
    @Transactional
    public Tag findOrCreateByName(String tagName) {
        Optional<Tag> existingTag = tagRepository.findByTagName(tagName);
        Tag newTag = new Tag();
        newTag.setTagName(tagName);
        return existingTag.orElseGet(() -> tagRepository.saveAndFlush(newTag));
    }

    @Transactional
    public List<TagDTO> getAllTags() {
        return tagRepository.findAll().stream()
            .map(tagMapper::toTagDTO)
            .toList();
    }

    @Transactional
    public TagDTO getTagById(Long id) {
        Tag tag = tagRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag with that id not found."));
        return tagMapper.toTagDTO(tag);
    }

    @Transactional
    public void deleteTag(Long id) {
        if(!tagRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag with that id not found.");
        }
        tagRepository.deleteById(id);
    }
}
