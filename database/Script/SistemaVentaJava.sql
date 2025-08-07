-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS bd_sistemaVentas;
USE bd_sistemaVentas;

-- Tabla de usuarios
CREATE TABLE tb_usuarios (
    idUsuario INT(11) AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL,
    apellido VARCHAR(30) NOT NULL,
    usuario VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    estado INT(1) NOT NULL
);

-- Insertar usuario de ejemplo
INSERT INTO tb_usuarios(nombre, apellido, usuario, password, telefono, estado) 
VALUES ("Diego", "Arroyo", "diego", "2006", "926450789", 1);

-- Tabla de clientes
CREATE TABLE tb_cliente (
    idCliente INT(11) AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL,
    apellido VARCHAR(30) NOT NULL,
    cedula VARCHAR(30) NOT NULL,
    direccion VARCHAR(100) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    estado INT(1) NOT NULL
);

-- Tabla de categorías
CREATE TABLE tb_Categoria (
    idCategoria INT(11) AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(200) NOT NULL,
    estado INT(1) NOT NULL
);

-- Tabla de productos
CREATE TABLE tb_producto (
    idProducto INT(11) AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    cantidad INT(11) NOT NULL,
    precio DOUBLE(10,2) NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    porcentajeIva INT(2) NOT NULL,
    idCategoria INT(11) NOT NULL,
    estado INT(1) NOT NULL
);

-- Tabla de cabecera de venta
CREATE TABLE tb_cabecera_Venta (
    idCabeceraVenta INT(11) AUTO_INCREMENT PRIMARY KEY,
    idCliente INT(11) NOT NULL,
    valorPagar DOUBLE(10,2) NOT NULL,
    fechaVenta DATE NOT NULL,
    estado INT(1) NOT NULL
);

-- Tabla de detalle de venta
CREATE TABLE tb_detalleVenta (
    idDetalleVenta INT(11) AUTO_INCREMENT PRIMARY KEY,
    idCabeceraVenta INT(11),
    idProducto INT(11) NOT NULL,
    cantidad INT(11) NOT NULL,
    precioUnitario DOUBLE(10,2),
    subTotal DOUBLE(10,2),
    iva DOUBLE(10,2),
    totalPagar DOUBLE(10,2),
    estado INT(1) NOT NULL
);

-- Consultas útiles
SELECT * FROM tb_usuarios;
SELECT * FROM tb_cliente;
SELECT * FROM tb_producto;
SELECT * FROM tb_Categoria;
SELECT * FROM tb_cabecera_Venta;
SELECT * FROM tb_detalleVenta;

-- Descripciones de tablas
DESCRIBE tb_detalleVenta;
DESCRIBE tb_cabecera_Venta;

-- Mostrar tablas y bases de datos
SHOW TABLES;
SHOW DATABASES;

-- Consulta de ventas con cliente
SELECT cv.idCabeceraVenta AS idVenta, cv.fechaVenta AS fecha, 
       CONCAT(c.nombre, ' ', c.apellido) AS cliente, cv.valorPagar AS total
FROM tb_cabecera_Venta cv
INNER JOIN tb_cliente c ON cv.idCliente = c.idCliente
ORDER BY cv.fechaVenta DESC;


