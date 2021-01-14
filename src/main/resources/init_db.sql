CREATE SCHEMA `taxi_service` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
CREATE TABLE `taxi_service`.`manufacturers` (
                                                `manufacture_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                                `manufacture_name` VARCHAR(225) NOT NULL,
                                                `manufacture_country` VARCHAR(225) NOT NULL,
                                                `deleted` TINYINT(8) NOT NULL DEFAULT 0,
                                                PRIMARY KEY (`manufacture_id`));
