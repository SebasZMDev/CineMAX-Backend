CREATE DATABASE IF NOT EXISTS cine_audit_db;
USE cine_audit_db;

DROP TABLE IF EXISTS logs_auditoria;

CREATE TABLE logs_auditoria(
    id BIGINT NOT NULL AUTO_INCREMENT,
    entidad VARCHAR(50) NOT NULL,       -- "USUARIO", "RESERVA"
    accion VARCHAR(50) NOT NULL,        -- "REGISTRO", "CREAR_RESERVA"
    usuario_id BIGINT,
    detalle VARCHAR(500),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);




CREATE DATABASE IF NOT EXISTS cine_auth_db;
USE cine_auth_db;

DROP TABLE IF EXISTS refresh_tokens;
DROP TABLE IF EXISTS usuario_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE roles(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY(id)
);

CREATE TABLE usuario_roles(
    usuario_id BIGINT NOT NULL,
    rol_id BIGINT NOT NULL,
    PRIMARY KEY(usuario_id, rol_id),
    CONSTRAINT fk_usuario_roles_usuario FOREIGN KEY(usuario_id) REFERENCES usuarios(id),
    CONSTRAINT fk_usuario_roles_rol FOREIGN KEY(rol_id) REFERENCES roles(id)
);

CREATE TABLE refresh_tokens(
    id BIGINT NOT NULL AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    token VARCHAR(500) NOT NULL,
    expiracion TIMESTAMP NOT NULL,
    revocado BOOLEAN DEFAULT FALSE,
    PRIMARY KEY(id),
    CONSTRAINT fk_refresh_usuario FOREIGN KEY(usuario_id) REFERENCES usuarios(id)
);

INSERT INTO roles(nombre) VALUES ('ADMIN'), ('CLIENTE');

INSERT INTO usuarios (nombre, email, password_hash)
VALUES ('admin', 'admin@cine.com', '$2b$10$gUzRHx7rawcsN7jHtJmWruDlOZG8T9WFCvYBNxMgOb0VbTuyGeVvm');

INSERT INTO usuario_roles (usuario_id, rol_id)
SELECT u.id, r.id FROM usuarios u, roles r
WHERE u.email = 'admin@cine.com' AND r.nombre = 'ADMIN';

























CREATE DATABASE IF NOT EXISTS cine_cinema_db;
USE cine_cinema_db;

DROP TABLE IF EXISTS tb_reserva_asiento;
DROP TABLE IF EXISTS tb_asiento;
DROP TABLE IF EXISTS tb_reserva;
DROP TABLE IF EXISTS tb_funcion;
DROP TABLE IF EXISTS tb_sala;
DROP TABLE IF EXISTS tb_pelicula;

CREATE TABLE tb_pelicula(
    cod_pel INT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    genero VARCHAR(50),
    duracion INT,
    clasificacion VARCHAR(10),
    fecha_estreno DATE,
    precio_entrada DECIMAL(10,2),
    PRIMARY KEY(cod_pel)
);

CREATE TABLE tb_sala(
    cod_sala INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(50),
    capacidad INT,
    tipo VARCHAR(20),
    PRIMARY KEY(cod_sala)
);

CREATE TABLE tb_funcion(
    cod_fun INT NOT NULL AUTO_INCREMENT,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    cod_pel INT NOT NULL,
    cod_sala INT NOT NULL,
    PRIMARY KEY(cod_fun),
    CONSTRAINT fk_funcion_pelicula FOREIGN KEY(cod_pel) REFERENCES tb_pelicula(cod_pel),
    CONSTRAINT fk_funcion_sala FOREIGN KEY(cod_sala) REFERENCES tb_sala(cod_sala)
);

CREATE TABLE tb_asiento(
    cod_asiento INT NOT NULL AUTO_INCREMENT,
    cod_sala INT NOT NULL,
    fila CHAR(1) NOT NULL,
    numero INT NOT NULL,
    PRIMARY KEY(cod_asiento),
    CONSTRAINT fk_asiento_sala FOREIGN KEY(cod_sala) REFERENCES tb_sala(cod_sala)
);

