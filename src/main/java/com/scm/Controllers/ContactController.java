package com.scm.Controllers;

import com.scm.Configuration.FetchUserEmail;
import com.scm.Forms.ContactForm;
import com.scm.Helper.MessageContent;
import com.scm.Helper.MessageType;
import com.scm.Models.Contact;
import com.scm.Models.SocialLinks;
import com.scm.Models.SocialMedia;
import com.scm.Models.User;
import com.scm.Services.ContactServiceImplementation;
import com.scm.Services.ImageServiceImplementation;
import com.scm.Services.UserServiceImplementation;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@Controller
@RequestMapping("/user")
public class ContactController {

    private String filename = UUID.randomUUID().toString();

    private final ImageServiceImplementation imageServiceImplementation;

    private final ContactServiceImplementation contactServiceImplementation;

    private final UserServiceImplementation userServiceImplementation;

    @Value("${default.image.url}")
    private String defaultImageUrl;

    public ContactController(ContactServiceImplementation contactServiceImplementation,
                             ImageServiceImplementation imageServiceImplementation,
                             UserServiceImplementation userServiceImplementation
                             ) {
        this.contactServiceImplementation = contactServiceImplementation;
        this.imageServiceImplementation = imageServiceImplementation;
        this.userServiceImplementation = userServiceImplementation;
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/add-contact")
    public String addNewContactForm(Model model){
        ContactForm contactForm = new ContactForm();

        model.addAttribute("contactForm",contactForm);
        return "user/addContact";
    }


    @RequestMapping(value="/new-contact",method = RequestMethod.POST)
    public String addNewContact(@Valid @ModelAttribute("contactForm") ContactForm contactForm,
                                BindingResult bindingResult,
                                Authentication authentication,
                                Model model){

        logger.info("Creating Contact Object");

        if(bindingResult.hasErrors()){
            return "user/addContact";
        }

        Contact contact = new Contact();

        String fileUrl = "";


        if(contactForm.getPicture().getSize()!=0){
            fileUrl = imageServiceImplementation.uploadImage(contactForm.getPicture(),filename);
        }

//      Uploading Image From FrontEnd


        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setAddress(contactForm.getAddress());
        contact.setPhoneNumber(contactForm.getPhoneNumber());

        List<SocialLinks> socialLinks = new ArrayList<>();

        SocialLinks socialLinksInstagram = new SocialLinks();
        socialLinksInstagram.setSocialLink(contactForm.getSocialLinkInstagram());
        socialLinksInstagram.setSocialMediaTitle(SocialMedia.INSTAGRAM);
        socialLinksInstagram.setContact(contact);

        SocialLinks socialLinksFacebook = new SocialLinks();
        socialLinksFacebook.setSocialLink(contactForm.getSocialLinkFacebook());
        socialLinksFacebook.setSocialMediaTitle(SocialMedia.FACEBOOK);
        socialLinksFacebook.setContact(contact);

        socialLinks.add(socialLinksInstagram);
        socialLinks.add(socialLinksFacebook);

        contact.setSocialLinks(socialLinks);

        contact.setFavourite(contactForm.isFavourite());

        if(fileUrl.isEmpty()){
            contact.setPicture(defaultImageUrl);
        }else{
            contact.setPicture(fileUrl);
        }


        contact.setImagePublicId(filename);

        logger.info("Saving Contact Object");

        Contact savedContact = contactServiceImplementation.saveContact(contact,authentication);

        logger.info("Getting Saved Contact Object");

        if(savedContact != null){
            MessageContent messageContent = new MessageContent();
            messageContent.setMessage("Contact Added Successfully");
            messageContent.setMessageType(MessageType.green);
            model.addAttribute("messageContent",messageContent);
            model.addAttribute("contactForm",new ContactForm());
        }else{
            MessageContent messageContent = new MessageContent();
            messageContent.setMessage("Something went wrong");
            messageContent.setMessageType(MessageType.red);
            model.addAttribute("messageContent",messageContent);
            model.addAttribute("contactForm",new ContactForm());
        }

        logger.info(contactForm.toString());
        return "user/addContact";
    }

    @RequestMapping("/all-contacts")
    public String getAllContacts(
            @RequestParam(value="page",defaultValue = "0") Integer page,
            @RequestParam(value="size",defaultValue = "4") Integer size,
            @RequestParam(value="sortBy",defaultValue = "name") String sortBy,
            @RequestParam(value="direction",defaultValue = "asc") String direction,
            Model model,
            Authentication authentication,
            HttpSession session){

//        String email = FetchUserEmail.getLoggedInUserEmail(authentication);
//        String email = session.get

//        User user = userServiceImplementation.getUserByEmail(email);
        User user = (User) session.getAttribute("loggedInUser");

        Page<Contact> contacts = contactServiceImplementation.getContactsByUserId(user.getId(),page,size,sortBy,direction);

        model.addAttribute("contacts", contacts);


        return "user/allcontacts";
    }

    @PostMapping("/mark-fav/{id}")
    public String markFavContact(
            @PathVariable("id") Long id,
            @RequestParam("pageno") String pageno
    ){

        contactServiceImplementation.toggleFav(id);
        return "redirect:/user/all-contacts?page="+pageno;
    }

    @GetMapping("/contact-profile/{id}")
    public String getContactProfile(
            @PathVariable("id") Long id,

            Model model
    ){

        Contact userContact = contactServiceImplementation.getContactById(id);
        model.addAttribute("userContact",userContact);
        model.addAttribute("instagram",userContact.getSocialLinks().getFirst());
        model.addAttribute("facebook",userContact.getSocialLinks().getLast());
        return "user/contactProfile";
    }

    @DeleteMapping("/contact-delete/{id}")
    public ResponseEntity<HttpStatus> deleteContact(
            @PathVariable("id") Long id
    ){
        return contactServiceImplementation.deleteContact(id);

    }

    @GetMapping("/getFavourites")
    public String getFavContacts(
            @RequestParam(value = "page",defaultValue = "0") Integer page,
            @RequestParam(value = "size",defaultValue = "4") Integer size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            HttpSession session,
            Model model
    ){
        User user = (User) session.getAttribute("loggedInUser");

        Page<Contact> contacts = contactServiceImplementation.getContactsByUserId(user.getId(),page,size,sortBy,direction);

        model.addAttribute("contacts", contacts);


        return "user/favcontacts";
    }
}
