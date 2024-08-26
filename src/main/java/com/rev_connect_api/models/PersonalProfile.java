package com.rev_connect_api.models;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "personal_profiles")
public class PersonalProfile {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String bio = "";

    public PersonalProfile() {
        
    }

    public PersonalProfile(User user, String bio){
        this.user = user;
        this.bio = bio;
    }

    public Long getId(){
        return this.id;
    }

    public String getBio(){
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public User getUser(){
        return this.user;
    }

    @Override
    public String toString() {
        return "{ User: " + user + ", bio: " + bio + " }";
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || o.getClass() != this.getClass()) {
            return false;
        }

        PersonalProfile otherProfile = (PersonalProfile) o;
        return Objects.equals(this.id, otherProfile.getId()) && Objects.equals(this.bio, otherProfile.getBio()) && Objects.equals(this.user, otherProfile.getUser());
    }
}
