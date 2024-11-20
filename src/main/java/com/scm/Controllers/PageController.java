package com.scm.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class PageController {
    
    @RequestMapping({"/home","","/"})
    public String home(Model model){
        model.addAttribute("name", "Welcome to my Homepage !! Mudit");
        return "home";
    }
    
    @RequestMapping("/about")
    public String aboutPage(){
        return "about";
    }

    @RequestMapping("/services")
    public String servicesPage(){
        return "services";
    }
}
