package tecnicotec.salud_cr.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import tecnicotec.salud_cr.data.User;

import java.util.Optional;


/**
 * Repository interface for User-related operations.
 * Implementations will be provided later (in-memory, JPA, etc.).
 */
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT * FROM users WHERE user_name = :username")
    Optional<User> findByUsername(String username);

    @Query("SELECT * FROM users WHERE email = :email")
    Optional<User> findByEmail(String email);
}

