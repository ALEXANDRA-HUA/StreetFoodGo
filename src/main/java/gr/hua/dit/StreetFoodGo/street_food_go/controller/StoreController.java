package gr.hua.dit.StreetFoodGo.street_food_go.controller;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.Store;
import gr.hua.dit.StreetFoodGo.street_food_go.entities.User;
import gr.hua.dit.StreetFoodGo.street_food_go.service.CurrencyService;
import gr.hua.dit.StreetFoodGo.street_food_go.service.StoreService;
import gr.hua.dit.StreetFoodGo.street_food_go.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final UserService userService;
    private final CurrencyService currencyService;

    // --- VIEW (Σελίδα Καταστήματος / Μενού) ---
    @GetMapping("/store/{id}")
    public String showStoreMenu(@PathVariable Long id, Model model, Principal principal) {
        // 1. Βρες το κατάστημα
        Store store = storeService.getStoreById(id);

        // --- ΕΞΩΤΕΡΙΚΗ ΥΠΗΡΕΣΙΑ (Ισοτιμία) ---
        Double dollarRate = currencyService.getDollarRate();
        model.addAttribute("dollarRate", dollarRate);
        // -------------------------

        model.addAttribute("store", store);
        model.addAttribute("products", store.getProducts());

        // Μεταβλητή για τον έλεγχο αν ο τρέχων χρήστης είναι ο ιδιοκτήτης
        boolean isOwner = false;

        // 2. Φόρτωση χρήστη και διευθύνσεων (αν είναι συνδεδεμένος)
        if (principal != null) {
            try {
                // Προσπάθεια εύρεσης χρήστη. Αν έχει διαγραφεί η βάση αλλά έμεινε το cookie,
                // αυτό θα πετάξει RuntimeException το οποίο πιάνουμε στο catch.
                User user = userService.findByUsername(principal.getName());

                model.addAttribute("addresses", user.getAddresses());

                // ΕΛΕΓΧΟΣ: Είναι ο συνδεδεμένος χρήστης ο ιδιοκτήτης του μαγαζιού;
                if (store.getOwner() != null && store.getOwner().getId().equals(user.getId())) {
                    isOwner = true;
                }
            } catch (RuntimeException e) {
                // Αν ο χρήστης δεν βρεθεί (π.χ. μετά από restart της εφαρμογής),
                // τον αντιμετωπίζουμε σαν επισκέπτη για να μην "σκάσει" η σελίδα.
                System.err.println("Warning: User " + principal.getName() + " not found in DB. Treating as guest.");
                model.addAttribute("addresses", new ArrayList<>());
            }
        } else {
            // Αν δεν υπάρχει principal (δεν είναι συνδεδεμένος καθόλου)
            model.addAttribute("addresses", new ArrayList<>());
        }

        model.addAttribute("isOwner", isOwner); // Το στέλνουμε στο HTML

        return "store"; // Επιστρέφει το store.html
    }

    // --- Ενέργεια για Άνοιγμα/Κλείσιμο από τον Ιδιοκτήτη ---
    @PostMapping("/store/{id}/status")
    public String toggleStoreStatus(@PathVariable Long id, @RequestParam("open") boolean open, Principal principal) {
        Store store = storeService.getStoreById(id);

        if (principal != null) {
            try {
                User currentUser = userService.findByUsername(principal.getName());
                // Έλεγχος ασφαλείας: Μόνο ο ιδιοκτήτης μπορεί να το αλλάξει
                if (store.getOwner() != null && store.getOwner().getId().equals(currentUser.getId())) {
                    storeService.setOpen(id, open);
                }
            } catch (Exception e) {
                // Αν δεν βρεθεί ο χρήστης, απλά αγνοούμε το αίτημα
                System.err.println("Failed to toggle store status: User not found.");
            }
        }

        // Επιστροφή στη σελίδα του καταστήματος
        return "redirect:/store/" + id;
    }

    // --- API ENDPOINTS (Για χρήση JSON) ---

    @GetMapping("/stores")
    @ResponseBody
    public List<Store> getAllStores() {
        return storeService.getAllStores();
    }

    @GetMapping("/stores/open")
    @ResponseBody
    public List<Store> getOpenStores() {
        return storeService.getOpenStores();
    }

    @PutMapping("/stores/{storeId}/open")
    @ResponseBody
    public Store openStore(@PathVariable Long storeId) {
        return storeService.setOpen(storeId, true);
    }

    @PutMapping("/stores/{storeId}/close")
    @ResponseBody
    public Store closeStore(@PathVariable Long storeId) {
        return storeService.setOpen(storeId, false);
    }
}