package com.rev_connect_api.mapper;

import org.mapstruct.Mapper;

import com.rev_connect_api.dto.TagDTO;

import com.rev_connect_api.models.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDTO toTagDTO(Tag tag);

    Tag toTagEntity(TagDTO tagDTO);
}
