package dia.kids.cms.service;

import dia.kids.cms.exception.ArticleNotFoundException;
import dia.kids.cms.exception.CommentNotFoundException;
import dia.kids.cms.exception.InvalidCommentException;
import dia.kids.cms.model.Article;
import dia.kids.cms.model.Comment;
import dia.kids.cms.model.CommentDto;
import dia.kids.cms.repository.ArticleRepository;
import dia.kids.cms.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository repository;
    private final ArticleRepository articleRepository;

    public Comment updateComment(CommentDto commentDto, Long id) {
        Comment comment = repository.findById(id)
                                    .orElseThrow(() -> new CommentNotFoundException("Comment not found!"));
        if (!Objects.equals(commentDto.getArticleId(), comment.getArticleId())) {
            throw new InvalidCommentException("Article id not equals to existing one!");
        }

        comment.setUpdatedAt(LocalDateTime.now());
        comment.setText(commentDto.getText());
        return repository.save(comment);
    }

    public Comment publishComment(CommentDto commentDto) {
        LocalDateTime now = LocalDateTime.now();
        Article article = articleRepository.findById(commentDto.getArticleId())
                                           .orElseThrow(() -> new ArticleNotFoundException("Article not found!"));

        Comment comment = Comment.builder()
                                 .articleId(article.getId())
                                 .postedAt(now)
                                 .updatedAt(now)
                                 .userId(commentDto.getUserId())
                                 .text(commentDto.getText())
                                 .build();
        return repository.save(comment);
    }

    public Comment getComment(Long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
    }

    public List<Comment> getAllByArticleID(Long id) {
        Article article = articleRepository.findById(id)
                                           .orElseThrow(() -> new ArticleNotFoundException("Article not found!"));
        return repository.findAllByArticleId(article.getId());
    }
}
