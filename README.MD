# 🍽️ El Sabor de Sión - API de Gestión de Restaurante y Domicilios

## 📌 Descripción  

*"El Sabor de Sión"* es un restaurante en crecimiento que enfrenta problemas en la gestión manual de pedidos, control del menú y administración de usuarios. Los pedidos se toman de forma manual, lo que genera errores en la preparación, retrasos en las entregas y dificultades en el seguimiento de los pagos.

Además, el restaurante no cuenta con un sistema digital que permita a los clientes realizar pedidos en línea, lo que limita su capacidad de expansión.

Con el crecimiento del negocio, se requiere un sistema que **automatice la gestión de pedidos, el menú y la administración de usuarios**, permitiendo a los clientes hacer pedidos en línea y optimizando la entrega de los mismos. Se tomó, como parte de la solución, desarrollar una API RESTFull que se encargue de comunicar el servidor con la aplicación gráfica.

# 📜 Requerimientos del Sistema

1. **Usuarios y Roles:**
    - **Administrador**: Gestiona menú, usuarios y reportes.
    - **Gestor de Pedidos:** Acepta, cancela y actualiza el estado de los pedidos.
    - **Cliente:** Realiza pedidos y consulta su historial.

2. **Menú y Productos:**

    - CRUD de platos con categorías (Entradas, Platos Fuertes, Bebidas, Postres).
    - Validación de disponibilidad (no se pueden pedir platos agotados).

3. **Pedidos:**

    - Flujo de estados: **Pendiente → En Preparación → Listo → Entregado.**
    - Pedidos a domicilio requieren dirección y contacto.
    - No se puede modificar un pedido en preparación.

4. Pagos:

    - Métodos aceptados: **Efectivo, Tarjeta, Transferencia.**
    - No se pueden realizar pedidos con facturas pendientes.

---

# 🛠 Endpoints Principales
### 🔐 Autenticación
- `POST /auth/register` → Registrar usuario
- `POST /auth/login` → Iniciar sesión
### 🍽️ Menú
- `GET /menu` → Listar platos
- `POST /menu` → Agregar plato (Admin)
- `PUT /menu/{id}` → Editar plato (Admin)
- `DELETE /menu/{id}` → Eliminar plato (Admin)
### 🛒 Pedidos
- `POST /pedidos` → Crear pedido (Cliente)
- `GET /pedidos/{id}` → Consultar pedido
- `PUT /pedidos/{id}/estado` → Actualizar estado (Gestor de Pedidos)
### 💰 Pagos
- `POST /pagos` → Registrar pago
- `GET /pagos/{pedidoId}` → Consultar pago
---

## 🚀 **Tecnologías Utilizadas**
- **Java 21+**
- **Spring Boot 3+**
- **Spring Security + JWT** (Autenticación, autorización y seguridad)
- **Spring Data JPA** (Persistencia)
- **Spring Jakarta Validation** (Validacion de campos)
- **MySQL** (Base de datos)
- **Lombok** (Reducción de código boilerplate)
- **Swagger/OpenAPI** (Documentación)
- **MapStruct** (Conversión de entidades a DTOs)
- **API de pago y transacciones de Mercado Pago** (Para manejar los procesos de pagos en la app)
---

## 📂 **Estructura del Proyecto**
```bash
el-sabor-de-sion/
│── src/main/java/com/elsabordesion
│   ├── security/               # Configuración de seguridad y JWT. Su estructura de subcarpetas es una copia de la estructra principal, pero en el contexto de segurodad
│   ├── controller/             # Controladores (endpoints de la API)
│   ├── data/                   # Contiene todo lo necesario para la persistencia
│   │   ├── dto/                # Objetos para transferencia de datos
│   │   │   ├── input/          # DTOs para la entrada de datos
│   │   │   ├── output/         # DTOs para la salidad de datos
│   │   ├── enums/              # Contiene los enumerados para representar columnas con opcciones fijas (ENUMS)
│   │   ├── entity/             # Entidades para mapear en la base de datos
│   │   ├── repository/         # Repositorios para acceso a datos                  
│   ├── service/                # Lógica de negocio
│   ├── util/                   # Clases utilitarias (mapeo, validaciones, etc.)
│   │   ├── mapper              # Mapeadores
│── src/main/resources/
│   ├── application.properties  # Configuración de la aplicación
│── pom.xml                     # Dependencias del proyecto
│── README.md                   # Documentación del proyecto
