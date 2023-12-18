//package spring.webappmpl_3;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import spring.webappmpl_3.entity.*;
//import spring.webappmpl_3.repository.*;
//import spring.webappmpl_3.service.*;
//
//import java.util.Collections;
//import java.util.List;
//
//@SpringBootTest
//class WebAppMpl3ApplicationTests {
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private CoffeeshopService coffeeshopService;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private CustomerService customerService;
//
//    @Autowired
//    private MenuService menuService;
//
//    @Autowired
//    private OrderService orderService;
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Test
//    void contextLoads() {
//    }
//
//    @Test
//    void CoffeeshopTest() {
//        System.out.println("===================Show all");
//        List<Coffeeshop> list = coffeeshopService.getAllList();
//        list.stream().forEach(System.out::println);
//        System.out.println("===================Perform search");
//        list = coffeeshopService.performSearch("by");
//        list.stream().forEach(System.out::println);
//        System.out.println("===================Find by id");
//        list = Collections.singletonList(coffeeshopService.getCoffeeShopById(1L));
//        list.stream().forEach(System.out::println);
////        System.out.println("===================Add new");
////        Coffeeshop coffeeshop = new Coffeeshop("test","Address",4,1,"some@email.com");
////        coffeeshopService.addCoffeeShop(coffeeshop);
////        list = coffeeshopService.getAllList();
////        list.stream().forEach(System.out::println);
//    }
////    @Test
////    void CustomerTest(){
////        Coffeeshop coffeeshop = coffeeshopService.getCoffeeShopById(1L);
////        System.out.println("===================Show by id == 1");
////        List<CoffeeshopCustomer> list = customerService.getCustomerByCoffeeshop(coffeeshop);
////        list.stream().forEach(System.out::println);
////    }
//    @Test
//    void MenuTest() {
//        Coffeeshop coffeeshop = coffeeshopService.getCoffeeShopById(1L);
//        System.out.println("===================Show by id == 1");
//        List<CoffeeshopMenu> menu = menuService.getMenuByCoffeeshop(coffeeshop);
//        menu.stream().forEach(System.out::println);
//    }
//    @Test
//    void OrderTest(){
//        CoffeeshopCustomer customer = customerService.getCustomerhopById(1L);
////        List<OrderCoffeeshop> order = orderRepository.findAll();
////        order.stream().forEach(System.out::println);
//        System.out.println("===================Show by id == 1");
//        List<OrderCoffeeshop> order = orderService.getOrdersByCustomerWithItems(customer);
//        order.stream().forEach(System.out::println);
//    }
//    @Test
//    void User(){
////        User user = new User();
////        user.setUsername("johndoe");
////        user.setPassword("mypassword");
////        user.setRole("USER");
////
////        String encodedPassword = passwordEncoder.encode(user.getPassword());
////
////        userService.updateUser(user);
//        User user = userService.getCustomerhopById(1L);
//        System.out.println(user);
//    }
//
//}
