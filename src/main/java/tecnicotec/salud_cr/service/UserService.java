package tecnicotec.salud_cr.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tecnicotec.salud_cr.data.User;
import tecnicotec.salud_cr.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> authenticate(String username) {
        return userRepository.findByUsername(username);
    }

    public User register(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setType(User.Type.REGULAR);
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    // Busqueda por id
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // Listar todos los usuarios (para admin)
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    // Actualizar el tipo/rol de usuario (solo admin)
    public User updateType(Long id, User.Type newType) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Username does not exist"));
        user.setType(newType);
        return userRepository.save(user);
    }

    // Eliminar usuario (para admin)
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User does not exist");
        }
        userRepository.deleteById(id);
    }

    public List<User> findAllByType(User.Type type) {
        return userRepository.findAllByType(type);
    }
}

