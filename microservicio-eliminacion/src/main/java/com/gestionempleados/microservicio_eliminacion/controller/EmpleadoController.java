package com.gestionempleados.microservicio_eliminacion.controller;

import com.gestionempleados.microservicio_eliminacion.dto.EmpleadoRequest;
import com.gestionempleados.microservicio_eliminacion.model.Empleado;
import com.gestionempleados.microservicio_eliminacion.repository.EmpleadoRepository;

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
@RequestMapping("/empleados")
@Tag(
        name = "Empleados",
        description = "Operaciones CRUD sobre empleados"
)
public class EmpleadoController {

    private final EmpleadoRepository repository;

    public EmpleadoController(EmpleadoRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Operation(summary = "Crear un empleado")
    public ResponseEntity<Empleado> crear(
            @Valid @RequestBody EmpleadoRequest request) {

        Empleado empleado = new Empleado(
                request.nombre(),
                request.especialidad()
        );

        Empleado creado = repository.save(empleado);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(creado);
    }

    @GetMapping
    @Operation(summary = "Listar todos los empleados")
    public List<Empleado> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar un empleado por ID")
    public ResponseEntity<Empleado> consultar(
            @PathVariable Long id) {

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un empleado")
    public ResponseEntity<Empleado> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EmpleadoRequest request) {

        return repository.findById(id)
                .map(empleado -> {

                    empleado.setNombre(request.nombre());
                    empleado.setEspecialidad(request.especialidad());

                    return ResponseEntity.ok(
                            repository.save(empleado)
                    );
                })
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un empleado")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/health")
    @Operation(summary = "Verificar que el servicio está activo")
    public ResponseEntity<Map<String, String>> health() {

        return ResponseEntity.ok(
                Map.of(
                        "estado", "activo",
                        "servicio", "microservicio-java"
                )
        );
    }
}