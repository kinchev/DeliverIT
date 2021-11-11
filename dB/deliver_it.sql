-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.6.4-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for deliverit
CREATE DATABASE IF NOT EXISTS `deliverit` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `deliverit`;

-- Dumping structure for table deliverit.addresses
CREATE TABLE IF NOT EXISTS `addresses` (
    `аddress_id` int(11) NOT NULL AUTO_INCREMENT,
    `city_id` int(11) NOT NULL,
    `street_name` varchar(30) DEFAULT NULL,
    PRIMARY KEY (`аddress_id`),
    UNIQUE KEY `Addresses_AddressID_uindex` (`аddress_id`),
    KEY `addresses_cities_fk` (`city_id`),
    CONSTRAINT `addresses_cities_fk` FOREIGN KEY (`city_id`) REFERENCES `cities` (`city_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

-- Dumping data for table deliverit.addresses: ~20 rows (approximately)
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
INSERT IGNORE INTO `addresses` (`address_id`, `city_id`, `street_name`) VALUES
	(1, 1, 'Totleben 11'),
	(2, 2, 'Marica 1'),
	(3, 3, 'Dr.Piskiuliev 33'),
	(4, 4, 'Alexandrov 55'),
	(5, 5, 'Calea Victoriei 44'),
	(6, 6, 'Ermou 8'),
	(7, 7, 'Kärntner Straße 5'),
	(8, 8, 'Kurfürstendamm 18'),
	(9, 9, 'The Champs-Élysées 44'),
	(10, 10, 'Via Condotti 4'),
	(11, 11, 'Carret de Moret 3'),
	(12, 12, 'Rua nova do Carvalho 22'),
	(13, 13, '5 Oxford Street'),
	(14, 14, '10 5th Ave'),
	(15, 9, 'Avenue Viktor  Hugo 77'),
	(16, 18, 'Hauptstrasse 55'),
	(17, 11, 'Carret de Moret 33'),
	(18, 9, 'Rue de Rivoli 15'),
	(19, 19, 'Cherven 1'),
	(20, 1, 'Nenko Balkanski 1');
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;

-- Dumping structure for table deliverit.categories
CREATE TABLE IF NOT EXISTS `categories` (
    `category_id` int(11) NOT NULL AUTO_INCREMENT,
    `category_name` varchar(30) NOT NULL,
    PRIMARY KEY (`category_id`),
    UNIQUE KEY `Categories_CategoryID_uindex` (`category_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- Dumping data for table deliverit.categories: ~8 rows (approximately)
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT IGNORE INTO `categories` (`category_id`, `category_name`) VALUES
	(1, 'electronics'),
	(2, 'home'),
	(3, 'sport'),
	(4, 'baby'),
	(5, 'fashion'),
	(6, 'jewelry'),
	(7, 'cars'),
	(8, 'hobby');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;

-- Dumping structure for table deliverit.cities
CREATE TABLE IF NOT EXISTS `cities` (
    `city_id` int(11) NOT NULL AUTO_INCREMENT,
    `city_name` varchar(30) NOT NULL,
    `country_id` int(11) NOT NULL,
    PRIMARY KEY (`city_id`),
    UNIQUE KEY `Cities_CityID_uindex` (`city_id`),
    KEY `cities_countries_fk` (`country_id`),
    CONSTRAINT `cities_countries_fk` FOREIGN KEY (`country_id`) REFERENCES `countries` (`country_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

-- Dumping data for table deliverit.cities: ~20 rows (approximately)
/*!40000 ALTER TABLE `cities` DISABLE KEYS */;
INSERT IGNORE INTO `cities` (`city_id`, `city_name`, `country_id`) VALUES
	(1, 'Sofia', 1),
	(2, 'Plovdiv', 1),
	(3, 'Varna', 1),
	(4, 'Burgas', 1),
	(5, 'Bucharest', 2),
	(6, 'Athens', 3),
	(7, 'Vienna', 4),
	(8, 'Berlin', 5),
	(9, 'Paris', 6),
	(10, 'Rome', 7),
	(11, 'Valencia', 8),
	(12, 'Lisbon', 9),
	(13, 'London', 10),
	(14, 'New York', 11),
	(15, 'Madrid', 8),
	(16, 'Torino', 7),
	(17, 'Miami', 11),
	(18, 'Konz', 5),
	(19, 'Ruse', 1),
	(20, 'Milano', 7);
/*!40000 ALTER TABLE `cities` ENABLE KEYS */;

-- Dumping structure for table deliverit.countries
CREATE TABLE IF NOT EXISTS `countries` (
    `country_id` int(11) NOT NULL AUTO_INCREMENT,
    `country_name` varchar(30) NOT NULL,
    PRIMARY KEY (`country_id`),
    UNIQUE KEY `Countries_CountryID_uindex` (`country_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

-- Dumping data for table deliverit.countries: ~11 rows (approximately)
/*!40000 ALTER TABLE `countries` DISABLE KEYS */;
INSERT IGNORE INTO `countries` (`country_id`, `country_name`) VALUES
	(1, 'Bulgaria'),
	(2, 'Romania'),
	(3, 'Greece'),
	(4, 'Austria'),
	(5, 'Germany'),
	(6, 'France'),
	(7, 'Italy'),
	(8, 'Spain'),
	(9, 'Portugal'),
	(10, 'UK'),
	(11, 'USA');
/*!40000 ALTER TABLE `countries` ENABLE KEYS */;

-- Dumping structure for table deliverit.orders
CREATE TABLE IF NOT EXISTS `orders` (
    `order_id` int(11) NOT NULL AUTO_INCREMENT,
    `parcel_id` int(11) NOT NULL,
    `shipment_id` int(11) NOT NULL,
    PRIMARY KEY (`order_id`),
    UNIQUE KEY `orders_order_id_uindex` (`order_id`),
    KEY `orders_parcels_fk` (`parcel_id`),
    KEY `orders_shipments_fk` (`shipment_id`),
    CONSTRAINT `orders_parcels_fk` FOREIGN KEY (`parcel_id`) REFERENCES `parcels` (`parcel_id`),
    CONSTRAINT `orders_shipments_fk` FOREIGN KEY (`shipment_id`) REFERENCES `shipments` (`shipment_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table deliverit.orders: ~0 rows (approximately)
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT IGNORE INTO `orders` (`order_id`, `parcel_id`, `shipment_id`) VALUES
	(1, 1, 1),
	(2, 2, 3),
	(3, 3, 3),
	(4, 4, 4),
	(5, 5, 2),
	(6, 6, 1),
	(7, 7, 4),
	(8, 8, 4),
	(9, 9, 3),
	(10, 10, 1);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;

-- Dumping structure for table deliverit.parcels
CREATE TABLE IF NOT EXISTS `parcels` (
    `parcel_id` int(11) NOT NULL AUTO_INCREMENT,
    `user_id` int(11) NOT NULL,
    `origin_warehouse_id` int(11) NOT NULL,
    `destination_warehouse_id` int(11) NOT NULL,
    `category_id` int(11) NOT NULL,
    `weight` double DEFAULT NULL,
    `deliver_to_user` tinyint(1) DEFAULT NULL,
    PRIMARY KEY (`parcel_id`),
    UNIQUE KEY `Parcells_ParcellID_uindex` (`parcel_id`),
    KEY `parcells_deliverytypes_DeliveryTypeID_fk` (`deliver_to_user`),
    KEY `parcels_users_fk` (`user_id`),
    KEY `parcels_categories_fk` (`category_id`),
    KEY `parcels_destination_warehouse_warehouses_fk` (`destination_warehouse_id`),
    KEY `parcels_origin_warehouse_warehouses_fk` (`origin_warehouse_id`),
    CONSTRAINT `parcels_categories_fk` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`),
    CONSTRAINT `parcels_destination_warehouse_warehouses_fk` FOREIGN KEY (`destination_warehouse_id`) REFERENCES `warehouses` (`warehouse_id`),
    CONSTRAINT `parcels_origin_warehouse_warehouses_fk` FOREIGN KEY (`origin_warehouse_id`) REFERENCES `warehouses` (`warehouse_id`),
    CONSTRAINT `parcels_users_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table deliverit.parcels: ~10 rows (approximately)
/*!40000 ALTER TABLE `parcels` DISABLE KEYS */;
INSERT IGNORE INTO `parcels` (`parcel_id`, `user_id`, `origin_warehouse_id`, `destination_warehouse_id`, `category_id`, `weight`, `deliver_to_user`) VALUES
	(1, 1, 3, 1, 1, 1.21, 0),
	(2, 1, 4, 1, 5, 4.11, 1),
	(3, 7, 4, 1, 3, 6.9, 1),
	(4, 3, 4, 2, 2, 22.1, 1),
	(5, 4, 3, 2, 7, 1765, 0),
	(6, 4, 3, 1, 2, 3.21, 0),
	(7, 5, 4, 2, 7, 1232, 1),
	(8, 6, 4, 2, 8, 14, 0),
	(9, 8, 4, 1, 8, 22, 1),
	(10, 9, 3, 1, 1, 1.21, 1);
/*!40000 ALTER TABLE `parcels` ENABLE KEYS */;

-- Dumping structure for table deliverit.roles
CREATE TABLE IF NOT EXISTS `roles` (
    `role_id` int(11) NOT NULL AUTO_INCREMENT,
    `cityName` varchar(30) NOT NULL,
    PRIMARY KEY (`role_id`),
    UNIQUE KEY `roles_role_id_uindex` (`role_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table deliverit.roles: ~2 rows (approximately)
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT IGNORE INTO `roles` (`role_id`, `cityName`) VALUES
	(1, 'customer'),
	(2, 'employee');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;

-- Dumping structure for table deliverit.shipments
CREATE TABLE IF NOT EXISTS `shipments` (
    `shipment_id` int(11) NOT NULL AUTO_INCREMENT,
    `origin_warehouse_id` int(11) NOT NULL,
    `destination_warehouse_id` int(11) NOT NULL,
    `departure_date` date DEFAULT NULL,
    `arrival_date` date DEFAULT NULL,
    PRIMARY KEY (`shipment_id`),
    UNIQUE KEY `Shipments_ShipmentID_uindex` (`shipment_id`),
    KEY `shipments_destination_warehouse_warehouses_fk` (`destination_warehouse_id`),
    KEY `shipments_origin_warehouse_warehouses_fk` (`origin_warehouse_id`),
    CONSTRAINT `shipments_destination_warehouse_warehouses_fk` FOREIGN KEY (`destination_warehouse_id`) REFERENCES `warehouses` (`warehouse_id`),
    CONSTRAINT `shipments_origin_warehouse_warehouses_fk` FOREIGN KEY (`origin_warehouse_id`) REFERENCES `warehouses` (`warehouse_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table deliverit.shipments: ~4 rows (approximately)
/*!40000 ALTER TABLE `shipments` DISABLE KEYS */;
INSERT IGNORE INTO `shipments` (`shipment_id`, `origin_warehouse_id`, `destination_warehouse_id`, `departure_date`, `arrival_date`) VALUES
	(1, 3, 1, NULL, NULL),
	(2, 3, 2, NULL, NULL),
	(3, 4, 1, NULL, NULL),
	(4, 4, 2, NULL, NULL);
/*!40000 ALTER TABLE `shipments` ENABLE KEYS */;

-- Dumping structure for table deliverit.users
CREATE TABLE IF NOT EXISTS `users` (
    `user_id` int(11) NOT NULL AUTO_INCREMENT,

    `password` varchar(50) NOT NULL,
    `first_name` varchar(30) NOT NULL,
    `last_name` varchar(30) DEFAULT NULL,
    `address_id` int(11) DEFAULT NULL,
    `email` varchar(50) NOT NULL,
    PRIMARY KEY (`user_id`),
    KEY `users_address_fk1` (`address_id`),
    CONSTRAINT `users_address_fk1` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`аddress_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

-- Dumping data for table deliverit.users: ~14 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT IGNORE INTO `users` (`user_id`, `username`, `password`, `first_name`, `last_name`, `address_id`, `email`) VALUES
	(1, 'vankata123', 'meche123', 'Ivan', 'Ivanov', 1, 'vanio@abv.bg'),
	(2, 'vikiii', '12345678', 'Viktoria', 'Karamanova', 2, 'vk_home@abv.bg'),
	(3, 'snejna_topka', 'mamababa_babamama', 'Snejina', 'Petrova', 3, 'snej_18@abv.bg'),
	(4, 'moni_work', 'furnel454565', 'Monica', 'Beluchi', 4, '@abv.bg'),
	(5, 'cheishesko99', 'poncho_4343@$', 'Raluka', 'Marluka', 5, 'raluka@gmail.com'),
	(6, 'Niii123', 'inv2343_$ff@', 'Nidan', 'Ishtarova', 6, 'nnn123@gmail.com'),
	(7, 'betoven18', 'machael_jackson', 'Iohan', 'Mozart', 7, 'iohan_work@yahoo.com'),
	(8, 'bmw666', 'fulera6666', 'Mark', 'Guntterov', 8, 'markkk@yahoo.com'),
	(9, 'shanel555', '%%hooofooo123', 'Emmanuel', 'Shirak', 9, 'manul@yahoo.fr'),
	(10, 'pizza_margarita', 'telebapis999', 'Franco', 'Polo', 10, 'fr999@gmail.com'),
	(11, 'hg_home', 'snnopy&&&', 'Havier', 'Guriega', 11, 'havi@gmail.com'),
	(12, 'google321', '$moneymoneymoney%', 'Gogo', 'Mogo', 12, 'ggg999@gmail.com'),
	(13, 'gottalent_123', '@kiss_miss_%$#', 'Saimon', 'Callol', 13, 's_boss@yahoo.com'),
	(14, 'president15', 'opamopatralala^', 'George', 'Vashington', 14, 'gv@gmail.com');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

-- Dumping structure for table deliverit.users_roles
CREATE TABLE IF NOT EXISTS `users_roles` (
    `user_id` int(11) NOT NULL,
    `role_id` int(11) NOT NULL,
    KEY `users_roles_roles_role_id_fk` (`role_id`),
    KEY `users_roles_users_user_id_fk` (`user_id`),
    CONSTRAINT `users_roles_roles_role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`),
    CONSTRAINT `users_roles_users_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table deliverit.users_roles: ~14 rows (approximately)
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT IGNORE INTO `users_roles` (`user_id`, `role_id`) VALUES
	(1, 1),
	(2, 2),
	(3, 1),
	(4, 1),
	(5, 1),
	(6, 1),
	(7, 1),
	(8, 1),
	(9, 1),
	(10, 1),
	(11, 2),
	(12, 1),
	(13, 2),
	(14, 1);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;

-- Dumping structure for table deliverit.warehouses
CREATE TABLE IF NOT EXISTS `warehouses` (
    `warehouse_id` int(11) NOT NULL AUTO_INCREMENT,
    `address_id` int(11) NOT NULL,
    PRIMARY KEY (`warehouse_id`),
    UNIQUE KEY `Warehouses_warehouseID_uindex` (`warehouse_id`),
    KEY `warehouses_addresses_fk` (`address_id`),
    CONSTRAINT `warehouses_addresses_fk` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`аddress_id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table deliverit.warehouses: ~4 rows (approximately)
/*!40000 ALTER TABLE `warehouses` DISABLE KEYS */;
INSERT IGNORE INTO `warehouses` (`warehouse_id`, `address_id`) VALUES
	(1, 1),
	(2, 2),
	(3, 10),
	(4, 11);
/*!40000 ALTER TABLE `warehouses` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
