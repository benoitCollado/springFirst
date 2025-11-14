package fr.epsi.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.epsi.spring.model.User;
//import fr.epsi.spring.model.User;
import fr.epsi.spring.service.UserService;
import java.util.List;

import fr.epsi.spring.security.annotation.*;




@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }


    @GetMapping
    @Authenticated
    public String showLoginPage(Model model) {
        model.addAttribute("users", userService.findAll());
        List<User> listeUser = userService.findAll();
        for(User user: listeUser){
            logger.info("l'id: {}", user.getId());
            logger.info("le nom: {}", user.getUsername());
        }
        return "user-list";
    }


    @GetMapping("/create")
    @Authenticated
    @HasRole("USER")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    @PostMapping("/save")
    @Authenticated
    @HasRole("USER")
    public String saveEmployee(@ModelAttribute User user){
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    @Authenticated
    @HasRole("USER")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        User user = userService.findById(id).orElseThrow();
        model.addAttribute("user", user);
        return "user-form";
    }

    @GetMapping("/delete/{id}")
    @Authenticated
    @HasRole("USER")
    public String deleteUser(@PathVariable("id") Long id){
        userService.deleteById(id);
        return "redirect:/users";
    }
    
    
}
