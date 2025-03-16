package com.practice.springjpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.springjpa.dto.CategoryCreateDto;
import com.practice.springjpa.dto.CategoryDto;
import com.practice.springjpa.dto.ProductDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIT {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    public void createTest() {
        CategoryCreateDto categoryCreateDto = new CategoryCreateDto();
        categoryCreateDto.setName("Категория");

        String categoryJson = objectMapper.writeValueAsString(categoryCreateDto);

        String contentAsString = mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.options.length()").value(0))
                .andReturn()
                .getResponse()
                .getContentAsString();

        CategoryDto categoryDto = objectMapper.readValue(contentAsString, CategoryDto.class);
        int id = categoryDto.getId();

        ProductDto productDto = new ProductDto();
        productDto.setName("Товар");
        productDto.setPrice(2000);

        String json = objectMapper.writeValueAsString(productDto);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .param("categoryId", Integer.toString(id)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.price").value(productDto.getPrice()))
                .andExpect(jsonPath("$.category").value(categoryCreateDto.getName()));
    }

    @Test
    @SneakyThrows
    public void createTestNoCategory() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Товар");
        productDto.setPrice(2000);

        String json = objectMapper.writeValueAsString(productDto);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .param("categoryId", "100"))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void shouldFindById() {
        int productId = 1;
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/products/" + productId));

        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.id").value(productId));
        resultActions.andExpect(jsonPath("$.name").value("Intel Core I9 9900"));
        resultActions.andExpect(jsonPath("$.price").value(249990));
        resultActions.andExpect(jsonPath("$.category").value("Процессоры"));
    }

    @Test
    @SneakyThrows
    public void shouldFindAll() {
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Intel Core I9 9900"))
                .andExpect(jsonPath("$[0].price").value(249990))
                .andExpect(jsonPath("$[0].category").value("Процессоры"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("AMD Ryzen R7 7700"))
                .andExpect(jsonPath("$[1].price").value(269990))
                .andExpect(jsonPath("$[1].category").value("Процессоры"));
    }
}

