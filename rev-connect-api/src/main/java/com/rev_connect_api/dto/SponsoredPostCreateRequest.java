package com.rev_connect_api.dto;
import jakarta.validation.constraints.NotEmpty;

public class SponsoredPostCreateRequest {
    @NotEmpty(message = "title is blank")
    private String title;
    @NotEmpty(message = "content is blank")
    private String content;
    @NotEmpty(message = "sponsor is blank")
    private String sponsor;

    public SponsoredPostCreateRequest() {}

    public SponsoredPostCreateRequest(String title, String content, String sponsor) {
        this.title = title;
        this.content = content;
        this.sponsor = sponsor;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSponsor() {
        return sponsor;
    }
}
