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
public class OrderEditController {
    private final MenuService menuService;
    private final CoffeeshopService coffeeshopService;
    private final OrderService orderService;
    private final UserService userService;
    public OrderEditController(MenuService menuService, CoffeeshopService coffeeshopService, OrderService orderService,UserService userService) {
        this.menuService = menuService;
        this.coffeeshopService = coffeeshopService;
        this.orderService = orderService;
        this.userService = userService;
    }
    @PostMapping("/EditOrder")
    public String editOrderCoffeeshop(
            @RequestParam("coffeeshopId") Long coffeeshopId,
            @RequestParam("orderId") Long orderId,
            @RequestParam("selectedItems") List<Long> selectedItems,
            @RequestParam("quantity") List<Integer> quantity,
            @RequestParam("status") String status,
            @RequestParam("payment_method") boolean paymentMethod,
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

        OrderCoffeeshop existingOrder = orderService.getOrderCoffeeshopById(orderId);
        if (existingOrder != null) {
            existingOrder.setUser(user);
            existingOrder.setOrder_date(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            existingOrder.setPossible_ready_time(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusMinutes(10));
            existingOrder.setItems(selectedMenuItems);
            existingOrder.setTotal_amount(totalAmount);
            existingOrder.setStatus(status);
            existingOrder.setPayment_method(paymentMethod);
            existingOrder.setPayment_status(paymentStatus);
            orderService.updateAddOrder(existingOrder);
        }

        return "redirect:/Order?coffeeshopId=" + coffeeshopId + "&authorities=" + username;
    }
    @GetMapping("/EditOrder")
    public String editOrderCoffeeshopForm(@RequestParam("coffeeshopId") Long coffeeshopId,
                                          @RequestParam("orderId") Long orderId,
                                          Model model) {
        Coffeeshop selectedCoffeeshop = coffeeshopService.getCoffeeShopById(coffeeshopId);
        List<CoffeeshopMenu> menu = menuService.getMenuByCoffeeshop(selectedCoffeeshop);
        OrderCoffeeshop orderCoffeeshop = orderService.getOrderCoffeeshopById(orderId);

        model.addAttribute("menuList", menu);
        model.addAttribute("selectedCoffeeshop", selectedCoffeeshop);
        model.addAttribute("order", orderCoffeeshop);

        return "order/editOrder";
    }
}
