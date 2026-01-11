package gr.hua.dit.StreetFoodGo.street_food_go.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000) // Για να χωράει μεγάλες περιγραφές
    private String description;

    private BigDecimal price;

    // ΠΡΟΕΠΙΛΟΓΗ: true (Διαθέσιμο)
    @Builder.Default
    private boolean available = true;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}