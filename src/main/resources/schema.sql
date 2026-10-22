-- se recrea la base de datos de prueba en cada arranque (spring.sql.init.mode=always)
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS mensaje;
DROP TABLE IF EXISTS atencion;
DROP TABLE IF EXISTS agente;
DROP TABLE IF EXISTS campana;
DROP TABLE IF EXISTS categoria;
DROP TABLE IF EXISTS cliente;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS rol;
SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE rol (
	id_rol INT AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(30) NOT NULL
);

CREATE TABLE usuario (
	id_usuario INT AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(100) NOT NULL,
	correo VARCHAR(100) NOT NULL UNIQUE,
	password VARCHAR(100) NOT NULL,
	estado VARCHAR(20) DEFAULT 'ACTIVO',
	id_rol INT NOT NULL,
	CONSTRAINT fk_usuario_rol FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);

CREATE TABLE cliente (
	id_cliente INT AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(100) NOT NULL,
	telefono VARCHAR(15) NOT NULL,
	correo VARCHAR(100),
	estado VARCHAR(20) DEFAULT 'ACTIVO',
	fecha_registro DATETIME
);

CREATE TABLE agente (
	id_agente INT AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(100) NOT NULL,
	turno VARCHAR(20),
	estado VARCHAR(20) DEFAULT 'ACTIVO',
	id_usuario INT,
	CONSTRAINT fk_agente_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

CREATE TABLE categoria (
	id_categoria INT AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(60) NOT NULL,
	descripcion VARCHAR(200),
	estado VARCHAR(20) DEFAULT 'ACTIVO'
);

CREATE TABLE campana (
	id_campana INT AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(100) NOT NULL,
	estado VARCHAR(20) DEFAULT 'ACTIVA'
);

CREATE TABLE atencion (
	id_atencion INT AUTO_INCREMENT PRIMARY KEY,
	id_cliente INT NOT NULL,
	id_agente INT NOT NULL,
	id_categoria INT NOT NULL,
	fecha DATETIME NOT NULL,
	estado VARCHAR(20) DEFAULT 'PENDIENTE',
	CONSTRAINT fk_atencion_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
	CONSTRAINT fk_atencion_agente FOREIGN KEY (id_agente) REFERENCES agente(id_agente),
	CONSTRAINT fk_atencion_categoria FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);

CREATE TABLE mensaje (
	id_mensaje INT AUTO_INCREMENT PRIMARY KEY,
	id_atencion INT NOT NULL,
	id_campana INT,
	contenido VARCHAR(500) NOT NULL,
	estado_envio VARCHAR(20) DEFAULT 'PENDIENTE',
	fecha_envio DATETIME,
	CONSTRAINT fk_mensaje_atencion FOREIGN KEY (id_atencion) REFERENCES atencion(id_atencion),
	CONSTRAINT fk_mensaje_campana FOREIGN KEY (id_campana) REFERENCES campana(id_campana)
);
