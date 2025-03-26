package com.example.shop;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shop.model.Order;
import com.example.shop.model.Product;
import com.example.shop.repository.OrderRepository;
import com.example.shop.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;

    @Transactional
    public void orderProduct(Long productId, Long userId) throws Exception {
        Product product = productRepo.findByIdPerssimisticWrite(productId).orElseThrow();
        long count = orderRepo.countByProductId(productId);
        if (product.getQuantity().longValue() <= count) {
            throw new Exception("SOLD_OUT");
        }
        Order order = Order.builder()
                .product(product)
                .userId(userId)
                .build();
        orderRepo.save(order);
    }
}
