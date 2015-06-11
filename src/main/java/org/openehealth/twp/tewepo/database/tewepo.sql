-- phpMyAdmin SQL Dump
-- version 3.3.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 27. Mai 2011 um 11:16
-- Server Version: 5.1.54
-- PHP-Version: 5.3.5-1ubuntu7.2

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `tewepo`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `activationdatabaseentry`
--

DROP TABLE IF EXISTS `activationdatabaseentry`;
CREATE TABLE IF NOT EXISTS `activationdatabaseentry` (
  `id` bigint(20) NOT NULL,
  `version` int(11) NOT NULL,
  `activationCode` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `person_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKE73F1E811F93855B` (`person_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `activationdatabaseentry`
--


-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `address`
--

DROP TABLE IF EXISTS `address`;
CREATE TABLE IF NOT EXISTS `address` (
  `id` bigint(20) NOT NULL,
  `version` int(11) NOT NULL,
  `street` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zipcode` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `location` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isChanged` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `address`
--

INSERT INTO `address` (`id`, `version`, `street`, `number`, `zipcode`, `location`, `isChanged`) VALUES
(4, 0, 'Tiergartenstraße', '15', '69121', 'Heidelberg', b'1');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `lockedip`
--

DROP TABLE IF EXISTS `lockedip`;
CREATE TABLE IF NOT EXISTS `lockedip` (
  `id` bigint(20) NOT NULL,
  `version` int(11) NOT NULL,
  `ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `attemptNo` int(11) NOT NULL,
  `active` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `lockedip`
--


-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `log_lastrecipients`
--

DROP TABLE IF EXISTS `log_lastrecipients`;
CREATE TABLE IF NOT EXISTS `log_lastrecipients` (
  `id` bigint(20) NOT NULL,
  `version` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  `recipientsLocation` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `recipientsInstitution` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `recipientsRecipient` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `recipientsPublicKeyFileName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `recipientsMailAddress1` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `recipientsMailAddress2` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `recipientsMailAddress3` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `person_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8240FAEB1F93855B` (`person_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `log_lastrecipients`
--


-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `person`
--

DROP TABLE IF EXISTS `person`;
CREATE TABLE IF NOT EXISTS `person` (
  `id` bigint(20) NOT NULL,
  `version` int(11) NOT NULL,
  `loginname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL UNIQUE,
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `emailaddress` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `forename` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `surname` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `organisation` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `department` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `occupationgroup` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `accountActive` bit(1) NOT NULL,
  `address_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC4E39B55DE5B2599` (`address_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `person`
--

INSERT INTO `person` (`id`, `version`, `loginname`, `password`, `emailaddress`, `title`, `forename`, `surname`, `organisation`, `department`, `occupationgroup`, `accountActive`, `address_id`) VALUES
(5, 1, 'sysadmin', '$2a$10$L6IwhCeBrE5qwYq/jENObuU0JoRZYijXZk32oDEwHDBuhdlLdnXIi', 'max.mustermann@test.de', '','Max', 'Mustermann', 'Testunternehmen', 'Testabteilung', 'Medizininformatiker', b'1', 4);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `person_role`
--

DROP TABLE IF EXISTS `person_role`;
CREATE TABLE IF NOT EXISTS `person_role` (
  `person_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL,
  KEY `FKE6A16B201F93855B` (`person_id`),
  KEY `FKE6A16B206517DA54` (`roles_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `person_role`
--

INSERT INTO `person_role` (`person_id`, `roles_id`) VALUES
(5, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `primary_keys`
--

DROP TABLE IF EXISTS `primary_keys`;
CREATE TABLE IF NOT EXISTS `primary_keys` (
  `sequence_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `primary_keys`
--

INSERT INTO `primary_keys` (`sequence_name`, `sequence_next_hi_value`) VALUES
('AbstractPersistentObject', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint(20) NOT NULL,
  `version` int(11) NOT NULL,
  `role` int(11) DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `role`
--

INSERT INTO `role` (`id`, `version`, `role`, `description`) VALUES
(1, 0, 0, 'Systemadministrator'),
(2, 0, 1, 'Arzt'),
(3, 0, 2, 'Patient');




-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `log_lastfiles`
--

DROP TABLE IF EXISTS `log_lastfiles`;
CREATE TABLE IF NOT EXISTS `log_lastfiles` (
  `id` bigint(20) NOT NULL,
  `version` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  `recipientsLocation` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `recipientsInstitution` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `recipientsRecipient` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `recipientsPublicKeyFileName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `recipientsMailAddress` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `person_id` bigint(20) DEFAULT NULL,
  `filename` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8240FAEB1F93855B` (`person_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Daten für Tabelle `log_lastfiles`
--


-- --------------------------------------------------------