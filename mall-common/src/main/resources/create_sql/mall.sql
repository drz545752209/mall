/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-05-09 11:41:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for biz
-- ----------------------------
DROP TABLE IF EXISTS `biz`;
CREATE TABLE `biz` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `pwd` varchar(32) NOT NULL,
  `biz_score` double(4,0) unsigned DEFAULT '0' COMMENT '商户评分',
  `contant_way` varchar(32) DEFAULT NULL COMMENT '联系方式',
  `location` varchar(32) DEFAULT NULL COMMENT '所在地',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `product_id` int(8) NOT NULL,
  `order_id` int(8) NOT NULL,
  `store_id` int(8) NOT NULL,
  `comment` varchar(128) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for logistics
-- ----------------------------
DROP TABLE IF EXISTS `logistics`;
CREATE TABLE `logistics` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `biz_id` int(8) DEFAULT NULL,
  `user_id` int(8) NOT NULL,
  `order_detail_id` int(8) NOT NULL,
  `status` varchar(8) NOT NULL COMMENT '物流详细信息',
  `score` int(2) DEFAULT NULL,
  `company_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for logistics_admin
-- ----------------------------
DROP TABLE IF EXISTS `logistics_admin`;
CREATE TABLE `logistics_admin` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `pwd` varchar(32) NOT NULL,
  `company_name` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `user_id` int(8) NOT NULL,
  `store_id` int(8) NOT NULL,
  `detail_id` int(8) NOT NULL,
  `operator` varchar(16) DEFAULT NULL,
  `operate_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `product_id` int(8) NOT NULL,
  `sum_consume` bigint(16) unsigned NOT NULL DEFAULT '0' COMMENT '消费金额',
  `status` varchar(8) NOT NULL,
  `count_consume` bigint(16) unsigned NOT NULL DEFAULT '0' COMMENT '购买数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `store_name` varchar(16) NOT NULL,
  `name` varchar(16) NOT NULL,
  `sales_volume` bigint(16) NOT NULL DEFAULT '0',
  `img` varchar(32) DEFAULT NULL,
  `type` varchar(16) NOT NULL,
  `price` int(8) NOT NULL,
  `description` varchar(32) DEFAULT NULL,
  `comment` varchar(64) DEFAULT NULL,
  `is_show` tinyint(1) NOT NULL DEFAULT '0',
  `is_del` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for promotion
-- ----------------------------
DROP TABLE IF EXISTS `promotion`;
CREATE TABLE `promotion` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `msg` varchar(32) DEFAULT NULL COMMENT '促销信息',
  `dicount` int(2) NOT NULL DEFAULT '10' COMMENT '折扣',
  `product_id` int(8) NOT NULL COMMENT '商品id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for stock
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `product_id` int(8) NOT NULL,
  `count` bigint(16) NOT NULL DEFAULT '0',
  `in_date` datetime DEFAULT NULL,
  `out_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for store
-- ----------------------------
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `biz_id` int(8) NOT NULL,
  `name` varchar(16) NOT NULL,
  `score` double(4,0) unsigned DEFAULT '0' COMMENT '店铺评分',
  `sales_volume` bigint(16) unsigned DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Unique_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `pwd` varchar(32) NOT NULL,
  `credit` int(16) unsigned DEFAULT '0' COMMENT '废弃字段当余额使用',
  `contant_way` varchar(32) DEFAULT NULL COMMENT '联系方式',
  `address` varchar(32) DEFAULT NULL COMMENT '收货地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Procedure structure for 插入100条数据
-- ----------------------------
DROP PROCEDURE IF EXISTS `插入100条数据`;
DELIMITER ;;
CREATE DEFINER=`skip-grants user`@`skip-grants host` PROCEDURE `插入100条数据`()
BEGIN
    #Routine body goes here...
  DECLARE var INT;
  DECLARE productName VARCHAR(16);
  DECLARE storeName VARCHAR(16);
  DECLARE saleVolume BIGINT(16);
  DECLARE img VARCHAR(32);
  DECLARE type VARCHAR(16);
  DECLARE price INT(8);
  DECLARE description VARCHAR(32);
  DECLARE comments VARCHAR(64);
  DECLARE isShow TINYINT(1);
  DECLARE isDel TINYINT(1);
  SET var=100000;
  WHILE var<1000000 DO
  SET productName=CONCAT('product',var);
  SET storeName='因特尔';
  SET saleVolume=0;
  SET img=CONCAT('E:\新建文件夹\pic\\',var,'.img');
  SET type='相机';
  SET price=100+var;
  SET description=CONCAT('好货',var);
  SET comments=CONCAT('垃圾',var);
  SET isShow=0;
  SET isDel=0;
  INSERT INTO product VALUES (DEFAULT,storeName,productName,saleVolume,img,type,price,description,comments,isShow,isDel);
  SET var=var+1;
  END WHILE;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for 插入订单详情
-- ----------------------------
DROP PROCEDURE IF EXISTS `插入订单详情`;
DELIMITER ;;
CREATE DEFINER=`skip-grants user`@`skip-grants host` PROCEDURE `插入订单详情`()
BEGIN
    #Routine body goes here...
  DECLARE var INT;
  DECLARE product_id INT(8);
  DECLARE sum_consume BIGINT(16);
  DECLARE status VARCHAR(8);
  DECLARE count_consume VARCHAR(16);
  SET var=0;
END
;;
DELIMITER ;
