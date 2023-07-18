package dia.kids.cms;

import dia.kids.cms.exception.PictureNotFoundException;
import dia.kids.cms.model.Picture;
import dia.kids.cms.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FileServiceTest {
    private final FileService service;

    @Autowired
    public FileServiceTest(FileService service) {
        this.service = service;
    }

    @Test
    public void shouldUploadFileOnServer() throws IOException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.png");
        MockMultipartFile file = new MockMultipartFile("test.png", "test.png", "image/png", inputStream);

        Picture picture = service.addPicture(file);
        ResponseEntity<Resource> entity = service.getPicture(picture.getId());
        byte[] downloaded = Objects.requireNonNull(entity.getBody()).getContentAsByteArray();
        assertArrayEquals(file.getBytes(), downloaded);
    }

    @Test
    public void shouldThrowExceptionIfPictureNotFound() {
        PictureNotFoundException exception = assertThrows(PictureNotFoundException.class, () -> service.getPicture(1000L));
        assertEquals("Picture not found!", exception.getMessage());
    }

    @Test
    public void shouldUpdateExistingPicture() throws IOException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.png");
        MockMultipartFile file = new MockMultipartFile("test.png", "test.png", "image/png", inputStream);

        Picture picture = service.addPicture(file);

        InputStream newInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample.png");
        MockMultipartFile newFile = new MockMultipartFile("sample.png", "sample.png", "image/png", newInputStream);
        Picture newPicture = service.updatePicture(newFile, picture.getId());

        ResponseEntity<Resource> entity = service.getPicture(newPicture.getId());
        byte[] downloaded = Objects.requireNonNull(entity.getBody()).getContentAsByteArray();
        assertArrayEquals(newFile.getBytes(), downloaded);
    }

    @Test
    public void shouldThrowExceptionWhenUpdateNotExistingPicture() throws IOException {
        InputStream newInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sample.png");
        MockMultipartFile newFile = new MockMultipartFile("sample.png", "sample.png", "image/png", newInputStream);
        PictureNotFoundException exception = assertThrows(PictureNotFoundException.class, () -> service.updatePicture(newFile, 1000L));
        assertEquals("Picture not found!", exception.getMessage());
    }

    @Test
    public void shouldDeletePictureIfContains() throws IOException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.png");
        MockMultipartFile file = new MockMultipartFile("test.png", "test.png", "image/png", inputStream);

        Picture picture = service.addPicture(file);
        ResponseEntity<Resource> entity = service.getPicture(picture.getId());
        byte[] downloaded = Objects.requireNonNull(entity.getBody()).getContentAsByteArray();
        assertArrayEquals(file.getBytes(), downloaded);

        service.deletePicture(picture.getId());
        assertThrows(PictureNotFoundException.class, () -> service.getPicture(picture.getId()));
    }

    @Test
    public void shouldThrowExceptionIfDeleteNotExistingPicture() {
        assertThrows(PictureNotFoundException.class, () -> service.deletePicture(1000L));
    }
}
