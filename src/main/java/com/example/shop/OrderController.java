package com.example.shop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Data
    static class OrderRequest {
        private Long productId;
        private Long userId;
    }

    @PostMapping
    public ResponseEntity<?> orderProduct(@RequestBody OrderRequest req) throws Exception {
        orderService.orderProduct(req.getProductId(), req.getUserId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
