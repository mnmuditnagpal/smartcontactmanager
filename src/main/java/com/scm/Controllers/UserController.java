package com.scm.Controllers;

import com.scm.Services.UserServiceImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserServiceImplementation userServiceImplementation;

    public UserController(UserServiceImplementation userServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
    }

    //dashboard view
    @RequestMapping(value = "/dashboard")
    public String userDashboard(Authentication authentication, Model model){
//
//        model.addAttribute("email",email);

        return "user/dashboard";
    }

    //profile view
    @RequestMapping("/profile")
    public String userProfile(){
        return "user/profile";
    }


    @DeleteMapping("/delete-account")
    public ResponseEntity<HttpStatus> deleteUserAccount(){
       return userServiceImplementation.deleteUser();
    }

}
