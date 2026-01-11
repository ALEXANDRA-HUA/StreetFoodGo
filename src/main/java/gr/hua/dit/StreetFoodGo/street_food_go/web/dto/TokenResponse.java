package gr.hua.dit.StreetFoodGo.street_food_go.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {

    private String token;
    private String tokenType = "Bearer";
}
