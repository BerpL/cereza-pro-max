-- datos de prueba de Cereza Pro Max
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
