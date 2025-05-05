package com.example.shipping.kafka;

import com.example.shipping.service.ShippingService;
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

    private final ShippingService shippingService;

    @KafkaListener(topics = "order-events", groupId = "shipping-group")
    public void consume(String message) throws JSONException {
        log.info("📥 Kafka'dan sipariş mesajı alındı: {}", message);

        // JSON mesajdan orderId'yi çıkar
        JSONObject json = new JSONObject(message);
        Long orderId = json.getLong("id"); // siparsi id ceker
        String address = json.getString("address");


        shippingService.createShipping(orderId, address); // bu id ve adrese gore shipping kaydeder

        log.info("🚚 Shipping kaydı oluşturuldu, orderId: {}", orderId); // terminale mesaj basar, ne oldugu takip edelior
    }
}