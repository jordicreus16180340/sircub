-- MySQL dump 10.13  Distrib 5.6.21, for Linux (x86_64)
--
-- Host: localhost    Database: sircub2
-- ------------------------------------------------------
-- Server version	5.6.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `annotation_datasets`
--

DROP TABLE IF EXISTS `annotation_datasets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `annotation_datasets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `annotation_labels`
--

DROP TABLE IF EXISTS `annotation_labels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `annotation_labels` (
  `annotation_dataset_id` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`annotation_dataset_id`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `annotations`
--

DROP TABLE IF EXISTS `annotations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `annotations` (
  `annotation_dataset_id` int(11) NOT NULL,
  `location_id` int(11) NOT NULL,
  `latitude` decimal(10,8) NOT NULL,
  `longitude` decimal(11,8) NOT NULL,
  `year_id` int(11) NOT NULL,
  `year` varchar(9) NOT NULL,
  `first_season_id` int(11) NOT NULL,
  `second_season_id` int(11) NOT NULL,
  `first_season` varchar(30) NOT NULL,
  `second_season` varchar(30) NOT NULL,
  PRIMARY KEY (`annotation_dataset_id`,`location_id`,`year_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `descriptor_datasets`
--

DROP TABLE IF EXISTS `descriptor_datasets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `descriptor_datasets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `nb_years` int(11) NOT NULL,
  `nb_values` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `descriptor_locations`
--

DROP TABLE IF EXISTS `descriptor_locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `descriptor_locations` (
  `descriptor_dataset_id` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `latitude` decimal(10,8) NOT NULL,
  `longitude` decimal(11,8) NOT NULL,
  PRIMARY KEY (`descriptor_dataset_id`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `descriptors`
--

DROP TABLE IF EXISTS `descriptors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `descriptors` (
  `descriptor_dataset_id` int(11) NOT NULL,
  `descriptor_location_id` int(11) NOT NULL,
  `row` int(11) NOT NULL,
  `col` int(11) NOT NULL,
  `seas` int(11) NOT NULL,
  `beg` decimal(10,2) NOT NULL,
  `end` decimal(10,2) NOT NULL,
  `beg_relative` decimal(10,2) DEFAULT NULL,
  `end_relative` decimal(10,2) DEFAULT NULL,
  `length` decimal(10,2) NOT NULL,
  `base` decimal(10,2) NOT NULL,
  `mid_x` decimal(10,2) NOT NULL,
  `max` decimal(10,2) NOT NULL,
  `amp` decimal(10,2) NOT NULL,
  `l_der` decimal(10,2) NOT NULL,
  `r_der` decimal(10,2) NOT NULL,
  `l_integ` decimal(10,2) NOT NULL,
  `s_integ` decimal(10,2) NOT NULL,
  PRIMARY KEY (`descriptor_dataset_id`,`descriptor_location_id`,`seas`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-08  0:03:44
