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


-- Listage de la structure de la base pour puissance4
DROP DATABASE IF EXISTS `puissance4`;
CREATE DATABASE IF NOT EXISTS `puissance4` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `puissance4`;

-- Listage de la structure de la table puissance4. donneetemp
DROP TABLE IF EXISTS `donneetemp`;
CREATE TABLE IF NOT EXISTS `donneetemp` (
  `iddonnee1` int(11) NOT NULL,
  `iddonnee2` int(11) NOT NULL,
  `iddonnee3` int(11) NOT NULL,
  `dernierJouer` int(11) DEFAULT NULL,
  `joueur` int(11) DEFAULT NULL,
  `precedent` int(11) DEFAULT NULL,
  `colonne` int(11) DEFAULT NULL,
  `resultat` int(11) DEFAULT NULL,
  `calculer` int(11) DEFAULT NULL,
  `index` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`index`),
  UNIQUE KEY `index_UNIQUE` (`index`),
  UNIQUE KEY `index` (`iddonnee1`,`iddonnee2`,`iddonnee3`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de la table puissance4. lien
DROP TABLE IF EXISTS `lien`;
CREATE TABLE IF NOT EXISTS `lien` (
  `parent` bigint(20) unsigned NOT NULL,
  `enfant` bigint(20) unsigned NOT NULL,
  UNIQUE KEY `id` (`parent`,`enfant`),
  KEY `idparent` (`parent`),
  KEY `idenfant` (`enfant`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de la table puissance4. neud
DROP TABLE IF EXISTS `neud`;
CREATE TABLE IF NOT EXISTS `neud` (
  `idneud` bigint(20) unsigned NOT NULL,
  `explorable` tinyint(1) DEFAULT NULL,
  `calculer` tinyint(1) DEFAULT NULL,
  `niveau` tinyint(1) DEFAULT NULL,
  `feuille` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idneud`),
  UNIQUE KEY `idneud_UNIQUE` (`idneud`),
  KEY `Index 3` (`explorable`,`niveau`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Les données exportées n'étaient pas sélectionnées.

-- Listage de la structure de la table puissance4. reprise
DROP TABLE IF EXISTS `reprise`;
CREATE TABLE IF NOT EXISTS `reprise` (
  `idreprise` bigint(20) NOT NULL,
  PRIMARY KEY (`idreprise`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- Les données exportées n'étaient pas sélectionnées.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
