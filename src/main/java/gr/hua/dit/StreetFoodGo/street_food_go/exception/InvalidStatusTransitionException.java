package gr.hua.dit.StreetFoodGo.street_food_go.exception;

public class InvalidStatusTransitionException extends RuntimeException {
    public InvalidStatusTransitionException(String msg) {
        super(msg);
    }
}
