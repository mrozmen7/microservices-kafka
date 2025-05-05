package com.example.shipping.service;

import com.example.shipping.model.Shipping;
import com.example.shipping.repository.ShippingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingService {

    private final ShippingRepository shippingRepository;

    public Shipping createShipping(Long orderId, String address) {
        Shipping shipping = Shipping.builder()
                .orderId(orderId)
                .status("PENDING")  // Başlangıç durumu
                .address(address) // Şimdilik sabit
                .build();

        return shippingRepository.save(shipping);
    }
}