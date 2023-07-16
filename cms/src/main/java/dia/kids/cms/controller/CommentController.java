package dia.kids.cms.controller;

import dia.kids.cms.model.Comment;
import dia.kids.cms.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CommentController {
    private final CommentService service;

    @GetMapping("/api/comments/{commentId}")
    public Comment getComment(@RequestBody @PathVariable Integer commentId) {
        return service.getComment(commentId);
    }
    @GetMapping("/api/commentsbyarticle/{articleId}")
    public List<Comment> getComments(@RequestBody @PathVariable Integer articleId) {
        return service.getAllByArticleID(articleId);
    }
}
