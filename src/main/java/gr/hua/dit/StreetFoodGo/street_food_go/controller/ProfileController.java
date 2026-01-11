package gr.hua.dit.StreetFoodGo.street_food_go.controller; // Προσοχή στο package name σου

import gr.hua.dit.StreetFoodGo.street_food_go.entities.Address;
import gr.hua.dit.StreetFoodGo.street_food_go.entities.User;
import gr.hua.dit.StreetFoodGo.street_food_go.service.UserService;
import gr.hua.dit.StreetFoodGo.street_food_go.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final AddressRepository addressRepository;

    @GetMapping
    public String showProfile(@AuthenticationPrincipal UserDetails userDetails,
                              @RequestParam(required = false) Long returnToStore, // <--- ΝΕΟ: Διαβάζουμε το ID του καταστήματος
                              Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("addresses", user.getAddresses());
        model.addAttribute("newAddress", new Address());
        model.addAttribute("returnToStore", returnToStore); // <--- ΝΕΟ: Το στέλνουμε στη σελίδα
        return "profile";
    }

    @PostMapping("/address/add")
    public String addAddress(@ModelAttribute Address address,
                             @AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam(required = false) Long returnToStore) { // <--- ΝΕΟ: Το διαβάζουμε και στην αποθήκευση
        User user = userService.findByUsername(userDetails.getUsername());
        user.addAddress(address);
        addressRepository.save(address);

        // Αν υπάρχει ID καταστήματος, επιστρέφουμε στο προφίλ ΚΡΑΤΩΝΤΑΣ το ID στο URL
        if (returnToStore != null) {
            return "redirect:/profile?returnToStore=" + returnToStore;
        }
        return "redirect:/profile";
    }
}