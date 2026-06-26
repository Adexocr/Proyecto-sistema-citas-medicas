package tecnicotec.salud_cr.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tecnicotec.salud_cr.data.User;
import tecnicotec.salud_cr.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // Registro para un nuevo usuario

    public User register(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException(" El nombre de usuario ya existe");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException(" El correo electrónico ya esta en uso");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setType(User.Type.REGULAR);
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);

    }

    // Busqueda por nombre de usuario
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);

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
                .orElseThrow(() -> new IllegalArgumentException(" El usuario no existe"));
        user.setType(newType);
        return userRepository.save(user);
    }

    // Eliminar usuario (para admin)
    public void deleteById(Long id) {
        if  (!userRepository.existsById(id)) {
            throw new IllegalArgumentException(" El usuario no existe");
        }
        userRepository.deleteById(id);
        }
    }

