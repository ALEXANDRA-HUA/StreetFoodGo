package gr.hua.dit.StreetFoodGo.street_food_go.exception;

public class OrderModificationNotAllowedException extends RuntimeException {
    public OrderModificationNotAllowedException(String msg) {
        super(msg);
    }
}
