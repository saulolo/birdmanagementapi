-- ============================================
-- CREA LA BASE DE DATOS:
-- Descripción: Crea la base de datos para la gestión de avistamiento de Aves.
-- Fecha de creación: 19-Oct-25
-- Nota: Consulta para crear la base de datos (Ejecucin manual fuera de Spring/Hibernate)
-- Esta sentencia solo se usa si la BD no existe.
-- ============================================
CREATE
DATABASE bd_bird_management_ias;


-- ####################
-- ## TABLAS DE SEGURIDAD
-- ####################

-- 1. Consultar todos los Permisos definidos
SELECT *
FROM permissions;

-- 2. Consultar todos los Roles definidos
SELECT *
FROM roles;

-- 3. Consultar la tabla de enlace de Roles y Permisos (ID y Permisos asociados)
SELECT *
FROM roles_permissions;

-- 4. Consultar Roles y Permisos asociados con sus nombres (Vista detallada)
SELECT r.id_role,
       r.role_name AS rol,
       p.name      AS permiso_otorgado
FROM roles r
         INNER JOIN
     roles_permissions rp ON r.id_role = rp.id_role
         INNER JOIN
     permissions p ON rp.id_permission = p.id_permission
ORDER BY r.id_role, p.id_permission;


-- ####################
-- ## TABLAS DE USUARIOS
-- ####################

-- 5. Consultar todos los Usuarios (users)
SELECT *
FROM users;

-- 6. Consultar la tabla de enlace de Usuarios y Roles (users_roles)
SELECT *
FROM users_roles;

-- 7. Consultar Usuarios, sus Roles asignados y todos los Permisos que dicho Rol otorga.
SELECT u.id_user,
       u.name                                   AS user_name,
       u.username,
       u.password, -- Campo de contrasea (hash) incluido aqu
       r.role_name,
       STRING_AGG(p.name, ', ' ORDER BY p.name) AS permissions_granted
FROM users u
         INNER JOIN
     users_roles ur ON u.id_user = ur.id_user
         INNER JOIN
     roles r ON ur.id_role = r.id_role
         INNER JOIN
     roles_permissions rp ON r.id_role = rp.id_role
         INNER JOIN
     permissions p ON rp.id_permission = p.id_permission
GROUP BY u.id_user, u.name, u.username, u.password, r.role_name
ORDER BY u.id_user;


-- ####################
-- ## TABLAS DE NEGOCIOS PRINCIPALES
-- ####################

-- 8. Consultar todos las familas (families)
SELECT *
FROM families;

-- 9. Consultar todos los habitats (habitats)
SELECT *
FROM habitats;

-- 10. Consultar todas las aves (birds)
SELECT *
FROM birds;

-- 11. Consultar todos los paises (countries)
SELECT *
FROM countries;

-- 12. Consultar todos los avistamientos (sightings)
SELECT *
FROM sightings;


-- ####################
-- ## MANTENIMIENTO (Ejecucin CUIDADOSA)
-- ####################

-- Consultar para reiniciar la tabla de usuarios (ej: en entornos de prueba)
-- TRUNCATE TABLE users RESTART IDENTITY CASCADE;

-- Consultar para eliminar la tabla de usuarios (Ejecucin muy CUIDADOSA)
-- DROP TABLE IF EXISTS users;