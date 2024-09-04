package com.rev_connect_api.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.rev_connect_api.dto.PostRequestDTO;
import com.rev_connect_api.dto.PostResponseDTO;
import com.rev_connect_api.models.Post;
import com.rev_connect_api.models.Tag;
import com.rev_connect_api.models.User;

@Mapper(componentModel = "spring", uses = {TagMapper.class, UserMapper.class})
public interface PostMapper {

    @Mapping(target = "postId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "tags", source = "tagNames", qualifiedByName = "mapTagNamesToTags")
    @Mapping(target = "taggedUsers", source = "taggedUserIds", qualifiedByName = "mapUserIdsToUsers")
    Post toPostEntity(PostRequestDTO postRequestDTO);

    @Mapping(target = "tagNames", source = "tags", qualifiedByName = "mapTagsToTagNames")
    @Mapping(target = "taggedUsernames", source = "taggedUsers", qualifiedByName = "mapUsersToUsernames")
    PostResponseDTO toPostResponseDTO(Post post);

    @Named("mapTagNamesToTags")
    default Set<Tag> mapTagNamesToTags(Set<String> tagNames) {
        return tagNames.stream()
            .map(tagName -> {
                Tag tag = new Tag();
                tag.setTagName(tagName);
                return tag;
            }).collect(Collectors.toSet());
    }

    @Named("mapTagsToTagNames")
    default Set<String> mapTagsToTagNames(Set<Tag> tags) {
        return tags.stream()
            .map(Tag::getTagName)
            .collect(Collectors.toSet());
    }

    @Named("mapUserIdsToUsers")
    default Set<User> mapUserIdsToUsers(Set<Long> userIds) {
        return userIds.stream()
            .map(userId -> {
                User user = new User();
                user.setUserId(userId);
                return user;
            }).collect(Collectors.toSet());
    }

    @Named("mapUsersToUsernames")
    default Set<String> mapUsersToUsernames(Set<User> users) {
        return users.stream()
            .map(User::getUsername)
            .collect(Collectors.toSet());
    }
}