package gr.hua.dit.StreetFoodGo.street_food_go.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User customer;

    @NotBlank private String street;
    @NotBlank private String city;
    @NotBlank private String zip;

    private String notes;

    // για μελλοντικό geolocation
    private Double lat;
    private Double lng;
}