CREATE TABLE tb_reserva(
    cod_res INT NOT NULL AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,          -- ya NO es FK, viene de auth_db
    cod_fun INT NOT NULL,
    cantidad_entradas INT NOT NULL,
    fecha_reserva DATE NOT NULL,
    PRIMARY KEY(cod_res),
    CONSTRAINT fk_reserva_funcion FOREIGN KEY(cod_fun) REFERENCES tb_funcion(cod_fun)
);

CREATE TABLE tb_reserva_asiento(
    cod_res INT NOT NULL,
    cod_asiento INT NOT NULL,
    PRIMARY KEY(cod_res, cod_asiento),
    CONSTRAINT fk_ra_reserva FOREIGN KEY(cod_res) REFERENCES tb_reserva(cod_res),
    CONSTRAINT fk_ra_asiento FOREIGN KEY(cod_asiento) REFERENCES tb_asiento(cod_asiento)
);

-- aquí van TODOS tus INSERT de tb_pelicula, tb_sala, tb_funcion y tb_asiento
-- (los dejas exactamente igual a como los tenías, solo cambia el DROP/CREATE DATABASE de arriba)

-- =====================================
-- PELICULAS
-- =====================================

INSERT INTO tb_pelicula
(titulo,genero,duracion,clasificacion,fecha_estreno,precio_entrada)
VALUES
('Star Wars III','Ciencia Ficcion',192,'PG13','2025-12-19',25.50),
('Superman','Accion',145,'PG13','2025-07-11',16),
('Jurassic World','Aventura',150,'PG13','2025-07-02',20),
('Minecraft Movie','Aventura',110,'PG','2025-04-15',18.50),
('Backrooms','Terror',108,'PG18','2025-05-28',22.00),
('ChainsawMan Reze','Animacion',140,'PG18','2025-12-18',22.50),
('Killer Bean','Accion',169,'PG13','2025-05-23',27.00),
('Digital Circus','Animacion',122,'PG18','2025-09-05',22.50),
('Megamente 2','Animacion',140,'PG','2025-11-21',25.00),
('Free Fire: La pelicula','Accion',140,'PG','2025-11-21',25.00);
-- =====================================
-- SALAS
-- =====================================

INSERT INTO tb_sala(nombre,capacidad,tipo)
VALUES
('Sala 1',120,'Tradicional'),
('Sala 2',100,'3D'),
('Sala 3',80,'4DX'),
('Sala VIP',60,'VIP');

-- =====================================
-- FUNCIONES
-- =====================================


INSERT INTO tb_funcion
(fecha,hora,cod_pel,cod_sala)
VALUES



-- STAR WARS III (cod_pel = 1)
('2026-06-12','12:00:00',1,1),
('2026-06-12','15:00:00',1,2),
('2026-06-12','17:30:00',1,1),
('2026-06-12','21:00:00',1,3),
('2026-06-13','13:00:00',1,3),
('2026-06-13','16:00:00',1,2),
('2026-06-13','19:00:00',1,4),
('2026-06-13','21:30:00',1,2),
('2026-06-14','12:30:00',1,2),
('2026-06-14','15:00:00',1,4),
('2026-06-14','18:00:00',1,1),
('2026-06-14','21:00:00',1,3),

-- SUPERMAN (cod_pel = 2)
('2026-06-12','13:30:00',2,3),
('2026-06-12','16:00:00',2,1),
('2026-06-12','18:00:00',2,4),
('2026-06-12','21:00:00',2,2),
('2026-06-13','12:00:00',2,2),
('2026-06-13','14:30:00',2,3),
('2026-06-13','17:30:00',2,1),
('2026-06-13','20:00:00',2,3),
('2026-06-14','14:00:00',2,1),
('2026-06-14','16:30:00',2,2),
('2026-06-14','19:00:00',2,4),
('2026-06-14','22:30:00',2,2),

