package com.practice.springjpa.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CategoryCreateDto {
    String name;
    List<String> options = new ArrayList<>();
}
