package gr.hua.dit.StreetFoodGo.street_food_go.controller;

// --- Imports για τις οντότητες και το Repository ---
import gr.hua.dit.StreetFoodGo.street_food_go.entities.Order;
import gr.hua.dit.StreetFoodGo.street_food_go.entities.OrderStatus;
import gr.hua.dit.StreetFoodGo.street_food_go.repository.OrderRepository;

// --- Imports για Spring Framework & Security ---
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// --- Imports για Java Utilities (ΕΔΩ ΗΤΑΝ ΤΟ ΠΡΟΒΛΗΜΑ) ---
import java.security.Principal; // Διορθώνει το Principal
import java.util.List;          // Διορθώνει το List

@Controller
@RequestMapping("/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OrderRepository orderRepository;

    // Dashboard Ιδιοκτήτη: Δείχνει όλες τις παραγγελίες
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('OWNER')")
    public String dashboard(Model model, Principal principal) {
        // Τραβάμε τις παραγγελίες με βάση το username του ιδιοκτήτη
        List<Order> orders = orderRepository.findByStore_Owner_UsernameOrderByCreatedDateDesc(principal.getName());
        model.addAttribute("orders", orders);
        return "owner-dashboard";
    }

    // Αλλαγή κατάστασης παραγγελίας (Αποδοχή, Απόρριψη κλπ)
    @PostMapping("/orders/{id}/status")
    @PreAuthorize("hasRole('OWNER')")
    public String updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Η παραγγελία δεν βρέθηκε"));

        order.setStatus(status);
        orderRepository.save(order);

        return "redirect:/owner/dashboard";
    }
}