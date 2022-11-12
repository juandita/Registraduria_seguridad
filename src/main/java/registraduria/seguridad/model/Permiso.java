package registraduria.seguridad.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Permiso {

    @Id
    private String _id;
    private String url;
    private String metodo;

    public Permiso(String url, String metodo) {
        this.url = url;
        this.metodo = metodo;
    }

}