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

