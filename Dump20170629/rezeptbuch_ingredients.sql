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
-- Table structure for table `ingredients`
--

DROP TABLE IF EXISTS `ingredients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ingredients` (
  `recipe` bigint(20) DEFAULT NULL,
  `ingredient` varchar(255) DEFAULT NULL,
  `unit` varchar(20) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  KEY `recipe` (`recipe`),
  CONSTRAINT `ingredients_ibfk_1` FOREIGN KEY (`recipe`) REFERENCES `recipes` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredients`
--

LOCK TABLES `ingredients` WRITE;
/*!40000 ALTER TABLE `ingredients` DISABLE KEYS */;
INSERT INTO `ingredients` VALUES (9,'Nudeln (Spaghetti)','Gramm',400),(9,'Öl','Esslöffel',2),(9,'Butter','Gramm',25),(9,'Hühnerbrühe','Milliliter',200),(9,'Sherry','Esslöffel',3),(9,'Thunfisch','Gramm',200),(9,'Sahne','Esslöffel',2),(9,'Petersilie','Esslöffel',3),(12,'Paprikaschoten','Stück',5),(12,'Vollkornreis','Gramm',500),(12,'Gemüse nach Wahl','Kilogramm',1),(12,'Hähnchen','Gramm',400),(12,'Frischkäse','Stück',1),(12,'Zwiebel','Stück',1),(12,'Käse, gerieben','Gramm',150),(12,'Salz und Pfeffer','Prise',1),(12,'Gemüsebrühe','Milliliter',250),(13,'Kartoffeln','Gramm',500),(13,'Brokkoli','Stück',1),(13,'Blumenkohl','Stück',1),(13,'Frischkäse','Gramm',150),(13,'Käse, gerieben','Esslöffel',1),(13,'Salz und Pfeffer','Prise',1),(13,'Knoblauchzehe','Stück',1),(14,'Hänchenbrust','Kilogramm',1),(14,'Porree','Stück',2),(14,'Champignons','Gramm',600),(14,'Paprikaschoten','Stück',2),(14,'Zwiebeln','Stück',2),(14,'Sahneschmelzkäse','Gramm',200),(14,'Kräuterschmelzkäse','Gramm',200),(14,'Gemüsebrühe','Liter',1),(14,'Schlagsahne','Milliliter',150),(14,'Teriyakisauce','Milliliter',50),(14,'Currypaste','Teelöffel',2),(14,'Mehl','Esslöffel',3),(14,'Currypulver','Esslöffel',2),(14,'Petersilie, getrocknet','Esslöffel',3),(14,'Margerine oder Butter','Gramm',50),(14,'Salz und Pfeffer','Prise',1),(14,'Öl zum Braten','Esslöffel',1),(15,'Spargel','Gramm',200),(15,'Paprika','Stück',1),(15,'Zucchini','Stück',1),(15,'Lauchzwiebeln','Stück',3),(15,'Rinderhackfleisch','Gramm',500),(15,'Kokosöl','Teelöffel',1),(15,'Salz','Prise',1),(15,'Paprikapulver','Prise',1),(16,'Erdbeeren','Gramm',1500),(17,'Vodka','Centiliter',5),(17,'Red Bull','Centiliter',5),(17,'Eiswürfel','sonstiges',1),(18,'Wurst','Gramm',500),(18,'Eier','Stück',5),(18,'Tomaten','Stück',6),(18,'Zwiebel','Stück',1),(18,'Schlagsahne','Milliliter',100),(19,'Schwarztee','Milliliter',500),(19,'Wasser','Milliliter',1500),(19,'Zitrone','Stück',1),(19,'Erdbeersirup','Esslöffel',4),(19,'Eiswürfel','Stück',10),(23,'Schweinelende','Stück',1),(23,'Balsamico','Milliliter',40),(23,'Olivenöl','Milliliter',60),(23,'Sojasauce','Esslöffel',1),(23,'Pfeffer','Esslöffel',1),(23,'Salz','Teelöffel',1),(23,'Paprikapulver','Teelöffel',1),(23,'Rosmarinmandeln','Gramm',10),(26,'Butter','Gramm',60),(26,'Zucker','Gramm',60),(26,'Mehl','Gramm',30),(26,'Macadamianüsse, gemahlen','Gramm',70),(26,'Zitronenabrieb','Teelöffel',1),(26,'Eiweiß','Stück',2),(26,'Himbeeren, gefrohren','Stück',6),(32,'Limetten','Stück',4),(32,'Wodka','Centiliter',8),(32,'Eiswürfel','sonstiges',1),(32,'Ginger Beer','Milliliter',500),(21,'Milch','Milliliter',800),(21,'Weichweizengrieß','Gramm',80),(21,'Zucker','Esslöffel',2),(21,'Packung Vanillezucker','Stück',1),(21,'Salz','Prise',1),(21,'Ei','Stück',1),(21,'Butter','Gramm',25),(33,'Mehl Type 405','Gramm',500),(33,'Wasser','Milliliter',300),(33,'Salz','Teelöffel',2),(33,'Hefe','Gramm',5),(33,'Dose Tomaten','sonstiges',1),(33,'Salz und Pfeffer','Prise',1),(33,'Zucker','Prise',1),(33,'Basilikum','Teelöffel',1),(33,'Oregano','Teelöffel',1),(34,'Kartoffel, mehlig kochend','Gramm',500),(34,'Stärkemehl','Gramm',100),(34,'Eigelb','Stück',1),(34,'Butter','Esslöffel',1),(34,'Salz','Prise',1),(34,'Muskat','Prise',1),(35,'Bund Basilikum, gezupft','sonstiges',1),(35,'Olivenöl','Milliliter',120),(35,'Knoblauchzehe, gepellt, klein','Stück',1),(35,'Parmesan, frisch gerieben','Gramm',60),(35,'Pinienkerne, geröstet','Gramm',30),(36,'Weißbrot, altbackenes','Gramm',300),(36,'Milch','Milliliter',250),(36,'Spinat','Gramm',800),(36,'Butter','Gramm',30),(36,'Zwiebel','Stück',1),(36,'Eier','Stück',2),(36,'Salz und Pfeffer','sonstiges',1),(36,'Mehl','Esslöffel',1),(36,'Semmelbrösel','Esslöffel',2),(36,'Butter','Gramm',80),(36,'Parmesan, frisch gerieben','sonstiges',1),(36,'Knoblauchzehe','Stück',1),(36,'Muskat','Prise',1);
/*!40000 ALTER TABLE `ingredients` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-29 10:47:24
