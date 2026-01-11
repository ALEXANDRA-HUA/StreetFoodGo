package gr.hua.dit.StreetFoodGo.street_food_go.repository;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
