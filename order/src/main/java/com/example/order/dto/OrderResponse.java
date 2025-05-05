package com.example.order.dto;

import com.example.order.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderResponse {
    private String message;
    private Order order;
}
// String Bilgisini "Kafka'ya sipariş mesajı gönderildi" postmanda gorulmesi icin

// public class Order {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String productId; // urun id
//    private Integer quantity; // miktar
//    private Double price; // fiyat
//}