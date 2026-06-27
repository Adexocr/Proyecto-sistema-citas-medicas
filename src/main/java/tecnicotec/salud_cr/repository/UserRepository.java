package tecnicotec.salud_cr.repository;


import org.springframework.data.repository.CrudRepository;
import tecnicotec.salud_cr.data.User;

import java.util.Optional;


/**
 * Repository interface for User-related operations.
 * Implementations will be provided later (in-memory, JPA, etc.).
 */
public interface UserRepository extends CrudRepository<User, Long> {


    Optional<User> findByUsername(String username);


    Optional<User> findByEmail(String email);
}

