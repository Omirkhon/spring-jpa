package com.practice.springjpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.springjpa.mapper.ProductMapper;
import com.practice.springjpa.model.Category;
import com.practice.springjpa.model.Product;
import com.practice.springjpa.repository.CategoryRepository;
import com.practice.springjpa.repository.ProductRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest({ProductController.class, ProductMapper.class})
@AutoConfigureMockMvc
public class ProductControllerTest {
    @MockitoBean
    ProductRepository productRepository;
    @MockitoBean
    CategoryRepository categoryRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    public void findById_test() {
        Category category = new Category();
        category.setId(200);
        category.setName("Категория");

        Product product = new Product();
        product.setId(100);
        product.setName("Товар");
        product.setPrice(1000);
        product.setCategory(category);

        Mockito.when(productRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(product));

        mockMvc.perform(get("/products/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.name").value("Товар"))
                .andExpect(jsonPath("$.price").value(1000))
                .andExpect(jsonPath("$.category").value("Категория"))
                .andExpect(jsonPath("$.options").isEmpty());
    }

    @Test
    @SneakyThrows
    public void findById_test_fail() {
        int wrongId = 999;

        Mockito.when(productRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/products/" + wrongId))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void findAll_test() {
        Category category = new Category();
        category.setId(200);
        category.setName("Категория");

        Product product = new Product();
        product.setId(100);
        product.setName("Товар");
        product.setPrice(1000);
        product.setCategory(category);

        Product product2 = new Product();
        product2.setId(200);
        product2.setName("Товар2");
        product2.setPrice(2000);
        product2.setCategory(category);

        Mockito.when(productRepository.findAll())
                .thenReturn(List.of(product, product2));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(100))
                .andExpect(jsonPath("$[0].name").value("Товар"))
                .andExpect(jsonPath("$[0].price").value(1000))
                .andExpect(jsonPath("$[0].category").value("Категория"))
                .andExpect(jsonPath("$[0].options").isEmpty())
                .andExpect(jsonPath("$[1].id").value(200))
                .andExpect(jsonPath("$[1].name").value("Товар2"))
                .andExpect(jsonPath("$[1].price").value(2000))
                .andExpect(jsonPath("$[1].category").value("Категория"))
                .andExpect(jsonPath("$[1].options").isEmpty());
    }

    @Test
    @SneakyThrows
    public void create_test() {
        Category category = new Category();
        category.setId(200);
        category.setName("Категория");

        Product product = new Product();
        product.setName("Товар");
        product.setPrice(1000);

        String json = objectMapper.writeValueAsString(product);

        Mockito.when(categoryRepository.findById(Mockito.anyInt()))
                        .thenReturn(Optional.of(category));

        Mockito.when(productRepository.save(Mockito.any()))
                .thenReturn(product);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .param("categoryId", Integer.toString(category.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.category").value(category.getName()));
    }
}
