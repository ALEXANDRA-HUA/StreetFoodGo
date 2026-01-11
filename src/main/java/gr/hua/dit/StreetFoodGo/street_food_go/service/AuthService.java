package gr.hua.dit.StreetFoodGo.street_food_go.service;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.Role;
import gr.hua.dit.StreetFoodGo.street_food_go.entities.User;
import gr.hua.dit.StreetFoodGo.street_food_go.repository.UserRepository;
import gr.hua.dit.StreetFoodGo.street_food_go.security.jwt.JwtService;
import gr.hua.dit.StreetFoodGo.street_food_go.web.dto.LoginRequest;
import gr.hua.dit.StreetFoodGo.street_food_go.web.dto.RegisterRequest;
import gr.hua.dit.StreetFoodGo.street_food_go.web.dto.TokenResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponse register(RegisterRequest request) {
        // Διορθωμένο: Σωστή αντιστοίχιση πεδίων
        var user = User.builder()
                .username(request.getUsername())         // Username στο username
                .email(request.getEmail())               // Email στο email
                .firstName(request.getFirstName())       // Νέο πεδίο
                .lastName(request.getLastName())         // Νέο πεδίο
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))   // Παίρνουμε τον ρόλο από τη φόρμα
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return TokenResponse.builder()
                .token(jwtToken)
                .build();
    }

    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return TokenResponse.builder()
                .token(jwtToken)
                .build();
    }
}