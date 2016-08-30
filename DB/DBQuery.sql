-- MySQL dump 10.13  Distrib 5.7.11, for Win64 (x86_64)
--
-- Host: 192.168.1.167    Database: dbparking
-- ------------------------------------------------------
-- Server version	5.7.11

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
-- Table structure for table `t_admin`
--

DROP TABLE IF EXISTS `t_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_admin` (
  `admin_id` varchar(64) NOT NULL,
  `admin_password` varchar(64) NOT NULL,
  `admin_token` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_admin`
--

LOCK TABLES `t_admin` WRITE;
/*!40000 ALTER TABLE `t_admin` DISABLE KEYS */;
INSERT INTO `t_admin` VALUES ('8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','deca4a03814c2533efd11bc8dfa61fce410ff9a7520daedea366946074dffbde');
/*!40000 ALTER TABLE `t_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_heartbeatlog`
--

DROP TABLE IF EXISTS `t_heartbeatlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_heartbeatlog` (
  `heartbeatlog_id` int(11) NOT NULL AUTO_INCREMENT,
  `parkingfacility_id` int(11) NOT NULL,
  `parkingslot_floor` varchar(10) NOT NULL,
  `parkingslot_zone` varchar(10) NOT NULL,
  `heartbeatlog_datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`heartbeatlog_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_heartbeatlog`
--

LOCK TABLES `t_heartbeatlog` WRITE;
/*!40000 ALTER TABLE `t_heartbeatlog` DISABLE KEYS */;
INSERT INTO `t_heartbeatlog` VALUES (1,1,'1','1','2016-07-28 23:46:00');
/*!40000 ALTER TABLE `t_heartbeatlog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_parkingfacility`
--

DROP TABLE IF EXISTS `t_parkingfacility`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_parkingfacility` (
  `parkingfacility_id` int(11) NOT NULL AUTO_INCREMENT,
  `parkingfacility_am_price` int(11) NOT NULL,
  `parkingfacility_pm_price` int(11) NOT NULL,
  `parkingfacility_graceperiod` int(11) NOT NULL,
  PRIMARY KEY (`parkingfacility_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_parkingfacility`
--

LOCK TABLES `t_parkingfacility` WRITE;
/*!40000 ALTER TABLE `t_parkingfacility` DISABLE KEYS */;
INSERT INTO `t_parkingfacility` VALUES (1,4,5,15),(2,4,7,3);
/*!40000 ALTER TABLE `t_parkingfacility` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_parkingslot`
--

DROP TABLE IF EXISTS `t_parkingslot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_parkingslot` (
  `parkingslot_seq` int(11) NOT NULL AUTO_INCREMENT,
  `parkingslot_id` int(11) NOT NULL,
  `parkingfacility_id` int(11) NOT NULL,
  `parkingslot_floor` varchar(10) NOT NULL,
  `parkingslot_zone` varchar(10) NOT NULL,
  `parkingslot_state` tinyint(1) NOT NULL,
  PRIMARY KEY (`parkingslot_seq`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_parkingslot`
--

LOCK TABLES `t_parkingslot` WRITE;
/*!40000 ALTER TABLE `t_parkingslot` DISABLE KEYS */;
INSERT INTO `t_parkingslot` VALUES (1,1,1,'1','1',1),(2,2,1,'1','1',0),(3,3,1,'1','1',0),(4,4,1,'1','1',0);
/*!40000 ALTER TABLE `t_parkingslot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_resv`
--

DROP TABLE IF EXISTS `t_resv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_resv` (
  `resv_id` int(11) NOT NULL AUTO_INCREMENT,
  `parkingslot_seq` int(11) NOT NULL,
  `resv_phonenum` varchar(64) NOT NULL,
  `resv_creditnum` varchar(64) NOT NULL,
  `resv_starttime` datetime NOT NULL,
  `resv_authenticationnum` varchar(64) NOT NULL,
  `parking_starttime` datetime DEFAULT NULL,
  `parking_exittime` datetime DEFAULT NULL,
  `resv_cancel` varchar(10) NOT NULL,
  `parking_price` int(11) DEFAULT NULL,
  PRIMARY KEY (`resv_id`)
) ENGINE=MyISAM AUTO_INCREMENT=298 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_resv`
--

LOCK TABLES `t_resv` WRITE;
/*!40000 ALTER TABLE `t_resv` DISABLE KEYS */;
INSERT INTO `t_resv` VALUES (247,1,'N/mk+RXOGkkDF9fVWIMimA==','tB71MBnzYCtkReGzPX6E9CEXC22pWh9UZswvWe46VoU=','2016-07-28 17:20:00','5LN6u11VVCtPylG2p9LrhA==','2016-07-28 17:08:00','2016-07-28 17:09:00','terminate',7),(246,2,'gGCHYI/t9F34r8MdsfJfKA==','DT44i2BUmaCxUIeE1lTQCx9x7rIyNsl6xKhBvEtpCS0=','2016-07-28 17:20:00','ftFh/bAwXuo6kxAEdUCrmQ==','2016-07-28 17:06:00','2016-07-28 17:06:00','terminate',0),(245,3,'+PIv+y1QJ2zG77XvTBhuNw==','jx1yCPQCfxblrf4hiMVaSq+BDghqYM52gtdm6bcffUc=','2016-07-28 17:50:00','Oi6Si+vW30H+GC+tHGmAUQ==','2016-07-28 17:07:00','2016-07-28 17:09:00','terminate',7),(244,4,'jqbaZYkG7yWxLLCT350GvA==','ZD2YE7Ekk8j1eSTplWS/TFKJ6BSVGryqocqGBAVmHqg=','2016-07-28 17:50:00','CjsTTpFOUG6Ntq5abVQleQ==','2016-07-28 16:47:00','2016-07-28 17:04:00','terminate',7),(243,1,'PisLNEWN0jVQbOLplw1T5Q==','im6FBh4W2buSHKOcTs8KiqMW1ThO6Q9Qau9nIP48wMs=','2016-07-28 16:50:00','cgE6bpHMtBPpI4O6+RBksw==','2016-07-28 16:46:00','2016-07-28 17:04:00','terminate',7),(242,1,'2wISwUjo0U+iIAe6R/2rLg==','WcxDaOL3X1ZvIrGxKTXxz45mtdjelph9R3NaOiMZfn0=','2016-07-28 17:50:00','La1k8pmfG8R1H8FQhDqisw==','2016-07-28 16:44:00','2016-07-28 16:44:00','terminate',0),(241,1,'/5HDCuEiTNsKt8leoKxcfg==','EnfQSYGq/A7F/nj5WLyuJy85pWGerPI/+FiYURXMCKs=','2016-07-28 16:50:00','m7znhbsMEE0iL8Wxl2TgIw==','2016-07-28 16:39:00','2016-07-28 16:42:00','terminate',7),(240,4,'9BS68Gq2sDu4NPhebzjMPg==','3O57jkUH7vgNTwHM/STdKM+R9hdZvwJqN12J8ILSymo=','2016-07-28 16:50:00','Dj+Vg2ZW2Aoe06F7tUHciw==','2016-07-28 16:36:00','2016-07-28 16:43:00','terminate',7),(239,2,'9BS68Gq2sDu4NPhebzjMPg==','3O57jkUH7vgNTwHM/STdKM+R9hdZvwJqN12J8ILSymo=','2016-07-28 16:50:00','lrH/FqUY1hLHci7rsdVzvw==',NULL,NULL,'cancel',NULL),(238,2,'9BS68Gq2sDu4NPhebzjMPg==','3O57jkUH7vgNTwHM/STdKM+R9hdZvwJqN12J8ILSymo=','2016-07-28 16:50:00','tv9MpWmdwIpJV4bAguy0dw==',NULL,NULL,'cancel',NULL),(237,4,'9BS68Gq2sDu4NPhebzjMPg==','3O57jkUH7vgNTwHM/STdKM+R9hdZvwJqN12J8ILSymo=','2016-07-28 16:50:00','gWsulZvgoAFn+1HxZW2MYw==','2016-07-28 16:14:00','2016-07-28 16:15:00','terminate',6),(236,3,'uOo4uAZccdvMmhboed7x2Q==','mvui6gOYhKJ/mWU2/iXETNJhrwFwh6zdMBhL5973bY4=','2016-07-28 16:10:00','pqYWZAPAt8VmLOzC//Qlog==',NULL,NULL,'cancel',NULL),(235,1,'gojG9okLDQNDY4+/LUvbmg==','dFPhXCN9MH1JjvL2ck/Z5tVn1eGGUyzhFEqDfx2oUZI=','2016-07-28 16:00:00','aORvSPoCUYL/msIyh/MjGw==',NULL,NULL,'cancel',NULL),(234,3,'gojG9okLDQNDY4+/LUvbmg==','dFPhXCN9MH1JjvL2ck/Z5tVn1eGGUyzhFEqDfx2oUZI=','2016-07-28 15:50:00','rkMXWaV+6CGGpqXV3UzMzQ==','2016-07-28 15:49:00','2016-07-28 15:50:00','terminate',6),(231,4,'bX5iIfY84wj9AtWTzgbBbw==','0XHGo+8AZ8J8i6FGtnhkhqJL/b0rVfxzVhbYU6BJEqw=','2016-07-27 21:50:00','SlUNjAgFnF4RsZltIKvCRA==','2016-07-27 21:35:00','2016-07-27 21:36:00','terminate',6),(232,2,'lqvFKJ4jcWIF83PVUv+cfA==','RyFd19In1Zo1PKFagvUHkDkqf92gXZD6+Ev0J+xjSyY=','2016-07-27 21:50:00','L9r7U4T7Ctz2GjUz/8i19Q==','2016-07-27 21:36:00','2016-07-27 21:36:00','terminate',0),(233,1,'kQVc43e5v/FNjAKOt1GR5Q==','dFPhXCN9MH1JjvL2ck/Z5tVn1eGGUyzhFEqDfx2oUZI=','2016-07-28 15:50:00','8HQMR/CsbTj33KpdK2gYNQ==',NULL,NULL,'cancel',NULL),(228,1,'7lwbiej02+iIK4PZnnKXdw==','CxxMerVQ6Y5CXsz9kOL76SpWgn2Be4poodG0bAgdcH8=','2016-07-27 21:50:00','AbLOQQGD3bpolAb3e0L6vQ==','2016-07-27 21:31:00','2016-07-27 21:32:00','terminate',6),(227,1,'7lwbiej02+iIK4PZnnKXdw==','CxxMerVQ6Y5CXsz9kOL76SpWgn2Be4poodG0bAgdcH8=','2016-07-27 21:50:00','syz18oFez2fKFwFcB4fcmw==','2016-07-27 21:27:00','2016-07-27 21:30:00','terminate',6),(226,3,'BImddLWJRZ0B5gCXyqNv0A==','sITuzrxTFge4HAeye97Kr/V3PnjKvN5CIRRMBdw1GWw=','2016-07-27 22:50:00','VP6ezDPmsya7fzIH2xEafw==','2016-07-27 21:27:00','2016-07-27 21:30:00','terminate',6),(225,3,'/LLMW2W75xMDPIQia8HjFw==','ul408HJKUgC1IcPCBvVenG8/HTG2RpLhkHVhum4mRH0=','2016-07-27 22:50:00','wsgEeUr0NCHrqRaqX4/EjQ==','2016-07-27 21:25:00','2016-07-27 21:26:00','terminate',6),(224,1,'BImddLWJRZ0B5gCXyqNv0A==','qjTwMnFYWIbJhaRROtZx1ULSmVuy7PsrijF+yWoYlnE=','2016-07-27 21:50:00','PkbSO6qwsyVqyOcnljp9qQ==','2016-07-27 21:23:00','2016-07-27 21:23:00','terminate',0),(223,1,'dhQxbW1iijCRcxd8U73XGw==','ul408HJKUgC1IcPCBvVenG8/HTG2RpLhkHVhum4mRH0=','2016-07-27 22:50:00','oBdNZa+7eBMOC0Tny7TsOw==','2016-07-27 21:16:00','2016-07-27 21:16:00','terminate',0),(222,2,'BImddLWJRZ0B5gCXyqNv0A==','CyLtuGWVvIYUQzuUqojmZG1oO7nD98WdOk2ja9tN8E0=','2016-07-27 21:50:00','19p94D19bPACIIrXdegqrg==','2016-07-27 21:20:00','2016-07-27 21:21:00','terminate',6),(248,1,'25YFjPk2GWvy4K3uWIxAkQ==','sC49+h5QyLAj2B+P0LDurkEeVQSfc9UDwfKVRnnG4CM=','2016-07-28 17:50:00','LAO8YKxtbzvnzLVUuchhaQ==','2016-07-28 17:10:00','2016-07-28 17:10:00','terminate',0),(249,1,'N/mk+RXOGkkDF9fVWIMimA==','tB71MBnzYCtkReGzPX6E9CEXC22pWh9UZswvWe46VoU=','2016-07-28 17:20:00','ZZlqmqT1GZTQ+fphfj0Xeg==',NULL,NULL,'cancel',NULL),(250,4,'N/mk+RXOGkkDF9fVWIMimA==','tB71MBnzYCtkReGzPX6E9CEXC22pWh9UZswvWe46VoU=','2016-07-28 17:30:00','1Rbb4E1o/JdTmeHvMGFtdw==','2016-07-28 17:31:00','2016-07-28 17:31:00','terminate',0),(251,2,'25YFjPk2GWvy4K3uWIxAkQ==','sC49+h5QyLAj2B+P0LDurkEeVQSfc9UDwfKVRnnG4CM=','2016-07-28 17:50:00','QOvafSDJzq46RiSSHnIeVg==','2016-07-28 17:26:00','2016-07-28 17:26:00','terminate',0),(259,1,'25YFjPk2GWvy4K3uWIxAkQ==','sC49+h5QyLAj2B+P0LDurkEeVQSfc9UDwfKVRnnG4CM=','2016-07-28 17:50:00','ScHGwIcJXnxZqP2IwaLLtw==','2016-07-28 17:46:00','2016-07-28 17:46:00','terminate',0),(258,4,'25YFjPk2GWvy4K3uWIxAkQ==','sC49+h5QyLAj2B+P0LDurkEeVQSfc9UDwfKVRnnG4CM=','2016-07-28 17:50:00','HzLPoJAyHYV0Zxspkn7ejw==','2016-07-28 17:45:00','2016-07-28 17:45:00','terminate',0),(257,2,'N/mk+RXOGkkDF9fVWIMimA==','tB71MBnzYCtkReGzPX6E9CEXC22pWh9UZswvWe46VoU=','2016-07-28 19:00:00','NawjIkVfZYXiEo48pqXt5A==','2016-07-28 17:44:00','2016-07-28 17:45:00','terminate',8),(255,2,'N/mk+RXOGkkDF9fVWIMimA==','tB71MBnzYCtkReGzPX6E9CEXC22pWh9UZswvWe46VoU=','2016-07-28 17:40:00','Ll8EZQqp/nF0aV3yzWXo7Q==','2016-07-28 17:35:00','2016-07-28 17:41:00','terminate',8),(256,1,'N/mk+RXOGkkDF9fVWIMimA==','tB71MBnzYCtkReGzPX6E9CEXC22pWh9UZswvWe46VoU=','2016-07-28 19:00:00','SVi7blbk7dcqrLqYCYn3sw==',NULL,NULL,'cancel',NULL),(170,1,'9BS68Gq2sDu4NPhebzjMPg==','WcxDaOL3X1ZvIrGxKTXxz45mtdjelph9R3NaOiMZfn0=','2016-07-27 07:25:00','DSFvjajdvljaldjfovjvADC=',NULL,NULL,'cancel',NULL),(181,1,'9BS68Gq2sDu4NPhebzjMPg==','WcxDaOL3X1ZvIrGxKTXxz45mtdjelph9R3NaOiMZfn0=','2016-07-27 15:25:00','advddvDGVAgnalnlvkxz45mtdjelp=',NULL,NULL,'cancel',NULL),(180,1,'9BS68Gq2sDu4NPhebzjMPg==','WcxDaOL3X1ZvIrGxKTXxz45mtdjelph9R3NaOiMZfn0=','2016-07-27 08:25:00','xz45mtdjelph9R3NaOiMZfn0=','2016-07-27 07:40:00','2016-07-27 13:17:00','terminate',30),(172,1,'9BS68Gq2sDu4NPhebzjMPg==','WcxDaOL3X1ZvIrGxKTXxz45mtdjelph9R3NaOiMZfn0=','2016-07-27 09:25:00','QEGASDfhaDSFknzklnlafd=',NULL,NULL,'cancel',NULL),(188,2,'kQVc43e5v/FNjAKOt1GR5Q==','3O57jkUH7vgNTwHM/STdKM+R9hdZvwJqN12J8ILSymo=','2016-07-27 15:00:00','adsdasfnknvdnklvanlkdlan=',NULL,NULL,'cancel',NULL),(185,4,'kQVc43e5v/FNjAKOt1GR5Q==','3O57jkUH7vgNTwHM/STdKM+R9hdZvwJqN12J8ILSymo=','2016-07-27 14:25:00','ADVavdnklnadDFvaknlv=',NULL,NULL,'cancel',NULL),(175,3,'kQVc43e5v/FNjAKOt1GR5Q==','jx1yCPQCfxblrf4hiMVaSq+BDghqYM52gtdm6bcffUc=','2016-07-27 07:25:00','ADFADvnvralknglkdvah=',NULL,NULL,'cancel',NULL),(176,4,'kQVc43e5v/FNjAKOt1GR5Q==','jx1yCPQCfxblrf4hiMVaSq+BDghqYM52gtdm6bcffUc=','2016-07-27 07:25:00','kdnvkahaDSFknzklnlafd=','2016-07-27 07:12:00','2016-07-27 09:00:00','terminate',16),(177,3,'gojG9okLDQNDY4+/LUvbmg==','jx1yCPQCfxblrf4hiMVaSq+BDghqYM52gtdm6bcffUc=','2016-07-27 07:25:00','cakdmlkmlDAacDCknzklnlafd=','2016-07-27 06:12:00','2016-07-27 09:12:00','terminate',24),(178,3,'gojG9okLDQNDY4+/LUvbmg==','jx1yCPQCfxblrf4hiMVaSq+BDghqYM52gtdm6bcffUc=','2016-07-27 07:25:00','DVAGVBQWEGFadvhkhlkdasfj=',NULL,NULL,'cancel',NULL),(179,2,'kQVc43e5v/FNjAKOt1GR5Q==','jx1yCPQCfxblrf4hiMVaSq+BDghqYM52gtdm6bcffUc=','2016-07-27 07:25:00','GAVAevjkaldjvkjaldjo=','2016-07-27 06:40:00','2016-07-27 16:35:00','terminate',60),(260,1,'f9PLY16DYe94XClkHOCAfw==','0XHGo+8AZ8J8i6FGtnhkhqJL/b0rVfxzVhbYU6BJEqw=','2016-07-28 19:50:00','Q/uYU2igc7V0CHfhgrXZEQ==',NULL,NULL,'cancel',NULL),(140,4,'bX5iIfY84wj9AtWTzgbBbw==','0XHGo+8AZ8J8i6FGtnhkhqJL/b0rVfxzVhbYU6BJEqw=','2016-07-25 21:50:00','SlUNjAgFnF4RsZltIKvCRA==','2016-07-25 21:35:00','2016-07-25 21:36:00','terminate',6),(141,2,'lqvFKJ4jcWIF83PVUv+cfA==','RyFd19In1Zo1PKFagvUHkDkqf92gXZD6+Ev0J+xjSyY=','2016-07-25 21:50:00','L9r7U4T7Ctz2GjUz/8i19Q==','2016-07-25 21:36:00','2016-07-25 21:36:00','terminate',0),(142,1,'7lwbiej02+iIK4PZnnKXdw==','CxxMerVQ6Y5CXsz9kOL76SpWgn2Be4poodG0bAgdcH8=','2016-07-25 21:50:00','AbLOQQGD3bpolAb3e0L6vQ==','2016-07-25 21:31:00','2016-07-25 21:32:00','terminate',6),(143,1,'7lwbiej02+iIK4PZnnKXdw==','CxxMerVQ6Y5CXsz9kOL76SpWgn2Be4poodG0bAgdcH8=','2016-07-25 21:50:00','syz18oFez2fKFwFcB4fcmw==','2016-07-25 21:27:00','2016-07-25 21:30:00','terminate',6),(144,3,'BImddLWJRZ0B5gCXyqNv0A==','sITuzrxTFge4HAeye97Kr/V3PnjKvN5CIRRMBdw1GWw=','2016-07-25 22:50:00','VP6ezDPmsya7fzIH2xEafw==','2016-07-25 21:27:00','2016-07-25 21:30:00','terminate',6),(145,3,'/LLMW2W75xMDPIQia8HjFw==','ul408HJKUgC1IcPCBvVenG8/HTG2RpLhkHVhum4mRH0=','2016-07-25 22:50:00','wsgEeUr0NCHrqRaqX4/EjQ==','2016-07-25 21:25:00','2016-07-25 21:26:00','terminate',6),(146,1,'BImddLWJRZ0B5gCXyqNv0A==','qjTwMnFYWIbJhaRROtZx1ULSmVuy7PsrijF+yWoYlnE=','2016-07-25 21:50:00','PkbSO6qwsyVqyOcnljp9qQ==','2016-07-25 21:23:00','2016-07-25 21:23:00','terminate',0),(147,1,'dhQxbW1iijCRcxd8U73XGw==','ul408HJKUgC1IcPCBvVenG8/HTG2RpLhkHVhum4mRH0=','2016-07-25 22:50:00','oBdNZa+7eBMOC0Tny7TsOw==','2016-07-25 21:16:00','2016-07-25 21:16:00','terminate',0),(148,2,'BImddLWJRZ0B5gCXyqNv0A==','CyLtuGWVvIYUQzuUqojmZG1oO7nD98WdOk2ja9tN8E0=','2016-07-25 21:50:00','19p94D19bPACIIrXdegqrg==','2016-07-25 21:20:00','2016-07-25 21:21:00','terminate',6),(149,4,'bX5iIfY84wj9AtWTzgbBbw==','0XHGo+8AZ8J8i6FGtnhkhqJL/b0rVfxzVhbYU6BJEqw=','2016-07-26 21:50:00','SlUNjAgFnF4RsZltIKvCRA==','2016-07-26 21:35:00','2016-07-26 21:36:00','terminate',6),(150,2,'lqvFKJ4jcWIF83PVUv+cfA==','RyFd19In1Zo1PKFagvUHkDkqf92gXZD6+Ev0J+xjSyY=','2016-07-26 21:50:00','L9r7U4T7Ctz2GjUz/8i19Q==','2016-07-26 21:36:00','2016-07-26 21:36:00','terminate',6),(158,3,'lqvFKJ4jcWIF83PVUv+cfA==','sITuzrxTFge4HAeye97Kr/V3PnjKvN5CIRRMBdw1GWw=','2016-07-26 22:30:00','wsgEeUr0NCHrqRaqX4/EjQ==','2016-07-29 22:33:00','2016-07-29 22:45:36','terminate',12),(151,1,'7lwbiej02+iIK4PZnnKXdw==','CxxMerVQ6Y5CXsz9kOL76SpWgn2Be4poodG0bAgdcH8=','2016-07-26 21:50:00','AbLOQQGD3bpolAb3e0L6vQ==','2016-07-26 21:31:00','2016-07-26 21:32:00','terminate',6),(152,1,'7lwbiej02+iIK4PZnnKXdw==','CxxMerVQ6Y5CXsz9kOL76SpWgn2Be4poodG0bAgdcH8=','2016-07-26 21:50:00','syz18oFez2fKFwFcB4fcmw==','2016-07-26 21:27:00','2016-07-26 21:30:00','terminate',6),(153,3,'BImddLWJRZ0B5gCXyqNv0A==','sITuzrxTFge4HAeye97Kr/V3PnjKvN5CIRRMBdw1GWw=','2016-07-26 22:50:00','VP6ezDPmsya7fzIH2xEafw==','2016-07-26 21:27:00','2016-07-26 21:30:00','terminate',6),(154,3,'/LLMW2W75xMDPIQia8HjFw==','ul408HJKUgC1IcPCBvVenG8/HTG2RpLhkHVhum4mRH0=','2016-07-26 22:50:00','wsgEeUr0NCHrqRaqX4/EjQ==','2016-07-26 21:25:00','2016-07-26 21:26:00','terminate',6),(155,1,'BImddLWJRZ0B5gCXyqNv0A==','qjTwMnFYWIbJhaRROtZx1ULSmVuy7PsrijF+yWoYlnE=','2016-07-26 21:50:00','PkbSO6qwsyVqyOcnljp9qQ==','2016-07-26 21:23:00','2016-07-26 21:23:00','terminate',6),(156,1,'dhQxbW1iijCRcxd8U73XGw==','ul408HJKUgC1IcPCBvVenG8/HTG2RpLhkHVhum4mRH0=','2016-07-26 22:50:00','oBdNZa+7eBMOC0Tny7TsOw==','2016-07-26 21:16:00','2016-07-26 21:16:00','terminate',6),(157,2,'BImddLWJRZ0B5gCXyqNv0A==','CyLtuGWVvIYUQzuUqojmZG1oO7nD98WdOk2ja9tN8E0=','2016-07-26 21:50:00','19p94D19bPACIIrXdegqrg==','2016-07-26 21:20:00','2016-07-26 21:21:00','terminate',6),(261,1,'Zphp5lN7cpntNiDFAJsCFA==','OrsGHVZyNuq0UKvvtv1BsOEli4IllUooUobRIvQ/9MY=','2016-07-28 22:50:00','dl90GDu+HHLcHQw1cgHdVw==',NULL,NULL,'cancel',NULL),(262,2,'BImddLWJRZ0B5gCXyqNv0A==','0F3bmowyGZpE9AZneD8v1tFL9nYLX0Totq0a6oIirhE=','2016-07-28 22:50:00','UT1Es6LYsQn44OTjXynHpw==','2016-07-28 22:10:00','2016-07-28 22:10:00','terminate',0),(263,1,'gWPL42dRJwj3SE8pFmfTIA==','0XHGo+8AZ8J8i6FGtnhkhqJL/b0rVfxzVhbYU6BJEqw=','2016-07-28 22:50:00','utXYp+e+xhTM4JJAd1lAUg==','2016-07-28 22:12:00','2016-07-28 22:12:00','terminate',0),(264,2,'hH0H4YiMVYVcJQs+0sODCg==','Ihk4PUxaivTthpmtj+Z9ZSNLDq4q9JacafhxLPov9Ws=','2016-07-28 22:50:00','AAPgGNP1v89Ch99OYq7vCQ==','2016-07-28 22:13:00','2016-07-28 22:14:00','terminate',8),(265,1,'9YMdRIqz7UisJcUh/E46aw==','rghwL5K0PIL5+LSmBb9yriCr+bRaCKMT3wvUCMh0CAE=','2016-07-28 22:50:00','yOrZPZW+1C+oSjcSBhs55Q==','2016-07-28 22:15:00','2016-07-28 22:19:00','terminate',5),(266,3,'Cp3858NDkpkeevQa5IGUnA==','/PaC3jxEF8rxVMM/jzrRBVSrIS3TIM6AzKIwNRHy+rE=','2016-07-28 22:50:00','ZQx2dzSGCYpuxlr6PAxfGA==','2016-07-28 22:18:00','2016-07-28 22:18:00','terminate',0),(267,2,'F4Aa7xnL/zPGEwJYzdJhwg==','HLH1xfx/taXphachR1wKq2nSwIuKe4o9lmEajdj8uEs=','2016-07-28 22:50:00','tfv4MwED3ClqIowMz2JidQ==','2016-07-28 22:16:00','2016-07-28 22:17:00','terminate',8),(268,1,'BImddLWJRZ0B5gCXyqNv0A==','mBOfavvPEkgt/3Ov9GkieLhEbAME3pOlA57dJJSVFyg=','2016-07-28 22:50:00','B7KmIrib9jFcLHIVVlbaww==',NULL,NULL,'cancel',NULL),(273,1,'6r/MG4KRulLuwiaQaqM9QQ==','mBOfavvPEkgt/3Ov9GkieLhEbAME3pOlA57dJJSVFyg=','2016-07-28 23:50:00','0C+t/NExEdun1Flrjo0C1Q==',NULL,NULL,'cancel',NULL),(274,1,'6r/MG4KRulLuwiaQaqM9QQ==','mBOfavvPEkgt/3Ov9GkieLhEbAME3pOlA57dJJSVFyg=','2016-07-28 23:50:00','m2fTGuivIYQLHQQkqMhV2w==',NULL,NULL,'cancel',NULL),(275,1,'UuQ2x5PjAnX+p6Sa66Wyog==','/PaC3jxEF8rxVMM/jzrRBVSrIS3TIM6AzKIwNRHy+rE=','2016-07-28 23:50:00','gVo8neq3ssEsTY1Xf125aQ==',NULL,NULL,'cancel',NULL),(276,2,'kYFSkte9stVzAQOsExhecg==','mBOfavvPEkgt/3Ov9GkieLhEbAME3pOlA57dJJSVFyg=','2016-07-28 23:50:00','DvFGy+vBH5S91l7Z+6HMIw==',NULL,NULL,'cancel',NULL),(277,2,'kYFSkte9stVzAQOsExhecg==','mBOfavvPEkgt/3Ov9GkieLhEbAME3pOlA57dJJSVFyg=','2016-07-28 23:50:00','7goo6GAL4tEl6VOuRgLsNQ==',NULL,NULL,'cancel',NULL),(278,3,'E3f8Pbxr5zdp3/ldJGYa5g==','5caXbuKEgV1+P7IvmxR+oJusjwhPBYf2i/c1fjwgwgo=','2016-07-28 23:50:00','lGxFE756ZJvMt0Q/PI7RDg==',NULL,NULL,'cancel',NULL),(279,3,'E3f8Pbxr5zdp3/ldJGYa5g==','5caXbuKEgV1+P7IvmxR+oJusjwhPBYf2i/c1fjwgwgo=','2016-07-28 23:50:00','o7ENgJIA6DBaU6smRI/jJQ==',NULL,NULL,'cancel',NULL),(280,2,'kYFSkte9stVzAQOsExhecg==','mBOfavvPEkgt/3Ov9GkieLhEbAME3pOlA57dJJSVFyg=','2016-07-28 23:50:00','15Jd/ED6i4S9+Bj9WK9qAg==',NULL,NULL,'cancel',NULL),(281,3,'p1u+avdyZrA/AXpI/Blncw==','5caXbuKEgV1+P7IvmxR+oJusjwhPBYf2i/c1fjwgwgo=','2016-07-28 23:50:00','lRzTBdizhrjOESnqLoncUw==',NULL,NULL,'cancel',NULL),(282,3,'p1u+avdyZrA/AXpI/Blncw==','5caXbuKEgV1+P7IvmxR+oJusjwhPBYf2i/c1fjwgwgo=','2016-07-28 23:50:00','WWvbIBWVfQDUmHrXOCWOcQ==',NULL,NULL,'cancel',NULL),(283,2,'p1u+avdyZrA/AXpI/Blncw==','mBOfavvPEkgt/3Ov9GkieLhEbAME3pOlA57dJJSVFyg=','2016-07-28 23:50:00','8hQpUbLPorN/1WOX6zYNKw==',NULL,NULL,'cancel',NULL),(284,3,'mJ1AHHem7jLmgAnK28DWbA==','5caXbuKEgV1+P7IvmxR+oJusjwhPBYf2i/c1fjwgwgo=','2016-07-28 23:50:00','DY8S+Wc7cun+cr9BT4Mtqg==',NULL,NULL,'cancel',NULL),(285,1,'p1u+avdyZrA/AXpI/Blncw==','mBOfavvPEkgt/3Ov9GkieLhEbAME3pOlA57dJJSVFyg=','2016-07-28 23:50:00','v5mqwRI54k7cPgmxu1DHng==',NULL,NULL,'cancel',NULL),(286,2,'mJ1AHHem7jLmgAnK28DWbA==','5caXbuKEgV1+P7IvmxR+oJusjwhPBYf2i/c1fjwgwgo=','2016-07-28 23:50:00','BvOM8auRv2gTiKkVFuJfvg==',NULL,NULL,'cancel',NULL),(287,3,'zfEyo/HsniqcFzOzR6JcyQ==','xg+bgwfzNtBTF7lkrQ6fBpqjyn3dHGbUe4ezsw9ZwLc=','2016-07-29 00:50:00','BR4u8DQOZ3W2ASFqvqj6aQ==',NULL,NULL,'resv',NULL),(288,1,'7sJ8G6g0eYlzRPM3AFkFRg==','0XHGo+8AZ8J8i6FGtnhkhqJL/b0rVfxzVhbYU6BJEqw=','2016-07-29 00:50:00','Ae5zc23cIEN/SyBnUcG2kw==',NULL,NULL,'cancel',NULL),(289,1,'7sJ8G6g0eYlzRPM3AFkFRg==','0XHGo+8AZ8J8i6FGtnhkhqJL/b0rVfxzVhbYU6BJEqw=','2016-07-29 00:50:00','0C7zZtaatER2H1//XSc13A==',NULL,NULL,'cancel',NULL),(290,1,'7sJ8G6g0eYlzRPM3AFkFRg==','0XHGo+8AZ8J8i6FGtnhkhqJL/b0rVfxzVhbYU6BJEqw=','2016-07-29 00:50:00','2jFjbv2JirFbYI/9qdTpyw==',NULL,NULL,'cancel',NULL),(291,1,'7sJ8G6g0eYlzRPM3AFkFRg==','0XHGo+8AZ8J8i6FGtnhkhqJL/b0rVfxzVhbYU6BJEqw=','2016-07-29 00:50:00','oHMGfWAFwGwd5O5q+mRQYQ==',NULL,NULL,'cancel',NULL),(292,1,'7sJ8G6g0eYlzRPM3AFkFRg==','0XHGo+8AZ8J8i6FGtnhkhqJL/b0rVfxzVhbYU6BJEqw=','2016-07-29 00:50:00','f0W+BaCTO5PsFMRZ9jXkEw==',NULL,NULL,'cancel',NULL),(293,1,'btiQjZb+NmsfDgMQIsBCaQ==','0XHGo+8AZ8J8i6FGtnhkhqJL/b0rVfxzVhbYU6BJEqw=','2016-07-29 00:50:00','G16fPK194fGFjZc3lWfMuw==',NULL,NULL,'cancel',NULL),(294,1,'F681J6UZFpvf37pvfgy4Sw==','0XHGo+8AZ8J8i6FGtnhkhqJL/b0rVfxzVhbYU6BJEqw=','2016-07-29 00:50:00','0O+HiIwNqoHjRnU5u28wTg==',NULL,NULL,'cancel',NULL),(295,1,'F681J6UZFpvf37pvfgy4Sw==','0XHGo+8AZ8J8i6FGtnhkhqJL/b0rVfxzVhbYU6BJEqw=','2016-07-29 00:50:00','tRpi7rTWzOWpljeRj1HZNA==',NULL,NULL,'cancel',NULL),(296,1,'IxQNFZkZixmIepUoRrGJVw==','0XHGo+8AZ8J8i6FGtnhkhqJL/b0rVfxzVhbYU6BJEqw=','2016-07-29 00:50:00','6fUEZn23aiavAAnRYB3Cfg==',NULL,NULL,'cancel',NULL),(297,1,'SdGH7hBO5e7dvYffJaWUCg==','0XHGo+8AZ8J8i6FGtnhkhqJL/b0rVfxzVhbYU6BJEqw=','2016-07-29 00:50:00','0kIRIIHU4evKklo1XAauFQ==',NULL,NULL,'resv',NULL);
/*!40000 ALTER TABLE `t_resv` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-07-29  0:32:25
