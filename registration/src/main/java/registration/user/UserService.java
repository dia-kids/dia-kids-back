package registration.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<User> findUserById(int id) {
        try {
            var user = userRepository.findById((long) id).orElseThrow();
            return ResponseEntity.ok(user);
        } catch (NoSuchElementException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
