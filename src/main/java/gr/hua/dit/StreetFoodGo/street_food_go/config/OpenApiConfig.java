package gr.hua.dit.StreetFoodGo.street_food_go.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Διαμόρφωση για την OpenAPI Specification (Swagger UI).
 * Ορίζει τις πληροφορίες της εφαρμογής και τον μηχανισμό ασφαλείας JWT Bearer Token.
 */
@Configuration
public class OpenApiConfig {

    private static final String SCHEME_NAME = "bearerAuth";
    private static final String SCHEME = "bearer";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // 1. Προσθήκη Πληροφοριών API
                .info(apiInfo())

                // 2. Ορισμός JWT Security Scheme
                .components(new Components()
                        .addSecuritySchemes(SCHEME_NAME, createJwtSecurityScheme()))

                // 3. Εφαρμογή Security Scheme σε όλα τα Endpoints
                //    (Εκτός από όσα έχουν δηλωθεί ως permitAll, π.χ. /auth/login, /auth/register)
                .addSecurityItem(new SecurityRequirement().addList(SCHEME_NAME));
    }

    private Info apiInfo() {
        return new Info()
                .title("StreetFoodGo - Online Ordering API")
                .description("RESTful API για online παραγγελίες από καταστήματα/food trucks. Υποστηρίζει ρόλους: VISITOR, CUSTOMER, OWNER.")
                .version("v1.0.0")
                .contact(new Contact()
                        .name("Μέλος Β - API & Security Specialist")
                        .email("your.email@example.com"));
    }

    /**
     * Δημιουργεί το Security Scheme για το JWT.
     * Αυτό επιτρέπει στο Swagger UI να εμφανίζει ένα πεδίο εισόδου για το token
     * και να το προσθέτει αυτόματα στις headers των αιτημάτων.
     */
    private SecurityScheme createJwtSecurityScheme() {
        return new SecurityScheme()
                .name(SCHEME_NAME)
                .type(SecurityScheme.Type.HTTP)
                .scheme(SCHEME)
                .bearerFormat("JWT") // Προσδιορίζει ότι το token είναι JWT
                .in(SecurityScheme.In.HEADER) // Το token στέλνεται στην Header
                .description("Εισάγετε το JWT (Bearer Token) που λάβατε μετά το login.");
    }
}
