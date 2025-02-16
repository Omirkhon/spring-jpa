package com.practice.springjpa.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    int price;
    @ManyToOne
    Category category;

    @OneToMany(mappedBy = "product")
    final List<Value> values = new ArrayList<>();

    public void addValue(Value value) {
        this.values.add(value);
    }
}
