package registraduria.seguridad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import registraduria.seguridad.model.Usuario;
import registraduria.seguridad.repository.Usuariorepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private Usuariorepository usuarioRepo;

    @PostMapping("/validate")
    public Usuario validate(@RequestBody Usuario infoUsuario, final HttpServletResponse response) throws IOException {

        String correo = infoUsuario.getCorreo();
        String password = infoUsuario.getContrasena();
        password = tecnicaHash(password);

        Optional<Usuario> opt = this.usuarioRepo.findByCorreoAndContrasena(correo, password);

        if(opt.isPresent())
        {
            Usuario u = opt.get();
            u.setContrasena("");
            return u;
        }
        else
        {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

    }

    @GetMapping("")
    public List<Usuario> index() {
        return this.usuarioRepo.findAll();
    }

    @GetMapping("/{id}")
    public Usuario show(@PathVariable String id) {
        Optional<Usuario> opt = this.usuarioRepo.findById(id);
        return opt.orElse(null);
    }

    @PostMapping("")
    public Usuario create(@RequestBody Usuario infoUsuario) {
        String nuevaContrasena = tecnicaHash(infoUsuario.getContrasena());
        infoUsuario.setContrasena(nuevaContrasena);
        return this.usuarioRepo.save(infoUsuario);
    }

    @PutMapping("/{id}")
    public Usuario update(@PathVariable String id, @RequestBody Usuario infoUsuario) {
        Optional<Usuario> opt = this.usuarioRepo.findById(id);
        if(opt.isPresent())
        {
            Usuario actual = opt.get();

            if(infoUsuario.getContrasena() != null && !infoUsuario.getContrasena().isBlank())
                actual.setContrasena(infoUsuario.getContrasena());
            if(infoUsuario.getSeudonimo() != null && !infoUsuario.getSeudonimo().isBlank())
                actual.setSeudonimo(infoUsuario.getSeudonimo());
            if(infoUsuario.getCorreo() != null && !infoUsuario.getCorreo().isBlank())
                actual.setCorreo(infoUsuario.getCorreo());
            if(infoUsuario.getRol() != null)
                actual.setRol(infoUsuario.getRol());

            return this.usuarioRepo.save(actual);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {

        Optional<Usuario> opt = this.usuarioRepo.findById(id);
        if(opt.isPresent())
        {
            this.usuarioRepo.deleteById(id);
        }

    }

    public String tecnicaHash(String password) {

        MessageDigest md;

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] hash = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();

        for(byte b : hash) {
            sb.append( String.format("%02x", b) );
        }
        return sb.toString();

    }



}
