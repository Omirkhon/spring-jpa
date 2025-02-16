package com.practice.springjpa.dto;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {
    @Id
    int id;
    String name;
    double price;
    String category;
}
