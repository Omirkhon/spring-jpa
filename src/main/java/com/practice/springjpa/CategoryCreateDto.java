package com.practice.springjpa;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryCreateDto {
    String name;
    String[] options;
}
