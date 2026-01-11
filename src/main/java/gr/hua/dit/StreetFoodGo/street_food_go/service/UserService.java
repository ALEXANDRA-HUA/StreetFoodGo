package gr.hua.dit.StreetFoodGo.street_food_go.service;



import gr.hua.dit.StreetFoodGo.street_food_go.entities.User;

import java.util.List;



public interface UserService {

    Long saveUser(User user);

    Integer updateUser(User user);

    void deleteUser(Long id);

    List<User> getUsers();

    User getUser(Long id);



// --- Η ΜΕΘΟΔΟΣ ΠΟΥ ΛΕΙΠΕΙ ---

    User findByUsername(String username);

}