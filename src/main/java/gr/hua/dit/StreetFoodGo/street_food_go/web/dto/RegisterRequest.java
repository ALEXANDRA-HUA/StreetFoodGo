package gr.hua.dit.StreetFoodGo.street_food_go.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Το όνομα χρήστη είναι υποχρεωτικό")
    @Size(min = 3, max = 50, message = "Το όνομα χρήστη πρέπει να είναι από 3 έως 50 χαρακτήρες")
    private String username;

    @NotBlank(message = "Το email είναι υποχρεωτικό")
    @Email(message = "Παρακαλώ εισάγετε έγκυρη διεύθυνση email")
    private String email;

    @NotBlank(message = "Ο κωδικός είναι υποχρεωτικός")
    @Size(min = 4, message = "Ο κωδικός πρέπει να έχει τουλάχιστον 4 χαρακτήρες")
    private String password;

    @NotBlank(message = "Το όνομα είναι υποχρεωτικό")
    private String firstName;

    @NotBlank(message = "Το επώνυμο είναι υποχρεωτικό")
    private String lastName;

    @NotBlank(message = "Ο ρόλος είναι υποχρεωτικός")
    private String role;
}