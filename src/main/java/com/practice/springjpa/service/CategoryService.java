package com.practice.springjpa.service;

import com.practice.springjpa.model.Category;
import com.practice.springjpa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category create(Category category) {
        if (category.getName().isBlank()) {
            throw new RuntimeException("Название категорий не должно быть пустым");
        }

        Optional<Category> optional = categoryRepository.findByName(category.getName());
        if (optional.isPresent()) {
            throw new RuntimeException("Категория с таким названием уже существует");
        }

        categoryRepository.save(category);
        return category;
    }

    public void deleteById(int categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public Category findById(int categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow();
    }
}
