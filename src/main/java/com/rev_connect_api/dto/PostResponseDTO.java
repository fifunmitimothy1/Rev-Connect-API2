package com.rev_connect_api.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class PostResponseDTO {

    private Long postId;
    private Long authorId;
    private String title;
    private String content; 
    private String isPinned;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
    private LocalDateTime isPinnedAt;

    private Set<String> tagNames;
    private Set<String> taggedUsernames;
}
