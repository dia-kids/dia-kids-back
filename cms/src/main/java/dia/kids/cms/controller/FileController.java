package dia.kids.cms.controller;

import dia.kids.cms.model.Picture;
import dia.kids.cms.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class FileController {
    private final FileService service;

    @PostMapping("/api/pictures")
    public Picture addPicture(@RequestParam("file") MultipartFile file)
            throws IOException {
        return service.addPicture(file);
    }

    @PutMapping("/api/pictures/{pictureId}")
    public Picture updatePicture(@RequestParam("file") MultipartFile file,
                                 @PathVariable Long pictureId)
            throws IOException {
        return service.updatePicture(file, pictureId);
    }

    @GetMapping("/api/pictures/{pictureId}")
    public ResponseEntity<Resource> getPicture(@PathVariable Long pictureId)
            throws IOException {
        return service.getPicture(pictureId);
    }

    @DeleteMapping("/api/pictures/{pictureId}")
    public void deletePicture(@PathVariable Long pictureId) throws IOException {
        service.deletePicture(pictureId);
    }
}
