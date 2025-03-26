package com.example.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.shop.model.Product;
import com.example.shop.repository.ProductRepository;

import jakarta.annotation.PostConstruct;

@Component
public class StartService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    ProductRepository productRepo;

    @PostConstruct
    public void init() {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=0;");
        jdbcTemplate.execute("TRUNCATE TABLE order_tb;");
        jdbcTemplate.execute("TRUNCATE TABLE product_tb;");
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=1;");

        final var product = Product.builder()
                .name("지드래곤 콘서트")
                .price(100000)
                .quantity(100)
                .build();
        productRepo.save(product);
    }
}