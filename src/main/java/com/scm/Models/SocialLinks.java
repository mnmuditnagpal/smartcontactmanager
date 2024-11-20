package com.scm.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SocialLinks extends BaseModel{
    private String socialLink;
    @Enumerated(value = EnumType.STRING)
    private SocialMedia socialMediaTitle;
    @ManyToOne
    private Contact contact;

    @Override
    public String toString() {
        return "SocialLinks{" +
                "socialLink='" + socialLink + '\'' +
                ", socialMediaTitle=" + socialMediaTitle +
                ", contact=" + contact +
                '}';
    }

    public String getSocialLink() {
        return socialLink;
    }

    public void setSocialLink(String socialLink) {
        this.socialLink = socialLink;
    }

    public SocialMedia getSocialMediaTitle() {
        return socialMediaTitle;
    }

    public void setSocialMediaTitle(SocialMedia socialMediaTitle) {
        this.socialMediaTitle = socialMediaTitle;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
