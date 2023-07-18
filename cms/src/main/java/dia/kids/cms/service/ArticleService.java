package dia.kids.cms.service;

import dia.kids.cms.exception.ArticleNotFoundException;
import dia.kids.cms.model.Article;
import dia.kids.cms.model.ArticleDto;
import dia.kids.cms.repository.ArticleRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleService {
    private final ArticleRepository repository;

    public Article addArticle(ArticleDto articleDto) {
        Article article = new Article();

        article.setText(articleDto.getText());
        LocalDateTime now = LocalDateTime.now();
        article.setPublishedAt(now);
        article.setUpdatedAt(now);

        return repository.save(article);
    }

    public Article updateArticle(ArticleDto articleDto, Long articleId) {
        Article article = repository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found!"));
        article.setText(articleDto.getText());
        article.setUpdatedAt(LocalDateTime.now());

        return repository.save(article);
    }

    public Article getArticle(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found"));
    }

    public void deleteArticle(Long articleId) {
        Article article = repository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found!"));
        repository.deleteById(article.getId());
    }
}
