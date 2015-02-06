create database cacique;

use cacique;

CREATE  TABLE `cacique`.`clientes` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `nombre` VARCHAR(100) NOT NULL ,
  `telefono` VARCHAR(60) NULL ,
  `celular` VARCHAR(60) NULL ,
  `direccion` VARCHAR(120) NULL ,
  `nacimiento` DATE NULL ,
  `facebook` VARCHAR(60) NULL ,
  `email` VARCHAR(60) NULL ,
  `dni` VARCHAR(45) NULL ,
	cuenta FLOAT NULL DEFAULT 0,
  PRIMARY KEY (`id`) );

CREATE  TABLE `cacique`.`proveedors` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `nombre` VARCHAR(100) NOT NULL ,
  `cuit` VARCHAR(45) NULL ,
  `direccion` VARCHAR(100) NULL ,
  `telefono` VARCHAR(45) NULL ,
  `celular` VARCHAR(45) NULL ,
  `email` VARCHAR(45) NULL ,
  `cuenta_corriente` FLOAT NULL DEFAULT 0,
  `forma_de_pago` VARCHAR(45) NULL ,
  PRIMARY KEY (`id`) );

CREATE  TABLE `cacique`.`articulos` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `codigo` VARCHAR(45) NOT NULL ,
  `nombre` VARCHAR(45) NULL ,
  `marca` VARCHAR(45) NULL ,
  `stock_actual` FLOAT NULL DEFAULT 0 ,
  `stock_minimo` FLOAT NULL DEFAULT 0 ,
  `precio_compra` FLOAT NULL DEFAULT 0 ,
  `precio_venta` FLOAT NULL DEFAULT 0 ,
  `descripcion` VARCHAR(200) NULL ,
  `ultima_compra` DATE NULL ,
  `proveedor_id` INT NULL ,
  PRIMARY KEY (`id`, `codigo`) );
ALTER TABLE `cacique`.`articulos` ADD COLUMN `equivalencia_1` VARCHAR(45) NULL  AFTER `proveedor_id` , ADD COLUMN `equivalencia_2` VARCHAR(45) NULL  AFTER `equivalencia_1` , ADD COLUMN `equivalencia_3` VARCHAR(45) NULL  AFTER `equivalencia_2` ;

create table usuarios (
	id integer not null auto_increment,
	nombre varchar(50) default 'cacique',
	pass varchar(50) default 'cacique',
	primary key(id));

CREATE  TABLE `cacique`.`emails` (
  `email` VARCHAR(45) NOT NULL ,
  `password` VARCHAR(45) NULL ,
  PRIMARY KEY (`email`) );


ALTER TABLE `cacique`.`emails` ADD COLUMN `id` VARCHAR(45) NOT NULL  AFTER `password` 
, DROP PRIMARY KEY 
, ADD PRIMARY KEY (`email`, `id`) ;


ALTER TABLE `cacique`.`emails` CHANGE COLUMN `id` `id` INT NOT NULL AUTO_INCREMENT  
, DROP PRIMARY KEY 
, ADD PRIMARY KEY (`id`, `email`) ;

/*Versión 2.0 !*/
CREATE  TABLE `cacique`.`ips` (
  `id` INT NOT NULL ,
  `remoto` VARCHAR(45) NULL ,
  `servidor` INT(2) NULL ,
  PRIMARY KEY (`id`) );
ALTER TABLE `cacique`.`ips` CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT  ;
ALTER TABLE `cacique`.`ips` CHANGE COLUMN `remoto` `remoto` VARCHAR(45) NULL DEFAULT 'localhost'  , CHANGE COLUMN `servidor` `servidor` INT(2) NULL DEFAULT 0  ;


 GRANT ALL PRIVILEGES ON *.* TO 'tecpro'@'%'  IDENTIFIED BY 'tecpro'; 
 GRANT ALL PRIVILEGES ON *.* TO 'tecpro'@'localhost'  IDENTIFIED BY 'tecpro' WITH GRANT OPTION; 

