package dia.kids.cms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dia.kids.cms.controller.ArticleController;
import dia.kids.cms.exception.ArticleNotFoundException;
import dia.kids.cms.model.Article;
import dia.kids.cms.model.ArticleDto;
import dia.kids.cms.service.ArticleService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@WebMvcTest(controllers = ArticleController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ArticleControllerTest {
    @MockBean
    private ArticleService service;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    private final Article article = Article.builder()
                                          .id(1L)
                                          .publishedAt(LocalDateTime.now())
                                          .updatedAt(LocalDateTime.now())
                                          .text("# Lol text")
                                          .build();

    @Test
    public void shouldReturnArticleAndCode200() throws Exception {
        when(service.getArticle(any())).thenReturn(article);

        mvc.perform(get("/api/articles/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(1L))
           .andExpect(jsonPath("$.text").value("# Lol text"));
    }

    @Test
    public void shouldReturnCode404IfArticleNotFound() throws Exception {
        when(service.getArticle(any())).thenThrow(ArticleNotFoundException.class);
        mvc.perform(get("/api/articles/2"))
           .andExpect(status().isNotFound());
    }

    @Test
    public void shouldSaveArticleAndReturnCode200() throws Exception {
        when(service.addArticle(any())).thenReturn(article);

        mvc.perform(post("/api/articles")
                        .content(mapper.writeValueAsString(new ArticleDto("# Lol text")))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(1L))
           .andExpect(jsonPath("$.text").value("# Lol text"));
    }

    @Test
    public void shouldNotSaveArticleIfEmptyAndReturnCode400() throws Exception {
        when(service.addArticle(any())).thenThrow(ValidationException.class);

        mvc.perform(post("/api/articles")
                        .content(mapper.writeValueAsString(new ArticleDto("")))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdateArticleAndReturnCode200() throws Exception {
        when(service.updateArticle(any(), any())).thenReturn(article);

        mvc.perform(put("/api/articles/1")
                        .content(mapper.writeValueAsString(new ArticleDto("# Lol text")))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(1L))
           .andExpect(jsonPath("$.text").value("# Lol text"));
    }

    @Test
    public void shouldReturnCode404IfUpdateNotExistingArticle() throws Exception {
        when(service.updateArticle(any(), any())).thenThrow(ArticleNotFoundException.class);

        mvc.perform(put("/api/articles/2")
                        .content(mapper.writeValueAsString(new ArticleDto("# Lol text")))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnCode400IfUpdateArticleIsIncorrect() throws Exception {
        when(service.updateArticle(any(), any())).thenThrow(ValidationException.class);
        mvc.perform(put("/api/articles/1")
                        .content(mapper.writeValueAsString(new ArticleDto("")))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isBadRequest());
    }
}
