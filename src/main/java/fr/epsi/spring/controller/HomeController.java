package fr.epsi.spring.controller;

import javax.servlet.http.Cookie;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import fr.epsi.spring.security.annotation.*;



@Controller
@RequestMapping("/home")
public class HomeController {
    
    public HomeController(){

    }

    @GetMapping
    @Authenticated
    @HasRole("USER")
    public String getHome(Model model) {
        return "home";
    }

    @GetMapping("resettoken")
    public String getResetToken(Model model, HttpServletResponse response) {
         Cookie cookie = new Cookie("token", "");
                    //cookie.setHttpOnly(true);
                    //cookie.setSecure(true);     
                    cookie.setPath("/");        
                    cookie.setMaxAge(24 * 60 * 60);

                    response.addCookie(cookie);
        return "redirect:/home";
    }
    

    
}
