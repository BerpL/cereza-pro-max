# Cereza Pro Max

Aplicacion web para la gestion de atencion al cliente por WhatsApp (Contact Center) de una empresa de taxi corporativo.
Proyecto final del curso Lenguaje de Programacion II, Cibertec, 2026.

## Tecnologias
- Java 17, Spring Boot 3.3 (Spring MVC, Spring Data JPA, Thymeleaf)
- Hibernate y MySQL
- Maven
- Git y GitHub

## Integrantes
- Eder Ayrton Cornejo Diaz (coordinador)
- Gabriela Denisse Huaringa Garcia (analista)
- Brenda Daniela Cornejo Diaz (base de datos)
- Luis Diego Quispe Monroy (backend)
- Ricardo Antonio Ramirez Pacheco (frontend)
- Lyan Flores Davila (consultas y pruebas)
- Jhon Elvis Cana Condori (control de versiones)

## Ejecutar
1. Ejecutar `src/main/resources/db/cereza_pro_max.sql` en MySQL.
2. Revisar usuario y clave en `src/main/resources/application.properties`.
3. `mvn spring-boot:run` y abrir http://localhost:8080/login

Usuarios de prueba: admin@cereza.com / admin123, supervisor@cereza.com / super123, agente@cereza.com / agente123
