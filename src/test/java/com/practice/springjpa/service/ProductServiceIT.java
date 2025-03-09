package com.practice.springjpa.service;

import com.practice.springjpa.model.Category;
import com.practice.springjpa.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
public class ProductServiceIT {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @Test
    void updatePrice() {
        Category category = new Category();
        category.setName("Тестовая категория");
        categoryService.create(category);

        Product product = new Product();
        product.setName("Тестовый товар");
        product.setPrice(2000);
        productService.create(product, category.getId());

        Product newProduct = new Product();
        newProduct.setPrice(1000);

        Product result = productService.update(product.getId(), newProduct);

        assertEquals(newProduct.getPrice(), result.getPrice());
        assertEquals(product.getName(), result.getName());
    }

    @Test
    void updateName() {
        Category category = new Category();
        category.setName("Тестовая категория 2");
        categoryService.create(category);

        Product product = new Product();
        product.setName("Тестовый товар");
        product.setPrice(2000);
        productService.create(product, category.getId());

        Product newProduct = new Product();
        newProduct.setName("Товар");

        Product result = productService.update(product.getId(), newProduct);

        assertEquals(newProduct.getName(), result.getName());
        assertEquals(product.getPrice(), result.getPrice());
    }

    @Test
    void updateByWrongId() {
        String message = "Товар не найден";

        Category category = new Category();
        category.setName("Тестовая категория 3");
        categoryService.create(category);

        Product product = new Product();
        product.setName("Тестовый товар");
        product.setPrice(2000);
        productService.create(product, category.getId());

        Product newProduct = new Product();
        newProduct.setName("Товар");

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> productService.update(product.getId()+1, newProduct));
        Assertions.assertEquals(message, exception.getMessage());
    }
}