CREATE  TABLE `cacique`.`envios` (
  `fecha` DATE NOT NULL ,
  `enviado` VARCHAR(5) NULL ,
  PRIMARY KEY (`fecha`) );
ALTER TABLE `cacique`.`envios` ADD COLUMN `id` INT NOT NULL AUTO_INCREMENT  AFTER `enviado` 
, DROP PRIMARY KEY 
, ADD PRIMARY KEY (`id`, `fecha`) , RENAME TO  `cacique`.`envios` ;

ALTER TABLE `cacique`.`envios` CHANGE COLUMN `enviado` `enviado` INT NULL DEFAULT NULL  ;

create table cacique.ventas (
    id integer not null auto_increment,
    monto float,
    cliente_id integer,
    fecha date not null,
    pago int,
	pago_id INT NULL DEFAULT NULL,
    PRIMARY KEY (`id`) );

create table cacique.compras (
    id integer not null auto_increment,
    monto float,
    proveedor_id integer,
    fecha date not null,
	pago INT(11) NULL DEFAULT 0,
	fecha_pago DATE NULL,
	descuento FLOAT NULL DEFAULT 0,
	pago_id INT NULL,
    PRIMARY KEY (`id`) );

create table clientes_articulos(
    id integer not null auto_increment,
    cliente_id integer,
    articulo_id integer,
    cantidad float not null,
	precio_final float,
	check (cantidad>0),
	check (precio_final>0),
    primary key(id) );


create table articulos_ventas (
    id integer not null auto_increment,
    venta_id integer,
    articulo_id integer,
    cantidad float not null,
	precio_final float,
	check (cantidad>0),
	check (precio_final>0),
    primary key(id) );


create table articulos_compras (
    id integer not null auto_increment,
    compra_id integer,
    articulo_id integer,
    cantidad float not null,
    precio_final float,
	check (cantidad>0),
	check (precio_final>0),
    primary key(id));

