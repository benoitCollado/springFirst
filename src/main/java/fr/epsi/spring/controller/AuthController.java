package fr.epsi.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import fr.epsi.spring.model.User;
import fr.epsi.spring.service.UserService;
import java.util.List;
import fr.epsi.spring.util.JWTUtil;
import fr.epsi.spring.service.AuthService;
import java.util.Optional;



@Controller
@RequestMapping("/auth")
public class AuthController {
    
    private UserService userService;
    private AuthService authService;
    private JWTUtil jwtUtil;

    public AuthController(UserService userService, AuthService authService, JWTUtil jwtUtil){
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/register")
    public String showRegister(Model model){
        //User user = authService.register(username, password, "USER");
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password,Model model) {
        try{
            model.addAttribute("user", new User());
            authService.register(username, password, "USER");
            return "login";
        }catch(Exception e){
            model.addAttribute("error", "une ereur est survenue lors de l'inscription veuillez réessayer ou nous contacter");
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLogin(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model, HttpServletResponse response){
        try{
            Optional<User> userTry = userService.findByUserName(username);
            if(userTry.isPresent()){
                User user = userTry.get();
                if(authService.checkPassword(password,user.getPassword())){
                    String token = jwtUtil.generateToken(user.getPassword(), user.getRole());
                    Cookie cookie = new Cookie("token", token);
                    //cookie.setHttpOnly(true);
                    //cookie.setSecure(true);     
                    cookie.setPath("/");        
                    cookie.setMaxAge(24 * 60 * 60);

                    response.addCookie(cookie);
                }else {
                    throw new Exception("erreur");
                }
            }else{
                throw new Exception("erreur");
            }
            return "redirect:/home";
        }catch(Exception e){
            model.addAttribute("user", new User());
            model.addAttribute("error", "impossible de vous coonecter");
            return "login";
        }
    }
    

}
