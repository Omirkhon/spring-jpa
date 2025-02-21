package com.practice.springjpa.mapper;

import com.practice.springjpa.dto.CategoryDto;
import com.practice.springjpa.dto.CategoryDto.*;
import com.practice.springjpa.model.Category;
import com.practice.springjpa.model.Option;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {
    public List<CategoryDto> toDto(List<Category> categories) {
        return categories.stream()
                .map(this::toDto)
                .toList();
    }

    public CategoryDto toDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setOptions(toOptionDto(category.getOptions()));
        return categoryDto;
    }

    private List<OptionForCategoryDto> toOptionDto(List<Option> options) {
        return options.stream()
                .map(this::toOptionDto)
                .toList();
    }

    private OptionForCategoryDto toOptionDto(Option option) {
        OptionForCategoryDto optionDto = new OptionForCategoryDto();
        optionDto.setId(option.getId());
        optionDto.setName(option.getName());
        return optionDto;
    }

    // Option - > OptionForCategoryDto
}