-- JURASSIC WORLD (cod_pel = 3)
('2026-06-12','12:30:00',3,1),
('2026-06-12','15:30:00',3,3),
('2026-06-12','17:00:00',3,2),
('2026-06-12','21:00:00',3,4),
('2026-06-13','12:00:00',3,4),
('2026-06-13','14:30:00',3,2),
('2026-06-13','18:30:00',3,3),
('2026-06-13','21:00:00',3,1),
('2026-06-14','13:00:00',3,2),
('2026-06-14','15:30:00',3,4),
('2026-06-14','18:30:00',3,1),
('2026-06-14','22:00:00',3,3),

-- MINECRAFT MOVIE (cod_pel = 4)
('2026-06-12','11:00:00',4,1),
('2026-06-12','14:00:00',4,3),
('2026-06-12','16:00:00',4,2),
('2026-06-12','19:30:00',4,3),
('2026-06-13','12:30:00',4,3),
('2026-06-13','15:00:00',4,1),
('2026-06-13','17:30:00',4,2),
('2026-06-13','20:30:00',4,4),
('2026-06-14','12:00:00',4,3),
('2026-06-14','14:30:00',4,1),
('2026-06-14','16:30:00',4,2),
('2026-06-14','19:00:00',4,4),

-- BACKROOMS (cod_pel = 5)
('2026-06-12','15:00:00',5,3),
('2026-06-12','18:00:00',5,1),
('2026-06-12','20:30:00',5,4),
('2026-06-12','22:00:00',5,2),
('2026-06-13','14:00:00',5,2),
('2026-06-13','16:30:00',5,4),
('2026-06-13','20:00:00',5,1),
('2026-06-13','22:30:00',5,3),
('2026-06-14','15:00:00',5,3),
('2026-06-14','17:30:00',5,1),
('2026-06-14','20:00:00',5,2),
('2026-06-14','23:00:00',5,4),

-- CHAINSAWMAN REZE (cod_pel = 6)
('2026-06-12','13:00:00',6,3),
('2026-06-12','16:30:00',6,1),
('2026-06-12','18:30:00',6,2),
('2026-06-12','21:30:00',6,4),
('2026-06-13','12:30:00',6,4),
('2026-06-13','15:00:00',6,1),
('2026-06-13','18:30:00',6,2),
('2026-06-13','21:30:00',6,4),
('2026-06-14','13:30:00',6,4),
('2026-06-14','16:00:00',6,2),
('2026-06-14','18:30:00',6,1),
('2026-06-14','21:00:00',6,3),

-- KILLER BEAN (cod_pel = 7)
('2026-06-12','11:30:00',7,2),
('2026-06-12','14:00:00',7,4),
('2026-06-12','16:30:00',7,1),
('2026-06-12','19:00:00',7,3),
('2026-06-13','13:30:00',7,4),
('2026-06-13','16:00:00',7,3),
('2026-06-13','19:30:00',7,2),
('2026-06-13','22:00:00',7,1),
('2026-06-14','12:00:00',7,2),
('2026-06-14','15:00:00',7,2),
('2026-06-14','18:00:00',7,3),
('2026-06-14','21:30:00',7,4),

-- DIGITAL CIRCUS (cod_pel = 8)
('2026-06-12','10:30:00',8,1),
('2026-06-12','13:00:00',8,3),
('2026-06-12','15:00:00',8,2),
('2026-06-12','18:30:00',8,4),
('2026-06-13','11:30:00',8,4),
('2026-06-13','14:00:00',8,2),
('2026-06-13','18:00:00',8,1),
('2026-06-13','21:00:00',8,3),
('2026-06-14','12:00:00',8,1),
('2026-06-14','14:30:00',8,2),
('2026-06-14','17:00:00',8,2),
('2026-06-14','20:30:00',8,3),

-- MEGAMENTE 2 (cod_pel = 9)
('2026-06-12','11:00:00',9,3),
('2026-06-12','14:30:00',9,1),
('2026-06-12','16:30:00',9,4),
('2026-06-12','18:00:00',9,2),
('2026-06-13','13:00:00',9,1),
('2026-06-13','15:30:00',9,2),
('2026-06-13','17:00:00',9,4),
('2026-06-13','20:00:00',9,4),
('2026-06-14','12:30:00',9,3),
('2026-06-14','14:30:00',9,2),
('2026-06-14','16:00:00',9,1),
('2026-06-14','19:30:00',9,2),

