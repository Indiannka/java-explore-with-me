package ru.practicum.ewm.category.converter;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.model.dto.CategoryDto;

@Component
public class CategoryConverter {

    public CategoryDto convert(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Category convert(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }
}
