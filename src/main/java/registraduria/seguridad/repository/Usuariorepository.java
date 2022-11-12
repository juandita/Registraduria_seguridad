package registraduria.seguridad.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import registraduria.seguridad.model.Usuario;

import java.util.Optional;

public interface Usuariorepository extends MongoRepository<Usuario, String> {

    Optional<Usuario> findByCorreoAndContrasena(String correo, String contrasena);

}
