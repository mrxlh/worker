/*
Navicat MariaDB Data Transfer

Source Server         : local
Source Server Version : 100308
Source Host           : localhost:3306
Source Database       : worker

Target Server Type    : MariaDB
Target Server Version : 100308
File Encoding         : 65001

Date: 2019-03-04 13:58:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for schedule_tablefix
-- ----------------------------
DROP TABLE IF EXISTS `schedule_tablefix`;
CREATE TABLE `schedule_tablefix` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `task_type` varchar(255) NOT NULL COMMENT '任务类型',
  `table_fix` tinyint(4) NOT NULL COMMENT '任务表后缀',
  PRIMARY KEY (`id`),
  UNIQUE KEY `schedule_tablefix_tasktype` (`task_type`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='任务路由表';

-- ----------------------------
-- Records of schedule_tablefix
-- ----------------------------
INSERT INTO `schedule_tablefix` VALUES ('1', 'init_customer_banlance', '0');
INSERT INTO `schedule_tablefix` VALUES ('2', 'write_back_sol', '1');
