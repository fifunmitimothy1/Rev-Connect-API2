package com.rev_connect_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PostCreateRequest {

    @NotEmpty(message = "title is blank")
    private String title;
    @NotEmpty(message = "content is blank")
    private String content;

    public PostCreateRequest() {}

    public PostCreateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
