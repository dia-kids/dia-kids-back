package dia.kids.cms.controller;

import dia.kids.cms.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class FileController {
    private final FileService service;
    @GetMapping("/api/pictures/{pictureId}")
    public ResponseEntity<Resource> getPicture(@RequestBody @PathVariable Integer pictureId) throws IOException {
        return service.getPicture(pictureId);
    }
}
