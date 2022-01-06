CREATE TABLE `Mascotas` (
	`id` int NOT NULL AUTO_INCREMENT,
	`nombre` varchar(25) NOT NULL,
	`especie` varchar(25) NOT NULL,
	`color` varchar(25) NOT NULL,
	`sexo` varchar(25) NOT NULL,
	`enfermedades` varchar(255) NOT NULL,
	`anotaciones` varchar(255) NOT NULL,
	`vacunas` varchar(255) NOT NULL,
	`chip` BOOLEAN NOT NULL,
	`codigoCliente` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Roles` (
	`id` int NOT NULL AUTO_INCREMENT,
	`rol` varchar(25) NOT NULL,
	`password` varchar(25) NOT NULL,
	`codigoVeterinario` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Medicamentos` (
	`id` int NOT NULL AUTO_INCREMENT,
	`nombre` varchar(25) NOT NULL,
	`precio` int NOT NULL,
	`codigoProveedor` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Veterinarios` (
	`id` int NOT NULL AUTO_INCREMENT,
	`nombre` varchar(25) NOT NULL,
	`direccion` varchar(25) NOT NULL,
	`telefono` varchar(25) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Clientes` (
	`id` int NOT NULL AUTO_INCREMENT,
	`nombre` varchar(25) NOT NULL,
	`direccion` varchar(100) NOT NULL,
	`localidad` varchar(25) NOT NULL,
	`telefono` varchar(25) NOT NULL,
	`email` varchar(25) NOT NULL,
	`CP` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Consultas` (
	`id` int NOT NULL AUTO_INCREMENT,
	`fecha` TIMESTAMP NOT NULL,
	`diagnostico` varchar(255) NOT NULL,
	`tratamiento` varchar(255) NOT NULL,
	`codigoMedicamento` int NOT NULL,
	`codigoVeterinario` int NOT NULL,
	`codigoMascota` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Facturas` (
	`id` int NOT NULL AUTO_INCREMENT,
	`total` DECIMAL NOT NULL,
	`IGIC` DECIMAL NOT NULL,
	`totalConIGIC` DECIMAL NOT NULL,
	`codigoConsulta` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Proveedores` (
	`id` int NOT NULL AUTO_INCREMENT,
	`nombre` varchar(25) NOT NULL,
	`direccion` varchar(255) NOT NULL,
	`localidad` varchar(25) NOT NULL,
	`telefono` varchar(25) NOT NULL,
	`email` varchar(25) NOT NULL,
	`CP` int NOT NULL,
	`alta` DATE NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `DetalleFactura` (
	`id` int NOT NULL AUTO_INCREMENT,
	`descripcion` varchar(255) NOT NULL,
	`unidades` int NOT NULL,
	`precio` DECIMAL NOT NULL,
	`importe` DECIMAL NOT NULL,
	`codigoFactura` int NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `Citas` (
	`id` int NOT NULL AUTO_INCREMENT,
	`fecha` DATE NOT NULL,
	`codigoVeterinario` int NOT NULL,
	`codigoMascota` int NOT NULL,
	PRIMARY KEY (`id`)
);

ALTER TABLE `Mascotas` ADD CONSTRAINT `Mascotas_fk0` FOREIGN KEY (`codigoCliente`) REFERENCES `Clientes`(`id`);

ALTER TABLE `Roles` ADD CONSTRAINT `Roles_fk0` FOREIGN KEY (`codigoVeterinario`) REFERENCES `Veterinarios`(`id`);

ALTER TABLE `Medicamentos` ADD CONSTRAINT `Medicamentos_fk0` FOREIGN KEY (`codigoProveedor`) REFERENCES `Proveedores`(`id`);

ALTER TABLE `Consultas` ADD CONSTRAINT `Consultas_fk0` FOREIGN KEY (`codigoMedicamento`) REFERENCES `Medicamentos`(`id`);

ALTER TABLE `Consultas` ADD CONSTRAINT `Consultas_fk1` FOREIGN KEY (`codigoVeterinario`) REFERENCES `Veterinarios`(`id`);

ALTER TABLE `Consultas` ADD CONSTRAINT `Consultas_fk2` FOREIGN KEY (`codigoMascota`) REFERENCES `Mascotas`(`id`);

ALTER TABLE `Facturas` ADD CONSTRAINT `Facturas_fk0` FOREIGN KEY (`codigoConsulta`) REFERENCES `Consultas`(`id`);

ALTER TABLE `DetalleFactura` ADD CONSTRAINT `DetalleFactura_fk0` FOREIGN KEY (`codigoFactura`) REFERENCES `Facturas`(`id`);

ALTER TABLE `Citas` ADD CONSTRAINT `Citas_fk0` FOREIGN KEY (`codigoVeterinario`) REFERENCES `Veterinarios`(`id`);

ALTER TABLE `Citas` ADD CONSTRAINT `Citas_fk1` FOREIGN KEY (`codigoMascota`) REFERENCES `Mascotas`(`id`);











