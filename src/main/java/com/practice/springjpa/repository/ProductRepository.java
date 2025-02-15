package com.practice.springjpa.repository;

import com.practice.springjpa.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByPriceBetween(int price1, int price2);

    List<Product> findByNameContainingIgnoreCase(String substring);

    Optional<Product> findFirstByOrderByPriceDesc();
}
