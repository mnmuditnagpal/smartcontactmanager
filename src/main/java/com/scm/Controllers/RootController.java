package com.scm.Controllers;

import com.scm.Configuration.FetchUserEmail;
import com.scm.Models.User;
import com.scm.Services.ContactServiceImplementation;
import com.scm.Services.UserServiceImplementation;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {
    private final UserServiceImplementation userServiceImplementation;

    private String email;
    private User loggedInUser;


    public RootController(UserServiceImplementation userServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
    }



    @ModelAttribute
    public void loggedInUser(Model model, Authentication authentication, HttpSession httpSession){
        if(authentication == null){
            return;
        }

        if(httpSession.getAttribute("loggedInUser")==null){
            email = FetchUserEmail.getLoggedInUserEmail(authentication);
            loggedInUser = userServiceImplementation.getUserByEmail(email);
            httpSession.setAttribute("loggedInUser",loggedInUser);
        }

        loggedInUser = (User) httpSession.getAttribute("loggedInUser");
        email = loggedInUser.getEmail();

//        Integer favCount = contactServiceImplementation.getFavouriteContactsByUserId(getUser.getId())==null ? 0 : contactServiceImplementation.getFavouriteContactsByUserId(getUser.getId()).size();
        model.addAttribute("favCount",loggedInUser==null?0:loggedInUser.getFavCount());
        model.addAttribute("loggedInUser",loggedInUser);
    }
}
