package com.example.inventory.kafka;


import com.example.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final InventoryService inventoryService;

    @KafkaListener(topics = "order-events", groupId = "inventory-group")
    public void consume(String message) throws JSONException {
        log.info("📦 Kafka'dan sipariş mesajı alındı: {}", message);

        // JSON mesajdan orderId ve productId'yi al
        JSONObject json = new JSONObject(message);
        Long orderId = json.getLong("id");
        String productId = json.getString("productId");

        // Stok kontrolü yap
        inventoryService.checkInventory(orderId, productId);

        log.info("✅ Inventory kaydı oluşturuldu → orderId: {}, productId: {}", orderId, productId);
    }
}