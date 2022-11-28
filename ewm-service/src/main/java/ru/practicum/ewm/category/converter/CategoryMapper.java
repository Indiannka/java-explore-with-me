package ru.practicum.ewm.category.converter;

import org.mapstruct.Mapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.model.dto.CategoryDto;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto convertToDto(Category category);

    Category convert(CategoryDto categoryDto);

}