package com.gestionempleados.microservicio_eliminacion.controller;

import com.gestionempleados.microservicio_eliminacion.dto.TareaRequest;
import com.gestionempleados.microservicio_eliminacion.model.Tarea;
import com.gestionempleados.microservicio_eliminacion.repository.TareaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/tareas")
@Tag(name = "Tareas", description = "CRUD de tareas")
public class TareaController {

    private final TareaRepository repository;

    public TareaController(TareaRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Operation(summary = "Crear una tarea")
    public ResponseEntity<Tarea> crear(
            @Valid @RequestBody TareaRequest request) {

        Tarea tarea = new Tarea(
                request.titulo(),
                request.descripcion(),
                request.estado(),
                request.empleadoId()
        );

        Tarea creada = repository.save(tarea);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creada.getId())
                .toUri();

        return ResponseEntity.created(location).body(creada);
    }

    @GetMapping
    @Operation(summary = "Listar tareas")
    public List<Tarea> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar tarea")
    public ResponseEntity<Tarea> consultar(@PathVariable Long id) {

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tarea")
    public ResponseEntity<Tarea> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody TareaRequest request) {

        return repository.findById(id)
                .map(tarea -> {

                    tarea.setTitulo(request.titulo());
                    tarea.setDescripcion(request.descripcion());
                    tarea.setEstado(request.estado());
                    tarea.setEmpleadoId(request.empleadoId());

                    return ResponseEntity.ok(
                            repository.save(tarea)
                    );
                })
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tarea")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}