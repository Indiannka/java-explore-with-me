package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.converter.CategoryConverter;
import ru.practicum.ewm.category.model.dto.CategoryDto;
import ru.practicum.ewm.exceptions.NotFoundException;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryConverter converter;
    private final CategoryRepository categoryRepository;

    @Override
    public Collection<Category> getAll(int from, int size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        return categoryRepository.findAll(pageable).toList();
    }

    @Override
    public Category getById(long categoryId) {
       return categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException(String.format("Category with id=%d was not found.", categoryId)));
    }

    @Override
    public Category createOrUpdate(CategoryDto categoryDto) {
        Category category = converter.convert(categoryDto);
        return categoryRepository.save(category);
    }

    @Override
    public void delete(long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}