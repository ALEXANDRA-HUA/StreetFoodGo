package gr.hua.dit.StreetFoodGo.street_food_go.service;

public interface CurrencyService {
    /**
     * Επιστρέφει την ισοτιμία Ευρώ -> Δολαρίου.
     * @return Η τιμή του δολαρίου έναντι 1 Ευρώ.
     */
    Double getDollarRate();
}