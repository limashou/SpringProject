package spring.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.project.entity.Coffeeshop;
import spring.project.entity.OrderCoffeeshop;
import spring.project.entity.User;
import spring.project.repository.OrderRepository;


import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderCoffeeshop> getAllList() {
        return orderRepository.findAll();
    }

    public List<OrderCoffeeshop> getOrdersByCustomer(Coffeeshop coffeeshop) {
        return orderRepository.findByCoffeeshop(coffeeshop);
    }
    public void updateAddOrder(OrderCoffeeshop order) { orderRepository.save(order); }

    @Transactional
    public List<OrderCoffeeshop> getOrdersByCustomerWithItems(Coffeeshop coffeeshop) {
        return orderRepository.findWithItemsByCoffeeshopCustomer(coffeeshop);
    }
    @Transactional
    public List<OrderCoffeeshop> getOrdersByCustomerWithItemsReady(Coffeeshop coffeeshop, User user){
        return orderRepository.findWithItemsByCoffeeshopCustomerReady(coffeeshop,user);
    }
    @Transactional
    public List<OrderCoffeeshop> getOrdersByCustomerWithItemsNotReady(Coffeeshop coffeeshop,User user){
        return orderRepository.findWithItemsByCoffeeshopCustomerNotReady(coffeeshop,user);
    }
    public OrderCoffeeshop cancelOrder(Long id, String newStatus,String newStatusPayment) {
        OrderCoffeeshop order = orderRepository.findById(id).orElse(null);
        order.setStatus(newStatus);
        order.setPayment_status(newStatusPayment);
        return orderRepository.save(order);
    }
//    public OrderCoffeeshop refunded(Long id, String newStatus) {
//        OrderCoffeeshop order = orderRepository.findById(id).orElse(null);
//        order.setPayment_status(newStatus);
//        return orderRepository.save(order);
//    }
    public void deleteOrderCoffeeshopById(Long id) {
        orderRepository.deleteById(id);
    }

    public OrderCoffeeshop getOrderCoffeeshopById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }
}
