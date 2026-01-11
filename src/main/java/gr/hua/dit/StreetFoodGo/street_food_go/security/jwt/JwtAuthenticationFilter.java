package gr.hua.dit.StreetFoodGo.street_food_go.security.jwt;

import gr.hua.dit.StreetFoodGo.street_food_go.security.auth.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Φίλτρο που ελέγχει την ύπαρξη και την εγκυρότητα του JWT σε κάθε αίτημα (request).
 * Εφαρμόζεται ΜΟΝΟ στα REST API endpoints.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Έλεγχος Header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Εξαγωγή Token και Username
        jwt = authHeader.substring(7); // "Bearer " έχει 7 χαρακτήρες

        try {
            username = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            // Αν το token είναι λανθασμένο ή έληξε
            System.err.println("JWT error: " + e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Αυθεντικοποίηση (αν δεν είναι ήδη αυθεντικοποιημένος)
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Δημιουργία αντικειμένου Authentication
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // Δεν χρειάζεται τα credentials, αφού το token είναι το credential
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Ορισμός του χρήστη ως αυθεντικοποιημένου στο Spring Security Context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
