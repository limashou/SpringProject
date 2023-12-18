package spring.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.project.entity.Coffeeshop;

import java.util.List;
@Repository
public interface CoffeeshopRepository extends JpaRepository<Coffeeshop,Long> {
    List<Coffeeshop> findByNameContaining(String searchQuery);
    List<Coffeeshop> findByOrderByRateDesc();
}
