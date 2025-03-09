package com.practice.springjpa.service;

import com.practice.springjpa.model.Category;
import com.practice.springjpa.model.Product;
import com.practice.springjpa.repository.CategoryRepository;
import com.practice.springjpa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public Product create(Product product, int categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Категория не найдена"));
        product.setCategory(category);
        productRepository.save(product);
        return product;
    }

    public Product update(int productId, Product updatedProduct) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Товар не найден"));
        if (updatedProduct.getName() != null && !updatedProduct.getName().isBlank()) {
            existingProduct.setName(updatedProduct.getName());
        }

        if (updatedProduct.getPrice() != null) {
            existingProduct.setPrice(updatedProduct.getPrice());
        }

        productRepository.save(existingProduct);
        return existingProduct;
    }
}
