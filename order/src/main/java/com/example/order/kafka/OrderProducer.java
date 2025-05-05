package com.example.order.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendOrderEvent(String orderJson) {
        kafkaTemplate.send("order-events", orderJson);
        System.out.println("✅ Kafka'ya sipariş mesajı gönderildi: " + orderJson);

    }
}

// 	•	Kafka’ya mesaj gönderir.
//	•	"order-events" adında bir topic’e siparişleri yollar.
//	•	orderJson → siparişin JSON hali (string)