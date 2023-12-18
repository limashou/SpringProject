package spring.project.controller.coffeeshop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.project.entity.Coffeeshop;
import spring.project.service.CoffeeshopService;

import java.io.IOException;

@Controller
public class CoffeeshopEditController {
    private final CoffeeshopService coffeeshopService;

    public CoffeeshopEditController(CoffeeshopService coffeeshopService) {
        this.coffeeshopService = coffeeshopService;
    }

    @GetMapping("/EditCoffeeshop")
    public String showEditCoffeeshopForm(@RequestParam("coffeeshopId") long coffeeshopId, Model model) {
        Coffeeshop selectedCoffeeshop = coffeeshopService.getCoffeeShopById(coffeeshopId);
        model.addAttribute("selectedCoffeeshop", selectedCoffeeshop);
        return "coffeeshop/editCoffeeshop";
    }

    @PostMapping("/EditCoffeeshop")
    public String editCoffeeshop(
            @RequestParam("coffeeshopId") long coffeeshopId,
            @RequestParam("name") String name,
            @RequestParam("address") String address,
            @RequestParam("rate") int rate,
            @RequestParam("working_hours") int workingHours,
            @RequestParam("email") String email
    ) throws IOException {
        Coffeeshop existingCoffeeshop = coffeeshopService.getCoffeeShopById(coffeeshopId);
        if (existingCoffeeshop != null) {
            existingCoffeeshop.setName(name);
            existingCoffeeshop.setAddress(address);
            existingCoffeeshop.setRate(rate);
            existingCoffeeshop.setWorking_hours(workingHours);
            existingCoffeeshop.setEmail(email);
            coffeeshopService.updateAddCoffeeShop(existingCoffeeshop);
        }
        return "redirect:/coffeeshop";
    }
}
