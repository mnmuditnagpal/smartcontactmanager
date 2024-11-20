package com.scm.Forms;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class ContactForm {

    @NotBlank(message = "Name is Required")
    private String name;

    @Email(message = "Invalid Email Address")
    private String email;

    @Size(min=9,max=10,message="Invalid Phone Number")
    private String phoneNumber;

    @NotBlank(message="Address is Required")
    private String address;
    private MultipartFile picture;
    private boolean favourite;
    private String socialLinkInstagram;
    private String socialLinkFacebook;

    @Override
    public String toString() {
        return "ContactForm{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", picture=" + picture +
                ", favourite=" + favourite +
                ", socialLinkInstagram='" + socialLinkInstagram + '\'' +
                ", socialLinkFacebook='" + socialLinkFacebook + '\'' +
                '}';
    }

    public String getSocialLinkFacebook() {
        return socialLinkFacebook;
    }

    public void setSocialLinkFacebook(String socialLinkFacebook) {
        this.socialLinkFacebook = socialLinkFacebook;
    }

    public @NotBlank(message = "Name is Required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is Required") String name) {
        this.name = name;
    }

    public @Email(message = "Invalid Email Address") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Invalid Email Address") String email) {
        this.email = email;
    }

    public @Size(min = 9, max = 10, message = "Invalid Phone Number") String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Size(min = 9, max = 10, message = "Invalid Phone Number") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @NotBlank(message = "Address is Required") String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank(message = "Address is Required") String address) {
        this.address = address;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getSocialLinkInstagram() {
        return socialLinkInstagram;
    }

    public void setSocialLinkInstagram(String socialLinkInstagram) {
        this.socialLinkInstagram = socialLinkInstagram;
    }
}
