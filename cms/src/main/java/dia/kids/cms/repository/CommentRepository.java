package dia.kids.cms.repository;

import dia.kids.cms.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByArticleId(Integer articleId);
}
