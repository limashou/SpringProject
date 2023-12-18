package spring.project.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.project.entity.Coffeeshop;
import spring.project.entity.CoffeeshopMenu;
import spring.project.entity.OrderCoffeeshop;
import spring.project.entity.User;
import spring.project.service.CoffeeshopService;
import spring.project.service.MenuService;
import spring.project.service.OrderService;
import spring.project.service.UserService;

import java.time.LocalDateTime;

import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

@Controller
public class OrderAddController {
    private final MenuService menuService;
    private final CoffeeshopService coffeeshopService;
    private final OrderService orderService;

    private final UserService userService;

    public OrderAddController(UserService userService,MenuService menuService, CoffeeshopService coffeeshopService, OrderService orderService) {
        this.userService = userService;
        this.menuService = menuService;
        this.coffeeshopService = coffeeshopService;
        this.orderService = orderService;
    }


    @PostMapping("/AddOrder")
    public String addOrderCoffeeshop(
            @RequestParam("coffeeshopId") Long coffeeshopId,
            @RequestParam("selectedItems") List<Long> selectedItems,
            @RequestParam("quantity") List<Integer> quantity,
            @RequestParam("status") String status,
            @RequestParam("paymentMethod") boolean paymentMethod,
            @RequestParam("paymentStatus") String paymentStatus,
            @RequestParam("authorities") long username

    ) {
        Coffeeshop coffeeshop = coffeeshopService.getCoffeeShopById(coffeeshopId);
        User user = userService.getUserById(username);
        List<CoffeeshopMenu> selectedMenuItems = new LinkedList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            Long menuId = selectedItems.get(i);
            int itemQuantity = quantity.get(i);
            for (int j = 1; j <= itemQuantity; j++) {
                CoffeeshopMenu menu = menuService.getMenuItemById(menuId);
                selectedMenuItems.add(menu);
            }
        }
        double totalAmount = menuService.calculateTotalAmount(selectedMenuItems);
        OrderCoffeeshop order = new OrderCoffeeshop(user,coffeeshop,
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusMinutes(10),
                selectedMenuItems, totalAmount, status, paymentMethod,paymentStatus);
        orderService.updateAddOrder(order);

        return "redirect:/Order?coffeeshopId=" + coffeeshopId + "&authorities=" + username;
    }

    @GetMapping("/AddOrder")
    public String addOrderCoffeeshopForm(@RequestParam("coffeeshopId") Long coffeeshopId,  Model model, @RequestParam("authorities") long username
    ) {
        Coffeeshop selectedCoffeeshop = coffeeshopService.getCoffeeShopById(coffeeshopId);
        List<CoffeeshopMenu> menu = menuService.getMenuByCoffeeshop(selectedCoffeeshop);
        User user = userService.getUserById(username);
        model.addAttribute("menuList", menu);
        model.addAttribute("authorities",user);
        model.addAttribute("selectedCoffeeshop", selectedCoffeeshop);
        return "order/addOrder";
    }
}
