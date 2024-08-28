package com.rev_connect_api.models;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.*;

@Entity
@Table(name = "businessprofile")
public class BusinessProfile {
    
    @Column(name = "profile_id")
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "bio_text", columnDefinition = "VARCHAR(MAX)")
    private String bioText;

    @Column(name = "theme", columnDefinition = "VARCHAR(255)")
    private String theme;

    @Column(name = "pfp_url", columnDefinition = "VARCHAR(255)")
    @URL(message = "Invalid URL format for Profile Picture")
    private String profilePictureURL;

    @Column(name = "banner_url", columnDefinition = "VARCHAR(255)")
    @URL(message = "Invalid URL format for Banner")
    private String bannerURL;


    /**
     * Basic No Args Constructor
     */
    public BusinessProfile() {
    }

    /**
     * Basic No Id Constructor
     */
    public BusinessProfile(User user, String bioText, String theme, String profilePictureURL, String bannerURL) {
        this.user = user;
        this.bioText = bioText;
        this.theme = theme;
        this.profilePictureURL = profilePictureURL;
        this.bannerURL = bannerURL;
    }


    // Generated Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBioText() {
        return bioText;
    }

    public void setBioText(String bioText) {
        this.bioText = bioText;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public String getBannerURL() {
        return bannerURL;
    }

    public void setBannerURL(String bannerURL) {
        this.bannerURL = bannerURL;
    }

    // Generated
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        result = prime * result + ((bioText == null) ? 0 : bioText.hashCode());
        result = prime * result + ((theme == null) ? 0 : theme.hashCode());
        result = prime * result + ((profilePictureURL == null) ? 0 : profilePictureURL.hashCode());
        result = prime * result + ((bannerURL == null) ? 0 : bannerURL.hashCode());
        return result;
    }

    // Generated
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BusinessProfile other = (BusinessProfile) obj;
        if (id != other.id)
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        if (bioText == null) {
            if (other.bioText != null)
                return false;
        } else if (!bioText.equals(other.bioText))
            return false;
        if (theme == null) {
            if (other.theme != null)
                return false;
        } else if (!theme.equals(other.theme))
            return false;
        if (profilePictureURL == null) {
            if (other.profilePictureURL != null)
                return false;
        } else if (!profilePictureURL.equals(other.profilePictureURL))
            return false;
        if (bannerURL == null) {
            if (other.bannerURL != null)
                return false;
        } else if (!bannerURL.equals(other.bannerURL))
            return false;
        return true;
    }
    
    // Generated
    @Override
    public String toString() {
        return "BusinessProfile [id=" + id + ", user=" + user + ", bioText=" + bioText + ", theme=" + theme
                + ", profilePictureURL=" + profilePictureURL + ", bannerURL=" + bannerURL + "]";
    }
   

}
