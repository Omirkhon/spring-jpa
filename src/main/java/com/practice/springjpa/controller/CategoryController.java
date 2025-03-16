package com.practice.springjpa.controller;

import com.practice.springjpa.dto.CategoryCreateDto;
import com.practice.springjpa.dto.CategoryDto;
import com.practice.springjpa.mapper.CategoryMapper;
import com.practice.springjpa.model.Category;
import com.practice.springjpa.model.Option;
import com.practice.springjpa.repository.CategoryRepository;
import com.practice.springjpa.repository.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final OptionRepository optionRepository;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @GetMapping("{id}")
    public Category findById(@PathVariable int id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@RequestBody CategoryCreateDto categoryCreateDto) {
        List<Option> options = new ArrayList<>();

        Category category = new Category();
        category.setName(categoryCreateDto.getName());
        categoryRepository.save(category);


        for (String string : categoryCreateDto.getOptions()) {
            Option option = new Option();
            option.setName(string);
            options.add(option);
            option.setCategory(category);
        }

        category.getOptions().addAll(options);

        optionRepository.saveAll(options);
        return categoryMapper.toDto(category);
    }

    @PutMapping("{id}")
    public Category update(@PathVariable int id, @RequestBody Category category) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow();
        existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable int id) {
        categoryRepository.deleteById(id);
    }
}
