package dia.kids.cms.service;

import dia.kids.cms.repository.PictureRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FileService {
    private final PictureRepository repository;
}
