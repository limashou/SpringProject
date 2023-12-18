package spring.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "coffeeshop")
public class Coffeeshop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "coffeshop_name",nullable = false,length = 40)
    private String name;
    @Column(nullable = false,length = 50)
    private String address;
    @Column(nullable = false)
    private int  rate;
    @Column(name = "working_hours",nullable = false)
    @ColumnDefault(value = "1")
    private int working_hours;
    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "coffeeshop", cascade = CascadeType.ALL)
    private List<OrderCoffeeshop> coffeeshopCustomers = new ArrayList<>();


    public Coffeeshop(String name, String address, int rate, int working_hours, String email) {
        this.name = name;
        this.address = address;
        this.rate = rate;
        this.working_hours = working_hours;
        this.email = email;
        coffeeshopCustomers = new ArrayList<>();
    }

    @Override
    public String toString() {
        return id + "\t" + name + "\t" +address + "\t" + rate + "\t" + working_hours + "\t" + email;
    }
    public void addCustomer(OrderCoffeeshop coffeeshopCustomer){
        coffeeshopCustomers.add(coffeeshopCustomer);
        coffeeshopCustomer.setCoffeeshop(this);
    }
    public String toStringForHTMLtable() {
        final StringBuilder sb = new StringBuilder("");
        sb.append("<tr>");
        sb.append("<td>").append(name).append("</td>");
        sb.append("<td>").append(getAddress()).append("</td>");
        sb.append("<td>").append(getRate()).append("</td>");
        sb.append("<td>").append(toStringWorking_hours(getWorking_hours())).append("</td>");
        sb.append("<td>").append(getEmail()).append("</td>");
        sb.append("</tr>");
        return sb.toString();
    }

    public String toStringWorking_hours(int working_hours){
        String hours = "";
        switch (working_hours){
            case 1:
                hours = "08:00-22:00";
                break;
            case 2:
                hours = "10:00-24:00";
                break;
            case 3:
                hours = "00:00-23:59";
                break;
        }
            return hours;
    }
}
