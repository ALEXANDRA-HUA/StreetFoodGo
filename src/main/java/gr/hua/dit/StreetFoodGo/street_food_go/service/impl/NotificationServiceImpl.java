package gr.hua.dit.StreetFoodGo.street_food_go.service.impl;

// Εισαγωγή του Interface από το πάνω πακέτο (service)
import gr.hua.dit.StreetFoodGo.street_food_go.service.NotificationService;
import org.springframework.stereotype.Service;

/**
 * Υλοποίηση της υπηρεσίας ειδοποιήσεων.
 * Αυτή η κλάση υλοποιεί το interface NotificationService.
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendNotification(String email, String message) {
        // Mock λειτουργία: Εκτύπωση στο console για σκοπούς δοκιμής
        System.out.println("------------------------------------------");
        System.out.println("ΑΠΟΣΤΟΛΗ ΕΙΔΟΠΟΙΗΣΗΣ (Mock)");
        System.out.println("Προς: " + email);
        System.out.println("Περιεχόμενο: " + message);
        System.out.println("------------------------------------------");

        // Σημείωση: Σε μια κανονική εφαρμογή, εδώ θα γινόταν χρήση
        // της βιβλιοθήκης JavaMailSender ή κάποιου εξωτερικού API (π.χ. SendGrid).
    }
}

