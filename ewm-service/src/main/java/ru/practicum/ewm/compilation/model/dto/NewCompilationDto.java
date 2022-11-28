package ru.practicum.ewm.compilation.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.configs.Create;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class NewCompilationDto {

    private Set<Long> events;

    @NotNull(message = "Поле title не должно быть пустым", groups = {Create.class})
    private String title;

    private Boolean pinned;
}
