package com.gestionempleados.microservicio_eliminacion.repository;

import com.gestionempleados.microservicio_eliminacion.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
}