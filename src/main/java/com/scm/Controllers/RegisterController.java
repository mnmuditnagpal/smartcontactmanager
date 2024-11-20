package com.scm.Controllers;

import com.scm.Dtos.VerificationResponseDto;
import com.scm.Forms.LoginForm;
import com.scm.Helper.Status;
import com.scm.Services.UserServiceImplementation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.Helper.MessageType;
import com.scm.Helper.MessageContent;
import com.scm.Forms.UserForm;
import com.scm.Models.User;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class RegisterController {

    private final UserServiceImplementation userServiceImplementation;


    public RegisterController(UserServiceImplementation userServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
    }

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //Sign Up Page
    @RequestMapping("/register")
    public String registerUser(Model model){
        model.addAttribute("userForm",new UserForm());
        return "register";
    }

    //Registering User
    @RequestMapping(value = "/register-user", method = RequestMethod.POST)
    public String registeringUser(@Valid @ModelAttribute("userForm") UserForm userForm,BindingResult bindingResult,Model model){
        logger.info(userForm.toString());

        if(bindingResult.hasErrors()){
            return "register";
        }

       User registerUser = new User();
       registerUser.setName(userForm.getName());
       registerUser.setEmail(userForm.getEmail());
       registerUser.setPassword(
                    userForm.getPassword()
       );
       registerUser.setPhoneNumber(userForm.getPhoneNumber());
       registerUser.setTerms(userForm.isTerms());

       Status savedUserStatus = userServiceImplementation.registerUser(registerUser);

       MessageContent messageContent = new MessageContent();

       if(savedUserStatus == Status.FAILURE){
        messageContent.setMessage("Username already exists!!");
        messageContent.setMessageType(MessageType.red);

        model.addAttribute("messageContent", messageContent);

        return "register";

       }

        return "redirect:/register-success";
    }

    @RequestMapping(value="/register-success")
    public String registerSuccess(){
        return "registerSuccess";
    }

    @RequestMapping(value = "/auth/verifyaccount")
    public String verifyActivationLink(
            @RequestParam("token") String token,
            Model model
    ){
        VerificationResponseDto verificationResponseDto = userServiceImplementation.verifyUserEmail(token);

        model.addAttribute(verificationResponseDto.getMessageContent());
        model.addAttribute("loginForm", new LoginForm());

        return "login";
    }

}
