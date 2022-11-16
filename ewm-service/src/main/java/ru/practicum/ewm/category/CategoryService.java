package ru.practicum.ewm.category;

import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.model.dto.CategoryDto;

import java.util.Collection;

public interface CategoryService {

    Collection<Category> getAll(int from, int size);

    Category getById(long categoryId);

    Category createOrUpdate(CategoryDto categoryDto);

    void delete(long categoryId);
}
