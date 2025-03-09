package com.practice.springjpa.repository;

import com.practice.springjpa.model.Category;
import com.practice.springjpa.model.Option;
import com.practice.springjpa.model.Product;
import com.practice.springjpa.model.Value;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
    @Autowired
    EntityManager entityManager;

    @Test
    void optionsShouldBeConnectedWithCategory() {
        Category category = new Category();
        category.setName("Smartphones");
        categoryRepository.save(category);

        Option color = new Option();
        color.setName("Color");
        color.setCategory(category);
        optionRepository.save(color);

        Option storage = new Option();
        storage.setName("Storage");
        storage.setCategory(category);
        optionRepository.save(storage);

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

        Value storageValue = new Value();
        storageValue.setName("128GB");
        storageValue.setOption(storage);
        storageValue.setProduct(product);
        valueRepository.save(storageValue);

        int id = product.getId();

        entityManager.clear();
        assertEquals(2, productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(""))
                .getValues().size());
        assertEquals(2, categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(""))
                .getOptions().size());
    }

    @Test
    void optionsAndValuesShouldBeConnectedWithProduct() {
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

        Option storage = new Option();
        storage.setName("Storage");
        storage.setCategory(smartphones);
        optionRepository.save(storage);

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

        Option manufacturerLaptop = new Option();
        manufacturerLaptop.setName("Manufacturer");
        manufacturerLaptop.setCategory(laptops);
        optionRepository.save(manufacturerLaptop);

        Option storageLaptop = new Option();
        storageLaptop.setName("Storage");
        storageLaptop.setCategory(laptops);
        optionRepository.save(storageLaptop);

        Value manufacturerValuePhone = new Value();
        manufacturerValuePhone.setName("Apple");
        manufacturerValuePhone.setOption(manufacturer);
        manufacturerValuePhone.setProduct(product);
        valueRepository.save(manufacturerValuePhone);

        Value storageValuePhone = new Value();
        storageValuePhone.setName("128GB");
        storageValuePhone.setOption(storage);
        storageValuePhone.setProduct(product);
        valueRepository.save(storageValuePhone);

        Value manufacturerValueLaptop = new Value();
        manufacturerValueLaptop.setName("Apple");
        manufacturerValueLaptop.setOption(manufacturerLaptop);
        manufacturerValueLaptop.setProduct(product2);
        valueRepository.save(manufacturerValueLaptop);

        Value storageValueLaptop = new Value();
        storageValueLaptop.setName("512GB");
        storageValueLaptop.setOption(storageLaptop);
        storageValueLaptop.setProduct(product2);
        valueRepository.save(storageValueLaptop);

        entityManager.clear();
        assertEquals(2, productRepository.findAllByValuesName("Apple").size());
        assertEquals(1, productRepository.findAllByValuesName("512GB").size());
    }
}
