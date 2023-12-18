package spring.project.controller.coffeeshop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.project.entity.Coffeeshop;
import spring.project.service.CoffeeshopService;

import java.io.IOException;

@Controller
public class CoffeeshopAddController {
    private final CoffeeshopService coffeeshopService;
    public CoffeeshopAddController(CoffeeshopService coffeeshopService) {
        this.coffeeshopService = coffeeshopService;
    }

    @GetMapping("/AddCoffeeshop")
    public String showAddCoffeeshopForm() {
        return "coffeeshop/addCoffeeshop";
    }

    @PostMapping("/AddCoffeeshop")
    public String addCoffeeshop(
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("rate") int rate,
            @RequestParam("working_hours") int workingHours,
            @RequestParam("email") String email
    ) throws IOException {
        Coffeeshop coffeeshop = new Coffeeshop(name, address, rate, workingHours, email);
        coffeeshopService.updateAddCoffeeShop(coffeeshop);
        return "redirect:/coffeeshop";
    }
}
