package registraduria.seguridad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import registraduria.seguridad.model.Permiso;
import registraduria.seguridad.model.PermisoRol;
import registraduria.seguridad.model.Rol;
import registraduria.seguridad.repository.PermisoRepository;
import registraduria.seguridad.repository.PermisoRolRepository;
import registraduria.seguridad.repository.RolRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/permisos-roles")
public class RolPermisoController {

    @Autowired
    private PermisoRolRepository permisoRolRepo;

    @Autowired
    private PermisoRepository permisoRepo;

    @Autowired
    private RolRepository rolRepo;

    @GetMapping("")
    public List<PermisoRol> index() {
        return this.permisoRolRepo.findAll();
    }

    @GetMapping("{id}")
    public PermisoRol show(@PathVariable String id) {
        Optional<PermisoRol> optPermiso = this.permisoRolRepo.findById(id);
        return optPermiso.orElse(null);
    }

    @PostMapping("")
    public PermisoRol create(@RequestBody PermisoRol p) {

        Optional<Permiso> optPermiso = this.permisoRepo.findById(p.getPermiso().get_id());
        if(optPermiso.isEmpty())
        {
            return null;
        }

        Optional<Rol> rolOpt = this.rolRepo.findById(p.getRol().get_id());

        if(rolOpt.isEmpty())
        {
            return null;
        }

        return this.permisoRolRepo.save(p);
    }

    @PutMapping("{id}")
    public PermisoRol update(@PathVariable String id, @RequestBody PermisoRol p) {
        Optional<PermisoRol> optPermiso = this.permisoRolRepo.findById(id);
        if(optPermiso.isPresent())
        {
            PermisoRol actual = optPermiso.get();

            return this.permisoRolRepo.save(actual);
        }
        return null;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id)
    {
        this.permisoRolRepo.deleteById(id);
    }

    @PostMapping("/validar-permiso/rol/{id_rol}")
    public PermisoRol getPermiso(@PathVariable String id_rol, @RequestBody Permiso infoPermiso,
                                 final HttpServletResponse response) throws IOException {

        Optional<Permiso> opt = this.permisoRepo.findByUrlAndMetodo(infoPermiso.getUrl(), infoPermiso.getMetodo());

        if(!opt.isPresent())
        {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        Permiso p = opt.get();

        Optional<Rol> optRol = this.rolRepo.findById(id_rol);

        if(!optRol.isPresent())
        {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        Rol r = optRol.get();

        Optional<PermisoRol> optPermisoRol = this.permisoRolRepo.findByRolAndPermiso(r, p);

        if(!optPermisoRol.isPresent())
        {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }

        return optPermisoRol.get();

    }

}