package spring.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.project.entity.Coffeeshop;

import spring.project.entity.OrderCoffeeshop;
import org.springframework.data.jpa.repository.Query;
import spring.project.entity.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderCoffeeshop,Long> {
    List<OrderCoffeeshop> findByCoffeeshop(Coffeeshop coffeeshop);
    @Query("SELECT oc FROM OrderCoffeeshop oc JOIN FETCH oc.items WHERE oc.coffeeshop = :coffeeshop AND oc.status = 'ready' OR oc.status = 'canceled' AND oc.user = :user ORDER BY oc.order_date DESC")
    List<OrderCoffeeshop> findWithItemsByCoffeeshopCustomerReady(@Param("coffeeshop") Coffeeshop coffeeshop, @Param("user") User user);
    @Query("SELECT oc FROM OrderCoffeeshop oc JOIN FETCH oc.items WHERE oc.coffeeshop = :coffeeshop AND oc.status = 'not_ready' AND oc.user = :user ORDER BY oc.order_date DESC")
    List<OrderCoffeeshop> findWithItemsByCoffeeshopCustomerNotReady(@Param("coffeeshop") Coffeeshop coffeeshop, @Param("user") User user);
    @Query("SELECT oc FROM OrderCoffeeshop oc JOIN FETCH oc.items WHERE oc.coffeeshop = :coffeeshop ORDER BY oc.order_date DESC")
    List<OrderCoffeeshop> findWithItemsByCoffeeshopCustomer(@Param("coffeeshop") Coffeeshop coffeeshop);
}
