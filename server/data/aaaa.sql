CREATE DATABASE  IF NOT EXISTS `project_today` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `project_today`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: project_today
-- ------------------------------------------------------
-- Server version	5.6.38-log

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `id` varchar(20) NOT NULL,
  `pwd` varchar(20) NOT NULL,
  `uid` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_like` int(11) DEFAULT '0',
  `gps_permission` tinyint(4) NOT NULL DEFAULT '0',
  `search_permission` tinyint(4) NOT NULL DEFAULT '0',
  `birth` date NOT NULL,
  `last_login` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid_UNIQUE` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES ('\'insert01\'','\'1234\'','\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','\'우울산시\'','\'aaa@gmail.com\'','2018-09-06 13:31:49',0,1,1,'0000-00-00','2018-09-10 19:08:05'),('\'insert03\'','\'1234\'','\'2b8d20693334769e6be2e01e8ce18a9e5a99b26b\'','\'인천\'','\'insert@gmail.com\'','2018-09-06 14:36:26',0,0,0,'0000-00-00','2018-09-10 17:57:15'),('\'insert04\'','\'5678\'','\'3151d9b9823b9882136122a10234a29e0bb2c0ac\'','\'서울\'','\'insert04@gmail.com\'','2018-09-06 14:49:52',0,0,0,'0000-00-00','2018-09-10 17:57:15'),('\'insert06\'','\'5678\'','\'3a1ff1030971e2e7a12e0a8a9756ffb88cb51a4c\'','\'서울\'','\'insert04@gmail.com\'','2018-09-07 11:24:19',0,0,0,'0000-00-00','2018-09-10 17:57:15'),('\'insert07\'','\'1234\'','\'b9b47c9e70d022a1bb1650c5c192da9426bf1580\'','\'부산\'','\'insert04@gmail.com\'','2018-09-07 11:24:45',0,0,0,'0000-00-00','2018-09-10 17:57:15'),('\'insert08\'','\'a901!\'','\'f381d6de4887abf9f09bd64d945d1dfe1d0f9398\'','\'어디\'','\'insert08@gmail.com\'','2018-09-07 11:27:30',0,0,0,'0000-00-00','2018-09-10 17:57:15'),('\'insert10\'','\'a901!\'','\'339255928ea99ac3932c6d1a74ae0c30d5153684\'','\'어디\'','\'insert08@gmail.com\'','2018-09-07 11:45:18',0,0,0,'0000-00-00','2018-09-10 17:57:15'),('\'i\\\'nsert01\\\'\'','\'5678\'','\'287a71a40b92efbdd5cfe7f28994afa3216ccbfc\'','\'서울\'','\'insert04@gmail.com\'','2018-09-07 11:18:02',0,0,0,'0000-00-00','2018-09-10 17:57:15'),('\'nsert05\'','NULL','\'2a552a580ef5b909b291caf457a751e614a952e1\'','NULL','NULL','2018-09-06 15:16:13',0,0,0,'0000-00-00','2018-09-10 17:57:15'),('\'test02\'','\'1234\'','\'f3222dd538b5ec1a421017d1c58b9a90418b8f1d\'','\'서울어딘가\'','\'test02@gmail.com\'','2018-09-07 11:45:55',0,0,0,'0000-00-00','2018-09-10 17:57:15'),('\'\\\'insert05\\\'\'','NULL','\'787f8590ba31205b45e739a527b5b5dc3dc5560a\'','NULL','NULL','2018-09-06 15:14:45',0,0,0,'0000-00-00','2018-09-10 17:57:15'),('\'\\\'insert06\\\'\'','NULL','\'c8e5c218a8e90355cd64e2a07a6e3e3a56515ee6\'','NULL','NULL','2018-09-07 10:32:56',0,0,0,'0000-00-00','2018-09-10 17:57:15'),('\'\\\'insert09\\\'\'','\'a901!\'','\'31d6d505aa684e94a813fe5760c5d060a8e5b436\'','\'어디\'','\'insert08@gmail.com\'','2018-09-07 11:43:11',0,0,0,'0000-00-00','2018-09-10 17:57:15'),('\'\\\'insert10\\\'\'','\'a901!\'','\'b21b1a7bd5f48b98a55321fd272229173c622da2\'','\'어디\'','\'insert08@gmail.com\'','2018-09-07 11:44:26',0,0,0,'0000-00-00','2018-09-10 17:57:15'),('test01','1234','test001','서울시','test01@naver.com','2018-09-01 17:09:53',0,0,0,'0000-00-00','2018-09-10 17:57:15');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hash_tag`
--

DROP TABLE IF EXISTS `hash_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hash_tag` (
  `num` int(11) NOT NULL AUTO_INCREMENT,
  `hash_string` varchar(100) NOT NULL,
  `uid` varchar(45) NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hash_tag`
--

LOCK TABLES `hash_tag` WRITE;
/*!40000 ALTER TABLE `hash_tag` DISABLE KEYS */;
INSERT INTO `hash_tag` VALUES (1,'null은평구#맛집$!#','\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','2018-09-12 11:47:26'),(2,'undefined#은평구#맛집','\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','2018-09-12 11:51:21'),(3,'undefined#은평구#맛집','\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','2018-09-12 11:54:56'),(4,'#은평구#맛집','\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','2018-09-12 12:17:05'),(5,'#은평구#맛집','\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','2018-09-12 14:17:46'),(6,'#은평구#맛집','\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','2018-09-12 14:18:11'),(7,'#은평구#맛집','\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','2018-09-12 14:20:53'),(8,'#은평구#맛집','\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','2018-09-12 14:21:27'),(9,'#은평구#맛집','\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','2018-09-12 14:23:32'),(10,'#은평구#맛집','\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','2018-09-12 14:28:26'),(11,'#은평구#맛집 ','\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','2018-09-12 14:29:19'),(12,'#은평구#맛집','\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','2018-09-12 15:11:06'),(13,'#은평구#맛집','\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','2018-09-12 15:11:43'),(14,'#은평구#맛집','\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','2018-09-12 15:20:29'),(15,'#은평구#undefined#맛집','\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','2018-09-12 15:27:31'),(16,'#은평구#맛집','\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','2018-09-12 15:29:35'),(17,'#은평구#맛집#undefined','\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','2018-09-12 15:30:46'),(18,'#은평구#맛집#undefined','\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','2018-09-12 15:31:31'),(19,'#은평구#undefined#맛집','\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','2018-09-12 15:31:56'),(20,'#null#은평구#맛집','\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','2018-09-12 15:32:05');
/*!40000 ALTER TABLE `hash_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_gps_log`
--

DROP TABLE IF EXISTS `user_gps_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_gps_log` (
  `num` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(45) NOT NULL,
  `longitude` varchar(20) NOT NULL,
  `latitude` varchar(20) NOT NULL,
  `altitude` varchar(20) NOT NULL,
  `accuracy` varchar(20) NOT NULL,
  `date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_gps_log`
--

LOCK TABLES `user_gps_log` WRITE;
/*!40000 ALTER TABLE `user_gps_log` DISABLE KEYS */;
INSERT INTO `user_gps_log` VALUES (1,'\'1c72f8d273d414c5b2f416f7ed939a4dce6edac2\'','500','300','100','30%','2018-09-15 17:20:14');
/*!40000 ALTER TABLE `user_gps_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-28 16:29:20
