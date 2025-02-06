/*
SQLyog Trial v13.1.8 (64 bit)
MySQL - 8.0.41 : Database - classroom_management
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`classroom_management` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `classroom_management`;

/*Table structure for table `applications` */

DROP TABLE IF EXISTS `applications`;

CREATE TABLE `applications` (
  `id` int NOT NULL AUTO_INCREMENT,
  `student_id` int NOT NULL,
  `classroom_id` int NOT NULL,
  `reason` text NOT NULL,
  `status` enum('PENDING','APPROVED','REJECTED','EXPIRED') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'PENDING',
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `expire_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `student_id` (`student_id`),
  KEY `classroom_id` (`classroom_id`),
  CONSTRAINT `applications_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `users` (`id`),
  CONSTRAINT `applications_ibfk_2` FOREIGN KEY (`classroom_id`) REFERENCES `classrooms` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `applications` */

insert  into `applications`(`id`,`student_id`,`classroom_id`,`reason`,`status`,`start_time`,`end_time`,`created_time`,`expire_time`) values 
(1,3,1,'参加学术活动','APPROVED','2025-02-05 08:00:00','2025-02-05 10:00:00','2025-02-05 08:01:00',NULL),
(2,4,1,'小组讨论','PENDING','2025-02-06 09:00:00','2025-02-06 11:00:00','2025-02-05 08:30:00','2025-02-08 08:30:00'),
(3,5,1,'自习','PENDING','2025-02-07 10:00:00','2025-02-07 12:00:00','2025-02-05 09:00:00','2025-02-08 09:00:00'),
(4,6,1,'讨论会','EXPIRED','2025-01-25 13:00:00','2025-01-25 14:00:00','2025-02-05 10:00:00','2025-02-05 10:30:00'),
(5,3,1,'会议','PENDING','2025-02-06 10:00:00','2025-02-06 12:00:00','2025-02-05 20:00:00','2025-02-08 20:00:00'),
(6,4,2,'学习小组','PENDING','2025-02-07 14:00:00','2025-02-07 16:00:00','2025-02-05 20:10:00','2025-02-08 20:10:00'),
(7,5,2,'辅导班','APPROVED','2025-02-06 16:00:00','2025-02-06 18:00:00','2025-02-05 20:20:00',NULL),
(8,6,1,'讲座','APPROVED','2025-02-06 18:00:00','2025-02-06 20:00:00','2025-02-05 20:30:00',NULL),
(9,7,3,'教室试用','EXPIRED','2025-02-04 08:00:00','2025-02-04 10:00:00','2025-02-05 20:40:00','2025-02-05 20:40:00');

/*Table structure for table `classrooms` */

DROP TABLE IF EXISTS `classrooms`;

CREATE TABLE `classrooms` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `capacity` int NOT NULL,
  `has_multimedia` tinyint(1) DEFAULT '0',
  `is_available` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `classrooms` */

insert  into `classrooms`(`id`,`name`,`capacity`,`has_multimedia`,`is_available`) values 
(1,'教室一',50,1,1),
(2,'教室二',30,0,1),
(3,'教室三',100,1,1),
(4,'教室四',20,1,0),
(5,'教三-304',60,1,1),
(6,'教三-305',45,1,1),
(7,'教四-101',80,0,1),
(8,'教四-102',50,1,0),
(9,'教五-201',30,1,1),
(10,'教五-202',100,0,1),
(11,'教六-303',40,1,1),
(12,'教六-304',70,0,1),
(13,'教七-101',35,1,0),
(14,'教七-102',25,1,1);

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ADMIN','STUDENT') NOT NULL,
  `student_id` varchar(20) DEFAULT NULL,
  `department` varchar(50) DEFAULT NULL,
  `id_card` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `users` */

insert  into `users`(`id`,`username`,`password`,`role`,`student_id`,`department`,`id_card`) values 
(1,'admin1','ef92b778bafe771e89245b89ecbc8a44a4e166c6659911881f383d4473e94f','ADMIN',NULL,NULL,NULL),
(2,'admin2','c6ba91b9d922e159893f46c387e5dc1b3dc5c101a5a4522f03b987177a24a91','ADMIN',NULL,NULL,NULL),
(3,'student1','ef92b778bafe771e89245b89ecbc8a44a4e166c6659911881f383d4473e94f','STUDENT','3124001234','计算机科学','440112'),
(4,'student2','c6ba91b9d922e159893f46c387e5dc1b3dc5c101a5a4522f03b987177a24a91','STUDENT','3124005678','自动化','440113'),
(5,'student3','5efc2b17da4f7736d192a74dde5891369e0685d4d38f2a455b6fcdab282df9c','STUDENT','3124009876','土木工程','440114'),
(6,'student4','11f52f7857db2224674e279053d9b4778bf921d894cebd1e8bd42e431e9ca6','STUDENT','3124006543','生物学','440115'),
(7,'student5','6733b7ffeace4887c3b3125879c78d8db318db9cbc05c50df3521f968df8','STUDENT','3124004321','化学工程','440116'),
(44,'student6','19cb711dfa5c9915c57bc8209b23da7b9ecae22627bc957eb25bcf53182a81','STUDENT','3124001357','计算机科学','440117'),
(45,'student7','facbd7953fa9ee64e1c57738eae96c5f32c415b370111de2b3cfa6b59b23ad0','STUDENT','3124002468','电子工程','440118'),
(46,'student8','b25119da9d473a1fca39713db51d7f612c93faa88bfc2ebe4f07dcfd47fd266','STUDENT','3124007890','物理学','440119'),
(47,'student9','2ce45fb0bf5e7f9a599b713bf3981dfe94abe138b18e74ceaed1f894cb22','STUDENT','3124009081','化学工程','440120'),
(48,'student10','a34e1cc0c8b2b42bcbec122223f654b23ede095bbe0b499816cf0d23746aaac','STUDENT','3124005678','土木工程','440121'),
(49,'student11','b84c4783ab67bf8f841be5b6797d753f268b87a19723338dbcd66de5f6bf01e','STUDENT','3124002345','生物学','440122'),
(50,'student12','a7c5bbb9aee1c49beb8819da6b5855aea43da6cf58b1b8bcf703ec74a4b359d','STUDENT','3124008765','环境科学','440123'),
(51,'student13','55ab15f68e5fec2f15debbbda3dba0e5af8387a866359a8bb7a41e7b1f589','STUDENT','3124002341','医学','440124'),
(52,'student14','29fe5f91a54c416bd44a4864b7b286a205cc1bd85741fa2a599e6d41ed5884b','STUDENT','3124002340','农业科学','440125'),
(53,'student15','11f52f7857db2224674e279053d9b4778bf921d894cebd1e8bd42e431e9ca6','STUDENT','3124006542','语言学','440126'),
(58,'admin4','ef92b778bafe771e89245b89ecbc8a44a4e166c6659911881f383d4473e94f','ADMIN',NULL,NULL,NULL),
(59,'admin5','93ff4d79302417d6912b8c2620c1a5fcb8dbe35c1a351a8f3cd7560e3f4d4f2','ADMIN',NULL,NULL,NULL),
(60,'admin3','93ff4d79302417d6912b8c2620c1a5fcb8dbe35c1a351a8f3cd7560e3f4d4f2','ADMIN',NULL,NULL,NULL),
(61,'admin6','ef92b778bafe771e89245b89ecbc8a44a4e166c6659911881f383d4473e94f','ADMIN',NULL,NULL,NULL),
(62,'admin7','c6ba91b9d922e159893f46c387e5dc1b3dc5c101a5a4522f03b987177a24a91','ADMIN',NULL,NULL,NULL),
(63,'password234','93ff4d79302417d6912b8c2620c1a5fcb8dbe35c1a351a8f3cd7560e3f4d4f2','ADMIN',NULL,NULL,NULL),
(64,'password000','11f52f7857db2224674e279053d9b4778bf921d894cebd1e8bd42e431e9ca6','ADMIN',NULL,NULL,NULL),
(65,'password789','5efc2b17da4f7736d192a74dde5891369e0685d4d38f2a455b6fcdab282df9c','ADMIN',NULL,NULL,NULL),
(66,'passwordabc','6733b7ffeace4887c3b3125879c78d8db318db9cbc05c50df3521f968df8','ADMIN',NULL,NULL,NULL),
(67,'password999','29fe5f91a54c416bd44a4864b7b286a205cc1bd85741fa2a599e6d41ed5884b','ADMIN',NULL,NULL,NULL),
(68,'password888','55ab15f68e5fec2f15debbbda3dba0e5af8387a866359a8bb7a41e7b1f589','ADMIN',NULL,NULL,NULL),
(69,'password777','a7c5bbb9aee1c49beb8819da6b5855aea43da6cf58b1b8bcf703ec74a4b359d','ADMIN',NULL,NULL,NULL),
(70,'password666','b84c4783ab67bf8f841be5b6797d753f268b87a19723338dbcd66de5f6bf01e','ADMIN',NULL,NULL,NULL),
(71,'password555','a34e1cc0c8b2b42bcbec122223f654b23ede095bbe0b499816cf0d23746aaac','ADMIN',NULL,NULL,NULL),
(72,'password444','2ce45fb0bf5e7f9a599b713bf3981dfe94abe138b18e74ceaed1f894cb22','ADMIN',NULL,NULL,NULL),
(73,'password333','b25119da9d473a1fca39713db51d7f612c93faa88bfc2ebe4f07dcfd47fd266','ADMIN',NULL,NULL,NULL),
(74,'password222','facbd7953fa9ee64e1c57738eae96c5f32c415b370111de2b3cfa6b59b23ad0','ADMIN',NULL,NULL,NULL),
(75,'password111','19cb711dfa5c9915c57bc8209b23da7b9ecae22627bc957eb25bcf53182a81','ADMIN',NULL,NULL,NULL);

/*!50106 set global event_scheduler = 1*/;

/* Event structure for event `update_expired_applications` */

/*!50106 DROP EVENT IF EXISTS `update_expired_applications`*/;

DELIMITER $$

/*!50106 CREATE DEFINER=`root`@`localhost` EVENT `update_expired_applications` ON SCHEDULE EVERY 1 HOUR STARTS '2025-02-05 21:27:28' ON COMPLETION NOT PRESERVE ENABLE DO UPDATE Applications
    SET status = 'EXPIRED'
    WHERE expire_time < NOW() AND status = 'PENDING' */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
