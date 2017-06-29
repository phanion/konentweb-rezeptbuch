-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: rezeptbuch
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Temporary view structure for view `v_rezeptuser`
--

DROP TABLE IF EXISTS `v_rezeptuser`;
/*!50001 DROP VIEW IF EXISTS `v_rezeptuser`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `v_rezeptuser` AS SELECT 
 1 AS `authorid`,
 1 AS `authorlastname`,
 1 AS `authorfirstname`,
 1 AS `recipeid`,
 1 AS `recipename`,
 1 AS `description`,
 1 AS `durationPreparation`,
 1 AS `durationCooking`,
 1 AS `difficulty`,
 1 AS `servings`,
 1 AS `image`,
 1 AS `filename`,
 1 AS `ingredient`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `v_rezeptuser`
--

/*!50001 DROP VIEW IF EXISTS `v_rezeptuser`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_rezeptuser` AS select `u`.`ID` AS `authorid`,`u`.`lastName` AS `authorlastname`,`u`.`firstName` AS `authorfirstname`,`r`.`ID` AS `recipeid`,`r`.`name` AS `recipename`,`r`.`description` AS `description`,`r`.`durationPreparation` AS `durationPreparation`,`r`.`durationCooking` AS `durationCooking`,`r`.`difficulty` AS `difficulty`,`r`.`servings` AS `servings`,`r`.`image` AS `image`,`r`.`filename` AS `filename`,`i`.`ingredient` AS `ingredient` from ((`users` `u` join `recipes` `r` on((`u`.`ID` = `r`.`creator`))) left join `ingredients` `i` on((`r`.`ID` = `i`.`recipe`))) order by `r`.`ID` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-29 10:47:25
