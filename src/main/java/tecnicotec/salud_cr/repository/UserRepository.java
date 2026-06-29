package tecnicotec.salud_cr.repository;


import org.springframework.data.repository.CrudRepository;
import tecnicotec.salud_cr.data.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAllByType(User.Type type);
}

