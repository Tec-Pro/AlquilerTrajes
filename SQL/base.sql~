create database alquilerTraje;

use alquilerTraje;

CREATE  TABLE `alquilerTraje`.`clientes` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `nombre` VARCHAR(100) NOT NULL ,
  `telefono` VARCHAR(60) NULL ,
  `celular` VARCHAR(60) NULL ,
  `direccion` VARCHAR(120) NULL , 
  `dni` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) );

CREATE  TABLE `alquilerTraje`.`articulos` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `tipo` VARCHAR(45) NOT NULL ,
  `modelo` VARCHAR(120) NULL ,
  `marca` VARCHAR(45) NULL ,
  `stock` INT NULL DEFAULT 0 ,
  `precio_compra` FLOAT NULL DEFAULT 0 ,
  `precio_alquiler` FLOAT NULL DEFAULT 0 ,
  `descripcion` VARCHAR(200) NULL ,
  `talle` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`));

CREATE  TABLE `alquilerTraje`.`ambos` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `nombre` VARCHAR(120) NOT NULL ,
  `marca` VARCHAR(45) NULL ,
  `stock` INT NULL DEFAULT 0 ,
  `precio_alquiler` FLOAT NULL DEFAULT 0 ,
  `descripcion` VARCHAR(200) NULL ,
  `talle` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`));

CREATE  TABLE `alquilerTraje`.`articulos_ambos` (
  `id` INT NOT NULL AUTO_INCREMENT ,
    `id_ambo` INT NOT NULL,
    `id_articulo` INT NOT NULL,
  PRIMARY KEY (`id`));

CREATE  TABLE `alquilerTraje`.`bajas` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `tipo` VARCHAR(45) NOT NULL ,
  `modelo` VARCHAR(120) NULL ,
  `marca` VARCHAR(45) NULL ,
  `descripcion` VARCHAR(200) NULL ,
  `talle` VARCHAR(45) NULL ,
  `fecha`DATE NULL,
  `cobro` FLOAT NULL,
  PRIMARY KEY (`id`));

CREATE  TABLE `alquilerTraje`.`reservas` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `fecha_reserva` DATE NOT NULL ,
  `fecha_entrega_reserva` DATE NOT NULL ,
  `id_cliente` INT NOT NULL,
  PRIMARY KEY (`id`));

CREATE  TABLE `alquilerTraje`.`articulos_reservas` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `id_articulo` INT NOT NULL,
  `id_reserva` INT NOT NULL,
  PRIMARY KEY (`id`));

CREATE  TABLE `alquilerTraje`.`remitos` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `numero` INT NOT NULL ,
  `fecha_de_remito` DATE NOT NULL ,
  `id_cliente` INT NOT NULL,
  `total` FLOAT NOT NULL,
  `senia` FLOAT NOT NULL,    
  PRIMARY KEY (`id`));

CREATE  TABLE `alquilerTraje`.`articulos_remitos` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `id_articulo` INT NOT NULL,
  `id_remito` INT NOT NULL,
  PRIMARY KEY (`id`));

 GRANT ALL PRIVILEGES ON *.* TO 'tecpro'@'%'  IDENTIFIED BY 'tecpro'; 
 GRANT ALL PRIVILEGES ON *.* TO 'tecpro'@'localhost'  IDENTIFIED BY 'tecpro' WITH GRANT OPTION; 

