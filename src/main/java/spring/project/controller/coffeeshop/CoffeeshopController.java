package spring.project.controller.coffeeshop;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.project.entity.Coffeeshop;
import spring.project.entity.User;
import spring.project.service.CoffeeshopService;
import spring.project.service.EmailService;
import spring.project.service.UserService;

import java.io.IOException;
import java.util.List;

@Controller
public class CoffeeshopController {
    @Autowired
    private EmailService emailService;
    private final UserService userService;
    private final CoffeeshopService coffeeshopService;

    public CoffeeshopController(CoffeeshopService coffeeshopService,UserService userService) {
        this.coffeeshopService = coffeeshopService;
        this.userService = userService;
    }

    @GetMapping("/coffeeshop")
    public String listCoffeeshops(@RequestParam(name = "sort", required = false) String sortOrder, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        String  information = ": " + user.getUsername() + " (  " + user.getRole() + " )";
        boolean hasUserAuthority = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));
        try {
            List<Coffeeshop> coffeeshops;
            if ("by_rate".equals(sortOrder)) {
                coffeeshops = coffeeshopService.sortByRate();
            } else {
                coffeeshops = coffeeshopService.getAllList();
            }
            model.addAttribute("information",information);
            model.addAttribute("coffeeshops", coffeeshops);
            model.addAttribute("authorities",user.getId());
            if(hasUserAuthority == true) {
                return "coffeeshop/coffeeshop";
            }else {
                return "coffeeshop/coffeeshopUser";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/coffeeshop")
    public String searchCoffeeshops(@RequestParam(name = "searchQuery") String searchQuery, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
        boolean hasUserAuthority = authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals("ADMIN"));
        if (searchQuery != null && !searchQuery.isEmpty()) {
            List<Coffeeshop> searchResults = coffeeshopService.performSearch(searchQuery);
            if (searchResults != null && !searchResults.isEmpty()) {
                model.addAttribute("coffeeshops", searchResults);
            } else {
                model.addAttribute("noResults", true);
            }
        } else {
            List<Coffeeshop> coffeeshops = coffeeshopService.getAllList();
            model.addAttribute("coffeeshops", coffeeshops);
        }
        if (hasUserAuthority == true){
            return "coffeeshop/coffeeshop";
        }else {
            return "coffeeshop/coffeeshopUser";
        }
    }

    @GetMapping("/registeredUser")
    public String showResteredUser(Model model) {
        List<User> users = userService.getRegisteredUser();
        model.addAttribute("user",users);
        return "coffeeshop/registeredUser";
    }

    @PostMapping("/send")
    public String generateReport(@RequestParam(name = "email") String email) throws IOException, MessagingException {
        List<Coffeeshop> reports = coffeeshopService.getAllList();

        StringBuilder csvString = new StringBuilder();
        csvString.append("id\t").append("Name\t").append("Address\t").append("Rate\t").append("Working Hours\t").
                append("Email\t").append("\n");
        for (Coffeeshop report : reports) {
            csvString.append(report.toString());
            csvString.append("\n");
        }

        emailService.sendEmail(email, "Report", csvString.toString());
        return "redirect:/coffeeshop";
    }

    @PostMapping("/DeleteCoffeeshop")
    public String deleteCoffeeshop(@RequestParam("coffeeshopId") long coffeeshopId) throws IOException {
        coffeeshopService.deleteCoffeeShopById(coffeeshopId);
        return "redirect:/coffeeshop";
    }

}

