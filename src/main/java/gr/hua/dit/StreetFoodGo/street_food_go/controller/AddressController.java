package gr.hua.dit.StreetFoodGo.street_food_go.controller;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.Address;
import gr.hua.dit.StreetFoodGo.street_food_go.service.AddressService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/customer/{customerId}")
    public List<Address> getAddresses(@PathVariable Long customerId) {
        return addressService.getAddressesByCustomer(customerId);
    }

    @PostMapping("/customer/{customerId}")
    public Address addAddress(
            @PathVariable Long customerId,
            @RequestBody Address address
    ) {
        return addressService.addAddress(customerId, address);
    }
}
