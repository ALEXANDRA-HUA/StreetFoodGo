package gr.hua.dit.StreetFoodGo.street_food_go.service.impl;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.Store;
import gr.hua.dit.StreetFoodGo.street_food_go.repository.StoreRepository;
import gr.hua.dit.StreetFoodGo.street_food_go.service.StoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public Store getStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Το κατάστημα δεν βρέθηκε με ID: " + id));
    }

    @Override
    public List<Store> getOpenStores() {
        return storeRepository.findByOpenTrue();
    }

    @Override
    public Store getById(Long storeId) {
        return storeRepository.findById(storeId).orElseThrow();
    }

    @Override
    public Store setOpen(Long storeId, boolean open) {
        Store store = storeRepository.findById(storeId).orElseThrow();
        store.setOpen(open);
        return store;
    }
}
