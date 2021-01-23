CREATE SCHEMA `taxi_service` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
CREATE TABLE `manufacturers` (
                                 `manufacturer_id` bigint NOT NULL AUTO_INCREMENT,
                                 `manufacturer_name` varchar(225) COLLATE utf8_bin NOT NULL,
                                 `manufacturer_country` varchar(225) COLLATE utf8_bin NOT NULL,
                                 `deleted` tinyint NOT NULL DEFAULT '0',
                                 PRIMARY KEY (`manufacturer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

INSERT INTO `taxi_service`.`manufacturers` (`manufacture_name`, `manufacture_country`) VALUES ('Audi', 'Germany');
INSERT INTO `taxi_service`.`manufacturers` (`manufacture_name`, `manufacture_country`) VALUES ('BMW', 'Germany');

CREATE TABLE `cars` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `manufacturer_id` bigint DEFAULT NULL,
                        `model` varchar(45) COLLATE utf8_bin DEFAULT NULL,
                        `deleted` tinyint NOT NULL DEFAULT '0',
                        PRIMARY KEY (`id`),
                        KEY `manufacturer_id_idx` (`manufacturer_id`),
                        CONSTRAINT `manufacturer_id` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturers` (`manufacture_id`)
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `drivers` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `name` varchar(225) COLLATE utf8_bin NOT NULL,
                           `license_number` varchar(225) COLLATE utf8_bin NOT NULL,
                           `deleted` tinyint NOT NULL DEFAULT '0',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `drivers_cars` (
                                `driver_id` int NOT NULL,
                                `car_id` int NOT NULL,
                                KEY `car_id_idx` (`car_id`),
                                KEY `driver_id_idx` (`driver_id`),
                                CONSTRAINT `car_id` FOREIGN KEY (`car_id`) REFERENCES `cars` (`id`),
                                CONSTRAINT `driver_id` FOREIGN KEY (`driver_id`) REFERENCES `drivers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

ALTER TABLE `taxi_service`.`drivers`
    ADD COLUMN `login` VARCHAR(225) NOT NULL AFTER `deleted`,
    ADD COLUMN `password` VARCHAR(225) NOT NULL AFTER `login`;
