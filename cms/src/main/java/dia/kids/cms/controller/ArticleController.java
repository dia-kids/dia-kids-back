package dia.kids.cms.controller;

import dia.kids.cms.model.Article;
import dia.kids.cms.model.ArticleDto;
import dia.kids.cms.service.ArticleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class ArticleController {
    private final ArticleService service;

    @PostMapping("/api/articles")
    public Article addArticle(@RequestBody @Valid ArticleDto article) {
        return service.addArticle(article);
    }

    @PutMapping("/api/articles/{articleId}")
    public Article updateArticle(@RequestBody @Valid ArticleDto article, @PathVariable Long articleId) {
        return service.updateArticle(article, articleId);
    }

    @GetMapping("/api/articles/{articleId}")
    public Article getArticle(@RequestBody @PathVariable Long articleId) {
        return service.getArticle(articleId);
    }

    @DeleteMapping("/api/articles/{articleId}")
    public void deleteArticle(@PathVariable Long articleId) {
        service.deleteArticle(articleId);
    }
}
