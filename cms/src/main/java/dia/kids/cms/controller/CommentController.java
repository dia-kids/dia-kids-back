package dia.kids.cms.controller;

import dia.kids.cms.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CommentController {
    private final CommentService service;
}
