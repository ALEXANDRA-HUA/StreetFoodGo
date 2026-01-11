package gr.hua.dit.StreetFoodGo.street_food_go.controller;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.Order;
import gr.hua.dit.StreetFoodGo.street_food_go.entities.User;
import gr.hua.dit.StreetFoodGo.street_food_go.service.OrderService;
import gr.hua.dit.StreetFoodGo.street_food_go.service.UserService;
import gr.hua.dit.StreetFoodGo.street_food_go.web.dto.OrderRequestDto; // Βεβαιώσου ότι έφτιαξες το αρχείο στο βήμα 1
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDto request, Principal principal) {
        // 1. Έλεγχος Authentication
        if (principal == null) {
            return ResponseEntity.status(401).body("Πρέπει να συνδεθείτε για να κάνετε παραγγελία.");
        }

        try {
            // 2. Εύρεση χρήστη (Η μέθοδος findByUsername πρέπει να υπάρχει στο UserService)
            User user = userService.findByUsername(principal.getName());

            // 3. Έλεγχος αν το καλάθι είναι άδειο
            if (request.getItems() == null || request.getItems().isEmpty()) {
                return ResponseEntity.badRequest().body("Το καλάθι είναι άδειο.");
            }

            // 4. Κλήση του Service για δημιουργία παραγγελίας
            Order order = orderService.createOrder(
                    user.getId(),
                    request.getStoreId(),
                    request.getOrderType(),
                    request.getAddressId(),
                    request.getItems()
            );

            // 5. Επιστροφή επιτυχίας
            return ResponseEntity.ok(order);

        } catch (RuntimeException e) {
            // Επιστροφή μηνύματος λάθους (π.χ. "Το κατάστημα είναι κλειστό")
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Γενικό σφάλμα
            return ResponseEntity.status(500).body("Προέκυψε σφάλμα διακομιστή: " + e.getMessage());
        }
    }
}