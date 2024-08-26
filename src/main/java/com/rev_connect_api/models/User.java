package com.rev_connect_api.models;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.context.annotation.Profile;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String userEmail;

	@Column(name = "password")
	private String password;

	@Column(name = "is_business")
	private Boolean isBusiness;

	@OneToOne(mappedBy = "user")
    private BusinessProfile businessProfile;




	public User(){}

	public User(String username, String firstName, String lastName,String userEmail,String password,Boolean isBusiness) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userEmail = userEmail;
		this.password = password;
		this.isBusiness = isBusiness;
	}

	public User(long id, String username, String firstName, String lastName,String userEmail,String password,Boolean isBusiness) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userEmail = userEmail;
		this.password = password;
		this.isBusiness = isBusiness;
	}

	public User(Long id, String username, String firstName, String lastName, String userEmail, String password,
			Boolean isBusiness, BusinessProfile businessProfile) {
		this.id = id;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userEmail = userEmail;
		this.password = password;
		this.isBusiness = isBusiness;
		this.businessProfile = businessProfile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Boolean getBusiness() {
		return isBusiness;
	}

	public void setBusiness(Boolean business) {
		isBusiness = business;
	}

	public BusinessProfile getBusinessProfile() {
		return businessProfile;
	}

	public void setProfile(BusinessProfile businessProfile) {
		this.businessProfile = businessProfile;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", userEmail=" + userEmail + ", password=" + password + ", isBusiness=" + isBusiness + "]";
	}

	
}
