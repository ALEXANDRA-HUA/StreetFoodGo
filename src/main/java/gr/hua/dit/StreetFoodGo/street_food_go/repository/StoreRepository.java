package gr.hua.dit.StreetFoodGo.street_food_go.repository;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByOpenTrue();
}
