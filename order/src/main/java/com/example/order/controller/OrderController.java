package com.example.order.controller;


import com.example.order.dto.OrderRequest;
import com.example.order.dto.OrderResponse;
import com.example.order.model.Order;
import com.example.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request) throws Exception {
        Order order = orderService.placeOrder(request);
        OrderResponse response = new OrderResponse("✅ Kafka'ya sipariş mesajı gönderildi", order);
        return ResponseEntity.ok(response);
    }
}

// @Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class OrderRequest {
//    private String productId;
//    private Integer quantity;
//    private Double price;
//}

// @Data
//@AllArgsConstructor
//public class OrderResponse {
//    private String message;
//    private Order order;
//}