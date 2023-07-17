package dia.kids.cms;

import com.fasterxml.jackson.databind.ObjectMapper;
import dia.kids.cms.controller.CommentController;
import dia.kids.cms.exception.ArticleNotFoundException;
import dia.kids.cms.exception.CommentNotFoundException;
import dia.kids.cms.exception.InvalidCommentException;
import dia.kids.cms.model.Comment;
import dia.kids.cms.model.CommentDto;
import dia.kids.cms.service.CommentService;
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
import java.util.List;

@WebMvcTest(controllers = CommentController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CommentControllerTest {
    @MockBean
    private CommentService service;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    private final Comment comment = Comment.builder()
                                          .id(1L)
                                          .userId(1L)
                                          .articleId(1L)
                                          .postedAt(LocalDateTime.now())
                                          .updatedAt(LocalDateTime.now())
                                          .text("lol text")
                                          .build();

    @Test
    public void shouldReturnCode200AndComment() throws Exception {
        when(service.getComment(any())).thenReturn(comment);

        mvc.perform(get("/api/comments/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(1L))
           .andExpect(jsonPath("$.userId").value(1L))
           .andExpect(jsonPath("$.articleId").value(1L))
           .andExpect(jsonPath("$.text").value("lol text"));
    }

    @Test
    public void shouldReturnCode404IfCommentNotFound() throws Exception {
        when(service.getComment(any())).thenThrow(CommentNotFoundException.class);
        mvc.perform(get("/api/comments/2"))
           .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn200AndListOfComments() throws Exception {
        when(service.getAllByArticleID(any())).thenReturn(List.of(comment));

        mvc.perform(get("/api/commentsbyarticle/1"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$[0].id").value(1L))
           .andExpect(jsonPath("$[0].userId").value(1L))
           .andExpect(jsonPath("$[0].articleId").value(1L))
           .andExpect(jsonPath("$[0].text").value("lol text"));
    }

    @Test
    public void shouldReturnCode404IfArticleNotFound() throws Exception {
        when(service.getAllByArticleID(any())).thenThrow(ArticleNotFoundException.class);

        mvc.perform(get("/api/commentsbyarticle/2"))
           .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnCode200WhenPublishComment() throws Exception {
        when(service.publishComment(any())).thenReturn(comment);

        mvc.perform(post("/api/comments")
                        .content(mapper.writeValueAsString(new CommentDto()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(1L))
           .andExpect(jsonPath("$.userId").value(1L))
           .andExpect(jsonPath("$.articleId").value(1L))
           .andExpect(jsonPath("$.text").value("lol text"));
    }

    @Test
    public void shouldReturnCode400IfIncorrectComment() throws Exception {
        when(service.publishComment(any())).thenThrow(ValidationException.class);

        mvc.perform(post("/api/comments")
                        .content(mapper.writeValueAsString(new CommentDto()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnCode404IfArticleNotFoundForComment() throws Exception {
        when(service.publishComment(any())).thenThrow(ArticleNotFoundException.class);

        mvc.perform(post("/api/comments")
                        .content(mapper.writeValueAsString(new CommentDto()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnCode200WhenUpdateComment() throws Exception {
        when(service.updateComment(any(), any())).thenReturn(comment);

        mvc.perform(put("/api/comments/1")
                        .content(mapper.writeValueAsString(new CommentDto()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id").value(1L))
           .andExpect(jsonPath("$.userId").value(1L))
           .andExpect(jsonPath("$.articleId").value(1L))
           .andExpect(jsonPath("$.text").value("lol text"));
    }

    @Test
    public void shouldReturnCode400IfIncorrectUpdatedComment() throws Exception {
        when(service.updateComment(any(), any())).thenThrow(ValidationException.class);

        mvc.perform(put("/api/comments/1")
                        .content(mapper.writeValueAsString(new CommentDto()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnCode404IfArticleNotFoundForUpdatedComment() throws Exception {
        when(service.updateComment(any(), any())).thenThrow(InvalidCommentException.class);

        mvc.perform(put("/api/comments/1")
                        .content(mapper.writeValueAsString(new CommentDto()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isBadRequest());
    }
}
