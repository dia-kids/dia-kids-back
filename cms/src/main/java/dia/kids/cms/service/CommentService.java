package dia.kids.cms.service;

import dia.kids.cms.exception.CommentNotFoundException;
import dia.kids.cms.model.Comment;
import dia.kids.cms.repository.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository repository;
    public Comment getComment(Integer id) {
        return repository.findById(id).orElseThrow(() -> new CommentNotFoundException("Comment not found"));
    }
    public List<Comment> getAllByArticleID(Integer id) {
        return repository.findAllByArticleId(id);
    }
}
