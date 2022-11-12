package registraduria.seguridad.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class PermisoRol {

    @Id
    private String _id;

    @DBRef
    private Rol rol;

    @DBRef
    private Permiso permiso;

}