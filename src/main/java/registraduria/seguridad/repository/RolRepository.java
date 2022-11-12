package registraduria.seguridad.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import registraduria.seguridad.model.Rol;

public interface RolRepository extends MongoRepository<Rol, String> {
}