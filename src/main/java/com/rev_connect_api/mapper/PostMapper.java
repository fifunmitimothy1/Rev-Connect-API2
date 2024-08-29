package com.rev_connect_api.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.rev_connect_api.dto.PostRequestDTO;
import com.rev_connect_api.dto.PostResponseDTO;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.models.Tag;
import com.rev_connect_api.services.TagService;

@Mapper(componentModel = "spring")
public interface PostMapper {

    public static final TagService tagService = null;

    @Mapping(target = "postId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "tagNames",  expression =  "java(mapTagNamesToTags(postRequestDTO.getTagName()))")
    // @Mapping(target = "taggedUserIds", source = "taggedUserIds", qualifiedByName = "mapUsersIdsToUsers")
    public abstract Post toPostEntity(PostRequestDTO postRequestDTO);

    // @Mapping(target = "tagNames", source = "tagNames", qualifiedByName = "mapTagsToTagNames")
    // @Mapping(target = "taggedUserIds", source = "taggedUserIds", qualifiedByName = "mapUsersToUserIds")
    // public abstract PostResponseDTO toPostResponseDTO(Post post);

     default Set<Tag> mapTagNamesToTags(Set<String> tagNames) {
        return tagNames.stream()
            .map(tagService::findOrCreateByName)
            .collect(Collectors.toSet());
    }

    
}
