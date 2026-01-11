package gr.hua.dit.StreetFoodGo.street_food_go.service;


public interface NotificationService {
    /**
     * Στέλνει ειδοποίηση σε χρήστη
     * @param email Το email του χρήστη
     * @param message Το μήνυμα της ειδοποίησης
     */
    void sendNotification(String email, String message);
}
