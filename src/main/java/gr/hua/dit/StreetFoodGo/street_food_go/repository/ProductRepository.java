package gr.hua.dit.StreetFoodGo.street_food_go.repository;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStoreId(Long storeId); // Φέρνει το μενού συγκεκριμένου καταστήματος
}
