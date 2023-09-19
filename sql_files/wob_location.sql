CREATE DATABASE  IF NOT EXISTS `wob` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `wob`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: wob
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `id` varchar(36) NOT NULL,
  `manager_name` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address_primary` varchar(255) DEFAULT NULL,
  `address_secondary` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `town` varchar(255) DEFAULT NULL,
  `postal_code` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES ('013cc380-c18b-4c87-8043-16e14f7127de','Stephie Jerred','519-354-3298','182 Holy Cross Pass',NULL,'South Africa','Diepsloot','0159'),('0fe479bb-fe39-4265-b59f-bb4ac5a060d4','Birgit Elcoux','194-828-2808','415 John Wall Alley',NULL,'Malta','Saint Lucia','SLZ'),('1d551b07-fd16-4760-88a3-4aa4fda13a2b','Nickie Winckle','948-517-7056','23 Dahle Road',NULL,'Kenya','Homa Bay',NULL),('5249f33c-fadf-44d9-ab70-471df29c20a6','Evvie Moxon','926-333-8959','7804 Surrey Hill',NULL,'China','Qianjin',NULL),('52ea143e-cb45-43af-981e-92cedb89f7a8','Reyna Paddemore','229-917-1848','36 Commercial Point',NULL,'Japan','Obihiro','652-0837'),('5ae22efb-f875-4134-a03d-6402efa31dc5','Edna Woosnam','997-249-0952','1496 Bobwhite Plaza',NULL,'China','Xindu',NULL),('5c3a4cf8-1ac4-456d-ba85-a782ff475256','Halsy Fleg','291-350-1582','0832 Scoville Place',NULL,'Vietnam','YĂŞn Láşˇc',NULL),('ac867cd8-c321-42ab-b179-01a4b8301a9c','Drusy Kornalik','805-715-0525','599 Stuart Crossing',NULL,'Russia','Borzoy','366406'),('bfcd9dc2-3ead-470b-9846-5a3f6d8f32a1','Imelda Cheson','404-220-0088','1 Redwing Drive',NULL,'France','Metz','57076 CEDEX 03'),('c47ab7b4-3a94-4218-8e0b-4bada1fb05c0','Mordecai Wilbore','527-298-7011','2 Park Meadow Road',NULL,'Spain','Donostia-San Sebastian','20015');
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-19 14:06:22
