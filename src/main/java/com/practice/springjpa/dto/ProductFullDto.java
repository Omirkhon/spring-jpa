package com.practice.springjpa.dto;

import com.practice.springjpa.model.Value;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductFullDto {
    int id;
    String name;
    double price;
    String category;
    Map<String, String> options = new HashMap<>();
}
