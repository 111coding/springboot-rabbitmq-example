package com.example.shop;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderMessage {
    private Long productId;
    private Long userId;
}
