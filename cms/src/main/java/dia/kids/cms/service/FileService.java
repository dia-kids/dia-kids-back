package dia.kids.cms.service;

import dia.kids.cms.exception.PictureNotFoundException;
import dia.kids.cms.model.Picture;
import dia.kids.cms.repository.PictureRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class FileService {
    private final PictureRepository repository;
    public ResponseEntity<Resource> getPicture(Integer id) throws IOException {
        Picture picture = repository.findById(id).orElseThrow(() -> new PictureNotFoundException("Picture not found"));
        String name = picture.getName();
        File file = new File("pictures\\" + name);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
