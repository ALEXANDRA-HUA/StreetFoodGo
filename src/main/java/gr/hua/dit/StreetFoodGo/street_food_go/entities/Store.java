package gr.hua.dit.StreetFoodGo.street_food_go.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList; // Χρειάζεται για τη λίστα
import java.util.List;      // Χρειάζεται για τη λίστα

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Το όνομα είναι υποχρεωτικό")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Η περιοχή είναι υποχρεωτική")
    @Column(nullable = false)
    private String area;

    @NotBlank(message = "Η διεύθυνση είναι υποχρεωτική")
    @Column(nullable = false)
    private String address;

    @NotBlank(message = "Ο τύπος κουζίνας είναι υποχρεωτικός")
    @Column(nullable = false)
    private String cuisineType;

    @Column(nullable = false)
    private boolean open;

    @PositiveOrZero(message = "Το ποσό πρέπει να είναι μηδέν ή θετικό")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal minOrderAmount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id")
    private User owner;

    // --- ΝΕΟ: Η λίστα με τα προϊόντα που έλειπε ---
    // mappedBy = "store": Σημαίνει ότι στο Product.java υπάρχει πεδίο "private Store store;"
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Product> products = new ArrayList<>();
}