CREATE  TABLE `cacique`.`pagos` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `fecha` DATE NULL DEFAULT NULL,
  `monto` FLOAT NULL ,
  `proveedor_id` INT NULL ,
	cliente_id INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`) );

CREATE  TABLE `cacique`.`autos` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `patente` VARCHAR(45) NULL ,
  `modelo` VARCHAR(45) NULL ,
  `marca` VARCHAR(45) NULL ,
  `cliente_id` INT NULL ,
  PRIMARY KEY (`id`) );

ALTER TABLE `cacique`.`autos` ADD COLUMN `ult_cambio_aceite` DATE NULL  AFTER `cliente_id` ;


CREATE  TABLE `cacique`.`trabajos` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `fecha` DATE NULL ,
  `kilometraje` VARCHAR(45) NULL ,
  `observaciones` VARCHAR(200) NULL ,
  `aceite_caja` INT NULL ,
  `aceite_diferencial` INT NULL ,
  `filtro_aire` INT NULL ,
  `filtro_combustible` INT NULL ,
  `filtro_aceite` INT NULL ,
  `filtro_habitaculo` INT NULL ,
  `liquido_freno` INT NULL ,
  `anticongelante` INT NULL ,
  `correa_multicanal` INT NULL ,
  `tensor_correa_multicanal` INT NULL ,
  `correa_distribucion` INT NULL ,
  `tensores` INT NULL ,
  `bomba_agua` INT NULL ,
  `bateria` INT NULL ,
  `tipo_bateria` VARCHAR(45) NULL ,
  `importe_bateria` FLOAT NULL ,
  `descripcion_bateria` VARCHAR(200) NULL ,
  `costo` FLOAT NULL ,
  `descripcion_adicional` VARCHAR(200) NULL ,
  `cliente_id` INT NULL ,
  `auto_id` INT NULL ,
  PRIMARY KEY (`id`) );

create table cacique.presupuestos (
    id integer not null auto_increment,
    monto float,
    cliente_id integer,
    fecha date not null,    
    PRIMARY KEY (`id`) );

create table articulos_presupuestos (
    id integer not null auto_increment,
    presupuesto_id integer,
    articulo_id integer,
    cantidad float not null,
	precio_final float,
	check (cantidad>0),
	check (precio_final>0),
    primary key(id) );
ALTER TABLE `cacique`.`trabajos` ADD COLUMN `aceite_motor` INT(11) NULL  AFTER `auto_id` ;

ALTER TABLE `cacique`.`trabajos` CHANGE COLUMN `aceite_caja` `aceite_caja` VARCHAR(6) NULL DEFAULT NULL  , CHANGE COLUMN `aceite_diferencial` `aceite_diferencial` VARCHAR(6) NULL DEFAULT NULL  , CHANGE COLUMN `filtro_aire` `filtro_aire` VARCHAR(6) NULL DEFAULT NULL  , CHANGE COLUMN `filtro_combustible` `filtro_combustible` VARCHAR(6) NULL DEFAULT NULL  , CHANGE COLUMN `filtro_aceite` `filtro_aceite` VARCHAR(6) NULL DEFAULT NULL  , CHANGE COLUMN `filtro_habitaculo` `filtro_habitaculo` VARCHAR(6) NULL DEFAULT NULL  , CHANGE COLUMN `liquido_freno` `liquido_freno` VARCHAR(6) NULL DEFAULT NULL  , CHANGE COLUMN `anticongelante` `anticongelante` VARCHAR(6) NULL DEFAULT NULL  , CHANGE COLUMN `correa_multicanal` `correa_multicanal` VARCHAR(6) NULL DEFAULT NULL  , CHANGE COLUMN `tensor_correa_multicanal` `tensor_correa_multicanal` VARCHAR(6) NULL DEFAULT NULL  , CHANGE COLUMN `correa_distribucion` `correa_distribucion` VARCHAR(6) NULL DEFAULT NULL  , CHANGE COLUMN `tensores` `tensores` VARCHAR(6) NULL DEFAULT NULL  , CHANGE COLUMN `bomba_agua` `bomba_agua` VARCHAR(6) NULL DEFAULT NULL  , CHANGE COLUMN `bateria` `bateria` VARCHAR(6) NULL DEFAULT NULL  , CHANGE COLUMN `aceite_motor` `aceite_motor` VARCHAR(6) NULL DEFAULT NULL  ;
ALTER TABLE `cacique`.`trabajos` CHANGE COLUMN `bateria` `bateria` INT(11) NULL DEFAULT NULL  ;
ALTER TABLE `cacique`.`trabajos` ADD COLUMN `mecanico` VARCHAR(45) NULL  AFTER `aceite_motor` ;
ALTER TABLE `cacique`.`trabajos` ADD COLUMN `aceite_usa` VARCHAR(45) NULL  AFTER `mecanico` ;
ALTER TABLE `cacique`.`presupuestos` ADD COLUMN `patente` VARCHAR(45) NULL  AFTER `fecha` , ADD COLUMN `realizado` VARCHAR(70) NULL  AFTER `patente` ;
ALTER TABLE `cacique`.`trabajos` ADD COLUMN `proximo_cambio` VARCHAR(45) NULL  AFTER `auto_id` ;
ALTER TABLE `cacique`.`pagos` ADD COLUMN `descripcion` VARCHAR(200) NULL  AFTER `cliente_id` ;

CREATE  TABLE `cacique`.`demos` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `fecha` DATE NULL ,
  `dias` INT NULL DEFAULT 15 ,
  PRIMARY KEY (`id`) );
ALTER TABLE `cacique`.`demos` ADD COLUMN `activado` INT(1) NULL  AFTER `dias` ;


