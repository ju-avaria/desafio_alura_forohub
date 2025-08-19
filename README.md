📚 Forumhub - Desafio Alura
---

**Forum Hub** es una aplicación backend desarrollada en Java con Spring, cuyo objetivo es replicar el funcionamiento de un foro en línea.
La **API** permite gestionar tópicos creados por los usuarios, aplicando buenas prácticas de desarrollo y arquitectura **REST**.

---

## 🚀 Funcionalidades principales

La API ofrece un **CRUD** completo para la gestión de tópicos, cursos, usuarios y respuestas:

- ➕ **Crear** un nuevo tópico, curso, usuario o respuesta.

- 📄 **Listar** todos los tópicos , curso, usuario o respuesta  creados.

- 🔎 **Consultar** un tópico, curso, usuario o respuesta en específico.

- ✏️ **Actualizar** un tópico , curso, usuario o respuesta existente

- 🗑️ **Eliminar** un tópico , curso, usuario o respuesta.

---
## 🛠️ **Características técnicas**

- Rutas implementadas siguiendo las mejores prácticas del modelo REST

- Validaciones aplicadas según reglas de negocio

- Persistencia de datos mediante base de datos relacional

- Autenticación y autorización para restringir el acceso a información sensible

---

## 🎯 **Objetivo**.

 El proyecto busca simular el backend de un foro, proporcionando una API sólida, validada y segura para el manejo de la información.
---

## 🧰 Tecnologías / Herramientas

- 💻 Lenguaje: Java
- 📚 Librerías/Frameworks: Spring Framework, Lombok,
- 🗄️ Base de datos: MySQL, Flyway
- 💡 Otros: Spring JPA, Spring Web, Sring DevTools, Spring Validation
- 🔐 Seguridad: Spring Security, JWT
- 🌐 Pruebas de la API : Insomnia

---

## 🔒 Seguridad

La seguridad es un pilar fundamental en Foro Hub, garantizando la protección de datos y el acceso controlado a los recursos de la API.

✅ **Mecanismos implementados**

**Autenticación con JWT**
Cada solicitud a los endpoints protegidos requiere un token JWT válido enviado en el encabezado Authorization.

**Hashing de contraseñas**
Las contraseñas nunca se almacenan en texto plano.
Se utiliza BCryptPasswordEncoder para generar un hash seguro antes de guardarlas en la base de datos.
---
## 🚀 Instalación

1. Clona el repositorio:

   ```bash
   https://github.com/ju-avaria/desafio_alura_forohub
   ```

2. Para poder usar la aplicacion necesitarás crear una base de datos en MySQL, y hacer algunos cambios en el 
   archivo **aplication.properties** 
3. Tendras que colocar el nombre de tu base de datos en la url del localhost, colocar tu nombre de usuario y contraeña
   de la base de datos y colocar en **secret** una nueva clave para ingresar el primer usuario a la DB:

```bash
spring.application.name=forumhub

spring.datasource.url=jdbc:mysql://localhost/foro_hub
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username={usuario}
spring.datasource.password={contrasena}

spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

api.security.secret=${JWT_SECRET:secret}
    
```

---

**Autor:** https://github.com/ju-avaria