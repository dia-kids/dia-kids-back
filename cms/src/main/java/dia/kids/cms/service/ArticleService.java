package dia.kids.cms.service;

import dia.kids.cms.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleService {
    private final ArticleRepository repository;
}
