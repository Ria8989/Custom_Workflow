-- MySQL dump 10.13  Distrib 8.0.24, for macos11 (x86_64)
--
-- Host: localhost    Database: workflow_v2
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
-- Table structure for table `next_task`
--

DROP TABLE IF EXISTS `next_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `next_task` (
  `id` int NOT NULL AUTO_INCREMENT,
  `task_action_id` int DEFAULT NULL,
  `next_task_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsgn45j2hdw4ri4ijqrw9jf5io` (`next_task_id`),
  CONSTRAINT `FKsgn45j2hdw4ri4ijqrw9jf5io` FOREIGN KEY (`next_task_id`) REFERENCES `task` (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `next_task`
--

LOCK TABLES `next_task` WRITE;
/*!40000 ALTER TABLE `next_task` DISABLE KEYS */;
INSERT INTO `next_task` VALUES (1,1,3),(2,1,4),(3,2,5),(4,3,5),(5,4,6),(6,5,2),(7,6,1),(8,7,2),(9,8,9),(10,9,9),(11,10,8),(12,11,7),(13,12,7),(14,13,7),(15,14,13),(16,16,12),(17,15,12),(18,17,11),(19,18,11),(20,19,11),(21,26,21),(22,27,22),(23,28,20),(24,29,19),(25,30,19),(26,31,19),(27,32,25),(28,33,26),(29,34,24),(30,35,23),(31,36,23),(32,37,23);
/*!40000 ALTER TABLE `next_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `UK_iubw515ff0ugtm28p8g3myt0h` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Admin'),(2,'Dean'),(7,'Oral Panel 111'),(6,'Panel 111'),(3,'Professor'),(4,'Student'),(5,'Workflow Creator');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task` (
  `task_id` int NOT NULL AUTO_INCREMENT,
  `all_user` bit(1) DEFAULT NULL,
  `eligible_action` varchar(500) DEFAULT NULL,
  `enable_upload` bit(1) DEFAULT NULL,
  `is_first_task` bit(1) DEFAULT NULL,
  `task_description` varchar(255) DEFAULT NULL,
  `task_name` varchar(255) NOT NULL,
  `task_type` enum('MANDATORY','OPTIONAL') DEFAULT NULL,
  `task_user_type` enum('USER','ROLE','INITIATOR') DEFAULT NULL,
  `upload_type` enum('MANDATORY','OPTIONAL') DEFAULT NULL,
  `workflow_id` int DEFAULT NULL,
  PRIMARY KEY (`task_id`),
  KEY `FKd9hs1mdj7rhc3g9iv1lkay0gw` (`workflow_id`),
  CONSTRAINT `FKd9hs1mdj7rhc3g9iv1lkay0gw` FOREIGN KEY (`workflow_id`) REFERENCES `workflow` (`workflow_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,_binary '',NULL,_binary '\0',_binary '\0','END','END','MANDATORY','USER',NULL,1),(2,_binary '','None',_binary '',_binary '','Upload 10th, 12th and Undergraduation Marksheet (if any), for Verification','Upload Marksheets','MANDATORY','INITIATOR','MANDATORY',1),(3,_binary '','None',_binary '',_binary '\0','Upload any valid Government approved Id card for verification','Upload Govt Id','MANDATORY','INITIATOR','MANDATORY',1),(4,_binary '','None',_binary '',_binary '\0','Upload any valid Address proof for verification','Upload Address proof','MANDATORY','INITIATOR','MANDATORY',1),(5,_binary '','Approve,Reject',_binary '',_binary '\0','Verify the Documents uploaded by Students','Verification of Document Stage 1','MANDATORY','ROLE','OPTIONAL',1),(6,_binary '','Approve,Reject',_binary '\0',_binary '\0','Verify the Documents uploaded by Students','Verification of Document','MANDATORY','USER',NULL,1),(7,_binary '',NULL,_binary '\0',_binary '\0','END','END','MANDATORY','USER',NULL,2),(8,_binary '','Upload Thesis',_binary '',_binary '','Students should upload the thesis.','Upload Thesis','MANDATORY','INITIATOR','MANDATORY',2),(9,_binary '','Approve without modification,Approve with modification,Reject',_binary '',_binary '\0','The panel should upload the review of the students thesis. ','Thesis Exam ','MANDATORY','ROLE','MANDATORY',2),(10,_binary '','Pass,Fail',_binary '\0',_binary '\0','Oral exam verdict of student','Oral Exam','MANDATORY','ROLE',NULL,2),(11,_binary '',NULL,_binary '\0',_binary '\0','END','END','MANDATORY','USER',NULL,3),(12,_binary '','Upload',_binary '',_binary '','Upload your proposal','Upload Proposal','MANDATORY','INITIATOR','MANDATORY',3),(13,_binary '','Approve without modification,Approve with modification,Reject',_binary '',_binary '\0','Review the proposal exam given by the student','Proposal Exam','MANDATORY','ROLE','MANDATORY',3),(14,_binary '','Pass,Fail',_binary '\0',_binary '\0','Submit the verdict on proposal oral examination of the student.','Proposal Oral','MANDATORY','ROLE',NULL,3),(15,_binary '',NULL,_binary '\0',_binary '\0','END','END','MANDATORY','USER',NULL,4),(16,_binary '','upload',_binary '',_binary '\0','upload paper','upload paper','MANDATORY','INITIATOR','MANDATORY',4),(17,_binary '','approve without modification,approve with modification,reject',_binary '',_binary '\0','paper exam','paper exam','MANDATORY','ROLE','MANDATORY',4),(18,_binary '','Pass,Fail',_binary '\0',_binary '\0','paper oral','paper oral','MANDATORY','ROLE',NULL,4),(19,_binary '',NULL,_binary '\0',_binary '\0','END','END','MANDATORY','USER',NULL,5),(20,_binary '','upload',_binary '',_binary '','upload essay','upload essay','MANDATORY','INITIATOR','MANDATORY',5),(21,_binary '','approve without modification,approve with modification,reject',_binary '',_binary '\0','essay exam','essay exam','MANDATORY','ROLE','MANDATORY',5),(22,_binary '','pass,fail',_binary '\0',_binary '\0','essay oral','essay oral','MANDATORY','ROLE',NULL,5),(23,_binary '',NULL,_binary '\0',_binary '\0','END','END','MANDATORY','USER',NULL,6),(24,_binary '','Upload',_binary '',_binary '','idea upload','Idea Upload','MANDATORY','INITIATOR','MANDATORY',6),(25,_binary '','approve without modification,approve with modification,reject',_binary '',_binary '\0','idea review','Idea Review','MANDATORY','ROLE','MANDATORY',6),(26,_binary '','Pass,Fail',_binary '\0',_binary '\0','idea oral','Idea Oral','MANDATORY','ROLE',NULL,6);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_action`
--

DROP TABLE IF EXISTS `task_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_action` (
  `task_action_id` int NOT NULL AUTO_INCREMENT,
  `action_name` varchar(255) DEFAULT NULL,
  `task_task_id` int DEFAULT NULL,
  PRIMARY KEY (`task_action_id`),
  KEY `FK5ujq8ym4ard220mtoo5t3jm3h` (`task_task_id`),
  CONSTRAINT `FK5ujq8ym4ard220mtoo5t3jm3h` FOREIGN KEY (`task_task_id`) REFERENCES `task` (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_action`
--

LOCK TABLES `task_action` WRITE;
/*!40000 ALTER TABLE `task_action` DISABLE KEYS */;
INSERT INTO `task_action` VALUES (1,'None',2),(2,'None',3),(3,'None',4),(4,'Approve',5),(5,'Reject',5),(6,'Approve',6),(7,'Reject',6),(8,'Upload Thesis',8),(9,'Approve without modification',9),(10,'Approve with modification',9),(11,'Reject',9),(12,'Pass',10),(13,'Fail',10),(14,'Upload',12),(15,'Approve without modification',13),(16,'Approve with modification',13),(17,'Reject',13),(18,'Pass',14),(19,'Fail',14),(20,'upload',16),(21,'approve without modification',17),(22,'approve with modification',17),(23,'reject',17),(24,'Pass',18),(25,'Fail',18),(26,'upload',20),(27,'approve without modification',21),(28,'approve with modification',21),(29,'reject',21),(30,'pass',22),(31,'fail',22),(32,'Upload',24),(33,'approve without modification',25),(34,'approve with modification',25),(35,'reject',25),(36,'Pass',26),(37,'Fail',26);
/*!40000 ALTER TABLE `task_action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_instance`
--

DROP TABLE IF EXISTS `task_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_instance` (
  `task_instance_id` int NOT NULL AUTO_INCREMENT,
  `is_first_task_instance` bit(1) DEFAULT NULL,
  `task_instance_last_update` datetime(6) DEFAULT NULL,
  `task_status` enum('PENDING','COMPLETE','FAILED','APPROVED','REJECTED','UPLOADED') DEFAULT NULL,
  `task_id` int DEFAULT NULL,
  `workflow_instance_id` int DEFAULT NULL,
  `task_instance_created` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`task_instance_id`),
  KEY `FK2whjw3l710f799q5bcw5b1a61` (`task_id`),
  KEY `FK3iuu8w91hd4x0265lxoxlg7ku` (`workflow_instance_id`),
  CONSTRAINT `FK2whjw3l710f799q5bcw5b1a61` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`),
  CONSTRAINT `FK3iuu8w91hd4x0265lxoxlg7ku` FOREIGN KEY (`workflow_instance_id`) REFERENCES `workflow_instance` (`workflow_instance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_instance`
--

LOCK TABLES `task_instance` WRITE;
/*!40000 ALTER TABLE `task_instance` DISABLE KEYS */;
INSERT INTO `task_instance` VALUES (1,_binary '','2024-04-26 08:52:21.552346','COMPLETE',2,1,NULL),(2,_binary '\0','2024-04-26 08:52:36.179664','COMPLETE',3,1,NULL),(3,_binary '\0','2024-04-26 08:52:36.183780','COMPLETE',4,1,NULL),(4,_binary '\0','2024-04-26 08:53:03.262488','COMPLETE',5,1,NULL),(5,_binary '\0','2024-04-26 08:54:36.103410','COMPLETE',6,1,NULL),(6,_binary '','2024-04-29 19:24:53.377931','COMPLETE',8,2,NULL),(7,_binary '\0','2024-04-29 19:28:25.515184','COMPLETE',9,2,NULL),(8,_binary '','2024-04-29 21:19:27.884438','COMPLETE',12,3,NULL),(9,_binary '\0','2024-04-29 21:19:55.425328','COMPLETE',13,3,NULL),(10,_binary '\0','2024-04-29 21:23:31.907396','PENDING',12,3,NULL),(11,_binary '','2024-04-29 21:47:28.147072','COMPLETE',20,4,NULL),(12,_binary '\0','2024-04-29 21:47:52.701974','COMPLETE',21,4,NULL),(13,_binary '\0','2024-04-29 21:49:22.170857','COMPLETE',22,4,NULL),(14,_binary '','2024-04-29 21:56:39.652848','COMPLETE',20,5,NULL),(15,_binary '\0','2024-04-29 21:57:04.695118','COMPLETE',21,5,NULL),(16,_binary '\0','2024-04-29 21:58:47.711203','COMPLETE',22,5,NULL),(17,_binary '','2024-04-29 22:02:33.288241','COMPLETE',20,6,NULL),(18,_binary '','2024-04-29 22:02:47.538215','COMPLETE',20,7,NULL),(19,_binary '\0','2024-04-29 22:03:12.208309','COMPLETE',21,7,NULL),(20,_binary '\0','2024-04-29 22:04:25.269217','COMPLETE',22,7,NULL),(21,_binary '\0','2024-04-29 22:08:33.792186','COMPLETE',21,6,NULL),(22,_binary '\0','2024-04-29 22:09:47.935656','COMPLETE',22,6,NULL),(23,_binary '','2024-04-29 22:26:56.796802','COMPLETE',20,8,NULL),(24,_binary '\0','2024-04-29 22:28:04.919217','COMPLETE',21,8,NULL),(25,_binary '\0','2024-04-29 22:28:50.270900','COMPLETE',22,8,NULL),(26,_binary '','2024-04-30 14:42:02.806260','COMPLETE',20,9,'2024-04-30 14:41:00.736261'),(27,_binary '\0','2024-04-30 14:48:21.580851','COMPLETE',21,9,NULL),(28,_binary '\0','2024-04-30 14:50:52.145414','COMPLETE',22,9,NULL),(29,_binary '','2024-05-04 00:39:31.744481','PENDING',2,10,'2024-05-04 00:39:31.744475'),(30,_binary '','2024-05-04 00:43:03.482137','PENDING',20,11,'2024-05-04 00:43:03.482133'),(31,_binary '','2024-05-04 00:47:39.463552','PENDING',20,12,'2024-05-04 00:47:39.463525'),(32,_binary '','2024-05-04 00:53:53.967060','PENDING',20,13,'2024-05-04 00:53:53.967043'),(33,_binary '','2024-05-04 01:59:32.532459','PENDING',20,14,'2024-05-04 01:59:32.532435'),(34,_binary '','2024-05-04 02:04:20.672740','PENDING',20,15,'2024-05-04 02:04:20.672734'),(35,_binary '','2024-05-04 07:27:08.757113','COMPLETE',20,16,'2024-05-04 07:23:39.794924'),(36,_binary '\0','2024-05-04 07:31:35.136021','COMPLETE',21,16,NULL),(37,_binary '\0','2024-05-04 07:33:32.688533','COMPLETE',22,16,NULL),(38,_binary '','2024-05-04 07:42:42.755270','COMPLETE',20,17,'2024-05-04 07:42:26.327977'),(39,_binary '\0','2024-05-04 07:44:24.946977','COMPLETE',21,17,NULL),(40,_binary '\0','2024-05-04 07:45:16.952013','COMPLETE',20,17,NULL),(41,_binary '','2024-05-04 08:02:20.707088','COMPLETE',20,18,'2024-05-04 08:02:05.148200'),(42,_binary '\0','2024-05-04 08:03:23.235416','COMPLETE',21,18,NULL),(43,_binary '','2024-05-04 08:08:57.667262','COMPLETE',20,19,'2024-05-04 08:08:44.899953'),(44,_binary '\0','2024-05-04 08:09:55.187760','COMPLETE',21,19,NULL),(45,_binary '\0','2024-05-04 08:10:18.128999','COMPLETE',20,19,NULL),(46,_binary '\0','2024-05-04 08:10:53.304417','COMPLETE',21,19,NULL),(47,_binary '\0','2024-05-04 08:11:17.817684','COMPLETE',22,19,NULL);
/*!40000 ALTER TABLE `task_instance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_role`
--

DROP TABLE IF EXISTS `task_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_role` (
  `task_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`task_id`,`role_id`),
  KEY `FKq9b82hrkm27tnvauabpgm284a` (`role_id`),
  CONSTRAINT `FKd3xqvuqyhyjm8etco6ubmh67b` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`),
  CONSTRAINT `FKq9b82hrkm27tnvauabpgm284a` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_role`
--

LOCK TABLES `task_role` WRITE;
/*!40000 ALTER TABLE `task_role` DISABLE KEYS */;
INSERT INTO `task_role` VALUES (5,3),(9,6),(13,6),(17,6),(21,6),(25,6),(10,7),(14,7),(18,7),(22,7),(26,7);
/*!40000 ALTER TABLE `task_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_user`
--

DROP TABLE IF EXISTS `task_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_user` (
  `task_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`task_id`,`user_id`),
  KEY `FKs46ejm4kitq56yd498a3fnr19` (`user_id`),
  CONSTRAINT `FKd1fn28rqhh1ku21jx7hcksvh9` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`),
  CONSTRAINT `FKs46ejm4kitq56yd498a3fnr19` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_user`
--

LOCK TABLES `task_user` WRITE;
/*!40000 ALTER TABLE `task_user` DISABLE KEYS */;
INSERT INTO `task_user` VALUES (6,1);
/*!40000 ALTER TABLE `task_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_user_instance`
--

DROP TABLE IF EXISTS `task_user_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_user_instance` (
  `task_user_instance_id` int NOT NULL AUTO_INCREMENT,
  `action_status` varchar(255) DEFAULT NULL,
  `comment` varchar(1000) DEFAULT NULL,
  `eligible_action` varchar(255) DEFAULT NULL,
  `task_user_instance_last_update` datetime(6) DEFAULT NULL,
  `task_user_instance_status` enum('PENDING','COMPLETE','FAILED','APPROVED','REJECTED','UPLOADED') DEFAULT NULL,
  `upload_file_path` varchar(255) DEFAULT NULL,
  `task_instance_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `task_user_instance_created` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`task_user_instance_id`),
  KEY `FK588symykvfu3yptkdegq3cxtn` (`task_instance_id`),
  KEY `FKcdrvflts4xknkjftjyef7yv00` (`user_id`),
  CONSTRAINT `FK588symykvfu3yptkdegq3cxtn` FOREIGN KEY (`task_instance_id`) REFERENCES `task_instance` (`task_instance_id`),
  CONSTRAINT `FKcdrvflts4xknkjftjyef7yv00` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_user_instance`
--

LOCK TABLES `task_user_instance` WRITE;
/*!40000 ALTER TABLE `task_user_instance` DISABLE KEYS */;
INSERT INTO `task_user_instance` VALUES (1,'None',NULL,'None','2024-04-26 08:52:36.153968','COMPLETE','sample_upload',1,8,NULL),(2,'None',NULL,'None','2024-04-26 08:52:53.995630','COMPLETE','sample_upload',2,8,NULL),(3,'None',NULL,'None','2024-04-26 08:53:03.219147','COMPLETE','sample_upload',3,8,NULL),(4,'Approve','very good','Approve,Reject','2024-04-26 08:53:46.638532','COMPLETE','approval_file',4,4,NULL),(5,'Approve','very good','Approve,Reject','2024-04-26 08:54:36.058133','COMPLETE','approval_file',4,6,NULL),(6,'Approve','good','Approve,Reject','2024-04-26 08:55:00.492230','COMPLETE',NULL,5,1,NULL),(7,'Upload Thesis','Uploaded thesis document.','Upload Thesis','2024-04-29 19:28:25.484129','COMPLETE',NULL,6,7,NULL),(8,'Approve without modification','Very good thesis.','Approve without modification,Approve with modification,Reject','2024-04-29 19:33:59.242158','COMPLETE',NULL,7,10,NULL),(9,'Approve without modification','The thesis looks good.','Approve without modification,Approve with modification,Reject','2024-04-29 19:32:15.323903','COMPLETE',NULL,7,4,NULL),(10,'Upload','Uploaded phd proposal','Upload','2024-04-29 21:19:55.405168','COMPLETE','8_Hermoine_sample.txt',8,8,NULL),(11,'Approve without modification','this is good proposal','Approve without modification,Approve with modification,Reject','2024-04-29 21:23:31.873474','COMPLETE','9_Hermoine_sample.txt',9,10,NULL),(12,'Approve without modification','proposal looks good','Approve without modification,Approve with modification,Reject','2024-04-29 21:21:49.429464','COMPLETE','9_Hermoine_sample.txt',9,4,NULL),(13,NULL,NULL,'Upload','2024-04-29 21:23:31.910310','PENDING',NULL,10,8,NULL),(14,'upload','uploaded essay','upload','2024-04-29 21:47:52.669233','COMPLETE','11_Hagrid_sample.txt',11,10,NULL),(15,'approve without modification','very good','approve without modification,approve with modification,reject','2024-04-29 21:49:22.132514','COMPLETE','12_Hagrid_sample.txt',12,4,NULL),(16,'approve without modification','good','approve without modification,approve with modification,reject','2024-04-29 21:48:44.417445','COMPLETE','12_Hagrid_sample.txt',12,10,NULL),(17,'pass','looks good','pass,fail','2024-04-29 21:50:50.683017','COMPLETE',NULL,13,1,NULL),(18,'pass','good','pass,fail','2024-04-29 21:52:01.617238','COMPLETE',NULL,13,6,NULL),(19,'upload','uploaded essay','upload','2024-04-29 21:57:04.665395','COMPLETE','14_Ron_sample.txt',14,9,NULL),(20,'approve without modification','essay exam looks good','approve without modification,approve with modification,reject','2024-04-29 21:58:11.562424','COMPLETE','15_Ron_sample.txt',15,10,NULL),(21,'approve without modification','good work','approve without modification,approve with modification,reject','2024-04-29 21:58:47.659011','COMPLETE','15_Ron_sample.txt',15,4,NULL),(22,'pass','keep it up','pass,fail','2024-04-29 21:59:47.305349','COMPLETE',NULL,16,1,NULL),(23,'pass','okay','pass,fail','2024-04-29 21:59:11.305694','COMPLETE',NULL,16,6,NULL),(24,'upload','uploaded my essay','upload','2024-04-29 22:08:33.761993','COMPLETE','17_Harry_sample.txt',17,7,NULL),(25,'upload','uploaded','upload','2024-04-29 22:03:12.179649','COMPLETE','18_Harry_sample.txt',18,7,NULL),(26,'approve without modification','excellent work','approve without modification,approve with modification,reject','2024-04-29 22:03:51.460293','COMPLETE','19_Harry_sample.txt',19,4,NULL),(27,'approve without modification','innovative essay','approve without modification,approve with modification,reject','2024-04-29 22:04:25.226443','COMPLETE','19_Harry_sample.txt',19,10,NULL),(28,'pass','keep up the good work','pass,fail','2024-04-29 22:05:34.771564','COMPLETE',NULL,20,1,NULL),(29,'pass','good','pass,fail','2024-04-29 22:04:43.299629','COMPLETE',NULL,20,6,NULL),(30,'approve without modification','got good insight','approve without modification,approve with modification,reject','2024-04-29 22:09:07.930231','COMPLETE','21_Harry_sample.txt',21,4,NULL),(31,'approve without modification','new ideas','approve without modification,approve with modification,reject','2024-04-29 22:09:47.894764','COMPLETE','21_Harry_sample.txt',21,10,NULL),(32,'pass','guarantee','pass,fail','2024-04-29 22:10:12.227352','COMPLETE',NULL,22,6,NULL),(33,'pass','love it','pass,fail','2024-04-29 22:10:31.495752','COMPLETE',NULL,22,1,NULL),(34,'upload','uploaded','upload','2024-04-29 22:26:56.771541','COMPLETE','23_Ron_sample.txt',23,9,'2024-04-29 22:26:40.188137'),(35,'approve without modification','marvellous','approve without modification,approve with modification,reject','2024-04-29 22:27:39.056411','COMPLETE','24_Ron_sample.txt',24,4,'2024-04-29 22:26:56.809154'),(36,'approve without modification','love it','approve without modification,approve with modification,reject','2024-04-29 22:28:04.909366','COMPLETE','24_Ron_sample.txt',24,10,'2024-04-29 22:26:56.812262'),(37,'pass','spectacular','pass,fail','2024-04-29 22:28:50.212745','COMPLETE',NULL,25,1,'2024-04-29 22:28:04.952882'),(38,'pass','great job','pass,fail','2024-04-29 22:28:31.060026','COMPLETE',NULL,25,6,'2024-04-29 22:28:04.956050'),(39,'upload','uploaded essay','upload','2024-04-30 14:42:02.780570','COMPLETE','26_Harry_samplefile.log',26,7,'2024-04-30 14:41:00.739682'),(40,'approve without modification','exam well written','approve without modification,approve with modification,reject','2024-04-30 14:48:21.568579','COMPLETE','27_Harry_samplefile.log',27,10,'2024-04-30 14:42:02.821267'),(41,'approve without modification','essay exam looks good','approve without modification,approve with modification,reject','2024-04-30 14:47:11.139955','COMPLETE','27_Harry_samplefile.log',27,4,'2024-04-30 14:42:02.824177'),(42,'pass','good','pass,fail','2024-04-30 14:49:53.059333','COMPLETE',NULL,28,6,'2024-04-30 14:48:21.631836'),(43,'pass','wow','pass,fail','2024-04-30 14:50:52.096793','COMPLETE',NULL,28,1,'2024-04-30 14:48:21.635628'),(44,NULL,NULL,'None','2024-05-04 00:39:31.748319','PENDING',NULL,29,8,'2024-05-04 00:39:31.748313'),(45,NULL,NULL,'upload','2024-05-04 00:43:03.487538','PENDING',NULL,30,7,'2024-05-04 00:43:03.487536'),(46,NULL,NULL,'upload','2024-05-04 00:47:39.470998','PENDING',NULL,31,7,'2024-05-04 00:47:39.470993'),(47,NULL,NULL,'upload','2024-05-04 00:53:53.977959','PENDING',NULL,32,7,'2024-05-04 00:53:53.977942'),(48,NULL,NULL,'upload','2024-05-04 01:59:32.542272','PENDING',NULL,33,7,'2024-05-04 01:59:32.542261'),(49,NULL,NULL,'upload','2024-05-04 02:04:20.676382','PENDING',NULL,34,7,'2024-05-04 02:04:20.676378'),(50,'upload','','upload','2024-05-04 07:27:08.704662','COMPLETE','35_Ron_samplefile.log',35,9,'2024-05-04 07:23:39.798310'),(51,'approve without modification','good','approve without modification,approve with modification,reject','2024-05-04 07:28:49.960388','COMPLETE',NULL,36,4,'2024-05-04 07:27:08.773513'),(52,'approve without modification','very good','approve without modification,approve with modification,reject','2024-05-04 07:31:35.043300','COMPLETE',NULL,36,10,'2024-05-04 07:27:08.779201'),(53,'pass','excellent','pass,fail','2024-05-04 07:33:02.570323','COMPLETE',NULL,37,6,'2024-05-04 07:31:35.185583'),(54,'pass','wow','pass,fail','2024-05-04 07:33:32.620475','COMPLETE',NULL,37,1,'2024-05-04 07:31:35.192314'),(55,'upload','','upload','2024-05-04 07:42:42.725258','COMPLETE',NULL,38,9,'2024-05-04 07:42:26.334264'),(56,'approve with modification','needs improvement','approve without modification,approve with modification,reject','2024-05-04 07:43:39.152297','COMPLETE',NULL,39,4,'2024-05-04 07:42:42.769786'),(57,'approve without modification','looks good','approve without modification,approve with modification,reject','2024-05-04 07:44:24.924430','COMPLETE','39_Ron_samplefile.log',39,10,'2024-05-04 07:42:42.774195'),(58,'upload','uploaded again','upload','2024-05-04 07:45:26.151664','COMPLETE',NULL,40,9,'2024-05-04 07:44:24.988425'),(59,'upload','','upload','2024-05-04 08:02:20.673803','COMPLETE',NULL,41,9,'2024-05-04 08:02:05.152768'),(60,'approve with modification','improve','approve without modification,approve with modification,reject','2024-05-04 08:02:53.906883','COMPLETE',NULL,42,4,'2024-05-04 08:02:20.723498'),(61,'approve without modification','okay','approve without modification,approve with modification,reject','2024-05-04 08:03:23.212096','COMPLETE',NULL,42,10,'2024-05-04 08:02:20.728073'),(62,'upload','','upload','2024-05-04 08:08:57.649392','COMPLETE','43_Ron_samplefile.log',43,9,'2024-05-04 08:08:44.903999'),(63,'approve without modification','','approve without modification,approve with modification,reject','2024-05-04 08:09:21.185010','COMPLETE',NULL,44,4,'2024-05-04 08:08:57.690302'),(64,'approve with modification','','approve without modification,approve with modification,reject','2024-05-04 08:09:55.172451','COMPLETE',NULL,44,10,'2024-05-04 08:08:57.693768'),(65,'upload','','upload','2024-05-04 08:10:18.116014','COMPLETE',NULL,45,9,'2024-05-04 08:09:55.223883'),(66,'approve without modification','','approve without modification,approve with modification,reject','2024-05-04 08:10:53.291504','COMPLETE',NULL,46,10,'2024-05-04 08:10:18.158555'),(67,'approve without modification','','approve without modification,approve with modification,reject','2024-05-04 08:10:33.451651','COMPLETE',NULL,46,4,'2024-05-04 08:10:18.161896'),(68,'pass','','pass,fail','2024-05-04 08:11:17.763999','COMPLETE',NULL,47,1,'2024-05-04 08:10:53.325095'),(69,'pass','','pass,fail','2024-05-04 08:11:10.250119','COMPLETE',NULL,47,6,'2024-05-04 08:10:53.327987');
/*!40000 ALTER TABLE `task_user_instance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `user_email` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_j09k2v8lxofv2vecxu2hde9so` (`user_email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'albuspass','albus@gmail.com','Albus'),(2,'johnpass','john@gmail.com','John'),(3,'sybillpass','sybill@gmail.com','Sybill'),(4,'severuspass','severus.snape@gmail.com','Severus Snape'),(6,'minervapass','minerva@gmail.com','Minerva'),(7,'harrypass','harry@gmail.com','Harry'),(8,'hermoinepass','hermoine@gmail.com','Hermoine'),(9,'ronpass','ron@gmail.com','Ron'),(10,'hagridpass','hagrid@gmail.com','Hagrid');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (2,1),(3,1),(1,2),(4,3),(6,3),(10,3),(7,4),(8,4),(9,4),(2,5),(3,5),(4,5),(6,5),(4,6),(10,6),(1,7),(6,7);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workflow`
--

DROP TABLE IF EXISTS `workflow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workflow` (
  `workflow_id` int NOT NULL AUTO_INCREMENT,
  `all_role` bit(1) DEFAULT NULL,
  `workflow_description` varchar(1000) DEFAULT NULL,
  `workflow_name` varchar(255) NOT NULL,
  `workflow_creator` int DEFAULT NULL,
  PRIMARY KEY (`workflow_id`),
  KEY `FKaqi70kxhg3rpyujws0o31d2aj` (`workflow_creator`),
  CONSTRAINT `FKaqi70kxhg3rpyujws0o31d2aj` FOREIGN KEY (`workflow_creator`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflow`
--

LOCK TABLES `workflow` WRITE;
/*!40000 ALTER TABLE `workflow` DISABLE KEYS */;
INSERT INTO `workflow` VALUES (1,_binary '','This workflow is designed to review Student documents. As part of this workflow Students are required to submit all documents for admission process, followed by reviews by the institution.','Student Document Verification',3),(2,_binary '\0','This workflow is to review the thesis submission of students.','Thesis Review',4),(3,_binary '','This workflow is to review PHD proposal','Proposal Review',4),(4,_binary '','paper review workflow','Paper review',4),(5,_binary '','essay review','essay review',4),(6,_binary '','idea review','Idea Review',6);
/*!40000 ALTER TABLE `workflow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workflow_instance`
--

DROP TABLE IF EXISTS `workflow_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workflow_instance` (
  `workflow_instance_id` int NOT NULL AUTO_INCREMENT,
  `workflow_instance_last_update` datetime(6) DEFAULT NULL,
  `workflow_status` enum('PENDING','COMPLETE','FAILED','APPROVED','REJECTED','UPLOADED') DEFAULT NULL,
  `workflow_id` int DEFAULT NULL,
  `workflow_instantiator` int DEFAULT NULL,
  `workflow_instance_created` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`workflow_instance_id`),
  KEY `FK3ik20q7wkox68s44qin3nbwsb` (`workflow_id`),
  KEY `FK4qg402e0n5mrg8ki8pffi8fap` (`workflow_instantiator`),
  CONSTRAINT `FK3ik20q7wkox68s44qin3nbwsb` FOREIGN KEY (`workflow_id`) REFERENCES `workflow` (`workflow_id`),
  CONSTRAINT `FK4qg402e0n5mrg8ki8pffi8fap` FOREIGN KEY (`workflow_instantiator`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflow_instance`
--

LOCK TABLES `workflow_instance` WRITE;
/*!40000 ALTER TABLE `workflow_instance` DISABLE KEYS */;
INSERT INTO `workflow_instance` VALUES (1,'2024-04-26 08:52:21.536176','COMPLETE',1,8,NULL),(2,'2024-04-29 19:24:53.348802','PENDING',2,7,NULL),(3,'2024-04-29 21:19:27.851616','PENDING',3,8,NULL),(4,'2024-04-29 21:47:28.122265','PENDING',5,10,NULL),(5,'2024-04-29 21:56:39.626209','PENDING',5,9,NULL),(6,'2024-04-29 22:02:33.259393','COMPLETE',5,7,NULL),(7,'2024-04-29 22:02:47.527097','PENDING',5,7,NULL),(8,'2024-04-29 22:28:50.277369','COMPLETE',5,9,'2024-04-29 22:26:40.155141'),(9,'2024-04-30 14:50:52.150042','COMPLETE',5,7,'2024-04-30 14:41:00.706888'),(10,'2024-05-04 00:39:31.703168','PENDING',1,8,'2024-05-04 00:39:31.703115'),(11,'2024-05-04 00:43:03.452166','PENDING',5,7,'2024-05-04 00:43:03.452123'),(12,'2024-05-04 00:47:34.805055','PENDING',5,7,'2024-05-04 00:47:34.804560'),(13,'2024-05-04 00:53:49.891728','PENDING',5,7,'2024-05-04 00:53:49.891631'),(14,'2024-05-04 01:59:27.393081','PENDING',5,7,'2024-05-04 01:59:27.393020'),(15,'2024-05-04 02:04:20.630412','PENDING',5,7,'2024-05-04 02:04:20.630346'),(16,'2024-05-04 07:33:32.695744','COMPLETE',5,9,'2024-05-04 07:23:39.736873'),(17,'2024-05-04 07:42:26.285999','PENDING',5,9,'2024-05-04 07:42:26.285507'),(18,'2024-05-04 08:02:05.119528','PENDING',5,9,'2024-05-04 08:02:05.119515'),(19,'2024-05-04 08:11:17.826873','COMPLETE',5,9,'2024-05-04 08:08:44.863619');
/*!40000 ALTER TABLE `workflow_instance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workflow_role`
--

DROP TABLE IF EXISTS `workflow_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workflow_role` (
  `workflow_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`workflow_id`,`role_id`),
  KEY `FKjqhnns9qa6c1oc336muls3vet` (`role_id`),
  CONSTRAINT `FKcj98yfcdfct2yjcs7fyufc2k3` FOREIGN KEY (`workflow_id`) REFERENCES `workflow` (`workflow_id`),
  CONSTRAINT `FKjqhnns9qa6c1oc336muls3vet` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workflow_role`
--

LOCK TABLES `workflow_role` WRITE;
/*!40000 ALTER TABLE `workflow_role` DISABLE KEYS */;
INSERT INTO `workflow_role` VALUES (2,4);
/*!40000 ALTER TABLE `workflow_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-04 13:53:53
