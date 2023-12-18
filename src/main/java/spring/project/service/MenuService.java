package spring.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.project.entity.Coffeeshop;
import spring.project.entity.CoffeeshopMenu;
import spring.project.repository.MenuRepository;

import java.util.List;

@Service
public class MenuService {
    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<CoffeeshopMenu> getAllList() {
        return menuRepository.findAll();
    }

    public CoffeeshopMenu getMenuItemById(Long menuItemId) {
        return menuRepository.findById(menuItemId).orElse(null);
    }

    public double calculateTotalAmount(List<CoffeeshopMenu> selectedMenuItems) {
        double totalAmount = 0;
        for (CoffeeshopMenu menuItem : selectedMenuItems) {
            totalAmount += menuItem.getCost();
        }
        return totalAmount;
    }

    public List<CoffeeshopMenu> getMenuByCoffeeshop(Coffeeshop coffeeshop) {
        return menuRepository.findByCoffeeshop(coffeeshop);
    }
}
