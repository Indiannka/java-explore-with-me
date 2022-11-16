package ru.practicum.ewm.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.converter.CategoryConverter;
import ru.practicum.ewm.category.model.dto.CategoryDto;
import ru.practicum.ewm.configs.Create;
import ru.practicum.ewm.configs.Update;

import javax.validation.constraints.Min;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryConverter categoryConverter;

    @GetMapping("/categories")
    public Collection<CategoryDto> getAll(@RequestParam(defaultValue = "0", required = false) @Min(0)  int from,
                                          @RequestParam (defaultValue = "10", required = false) @Min(1) int size) {
        log.info("GET request: запрос всех категорий");
        return categoryService.getAll(from, size).stream()
                .map(categoryConverter::convert)
                .collect(Collectors.toList());
    }

    @GetMapping("/categories/{catId}")
    public CategoryDto getById(@PathVariable("catId") long categoryId) {
        log.info("GET request: запрос категории c id={}", categoryId);
        return categoryConverter.convert(categoryService.getById(categoryId));
    }

    @PostMapping("/admin/categories")
    public CategoryDto create(@Validated({Create.class}) @RequestBody CategoryDto categoryDto) {
        log.info("POST request: создание категории:{}", categoryDto);
        return categoryConverter.convert(categoryService.createOrUpdate(categoryDto));
    }

    @PatchMapping("/admin/categories")
    public CategoryDto update(@Validated({Update.class}) @RequestBody CategoryDto categoryDto) {
        log.info("PATCH request: обновление категории:{}", categoryDto);
        return categoryConverter.convert(categoryService.createOrUpdate(categoryDto));
    }

    @DeleteMapping("/admin/categories/{catId}")
    public void delete(@PathVariable("catId") long categoryId) {
        log.info("DELETE request: удаление категории с id={}", categoryId);
        categoryService.delete(categoryId);
    }
}