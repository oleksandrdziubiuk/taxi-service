CREATE SCHEMA `taxi_service` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
CREATE TABLE `taxi_service`.`manufacturers` (
                                                `manufacture_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                                `manufacture_name` VARCHAR(225) NOT NULL,
                                                `manufacture_country` VARCHAR(225) NOT NULL,
                                                `deleted` TINYINT(8) NOT NULL DEFAULT 0,
                                                PRIMARY KEY (`manufacture_id`));

INSERT INTO `taxi_service`.`manufacturers` (`manufacture_name`, `manufacture_country`) VALUES ('Audi', 'Germany');
INSERT INTO `taxi_service`.`manufacturers` (`manufacture_name`, `manufacture_country`) VALUES ('BMW', 'Germany');

CREATE TABLE `cars` (
                        `car_id` int NOT NULL AUTO_INCREMENT,
                        `manufacturer_id` bigint DEFAULT NULL,
                        `car_model` varchar(45) COLLATE utf8_bin DEFAULT NULL,
                        `deleted` tinyint NOT NULL DEFAULT '0',
                        PRIMARY KEY (`car_id`),
                        KEY `manufacturer_id_idx` (`manufacturer_id`),
                        CONSTRAINT `manufacturer_id` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturers` (`manufacture_id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `drivers` (
                           `driver_id` int NOT NULL AUTO_INCREMENT,
                           `driver_name` varchar(225) COLLATE utf8_bin NOT NULL,
                           `license_number` varchar(225) COLLATE utf8_bin NOT NULL,
                           `deleted` tinyint NOT NULL DEFAULT '0',
                           PRIMARY KEY (`driver_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `drivers_cars` (
                                `driver_id` bigint NOT NULL,
                                `car_id` bigint NOT NULL,
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                PRIMARY KEY (`id`),
                                KEY `driver_id_fk_idx` (`driver_id`),
                                KEY `car_id_fk_idx` (`car_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
