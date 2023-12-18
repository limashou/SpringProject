package spring.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.project.entity.Coffeeshop;
import spring.project.repository.CoffeeshopRepository;

import java.util.List;

@Service
public class CoffeeshopService {
    private final CoffeeshopRepository coffeeshopRepository;

    @Autowired
    public CoffeeshopService(CoffeeshopRepository coffeeshopRepository) {
        this.coffeeshopRepository = coffeeshopRepository;
    }

    public List<Coffeeshop> getAllList() {
        return coffeeshopRepository.findAll();
    }

    public void deleteCoffeeShopById(Long id) {
        coffeeshopRepository.deleteById(id);
    }

    public Coffeeshop getCoffeeShopById(Long id) {
        return coffeeshopRepository.findById(id).orElse(null);
    }

    public List<Coffeeshop> sortByRate() {
        return coffeeshopRepository.findByOrderByRateDesc();
    }
    public void updateAddCoffeeShop(Coffeeshop coffeeshop) {
        coffeeshopRepository.save(coffeeshop);
    }

    public List<Coffeeshop> performSearch(String searchQuery) {
        return coffeeshopRepository.findByNameContaining(searchQuery);
    }
}

