package com.scm.Forms;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserForm {
    @NotBlank(message = "Name is required!!")
    private String name;

    @NotBlank(message = "Email is required!!")
    @Email(message = "Invalid Email Address!!")
    private String email;

    @Size(min=7, message = "Password cannot be blank!! Min 7 chars are required!!")
    private String password;

    @Size(min=10,max = 10,message = "Invalid Phone Number!!")
    private String phoneNumber;

    @AssertTrue(message = "Please accept the terms & conditions!!")
    private boolean terms;

    public @NotBlank(message = "Name is required!!") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is required!!") String name) {
        this.name = name;
    }

    public @Email(message = "Invalid Email Address!!") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Invalid Email Address!!") String email) {
        this.email = email;
    }

    public @Size(min = 7, message = "Password cannot be blank!! Min 7 chars are required!!") String getPassword() {
        return password;
    }

    public void setPassword(@Size(min = 7, message = "Password cannot be blank!! Min 7 chars are required!!") String password) {
        this.password = password;
    }

    public @Size(min = 10, max = 10, message = "Invalid Phone Number!!") String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Size(min = 10, max = 10, message = "Invalid Phone Number!!") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @AssertTrue(message = "Please accept the terms & conditions!!")
    public boolean isTerms() {
        return terms;
    }

    public void setTerms(@AssertTrue(message = "Please accept the terms & conditions!!") boolean terms) {
        this.terms = terms;
    }
}
