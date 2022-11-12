package registraduria.seguridad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import registraduria.seguridad.model.Permiso;
import registraduria.seguridad.repository.PermisoRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/permisos")
public class PermisoController {

    @Autowired
    private PermisoRepository permisoRepo;

    @GetMapping("")
    public List<Permiso> index() {
        return this.permisoRepo.findAll();
    }

    @GetMapping("{id}")
    public Permiso show(@PathVariable String id) {
        Optional<Permiso> optPermiso = this.permisoRepo.findById(id);
        return optPermiso.orElse(null);
    }

    @PostMapping("")
    public Permiso create(@RequestBody Permiso p) {
        return this.permisoRepo.save(p);
    }

    @PutMapping("{id}")
    public Permiso update(@PathVariable String id, @RequestBody Permiso p) {
        Optional<Permiso> optPermiso = this.permisoRepo.findById(id);
        if(optPermiso.isPresent())
        {
            Permiso actual = optPermiso.get();
            actual.setUrl(p.getUrl());
            actual.setMetodo(p.getMetodo());
            return this.permisoRepo.save(actual);
        }
        return null;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id)
    {
        this.permisoRepo.deleteById(id);
    }

}