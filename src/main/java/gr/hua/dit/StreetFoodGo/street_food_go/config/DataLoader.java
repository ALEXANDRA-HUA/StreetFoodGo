package gr.hua.dit.StreetFoodGo.street_food_go.config;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.*;
import gr.hua.dit.StreetFoodGo.street_food_go.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Αν υπάρχουν ήδη χρήστες, δεν ξανατρέχουμε τα data
        if (userRepository.findByUsername("user").isPresent()) {
            return;
        }

        // --- 1. Δημιουργία Χρηστών ---

        // Owner 1 (Ιδιοκτήτης)
        User owner1 = new User();
        owner1.setUsername("owner1");
        owner1.setEmail("owner1@test.com");
        owner1.setFirstName("Γιάννης");
        owner1.setLastName("Παπαδόπουλος");
        owner1.setPasswordHash(passwordEncoder.encode("123456"));
        owner1.setRole(Role.OWNER);
        userRepository.save(owner1);

        // Simple User (Πελάτης)
        User user = new User();
        user.setUsername("user");
        user.setEmail("user@test.com");
        user.setFirstName("Μαρία");
        user.setLastName("Δημητρίου");
        user.setPasswordHash(passwordEncoder.encode("123456"));
        user.setRole(Role.CUSTOMER);
        userRepository.save(user);

        // --- 2. Δημιουργία Καταστημάτων (Με Ελληνικά & Διευθύνσεις) ---

        // Κατάστημα 1: Souvlaki City
        Store s1 = new Store();
        s1.setName("Souvlaki City");
        s1.setArea("Αθήνα");                 // ΕΛΛΗΝΙΚΑ
        s1.setAddress("Μοναστηρακίου 15");   // ΠΡΟΣΘΗΚΗ ΔΙΕΥΘΥΝΣΗΣ
        s1.setCuisineType("Ελληνική");       // ΕΛΛΗΝΙΚΑ
        s1.setMinOrderAmount(BigDecimal.valueOf(5.0));
        s1.setOpen(true);
        s1.setOwner(owner1);
        storeRepository.save(s1);

        createProduct(s1, "Πίτα Γύρος Χοιρινό", 3.80);
        createProduct(s1, "Πίτα Καλαμάκι Κοτόπουλο", 3.50);
        createProduct(s1, "Χωριάτικη Σαλάτα", 6.50);
        createProduct(s1, "Τζατζίκι Μερίδα", 3.00);

        // Κατάστημα 2: Burger House
        Store s2 = new Store();
        s2.setName("Burger House");
        s2.setArea("Μαρούσι");               // ΕΛΛΗΝΙΚΑ
        s2.setAddress("Λεωφόρος Κηφισίας 40"); // ΠΡΟΣΘΗΚΗ ΔΙΕΥΘΥΝΣΗΣ
        s2.setCuisineType("Αμερικάνικη");    // ΕΛΛΗΝΙΚΑ
        s2.setMinOrderAmount(BigDecimal.valueOf(10.0));
        s2.setOpen(true);
        s2.setOwner(owner1);
        storeRepository.save(s2);

        createProduct(s2, "Cheeseburger", 8.50);
        createProduct(s2, "Bacon Mushroom Melt", 9.50);
        createProduct(s2, "Chicken Burger", 8.00);
        createProduct(s2, "Πατάτες Τηγανητές", 3.50);

        // Κατάστημα 3: Pizza Fanatic
        Store s3 = new Store();
        s3.setName("Pizza Fanatic");
        s3.setArea("Περιστέρι");             // ΕΛΛΗΝΙΚΑ
        s3.setAddress("Παναγή Τσαλδάρη 22"); // ΠΡΟΣΘΗΚΗ ΔΙΕΥΘΥΝΣΗΣ
        s3.setCuisineType("Ιταλική");        // ΕΛΛΗΝΙΚΑ
        s3.setMinOrderAmount(BigDecimal.valueOf(12.0));
        s3.setOpen(true);
        s3.setOwner(owner1);
        storeRepository.save(s3);

        createProduct(s3, "Pizza Margherita", 10.00);
        createProduct(s3, "Pizza Special", 14.00);
        createProduct(s3, "Pizza Pepperoni", 12.00);
        createProduct(s3, "Coca Cola 1.5L", 2.50);

        // Κατάστημα 4: Sushi Ko
        Store s4 = new Store();
        s4.setName("Sushi Ko");
        s4.setArea("Γλυφάδα");               // ΕΛΛΗΝΙΚΑ
        s4.setAddress("Αγγέλου Μεταξά 10");  // ΠΡΟΣΘΗΚΗ ΔΙΕΥΘΥΝΣΗΣ
        s4.setCuisineType("Ασιατική");       // ΕΛΛΗΝΙΚΑ
        s4.setMinOrderAmount(BigDecimal.valueOf(20.0));
        s4.setOpen(false); // Κλειστό
        s4.setOwner(owner1);
        storeRepository.save(s4);

        createProduct(s4, "Salmon Rolls (8τμχ)", 9.00);
        createProduct(s4, "Tuna Sashimi", 12.00);
        createProduct(s4, "Spicy Crab", 11.00);
        createProduct(s4, "Miso Soup", 5.00);

        // Κατάστημα 5: Sweet Crepes
        Store s5 = new Store();
        s5.setName("Sweet Crepes");
        s5.setArea("Περιστέρι");             // ΕΛΛΗΝΙΚΑ
        s5.setAddress("Αιμιλίου Βεΐκου 5");  // ΠΡΟΣΘΗΚΗ ΔΙΕΥΘΥΝΣΗΣ
        s5.setCuisineType("Γλυκά");          // ΕΛΛΗΝΙΚΑ
        s5.setMinOrderAmount(BigDecimal.valueOf(6.0));
        s5.setOpen(true);
        s5.setOwner(owner1);
        storeRepository.save(s5);

        createProduct(s5, "Κρέπα Σοκολάτα", 5.00);
        createProduct(s5, "Κρέπα Μπανάνα Μπισκότο", 6.50);
        createProduct(s5, "Waffle Special", 8.00);
        createProduct(s5, "Μπάλα Παγωτό", 2.50);

        System.out.println("--- DATA LOADED SUCCESSFULLY ---");
    }

    private void createProduct(Store store, String name, double price) {
        Product p = new Product();
        p.setName(name);
        p.setPrice(BigDecimal.valueOf(price));
        p.setStore(store);
        productRepository.save(p);
    }
}