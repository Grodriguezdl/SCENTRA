CREATE DATABASE IF NOT EXISTS Expo3;
USE Expo3;
-- Tabla Empresa
CREATE TABLE Empresa (
    idEmpresa INT NOT NULL,
    Nombre VARCHAR(100) NOT NULL,
    Tipo VARCHAR(50) NOT NULL,
    Color VARCHAR(50) NOT NULL,
    Iniciales VARCHAR(10) NOT NULL,
    Logo VARCHAR(255) NOT NULL,
    Codigo_empresa VARCHAR(20) NOT NULL,
    PRIMARY KEY (idEmpresa)
) ENGINE=InnoDB;    

-- Tabla Usuarios
CREATE TABLE Usuarios (
    idUsuarios INT NOT NULL,
    Nombre VARCHAR(100) NOT NULL,
    Rol VARCHAR(100) NOT NULL,
    Contrasena VARCHAR(45) NOT NULL,
    idEmpresa INT NOT NULL,
    Estado_Union_Empresa VARCHAR(20),
    PRIMARY KEY (idUsuarios),
    FOREIGN KEY (idEmpresa) REFERENCES Empresa(idEmpresa)
) ENGINE=InnoDB;

-- Tabla Materia Prima
CREATE TABLE Materia_Prima (
    idMateria_Prima INT NOT NULL,
    idEmpresa INT NOT NULL,    -- nuevo campo para relacionar con empresa
    Nombre VARCHAR(100) NOT NULL,
    Unidad_de_medida VARCHAR(20) NOT NULL,
    Cantidad_disponible INT NOT NULL,
    Cantidad_minima INT NOT NULL,
    Precio_unitario DECIMAL(10,2) NOT NULL,
    Ultima_actualización_inv DATETIME NOT NULL,
    PRIMARY KEY (idMateria_Prima),
    FOREIGN KEY (idEmpresa) REFERENCES Empresa(idEmpresa)
) ENGINE=InnoDB;

-- Tabla Material empaque
CREATE TABLE Material_empaque (
    idEmpaque INT NOT NULL,
    idEmpresa INT NOT NULL,    -- nuevo campo para relacionar con empresa
    Nombre VARCHAR(100) NOT NULL,
    Unidad_de_medida VARCHAR(20) NOT NULL,
    Cantidad INT NOT NULL,
    Precio_unitario DECIMAL(10,2) NOT NULL,
    Ultima_actualizacion_empq DATETIME NOT NULL,
    PRIMARY KEY (idEmpaque),
    FOREIGN KEY (idEmpresa) REFERENCES Empresa(idEmpresa)
) ENGINE=InnoDB;

-- Tabla Productos Terminados
CREATE TABLE Productos_Terminados (
    idProductos INT NOT NULL,
    idEmpresa INT NOT NULL,    -- nuevo campo para relacionar con empresa
    Nombre VARCHAR(100) NOT NULL,
    Descripción VARCHAR(250),
    Cantidad_disponible INT NOT NULL,
    Costo_produccion DECIMAL(10,2) NOT NULL,
    Precio_venta DECIMAL(10,2) NOT NULL,
    Fecha_produccion DATE NOT NULL,
    Ultima_actualizacion_prod DATETIME NOT NULL,
    PRIMARY KEY (idProductos),
    FOREIGN KEY (idEmpresa) REFERENCES Empresa(idEmpresa)
) ENGINE=InnoDB;

