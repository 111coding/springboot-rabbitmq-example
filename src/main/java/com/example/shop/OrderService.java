package com.example.shop;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shop.model.Order;
import com.example.shop.repository.OrderRepository;
import com.example.shop.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepo;
    private final OrderRepository orderRepo;
    private final AmqpTemplate rabbitTemplate;


    @Transactional
    public void orderProduct(Long productId, Long userId) throws Exception {
        var order = new OrderMessage(productId, userId);
        rabbitTemplate.convertAndSend("order.direct.exchange", "order.queue", order);
    }

    @RabbitListener(queues = { "order.queue" })
    public void processOrder(OrderMessage orderMessage) {
        final var productId = orderMessage.getProductId();
        final var userId = orderMessage.getUserId();
        var productOpt = productRepo.findById(productId);
        if (productOpt.isEmpty()) {
            return;
        }
        var product = productOpt.get();
        long count = orderRepo.countByProductId(productId);
        if (product.getQuantity().longValue() <= count) {
            return;
        }
        Order order = Order.builder()
                .product(product)
                .userId(userId)
                .build();
        orderRepo.save(order);
    }
}
