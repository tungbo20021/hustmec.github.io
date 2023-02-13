CREATE DATABASE  IF NOT EXISTS `quanly_tbi_yte` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `quanly_tbi_yte`;
-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: quanly_tbi_yte
-- ------------------------------------------------------
-- Server version	8.0.17

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
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `device` (
  `dv_id` int(11) NOT NULL AUTO_INCREMENT,
  `dv_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `dv_model` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `dv_serial` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `department` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `dv_brand` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `product_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `import_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `dv_file` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `func_score` int(11) DEFAULT NULL,
  `app_score` int(11) DEFAULT NULL,
  `maintain_score` int(11) DEFAULT NULL,
  `histo_score` int(11) DEFAULT NULL,
  `em_score` int(11) DEFAULT NULL,
  `type` varchar(2) COLLATE utf8mb4_bin DEFAULT NULL,
  `fre` varchar(2) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`dv_id`),
  KEY `dv_file` (`dv_file`),
  CONSTRAINT `device_ibfk_1` FOREIGN KEY (`dv_file`) REFERENCES `file` (`file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4189 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `device`
--

LOCK TABLES `device` WRITE;
/*!40000 ALTER TABLE `device` DISABLE KEYS */;
INSERT INTO `device` VALUES (4179,'Giường bệnh 3 tay quay',NULL,NULL,'Cấp cứu','Hoàng Nguyễn - Việt Nam','2015','10/2015',NULL,'Mới 100%',NULL,6,0,3,4,5,2,14,'I','A'),(4180,'Xe cáng bệnh nhân cấp cứu ','XC - 08',NULL,'Cấp cứu','Hoàng Nguyễn - Việt Nam','2015','10/2015','Cái','Mới 100%','Quỹ HĐPTSN và các nguồn thu hợp pháp khác của đơn vị',6,4,0,0,0,0,0,'N','-'),(4181,'Máy thở T Bird','VELA16186 - 07','AGT03437','Cấp cứu','Mỹ','','2007','Cái','Mới 100%','Ngân  sách',6,1,0,0,0,0,0,'N','-'),(4182,'Monitor theo dõi bệnh nhân - Nhật',NULL,NULL,'Cấp cứu','Nhật','','2011',NULL,'Mới 100%',NULL,6,0,10,5,5,2,22,'I','T'),(4183,'Cáng xe đẩy BN nằm INOX - VN',NULL,NULL,'Cấp cứu','','','2009',NULL,'Mới 100%',NULL,6,0,2,4,5,2,13,'I','A'),(4184,'Máy hút dịch',NULL,NULL,'Cấp cứu','Thomas - Mỹ','2009','7/2009',NULL,'Mới 100%',NULL,6,0,6,4,3,2,15,'I','S'),(4185,'Bơm tiêm điện',NULL,NULL,'Cấp cứu','Nhật','2009','10/2009',NULL,'Mới 100%',NULL,6,0,8,5,3,1,17,'I','S'),(4186,'Máy theo dõi SPO2 và nhịp mạch NIHOKOHDEN - Nhật Bản','OLV-2700K','01271','Cấp cứu','Nhật','2011','9/2011','Cái','Mới 100%','Quỹ PT hoạt động sự nghiệp',6,1,3,4,5,1,13,'I','A'),(4187,'Máy theo dõi SPO2 và nhịp mạch NIHOKOHDEN - Nhật Bản','OLV-2700K','01273','Cấp cứu','Nhật','2011','9/2011','Cái','Mới 100%','Quỹ PT hoạt động sự nghiệp',6,1,0,0,0,0,0,'N','-');
/*!40000 ALTER TABLE `device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `file` (
  `file_id` int(11) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`file_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `file_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
INSERT INTO `file` VALUES (6,'template.xlsx',1);
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loghistory`
--

DROP TABLE IF EXISTS `loghistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loghistory` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `log_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`log_id`),
  KEY `fk_user_id_idx` (`user_id`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=179 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loghistory`
--

LOCK TABLES `loghistory` WRITE;
/*!40000 ALTER TABLE `loghistory` DISABLE KEYS */;
INSERT INTO `loghistory` VALUES (167,'13-01-2021/16:20:11.216995200',1),(168,'13-01-2021/17:08:30.200761200',34),(169,'13-01-2021/17:08:52.603472',34),(170,'13-01-2021/17:09:25.986111100',34),(171,'13-01-2021/17:09:30.427611900',1),(172,'13-01-2021/17:29:50.752105200',34),(173,'13-01-2021/17:31:12.503057100',34),(174,'13-01-2021/17:32:37.934788400',34),(175,'13-01-2021/17:32:44.035380300',1),(176,'14-01-2021/22:49:10.906521900',1),(177,'14-01-2021/22:49:18.220466900',1),(178,'27-01-2021/20:44:35.141807500',1);
/*!40000 ALTER TABLE `loghistory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `password` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `email` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `mobile` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `department` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `login_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Nguyễn Thị Hoài Thi','17ba0791499db908433b80f37c5fbc89b870084b','thinguyen98@gmail.com','0982333777','Minh Khai','Admin','Cấp Cứu','admin','1'),(34,'Nguyễn Văn Thắng','ff432fcd41db0959b40f91c6f175338d40860251','nguyenvanthang@gmail.com','0358433707','Số 222 Minh Khai, Hai Bà Trưng, Hà Nội','User','Xét nghiệm','thang','16:28:30.996628300');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-27 21:44:10
