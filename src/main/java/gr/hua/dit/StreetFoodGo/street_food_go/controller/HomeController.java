package gr.hua.dit.StreetFoodGo.street_food_go.controller;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.Store;
import gr.hua.dit.StreetFoodGo.street_food_go.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final StoreService storeService;

    @GetMapping("/")
    public String home(
            Model model,
            @RequestParam(required = false) String area,
            @RequestParam(required = false) String cuisine
    ) {
        // 1. Παίρνουμε ΟΛΑ τα μαγαζιά
        List<Store> allStores = storeService.getAllStores();

        // 2. Υπολογίζουμε τις μοναδικές Περιοχές και Κουζίνες (για τα φίλτρα)
        List<String> areas = allStores.stream()
                .map(Store::getArea)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        List<String> cuisines = allStores.stream()
                .map(Store::getCuisineType)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        // 3. Φιλτράρισμα λίστας με βάση τις επιλογές του χρήστη
        List<Store> filteredStores = allStores.stream()
                .filter(store -> (area == null || area.isEmpty() || store.getArea().equalsIgnoreCase(area)))
                .filter(store -> (cuisine == null || cuisine.isEmpty() || store.getCuisineType().equalsIgnoreCase(cuisine)))
                .collect(Collectors.toList());

        // 4. Πέρασμα δεδομένων στο HTML
        model.addAttribute("stores", filteredStores);
        model.addAttribute("areas", areas);
        model.addAttribute("cuisines", cuisines);

        // Κρατάμε τις επιλογές για να φαίνονται επιλεγμένες στα dropdown
        model.addAttribute("selectedArea", area);
        model.addAttribute("selectedCuisine", cuisine);

        return "home";
    }
}