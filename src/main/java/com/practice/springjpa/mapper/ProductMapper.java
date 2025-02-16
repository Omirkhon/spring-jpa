package com.practice.springjpa.mapper;

import com.practice.springjpa.dto.ProductDto;
import com.practice.springjpa.dto.ProductFullDto;
import com.practice.springjpa.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {
    public ProductFullDto toFullDto(Product product) {
        ProductFullDto productFullDto = new ProductFullDto();
        productFullDto.setId(product.getId());
        productFullDto.setName(product.getName());
        productFullDto.setPrice(product.getPrice());
        productFullDto.setCategory(product.getCategory().getName());
        productFullDto.setValues(product.getValues());

        return productFullDto;
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
