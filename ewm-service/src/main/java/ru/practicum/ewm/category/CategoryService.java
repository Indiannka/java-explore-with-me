package ru.practicum.ewm.category;

import org.springframework.data.domain.Pageable;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.model.dto.CategoryDto;

import java.util.Collection;

public interface CategoryService {

    Collection<Category> getAll(Pageable pageable);

    Category getById(long categoryId);

    Category createOrUpdate(CategoryDto categoryDto);

    void delete(long categoryId);
}
