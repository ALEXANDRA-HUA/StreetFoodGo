package gr.hua.dit.StreetFoodGo.street_food_go.service.impl;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.Role;
import gr.hua.dit.StreetFoodGo.street_food_go.entities.User;
import gr.hua.dit.StreetFoodGo.street_food_go.repository.UserRepository;
import gr.hua.dit.StreetFoodGo.street_food_go.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Long saveUser(User user) {
        // Κρυπτογράφηση κωδικού πριν την αποθήκευση
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));

        // Αν δεν έχει οριστεί ρόλος, βάζουμε CUSTOMER ως default
        if (user.getRole() == null) {
            user.setRole(Role.CUSTOMER);
        }

        userRepository.save(user);
        return user.getId();
    }

    @Override
    @Transactional
    public Integer updateUser(User user) {
        userRepository.save(user);
        return 1;
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // --- Η ΜΕΘΟΔΟΣ ΠΟΥ ΕΛΕΙΠΕ (getUser) ---
    @Override
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    // --- Η ΜΕΘΟΔΟΣ ΠΟΥ ΠΡΟΣΘΕΣΑΜΕ ΓΙΑ ΤΟ StoreController (findByUsername) ---
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }
}