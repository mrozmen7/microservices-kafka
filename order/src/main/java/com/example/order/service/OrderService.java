package com.example.order.service;

import com.example.order.dto.OrderRequest;
import com.example.order.kafka.OrderProducer;
import com.example.order.model.Order;
import com.example.order.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;
    private final ObjectMapper objectMapper;

    public Order placeOrder(OrderRequest request) throws Exception {
        Order order = Order.builder()
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .address(request.getAddress())
                .build(); // Buraya kadar nesne olusturuldu ama Kayit yapilmadi

        // // 1. Veritabanına kaydet
        Order saved = orderRepository.save(order);

        // 2. Kafka mesajına çevir
        String orderJson = objectMapper.writeValueAsString(saved);

        // 3. Kafka’ya mesaj gönder
        orderProducer.sendOrderEvent(orderJson);

        return saved;
    }
}
// BEYIN
//  Asıl İş Mantığı Burada
// 1. Veriyi al
// 2. Veritabanına kaydet
// 3. Kafka'ya mesaj gönder
