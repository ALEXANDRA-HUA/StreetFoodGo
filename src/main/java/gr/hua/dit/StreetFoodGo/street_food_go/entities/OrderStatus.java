package gr.hua.dit.StreetFoodGo.street_food_go.entities;

public enum OrderStatus {
    NEW("Νέα Παραγγελία"),
    ACCEPTED("Αποδοχή"),
    REJECTED("Απόρριψη"),
    PREPARING("Ετοιμάζεται"),
    OUT_FOR_DELIVERY("Σε Διανομή"),
    COMPLETED("Ολοκληρώθηκε"),
    CANCELLED("Ακυρώθηκε");

    private final String greekName;

    OrderStatus(String greekName) {
        this.greekName = greekName;
    }

    public String getGreekName() {
        return greekName;
    }
}