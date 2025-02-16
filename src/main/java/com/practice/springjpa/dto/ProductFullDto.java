package com.practice.springjpa.dto;

import com.practice.springjpa.model.Value;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductFullDto {
    @Id
    int id;
    String name;
    double price;
    String category;
    List<Value> values;
}
