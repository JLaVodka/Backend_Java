package com.gestionempleados.microservicio_eliminacion.controller;

import com.gestionempleados.microservicio_eliminacion.dto.TareaRequest;
import com.gestionempleados.microservicio_eliminacion.model.Tarea;
import com.gestionempleados.microservicio_eliminacion.repository.TareaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/tareas")
@Tag(name = "Tareas", description = "Operaciones CRUD sobre tareas")
public class TareaController {

    private final TareaRepository repository;

    public TareaController(TareaRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Operation(summary = "Crear una tarea")
    public ResponseEntity<Tarea> crear(@Valid @RequestBody TareaRequest request) {
        Tarea creada = repository.save(
                new Tarea(request.titulo(), request.estado(), request.empleadoId()));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creada.getId())
                .toUri();
        return ResponseEntity.created(location).body(creada);
    }

    @GetMapping
    @Operation(summary = "Listar todas las tareas")
    public List<Tarea> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar una tarea por id")
    public ResponseEntity<Tarea> consultar(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una tarea por id")
    public ResponseEntity<Tarea> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody TareaRequest request) {
        return repository.findById(id)
                .map(tarea -> {
                    tarea.setTitulo(request.titulo());
                    tarea.setEstado(request.estado());
                    tarea.setEmpleadoId(request.empleadoId());
                    return ResponseEntity.ok(repository.save(tarea));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una tarea por id")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/health")
    @Operation(summary = "Verificar que el servicio está activo")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("estado", "activo", "servicio", "microservicio-java"));
    }
}
