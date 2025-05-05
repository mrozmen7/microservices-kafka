package com.example.inventory.service;


import com.example.inventory.model.Inventory;
import com.example.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public Inventory checkInventory(Long orderId, String productId) {
        Inventory inventory = Inventory.builder()
                .orderId(orderId)
                .productId(productId)
                .status("AVAILABLE") // Şimdilik her ürün mevcut kabul ediyoruz
                .build();

        return inventoryRepository.save(inventory);
    }
}