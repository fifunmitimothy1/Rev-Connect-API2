package com.rev_connect_api.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "is_business", nullable = false)
    private Boolean isBusiness;

    @Column(name = "account_type", nullable = false)
    private String accountType;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", updatable = true, nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "requester")
    @JsonIgnore
    private List<ConnectionRequest> sentRequests;

    @OneToMany(mappedBy = "recipient")
    @JsonIgnore
    private List<ConnectionRequest> receivedRequests;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

      public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Boolean getIsBusiness() {
        return isBusiness;
    }

    public void setIsBusiness(Boolean isBusiness) {
        this.isBusiness = isBusiness;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<ConnectionRequest> getSentRequests() {
        return sentRequests;
    }

    public void setSentRequests(List<ConnectionRequest> sentRequests) {
        this.sentRequests = sentRequests;
    }

    public List<ConnectionRequest> getReceivedRequests() {
        return receivedRequests;
    }

    public void setReceivedRequests(List<ConnectionRequest> receivedRequests) {
        this.receivedRequests = receivedRequests;
    }

    @JsonIgnore
    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {
        return roles.stream()
            .map((role) -> new SimpleGrantedAuthority(role.name()))
            .collect(Collectors.toSet());
    }


    public User(String username, String password, String email, String firstName, String lastName, boolean isBusiness) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isBusiness = isBusiness;
    }

    @Override
	public boolean equals(Object obj) {
        if(this == obj) return true;
        if( obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(userId, user.userId) && 
            Objects.equals(username, user.username) && Objects.equals(email, user.email);
    }

	@Override
    public int hashCode() {
        return Objects.hash(userId, username, email);
    }

    /**
     * Overriding the default toString() method allows for easy debugging.
     * @return a String representation of this class.
     */
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isBusiness=" + isBusiness +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", roles=" + roles +
                '}';
    }
}
