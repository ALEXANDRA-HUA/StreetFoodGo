package gr.hua.dit.StreetFoodGo.street_food_go.controller;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.Product;
import gr.hua.dit.StreetFoodGo.street_food_go.entities.Store;
import gr.hua.dit.StreetFoodGo.street_food_go.repository.ProductRepository;
import gr.hua.dit.StreetFoodGo.street_food_go.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final StoreService storeService;

    // Φόρμα Προσθήκης
    @GetMapping("/new")
    @PreAuthorize("hasRole('OWNER')")
    public String showAddForm(@RequestParam Long storeId, Model model) {
        Product product = new Product();
        product.setStore(storeService.getStoreById(storeId));
        model.addAttribute("product", product);
        model.addAttribute("storeId", storeId);
        return "product-form";
    }

    // Φόρμα Επεξεργασίας
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id).orElseThrow();
        model.addAttribute("product", product);
        model.addAttribute("storeId", product.getStore().getId());
        return "product-form";
    }

    // Αποθήκευση (Create & Update)
    @PostMapping("/save")
    @PreAuthorize("hasRole('OWNER')")
    public String saveProduct(@ModelAttribute Product product, @RequestParam Long storeId) {
        Store store = storeService.getStoreById(storeId);
        product.setStore(store);
        productRepository.save(product);
        return "redirect:/store/" + storeId;
    }

    // Διαγραφή
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public String deleteProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        Long storeId = product.getStore().getId();
        productRepository.delete(product);
        return "redirect:/store/" + storeId;
    }
}