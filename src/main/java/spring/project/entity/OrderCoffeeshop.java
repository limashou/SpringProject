package spring.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderCoffeeshop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private LocalDateTime order_date;

    @Column(nullable = false)
    private LocalDateTime possible_ready_time;
    @Transient
    private double total_amount;

    @Column(nullable = false,length = 10)
    private String status;

    @Column(nullable = false)
    @ColumnDefault(value = "1")
    private boolean payment_method;

    @Column
    private String payment_status;

    @ManyToOne
    @JoinColumn(name = "coffeeshop_id")
    private Coffeeshop coffeeshop;

    @ManyToOne
    @JoinColumn(name = "user_id")
     private  User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "items",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    private List<CoffeeshopMenu> items;

    public OrderCoffeeshop(
            User user,
            Coffeeshop coffeeshop,
            LocalDateTime order_date,
            LocalDateTime possible_ready_time,
            List<CoffeeshopMenu> items ,
            double total_amount,
            String status,
            boolean payment_method,
            String payment_status) {
        this.user = user;
        this.coffeeshop = coffeeshop;
        this.possible_ready_time = possible_ready_time;
        this.order_date = order_date;
        this.items = items;
        this.status = status;
        this.payment_method = payment_method;
        this.payment_status = payment_status;
        getTotal_amount();
    }

    public String getOrder_date() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
        return order_date.format(formatter);
    }
    public String getPossible_ready_time() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
        return possible_ready_time.format(formatter);
    }
    public String toStringSelectedItems() {
        StringBuilder itemsString = new StringBuilder();
        for (CoffeeshopMenu item : items) {
            if (itemsString.length() > 0) {
                itemsString.append("\n");
            }
            itemsString.append(item.getItems());
        }
        return itemsString.toString();
    }

    public double getTotal_amount() {
        double total = 0.0;
        for (CoffeeshopMenu item : items) {
            total += item.getCost();
        }
        this.total_amount = total;
        return total_amount;
    }
    public String getStatus() {
        String statusStr = "";
        switch (status){
            case "ready":
                statusStr = "Ready";
                break;
            case "not_ready":
                statusStr = "Not ready";
                break;
            case "canceled":
                statusStr = "Canceled";
                break;
        }
        return statusStr;
    }
    @Override
    public String toString() {
        return "OrderCoffeeshop{" +
                "id=" + id +
                ", order_date='" + order_date + '\'' +
                ", total_amount=" + getTotal_amount() +
                ", status=" + status +
                ", payment_method=" + payment_method +
//                ", coffeeshopCustomer=" + coffeeshopCustomer +
                ", items=" + toStringSelectedItems() +
                '}';
    }

    public String getPayment_status() {
        String statusStr = "";
        switch (payment_status){
            case "paid":
                statusStr = "Paid";
                break;
            case "unpaid":
                statusStr = "Unpaid";
                break;
            case "refunded":
                statusStr = "Refunded";
                break;
        }
        return statusStr;
    }
}
