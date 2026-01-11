package gr.hua.dit.StreetFoodGo.street_food_go.service;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.MenuItem;

import java.util.List;

public interface MenuService {

    List<MenuItem> getMenuByStore(Long storeId);

    MenuItem addMenuItem(Long storeId, MenuItem item);

    MenuItem setAvailability(Long itemId, boolean available);
}
