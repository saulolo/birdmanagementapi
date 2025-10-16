--Saul Echeverri
-- 16-oct-2025
-- Se agregan paises iniciales para la aplicación "Bird Management Api".
-- Nota: este script se ejecuta automáticamente al iniciar la aplicación
-- si está configurado el parámetro spring.jpa.hibernate.ddl-auto=create o update.
-- ####################
-- ## countries
-- ####################
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


