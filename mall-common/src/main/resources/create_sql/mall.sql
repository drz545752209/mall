/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : mall

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2019-05-18 20:15:38
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
-- Records of biz
-- ----------------------------
INSERT INTO `biz` VALUES ('1', 'dengrongzhang', 'e10adc3949ba59abbe56e057f20f883e', '4', null, null);

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
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1', '3', '1', '1', '还好');

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of logistics
-- ----------------------------
INSERT INTO `logistics` VALUES ('1', '1', '1', '1', '待发货', '5', null);
INSERT INTO `logistics` VALUES ('2', '1', '1', '2', '已退货', null, '顺丰快递');
INSERT INTO `logistics` VALUES ('3', '1', '1', '3', '已揽件,等待发货', null, '韵达快递');
INSERT INTO `logistics` VALUES ('4', '1', '1', '4', '待发货', null, null);

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of logistics_admin
-- ----------------------------
INSERT INTO `logistics_admin` VALUES ('1', 'dengrongzhang', 'e10adc3949ba59abbe56e057f20f883e', '顺丰快递');
INSERT INTO `logistics_admin` VALUES ('2', 'dengrongzhang1', 'e10adc3949ba59abbe56e057f20f883e', '韵达快递');
INSERT INTO `logistics_admin` VALUES ('3', 'dengrongzhang2', 'e10adc3949ba59abbe56e057f20f883e', '天天快递');

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('1', '1', '1', '1', null, null);
INSERT INTO `order` VALUES ('2', '1', '1', '2', null, null);
INSERT INTO `order` VALUES ('3', '1', '1', '3', null, null);
INSERT INTO `order` VALUES ('4', '1', '1', '4', null, null);

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES ('1', '3', '20800', '待发货', '4');
INSERT INTO `order_detail` VALUES ('2', '3', '20800', '已退货', '4');
INSERT INTO `order_detail` VALUES ('3', '4', '1600', '已揽件,等待发货', '2');
INSERT INTO `order_detail` VALUES ('4', '4', '2400', '待发货', '3');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `store_name` varchar(16) NOT NULL,
  `name` varchar(16) NOT NULL,
  `sales_volume` bigint(16) NOT NULL DEFAULT '0',
  `img` varchar(128) DEFAULT NULL,
  `type` varchar(16) NOT NULL,
  `price` int(8) NOT NULL,
  `description` varchar(32) DEFAULT NULL,
  `comment` varchar(64) DEFAULT NULL,
  `is_show` tinyint(1) NOT NULL DEFAULT '0',
  `is_del` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', 'dell', '小米8', '0', '【千玺代言】华为新品  HUAWEI nova 3全面屏高清四摄游戏手机 海报级自拍 6GB+128GB 蓝楹紫全网通双卡双待.jpg', '智能手机', '4000', '智能手机', null, '1', '0');
