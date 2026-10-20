-- Cereza Pro Max - script de base de datos
DROP DATABASE IF EXISTS cereza_pro_max;
CREATE DATABASE cereza_pro_max;
USE cereza_pro_max;

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

INSERT INTO rol (nombre) VALUES ('ADMINISTRADOR'), ('SUPERVISOR'), ('AGENTE');

INSERT INTO usuario (nombre, correo, password, id_rol) VALUES
('Administrador', 'admin@cereza.com', 'admin123', 1),
('Supervisor', 'supervisor@cereza.com', 'super123', 2),
('Agente', 'agente@cereza.com', 'agente123', 3);

INSERT INTO cliente (nombre, telefono, correo, fecha_registro) VALUES
('Javier Meliton', '+51990456701', 'jmeliton@gmail.com', NOW()),
('INBISERV SAC', '+51973340494', 'contacto@inbiserv.com', NOW()),
('Yony Palacios', '+51979336521', 'ypalacios@gmail.com', NOW()),
('Luis Alberto Rojas', '+51958662218', 'larojas@gmail.com', NOW()),
('ITAN Comercial', '+51926052598', 'ventas@itan.com', NOW());

INSERT INTO agente (nombre, turno, id_usuario) VALUES
('Luis Quispe', 'MANANA', 3),
('Brenda Cornejo', 'TARDE', NULL),
('Ricardo Ramirez', 'NOCHE', NULL);

INSERT INTO categoria (nombre, descripcion) VALUES
('Reserva de servicio', 'Solicitud de taxi corporativo'),
('Reclamo', 'Quejas por el servicio recibido'),
('Consulta de tarifas', 'Informacion de precios y rutas');

INSERT INTO campana (nombre) VALUES
('Promocion octubre'), ('Recordatorio de pago');

INSERT INTO atencion (id_cliente, id_agente, id_categoria, fecha, estado) VALUES
(1, 1, 1, '2026-10-05 09:15:00', 'ATENDIDA'),
(2, 2, 3, '2026-10-06 11:40:00', 'ATENDIDA'),
(3, 1, 2, '2026-10-08 16:05:00', 'PENDIENTE');

INSERT INTO mensaje (id_atencion, id_campana, contenido, estado_envio, fecha_envio) VALUES
(1, 1, 'Hola, su reserva fue confirmada para las 10:00 am', 'ENVIADO', '2026-10-05 09:16:00'),
(1, 1, 'Recuerde tener su DNI a la mano', 'ENVIADO', '2026-10-05 09:17:00'),
(2, NULL, 'La tarifa al aeropuerto es de S/ 60', 'ENVIADO', '2026-10-06 11:41:00'),
(3, 2, 'Estamos revisando su reclamo, le responderemos en 24 horas', 'PENDIENTE', NULL);
