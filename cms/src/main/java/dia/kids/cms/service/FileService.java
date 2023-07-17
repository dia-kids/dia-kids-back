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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
@AllArgsConstructor
public class FileService {
    private final PictureRepository repository;

    private void saveFile(MultipartFile file) throws IOException {
        String fileName = Objects.requireNonNull(file.getOriginalFilename());
        Path targetLocation = Paths.get("pictures/" + fileName);
        Files.copy(file.getInputStream(),  targetLocation, StandardCopyOption.REPLACE_EXISTING);
    }

    public Picture addPicture(MultipartFile file) throws IOException {
        Picture picture = new Picture();
        saveFile(file);
        picture.setName(file.getOriginalFilename());
        return repository.save(picture);
    }

    public Picture updatePicture(MultipartFile file, Long pictureId) throws IOException {
        Picture picture = repository.findById(pictureId)
                                    .orElseThrow(() -> new PictureNotFoundException("Picture not found!"));
        saveFile(file);
        picture.setName(file.getOriginalFilename());
        return repository.save(picture);
    }

    public ResponseEntity<Resource> getPicture(Long id) throws IOException {
        Picture picture = repository.findById(id)
                                    .orElseThrow(() -> new PictureNotFoundException("Picture not found"));

        String name = picture.getName();
        File file = new File("pictures/" + name);

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