INSERT INTO `product` VALUES ('2', 'dell', '华为荣耀8', '0', '【1年屏碎保+6期免息】Reno十倍变焦版855处理器手机.jpg', '智能手机', '5500', '智能手机', null, '1', '0');
INSERT INTO `product` VALUES ('3', 'dell', '小米6', '0', '【KPL官方比赛用机】vivo iQOO 44W超快闪充 8GB+128GB电光蓝 全面屏拍照手机 骁龙855电竞游戏 全网通4G手机.jpg', '智能手机', '5200', '智能手机', '还好', '1', '0');
INSERT INTO `product` VALUES ('4', 'dell', 'oppo手机find', '0', '【现货开抢！】OPPO Reno 10倍变焦版  高通骁龙855 4800万超清三摄 全网通拍照手机 极夜黑（6G+128G）.jpg', '智能手机', '800', '智能手机', null, '1', '0');
INSERT INTO `product` VALUES ('5', 'dell', '锤子A10', '0', 'Apple iPhone XR (A2108) 128GB 黑色 移动联通电信4G手机 双卡双待.jpg', '智能手机', '600', '智能手机', null, '1', '0');
INSERT INTO `product` VALUES ('6', 'dell', '锤子A11', '0', 'OPPO Reno 10倍变焦版 高通骁龙855 4800万超清三摄 8G+256G 雾海绿 全网通4G 全面屏拍照游戏智能手机.jpg', '智能手机', '988', '智能手机', null, '1', '0');
INSERT INTO `product` VALUES ('7', 'dell', 'oppo手机A11', '0', 'OPPO A7x 全面屏拍照手机 4GB+128GB 星空紫 全网通 移动联通电信4G 双卡双待手机.jpg', '智能手机', '888', '智能手机', null, '1', '0');
INSERT INTO `product` VALUES ('8', 'dell', '小米5', '0', '小米6X 全网通 6GB+64GB 赤焰红 移动联通电信4G手机 双卡双待 智能手机 拍照手机.jpg', '智能手机', '888', '智能手机', null, '1', '0');
INSERT INTO `product` VALUES ('9', 'dell', 'vivoA10', '0', 'vivo X27 8GB+256GB大内存 雀羽蓝 4800万AI三摄全面屏拍照手机 移动联通电信全网通4G手机.jpg', '智能手机', '889', '智能手机', null, '1', '0');
INSERT INTO `product` VALUES ('10', 'dell', 'vivoA12', '0', 'vivo 【套装】iQOO 水滴全面屏 AI三摄拍照 高通骁龙855 4G全网通 电竞游戏 智能手机 光电蓝【蓝牙耳机套装】 8GB 128GB.jpg', '智能手机', '4000', '智能手机', null, '1', '0');
INSERT INTO `product` VALUES ('11', 'dell', 'vivoA13', '0', 'vivo Z3 6GB+64GB 极光蓝 性能实力派 全面屏游戏手机 移动联通电信全网通4G手机.jpg', '智能手机', '888', '智能手机', null, '1', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of promotion
-- ----------------------------
INSERT INTO `promotion` VALUES ('1', null, '10', '1');
INSERT INTO `promotion` VALUES ('2', null, '10', '2');
INSERT INTO `promotion` VALUES ('3', null, '10', '3');
INSERT INTO `promotion` VALUES ('4', null, '10', '4');
INSERT INTO `promotion` VALUES ('5', null, '10', '5');
INSERT INTO `promotion` VALUES ('6', null, '10', '6');
INSERT INTO `promotion` VALUES ('7', null, '10', '7');
INSERT INTO `promotion` VALUES ('8', null, '10', '8');
INSERT INTO `promotion` VALUES ('9', null, '10', '9');
INSERT INTO `promotion` VALUES ('10', null, '10', '10');
INSERT INTO `promotion` VALUES ('11', null, '10', '11');

-- ----------------------------
-- Table structure for seckill_order
-- ----------------------------
DROP TABLE IF EXISTS `seckill_order`;
CREATE TABLE `seckill_order` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `user_id` int(8) DEFAULT NULL,
  `order_id` int(8) DEFAULT NULL,
  `goods_id` int(8) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_userid_goodsid` (`user_id`,`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seckill_order
-- ----------------------------
INSERT INTO `seckill_order` VALUES ('1', '1', '1', '1');

-- ----------------------------
-- Table structure for seckill_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `seckill_order_detail`;
CREATE TABLE `seckill_order_detail` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `user_id` int(8) DEFAULT NULL COMMENT '用户id',
  `goods_id` int(8) DEFAULT NULL COMMENT '商品id',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '冗余过来的商品名称',
  `goods_count` int(11) DEFAULT NULL COMMENT '商品数量',
  `goods_price` int(10) DEFAULT NULL COMMENT '商品价格',
  `status` int(2) DEFAULT NULL COMMENT '订单状态：0 未支付，1已支付，2 已发货，3 已收货，4 已退款，‘5 已完成',
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seckill_order_detail
-- ----------------------------
INSERT INTO `seckill_order_detail` VALUES ('1', '1', '1', '小米8', '1', '500', '1', '2019-05-18 09:07:37');

-- ----------------------------
-- Table structure for seckill_product
-- ----------------------------
DROP TABLE IF EXISTS `seckill_product`;
CREATE TABLE `seckill_product` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `goods_id` int(8) DEFAULT NULL COMMENT '商品id',
  `seckil_price` int(10) DEFAULT NULL COMMENT '秒杀价',
  `stock_count` int(11) DEFAULT NULL COMMENT '秒杀数量',
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of seckill_product
-- ----------------------------
INSERT INTO `seckill_product` VALUES ('1', '1', '500', '34', '2019-05-17 16:00:00', '2019-05-18 16:00:00');

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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stock
-- ----------------------------
INSERT INTO `stock` VALUES ('1', '1', '35', '2019-05-13 16:00:00', null);
INSERT INTO `stock` VALUES ('2', '2', '40', '2019-05-18 16:00:00', null);
INSERT INTO `stock` VALUES ('3', '3', '322', '2019-05-16 00:00:00', '2019-05-18 03:36:54');
INSERT INTO `stock` VALUES ('4', '4', '17', '2019-05-17 16:00:00', '2019-05-18 12:10:02');
INSERT INTO `stock` VALUES ('5', '5', '25', '2019-05-18 16:00:00', null);
INSERT INTO `stock` VALUES ('6', '6', '10', '2019-05-18 16:00:00', null);
INSERT INTO `stock` VALUES ('7', '7', '0', '2019-05-18 03:52:23', null);
INSERT INTO `stock` VALUES ('8', '8', '0', '2019-05-18 03:56:36', null);
INSERT INTO `stock` VALUES ('9', '9', '0', '2019-05-18 03:58:00', null);
INSERT INTO `stock` VALUES ('10', '10', '0', '2019-05-18 03:58:30', null);
INSERT INTO `stock` VALUES ('11', '11', '0', '2019-05-18 03:58:57', null);

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
-- Records of store
-- ----------------------------
INSERT INTO `store` VALUES ('1', '1', 'dell', '4', '0');

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
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'dengrongzhang', 'e10adc3949ba59abbe56e057f20f883e', '40200', '13251513690', '哈尔滨');

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
