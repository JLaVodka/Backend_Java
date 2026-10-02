package com.gestionempleados.microservicio_eliminacion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TareaRequest(

        @NotBlank(message = "El título es obligatorio")
        @Size(max = 200)
        String titulo,

        @NotBlank(message = "La descripción es obligatoria")
        String descripcion,

        @NotBlank(message = "El estado es obligatorio")
        @Size(max = 20)
        String estado,

        Long empleadoId
) {
}