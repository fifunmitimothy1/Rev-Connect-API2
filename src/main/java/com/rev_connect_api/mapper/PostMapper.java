package com.rev_connect_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.rev_connect_api.dto.PostRequestDTO;
import com.rev_connect_api.dto.PostResponseDTO;
import com.rev_connect_api.models.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "postId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Post toPostEntity(PostRequestDTO postRequestDTO);

    PostResponseDTO toPostResponseDTO(Post post);
}
