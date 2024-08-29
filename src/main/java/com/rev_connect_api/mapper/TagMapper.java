package com.rev_connect_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.rev_connect_api.dto.TagDTO;

import com.rev_connect_api.models.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDTO toTagDTO(Tag tag);

    @Mapping(target = "posts", ignore = true)
    Tag toTagEntity(TagDTO tagDTO);
}
