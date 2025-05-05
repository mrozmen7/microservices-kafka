package com.example.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String productId;
    private Integer quantity;
    private Double price;
    private String address; // yeni alan

}

// Kullanicidan gelen verileri temsil eder
// Postman’den gelen JSON verisini yakalayan sınıf.
// Order veritabanı nesnesi yerine, dışarıdan veri almak için kullanılır (temiz ve güvenli).