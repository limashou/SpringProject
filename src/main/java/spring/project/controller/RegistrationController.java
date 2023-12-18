package spring.project.controller;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.project.entity.User;
import spring.project.service.EmailService;
import spring.project.service.UserService;

import java.io.IOException;

@Controller
public class RegistrationController {
    private final UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public RegistrationController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            @RequestParam("check") String check
            ) throws IOException {
        if (userService.doesUserExist(username) == true) {
            return "redirect:/registration?exist";
        }
        else if (password.equals(check)) {
            User user = new User(username, passwordEncoder.encode(password), "USER", email);
            userService.updateAddUser(user);
            return "login";
        } else {
            return "registration";
        }

    }

    @GetMapping("/dontRemember")
    public String dontRemember() { return "dontRemember"; }

    @PostMapping("/dontRemember")
    public String sendEmail(
            @RequestParam("username") String username
    ) throws IOException, MessagingException {
        User user = userService.getUserByUsername(username);
        emailService.sendPassword(user.getEmail());
        return "dontRemember";
    }
}
