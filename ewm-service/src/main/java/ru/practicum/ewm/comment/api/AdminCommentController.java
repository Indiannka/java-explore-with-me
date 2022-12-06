package ru.practicum.ewm.comment.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.CommentService;
import java.util.Arrays;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/comments")
public class AdminCommentController {

    private final CommentService commentService;

    @DeleteMapping()
    public void delete(@RequestParam Long[] ids) {
        log.info("DELETE request: ADMIN удален(ы) комментарий(ии) id={}", Arrays.toString(ids));
        commentService.deleteByAdmin(ids);
    }
}