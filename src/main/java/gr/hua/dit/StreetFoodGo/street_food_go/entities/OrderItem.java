package gr.hua.dit.StreetFoodGo.street_food_go.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // ΔΙΟΡΘΩΣΗ: Χρήση Product αντί για MenuItem
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    // ΔΙΟΡΘΩΣΗ: Μετονομασία σε price για να ταιριάζει με τον OrderService
    @Column(nullable = false)
    private BigDecimal price;

    // Helper μέθοδος για να παίρνουμε το σύνολο της γραμμής (αν χρειαστεί)
    public BigDecimal getLineTotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}