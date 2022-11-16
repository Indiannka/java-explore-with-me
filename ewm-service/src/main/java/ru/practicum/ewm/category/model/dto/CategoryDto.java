package ru.practicum.ewm.category.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.practicum.ewm.configs.Create;
import ru.practicum.ewm.configs.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@EqualsAndHashCode
@Builder(toBuilder = true)
@AllArgsConstructor
public class CategoryDto {
    @NotNull(groups = {Update.class})
    private Long id;

    @NotBlank(message = "Поле name не должно быть пустым", groups = {Create.class})
    @NotBlank(message = "Поле name не должно быть пустым", groups = {Update.class})
    private String name;
}