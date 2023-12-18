package spring.project.controller.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.project.entity.Coffeeshop;
import spring.project.entity.OrderCoffeeshop;
import spring.project.entity.User;
import spring.project.service.CoffeeshopService;
import spring.project.service.MenuService;
import spring.project.service.OrderService;
import spring.project.service.UserService;

import java.io.IOException;
import java.util.List;

@Controller
public class OrderController {
    private  final CoffeeshopService coffeeshopService;
    private final OrderService orderService;
    private final MenuService menuService;
    private final UserService userService;

    public OrderController(UserService userService,
            CoffeeshopService coffeeshopService,
            OrderService orderService,
            MenuService menuService) {
        this.userService = userService;
        this.coffeeshopService = coffeeshopService;
        this.orderService = orderService;
        this.menuService = menuService;
    }

    @GetMapping("/Order")
    public String showOrders(
            @RequestParam("coffeeshopId") long coffeeshopId,
            Model model,
            @RequestParam("authorities") long username
    ) {
        User user = userService.getUserById(username);
        String authorities = user.getRole();
        Coffeeshop selectedCoffeeshop = coffeeshopService.getCoffeeShopById(coffeeshopId);
        if (authorities.equals("ADMIN")){
            List<OrderCoffeeshop> orders = orderService.getOrdersByCustomerWithItems(selectedCoffeeshop);
            model.addAttribute("selectedCoffeeshop", selectedCoffeeshop);
            model.addAttribute("orders", orders);
            return "order/orderAdmin";
        }else {
            List<OrderCoffeeshop> orders = orderService.getOrdersByCustomerWithItemsNotReady(selectedCoffeeshop,user);
            model.addAttribute("selectedCoffeeshop", selectedCoffeeshop);
            model.addAttribute("authorities",username);
            model.addAttribute("orders", orders);
            return "order/orders";
        }
    }

    @GetMapping("/ShowHistory")
    public String myHistoryOrder(@RequestParam("coffeeshopId") long coffeeshopId,Model model,@RequestParam("authorities") long username)throws IOException {
        Coffeeshop selectedCoffeeshop = coffeeshopService.getCoffeeShopById(coffeeshopId);
        User user = userService.getUserById(username);
        List<OrderCoffeeshop> orders = orderService.getOrdersByCustomerWithItemsReady(selectedCoffeeshop,user);
        model.addAttribute("selectedCoffeeshop", selectedCoffeeshop);
        model.addAttribute("authorities", user);
        model.addAttribute("orders", orders);
        return "order/history";
    }
    @PostMapping("/DeleteOrder")
    public String deleteOrder(@RequestParam("coffeeshopId") Long coffeeshopId,
                              @RequestParam("orderId") Long orderId,
                              @RequestParam("paymentStatus") String status,
                              @RequestParam("authorities") long username
    ){
        if(status.equals("Paid")){
            orderService.cancelOrder(orderId,"canceled","refunded");
        }else {
            orderService.cancelOrder(orderId, "canceled",status);
        }
        return "redirect:/Order?coffeeshopId=" + coffeeshopId + "&authorities=" + username;
    }
}

