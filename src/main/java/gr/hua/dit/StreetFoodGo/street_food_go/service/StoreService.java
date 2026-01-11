package gr.hua.dit.StreetFoodGo.street_food_go.service;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.Store;

import java.util.List;

public interface StoreService {

    List<Store> getAllStores();
    Store getStoreById(Long id);
    List<Store> getOpenStores();

    Store getById(Long storeId);

    Store setOpen(Long storeId, boolean open);
}
