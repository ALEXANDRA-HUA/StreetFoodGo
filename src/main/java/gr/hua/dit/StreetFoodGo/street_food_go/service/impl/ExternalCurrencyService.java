package gr.hua.dit.StreetFoodGo.street_food_go.service.impl;

import gr.hua.dit.StreetFoodGo.street_food_go.service.CurrencyService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class ExternalCurrencyService implements CurrencyService {

    // Εξωτερικό API (Black-box service)
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/EUR";

    @Override
    public Double getDollarRate() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class);

            if (response != null && response.containsKey("rates")) {
                Map<String, Double> rates = (Map<String, Double>) response.get("rates");
                return rates.get("USD");
            }
        } catch (Exception e) {
            System.err.println("Σφάλμα στο Currency Service: " + e.getMessage());
        }
        return null;
    }
}
