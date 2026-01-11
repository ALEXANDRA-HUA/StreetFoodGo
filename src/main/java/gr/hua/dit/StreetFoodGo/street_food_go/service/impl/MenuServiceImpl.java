package gr.hua.dit.StreetFoodGo.street_food_go.service.impl;

import gr.hua.dit.StreetFoodGo.street_food_go.entities.MenuItem;
import gr.hua.dit.StreetFoodGo.street_food_go.entities.Store;
import gr.hua.dit.StreetFoodGo.street_food_go.repository.MenuItemRepository;
import gr.hua.dit.StreetFoodGo.street_food_go.repository.StoreRepository;
import gr.hua.dit.StreetFoodGo.street_food_go.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    private final MenuItemRepository menuItemRepository;
    private final StoreRepository storeRepository;

    public MenuServiceImpl(MenuItemRepository menuItemRepository,
                           StoreRepository storeRepository) {
        this.menuItemRepository = menuItemRepository;
        this.storeRepository = storeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuItem> getMenuByStore(Long storeId) {
        return menuItemRepository.findByStoreId(storeId);
    }

    @Override
    public MenuItem addMenuItem(Long storeId, MenuItem item) {
        Store store = storeRepository.findById(storeId).orElseThrow();
        item.setId(null);
        item.setStore(store);
        item.setAvailable(true);
        return menuItemRepository.save(item);
    }

    @Override
    public MenuItem setAvailability(Long itemId, boolean available) {
        MenuItem item = menuItemRepository.findById(itemId).orElseThrow();
        item.setAvailable(available);
        return item;
    }
}
