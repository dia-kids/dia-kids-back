package dia.kids.cms.service;

import dia.kids.cms.exception.CommentNotFoundException;
import dia.kids.cms.model.Comment;
import dia.kids.cms.model.CommentDto;
import dia.kids.cms.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository repository;

    public Comment updateComment(CommentDto commentDto, Long id) {
        Comment comment = repository.findById(id)
                                    .orElseThrow(() -> new CommentNotFoundException("Comment not found!"));
        comment.setUpdatedAt(LocalDateTime.now());
        comment.setText(commentDto.getText());
        return repository.save(comment);
    }

    public Comment publishComment(CommentDto commentDto) {
        LocalDateTime now = LocalDateTime.now();
        Comment comment = Comment.builder()
                                 .articleId(commentDto.getArticleId())
                                 .postedAt(now)
                                 .updatedAt(now)
                                 .text(commentDto.getText())
                                 .build();
        return repository.save(comment);
    }

    public Comment getComment(Long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new CommentNotFoundException("Comment not found"));
    }

    public List<Comment> getAllByArticleID(Long id) {
        return repository.findAllByArticleId(id);
    }
}
