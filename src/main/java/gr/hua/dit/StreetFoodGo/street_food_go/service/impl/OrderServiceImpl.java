package gr.hua.dit.StreetFoodGo.street_food_go.service.impl;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.*;
import gr.hua.dit.StreetFoodGo.street_food_go.repository.*;
import gr.hua.dit.StreetFoodGo.street_food_go.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public Order createOrder(Long userId, Long storeId, String type, Long addressId, Map<Long, Integer> items) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        Address address = null;
        if (addressId != null && "DELIVERY".equalsIgnoreCase(type)) {
            address = addressRepository.findById(addressId)
                    .orElseThrow(() -> new RuntimeException("Address not found"));
        }

        Order order = Order.builder()
                .user(user)
                .store(store)
                .deliveryAddress(address)
                .createdDate(LocalDateTime.now())
                .status(OrderStatus.NEW)
                .orderType(type)
                .totalAmount(BigDecimal.ZERO)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Long, Integer> entry : items.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found: " + productId));

            BigDecimal lineTotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));
            total = total.add(lineTotal);

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(quantity)
                    .price(product.getPrice())
                    .build();

            order.addOrderItem(orderItem);
        }

        order.setTotalAmount(total);

        if (total.compareTo(store.getMinOrderAmount()) < 0) {
            throw new RuntimeException("Το ποσό είναι μικρότερο από το ελάχιστο όριο του καταστήματος (" + store.getMinOrderAmount() + "€)");
        }

        // ΣΗΜΑΝΤΙΚΗ ΑΛΛΑΓΗ: Χρήση saveAndFlush αντί για save
        // Αυτό διασφαλίζει ότι το ID δημιουργείται ΑΜΕΣΩΣ
        return orderRepository.saveAndFlush(order);
    }

    @Override
    @Transactional
    public void ownerSetStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        orderRepository.save(order);
    }
}