package gr.hua.dit.StreetFoodGo.street_food_go.web.controller;

import gr.hua.dit.StreetFoodGo.street_food_go.service.AuthService;
import gr.hua.dit.StreetFoodGo.street_food_go.web.dto.LoginRequest;
import gr.hua.dit.StreetFoodGo.street_food_go.web.dto.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest request, Model model) {
        try {
            // 1. Έλεγχος Κωδικού
            if (request.getPassword() == null || request.getPassword().length() < 6) {
                throw new RuntimeException("Ο κωδικός πρέπει να έχει τουλάχιστον 6 χαρακτήρες.");
            }

            // 2. ΥΠΟΧΡΕΩΤΙΚΑ ΠΕΛΑΤΗΣ (CUSTOMER)
            // Αφού αφαιρέσαμε το πεδίο από το HTML, το θέτουμε εδώ χειροκίνητα
            request.setRole("CUSTOMER");

            // 3. Εγγραφή
            authService.register(request);
            return "redirect:/login?registered";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("registerRequest", request);
            return "register";
        }
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginRequest request, HttpServletRequest httpRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(authentication);
            HttpSession session = httpRequest.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, sc);

            return "redirect:/?loginSuccess";

        } catch (Exception e) {
            return "redirect:/login?error";
        }
    }
}