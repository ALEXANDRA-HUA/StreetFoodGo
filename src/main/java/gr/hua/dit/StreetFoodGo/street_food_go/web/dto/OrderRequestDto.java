package gr.hua.dit.StreetFoodGo.street_food_go.web.dto;

import lombok.Data;
import java.util.Map;

@Data
public class OrderRequestDto {
    private Long storeId;
    private String orderType; // "DELIVERY" ή "TAKE_AWAY"
    private Long addressId;

    // Map<ProductId, Quantity> -> Π.χ. ID προϊόντος 101, ποσότητα 2
    private Map<Long, Integer> items;
}
