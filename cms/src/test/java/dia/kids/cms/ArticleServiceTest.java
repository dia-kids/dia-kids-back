package dia.kids.cms;

import dia.kids.cms.exception.ArticleNotFoundException;
import dia.kids.cms.model.Article;
import dia.kids.cms.model.ArticleDto;
import dia.kids.cms.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ArticleServiceTest {
    private final ArticleService service;

    @Autowired
    public ArticleServiceTest(ArticleService service) {
        this.service = service;
    }

    @Test
    public void saveAndReturnCorrectArticle() {
        ArticleDto articleDto = new ArticleDto();
        String text = "# dia-kids-back\nBackend repo for dia-kids website\n";
        articleDto.setText(text);

        Article saved = service.addArticle(articleDto);
        assertEquals(saved, service.getArticle(saved.getId()));
    }

    @Test
    public void throwsExceptionIfNoArticle() {
        ArticleNotFoundException exception = assertThrows(ArticleNotFoundException.class, () -> service.getArticle(1000L));
        assertEquals("Article not found", exception.getMessage());
    }

    @Test
    public void saveUpdatedArticleAndReturnIt() {
        ArticleDto articleDto = new ArticleDto();
        String text = "# dia-kids-back\nBackend repo for dia-kids website\n";
        articleDto.setText(text);
        Article saved = service.addArticle(articleDto);

        articleDto.setText("# dia-kids-back\nNot anymore backend repo for dia-kids website\n");
        saved = service.updateArticle(articleDto, saved.getId());

        assertEquals(saved, service.getArticle(saved.getId()));
    }

    @Test
    public void throwsExceptionIfUpdateNonExistingArticle() {
        ArticleDto articleDto = new ArticleDto();
        String text = "# dia-kids-back\nBackend repo for dia-kids website\n";
        articleDto.setText(text);

        ArticleNotFoundException exception = assertThrows(ArticleNotFoundException.class, () -> service.updateArticle(articleDto, 1000L));
        assertEquals("Article not found!", exception.getMessage());
    }
}
