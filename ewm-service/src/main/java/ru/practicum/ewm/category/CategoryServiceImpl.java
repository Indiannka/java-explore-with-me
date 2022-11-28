package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.converter.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.model.dto.CategoryDto;
import ru.practicum.ewm.exceptions.NotFoundException;

import java.util.Collection;

import static ru.practicum.ewm.configs.Constants.CATEGORY;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper converter;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public Collection<Category> getAll(int from, int size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        return categoryRepository.findAll(pageable).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Category getById(long categoryId) {
       return categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException(CATEGORY, categoryId));
    }

    @Override
    @Transactional
    public Category createOrUpdate(CategoryDto categoryDto) {
        Category category = converter.convert(categoryDto);
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void delete(long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}