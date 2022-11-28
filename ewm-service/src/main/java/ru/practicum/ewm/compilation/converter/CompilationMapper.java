package ru.practicum.ewm.compilation.converter;

import org.mapstruct.Mapper;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.model.dto.CompilationDto;
import ru.practicum.ewm.compilation.model.dto.NewCompilationDto;
import ru.practicum.ewm.configs.EntityMapper;


@Mapper(componentModel = "spring", uses = {EntityMapper.class})
public interface CompilationMapper {

    Compilation convert(NewCompilationDto dto);

    CompilationDto convertToDto(Compilation compilation);
}
