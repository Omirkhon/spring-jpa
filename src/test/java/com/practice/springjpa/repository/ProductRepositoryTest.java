package com.practice.springjpa.repository;

import com.practice.springjpa.model.Category;
import com.practice.springjpa.model.Option;
import com.practice.springjpa.model.Product;
import com.practice.springjpa.model.Value;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    ValueRepository valueRepository;
    @Autowired
    ProductRepository productRepository;

    @Test
    void optionsShouldBeConnectedWithCategory() {
        List<Value> values = new ArrayList<>();
        List<Option> options = new ArrayList<>();

        Category category = new Category();
        category.setName("Smartphones");
        categoryRepository.save(category);

        Option color = new Option();
        color.setName("Color");
        color.setCategory(category);
        optionRepository.save(color);
        options.add(color);

        Option storage = new Option();
        storage.setName("Storage");
        storage.setCategory(category);
        optionRepository.save(storage);
        options.add(storage);

        Product product = new Product();
        product.setName("IPhone 15");
        product.setPrice(2000);
        product.setCategory(category);
        productRepository.save(product);

        Value colorValue = new Value();
        colorValue.setName("Black");
        colorValue.setOption(color);
        colorValue.setProduct(product);
        valueRepository.save(colorValue);
        values.add(colorValue);

        Value storageValue = new Value();
        storageValue.setName("128GB");
        storageValue.setOption(storage);
        storageValue.setProduct(product);
        valueRepository.save(storageValue);
        values.add(storageValue);

        assertEquals(values, product.getValues());
        assertEquals(options, category.getOptions());
    }

    @Test
    void optionsAndValuesShouldBeConnectedWithProduct() {
        List<Value> values = new ArrayList<>();
        List<Option> options = new ArrayList<>();

        Category smartphones = new Category();
        smartphones.setName("Smartphones");
        categoryRepository.save(smartphones);

        Category laptops = new Category();
        laptops.setName("Laptops");
        categoryRepository.save(laptops);

        Option manufacturer = new Option();
        manufacturer.setName("Manufacturer");
        manufacturer.setCategory(smartphones);
        optionRepository.save(manufacturer);
        options.add(manufacturer);

        Option storage = new Option();
        storage.setName("Storage");
        storage.setCategory(smartphones);
        optionRepository.save(storage);
        options.add(storage);

        Product product = new Product();
        product.setName("IPhone 12");
        product.setPrice(2000);
        product.setCategory(smartphones);
        productRepository.save(product);

        Product product2 = new Product();
        product2.setName("Macbook Pro 14");
        product2.setPrice(2000);
        product2.setCategory(smartphones);
        productRepository.save(product2);
    }
}
