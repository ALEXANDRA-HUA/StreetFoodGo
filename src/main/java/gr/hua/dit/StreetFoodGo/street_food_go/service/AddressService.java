package gr.hua.dit.StreetFoodGo.street_food_go.service;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.Address;

import java.util.List;

public interface AddressService {

    List<Address> getAddressesByCustomer(Long customerId);

    Address addAddress(Long customerId, Address address);
}
