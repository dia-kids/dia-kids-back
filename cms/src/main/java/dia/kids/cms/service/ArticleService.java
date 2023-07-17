package dia.kids.cms.service;

import dia.kids.cms.exception.ArticleNotFoundException;
import dia.kids.cms.model.Article;
import dia.kids.cms.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleService {
    private final ArticleRepository repository;

    public Article getArticle(Long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new ArticleNotFoundException("Article not found"));
    }
}
