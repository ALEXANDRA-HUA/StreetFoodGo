package gr.hua.dit.StreetFoodGo.street_food_go.service.impl;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.Address;
import gr.hua.dit.StreetFoodGo.street_food_go.entities.User;
import gr.hua.dit.StreetFoodGo.street_food_go.repository.AddressRepository;
import gr.hua.dit.StreetFoodGo.street_food_go.repository.UserRepository;
import gr.hua.dit.StreetFoodGo.street_food_go.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressServiceImpl(AddressRepository addressRepository,
                              UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Address> getAddressesByCustomer(Long customerId) {
        return addressRepository.findByCustomerId(customerId);
    }

    @Override
    public Address addAddress(Long customerId, Address address) {
        User customer = userRepository.findById(customerId).orElseThrow();
        address.setId(null);
        address.setCustomer(customer);
        return addressRepository.save(address);
    }
}
