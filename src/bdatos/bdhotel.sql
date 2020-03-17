/*
SQLyog Ultimate v9.63 
MySQL - 5.5.5-10.4.6-MariaDB : Database - bdhotel
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bdhotel` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `bdhotel`;

/*Table structure for table `ingventa` */

DROP TABLE IF EXISTS `ingventa`;

CREATE TABLE `ingventa` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idlog` int(11) NOT NULL,
  `fecha` text NOT NULL,
  `habitacion` text NOT NULL,
  `valor` double NOT NULL,
  `tiempo` text NOT NULL,
  `tipo` text NOT NULL,
  `datetime` datetime NOT NULL,
  `estado` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idlog` (`idlog`),
  CONSTRAINT `ingventa_ibfk_1` FOREIGN KEY (`idlog`) REFERENCES `userlog` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ingventa` */

/*Table structure for table `userlog` */

DROP TABLE IF EXISTS `userlog`;

CREATE TABLE `userlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usuario` text NOT NULL,
  `clave` text NOT NULL,
  `estado` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `userlog` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