-- FREE FIRE: LA PELICULA (cod_pel = 10)
('2026-06-12','14:30:00',10,2),
('2026-06-12','17:00:00',10,1),
('2026-06-12','20:00:00',10,3),
('2026-06-12','22:00:00',10,4),
('2026-06-13','13:00:00',10,1),
('2026-06-13','15:30:00',10,2),
('2026-06-13','19:30:00',10,3),
('2026-06-13','22:00:00',10,4),
('2026-06-14','13:00:00',10,4),
('2026-06-14','14:30:00',10,1),
('2026-06-14','17:30:00',10,1),
('2026-06-14','21:00:00',10,2);


-- =====================================
-- ASIENTOS SALA 1 (120)
-- Filas A-J, asientos 1-12
-- =====================================

INSERT INTO tb_asiento(cod_sala, fila, numero)
WITH RECURSIVE nums AS (
    SELECT 1 n
    UNION ALL
    SELECT n + 1
    FROM nums
    WHERE n < 12
)
SELECT
    1,
    filas.fila,
    nums.n
FROM (
    SELECT 'A' fila UNION ALL
    SELECT 'B' UNION ALL
    SELECT 'C' UNION ALL
    SELECT 'D' UNION ALL
    SELECT 'E' UNION ALL
    SELECT 'F' UNION ALL
    SELECT 'G' UNION ALL
    SELECT 'H' UNION ALL
    SELECT 'I' UNION ALL
    SELECT 'J'
) filas
CROSS JOIN nums;

-- =====================================
-- ASIENTOS SALA 2 (100)
-- Filas A-J, asientos 1-10
-- =====================================

INSERT INTO tb_asiento(cod_sala, fila, numero)
WITH RECURSIVE nums AS (
    SELECT 1 n
    UNION ALL
    SELECT n + 1
    FROM nums
    WHERE n < 10
)
SELECT
    2,
    filas.fila,
    nums.n
FROM (
    SELECT 'A' fila UNION ALL
    SELECT 'B' UNION ALL
    SELECT 'C' UNION ALL
    SELECT 'D' UNION ALL
    SELECT 'E' UNION ALL
    SELECT 'F' UNION ALL
    SELECT 'G' UNION ALL
    SELECT 'H' UNION ALL
    SELECT 'I' UNION ALL
    SELECT 'J'
) filas
CROSS JOIN nums;

-- =====================================
-- ASIENTOS SALA 3 (80)
-- Filas A-H, asientos 1-10
-- =====================================

INSERT INTO tb_asiento(cod_sala, fila, numero)
WITH RECURSIVE nums AS (
    SELECT 1 n
    UNION ALL
    SELECT n + 1
    FROM nums
    WHERE n < 10
)
SELECT
    3,
    filas.fila,
    nums.n
FROM (
    SELECT 'A' fila UNION ALL
    SELECT 'B' UNION ALL
    SELECT 'C' UNION ALL
    SELECT 'D' UNION ALL
    SELECT 'E' UNION ALL
    SELECT 'F' UNION ALL
    SELECT 'G' UNION ALL
    SELECT 'H'
) filas
CROSS JOIN nums;

-- =====================================
-- ASIENTOS SALA VIP (60)
-- Filas A-F, asientos 1-10
-- =====================================

INSERT INTO tb_asiento(cod_sala, fila, numero)
WITH RECURSIVE nums AS (
    SELECT 1 n
    UNION ALL
    SELECT n + 1
    FROM nums
    WHERE n < 10
)
SELECT
    4,
    filas.fila,
    nums.n
FROM (
    SELECT 'A' fila UNION ALL
    SELECT 'B' UNION ALL
    SELECT 'C' UNION ALL
    SELECT 'D' UNION ALL
    SELECT 'E' UNION ALL
    SELECT 'F'
) filas
CROSS JOIN nums;




