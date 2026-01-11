package gr.hua.dit.StreetFoodGo.street_food_go.exception;

public class StoreClosedException extends RuntimeException {
    public StoreClosedException(Long storeId) {
        super("Store is closed: " + storeId);
    }
}
