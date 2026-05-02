-- MySQL dump 10.13  Distrib 5.7.31, for Linux (x86_64)
--
-- Host: localhost    Database: springboot02b8755d
-- ------------------------------------------------------
-- Server version	5.7.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `springboot02b8755d`
--

/*!40000 DROP DATABASE IF EXISTS `springboot02b8755d`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `springboot02b8755d` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `springboot02b8755d`;

--
-- Table structure for table `chukufenbo`
--

DROP TABLE IF EXISTS `chukufenbo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chukufenbo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `shenlingbianhao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '申领编号',
  `wuzimingcheng` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资名称',
  `wuzizhonglei` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资种类',
  `jigouzhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构账号',
  `jigoumingcheng` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构名称',
  `wuzitupian` longtext COLLATE utf8mb4_unicode_ci COMMENT '物资图片',
  `wuzishuliang` int(11) DEFAULT NULL COMMENT '出库数量',
  `chukudan` longtext COLLATE utf8mb4_unicode_ci COMMENT '出库单',
  `chukushijian` datetime DEFAULT NULL COMMENT '出库时间',
  `wuliuzhuangtai` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物流状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='出库分拨';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chukufenbo`
--

LOCK TABLES `chukufenbo` WRITE;
/*!40000 ALTER TABLE `chukufenbo` DISABLE KEYS */;
INSERT INTO `chukufenbo` VALUES (1,'2026-04-27 00:55:30','申领编号1','压缩饼干','食品饮水类','24','青禾公益成长计划','upload/chukufenbo_压缩饼干1.jpg,upload/chukufenbo_压缩饼干2.jpg,upload/chukufenbo_压缩饼干3.jpg',292,'','2026-04-27 08:55:30','已签收'),(2,'2026-04-27 00:55:30','申领编号2','铁锹','抢险工具类','25','福泽公益服务中心','upload/chukufenbo_铁锹1.jpg,upload/chukufenbo_铁锹2.jpg,upload/chukufenbo_铁锹3.jpg',437,'','2026-04-27 08:55:30','已签收'),(3,'2026-04-27 00:55:30','申领编号3','方便面','食品饮水类','27','仁善公益行动联盟','upload/chukufenbo_方便面1.jpg,upload/chukufenbo_方便面2.jpg,upload/chukufenbo_方便面3.jpg',412,'','2026-04-27 08:55:30','已签收'),(4,'2026-04-27 00:55:30','申领编号4','体温计','医疗救护类','26','筑梦公益服务站','upload/chukufenbo_体温计1.jpg,upload/chukufenbo_体温计2.jpg,upload/chukufenbo_体温计3.jpg',235,'','2026-04-27 08:55:30','已签收'),(5,'2026-04-27 00:55:30','申领编号5','绳索','抢险工具类','21','春苗公益成长计划','upload/chukufenbo_绳索1.jpg,upload/chukufenbo_绳索2.jpg,upload/chukufenbo_绳索3.jpg',459,'','2026-04-27 08:55:30','已签收'),(6,'2026-04-27 00:55:30','申领编号6','棉被','救灾安置类','28','德善公益行动联盟','upload/chukufenbo_棉被1.jpg,upload/chukufenbo_棉被2.jpg,upload/chukufenbo_棉被3.jpg',267,'','2026-04-27 08:55:30','已签收'),(7,'2026-04-27 00:55:30','申领编号7','雨衣','防护装备类','23','乐助公益服务中心','upload/chukufenbo_雨衣1.jpg,upload/chukufenbo_雨衣2.jpg,upload/chukufenbo_雨衣3.jpg',307,'','2026-04-27 08:55:30','已签收'),(8,'2026-04-27 00:55:30','申领编号8','消毒液','防疫消杀类','22','益启公益发展基金会','upload/chukufenbo_消毒液1.jpg,upload/chukufenbo_消毒液2.jpg,upload/chukufenbo_消毒液3.jpg',345,'','2026-04-27 08:55:30','已签收'),(9,'2026-04-27 01:06:11','1777251915647','压缩饼干','食品饮水类','22','益启公益发展基金会','upload/wuzixinxi_压缩饼干1.jpg,upload/wuzixinxi_压缩饼干2.jpg,upload/wuzixinxi_压缩饼干3.jpg',100,'upload/1777251967403_479.doc','2026-04-27 09:05:52','已签收');
/*!40000 ALTER TABLE `chukufenbo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config`
--

DROP TABLE IF EXISTS `config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '配置参数名称',
  `value` varchar(200) DEFAULT NULL COMMENT '配置参数值',
  `url` varchar(500) DEFAULT NULL COMMENT 'url',
  `type` int(11) DEFAULT NULL COMMENT '参数类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='配置文件';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config`
--

LOCK TABLES `config` WRITE;
/*!40000 ALTER TABLE `config` DISABLE KEYS */;
INSERT INTO `config` VALUES (1,'picture1','upload/picture1.jpg',NULL,1),(2,'picture2','upload/picture2.jpg',NULL,1),(3,'picture3','upload/picture3.jpg',NULL,1),(11,'baidu','{\"appId\":\"49214550\",\"apiKey\":\"7Otjpv2kn0ljQk45qXOXh5MO\",\"secretKey\":\"BMfbXRbTIVaB4C3SbRTtGqDv1wHDvyXS\"}',NULL,2),(14,'deepseek','{\"key\":\"sk-961af215e60e4f269c2808b3a55eb9e8\"}',NULL,2),(21,'bLoginBackgroundImg','',NULL,3),(22,'bRegisterBackgroundImg','',NULL,3),(23,'bIndexBackgroundImg','',NULL,3),(24,'bTopLogo','',NULL,3),(25,'bHomeLogo','',NULL,3),(26,'fLoginBackgroundImg','',NULL,3),(27,'fRegisterBackgroudImg','',NULL,3),(28,'fTopLogo','',NULL,3);
/*!40000 ALTER TABLE `config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discussshiyongfankui`
--

DROP TABLE IF EXISTS `discussshiyongfankui`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discussshiyongfankui` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `refid` bigint(20) NOT NULL COMMENT '关联表id',
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `avatarurl` longtext COLLATE utf8mb4_unicode_ci COMMENT '头像',
  `nickname` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户名',
  `content` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评论内容',
  `reply` longtext COLLATE utf8mb4_unicode_ci COMMENT '回复内容',
  `thumbsupnum` int(11) DEFAULT '0' COMMENT '赞',
  `crazilynum` int(11) DEFAULT '0' COMMENT '踩',
  `istop` int(11) DEFAULT '0' COMMENT '置顶(1:置顶,0:非置顶)',
  `tuserids` longtext COLLATE utf8mb4_unicode_ci COMMENT '赞用户ids',
  `cuserids` longtext COLLATE utf8mb4_unicode_ci COMMENT '踩用户ids',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='使用反馈评论';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discussshiyongfankui`
--

LOCK TABLES `discussshiyongfankui` WRITE;
/*!40000 ALTER TABLE `discussshiyongfankui` DISABLE KEYS */;
/*!40000 ALTER TABLE `discussshiyongfankui` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jieshoujigou`
--

DROP TABLE IF EXISTS `jieshoujigou`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jieshoujigou` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `jigouzhanghao` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '机构账号',
  `jigoumingcheng` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '机构名称',
  `mima` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `quyu` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '区域',
  `jigoujianjie` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构简介',
  `jigoudizhi` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构地址',
  `yingyezhizhao` longtext COLLATE utf8mb4_unicode_ci COMMENT '营业执照',
  `jigoudengjizhengshu` longtext COLLATE utf8mb4_unicode_ci COMMENT '机构登记证书',
  `lianxifangshi` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系方式',
  `touxiang` longtext COLLATE utf8mb4_unicode_ci COMMENT '头像',
  `sfsh` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '待审核' COMMENT '是否审核',
  `shhf` longtext COLLATE utf8mb4_unicode_ci COMMENT '审核回复',
  `status` int(11) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `jigouzhanghao` (`jigouzhanghao`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='接收机构';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jieshoujigou`
--

LOCK TABLES `jieshoujigou` WRITE;
/*!40000 ALTER TABLE `jieshoujigou` DISABLE KEYS */;
INSERT INTO `jieshoujigou` VALUES (21,'2026-04-27 00:55:29','24','青禾公益成长计划','123456','昆明','机构简介1','机构地址1','','','13555556665','upload/jieshoujigou_touxiang1.jpg','是','',0),(22,'2026-04-27 00:55:29','25','福泽公益服务中心','123456','银川','机构简介2','机构地址2','','','13922223332','upload/jieshoujigou_touxiang2.jpg','是','',0),(23,'2026-04-27 00:55:29','27','仁善公益行动联盟','123456','长沙','机构简介3','机构地址3','','','15477788886','upload/jieshoujigou_touxiang3.jpg','是','',0),(24,'2026-04-27 00:55:29','26','筑梦公益服务站','123456','南昌','机构简介4','机构地址4','','','15922233331','upload/jieshoujigou_touxiang4.jpg','是','',0),(25,'2026-04-27 00:55:29','21','春苗公益成长计划','123456','深圳','机构简介5','机构地址5','','','13466667776','upload/jieshoujigou_touxiang5.jpg','是','',0),(26,'2026-04-27 00:55:29','28','德善公益行动联盟','123456','重庆','机构简介6','机构地址6','','','13288889998','upload/jieshoujigou_touxiang6.jpg','是','',0),(27,'2026-04-27 00:55:29','23','乐助公益服务中心','123456','乌鲁木齐','机构简介7','机构地址7','','','15100011119','upload/jieshoujigou_touxiang7.jpg','是','',0),(28,'2026-04-27 00:55:29','22','益启公益发展基金会','123456','杭州','机构简介8','机构地址8','','','13644445554','upload/jieshoujigou_touxiang8.jpg','是','',0);
/*!40000 ALTER TABLE `jieshoujigou` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jieshouxinxi`
--

DROP TABLE IF EXISTS `jieshouxinxi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jieshouxinxi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `shenlingbianhao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '申领编号',
  `wuzimingcheng` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资名称',
  `wuzizhonglei` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资种类',
  `jigouzhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构账号',
  `jigoumingcheng` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构名称',
  `wuzishuliang` int(11) DEFAULT NULL COMMENT '出库数量',
  `chukudan` longtext COLLATE utf8mb4_unicode_ci COMMENT '出库单',
  `qianshoutupian` longtext COLLATE utf8mb4_unicode_ci COMMENT '签收图片',
  `qianshoushijian` datetime DEFAULT NULL COMMENT '签收时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='接收信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jieshouxinxi`
--

LOCK TABLES `jieshouxinxi` WRITE;
/*!40000 ALTER TABLE `jieshouxinxi` DISABLE KEYS */;
INSERT INTO `jieshouxinxi` VALUES (1,'2026-04-27 00:55:30','申领编号1','压缩饼干','食品饮水类','24','青禾公益成长计划',292,'','upload/jieshouxinxi_压缩饼干1.jpg,upload/jieshouxinxi_压缩饼干2.jpg,upload/jieshouxinxi_压缩饼干3.jpg','2026-04-27 08:55:30'),(2,'2026-04-27 00:55:30','申领编号2','铁锹','抢险工具类','25','福泽公益服务中心',437,'','upload/jieshouxinxi_铁锹1.jpg,upload/jieshouxinxi_铁锹2.jpg,upload/jieshouxinxi_铁锹3.jpg','2026-04-27 08:55:30'),(3,'2026-04-27 00:55:30','申领编号3','方便面','食品饮水类','27','仁善公益行动联盟',412,'','upload/jieshouxinxi_方便面1.jpg,upload/jieshouxinxi_方便面2.jpg,upload/jieshouxinxi_方便面3.jpg','2026-04-27 08:55:30'),(4,'2026-04-27 00:55:30','申领编号4','体温计','医疗救护类','26','筑梦公益服务站',235,'','upload/jieshouxinxi_体温计1.jpg,upload/jieshouxinxi_体温计2.jpg,upload/jieshouxinxi_体温计3.jpg','2026-04-27 08:55:30'),(5,'2026-04-27 00:55:30','申领编号5','绳索','抢险工具类','21','春苗公益成长计划',459,'','upload/jieshouxinxi_绳索1.jpg,upload/jieshouxinxi_绳索2.jpg,upload/jieshouxinxi_绳索3.jpg','2026-04-27 08:55:30'),(6,'2026-04-27 00:55:30','申领编号6','棉被','救灾安置类','28','德善公益行动联盟',267,'','upload/jieshouxinxi_棉被1.jpg,upload/jieshouxinxi_棉被2.jpg,upload/jieshouxinxi_棉被3.jpg','2026-04-27 08:55:30'),(7,'2026-04-27 00:55:30','申领编号7','雨衣','防护装备类','23','乐助公益服务中心',307,'','upload/jieshouxinxi_雨衣1.jpg,upload/jieshouxinxi_雨衣2.jpg,upload/jieshouxinxi_雨衣3.jpg','2026-04-27 08:55:30'),(8,'2026-04-27 00:55:30','申领编号8','消毒液','防疫消杀类','22','益启公益发展基金会',345,'','upload/jieshouxinxi_消毒液1.jpg,upload/jieshouxinxi_消毒液2.jpg,upload/jieshouxinxi_消毒液3.jpg','2026-04-27 08:55:30'),(9,'2026-04-27 01:06:42','1777251915647','压缩饼干','食品饮水类','22','益启公益发展基金会',100,'upload/1777251967403_479.doc','upload/1777252001478_730.jpg','2026-04-27 09:06:35');
/*!40000 ALTER TABLE `jieshouxinxi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `juanzengren`
--

DROP TABLE IF EXISTS `juanzengren`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `juanzengren` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `zhanghao` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
  `xingming` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
  `mima` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `xingbie` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
  `lianxifangshi` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系方式',
  `touxiang` longtext COLLATE utf8mb4_unicode_ci COMMENT '头像',
  `status` int(11) DEFAULT '0' COMMENT '状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `zhanghao` (`zhanghao`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='捐赠人';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `juanzengren`
--

LOCK TABLES `juanzengren` WRITE;
/*!40000 ALTER TABLE `juanzengren` DISABLE KEYS */;
INSERT INTO `juanzengren` VALUES (11,'2026-04-27 00:55:29','005','李强','123456','男','13555556665','upload/juanzengren_touxiang1.jpg',0),(12,'2026-04-27 00:55:29','002','刘芳','123456','女','13922223332','upload/juanzengren_touxiang2.jpg',0),(13,'2026-04-27 00:55:29','016','梁艳','123456','女','15477788886','upload/juanzengren_touxiang3.jpg',0),(14,'2026-04-27 00:55:29','011','朱峰','123456','男','15922233331','upload/juanzengren_touxiang4.jpg',0),(15,'2026-04-27 00:55:29','006','赵静','123456','女','13466667776','upload/juanzengren_touxiang5.jpg',0),(16,'2026-04-27 00:55:29','008','吴婷','123456','女','13288889998','upload/juanzengren_touxiang6.jpg',0),(17,'2026-04-27 00:55:29','019','彭宇','123456','男','15100011119','upload/juanzengren_touxiang7.jpg',0),(18,'2026-04-27 00:55:29','004','王丽','123456','女','13644445554','upload/juanzengren_touxiang8.jpg',0);
/*!40000 ALTER TABLE `juanzengren` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `juanzengwuzi`
--

DROP TABLE IF EXISTS `juanzengwuzi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `juanzengwuzi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `juanzengbianhao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '捐赠编号',
  `wuzimingcheng` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资名称',
  `wuzizhonglei` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资种类',
  `wuzishuoming` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资说明',
  `wuzitupian` longtext COLLATE utf8mb4_unicode_ci COMMENT '物资图片',
  `xinjiuchengdu` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '新旧程度',
  `wuzishuliang` int(11) DEFAULT NULL COMMENT '物资数量',
  `wuzizhongliang` double DEFAULT NULL COMMENT '物资重量',
  `youxiaoqi` date DEFAULT NULL COMMENT '有效期',
  `juanzengshijian` datetime DEFAULT NULL COMMENT '捐赠时间',
  `yanshouzhuangtai` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '验收状态',
  `zhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账号',
  `xingming` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  `sfsh` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '待审核' COMMENT '是否审核',
  `shhf` longtext COLLATE utf8mb4_unicode_ci COMMENT '审核回复',
  PRIMARY KEY (`id`),
  UNIQUE KEY `juanzengbianhao` (`juanzengbianhao`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='捐赠物资';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `juanzengwuzi`
--

LOCK TABLES `juanzengwuzi` WRITE;
/*!40000 ALTER TABLE `juanzengwuzi` DISABLE KEYS */;
INSERT INTO `juanzengwuzi` VALUES (1,'2026-04-27 00:55:29','1111111111','压缩饼干','食品饮水类','富含蛋白质保质期36个月','upload/juanzengwuzi_压缩饼干1.jpg,upload/juanzengwuzi_压缩饼干2.jpg,upload/juanzengwuzi_压缩饼干3.jpg','新旧程度1',292,1,'2026-04-27','2026-04-27 08:55:29','已验收','005','李强','是',''),(2,'2026-04-27 00:55:29','2222222222','铁锹','抢险工具类','木柄防滑设计铲头坚固耐用','upload/juanzengwuzi_铁锹1.jpg,upload/juanzengwuzi_铁锹2.jpg,upload/juanzengwuzi_铁锹3.jpg','新旧程度2',437,2,'2026-04-27','2026-04-27 08:55:29','已验收','002','刘芳','是',''),(3,'2026-04-27 00:55:29','3333333333','方便面','食品饮水类','红烧牛肉味非油炸面饼','upload/juanzengwuzi_方便面1.jpg,upload/juanzengwuzi_方便面2.jpg,upload/juanzengwuzi_方便面3.jpg','新旧程度3',412,3,'2026-04-27','2026-04-27 08:55:29','已验收','016','梁艳','是',''),(4,'2026-04-27 00:55:29','4444444444','体温计','医疗救护类','快速测温精准度±01℃','upload/juanzengwuzi_体温计1.jpg,upload/juanzengwuzi_体温计2.jpg,upload/juanzengwuzi_体温计3.jpg','新旧程度4',235,4,'2026-04-27','2026-04-27 08:55:29','已验收','011','朱峰','是',''),(5,'2026-04-27 00:55:29','5555555555','绳索','抢险工具类','承重500kg耐磨抗老化','upload/juanzengwuzi_绳索1.jpg,upload/juanzengwuzi_绳索2.jpg,upload/juanzengwuzi_绳索3.jpg','新旧程度5',459,5,'2026-04-27','2026-04-27 08:55:29','已验收','006','赵静','是',''),(6,'2026-04-27 00:55:29','6666666666','棉被','救灾安置类','纯棉填充加厚保暖','upload/juanzengwuzi_棉被1.jpg,upload/juanzengwuzi_棉被2.jpg,upload/juanzengwuzi_棉被3.jpg','新旧程度6',267,6,'2026-04-27','2026-04-27 08:55:29','已验收','008','吴婷','是',''),(7,'2026-04-27 00:55:29','7777777777','雨衣','防护装备类','防水牛津布耐磨抗撕裂','upload/juanzengwuzi_雨衣1.jpg,upload/juanzengwuzi_雨衣2.jpg,upload/juanzengwuzi_雨衣3.jpg','新旧程度7',307,7,'2026-04-27','2026-04-27 08:55:29','已验收','019','彭宇','是',''),(8,'2026-04-27 00:55:29','8888888888','消毒液','防疫消杀类','含氯消毒液有效杀菌率999%','upload/juanzengwuzi_消毒液1.jpg,upload/juanzengwuzi_消毒液2.jpg,upload/juanzengwuzi_消毒液3.jpg','新旧程度8',345,8,'2026-04-27','2026-04-27 08:55:29','已验收','004','王丽','是',''),(9,'2026-04-27 01:04:23','1777251840121','压缩饼干','食品饮水类','测试','upload/1777251850781_969.jpg','全新',300,200,'2026-05-02','2026-04-27 09:04:00','已验收','004','王丽','是','测试');
/*!40000 ALTER TABLE `juanzengwuzi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `juanzengzhengshu`
--

DROP TABLE IF EXISTS `juanzengzhengshu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `juanzengzhengshu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `juanzengbianhao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '捐赠编号',
  `wuzimingcheng` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资名称',
  `wuzizhonglei` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资种类',
  `wuzishuoming` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资说明',
  `wuzitupian` longtext COLLATE utf8mb4_unicode_ci COMMENT '物资图片',
  `xinjiuchengdu` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '新旧程度',
  `wuzishuliang` int(11) DEFAULT NULL COMMENT '物资数量',
  `wuzizhongliang` double DEFAULT NULL COMMENT '物资重量',
  `youxiaoqi` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '有效期',
  `juanzengzhengshu` longtext COLLATE utf8mb4_unicode_ci COMMENT '捐赠证书',
  `banfashijian` datetime DEFAULT NULL COMMENT '颁发时间',
  `zhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账号',
  `xingming` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  `crossuserid` bigint(20) DEFAULT NULL COMMENT '跨表用户id',
  `crossrefid` bigint(20) DEFAULT NULL COMMENT '跨表主键id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='捐赠证书';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `juanzengzhengshu`
--

LOCK TABLES `juanzengzhengshu` WRITE;
/*!40000 ALTER TABLE `juanzengzhengshu` DISABLE KEYS */;
INSERT INTO `juanzengzhengshu` VALUES (1,'2026-04-27 00:55:30','捐赠编号1','压缩饼干','食品饮水类','富含蛋白质保质期36个月','upload/juanzengzhengshu_压缩饼干1.jpg,upload/juanzengzhengshu_压缩饼干2.jpg,upload/juanzengzhengshu_压缩饼干3.jpg','新旧程度1',292,1,'有效期1','','2026-04-27 08:55:30','005','李强',1,1),(2,'2026-04-27 00:55:30','捐赠编号2','铁锹','抢险工具类','木柄防滑设计铲头坚固耐用','upload/juanzengzhengshu_铁锹1.jpg,upload/juanzengzhengshu_铁锹2.jpg,upload/juanzengzhengshu_铁锹3.jpg','新旧程度2',437,2,'有效期2','','2026-04-27 08:55:30','002','刘芳',2,2),(3,'2026-04-27 00:55:30','捐赠编号3','方便面','食品饮水类','红烧牛肉味非油炸面饼','upload/juanzengzhengshu_方便面1.jpg,upload/juanzengzhengshu_方便面2.jpg,upload/juanzengzhengshu_方便面3.jpg','新旧程度3',412,3,'有效期3','','2026-04-27 08:55:30','016','梁艳',3,3),(4,'2026-04-27 00:55:30','捐赠编号4','体温计','医疗救护类','快速测温精准度±01℃','upload/juanzengzhengshu_体温计1.jpg,upload/juanzengzhengshu_体温计2.jpg,upload/juanzengzhengshu_体温计3.jpg','新旧程度4',235,4,'有效期4','','2026-04-27 08:55:30','011','朱峰',4,4),(5,'2026-04-27 00:55:30','捐赠编号5','绳索','抢险工具类','承重500kg耐磨抗老化','upload/juanzengzhengshu_绳索1.jpg,upload/juanzengzhengshu_绳索2.jpg,upload/juanzengzhengshu_绳索3.jpg','新旧程度5',459,5,'有效期5','','2026-04-27 08:55:30','006','赵静',5,5),(6,'2026-04-27 00:55:30','捐赠编号6','棉被','救灾安置类','纯棉填充加厚保暖','upload/juanzengzhengshu_棉被1.jpg,upload/juanzengzhengshu_棉被2.jpg,upload/juanzengzhengshu_棉被3.jpg','新旧程度6',267,6,'有效期6','','2026-04-27 08:55:30','008','吴婷',6,6),(7,'2026-04-27 00:55:30','捐赠编号7','雨衣','防护装备类','防水牛津布耐磨抗撕裂','upload/juanzengzhengshu_雨衣1.jpg,upload/juanzengzhengshu_雨衣2.jpg,upload/juanzengzhengshu_雨衣3.jpg','新旧程度7',307,7,'有效期7','','2026-04-27 08:55:30','019','彭宇',7,7),(8,'2026-04-27 00:55:30','捐赠编号8','消毒液','防疫消杀类','含氯消毒液有效杀菌率999%','upload/juanzengzhengshu_消毒液1.jpg,upload/juanzengzhengshu_消毒液2.jpg,upload/juanzengzhengshu_消毒液3.jpg','新旧程度8',345,8,'有效期8','','2026-04-27 08:55:30','004','王丽',8,8);
/*!40000 ALTER TABLE `juanzengzhengshu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `news` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `title` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标题',
  `introduction` longtext COLLATE utf8mb4_unicode_ci COMMENT '简介',
  `picture` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片',
  `content` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '内容',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发布人',
  `headportrait` longtext COLLATE utf8mb4_unicode_ci COMMENT '头像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='公告信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` VALUES (1,'2026-04-27 00:55:30','乡村童阅书香行动','线上线下结合','upload/news_picture1.jpg','捐赠图书物资培训志愿管理员开展阅读指导','发布人1','upload/news_headportrait1.jpg'),(2,'2026-04-27 00:55:30','荒漠绿植认养计划','线上线下结合','upload/news_picture2.jpg','线上支付认养定期反馈生长实地探访活动','发布人2','upload/news_headportrait2.jpg'),(3,'2026-04-27 00:55:30','乡村体育设施援建计划','线上线下结合','upload/news_picture3.jpg','设施采购安装体育器材捐赠健身指导培训','发布人3','upload/news_headportrait3.jpg'),(4,'2026-04-27 00:55:30','乡村教师赋能培训','线上','upload/news_picture4.jpg','学科教学技巧多媒体应用培训教研经验分享','发布人4','upload/news_headportrait4.jpg'),(5,'2026-04-27 00:55:30','困境家庭暖心包捐赠','线下','upload/news_picture5.jpg','物资募集整理按需求分装上门送达物资','发布人5','upload/news_headportrait5.jpg'),(6,'2026-04-27 00:55:30','旧衣新生公益项目','线上线下结合','upload/news_picture6.jpg','旧衣分类回收消毒翻新捐赠面料再生处理','发布人6','upload/news_headportrait6.jpg'),(7,'2026-04-27 00:55:30','社区公益维修服务日','线下','upload/news_picture7.jpg','家电家具维修小物件修补维修技巧讲解','发布人7','upload/news_headportrait7.jpg'),(8,'2026-04-27 00:55:30','星光助学一对一计划','线上线下结合','upload/news_picture8.jpg','走访核实情况对接资助资源跟踪学业进展','发布人8','upload/news_headportrait8.jpg');
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shiyongfankui`
--

DROP TABLE IF EXISTS `shiyongfankui`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shiyongfankui` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `shiyongrenshu` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '使用人数',
  `jutiyongtu` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '具体用途',
  `shiyongxiaoguo` longtext COLLATE utf8mb4_unicode_ci COMMENT '使用效果',
  `changjingzhaopian` longtext COLLATE utf8mb4_unicode_ci COMMENT '场景照片',
  `fankuishijian` datetime DEFAULT NULL COMMENT '反馈时间',
  `shenlingbianhao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '申领编号',
  `wuzimingcheng` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资名称',
  `wuzizhonglei` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资种类',
  `jigouzhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构账号',
  `jigoumingcheng` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构名称',
  `wuzishuliang` int(11) DEFAULT NULL COMMENT '出库数量',
  `discussnum` int(11) DEFAULT '0' COMMENT '评论数',
  `storeupnum` int(11) DEFAULT '0' COMMENT '收藏数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='使用反馈';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shiyongfankui`
--

LOCK TABLES `shiyongfankui` WRITE;
/*!40000 ALTER TABLE `shiyongfankui` DISABLE KEYS */;
INSERT INTO `shiyongfankui` VALUES (1,'2026-04-27 00:55:30','使用人数1','具体用途1','使用效果1','upload/shiyongfankui_压缩饼干1.jpg,upload/shiyongfankui_压缩饼干2.jpg,upload/shiyongfankui_压缩饼干3.jpg','2026-04-27 08:55:30','申领编号1','压缩饼干','食品饮水类','24','青禾公益成长计划',292,0,1),(2,'2026-04-27 00:55:30','使用人数2','具体用途2','使用效果2','upload/shiyongfankui_铁锹1.jpg,upload/shiyongfankui_铁锹2.jpg,upload/shiyongfankui_铁锹3.jpg','2026-04-27 08:55:30','申领编号2','铁锹','抢险工具类','25','福泽公益服务中心',437,0,2),(3,'2026-04-27 00:55:30','使用人数3','具体用途3','使用效果3','upload/shiyongfankui_方便面1.jpg,upload/shiyongfankui_方便面2.jpg,upload/shiyongfankui_方便面3.jpg','2026-04-27 08:55:30','申领编号3','方便面','食品饮水类','27','仁善公益行动联盟',412,0,3),(4,'2026-04-27 00:55:30','使用人数4','具体用途4','使用效果4','upload/shiyongfankui_体温计1.jpg,upload/shiyongfankui_体温计2.jpg,upload/shiyongfankui_体温计3.jpg','2026-04-27 08:55:30','申领编号4','体温计','医疗救护类','26','筑梦公益服务站',235,0,4),(5,'2026-04-27 00:55:30','使用人数5','具体用途5','使用效果5','upload/shiyongfankui_绳索1.jpg,upload/shiyongfankui_绳索2.jpg,upload/shiyongfankui_绳索3.jpg','2026-04-27 08:55:30','申领编号5','绳索','抢险工具类','21','春苗公益成长计划',459,0,5),(6,'2026-04-27 00:55:30','使用人数6','具体用途6','使用效果6','upload/shiyongfankui_棉被1.jpg,upload/shiyongfankui_棉被2.jpg,upload/shiyongfankui_棉被3.jpg','2026-04-27 08:55:30','申领编号6','棉被','救灾安置类','28','德善公益行动联盟',267,0,6),(7,'2026-04-27 00:55:30','使用人数7','具体用途7','使用效果7','upload/shiyongfankui_雨衣1.jpg,upload/shiyongfankui_雨衣2.jpg,upload/shiyongfankui_雨衣3.jpg','2026-04-27 08:55:30','申领编号7','雨衣','防护装备类','23','乐助公益服务中心',307,0,7),(8,'2026-04-27 00:55:30','使用人数8','具体用途8','使用效果8','upload/shiyongfankui_消毒液1.jpg,upload/shiyongfankui_消毒液2.jpg,upload/shiyongfankui_消毒液3.jpg','2026-04-27 08:55:30','申领编号8','消毒液','防疫消杀类','22','益启公益发展基金会',345,0,8),(9,'2026-04-27 01:07:09','20','测试测试','<p>测试测试</p>','upload/1777252027004_919.jpg','2026-04-27 09:06:57','1777251915647','压缩饼干','食品饮水类','22','益启公益发展基金会',100,0,0);
/*!40000 ALTER TABLE `shiyongfankui` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storeup`
--

DROP TABLE IF EXISTS `storeup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `storeup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `refid` bigint(20) DEFAULT NULL COMMENT '外键id',
  `tablename` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '表名',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `picture` longtext COLLATE utf8mb4_unicode_ci COMMENT '图片',
  `type` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '类型',
  `inteltype` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '推荐类型',
  `remark` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收藏表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storeup`
--

LOCK TABLES `storeup` WRITE;
/*!40000 ALTER TABLE `storeup` DISABLE KEYS */;
/*!40000 ALTER TABLE `storeup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syslog`
--

DROP TABLE IF EXISTS `syslog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `syslog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `username` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `operation` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户操作',
  `method` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求方法',
  `params` longtext COLLATE utf8mb4_unicode_ci COMMENT '请求参数',
  `time` bigint(20) DEFAULT NULL COMMENT '请求时长(毫秒)',
  `ip` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'IP地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syslog`
--

LOCK TABLES `syslog` WRITE;
/*!40000 ALTER TABLE `syslog` DISABLE KEYS */;
/*!40000 ALTER TABLE `syslog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userid` bigint(20) NOT NULL COMMENT '用户id',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `tablename` varchar(100) DEFAULT NULL COMMENT '表名',
  `role` varchar(100) DEFAULT NULL COMMENT '角色',
  `token` varchar(500) NOT NULL COMMENT '密码',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '新增时间',
  `expiratedtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '过期时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='token表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
INSERT INTO `token` VALUES (1,1,'admin','users','管理员','0g1k1pntsmjgfthxgatpqmo87vm9t1rd','2026-04-27 01:03:25','2026-04-27 02:05:38'),(2,18,'004','juanzengren','捐赠人','5gcqfz6mpn0wvzxr1dvsc1ftkfezpttj','2026-04-27 01:03:36','2026-04-27 02:03:36'),(3,28,'22','jieshoujigou','接收机构','gbewrzdqgj3nr5ss5ctuq1gscy4fpjv4','2026-04-27 01:05:13','2026-04-27 02:06:33');
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `username` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `role` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '管理员' COMMENT '角色',
  `image` longtext COLLATE utf8mb4_unicode_ci COMMENT '头像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2026-04-27 00:55:30','admin','admin','管理员','upload/image1.jpg');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wuzishenling`
--

DROP TABLE IF EXISTS `wuzishenling`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wuzishenling` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `juanzengbianhao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '捐赠编号',
  `shenlingbianhao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '申领编号',
  `wuzimingcheng` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资名称',
  `wuzizhonglei` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资种类',
  `wuziguige` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资规格',
  `wuzitupian` longtext COLLATE utf8mb4_unicode_ci COMMENT '物资图片',
  `shenlingshuliang` int(11) DEFAULT NULL COMMENT '申领数量',
  `wuzishenlingbiao` longtext COLLATE utf8mb4_unicode_ci COMMENT '物资申领表',
  `shenlingshijian` datetime DEFAULT NULL COMMENT '申领时间',
  `chukuzhuangtai` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '出库状态',
  `jigouzhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构账号',
  `jigoumingcheng` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构名称',
  `quyu` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '区域',
  `sfsh` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '待审核' COMMENT '是否审核',
  `shhf` longtext COLLATE utf8mb4_unicode_ci COMMENT '审核回复',
  PRIMARY KEY (`id`),
  UNIQUE KEY `shenlingbianhao` (`shenlingbianhao`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物资申领';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wuzishenling`
--

LOCK TABLES `wuzishenling` WRITE;
/*!40000 ALTER TABLE `wuzishenling` DISABLE KEYS */;
INSERT INTO `wuzishenling` VALUES (1,'2026-04-27 00:55:30',NULL,'1111111111','压缩饼干','食品饮水类','120g块','upload/wuzishenling_压缩饼干1.jpg,upload/wuzishenling_压缩饼干2.jpg,upload/wuzishenling_压缩饼干3.jpg',1,'','2026-04-27 08:55:30','已出库','24','青禾公益成长计划','昆明','是',''),(2,'2026-04-27 00:55:30',NULL,'2222222222','铁锹','抢险工具类','锰钢材质','upload/wuzishenling_铁锹1.jpg,upload/wuzishenling_铁锹2.jpg,upload/wuzishenling_铁锹3.jpg',2,'','2026-04-27 08:55:30','已出库','25','福泽公益服务中心','银川','是',''),(3,'2026-04-27 00:55:30',NULL,'3333333333','方便面','食品饮水类','140g袋','upload/wuzishenling_方便面1.jpg,upload/wuzishenling_方便面2.jpg,upload/wuzishenling_方便面3.jpg',3,'','2026-04-27 08:55:30','已出库','27','仁善公益行动联盟','长沙','是',''),(4,'2026-04-27 00:55:30',NULL,'4444444444','体温计','医疗救护类','电子体温计','upload/wuzishenling_体温计1.jpg,upload/wuzishenling_体温计2.jpg,upload/wuzishenling_体温计3.jpg',4,'','2026-04-27 08:55:30','已出库','26','筑梦公益服务站','南昌','是',''),(5,'2026-04-27 00:55:30',NULL,'5555555555','绳索','抢险工具类','尼龙绳直径10mm','upload/wuzishenling_绳索1.jpg,upload/wuzishenling_绳索2.jpg,upload/wuzishenling_绳索3.jpg',5,'','2026-04-27 08:55:30','已出库','21','春苗公益成长计划','深圳','是',''),(6,'2026-04-27 00:55:30',NULL,'6666666666','棉被','救灾安置类','200cm×150cm','upload/wuzishenling_棉被1.jpg,upload/wuzishenling_棉被2.jpg,upload/wuzishenling_棉被3.jpg',6,'','2026-04-27 08:55:30','已出库','28','德善公益行动联盟','重庆','是',''),(7,'2026-04-27 00:55:30',NULL,'7777777777','雨衣','防护装备类','分体式','upload/wuzishenling_雨衣1.jpg,upload/wuzishenling_雨衣2.jpg,upload/wuzishenling_雨衣3.jpg',7,'','2026-04-27 08:55:30','已出库','23','乐助公益服务中心','乌鲁木齐','是',''),(8,'2026-04-27 00:55:30',NULL,'8888888888','消毒液','防疫消杀类','500ml瓶','upload/wuzishenling_消毒液1.jpg,upload/wuzishenling_消毒液2.jpg,upload/wuzishenling_消毒液3.jpg',8,'','2026-04-27 08:55:30','已出库','22','益启公益发展基金会','杭州','是',''),(9,'2026-04-27 01:05:27',NULL,'1777251915647','压缩饼干','食品饮水类','120g块','upload/wuzixinxi_压缩饼干1.jpg,upload/wuzixinxi_压缩饼干2.jpg,upload/wuzixinxi_压缩饼干3.jpg',100,'upload/1777251926311_142.doc','2026-04-27 09:05:15','已出库','22','益启公益发展基金会','杭州','是','测试');
/*!40000 ALTER TABLE `wuzishenling` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wuzixinxi`
--

DROP TABLE IF EXISTS `wuzixinxi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wuzixinxi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `juanzengbianhao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '捐赠编号',
  `wuzimingcheng` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资名称',
  `wuzizhonglei` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资种类',
  `wuziguige` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资规格',
  `wuzixiangqing` longtext COLLATE utf8mb4_unicode_ci COMMENT '物资详情',
  `wuzitupian` longtext COLLATE utf8mb4_unicode_ci COMMENT '物资图片',
  `wuzishuliang` int(11) DEFAULT NULL COMMENT '物资数量',
  `baozhiqi` date DEFAULT NULL COMMENT '保质期',
  `cunchuweizhi` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '存储位置',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物资信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wuzixinxi`
--

LOCK TABLES `wuzixinxi` WRITE;
/*!40000 ALTER TABLE `wuzixinxi` DISABLE KEYS */;
INSERT INTO `wuzixinxi` VALUES (1,'2026-04-27 00:55:29','1111111111','压缩饼干','食品饮水类','120g块','富含蛋白质保质期36个月','upload/wuzixinxi_压缩饼干1.jpg,upload/wuzixinxi_压缩饼干2.jpg,upload/wuzixinxi_压缩饼干3.jpg',292,'2026-04-27','存储位置1'),(2,'2026-04-27 00:55:29','2222222222','铁锹','抢险工具类','锰钢材质','木柄防滑设计铲头坚固耐用','upload/wuzixinxi_铁锹1.jpg,upload/wuzixinxi_铁锹2.jpg,upload/wuzixinxi_铁锹3.jpg',437,'2026-04-27','存储位置2'),(3,'2026-04-27 00:55:29','3333333333','方便面','食品饮水类','140g袋','红烧牛肉味非油炸面饼','upload/wuzixinxi_方便面1.jpg,upload/wuzixinxi_方便面2.jpg,upload/wuzixinxi_方便面3.jpg',412,'2026-04-27','存储位置3'),(4,'2026-04-27 00:55:29','4444444444','体温计','医疗救护类','电子体温计','快速测温精准度±01℃','upload/wuzixinxi_体温计1.jpg,upload/wuzixinxi_体温计2.jpg,upload/wuzixinxi_体温计3.jpg',235,'2026-04-27','存储位置4'),(5,'2026-04-27 00:55:29','5555555555','绳索','抢险工具类','尼龙绳直径10mm','承重500kg耐磨抗老化','upload/wuzixinxi_绳索1.jpg,upload/wuzixinxi_绳索2.jpg,upload/wuzixinxi_绳索3.jpg',459,'2026-04-27','存储位置5'),(6,'2026-04-27 00:55:29','6666666666','棉被','救灾安置类','200cm×150cm','纯棉填充加厚保暖','upload/wuzixinxi_棉被1.jpg,upload/wuzixinxi_棉被2.jpg,upload/wuzixinxi_棉被3.jpg',267,'2026-04-27','存储位置6'),(7,'2026-04-27 00:55:29','7777777777','雨衣','防护装备类','分体式','防水牛津布耐磨抗撕裂','upload/wuzixinxi_雨衣1.jpg,upload/wuzixinxi_雨衣2.jpg,upload/wuzixinxi_雨衣3.jpg',307,'2026-04-27','存储位置7'),(8,'2026-04-27 00:55:29','8888888888','消毒液','防疫消杀类','500ml瓶','含氯消毒液有效杀菌率999%','upload/wuzixinxi_消毒液1.jpg,upload/wuzixinxi_消毒液2.jpg,upload/wuzixinxi_消毒液3.jpg',345,'2026-04-27','存储位置8'),(9,'2026-04-27 01:04:51','1777251840121','压缩饼干','食品饮水类','全新','测试','upload/1777251850781_969.jpg',300,'2026-05-02','');
/*!40000 ALTER TABLE `wuzixinxi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wuzizhonglei`
--

DROP TABLE IF EXISTS `wuzizhonglei`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wuzizhonglei` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `wuzizhonglei` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '物资种类',
  PRIMARY KEY (`id`),
  UNIQUE KEY `wuzizhonglei` (`wuzizhonglei`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='物资种类';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wuzizhonglei`
--

LOCK TABLES `wuzizhonglei` WRITE;
/*!40000 ALTER TABLE `wuzizhonglei` DISABLE KEYS */;
INSERT INTO `wuzizhonglei` VALUES (1,'2026-04-27 00:55:29','食品饮水类'),(2,'2026-04-27 00:55:29','抢险工具类'),(3,'2026-04-27 00:55:29','医疗救护类'),(4,'2026-04-27 00:55:29','救灾安置类'),(5,'2026-04-27 00:55:29','防护装备类'),(6,'2026-04-27 00:55:29','防疫消杀类'),(7,'2026-04-27 00:55:29','物资种类7'),(8,'2026-04-27 00:55:29','物资种类8');
/*!40000 ALTER TABLE `wuzizhonglei` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `yanshoujilu`
--

DROP TABLE IF EXISTS `yanshoujilu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yanshoujilu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `juanzengbianhao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '捐赠编号',
  `wuzimingcheng` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资名称',
  `wuzizhonglei` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资种类',
  `wuzishuoming` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资说明',
  `wuzitupian` longtext COLLATE utf8mb4_unicode_ci COMMENT '物资图片',
  `xinjiuchengdu` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '新旧程度',
  `wuzishuliang` int(11) DEFAULT NULL COMMENT '物资数量',
  `wuzizhongliang` double DEFAULT NULL COMMENT '物资重量',
  `youxiaoqi` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '有效期',
  `yanshoujieguo` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '验收结果',
  `yanshoujilu` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '验收记录',
  `yanshouren` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '验收人',
  `yanshoushijian` datetime DEFAULT NULL COMMENT '验收时间',
  `zhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账号',
  `xingming` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='验收记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `yanshoujilu`
--

LOCK TABLES `yanshoujilu` WRITE;
/*!40000 ALTER TABLE `yanshoujilu` DISABLE KEYS */;
INSERT INTO `yanshoujilu` VALUES (1,'2026-04-27 00:55:30','捐赠编号1','压缩饼干','食品饮水类','富含蛋白质保质期36个月','upload/yanshoujilu_压缩饼干1.jpg,upload/yanshoujilu_压缩饼干2.jpg,upload/yanshoujilu_压缩饼干3.jpg','新旧程度1',292,1,'有效期1','合格','验收记录1','验收人1','2026-04-27 08:55:30','005','李强'),(2,'2026-04-27 00:55:30','捐赠编号2','铁锹','抢险工具类','木柄防滑设计铲头坚固耐用','upload/yanshoujilu_铁锹1.jpg,upload/yanshoujilu_铁锹2.jpg,upload/yanshoujilu_铁锹3.jpg','新旧程度2',437,2,'有效期2','合格','验收记录2','验收人2','2026-04-27 08:55:30','002','刘芳'),(3,'2026-04-27 00:55:30','捐赠编号3','方便面','食品饮水类','红烧牛肉味非油炸面饼','upload/yanshoujilu_方便面1.jpg,upload/yanshoujilu_方便面2.jpg,upload/yanshoujilu_方便面3.jpg','新旧程度3',412,3,'有效期3','合格','验收记录3','验收人3','2026-04-27 08:55:30','016','梁艳'),(4,'2026-04-27 00:55:30','捐赠编号4','体温计','医疗救护类','快速测温精准度±01℃','upload/yanshoujilu_体温计1.jpg,upload/yanshoujilu_体温计2.jpg,upload/yanshoujilu_体温计3.jpg','新旧程度4',235,4,'有效期4','合格','验收记录4','验收人4','2026-04-27 08:55:30','011','朱峰'),(5,'2026-04-27 00:55:30','捐赠编号5','绳索','抢险工具类','承重500kg耐磨抗老化','upload/yanshoujilu_绳索1.jpg,upload/yanshoujilu_绳索2.jpg,upload/yanshoujilu_绳索3.jpg','新旧程度5',459,5,'有效期5','合格','验收记录5','验收人5','2026-04-27 08:55:30','006','赵静'),(6,'2026-04-27 00:55:30','捐赠编号6','棉被','救灾安置类','纯棉填充加厚保暖','upload/yanshoujilu_棉被1.jpg,upload/yanshoujilu_棉被2.jpg,upload/yanshoujilu_棉被3.jpg','新旧程度6',267,6,'有效期6','合格','验收记录6','验收人6','2026-04-27 08:55:30','008','吴婷'),(7,'2026-04-27 00:55:30','捐赠编号7','雨衣','防护装备类','防水牛津布耐磨抗撕裂','upload/yanshoujilu_雨衣1.jpg,upload/yanshoujilu_雨衣2.jpg,upload/yanshoujilu_雨衣3.jpg','新旧程度7',307,7,'有效期7','合格','验收记录7','验收人7','2026-04-27 08:55:30','019','彭宇'),(8,'2026-04-27 00:55:30','捐赠编号8','消毒液','防疫消杀类','含氯消毒液有效杀菌率999%','upload/yanshoujilu_消毒液1.jpg,upload/yanshoujilu_消毒液2.jpg,upload/yanshoujilu_消毒液3.jpg','新旧程度8',345,8,'有效期8','合格','验收记录8','验收人8','2026-04-27 08:55:30','004','王丽'),(9,'2026-04-27 01:04:51','1777251840121','压缩饼干','食品饮水类','测试','upload/1777251850781_969.jpg','全新',300,200,'2026-05-02','合格','测试','测试','2026-04-27 09:04:44','004','王丽');
/*!40000 ALTER TABLE `yanshoujilu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `yiyifankui`
--

DROP TABLE IF EXISTS `yiyifankui`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yiyifankui` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `yiyifankui` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '异议反馈',
  `zuozhengtupian` longtext COLLATE utf8mb4_unicode_ci COMMENT '佐证图片',
  `zuozhengcailiao` longtext COLLATE utf8mb4_unicode_ci COMMENT '佐证材料',
  `tijiaoshijian` datetime DEFAULT NULL COMMENT '提交时间',
  `shenlingbianhao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '申领编号',
  `wuzimingcheng` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资名称',
  `wuzizhonglei` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '物资种类',
  `jigouzhanghao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构账号',
  `jigoumingcheng` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构名称',
  `wuzishuliang` int(11) DEFAULT NULL COMMENT '出库数量',
  `chukudan` longtext COLLATE utf8mb4_unicode_ci COMMENT '出库单',
  `sfsh` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '待审核' COMMENT '是否审核',
  `shhf` longtext COLLATE utf8mb4_unicode_ci COMMENT '审核回复',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='异议反馈';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `yiyifankui`
--

LOCK TABLES `yiyifankui` WRITE;
/*!40000 ALTER TABLE `yiyifankui` DISABLE KEYS */;
INSERT INTO `yiyifankui` VALUES (1,'2026-04-27 00:55:30','异议反馈1','upload/yiyifankui_压缩饼干1.jpg,upload/yiyifankui_压缩饼干2.jpg,upload/yiyifankui_压缩饼干3.jpg','','2026-04-27 08:55:30','申领编号1','压缩饼干','食品饮水类','24','青禾公益成长计划',292,'','是',''),(2,'2026-04-27 00:55:30','异议反馈2','upload/yiyifankui_铁锹1.jpg,upload/yiyifankui_铁锹2.jpg,upload/yiyifankui_铁锹3.jpg','','2026-04-27 08:55:30','申领编号2','铁锹','抢险工具类','25','福泽公益服务中心',437,'','是',''),(3,'2026-04-27 00:55:30','异议反馈3','upload/yiyifankui_方便面1.jpg,upload/yiyifankui_方便面2.jpg,upload/yiyifankui_方便面3.jpg','','2026-04-27 08:55:30','申领编号3','方便面','食品饮水类','27','仁善公益行动联盟',412,'','是',''),(4,'2026-04-27 00:55:30','异议反馈4','upload/yiyifankui_体温计1.jpg,upload/yiyifankui_体温计2.jpg,upload/yiyifankui_体温计3.jpg','','2026-04-27 08:55:30','申领编号4','体温计','医疗救护类','26','筑梦公益服务站',235,'','是',''),(5,'2026-04-27 00:55:30','异议反馈5','upload/yiyifankui_绳索1.jpg,upload/yiyifankui_绳索2.jpg,upload/yiyifankui_绳索3.jpg','','2026-04-27 08:55:30','申领编号5','绳索','抢险工具类','21','春苗公益成长计划',459,'','是',''),(6,'2026-04-27 00:55:30','异议反馈6','upload/yiyifankui_棉被1.jpg,upload/yiyifankui_棉被2.jpg,upload/yiyifankui_棉被3.jpg','','2026-04-27 08:55:30','申领编号6','棉被','救灾安置类','28','德善公益行动联盟',267,'','是',''),(7,'2026-04-27 00:55:30','异议反馈7','upload/yiyifankui_雨衣1.jpg,upload/yiyifankui_雨衣2.jpg,upload/yiyifankui_雨衣3.jpg','','2026-04-27 08:55:30','申领编号7','雨衣','防护装备类','23','乐助公益服务中心',307,'','是',''),(8,'2026-04-27 00:55:30','异议反馈8','upload/yiyifankui_消毒液1.jpg,upload/yiyifankui_消毒液2.jpg,upload/yiyifankui_消毒液3.jpg','','2026-04-27 08:55:30','申领编号8','消毒液','防疫消杀类','22','益启公益发展基金会',345,'','是',''),(9,'2026-04-27 01:06:55','测试','upload/1777252010094_86.jpg','upload/1777252014511_53.doc','2026-04-27 09:06:42','1777251915647','压缩饼干','食品饮水类','22','益启公益发展基金会',100,'upload/1777251967403_479.doc','待审核','');
/*!40000 ALTER TABLE `yiyifankui` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-29 15:18:43
