package com.rev_connect_api.dto;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.Data;

@Data
public class PostResponseDTO {

    private Long postId;
    private Long authorId;
    private String title;
    private String content; 
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Set<String> tagNames;
    private Set<String> taggedUsernames;
}
