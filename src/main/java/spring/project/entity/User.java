package spring.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false,length = 25)
    private String username;
    private String password;
    @Column(nullable = false,length = 10)
    private String role;
    @Column
    private String email;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<OrderCoffeeshop> orderCoffeeshops = new ArrayList<>();

    public User(String username, String password ,String role,String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public boolean isEnabled() {
        return true;
    }

    public void addUser(OrderCoffeeshop orderCoffeeshop){
        orderCoffeeshops.add(orderCoffeeshop);
        orderCoffeeshop.setUser(this);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
