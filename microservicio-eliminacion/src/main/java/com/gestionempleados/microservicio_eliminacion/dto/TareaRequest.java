package com.gestionempleados.microservicio_eliminacion.dto;

import jakarta.validation.constraints.NotBlank;

public record TareaRequest(
        @NotBlank(message = "El título es obligatorio") String titulo,
        @NotBlank(message = "El estado es obligatorio") String estado,
        Long empleadoId) {
}
