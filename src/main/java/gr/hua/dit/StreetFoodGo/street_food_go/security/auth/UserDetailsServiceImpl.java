package gr.hua.dit.StreetFoodGo.street_food_go.security.auth;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.User;
import gr.hua.dit.StreetFoodGo.street_food_go.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Αναζήτηση του χρήστη στη βάση δεδομένων μέσω του UserRepository
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Ο χρήστης δεν βρέθηκε: " + username));
    }
}