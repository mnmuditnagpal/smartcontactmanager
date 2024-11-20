package com.scm.Controllers;


import com.scm.Services.UserServiceImplementation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.Forms.LoginForm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    private UserServiceImplementation userServiceImplementation;

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @RequestMapping(value = "/login-user",method = RequestMethod.POST)
    public String loginUser(@ModelAttribute("loginForm") LoginForm loginForm){
        return "redirect:/home";
    }

}
