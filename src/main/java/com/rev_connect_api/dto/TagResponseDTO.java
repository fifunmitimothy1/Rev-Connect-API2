package com.rev_connect_api.dto;

import java.util.Set;

import lombok.Data;

@Data
public class TagResponseDTO {

    private Long tagId;
    private String tagName;
    private Set<Long> postIds;
}
