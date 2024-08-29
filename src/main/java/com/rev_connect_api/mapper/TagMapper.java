package com.rev_connect_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.rev_connect_api.dto.TagResponseDTO;
import com.rev_connect_api.models.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mapping(target = "postIds", source = "postIds")
    TagResponseDTO toTagResponseDTO(Tag tag);
}
