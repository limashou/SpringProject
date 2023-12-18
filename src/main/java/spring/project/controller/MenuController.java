package spring.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.project.entity.Coffeeshop;
import spring.project.entity.CoffeeshopMenu;
import spring.project.service.CoffeeshopService;
import spring.project.service.MenuService;

import java.util.List;

@Controller
public class MenuController {
    private final CoffeeshopService coffeeshopService;
    private final MenuService menuService;

    public MenuController(CoffeeshopService coffeeshopService, MenuService menuService) {
        this.coffeeshopService = coffeeshopService;
        this.menuService = menuService;
    }
    @GetMapping("/Menu")
    public String listMenu(@RequestParam("coffeeshopId") long coffeeshopId, Model model){
        Coffeeshop selectedCoffeeshop = coffeeshopService.getCoffeeShopById(coffeeshopId);
        model.addAttribute("selectedCoffeeshop", selectedCoffeeshop);
        List<CoffeeshopMenu> menu = menuService.getMenuByCoffeeshop(selectedCoffeeshop);
        model.addAttribute("menu",menu);
        return "menu";
    }
}
