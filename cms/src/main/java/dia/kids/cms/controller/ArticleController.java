package dia.kids.cms.controller;

import dia.kids.cms.model.Article;
import dia.kids.cms.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ArticleController {
    private final ArticleService service;
    @GetMapping("/api/articles/{articleId}")
    public Article getArticle(@RequestBody @PathVariable Integer articleId) {
        return service.getArticle(articleId);
    }

}
