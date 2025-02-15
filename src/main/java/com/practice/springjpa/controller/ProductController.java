package com.practice.springjpa.controller;

import com.practice.springjpa.model.Category;
import com.practice.springjpa.model.Product;
import com.practice.springjpa.repository.CategoryRepository;
import com.practice.springjpa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @GetMapping("{id}")
    public Product findById(@PathVariable int id) {
        return productRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public Product create(@RequestParam int categoryId, @RequestBody Product product) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        product.setCategory(category);

        return productRepository.save(product);
    }

    @GetMapping("price-between/{price1}/and/{price2}")
    public List<Product> findByPriceBetween(@PathVariable int price1, @PathVariable int price2) {
        return productRepository.findByPriceBetween(price1, price2);
    }

    @GetMapping("name-containing/{substring}")
    public List<Product> findByNameContaining(@PathVariable String substring) {
        return productRepository.findByNameContainingIgnoreCase(substring);
    }

    @GetMapping("most-expensive")
    public Product findMostExpensiveProduct() {
        return productRepository.findFirstByOrderByPriceDesc().orElseThrow();
    }
}
