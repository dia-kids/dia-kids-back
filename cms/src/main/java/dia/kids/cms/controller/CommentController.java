package dia.kids.cms.controller;

import dia.kids.cms.model.Comment;
import dia.kids.cms.model.CommentDto;
import dia.kids.cms.service.CommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CommentController {
    private final CommentService service;

    @PutMapping("/api/comments/{commentId}")
    public Comment updateComment(@RequestBody @Valid CommentDto commentDto,
                                 @PathVariable Long commentId) {
        return service.updateComment(commentDto, commentId);
    }

    @PostMapping("/api/comments")
    public Comment publishComment(@RequestBody @Valid CommentDto commentDto) {
        return service.publishComment(commentDto);
    }

    @GetMapping("/api/comments/{commentId}")
    public Comment getComment(@RequestBody @PathVariable Long commentId) {
        return service.getComment(commentId);
    }

    @GetMapping("/api/commentsbyarticle/{articleId}")
    public List<Comment> getComments(@RequestBody @PathVariable Long articleId) {
        return service.getAllByArticleID(articleId);
    }
}
