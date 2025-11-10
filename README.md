# 🛒 TPSpringBoot – Gestión de Productos

## 📝 Descripción del proyecto
Aplicación RESTful construida con Spring Boot que expone un conjunto de endpoints para administrar un catálogo de productos de e-commerce. Permite crear, consultar, actualizar, eliminar y filtrar productos por categoría, validando los datos de entrada y documentando automáticamente la API con Swagger/OpenAPI. La persistencia se maneja con H2 en modo archivo, ideal para entornos de desarrollo y pruebas.

## ⚙️ Tecnologías utilizadas
- Java 21
- Spring Boot 3 (Web, Data JPA, Validation)
- Springdoc OpenAPI 2 (Swagger UI)
- H2 Database (modo file)
- Gradle Wrapper
- Jakarta Bean Validation

## 🚀 Instrucciones para clonar y ejecutar
```bash
# 1. Clonar el repositorio
git clone https://github.com/<tu-usuario>/TPSpringBoot.git
cd TPSpringBoot

# 2. Construir y ejecutar pruebas
./gradlew clean build

# 3. Levantar la aplicación
./gradlew bootRun
```

> **Nota:** En Windows PowerShell reemplace `./gradlew` por `.\gradlew`. Si usás IntelliJ IDEA o Eclipse, importá el proyecto como *Gradle Project* y ejecutá la clase `com.utn.productos.Main`.

### Variables relevantes
- `server.port=8080`
- `spring.datasource.url=jdbc:h2:file:/tmp/data/productosdb`

## 🌐 Endpoints

| Método | Ruta                         | Descripción                                      |
|--------|------------------------------|--------------------------------------------------|
| GET    | `/api/productos`             | Obtiene todos los productos.                     |
| GET    | `/api/productos/{id}`        | Obtiene un producto por su identificador.        |
| GET    | `/api/productos/{categoria}` | Obtiene productos filtrados por categoría.       |
| POST   | `/api/productos`             | Crea un producto nuevo.                          |
| PUT    | `/api/productos/{id}`        | Actualiza todos los datos del producto.          |
| PUT    | `/api/productos/{id}/stock`  | Actualiza únicamente el stock disponible.        |
| DELETE | `/api/productos/{id}`        | Elimina un producto existente (si no tiene stock). |

## 📸 Evidencias (Swagger UI & H2)
- ![Swagger – Documentación completa](docs/screenshots/swagger-overview.png)
- ![Swagger – POST exitoso](docs/screenshots/swagger-post-success.png)
- ![Swagger – GET productos](docs/screenshots/swagger-get-list.png)
- ![Swagger – Error 404 producto inexistente](docs/screenshots/swagger-error-404.png)
- ![Swagger – Error 400 validación](docs/screenshots/swagger-error-400.png)
- ![Consola H2 con datos persistidos](docs/screenshots/h2-console.png)

> Guarda las capturas en `docs/screenshots/` con los nombres mostrados para que se vean correctamente en el README.

## 🔗 Accesos rápidos
- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- Consola H2: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)  
  - **JDBC URL:** `jdbc:h2:file:/tmp/data/productosdb`  
  - **Usuario:** `sa`  
  - **Password:** *(vacío)*

## 💭 Conclusiones personales
El desarrollo de esta API reafirmó la importancia de diseñar DTOs inmutables, separar responsabilidades mediante capas claras y documentar cada endpoint con OpenAPI para acelerar la comunicación con otros equipos. Aprovechar Spring Boot 3 con Java 21 simplifica la configuración, y mantener tests básicos de contexto asegura que el arranque del proyecto sea estable y reproducible.

## 👤 Autor
- **Nombre:** José Martín Rodriguez Mortaloni 
- **Legajo:** 51069

