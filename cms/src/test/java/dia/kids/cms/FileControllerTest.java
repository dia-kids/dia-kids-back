package dia.kids.cms;

import dia.kids.cms.controller.FileController;
import dia.kids.cms.exception.PictureNotFoundException;
import dia.kids.cms.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FileController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FileControllerTest {
    @MockBean
    private FileService service;
    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldReturnCode200AndPicture() throws Exception {
        byte[] array = {1, 2, 3, 4, 5, 6, 7, 8};
        ByteArrayResource resource = new ByteArrayResource(array);

        when(service.getPicture(any())).thenReturn(ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource));
        mvc.perform(get("/api/pictures/1").accept(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnCode404IfPictureNotFound() throws Exception {
        when(service.getPicture(any())).thenThrow(PictureNotFoundException.class);

        mvc.perform(get("/api/pictures/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnCode200WhenDeletePicture() throws Exception {
        doNothing().when(service).deletePicture(any());

        mvc.perform(delete("/api/pictures/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnCode404WhenDeleteNotExistingPicture() throws Exception {
        doThrow(PictureNotFoundException.class).when(service).deletePicture(any());

        mvc.perform(delete("/api/pictures/1"))
                .andExpect(status().isNotFound());
    }
}
