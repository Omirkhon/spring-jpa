package com.practice.springjpa.mapper;

import com.practice.springjpa.dto.ProductDto;
import com.practice.springjpa.dto.ProductFullDto;
import com.practice.springjpa.model.Product;
import com.practice.springjpa.model.Value;
import com.practice.springjpa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    public ProductFullDto toFullDto(Product product) {
        ProductFullDto productFullDto = new ProductFullDto();
        productFullDto.setId(product.getId());
        productFullDto.setName(product.getName());
        productFullDto.setPrice(product.getPrice());
        productFullDto.setCategory(product.getCategory().getName());

        Map<String, String> options = new HashMap<>();

        for (Value value : product.getValues()) {
            options.put(value.getOption().getName(), value.getName());
        }
        productFullDto.setOptions(options);

        return productFullDto;
    }

    public Product fromDto(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());

        return product;
    }

    public ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setCategory(product.getCategory().getName());

        return productDto;
    }

    public List<ProductDto> toDto(List<Product> products) {
        return products.stream()
                .map(this::toDto)
                .toList();
    }

    public List<ProductFullDto> toFullDto(List<Product> products) {
        return products.stream()
                .map(this::toFullDto)
                .toList();
    }
}
