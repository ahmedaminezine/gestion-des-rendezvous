CREATE DATABASE  IF NOT EXISTS `task_manager_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `task_manager_db`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: task_manager_db
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `id_client` int NOT NULL,
  `age_client` int DEFAULT NULL,
  `ville_client` varchar(100) DEFAULT NULL,
  `id_personne` int DEFAULT NULL,
  PRIMARY KEY (`id_client`),
  KEY `id_idx` (`id_personne`),
  CONSTRAINT `id` FOREIGN KEY (`id_personne`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (-2052381977,40,'MKS',13);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medcine`
--

DROP TABLE IF EXISTS `medcine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medcine` (
  `id_medcine` int NOT NULL,
  `specialite` varchar(100) DEFAULT NULL,
  `anneeservice` int DEFAULT NULL,
  `id_pers` int DEFAULT NULL,
  PRIMARY KEY (`id_medcine`),
  KEY `id_idx` (`id_pers`),
  CONSTRAINT `id_pers` FOREIGN KEY (`id_pers`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medcine`
--

LOCK TABLES `medcine` WRITE;
/*!40000 ALTER TABLE `medcine` DISABLE KEYS */;
INSERT INTO `medcine` VALUES (2,'ZAMEL',23,14);
/*!40000 ALTER TABLE `medcine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `redezvous`
--

DROP TABLE IF EXISTS `redezvous`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `redezvous` (
  `id_redezvous` int NOT NULL,
  `horaire` varchar(45) DEFAULT NULL,
  `maladie` varchar(100) DEFAULT NULL,
  `id_client` int DEFAULT NULL,
  `id_medcine` int DEFAULT NULL,
  `date_rendezvous` date DEFAULT NULL,
  `CompteRendu` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id_redezvous`),
  KEY `id_client_idx` (`id_client`),
  KEY `id_medcine_idx` (`id_medcine`),
  CONSTRAINT `id_client` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_client`),
  CONSTRAINT `id_medcine` FOREIGN KEY (`id_medcine`) REFERENCES `medcine` (`id_medcine`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `redezvous`
--

LOCK TABLES `redezvous` WRITE;
/*!40000 ALTER TABLE `redezvous` DISABLE KEYS */;
INSERT INTO `redezvous` VALUES (-1407476951,'09:00','FD',-2052381977,2,'2025-06-04','NEANT'),(-1371321283,'15:00','JI',-2052381977,2,'2025-06-04','NEANT');
/*!40000 ALTER TABLE `redezvous` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (13,'USER','USER','ZINE HMAAD'),(14,'ADMIN','ADMIN','ADMIN');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-27  1:03:34
