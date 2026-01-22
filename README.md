# tf6-backend 🚀
Backend del proyecto académico **CONECTADOS+**, desarrollado en **Java + Spring Boot**, diseñado para gestionar usuarios, autenticación y servicios del sistema.

---

## 📌 Tecnologías utilizadas
- **Java 17+**
- **Spring Boot**
- **Spring Security + JWT**
- **Spring Data JPA**
- **Hibernate**
- **PostgreSQL / MySQL** (según configuración)
- **Maven**
- **Swagger/OpenAPI** (si aplica)
- **Render** (deploy)

---

## 🎯 Funcionalidades principales
✅ Autenticación de usuarios con JWT  
✅ Gestión de usuarios y roles (ej: `ROLE_ADMIN`, `ROLE_USER`)  
✅ CRUD de cursos  
✅ Matrícula y progreso de cursos  
✅ Ranking / Puntos por usuario (si aplica)  
✅ Seguridad por roles y endpoints protegidos  

---

## 🔐 Autenticación (JWT)
Este backend usa autenticación basada en **JWT (JSON Web Token)**.

### Endpoint de Login
`POST /api/authenticate`

#### Request JSON
```json
{
  "username": "Mauricio",
  "password": "123456"
}
