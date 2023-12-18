package spring.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "coffeshop_menu")
public class CoffeeshopMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false,length = 20)
    private String items;
    @Column(nullable = false)
    private double cost;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "coffeeshop_id")
    private Coffeeshop coffeeshop;

    public CoffeeshopMenu(String items, double cost) {
        this.items = items;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "CoffeeshopMenu{" +
                "id=" + id +
                ", items='" + items + '\'' +
                ", cost=" + cost +
                ", coffeeshop=" + coffeeshop.getId() +
                '}';
    }
}
