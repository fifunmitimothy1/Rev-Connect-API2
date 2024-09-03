package com.rev_connect_api.dto;

import jakarta.validation.constraints.NotEmpty;

public class PostCreateRequest {

    @NotEmpty(message = "title is blank")
    private String title;
    @NotEmpty(message = "content is blank")
    private String content;
    private Boolean isPinned;

    public PostCreateRequest() {}

    public PostCreateRequest(String title, String content, Boolean isPinned) {
        this.title = title;
        this.content = content;
        this.isPinned = isPinned;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Boolean getIsPinned() {
        return isPinned;
    }
}
