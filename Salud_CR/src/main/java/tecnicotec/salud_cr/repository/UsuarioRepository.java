package tecnicotec.salud_cr.repository;


import tecnicotec.salud_cr.entity.Usuario;
import  java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    Usuario save(Usuario usuario);
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findAll();
    Usuario update(Usuario usuario);
    boolean delete(Long id);
}
