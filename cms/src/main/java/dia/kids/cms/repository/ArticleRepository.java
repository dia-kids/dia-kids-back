package dia.kids.cms.repository;

import dia.kids.cms.model.Article;
import dia.kids.cms.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
