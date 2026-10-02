package com.gestionempleados.microservicio_eliminacion.repository;

import com.gestionempleados.microservicio_eliminacion.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
}