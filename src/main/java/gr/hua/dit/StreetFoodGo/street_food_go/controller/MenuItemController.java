package gr.hua.dit.StreetFoodGo.street_food_go.controller;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.MenuItem;
import gr.hua.dit.StreetFoodGo.street_food_go.service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuItemController {

    private final MenuService menuService;

    public MenuItemController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/{storeId}")
    public List<MenuItem> getMenu(@PathVariable Long storeId) {
        return menuService.getMenuByStore(storeId);
    }

    @PostMapping("/{storeId}")
    public MenuItem add(@PathVariable Long storeId, @RequestBody MenuItem item) {
        return menuService.addMenuItem(storeId, item);
    }

    @PutMapping("/{itemId}/availability")
    public MenuItem availability(@PathVariable Long itemId, @RequestParam boolean available) {
        return menuService.setAvailability(itemId, available);
    }
}
