/*
MySQL Backup
Database: finalproj
Backup Time: 2024-07-16 00:12:04
*/

SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `finalproj`.`user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
BEGIN;
LOCK TABLES `finalproj`.`user` WRITE;
DELETE FROM `finalproj`.`user`;
INSERT INTO `finalproj`.`user` (`id`,`username`) VALUES (1, 'peter'),(2, 'tom'),(3, 'test')
;
UNLOCK TABLES;
COMMIT;
