package ru.practicum.ewm.comment.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.CommentService;
import ru.practicum.ewm.comment.converter.CommentMapper;
import ru.practicum.ewm.comment.model.dto.CommentDto;
import ru.practicum.ewm.configs.SortBy;
import ru.practicum.ewm.event.model.dto.SearchParameters;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.configs.Constants.DATE_FORMAT;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
public class AdminCommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping()
    public Collection<CommentDto> getAllAdmin(@RequestParam(value = "text", required = false) String text,
                                              @RequestParam(value = "users", required = false) List<Long> users,
                                              @RequestParam(value = "events", required = false) List<Long> events,
                                              @RequestParam(value = "rangeStart", required = false) @DateTimeFormat(pattern = DATE_FORMAT)
                                                LocalDateTime rangeStart,
                                              @RequestParam(value = "rangeEnd", required = false) @DateTimeFormat(pattern = DATE_FORMAT)
                                                LocalDateTime rangeEnd,
                                              @RequestParam (value = "from", defaultValue = "0", required = false) @Min(0)  int from,
                                              @RequestParam (value = "size", defaultValue = "10", required = false) @Min(1) int size,
                                              @RequestParam (value = "sort", defaultValue = "CREATION_DATE", required = false) String sortBy) {
        SearchParameters parameters = SearchParameters.builder()
                .text(text)
                .users(users)
                .events(events)
                .rangeStart(rangeStart)
                .rangeEnd(rangeEnd)
                .page(from / size)
                .size(size)
                .sort(SortBy.valueOf(sortBy))
                .build();
        log.info("GET request: запрос комментариев с парметрами {}", parameters);
        return commentService.getAllAdmin(parameters).stream()
                .map(commentMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping()
    public void delete(@RequestParam Long[] ids) {
        log.info("DELETE request: ADMIN удален(ы) комментарий(ии) id={}", Arrays.toString(ids));
        commentService.deleteByAdmin(ids);
    }
}