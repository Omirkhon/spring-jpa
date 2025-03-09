package com.practice.springjpa.service;

import com.practice.springjpa.model.Category;
import com.practice.springjpa.model.Product;
import com.practice.springjpa.repository.CategoryRepository;
import com.practice.springjpa.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    ProductRepository productRepository;
    @InjectMocks
    ProductService productService;

    @Test
    void create_shouldThrowExceptionWhenCategoryIsNotFound() {
        String message = "Категория не найдена";

        Product product = new Product();

        when(categoryRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> productService.create(product, 999));
        Assertions.assertEquals(message, exception.getMessage());
    }

    @Test
    void create_shouldSave() {
        Product product = new Product();
        product.setName("Тестовый товар");

        Category category = new Category();
        category.setName("Тестовая категория");
        categoryRepository.save(category);

        when(categoryRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(category));

        when(productRepository.save(Mockito.any(Product.class)))
                .thenReturn(product);

        Product savedProduct = productService.create(product, category.getId());

        Assertions.assertEquals(product.getName(), savedProduct.getName());
    }
}
