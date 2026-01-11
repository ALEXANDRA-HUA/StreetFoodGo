package gr.hua.dit.StreetFoodGo.street_food_go.controller;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.User;
import gr.hua.dit.StreetFoodGo.street_food_go.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        // ΔΙΟΡΘΩΣΗ: Αλλαγή από getById σε getUser
        return userService.getUser(id);
    }
}