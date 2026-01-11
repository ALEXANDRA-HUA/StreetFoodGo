package gr.hua.dit.StreetFoodGo.street_food_go.repository;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Βρες παραγγελίες ενός συγκεκριμένου χρήστη (για τον πελάτη)
    List<Order> findByUser_UsernameOrderByCreatedDateDesc(String username);

    // Βρες παραγγελίες για τα μαγαζιά που ανήκουν σε συγκεκριμένο ιδιοκτήτη
    List<Order> findByStore_Owner_UsernameOrderByCreatedDateDesc(String ownerUsername);
}