package dia.kids.cms.controller;

import dia.kids.cms.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class FileController {
    private final FileService service;
}