-- Tabla Movimientos de Inventario (MODIFICADA para permitir ON DELETE SET NULL)
CREATE TABLE Movimientos_Inventario (
    idMovimiento INT NOT NULL,
    Tipo_movimiento VARCHAR(100) NOT NULL,
    Tipo_Item VARCHAR(50) NOT NULL, -- Indica si es 'Materia_Prima', 'Producto_Terminado' o 'Material_Empaque'
    idMateriaPrima_FK INT NULL,     -- Clave foránea para Materia_Prima, permite NULL
    idProductoTerminado_FK INT NULL, -- Clave foránea para Productos_Terminados, permite NULL
    idMaterialEmpaque_FK INT NULL,  -- Clave foránea para Material_empaque, permite NULL
    Cantidad INT NOT NULL,
    FechaMovimiento DATETIME NOT NULL,
    idUsuario INT NOT NULL,
    Costo_Unitario DECIMAL(20,2) NOT NULL,
    Costo_Total DECIMAL(20,2) NOT NULL,
    Comentarios_detalles VARCHAR(200),
    PRIMARY KEY (idMovimiento),
    FOREIGN KEY (idUsuario) REFERENCES Usuarios(idUsuarios),
    FOREIGN KEY (idMateriaPrima_FK) REFERENCES Materia_Prima(idMateria_Prima) ON DELETE SET NULL,
    FOREIGN KEY (idProductoTerminado_FK) REFERENCES Productos_Terminados(idProductos) ON DELETE SET NULL,
    FOREIGN KEY (idMaterialEmpaque_FK) REFERENCES Material_empaque(idEmpaque) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Datos para la Tabla Empresa (20 registros)
INSERT INTO Empresa (idEmpresa, Nombre, Tipo, Color, Iniciales, Logo, Codigo_empresa)
VALUES
    (1, 'Velas Aromáticas Luna',         'Fabricación', 'Lavanda',       'VAL', 'logo.png',    'EMP-001'),
    (2, 'Cera y Mechas S.L.',            'Proveedores', 'Blanco',        'CYM', 'logo.png',      'EMP-002'),
    (3, 'Envases EcoGreen',              'Empaque',     'Verde',         'EEG', 'logo.png', 'EMP-003'),
    (4, 'Aromas del Bosque',             'Fabricación', 'Pino',          'ADB', 'logo.png',   'EMP-004'),
    (5, 'Luz y Fragancia S.A.',          'Distribución','Amarillo',      'LYF', 'logo.png',  'EMP-005'),
    (6, 'Materias Primas del Sol',       'Proveedores', 'Naranja',       'MPS', 'logo.png',    'EMP-006'),
    (7, 'Creaciones Artesanales',        'Fabricación', 'Turquesa',      'CAR', 'logo.png',      'EMP-007'),
    (8, 'Distribuidora Fénix',           'Distribución','Rojo',          'DFX', 'logo.png',      'EMP-008'),
    (9, 'Soluciones de Empaque',         'Empaque',     'Gris',          'SDE', 'logo.png',      'EMP-009'),
    (10, 'Insumos Industriales S.A.',    'Proveedores', 'Azul Marino',   'IIS', 'logo.png',      'EMP-010'),
    (11, 'Velas del Alba',              'Fabricación', 'Celeste',       'VDA', 'logo.png',    'EMP-011'),
    (12, 'Esencias Naturales',          'Proveedores', 'Marrón',        'ESN', 'logo.png',      'EMP-012'),
    (13, 'Embalajes Seguros',           'Empaque',     'Nego',          'EMB', 'logo.png',      'EMP-013'),
    (14, 'Fabricantes Premium',         'Fabricación', 'Dorado',        'FPR', 'logo.png',      'EMP-014'),
    (15, 'Mayoristas Globales',         'Distribución','Plata',         'MGL', 'logo.png',      'EMP-015'),
    (16, 'Proveedores Top',             'Proveedores', 'Morado',        'PTO', 'logo.png',      'EMP-016'),
    (17, 'Artículos Aromáticos',        'Fabricación', 'Rosa',          'AAR', 'logo.png',      'EMP-017'),
    (18, 'Logística Express',           'Distribución','Magenta',       'LEX', 'logo.png',      'EMP-018'),
    (19, 'Suministros Ecológicos',      'Empaque',     'Lima',          'SEC', 'logo.png',      'EMP-019'),
    (20, 'Comercializadora Central',    'Fabricación', 'Blanco',        'CEN', 'logo.png',      'EMP-020');


-- Datos para la Tabla Usuarios (40 registros)
INSERT INTO Usuarios (idUsuarios, Nombre, Rol, Contrasena, idEmpresa, Estado_Union_Empresa)
VALUES
    (1, 'Gabriel Rodriguez',         'Administrador', 'admin001', 1, 'Activo'),
    (2, 'Alison Vicente',          'Empleado',      'emp002',   2, 'Activo'),
    (3, 'Livny Sahuay',         'Empleado',      'emp003',   3, 'Activo'),
    (4, 'Melvin Rodriguez',          'Administrador', 'admin004', 4, 'Activo'),
    (5, 'William Soto',           'Empleado',      'emp005',   5, 'Activo'),
    (6, 'Mario Castro',          'Administrador', 'admin006', 6, 'Activo'),
    (7, 'Elena Sánchez',         'Empleado',      'emp007',   7, 'Activo'),
    (8, 'Pedro López',           'Administrador', 'admin008', 8, 'Activo'),
    (9, 'Ana Martín',            'Empleado',      'emp009',   9, 'Activo'),
    (10, 'Javier Núñez',         'Administrador', 'admin010', 10, 'Activo'),
    (11, 'Isabel Pérez',         'Empleado',      'emp011',   11, 'Activo'),
    (12, 'Ricardo Vega',         'Administrador', 'admin012', 12, 'Activo'),
    (13, 'Lucía Ramos',          'Empleado',      'emp013',   13, 'Activo'),
    (14, 'Sergio Gil',           'Administrador', 'admin014', 14, 'Activo'),
    (15, 'Marta Díaz',           'Empleado',      'emp015',   15, 'Activo'),
    (16, 'Roberto Salas',        'Administrador', 'admin016', 16, 'Activo'),
    (17, 'Patricia Vidal',       'Empleado',      'emp017',   17, 'Activo'),
    (18, 'Alejandro Soto',       'Administrador', 'admin018', 18, 'Activo'),
    (19, 'Carolina Ortiz',       'Empleado',      'emp019',   19, 'Activo'),
    (20, 'Fernando Rey',         'Administrador', 'admin020', 20, 'Activo'),
    (21, 'Roberto Fuentes',      'Empleado',      'emp021', 1, 'Activo'),
    (22, 'Diana Morales',        'Empleado',      'emp022', 1, 'Activo'),
    (23, 'Jorge Vidal',          'Administrador', 'admin023', 2, 'Activo'),
    (24, 'Fernanda Castro',      'Empleado',      'emp024', 2, 'Activo'),
    (25, 'Gustavo Herrera',      'Empleado',      'emp025', 3, 'Activo'),
    (26, 'Carolina Vargas',      'Administrador', 'admin026', 3, 'Activo'),
    (27, 'Miguel Ángel Soto',    'Empleado',      'emp027', 4, 'Activo'),
    (28, 'Daniela Rojas',        'Empleado',      'emp028', 4, 'Activo'),
    (29, 'Pablo González',       'Administrador', 'admin029', 5, 'Activo'),
    (30, 'Valeria Méndez',       'Empleado',      'emp030', 5, 'Activo'),
    (31, 'Héctor Salazar',       'Empleado',      'emp031', 6, 'Activo'),
    (32, 'Natalia Bravo',        'Administrador', 'admin032', 6, 'Activo'),
    (33, 'Francisco Ramos',      'Empleado',      'emp033', 7, 'Activo'),
    (34, 'Sofía Paredes',        'Empleado',      'emp034', 7, 'Activo'),
    (35, 'Juan Carlos López',    'Administrador', 'admin035', 8, 'Activo'),
    (36, 'Mariana Soto',         'Empleado',      'emp036', 8, 'Activo'),
    (37, 'Luis Estrada',         'Empleado',      'emp037', 9, 'Activo'),
    (38, 'Gabriela Pinto',       'Administrador', 'admin038', 9, 'Activo'),
    (39, 'Emilio Guzmán',        'Empleado',      'emp039', 10, 'Activo'),
    (40, 'Rebeca Núñez',         'Empleado',      'emp040', 10, 'Activo');


-- Datos para la Tabla Materia_Prima (40 registros)
INSERT INTO Materia_Prima (idMateria_Prima, idEmpresa, Nombre, Unidad_de_medida, Cantidad_disponible, Cantidad_minima, Precio_unitario, Ultima_actualización_inv)
VALUES
    (1, 1, 'Cera de Soya',              'kg',         120,  20,  35.00, NOW()),
    (2, 2, 'Mechas de Algodón',         'unidad',     600, 100,   1.50, NOW()),
    (3, 3, 'Colorante Natural Violeta', 'ml',         250,  50,   0.80, NOW()),
    (4, 4, 'Aceite Esencial Pino',      'L',           30,   5, 120.00, NOW()),
    (5, 1, 'Alcohol Isopropílico',      'L',           40,  10,  30.00, NOW()),
    (6, 6, 'Glicerina Vegetal',         'L',           80,  15,  25.00, NOW()),
    (7, 7, 'Fragancia Cítrica',         'ml',         150,  30,   2.50, NOW()),
    (8, 8, 'Parafina Pura',             'kg',         500,  80,  12.00, NOW()),
    (9, 9, 'Pabilo Encerado',           'metro',     1000, 200,   0.10, NOW()),
    (10, 10, 'Aditivo Endurecedor',     'kg',          60,  10,   8.00, NOW()),
    (11, 11, 'Aceite Esencial Jazmín',  'ml',          75,  15,   3.20, NOW()),
    (12, 12, 'Cera de Abeja',           'kg',          90,  20,  45.00, NOW()),
    (13, 13, 'Pigmento Azul',           'gr',         100,  20,   0.50, NOW()),
    (14, 14, 'Mechas de Madera',        'unidad',     400,  50,   2.00, NOW()),
    (15, 15, 'Aceite de Coco Refinado', 'L',          110,  25,  28.00, NOW()),
    (16, 16, 'Colorante Verde Menta',   'ml',         180,  35,   0.90, NOW()),
    (17, 17, 'Pabilo de Cáñamo',        'metro',      700, 150,   0.15, NOW()),
    (18, 18, 'Aceite Esencial Lavanda', 'ml',         120,  25,   4.00, NOW()),
    (19, 19, 'Cera de Palma',           'kg',         300,  50,  20.00, NOW()),
    (20, 20, 'Fragancia Vainilla',      'ml',         200,  40,   2.80, NOW()),
    (21, 1, 'Pabilo de Cáñamo Orgánico', 'metro',      800,  150,   0.20, NOW()),
    (22, 2, 'Colorante Rojo Carmín',     'ml',         300,   60,   1.00, NOW()),
    (23, 3, 'Mechas Pre-encradas',       'unidad',     750,  120,   1.80, NOW()),
    (24, 4, 'Aceite Esencial Eucalipto', 'L',           25,    5, 110.00, NOW()),
    (25, 5, 'Cera de Coco',              'kg',         180,   30,  40.00, NOW()),
    (26, 6, 'Aditivo Estabilizador',     'kg',          70,   15,   9.50, NOW()),
    (27, 7, 'Fragancia Lavanda Francesa','ml',         160,   30,   3.50, NOW()),
    (28, 8, 'Cera Microcristalina',      'kg',         400,   70,  15.00, NOW()),
    (29, 9, 'Pabilo Trenzado',           'metro',      900,  180,   0.12, NOW()),
    (30, 10, 'Pigmento Negro Carbón',    'gr',         120,   25,   0.60, NOW()),
    (31, 1, 'Aceite Esencial Romero',    'ml',         100,   20,   3.80, NOW()),
    (32, 2, 'Cera de Arroz',             'kg',          60,   10,  32.00, NOW()),
    (33, 3, 'Mechas con Soporte',        'unidad',     550,  100,   2.50, NOW()),
    (34, 4, 'Colorante Amarillo Sol',    'ml',         220,   45,   0.70, NOW()),
    (35, 5, 'Aceite de Palma Sostenible','L',           90,   20,  27.00, NOW()),
    (36, 6, 'Glicol Propileno',          'L',          130,   25,  22.00, NOW()),
    (37, 7, 'Fragancia Sándalo',         'ml',         140,   28,   4.20, NOW()),
    (38, 8, 'Parafina de Alta Fusión',   'kg',         450,   75,  13.50, NOW()),
    (39, 9, 'Pabilo de Fibra Natural',   'metro',      650,  130,   0.18, NOW()),
    (40, 10, 'Aditivo UV Protector',     'kg',          50,    8,  10.00, NOW());


-- Datos para la Tabla Material_empaque (40 registros)
INSERT INTO Material_empaque (idEmpaque, idEmpresa, Nombre, Unidad_de_medida, Cantidad, Precio_unitario, Ultima_actualizacion_empq)
VALUES
    (1, 3, 'Frascos de Vidrio 150ml',    'unidad', 300,  8.50, NOW()),
    (2, 3, 'Tapas Metálicas',            'unidad', 300,  1.20, NOW()),
    (3, 5, 'Cajas de Cartón Pequeñas',   'unidad', 120,  5.00, NOW()),
    (4, 5, 'Etiquetas Eco',              'unidad', 500,  0.40, NOW()),
    (5, 2, 'Bolsas Plásticas',           'unidad', 200,  0.10, NOW()),
    (6, 6, 'Cajas con Ventana',          'unidad', 150,  6.50, NOW()),
    (7, 7, 'Bolsas de Organza',          'unidad', 400,  0.75, NOW()),
    (8, 8, 'Rollos de Burbuja',          'metro',   50, 15.00, NOW()),
    (9, 9, 'Cinta Adhesiva Kraft',       'unidad', 100,  2.20, NOW()),
    (10, 10, 'Sobres Acolchados',        'unidad',  80,  3.00, NOW()),
    (11, 11, 'Frascos de Cerámica 200ml','unidad', 250, 12.00, NOW()),
    (12, 12, 'Tapas de Corcho',          'unidad', 250,  1.50, NOW()),
    (13, 13, 'Cajas de Madera Pequeñas', 'unidad', 100,  8.00, NOW()),
    (14, 14, 'Etiquetas Personalizadas', 'unidad', 600,  0.60, NOW()),
    (15, 15, 'Bolsas de Papel Reciclado','unidad', 300,  0.25, NOW()),
    (16, 16, 'Protectores de Esquina',   'unidad', 500,  0.30, NOW()),
    (17, 17, 'Frascos de Cristal 100ml', 'unidad', 350,  7.00, NOW()),
    (18, 18, 'Cajas de Regalo Varias',   'unidad',  70,  9.50, NOW()),
    (19, 19, 'Etiquetas Biodegradables', 'unidad', 450,  0.55, NOW()),
    (20, 20, 'Bolsas de Tela Reutilizables','unidad',180, 1.80, NOW()),
    (21, 11, 'Frascos de Vidrio 100ml', 'unidad', 350,  7.00, NOW()),
    (22, 11, 'Tapas de Bambú',          'unidad', 350,  1.80, NOW()),
    (23, 12, 'Cajas de Regalo Cuadradas','unidad', 130,  7.50, NOW()),
    (24, 12, 'Etiquetas con Logo',      'unidad', 550,  0.50, NOW()),
    (25, 13, 'Bolsas de Yute',           'unidad', 220,  0.90, NOW()),
    (26, 13, 'Relleno de Papel Picado',  'kg',      40,  4.00, NOW()),
    (27, 14, 'Envases de Aluminio',     'unidad', 280,  4.50, NOW()),
    (28, 14, 'Dispensadores Pump',      'unidad', 280,  2.00, NOW()),
    (29, 15, 'Cintas Decorativas',      'metro',  200,  0.70, NOW()),
    (30, 15, 'Sobres de Papel Semilla', 'unidad', 100,  3.50, NOW()),
    (31, 16, 'Frascos Ámbar 50ml',      'unidad', 400,  5.00, NOW()),
    (32, 16, 'Cuentagotas de Vidrio',   'unidad', 400,  1.00, NOW()),
    (33, 17, 'Cajas de Madera Rectangulares','unidad', 90, 10.00, NOW()),
    (34, 17, 'Etiquetas Vintage',       'unidad', 500,  0.65, NOW()),
    (35, 18, 'Bolsas de Malla Algodón', 'unidad', 250,  1.20, NOW()),
    (36, 18, 'Sellos de Seguridad',     'unidad', 600,  0.20, NOW()),
    (37, 19, 'Frascos PET Transparentes','unidad', 320,  3.00, NOW()),
    (38, 19, 'Tapas Flip-Top',          'unidad', 320,  0.80, NOW()),
    (39, 20, 'Cajas Personalizadas',    'unidad',  60, 11.00, NOW()),
    (40, 20, 'Empaque Biodegradable',   'unidad', 200,  2.50, NOW());


-- Datos para la Tabla Productos_Terminados (40 registros)
INSERT INTO Productos_Terminados (idProductos, idEmpresa, Nombre, Descripción, Cantidad_disponible, Costo_produccion, Precio_venta, Fecha_produccion, Ultima_actualizacion_prod)
VALUES
    (1, 1, 'Vela Lavanda 150g',         'Vela aromática en frasco de vidrio',        50, 45.00,  90.00, '2025-06-01', NOW()),
    (2, 2, 'Kit Mechas y Mechones',     'Pack de 10 mechas + mechones de algodón',   80, 20.00,  40.00, '2025-06-05', NOW()),
    (3, 3, 'Envase 200ml Reutilizable', 'Envase eco-friendly para velas',            60, 10.00,  25.00, '2025-06-03', NOW()),
    (4, 4, 'Vela Pino Silvestre',       'Vela con aroma a pino natural',             70, 50.00, 100.00, '2025-06-07', NOW()),
    (5, 5, 'Pack Luz y Fragancia',      'Conjunto de 3 velas aromáticas',            40, 75.00, 150.00, '2025-06-08', NOW()),
    (6, 6, 'Vela Artesanal Canela',     'Vela rústica con aroma a canela',           30, 55.00, 110.00, '2025-05-28', NOW()),
    (7, 7, 'Jabón Artesanal Menta',     'Jabón natural hecho a mano',                100, 8.00,  18.00, '2025-06-10', NOW()),
    (8, 8, 'Difusor de Ambiente Limón', 'Difusor con varitas de bambú',              25, 60.00, 130.00, '2025-06-12', NOW()),
    (9, 9, 'Kit DIY Velas',             'Kit para hacer tus propias velas',          45, 30.00,  70.00, '2025-06-15', NOW()),
    (10, 10, 'Cera Perfumada Rosas',    'Pastillas de cera para quemador',           90, 15.00,  35.00, '2025-06-02', NOW()),
    (11, 11, 'Vela Jazmín Elegance',    'Vela premium con fragancia de jazmín',      65, 52.00, 105.00, '2025-06-04', NOW()),
    (12, 12, 'Bálsamo Labial Cera Abeja','Bálsamo natural hidratante',               110, 6.00,  15.00, '2025-06-18', NOW()),
    (13, 13, 'Vela Decorativa Azul',    'Vela tallada para decoración',              35, 40.00,  85.00, '2025-06-09', NOW()),
    (14, 14, 'Vela Aromática Mandarina','Vela de cera vegetal y cítricos',           75, 48.00,  95.00, '2025-06-11', NOW()),
    (15, 15, 'Kit de Iniciación Aromaterapia','Set con aceites y difusor',           20, 90.00, 180.00, '2025-06-14', NOW()),
    (16, 16, 'Vela Menta Fresca',       'Vela con aroma refrescante a menta',        55, 42.00,  88.00, '2025-06-06', NOW()),
    (17, 17, 'Vela Navideña Especias',  'Edición especial festiva',                  40, 60.00, 120.00, '2025-06-19', NOW()),
    (18, 18, 'Aceite para Masajes',     'Mezcla de aceites esenciales relajantes',   60, 35.00,  70.00, '2025-06-13', NOW()),
    (19, 19, 'Vela Vainilla Cremosa',   'Vela dulce con aroma intenso a vainilla',   85, 47.00,  93.00, '2025-06-17', NOW()),
    (20, 20, 'Spray Ambiente Bosque',   'Aromatizante natural para espacios',        70, 25.00,  50.00, '2025-06-01', NOW()),
    (21, 11, 'Vela Floral Jazmín',       'Vela aromática floral con notas de jazmín', 55, 58.00, 115.00, '2025-06-20', NOW()),
    (22, 12, 'Crema Humectante Rosa',    'Crema corporal con extracto de rosas',      90, 12.00,  28.00, '2025-06-18', NOW()),
    (23, 13, 'Set de Velas Cónicas',     'Colección de velas decorativas para eventos', 40, 38.00,  80.00, '2025-06-22', NOW()),
    (24, 14, 'Vela Zen Té Verde',        'Vela relajante con aroma a té verde',       65, 50.00, 100.00, '2025-06-19', NOW()),
    (25, 15, 'Kit de Aceites Esenciales','Variedad de aceites para aromaterapia',    30, 85.00, 170.00, '2025-06-17', NOW()),
    (26, 16, 'Vela Noche Estrellada',    'Vela con glitter y fragancia misteriosa',   50, 62.00, 125.00, '2025-06-23', NOW()),
    (27, 17, 'Jabón Exfoliante Coco',    'Jabón natural con partículas exfoliantes',  110, 9.50,  22.00, '2025-06-21', NOW()),
    (28, 18, 'Difusor Eléctrico',        'Difusor de aromas con temporizador',       20, 70.00, 140.00, '2025-06-16', NOW()),
    (29, 19, 'Vela Antimosquitos Citronela','Vela repelente de insectos natural',   70, 40.00,  80.00, '2025-06-24', NOW()),
    (30, 20, 'Perfume Sólido Vainilla',  'Perfume en barra con aroma dulce',          80, 18.00,  40.00, '2025-06-15', NOW()),
    (31, 1, 'Vela Relax Sándalo',        'Vela aromática para meditación y relajación', 45, 55.00, 110.00, '2025-06-25', NOW()),
    (32, 2, 'Kit de Fabricación Premium','Materiales de alta calidad para velas',    70, 65.00, 130.00, '2025-06-26', NOW()),
    (33, 3, 'Envase Geométrico',         'Diseño moderno para velas o decoraciones',   50, 15.00,  30.00, '2025-06-27', NOW()),
    (34, 4, 'Vela Cítrica Energía',      'Vela con aroma energizante de cítricos',     60, 48.00,  98.00, '2025-06-28', NOW()),
    (35, 5, 'Lámpara de Sal y Vela',     'Combinación decorativa y aromática',        25, 80.00, 160.00, '2025-06-29', NOW()),
    (36, 6, 'Vela Rústica Madera',       'Vela con textura rústica y aroma a madera', 35, 58.00, 115.00, '2025-06-20', NOW()),
    (37, 7, 'Set de Regalo Esencial',    'Pack de vela, jabón y difusor pequeño',     28, 70.00, 140.00, '2025-06-21', NOW()),
    (38, 8, 'Difusor de Ambiente Marino','Aroma fresco para espacios costeros',       30, 62.00, 125.00, '2025-06-22', NOW()),
    (39, 9, 'Vela Flotante Lirio',       'Vela diseñada para flotar en agua',         80, 20.00,  45.00, '2025-06-23', NOW()),
    (40, 10, 'Cera para Depilación Natural','Cera a base de ingredientes naturales',  100, 25.00,  55.00, '2025-06-24', NOW());


-- Datos para la Tabla Movimientos_Inventario (40 registros)
INSERT INTO Movimientos_Inventario (idMovimiento, Tipo_movimiento, Tipo_Item, idMateriaPrima_FK, idProductoTerminado_FK, idMaterialEmpaque_FK, Cantidad, FechaMovimiento, idUsuario, Costo_Unitario, Costo_Total, Comentarios_detalles)
VALUES
    (1, 'Entrada', 'Materia_Prima', 1, NULL, NULL, 60, '2025-06-20 10:00:00', 1, 35.00, 2100.00, 'Refuerzo de cera de soya'),
    (2, 'Entrada', 'Materia_Prima', 2, NULL, NULL, 200, '2025-06-18 11:30:00', 2, 1.50, 300.00, 'Compra de mechas adicionales'),
    (3, 'Salida',  'Materia_Prima', 1, NULL, NULL, 20, '2025-06-19 14:00:00', 1, 35.00, 700.00, 'Uso en elaboración de velas Luna'),
    (4, 'Salida',  'Productos_Terminados', NULL, 4, NULL, 10, '2025-06-15 09:15:00', 4, 50.00, 500.00, 'Producción de velas pino'),
    (5, 'Salida',  'Material_Empaque', NULL, NULL, 3, 15, '2025-06-17 16:45:00', 5, 10.00, 150.00, 'Uso de envases EcoGreen'),
    (6, 'Entrada', 'Material_Empaque', NULL, NULL, 1, 100, '2025-06-10 08:00:00', 3, 8.50, 850.00, 'Recepción de frascos de vidrio'),
    (7, 'Salida',  'Productos_Terminados', NULL, 1, NULL, 5, '2025-06-21 11:00:00', 1, 45.00, 225.00, 'Envío de velas Lavanda a cliente'),
    (8, 'Entrada', 'Materia_Prima', 4, NULL, NULL, 5, '2025-06-05 13:00:00', 4, 120.00, 600.00, 'Nueva compra de Aceite Esencial Pino'),
    (9, 'Salida',  'Material_Empaque', NULL, NULL, 4, 50, '2025-06-20 09:30:00', 5, 0.40, 20.00, 'Uso de etiquetas para nuevo lote'),
    (10, 'Salida', 'Materia_Prima', 5, NULL, NULL, 5, '2025-06-16 10:10:00', 1, 30.00, 150.00, 'Consumo de Alcohol Isopropílico en limpieza'),
    (11, 'Entrada', 'Materia_Prima', 6, NULL, NULL, 70, '2025-06-14 09:00:00', 6, 25.00, 1750.00, 'Adquisición de Glicerina Vegetal'),
    (12, 'Salida',  'Productos_Terminados', NULL, 7, NULL, 10, '2025-06-21 15:00:00', 7, 8.00, 80.00, 'Venta de Jabón Artesanal Menta'),
    (13, 'Entrada', 'Material_Empaque', NULL, NULL, 9, 80, '2025-06-11 10:45:00', 9, 2.20, 176.00, 'Stock de Cinta Adhesiva Kraft'),
    (14, 'Salida',  'Materia_Prima', 4, NULL, NULL, 2, '2025-06-18 16:00:00', 4, 120.00, 240.00, 'Uso de Aceite Esencial Pino en producción'),
    (15, 'Entrada', 'Productos_Terminados', NULL, 8, NULL, 30, '2025-06-13 12:00:00', 8, 60.00, 1800.00, 'Recepción de Difusores de Ambiente Limón'),
    (16, 'Salida',  'Material_Empaque', NULL, NULL, 2, 50, '2025-06-19 09:00:00', 3, 1.20, 60.00, 'Consumo de Tapas Metálicas'),
    (17, 'Entrada', 'Materia_Prima', 11, NULL, NULL, 20, '2025-06-07 14:30:00', 11, 3.20, 64.00, 'Compra de Aceite Esencial Jazmín'),
    (18, 'Salida',  'Productos_Terminados', NULL, 1, NULL, 10, '2025-06-20 17:00:00', 1, 45.00, 450.00, 'Venta adicional de Vela Lavanda'),
    (19, 'Entrada', 'Material_Empaque', NULL, NULL, 3, 50, '2025-06-09 11:00:00', 5, 5.00, 250.00, 'Reabastecimiento de Cajas de Cartón Pequeñas'),
    (20, 'Salida', 'Materia_Prima', 2, NULL, NULL, 100, '2025-06-12 10:00:00', 2, 1.50, 150.00, 'Uso de Mechas de Algodón en kit'),
    (21, 'Entrada', 'Materia_Prima', 21, NULL, NULL, 50, '2025-06-22 09:40:00', 21, 0.20, 10.00, 'Llegada de nuevo pabilo de cáñamo'),
    (22, 'Salida',  'Productos_Terminados', NULL, 21, NULL, 8, '2025-06-23 14:00:00', 21, 58.00, 464.00, 'Venta de Vela Floral Jazmín'),
    (23, 'Entrada', 'Material_Empaque', NULL, NULL, 21, 150, '2025-06-21 10:30:00', 22, 7.00, 1050.00, 'Reposición de frascos de vidrio 100ml'),
    (24, 'Salida',  'Materia_Prima', 24, NULL, NULL, 3, '2025-06-20 11:15:00', 27, 110.00, 330.00, 'Uso de Aceite Esencial Eucalipto en producción'),
    (25, 'Entrada', 'Productos_Terminados', NULL, 25, NULL, 15, '2025-06-19 15:30:00', 29, 85.00, 1275.00, 'Recepción de Kit de Aceites Esenciales'),
    (26, 'Salida',  'Material_Empaque', NULL, NULL, 23, 10, '2025-06-24 09:00:00', 23, 7.50, 75.00, 'Empaque de sets de velas cónicas'),
    (27, 'Entrada', 'Materia_Prima', 25, NULL, NULL, 40, '2025-06-17 13:00:00', 25, 40.00, 1600.00, 'Compra de cera de coco'),
    (28, 'Salida',  'Productos_Terminados', NULL, 28, NULL, 5, '2025-06-25 11:45:00', 28, 70.00, 350.00, 'Venta de Difusor Eléctrico'),
    (29, 'Entrada', 'Material_Empaque', NULL, NULL, 25, 80, '2025-06-16 08:30:00', 26, 0.90, 72.00, 'Reabastecimiento de bolsas de yute'),
    (30, 'Salida',  'Materia_Prima', 27, NULL, NULL, 10, '2025-06-26 10:00:00', 33, 3.50, 35.00, 'Consumo de Fragancia Lavanda Francesa'),
    (31, 'Entrada', 'Productos_Terminados', NULL, 31, NULL, 20, '2025-06-27 12:00:00', 31, 55.00, 1100.00, 'Producción de Vela Relax Sándalo'),
    (32, 'Salida',  'Material_Empaque', NULL, NULL, 27, 30, '2025-06-28 14:00:00', 30, 4.50, 135.00, 'Uso de envases de aluminio para cremas'),
    (33, 'Entrada', 'Materia_Prima', 31, NULL, NULL, 30, '2025-06-29 09:00:00', 32, 3.80, 114.00, 'Adquisición de Aceite Esencial Romero'),
    (34, 'Salida',  'Productos_Terminados', NULL, 34, NULL, 7, '2025-06-25 16:30:00', 34, 48.00, 336.00, 'Venta de Vela Cítrica Energía'),
    (35, 'Entrada', 'Material_Empaque', NULL, NULL, 29, 100, '2025-06-24 11:00:00', 35, 0.70, 70.00, 'Stock de cintas decorativas'),
    (36, 'Salida',  'Materia_Prima', 35, NULL, NULL, 8, '2025-06-23 10:15:00', 36, 27.00, 216.00, 'Consumo de Aceite de Palma Sostenible'),
    (37, 'Entrada', 'Productos_Terminados', NULL, 37, NULL, 10, '2025-06-22 15:00:00', 37, 70.00, 700.00, 'Preparación de Set de Regalo Esencial'),
    (38, 'Salida',  'Material_Empaque', NULL, NULL, 31, 40, '2025-06-21 09:30:00', 38, 5.00, 200.00, 'Uso de frascos ámbar para aceites'),
    (39, 'Entrada', 'Materia_Prima', 39, NULL, NULL, 150, '2025-06-20 14:00:00', 39, 0.18, 27.00, 'Llegada de pabilo de fibra natural'),
    (40, 'Salida',  'Productos_Terminados', NULL, 40, NULL, 12, '2025-06-29 17:00:00', 40, 25.00, 300.00, 'Venta de Cera para Depilación Natural');
