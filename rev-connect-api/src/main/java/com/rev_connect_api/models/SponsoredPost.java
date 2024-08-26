package com.rev_connect_api.models;
import java.util.*;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class SponsoredPost extends Post{
    private String sponsorName;
    private List<String> tags;

    public SponsoredPost() {
        sponsorName = "";
        tags = new ArrayList<>();
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public List<String> getTags() {
        return new ArrayList<>(tags);
    }
}
