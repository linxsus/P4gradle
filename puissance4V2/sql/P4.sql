-- --------------------------------------------------------
-- Hôte :                        127.0.0.1
-- Version du serveur:           10.5.9-MariaDB - mariadb.org binary distribution
-- SE du serveur:                Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Listage de la structure de la base pour puissance4v3
DROP DATABASE IF EXISTS `puissance4v3`;
CREATE DATABASE IF NOT EXISTS `puissance4v3` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `puissance4v3`;

-- Listage de la structure de la table puissance4v3. neud
DROP TABLE IF EXISTS `neud`;
CREATE TABLE IF NOT EXISTS `neud` (
  `idneud` bigint(20) unsigned NOT NULL,
  `etat` tinyint(1) DEFAULT NULL,
  `calculer` tinyint(1) DEFAULT NULL,
  `niveau` tinyint(1) DEFAULT NULL,
  `parent1` bigint(20) unsigned NOT NULL,
  `parent2` bigint(20) unsigned NOT NULL,
  `parent3` bigint(20) unsigned NOT NULL,
  `parent4` bigint(20) unsigned NOT NULL,
  `parent5` bigint(20) unsigned NOT NULL,
  `parent6` bigint(20) unsigned NOT NULL,
  `parent7` bigint(20) unsigned NOT NULL,
  `enfant1` bigint(20) unsigned NOT NULL,
  `enfant2` bigint(20) unsigned NOT NULL,
  `enfant3` bigint(20) unsigned NOT NULL,
  `enfant4` bigint(20) unsigned NOT NULL,
  `enfant5` bigint(20) unsigned NOT NULL,
  `enfant6` bigint(20) unsigned NOT NULL,
  `enfant7` bigint(20) unsigned NOT NULL,
  `quantum` INT(11) DEFAULT 0,
  PRIMARY KEY (`idneud`),
  UNIQUE KEY `idneud_UNIQUE` (`idneud`),
  KEY `Index 3` (`etat`,`niveau`),
  KEY `Index 1` (`quantum`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de la table puissance4v3. reprise
DROP TABLE IF EXISTS `reprise`;
CREATE TABLE IF NOT EXISTS `reprise` (
  `idreprise` bigint(20) NOT NULL,
  PRIMARY KEY (`idreprise`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED;

-- Les données exportées n'étaient pas sélectionnées.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
