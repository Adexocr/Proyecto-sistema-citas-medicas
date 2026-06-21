package tecnicotec.salud_cr.repository;


import org.springframework.data.repository.CrudRepository;
import tecnicotec.salud_cr.data.User;

/**
 * Repository interface for User-related operations.
 * Implementations will be provided later (in-memory, JPA, etc.).
 */
public interface UserRepository extends CrudRepository<User, Long> {}
