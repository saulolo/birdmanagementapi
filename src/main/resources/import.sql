-- ============================================
-- POBLADO INICIAL DE LA TABLA: countries
-- Descripción: Inserta 10 paises base para pruebas
-- Fecha de creación: 16-Oct-25
-- Nota: este script se ejecuta automáticamente al iniciar la aplicación
-- si está configurado el parámetro spring.jpa.hibernate.ddl-auto=create o update.
-- ============================================
INSERT INTO countries (name, iso_code)
VALUES ('Colombia', 'COL'),
       ('Brasil', 'BRA'),
       ('Argentina', 'ARG'),
       ('Chile', 'CHL'),
       ('Perú', 'PER'),
       ('México', 'MEX'),
       ('Estados Unidos', 'USA'),
       ('Canadá', 'CAN'),
       ('España', 'ESP'),
       ('Ecuador', 'ECU');


-- ============================================
-- POBLADO INICIAL DE LA TABLA: users
-- Descripción: Inserta 5 usuarios base para pruebas
-- Fecha de creación: 17-Oct-25
-- ============================================
INSERT INTO users (id_user, name, username, email, password, is_enabled, account_no_expired, account_no_locked,
                   credential_no_expired, created_date)
VALUES (1, 'Saul', 'saulolo', 'saulolo@example.com', '1234', TRUE, TRUE, TRUE, TRUE, '2025-10-16 18:37:30.745'),
       (2, 'Felipe', 'pipe', 'vasquez@example.com', '1234', TRUE, TRUE, TRUE, TRUE, '2025-10-16 18:37:30.745'),
       (3, 'Alejandra', 'aleja', 'arenasz@example.com', '1234', TRUE, TRUE, TRUE, TRUE, '2025-10-16 18:37:30.745'),
       (4, 'Leidy', 'zapata', 'zapata@example.com', '1234', TRUE, TRUE, TRUE, TRUE, '2025-10-16 18:37:30.745'),
       (5, 'Diego', 'pupilo', 'martinez@example.com', '1234', TRUE, TRUE, TRUE, TRUE, '2025-10-16 18:37:30.745');
-- Contraseña temporal: "1234"
-- Estos usuarios pueden asociarse a roles o permisos posteriormente.


-- ============================================
-- POBLADO INICIAL DE LA TABLA: roles
-- Descripción: Inserta 4 roles base para pruebas
-- Fecha de creación: 22-Oct-25
-- ============================================
INSERT INTO roles(id_role, role_name)
values (1, 'ADMIN'),
       (2, 'USER'),
       (3, 'DEVELOPER'),
       (4, 'INVITED');


-- ============================================
-- POBLADO INICIAL DE LA TABLA: permissions
-- Descripción: Inserta 5 permisos base para pruebas
-- Fecha de creación: 22-Oct-25
-- ============================================
INSERT INTO permissions(id_permission, name)
values (1, 'READ'),
       (2, 'CREATE'),
       (3, 'UPDATE'),
       (4, 'DELETE'),
       (5, 'REFACTOR');


-- ============================================
-- POBLADO INICIAL DE LA TABLA: roles_permissions
-- Descripción: Asocia los permisos base con los roles del sistema
-- Fecha de creación: 22-Oct-25
-- Nota: esta tabla establece las relaciones muchos a muchos entre roles y permisos
-- ============================================
INSERT INTO roles_permissions (id_permission, id_role)
VALUES (1, 1), -- ADMIN -> READ
       (2, 1), -- ADMIN -> CREATE
       (3, 1), -- ADMIN -> UPDATE
       (4, 1), -- ADMIN -> DELETE
       (1, 2), -- USER -> READ
       (2, 2), -- USER -> CREATE
       (3, 2), -- USER -> UPDATE
       (1, 3), -- DEVELOPER -> READ
       (2, 3), -- DEVELOPER -> CREATE
       (3, 3), -- DEVELOPER -> UPDATE
       (4, 3);
-- DEVELOPER -> DELETE
-- Los roles ADMIN, USER y DEVELOPER tienen distintos niveles de permisos.
-- El rol INVITED (id=4) no tiene permisos asociados por defecto.


-- ============================================
-- POBLADO INICIAL DE LA TABLA: users_roles
-- Descripción: Asocia los usuarios base con sus respectivos roles
-- Fecha de creación: 22-Oct-25
-- Nota: esta tabla define la relación muchos a muchos entre usuarios y roles
-- ============================================
INSERT INTO users_roles (id_role, id_user)
VALUES (1, 1), -- Saul (ADMIN)
       (2, 2), -- Felipe (USER)
       (2, 3), -- Alejandra (USER)
       (3, 4), -- Leidy (DEVELOPER)
       (4, 5);
-- Diego (INVITED)
-- Los usuarios se asocian a roles base según sus perfiles iniciales.

