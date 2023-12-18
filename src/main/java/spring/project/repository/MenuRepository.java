package spring.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.project.entity.Coffeeshop;
import spring.project.entity.CoffeeshopMenu;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<CoffeeshopMenu,Long> {
    List<CoffeeshopMenu> findByCoffeeshop(Coffeeshop coffeeshop);
}
