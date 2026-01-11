package gr.hua.dit.StreetFoodGo.street_food_go.service;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.OrderStatus;

import java.util.Map;
import java.util.Set;

public final class OrderStatusFlow {

    private static final Map<OrderStatus, Set<OrderStatus>> ALLOWED = Map.of(
            OrderStatus.NEW, Set.of(OrderStatus.ACCEPTED, OrderStatus.REJECTED),
            OrderStatus.ACCEPTED, Set.of(OrderStatus.PREPARING),
            OrderStatus.PREPARING, Set.of(OrderStatus.OUT_FOR_DELIVERY, OrderStatus.COMPLETED),
            OrderStatus.OUT_FOR_DELIVERY, Set.of(OrderStatus.COMPLETED),
            OrderStatus.REJECTED, Set.of(),
            OrderStatus.COMPLETED, Set.of()
    );

    private OrderStatusFlow() {}

    public static boolean canTransition(OrderStatus from, OrderStatus to) {
        return ALLOWED.getOrDefault(from, Set.of()).contains(to);
    }
}
