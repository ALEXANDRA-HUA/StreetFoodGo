package gr.hua.dit.StreetFoodGo.street_food_go.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Το όνομα χρήστη είναι υποχρεωτικό")
    private String username;

    @NotBlank(message = "Ο κωδικός είναι υποχρεωτικός")
    private String password;
}