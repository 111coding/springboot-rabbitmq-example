package com.example.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.example.shop.model.Product;

import jakarta.persistence.LockModeType;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("from Product p where p.id = :id")
    Optional<Product> findByIdPerssimisticWrite(Long id);
}
