CREATE DATABASE  IF NOT EXISTS `puissancetest` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `puissancetest`;
-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: puissancetest
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `donnee`
--

DROP TABLE IF EXISTS `donnee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donnee` (
  `iddonnee1` int NOT NULL,
  `iddonnee2` int NOT NULL,
  `iddonnee3` int NOT NULL,
  `dernierJouer` int DEFAULT NULL,
  `joueur` int DEFAULT NULL,
  `precedent` int DEFAULT NULL,
  `colonne` int DEFAULT NULL,
  `resultat` int DEFAULT NULL,
  `feuille` int DEFAULT NULL,
  `calculer` int DEFAULT '0',
  `index` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`index`),
  UNIQUE KEY `index_UNIQUE` (`index`),
  UNIQUE KEY `index_id` (`iddonnee1`,`iddonnee2`,`iddonnee3`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1385685377 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `donneetemp`
--

DROP TABLE IF EXISTS `donneetemp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donneetemp` (
  `iddonnee1` int NOT NULL,
  `iddonnee2` int NOT NULL,
  `iddonnee3` int NOT NULL,
  `dernierJouer` int DEFAULT NULL,
  `joueur` int DEFAULT NULL,
  `precedent` int DEFAULT NULL,
  `colonne` int DEFAULT NULL,
  `resultat` int DEFAULT NULL,
  `calculer` int DEFAULT NULL,
  `index` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`index`),
  UNIQUE KEY `index_UNIQUE` (`index`),
  UNIQUE KEY `index` (`iddonnee1`,`iddonnee2`,`iddonnee3`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `lien`
--

DROP TABLE IF EXISTS `lien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lien` (
  `parent` bigint NOT NULL,
  `enfant` bigint NOT NULL,
  `colonne` int DEFAULT NULL,
  UNIQUE KEY `id` (`parent`,`enfant`),
  KEY `idparent` (`parent`),
  KEY `idenfant` (`enfant`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `neud`
--

DROP TABLE IF EXISTS `neud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `neud` (
  `idneud` bigint unsigned NOT NULL,
  `explorable` tinyint(1) DEFAULT NULL,
  `calculer` tinyint(1) DEFAULT NULL,
  `niveau` tinyint(1) DEFAULT NULL,
  `feuille` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idneud`),
  UNIQUE KEY `idneud_UNIQUE` (`idneud`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `newdonnee`
--

DROP TABLE IF EXISTS `newdonnee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `newdonnee` (
  `id` bigint NOT NULL,
  `resultat` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reprise`
--

DROP TABLE IF EXISTS `reprise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reprise` (
  `idreprise` bigint NOT NULL,
  PRIMARY KEY (`idreprise`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-13 11:00:15
CREATE DATABASE  IF NOT EXISTS `puissance4` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `puissance4`;
-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: puissance4
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `donneetemp`
--

DROP TABLE IF EXISTS `donneetemp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donneetemp` (
  `iddonnee1` int NOT NULL,
  `iddonnee2` int NOT NULL,
  `iddonnee3` int NOT NULL,
  `dernierJouer` int DEFAULT NULL,
  `joueur` int DEFAULT NULL,
  `precedent` int DEFAULT NULL,
  `colonne` int DEFAULT NULL,
  `resultat` int DEFAULT NULL,
  `calculer` int DEFAULT NULL,
  `index` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`index`),
  UNIQUE KEY `index_UNIQUE` (`index`),
  UNIQUE KEY `index` (`iddonnee1`,`iddonnee2`,`iddonnee3`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `lien`
--

DROP TABLE IF EXISTS `lien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lien` (
  `parent` bigint NOT NULL,
  `enfant` bigint NOT NULL,
  `colonne` int DEFAULT NULL,
  UNIQUE KEY `id` (`parent`,`enfant`),
  KEY `idparent` (`parent`),
  KEY `idenfant` (`enfant`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `neud`
--

DROP TABLE IF EXISTS `neud`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `neud` (
  `idneud` bigint unsigned NOT NULL,
  `explorable` tinyint(1) DEFAULT NULL,
  `calculer` tinyint(1) DEFAULT NULL,
  `niveau` tinyint(1) DEFAULT NULL,
  `feuille` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idneud`),
  UNIQUE KEY `idneud_UNIQUE` (`idneud`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reprise`
--

DROP TABLE IF EXISTS `reprise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reprise` (
  `idreprise` bigint NOT NULL,
  PRIMARY KEY (`idreprise`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-02-13 11:00:15
