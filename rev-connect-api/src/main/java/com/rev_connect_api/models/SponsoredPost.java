package com.rev_connect_api.models;
import java.util.*;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class SponsoredPost extends Post{
    private String sponsor;
    private List<String> tags;

    public SponsoredPost() {
        sponsor = "";
        tags = new ArrayList<>();
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    public String getSponsor() {
        return sponsor;
    }

    public List<String> getTags() {
        return new ArrayList<>(tags);
    }

    private SponsoredPost(Builder builder) {
        super(builder);
        sponsor = builder.sponsor;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder extends Post.Builder {
        private String sponsor;

        public Builder() {
            System.out.println("spp");
        }

        public Builder sponsor(String val) {
            sponsor = val;
            return this;
        }

        @Override
        public SponsoredPost build() {
            return new SponsoredPost(this);
        }
    }
}