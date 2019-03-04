/*
Navicat MariaDB Data Transfer

Source Server         : local
Source Server Version : 100308
Source Host           : localhost:3306
Source Database       : worker

Target Server Type    : MariaDB
Target Server Version : 100308
File Encoding         : 65001

Date: 2019-03-04 13:59:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for schedule_task_0
-- ----------------------------
DROP TABLE IF EXISTS `schedule_task_0`;
CREATE TABLE `schedule_task_0` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `task_type` varchar(255) NOT NULL COMMENT '任务类型',
  `task_key1` varchar(100) DEFAULT NULL COMMENT '任务关键字1',
  `task_key2` varchar(100) DEFAULT NULL COMMENT '任务关键字2',
  `body_class` varchar(255) NOT NULL COMMENT '任务体类名',
  `task_body` varchar(4000) NOT NULL COMMENT '任务体内容',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（‘-1’取消‘0’初始化 ‘1’执行中 ‘2’完成 ‘10’失败）',
  `execute_count` int(11) NOT NULL DEFAULT 0 COMMENT '执行次数',
  `last_time` datetime NOT NULL COMMENT '最近更新时间',
  `region_no` tinyint(4) NOT NULL DEFAULT 0 COMMENT '任务分区号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `fingerprint` varchar(255) NOT NULL COMMENT '任务指纹',
  PRIMARY KEY (`id`),
  UNIQUE KEY `schedule_task_0_fingerprint` (`fingerprint`),
  KEY `schedule_task_0_index1` (`task_key1`),
  KEY `schedule_task_0_index2` (`task_key2`),
  KEY `schedule_task_0_select` (`status`,`region_no`)
) ENGINE=InnoDB AUTO_INCREMENT=31207 DEFAULT CHARSET=utf8 COMMENT='任务表_0';

-- ----------------------------
-- Records of schedule_task_0
-- ----------------------------
INSERT INTO `schedule_task_0` VALUES ('8114', '2019-07-31 11:48:05', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"sourceType\":\"1\",\"sourceId\":\"1\", \"money\":\"1\", \"batch\":\"1\", \"cityId\":\"1\", \"recSource\":\"1\", \"customerId\":\"1\", \"settlementMethod\":\"0\", \"settlementType\":\"0\", \"tradeSerialNumber\":\"1\"}', '0', '0', '2019-03-04 11:03:23', '6', '这是一个异常', '20618');
INSERT INTO `schedule_task_0` VALUES ('8118', '2017-07-31 14:28:31', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"sourceType\":\"1\",\"sourceId\":\"1\", \"money\":\"1\", \"batch\":\"1\", \"cityId\":\"1\", \"recSource\":\"1\", \"customerId\":\"1\", \"settlementMethod\":\"0\", \"settlementType\":\"0\", \"tradeSerialNumber\":\"1\"}', '0', '0', '2019-03-04 11:03:23', '50', null, '20624');
INSERT INTO `schedule_task_0` VALUES ('12532', '2017-10-24 16:14:41', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"sourceType\":\"1\",\"sourceId\":\"1\", \"money\":\"1\", \"batch\":\"1\", \"cityId\":\"1\", \"recSource\":\"1\", \"customerId\":\"1\", \"settlementMethod\":\"0\", \"settlementType\":\"0\", \"tradeSerialNumber\":\"1\"}', '0', '0', '2019-03-04 11:03:23', '56', null, '21002');
INSERT INTO `schedule_task_0` VALUES ('20306', '2017-11-14 16:57:13', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"sourceType\":\"1\",\"sourceId\":\"1\", \"money\":\"1\", \"batch\":\"1\", \"cityId\":\"1\", \"recSource\":\"1\", \"customerId\":\"1\", \"settlementMethod\":\"0\", \"settlementType\":\"0\", \"tradeSerialNumber\":\"1\"}', '0', '0', '2019-03-04 11:03:24', '32', null, '21080');
INSERT INTO `schedule_task_0` VALUES ('20316', '2017-11-15 18:44:45', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"sourceType\":\"1\",\"sourceId\":\"1\", \"money\":\"1\", \"batch\":\"1\", \"cityId\":\"1\", \"recSource\":\"1\", \"customerId\":\"1\", \"settlementMethod\":\"0\", \"settlementType\":\"0\", \"tradeSerialNumber\":\"1\"}', '0', '0', '2019-03-04 11:03:24', '31', null, '21090');
INSERT INTO `schedule_task_0` VALUES ('20318', '2017-11-15 18:44:45', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"sourceType\":\"1\",\"sourceId\":\"1\", \"money\":\"1\", \"batch\":\"1\", \"cityId\":\"1\", \"recSource\":\"1\", \"customerId\":\"1\", \"settlementMethod\":\"0\", \"settlementType\":\"0\", \"tradeSerialNumber\":\"1\"}', '0', '5', '2019-03-04 11:03:24', '31', null, '21092');
INSERT INTO `schedule_task_0` VALUES ('20320', '2017-11-15 18:44:45', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"sourceType\":\"1\",\"sourceId\":\"1\", \"money\":\"1\", \"batch\":\"1\", \"cityId\":\"1\", \"recSource\":\"1\", \"customerId\":\"1\", \"settlementMethod\":\"0\", \"settlementType\":\"0\", \"tradeSerialNumber\":\"1\"}', '0', '0', '2019-03-04 11:03:24', '31', null, '21094');
INSERT INTO `schedule_task_0` VALUES ('31082', '2017-11-30 11:59:54', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"sourceType\":\"1\",\"sourceId\":\"1\", \"money\":\"1\", \"batch\":\"1\", \"cityId\":\"1\", \"recSource\":\"1\", \"customerId\":\"1\", \"settlementMethod\":\"0\", \"settlementType\":\"0\", \"tradeSerialNumber\":\"1\"}', '0', '5', '2019-03-04 11:03:24', '21', null, '21084');
INSERT INTO `schedule_task_0` VALUES ('31084', '2017-11-30 11:59:58', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"sourceType\":\"1\",\"sourceId\":\"1\", \"money\":\"1\", \"batch\":\"1\", \"cityId\":\"1\", \"recSource\":\"1\", \"customerId\":\"1\", \"settlementMethod\":\"0\", \"settlementType\":\"0\", \"tradeSerialNumber\":\"1\"}', '0', '5', '2019-03-04 11:03:24', '15', null, '21082');
INSERT INTO `schedule_task_0` VALUES ('31132', '2017-11-30 13:23:14', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"sourceType\":\"1\",\"sourceId\":\"1\", \"money\":\"1\", \"batch\":\"1\", \"cityId\":\"1\", \"recSource\":\"1\", \"customerId\":\"1\", \"settlementMethod\":\"0\", \"settlementType\":\"0\", \"tradeSerialNumber\":\"1\"}', '0', '5', '2019-03-04 11:03:24', '59', null, '21282');
INSERT INTO `schedule_task_0` VALUES ('31189', '2018-04-19 16:09:17', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"sourceType\":\"1\",\"sourceId\":\"1\", \"money\":\"1\", \"batch\":\"1\", \"cityId\":\"1\", \"recSource\":\"1\", \"customerId\":\"1\", \"settlementMethod\":\"0\", \"settlementType\":\"0\", \"tradeSerialNumber\":\"1\"}', '0', '0', '2019-03-04 11:03:24', '62', null, '3');
INSERT INTO `schedule_task_0` VALUES ('31190', '2018-05-30 15:06:40', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"sourceType\":\"1\",\"sourceId\":\"1\", \"money\":\"1\", \"batch\":\"1\", \"cityId\":\"1\", \"recSource\":\"1\", \"customerId\":\"1\", \"settlementMethod\":\"0\", \"settlementType\":\"0\", \"tradeSerialNumber\":\"1\"}', '0', '0', '2019-03-04 11:03:24', '7', null, '22417');
INSERT INTO `schedule_task_0` VALUES ('31191', '2018-06-06 10:50:24', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"sourceType\":\"1\",\"sourceId\":\"1\", \"money\":\"1\", \"batch\":\"1\", \"cityId\":\"1\", \"recSource\":\"1\", \"customerId\":\"1\", \"settlementMethod\":\"0\", \"settlementType\":\"0\", \"tradeSerialNumber\":\"1\"}', '0', '0', '2019-03-04 11:03:24', '23', null, '22424');
INSERT INTO `schedule_task_0` VALUES ('31192', '2018-06-06 12:19:37', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"sourceType\":\"1\",\"sourceId\":\"1\", \"money\":\"1\", \"batch\":\"1\", \"cityId\":\"1\", \"recSource\":\"1\", \"customerId\":\"1\", \"settlementMethod\":\"0\", \"settlementType\":\"0\", \"tradeSerialNumber\":\"1\"}', '0', '5', '2019-03-04 11:03:24', '58', null, '22418');
INSERT INTO `schedule_task_0` VALUES ('31193', '2018-06-06 14:44:19', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"sourceType\":\"1\",\"sourceId\":\"1\", \"money\":\"1\", \"batch\":\"1\", \"cityId\":\"1\", \"recSource\":\"1\", \"customerId\":\"1\", \"settlementMethod\":\"0\", \"settlementType\":\"0\", \"tradeSerialNumber\":\"1\"}', '0', '5', '2019-03-04 11:03:24', '50', null, '22422');
INSERT INTO `schedule_task_0` VALUES ('31194', '2018-06-06 14:50:24', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"sourceType\":\"1\",\"sourceId\":\"1\", \"money\":\"1\", \"batch\":\"1\", \"cityId\":\"1\", \"recSource\":\"1\", \"customerId\":\"1\", \"settlementMethod\":\"0\", \"settlementType\":\"0\", \"tradeSerialNumber\":\"1\"}', '0', '5', '2019-03-04 11:03:24', '20', null, '22419');
INSERT INTO `schedule_task_0` VALUES ('31195', '2018-06-06 18:48:21', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"sourceType\":\"1\",\"sourceId\":\"1\", \"money\":\"1\", \"batch\":\"1\", \"cityId\":\"1\", \"recSource\":\"1\", \"customerId\":\"1\", \"settlementMethod\":\"0\", \"settlementType\":\"0\", \"tradeSerialNumber\":\"1\"}', '0', '5', '2019-03-04 11:03:24', '41', null, '22425');
INSERT INTO `schedule_task_0` VALUES ('31196', '2018-06-06 20:41:18', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"sourceType\":\"1\",\"sourceId\":\"1\", \"money\":\"1\", \"batch\":\"1\", \"cityId\":\"1\", \"recSource\":\"1\", \"customerId\":\"1\", \"settlementMethod\":\"0\", \"settlementType\":\"0\", \"tradeSerialNumber\":\"1\"}', '0', '5', '2019-03-04 11:03:24', '11', null, '22430');
INSERT INTO `schedule_task_0` VALUES ('31197', '2018-06-06 20:56:05', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"sourceType\":\"1\",\"sourceId\":\"1\", \"money\":\"1\", \"batch\":\"1\", \"cityId\":\"1\", \"recSource\":\"1\", \"customerId\":\"1\", \"settlementMethod\":\"0\", \"settlementType\":\"0\", \"tradeSerialNumber\":\"1\"}', '0', '5', '2019-03-04 11:03:25', '28', null, '22431');
INSERT INTO `schedule_task_0` VALUES ('31198', '2018-06-07 10:54:43', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"sourceType\":\"1\",\"sourceId\":\"1\", \"money\":\"1\", \"batch\":\"1\", \"cityId\":\"1\", \"recSource\":\"1\", \"customerId\":\"1\", \"settlementMethod\":\"0\", \"settlementType\":\"0\", \"tradeSerialNumber\":\"1\"}', '0', '5', '2019-03-04 11:03:27', '33', null, '22434');
INSERT INTO `schedule_task_0` VALUES ('31199', '2018-06-07 15:34:49', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"sourceType\":\"1\",\"sourceId\":\"1\", \"money\":\"1\", \"batch\":\"1\", \"cityId\":\"1\", \"recSource\":\"1\", \"customerId\":\"1\", \"settlementMethod\":\"0\", \"settlementType\":\"0\", \"tradeSerialNumber\":\"1\"}', '0', '5', '2019-03-04 11:03:25', '2', null, '22435');
INSERT INTO `schedule_task_0` VALUES ('31204', '2019-03-04 11:10:11', 'init_customer_banlance', null, null, 'com.collmall.model.WriteBackSolData', '{\"settlementMethod\":0,\"settlementType\":0}', '2', '0', '2019-03-04 11:13:18', '17', null, 'null_null_null');
