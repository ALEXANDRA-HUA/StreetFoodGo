package gr.hua.dit.StreetFoodGo.street_food_go.controller;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.Order;
import gr.hua.dit.StreetFoodGo.street_food_go.entities.OrderStatus;
import gr.hua.dit.StreetFoodGo.street_food_go.entities.User;
import gr.hua.dit.StreetFoodGo.street_food_go.repository.OrderRepository;
import gr.hua.dit.StreetFoodGo.street_food_go.repository.UserRepository;
import gr.hua.dit.StreetFoodGo.street_food_go.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// --- ΤΑ IMPORTS ΠΟΥ ΕΛΕΙΠΑΝ ---
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository; // Χρειάζεται για το myOrders

    @PostMapping("/checkout")
    public String checkout(
            @RequestParam Long storeId,
            @RequestParam String type,
            @RequestParam(required = false) Long addressId,
            @RequestParam Map<String, String> allParams,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        try {
            // Έλεγχος αν ο χρήστης είναι συνδεδεμένος
            if (userDetails == null) {
                return "redirect:/login"; // Αν δεν υπάρχει session, πήγαινε στο login
            }

            // 1. Βρίσκουμε τον πραγματικό χρήστη από τη βάση
            User user = userRepository.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("UserNotFound"));

            // 2. Μετατροπή των παραμέτρων items[id]=qty σε Map<Long, Integer>
            Map<Long, Integer> items = new HashMap<>();
            allParams.forEach((key, value) -> {
                if (key.startsWith("items[")) {
                    try {
                        String idPart = key.substring(6, key.length() - 1);
                        Long id = Long.parseLong(idPart);
                        int qty = Integer.parseInt(value);
                        items.put(id, qty);
                    } catch (NumberFormatException e) {
                        // Αγνοούμε λανθασμένα keys
                    }
                }
            });

            if (items.isEmpty()) {
                throw new RuntimeException("Το καλάθι είναι άδειο.");
            }

            // 3. Δημιουργία παραγγελίας
            Order order = orderService.createOrder(user.getId(), storeId, type, addressId, items);

            // 4. Επιτυχία -> Redirect
            return "redirect:/orders/success/" + order.getId();

        } catch (RuntimeException e) {
            e.printStackTrace();
            if ("UserNotFound".equals(e.getMessage()) || e.getMessage().contains("χρήστης δεν βρέθηκε")) {
                return "redirect:/login?error=SessionExpired";
            }
            return "redirect:/store/" + storeId + "?error=" + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/store/" + storeId + "?error=UnknownError";
        }
    }

    @GetMapping("/success/{id}")
    public String showSuccessPage(@PathVariable Long id, Model model) {
        model.addAttribute("orderId", id);
        return "order-success";
    }

    // Αλλαγή status (χρήσιμο αν το καλείς από εδώ, αλλιώς υπάρχει και στο OwnerController)
    @PostMapping("/{orderId}/status")
    public String changeStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status
    ) {
        orderService.ownerSetStatus(orderId, status);
        return "redirect:/owner/dashboard";
    }

    // --- ΝΕΑ ΜΕΘΟΔΟΣ: Οι παραγγελίες μου (Πελάτης) ---
    @GetMapping("/my-orders")
    public String myOrders(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";

        List<Order> myOrders = orderRepository.findByUser_UsernameOrderByCreatedDateDesc(principal.getName());
        model.addAttribute("orders", myOrders);
        return "my-orders";
    }
}