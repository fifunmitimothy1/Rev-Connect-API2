package com.rev_connect_api.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @Column(unique = true)
    private String username;

    private String userPwd;
    
    @Column(unique = true)
    private String email;

    private String firstName;

    private String lastName;

    boolean isBusiness;


    public User() {
        
    }

    public User(String username, String userPwd, String email, String firstName, String lastName, boolean isBusiness) {
        this.username = username;
        this.userPwd = userPwd;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isBusiness = isBusiness;
    }


    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public boolean isBusiness() {
        return isBusiness;
    }


    public void setBusiness(boolean isBusiness) {
        this.isBusiness = isBusiness;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.userPwd = passwordEncoder.encode(userPwd);
    }

@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    User other = (User) obj;

    return isBusiness == other.isBusiness &&
           (userId != null ? userId.equals(other.userId) : other.userId == null) &&
           (username != null ? username.equals(other.username) : other.username == null) &&
           (userPwd != null ? userPwd.equals(other.userPwd) : other.userPwd == null) &&
           (email != null ? email.equals(other.email) : other.email == null) &&
           (firstName != null ? firstName.equals(other.firstName) : other.firstName == null) &&
           (lastName != null ? lastName.equals(other.lastName) : other.lastName == null);
}
    
    @Override
    public String toString() {
        return "User [userId=" + userId + ", username=" + username + ", userPwd=" + userPwd + ", email=" + email
                + ", firstName=" + firstName + ", lastName=" + lastName + ", isBusiness=" + isBusiness + "]";
    }

}
