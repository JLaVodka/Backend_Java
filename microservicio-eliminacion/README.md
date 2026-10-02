# Microservicio Java de tareas

API CRUD con Java 17, Spring Boot, Maven, PostgreSQL y documentación OpenAPI/Swagger.

## Suposición sobre la base de datos

El código conserva la tabla de tu ejemplo: `tareas_tarea`, con las columnas `id`, `titulo`, `estado` y `empleado_id`. `spring.jpa.hibernate.ddl-auto=none` evita que Hibernate cambie el esquema. Confirma que esos nombres y tipos coinciden con tu base.

## Ejecutar localmente

1. Copia `.env.example` como `.env` y ajusta la URL, usuario y contraseña de PostgreSQL.
2. Desde esta carpeta, ejecuta `mvn spring-boot:run`.
3. Abre `http://localhost:8080/swagger-ui.html`.

También puedes generar el JAR con `mvn clean package -DskipTests` y ejecutarlo con `java -jar target/microservicio-java-1.0.0.jar`.

## Variables para Render

Configura `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` y `SPRING_DATASOURCE_PASSWORD` en el panel del servicio. La URL debe ser JDBC, por ejemplo `jdbc:postgresql://HOST_INTERNO:5432/NOMBRE_BD`, usando los datos internos de PostgreSQL de Render. La URL `postgresql://...` que Render muestra directamente no lleva el prefijo JDBC que espera Spring.

Render proporciona `PORT` para el servidor HTTP. La aplicación lo usa automáticamente y emplea `8080` como valor local predeterminado. `5432` es el puerto típico de PostgreSQL, no el puerto HTTP de la aplicación.

Comando de construcción: `mvn clean package -DskipTests`  
Comando de inicio: `java -jar target/microservicio-java-1.0.0.jar`

Cuando esté publicado, la documentación estará en `https://TU-SERVICIO.onrender.com/swagger-ui.html` y el esquema OpenAPI en `https://TU-SERVICIO.onrender.com/v3/api-docs`.

## Endpoints

| Método | Ruta | Uso |
|---|---|---|
| `POST` | `/tareas` | Crear tarea |
| `GET` | `/tareas` | Listar tareas |
| `GET` | `/tareas/{id}` | Consultar tarea |
| `PUT` | `/tareas/{id}` | Actualizar tarea |
| `DELETE` | `/tareas/{id}` | Eliminar tarea |
| `GET` | `/tareas/health` | Revisar estado del servicio |

Ejemplo de cuerpo para crear o actualizar:

```json
{
  "titulo": "Preparar informe",
  "estado": "pendiente",
  "empleadoId": 1
}
```

Este proyecto deja el CRUD Java y Swagger configurados. El fallback de resiliencia hacia otro lenguaje corresponde a una capa de consulta/orquestación y se debe conectar cuando estén listos los servicios alternativos.
