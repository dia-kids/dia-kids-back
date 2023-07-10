package dia.kids.cms.controller;

import dia.kids.cms.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ArticleController {
    private final ArticleService service;


}
