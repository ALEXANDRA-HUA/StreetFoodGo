package gr.hua.dit.StreetFoodGo.street_food_go.service;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.Order;
import gr.hua.dit.StreetFoodGo.street_food_go.entities.OrderStatus;

import java.util.Map;

public interface OrderService {
    Order createOrder(Long userId, Long storeId, String type, Long addressId, Map<Long, Integer> items);

    // Η μέθοδος που δημιουργεί το πρόβλημα (πρέπει να υπάρχει εδώ)
    void ownerSetStatus(Long orderId, OrderStatus status);
}