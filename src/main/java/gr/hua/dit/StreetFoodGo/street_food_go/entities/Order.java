package gr.hua.dit.StreetFoodGo.street_food_go.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    // ΠΡΟΣΟΧΗ: Αφαιρέσαμε το @NotNull για να επιτρέπεται το PICKUP (χωρίς διεύθυνση)
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address deliveryAddress;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @NotNull
    @Column(nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private String orderType; // PICKUP ή DELIVERY

    // ΣΗΜΑΝΤΙΚΟ: CascadeType.ALL για να αποθηκεύονται αυτόματα και τα προϊόντα
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    // Helper μέθοδος για να προσθέτουμε items και να φτιάχνεται η σχέση αμφίδρομα
    public void addOrderItem(OrderItem item) {
        orderItems.add(item);
        item.setOrder(this);
    }
}