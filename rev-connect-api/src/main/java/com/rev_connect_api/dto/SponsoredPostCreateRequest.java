package com.rev_connect_api.dto;
import jakarta.validation.constraints.NotEmpty;

public class SponsoredPostCreateRequest extends PostCreateRequest{

    @NotEmpty(message = "sponsor is blank")
    private String sponsor;

    public SponsoredPostCreateRequest() {}

    public SponsoredPostCreateRequest(String title, String content, String sponsor) {
        super(title, content);
        this.sponsor = sponsor;
    }

    public String getSponsor() {
        return sponsor;
    }
}
