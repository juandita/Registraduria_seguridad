package registraduria.seguridad.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import registraduria.seguridad.model.Permiso;
import registraduria.seguridad.model.PermisoRol;
import registraduria.seguridad.model.Rol;

import java.util.Optional;

public interface PermisoRolRepository extends MongoRepository<PermisoRol, String> {

    Optional<PermisoRol> findByRolAndPermiso(Rol rol, Permiso permiso);

}

