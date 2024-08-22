package com.rev_connect_api.entity;


import jakarta.persistence.*;

@Entity
@Table(name="system_users")
public class User {
    /**
     * An id for this Account. You should use this as the Entity's ID.
     */
    @Column(name="accountId")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer accountId;
    /**
     * A username for this Account (must be unique and not blank)
     */
    private String email;
    private String username;
    /**
     * A password for this account (must be over 4 characters)
     */
    private String password;
    /**
     * A default, no-args constructor, as well as correctly formatted getters and setters, are needed for
     * Jackson Objectmapper to work.
     */
    private String resetToken;
    public User(){
//gggddd
    }
    /**
     * When posting a new Account, the id can be generated by the database. In that case, a constructor without
     * accountId is needed.
     * @param username
     * @param password
     */
    public User(String username, String password){
        this.username = username;
        this.password = password;
        ///
    }
    /**
     * Whem retrieving an Account from the database, all fields will be needed. In that case, a constructor with all
     * fields is needed.
     * @param accountId
     * @param username
     * @param password
     */
    public User(Integer accountId, String username, String password) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @return accountId
     */
    public Integer getAccountId() {
        return accountId;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param accountId
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @return username
     */
    public String getUsername() {
        return username;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @return password
     */
    public String getPassword() {
        return password;
    }
    /**
     * Properly named getters and setters are necessary for Jackson ObjectMapper to work. You may use them as well.
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Get the current password reset token for the user
    public String getResetToken() { return resetToken;}

    // Set a new password reset token for the user
    public void setResetToken(String resetToken) { this.resetToken = resetToken;
    }

    /**
     * Overriding the default equals() method adds functionality to tell when two objects are identical, allowing
     * Assert.assertEquals and List.contains to function.
     * @param 0 the other object.
     * @return true if o is equal to this object.
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
            User other = (User) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}


    /**
     * Overriding the default toString() method allows for easy debugging.
     * @return a String representation of this class.
     */
    @Override
    public String toString() {
        return "User{" +
                "accountId=" + accountId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}