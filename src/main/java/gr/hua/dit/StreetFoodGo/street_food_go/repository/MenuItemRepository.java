package gr.hua.dit.StreetFoodGo.street_food_go.repository;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByStoreId(Long storeId);
}
