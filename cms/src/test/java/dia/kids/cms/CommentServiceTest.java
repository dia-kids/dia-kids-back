package dia.kids.cms;

import dia.kids.cms.exception.ArticleNotFoundException;
import dia.kids.cms.exception.CommentNotFoundException;
import dia.kids.cms.exception.InvalidCommentException;
import dia.kids.cms.model.Article;
import dia.kids.cms.model.ArticleDto;
import dia.kids.cms.model.Comment;
import dia.kids.cms.model.CommentDto;
import dia.kids.cms.service.ArticleService;
import dia.kids.cms.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CommentServiceTest {
    private final ArticleService articleService;
    private final CommentService commentService;
    private static Article article;

    @Autowired
    public CommentServiceTest(ArticleService articleService, CommentService commentService) {
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @BeforeEach
    public void makeArticleForTests() {
        ArticleDto articleDto = new ArticleDto();
        String text = "# dia-kids-back\nBackend repo for dia-kids website\n";
        articleDto.setText(text);

        article = articleService.addArticle(articleDto);
    }

    @Test
    public void shouldSaveAndReturnComment() {
        CommentDto commentDto = new CommentDto();
        commentDto.setUserId(1000L);
        commentDto.setArticleId(article.getId());
        commentDto.setText("some text");

        Comment saved = commentService.publishComment(commentDto);
        assertEquals(saved, commentService.getComment(saved.getId()));
    }

    @Test
    public void shouldThrowExceptionIfArticleNotFound() {
        CommentDto commentDto = new CommentDto();
        commentDto.setUserId(1000L);
        commentDto.setArticleId(1000L);
        commentDto.setText("some text");

        ArticleNotFoundException exception = assertThrows(ArticleNotFoundException.class, () -> commentService.publishComment(commentDto));
        assertEquals("Article not found!", exception.getMessage());
    }

    @Test
    public void shouldUpdateExistingComment() {
        CommentDto commentDto = new CommentDto();
        commentDto.setUserId(1000L);
        commentDto.setArticleId(article.getId());
        commentDto.setText("some text");

        Comment saved = commentService.publishComment(commentDto);
        commentDto.setText("new text");
        saved = commentService.updateComment(commentDto, saved.getId());

        assertEquals(saved, commentService.getComment(saved.getId()));
    }

    @Test
    public void shouldThrowExceptionIfUpdateCommentWithWrongArticleId() {
        CommentDto commentDto = new CommentDto();
        commentDto.setUserId(1000L);
        commentDto.setArticleId(article.getId());
        commentDto.setText("some text");

        Comment saved = commentService.publishComment(commentDto);
        commentDto.setText("new text");
        commentDto.setArticleId(1000L);
        InvalidCommentException exception = assertThrows(InvalidCommentException.class, () -> {
            commentService.updateComment(commentDto, saved.getId());
        });
        assertEquals("Article id not equals to existing one!", exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenUpdateNotExistingComment() {
        CommentDto commentDto = new CommentDto();
        commentDto.setUserId(1000L);
        commentDto.setArticleId(article.getId());
        commentDto.setText("some text");

        CommentNotFoundException exception = assertThrows(CommentNotFoundException.class, () -> {
            commentService.updateComment(commentDto, 1000L);
        });
        assertEquals("Comment not found!", exception.getMessage());
    }

    @Test
    public void shouldReturnListOfComments() {
        CommentDto commentDto = new CommentDto();
        commentDto.setUserId(1000L);
        commentDto.setArticleId(article.getId());
        commentDto.setText("some text");

        Comment first = commentService.publishComment(commentDto);
        Comment second = commentService.publishComment(commentDto);
        List<Comment> comments = commentService.getAllByArticleID(article.getId());

        assertEquals(2, comments.size());
        assertEquals(first, comments.get(0));
        assertEquals(second, comments.get(1));
    }

    @Test
    public void shouldThrowExceptionIfCommentsByArticleNotFound() {
        ArticleNotFoundException exception = assertThrows(ArticleNotFoundException.class, () -> {
            commentService.getAllByArticleID(1000L);
        });
        assertEquals("Article not found!", exception.getMessage());
    }
}
