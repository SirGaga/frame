/*
 Navicat Premium Data Transfer

 Source Server         : 本机mysql
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : meal

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 08/06/2020 09:03:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bas_process_model
-- ----------------------------
DROP TABLE IF EXISTS `bas_process_model`;
CREATE TABLE `bas_process_model`  (
  `id` bigint(20) NOT NULL,
  `process_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `process_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `process_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `insert_time` datetime(0) NULL DEFAULT NULL,
  `insert_user` bigint(20) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `update_user` bigint(20) NULL DEFAULT NULL,
  `flag` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bas_process_model
-- ----------------------------
INSERT INTO `bas_process_model` VALUES (1262929491242532866, '请假流程', '请假流程', '', 'leave.uflo.xml', NULL, '2020-05-20 10:13:49', 1, '2020-05-20 10:13:52', 1, 0);
INSERT INTO `bas_process_model` VALUES (1268452353604935682, 'ptws', 'ptws', '', 'ptws.uflo.xml', NULL, '2020-06-04 15:59:42', 1, '2020-06-05 13:27:14', 1, 1);
INSERT INTO `bas_process_model` VALUES (1268706053682462721, '请假流程', 'leave', '', 'leave.uflo.xml', NULL, '2020-06-05 08:47:49', 1, NULL, NULL, 1);

-- ----------------------------
-- Table structure for biz_leave
-- ----------------------------
DROP TABLE IF EXISTS `biz_leave`;
CREATE TABLE `biz_leave`  (
  `id` bigint(20) NOT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `apply_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `draft` int(255) NULL DEFAULT NULL,
  `insert_time` date NULL DEFAULT NULL,
  `insert_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` date NULL DEFAULT NULL,
  `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `flag` int(2) NULL DEFAULT NULL,
  `day_count` int(255) NULL DEFAULT NULL,
  `position` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of biz_leave
-- ----------------------------
INSERT INTO `biz_leave` VALUES (1267381727955857409, '2020-05-17 00:00:00', '2020-06-23 00:00:00', '22', 1, '2020-06-01', '1', '2020-06-01', '0', 1, 222, '1');
INSERT INTO `biz_leave` VALUES (1267668012838752258, '2020-06-02 00:00:00', '2020-06-03 00:00:00', '孔庆麟', 1, '2020-06-02', '1', '2020-06-02', '0', 1, 1, '1');
INSERT INTO `biz_leave` VALUES (1267696191892668418, '2020-06-02 00:00:00', '2020-06-03 00:00:00', '222', 1, '2020-06-02', '1', '2020-06-02', '0', 1, 12, '1');
INSERT INTO `biz_leave` VALUES (1267696487087783938, '2020-06-02 00:00:00', '2020-06-03 00:00:00', '1', 1, '2020-06-02', '1', '2020-06-02', '0', 1, 11, '1');
INSERT INTO `biz_leave` VALUES (1267697602038722561, '2020-06-02 00:00:00', '2020-06-03 00:00:00', '22', 1, '2020-06-02', '1', '2020-06-02', '0', 1, 22, '1');
INSERT INTO `biz_leave` VALUES (1267697766233141250, '2020-06-02 00:00:00', '2020-06-04 00:00:00', '33', 1, '2020-06-02', '1', '2020-06-02', '0', 1, 3, '1');
INSERT INTO `biz_leave` VALUES (1267699061228625921, '2020-06-02 00:00:00', '2020-06-02 00:00:00', '22', 1, '2020-06-02', '1', '2020-06-02', '0', 1, 33, '1');
INSERT INTO `biz_leave` VALUES (1267699410458959874, '2020-06-03 00:00:00', '2020-06-04 00:00:00', 'admin', 1, '2020-06-02', '1', '2020-06-02', '0', 1, 10, '1');
INSERT INTO `biz_leave` VALUES (1268019513981460482, '2020-06-03 00:00:00', '2020-06-04 00:00:00', '22', 1, '2020-06-03', '1', '2020-06-03', '0', 1, 222, '1');
INSERT INTO `biz_leave` VALUES (1268044081353768962, '2020-06-03 00:00:00', '2020-06-04 00:00:00', 'kql', 1, '2020-06-03', '1234350504367190017', '2020-06-03', '0', 1, 2, '1');
INSERT INTO `biz_leave` VALUES (1268047546087960577, '2020-06-24 00:00:00', '2020-06-26 00:00:00', '2', 1, '2020-06-03', '1234350504367190017', '2020-06-03', '0', 1, 2, '1');
INSERT INTO `biz_leave` VALUES (1268055587243986945, '2020-06-03 00:00:00', '2020-06-03 00:00:00', 'kql', 1, '2020-06-03', '1260383323119755265', '2020-06-03', '0', 1, 2, '1');
INSERT INTO `biz_leave` VALUES (1268068322732216322, '2020-06-03 00:00:00', '2020-06-04 00:00:00', 'admin', 1, '2020-06-03', '1', '2020-06-03', '0', 1, 4, '1');
INSERT INTO `biz_leave` VALUES (1268070597303226370, '2020-06-03 00:00:00', '2020-06-04 00:00:00', 'kql', 1, '2020-06-03', '1234350504367190017', '2020-06-03', '0', 1, 15, '1');
INSERT INTO `biz_leave` VALUES (1268705736022654978, '2020-06-05 00:00:00', '2020-06-06 00:00:00', 'rrrr', 0, '2020-06-05', '1', NULL, NULL, 1, 3, '1');

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `BLOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ENTRY_ID` varchar(95) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TRIG_INST_NAME`(`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY`(`SCHED_NAME`, `INSTANCE_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_FT_J_G`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_T_G`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TG`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_DURABLE` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_J_REQ_RECOVERY`(`SCHED_NAME`, `REQUESTS_RECOVERY`) USING BTREE,
  INDEX `IDX_QRTZ_J_GRP`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LOCK_NAME` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `INSTANCE_NAME` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `STR_PROP_1` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STR_PROP_2` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STR_PROP_3` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `INT_PROP_1` int(11) NULL DEFAULT NULL,
  `INT_PROP_2` int(11) NULL DEFAULT NULL,
  `LONG_PROP_1` bigint(20) NULL DEFAULT NULL,
  `LONG_PROP_2` bigint(20) NULL DEFAULT NULL,
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL,
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_NAME` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_GROUP` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_NAME` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `JOB_GROUP` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DESCRIPTION` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) NULL DEFAULT NULL,
  `PRIORITY` int(11) NULL DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) NULL DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) NULL DEFAULT NULL,
  `JOB_DATA` blob NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_J`(`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_JG`(`SCHED_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_C`(`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE,
  INDEX `IDX_QRTZ_T_G`(`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_STATE`(`SCHED_NAME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_STATE`(`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_N_G_STATE`(`SCHED_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME`(`SCHED_NAME`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST`(`SCHED_NAME`, `TRIGGER_STATE`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_STATE`) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP`(`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_GROUP`, `TRIGGER_STATE`) USING BTREE,
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for re_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `re_role_menu`;
CREATE TABLE `re_role_menu`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `menu_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `insert_time` date NULL DEFAULT NULL,
  `insert_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` date NULL DEFAULT NULL,
  `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `flag` int(2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of re_role_menu
-- ----------------------------
INSERT INTO `re_role_menu` VALUES ('1268066247268913154', '1235835297491607554', '200', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247281496065', '1235835297491607554', '201', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247281496066', '1235835297491607554', '2011', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247281496067', '1235835297491607554', '2012', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247285690370', '1235835297491607554', '2013', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247285690371', '1235835297491607554', '2014', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247285690372', '1235835297491607554', '2015', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247289884673', '1235835297491607554', '2016', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247294078978', '1235835297491607554', '202', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247294078979', '1235835297491607554', '2021', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247294078980', '1235835297491607554', '2022', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247294078981', '1235835297491607554', '2023', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247302467586', '1235835297491607554', '2024', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247302467587', '1235835297491607554', '2025', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247306661890', '1235835297491607554', '205', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247310856194', '1235835297491607554', '400', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247315050498', '1235835297491607554', '401', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247315050499', '1235835297491607554', '4011', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247315050500', '1235835297491607554', '4012', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247319244801', '1235835297491607554', '4013', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247319244802', '1235835297491607554', '4014', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247323439106', '1235835297491607554', '403', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247323439107', '1235835297491607554', '405', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247323439108', '1235835297491607554', '4051', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247327633409', '1235835297491607554', '4052', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247327633410', '1235835297491607554', '4053', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247327633411', '1235835297491607554', '4054', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247327633412', '1235835297491607554', '4055', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247327633413', '1235835297491607554', '500', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247331827714', '1235835297491607554', '404', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247331827715', '1235835297491607554', '4041', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247331827716', '1235835297491607554', '4042', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247331827717', '1235835297491607554', '4043', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247331827718', '1235835297491607554', '4044', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247336022018', '1235835297491607554', '4045', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247336022019', '1235835297491607554', '501', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247336022020', '1235835297491607554', '5011', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247336022021', '1235835297491607554', '502', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066247340216321', '1235835297491607554', '503', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066267821002754', '1260375212275351553', '500', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066267829391362', '1260375212275351553', '501', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066267829391363', '1260375212275351553', '5011', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066267829391364', '1260375212275351553', '502', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066267829391365', '1260375212275351553', '503', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066285466439682', '1260375270643286017', '500', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066285474828289', '1260375270643286017', '501', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066285474828290', '1260375270643286017', '5011', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066285474828291', '1260375270643286017', '502', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066285474828292', '1260375270643286017', '503', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066303149625345', '1260375330252734465', '200', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066303153819650', '1260375330252734465', '201', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066303153819651', '1260375330252734465', '2011', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066303153819652', '1260375330252734465', '2012', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066303153819653', '1260375330252734465', '2013', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066303153819654', '1260375330252734465', '2014', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066303153819655', '1260375330252734465', '2015', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066303153819656', '1260375330252734465', '2016', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066303153819657', '1260375330252734465', '205', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066303153819658', '1260375330252734465', '500', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066303153819659', '1260375330252734465', '404', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066303153819660', '1260375330252734465', '4041', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066303153819661', '1260375330252734465', '4042', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066303153819662', '1260375330252734465', '4043', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066303162208257', '1260375330252734465', '4044', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066303162208258', '1260375330252734465', '4045', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066303162208259', '1260375330252734465', '501', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066303162208260', '1260375330252734465', '5011', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066303162208261', '1260375330252734465', '502', '2020-06-03', '1', NULL, NULL, 1);
INSERT INTO `re_role_menu` VALUES ('1268066303162208262', '1260375330252734465', '503', '2020-06-03', '1', NULL, NULL, 1);

-- ----------------------------
-- Table structure for re_user_role
-- ----------------------------
DROP TABLE IF EXISTS `re_user_role`;
CREATE TABLE `re_user_role`  (
  `id` bigint(32) NOT NULL,
  `user_id` bigint(32) NULL DEFAULT NULL,
  `role_id` bigint(32) NULL DEFAULT NULL,
  `insert_time` date NULL DEFAULT NULL,
  `insert_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` date NULL DEFAULT NULL,
  `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `flag` int(2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of re_user_role
-- ----------------------------
INSERT INTO `re_user_role` VALUES (1264838841527734274, 1260383191787708418, 1260375212275351553, '2020-05-25', '1', NULL, NULL, 1);
INSERT INTO `re_user_role` VALUES (1264838874209751041, 1260383323119755265, 1260375330252734465, '2020-05-25', '1', NULL, NULL, 1);
INSERT INTO `re_user_role` VALUES (1264838891850993666, 1262610315634319362, 1260375330252734465, '2020-05-25', '1', NULL, NULL, 1);
INSERT INTO `re_user_role` VALUES (1264839077725769730, 1260398620031668225, 1260375270643286017, '2020-05-25', '1', NULL, NULL, 1);
INSERT INTO `re_user_role` VALUES (1264839106188316674, 1262977393881055233, 1260375212275351553, '2020-05-25', '1', NULL, NULL, 1);
INSERT INTO `re_user_role` VALUES (1264839195778650114, 1234350504367190017, 1235835297491607554, '2020-05-25', '1', NULL, NULL, 1);
INSERT INTO `re_user_role` VALUES (1265118395064451073, 1260383281751334914, 1260375270643286017, '2020-05-26', '1', NULL, NULL, 1);
INSERT INTO `re_user_role` VALUES (1265120200624242689, 1265120200343224321, 1235835297491607554, '2020-05-26', '1', NULL, NULL, 1);
INSERT INTO `re_user_role` VALUES (1267263330391687170, 1, 1235835297491607554, '2020-06-01', '1', NULL, NULL, 1);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(32) NOT NULL,
  `dept_num` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dept_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `insert_time` date NULL DEFAULT NULL,
  `insert_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` date NULL DEFAULT NULL,
  `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `flag` int(2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1234683802549317633, 'YUNYING', '运营', '2020-03-03', '1', NULL, NULL, 1);
INSERT INTO `sys_dept` VALUES (1234683841082388481, 'SHICHANG', '市场', '2020-03-03', '1', '2020-05-14', '1', 1);
INSERT INTO `sys_dept` VALUES (1264832068540489730, 'IT', '资讯', '2020-05-25', '1', NULL, NULL, 1);
INSERT INTO `sys_dept` VALUES (1264832107836923905, 'HR', '人事部', '2020-05-25', '1', NULL, NULL, 1);
INSERT INTO `sys_dept` VALUES (1264832134298787841, 'CW', '财务', '2020-05-25', '1', NULL, NULL, 1);
INSERT INTO `sys_dept` VALUES (1264832191710420994, 'ZJB', '总经办', '2020-05-25', '1', NULL, NULL, 1);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(32) NOT NULL,
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '父节点',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `permission` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限字符串',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `leaf` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否子节点',
  `url` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `insert_time` date NULL DEFAULT NULL,
  `insert_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` date NULL DEFAULT NULL,
  `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `flag` int(2) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (200, '0', '系统设置', '', 'layui-icon-set', '0', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (201, '200', '人员信息', 'user:index', 'layui-icon-user', '1', '/user/index', NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (202, '200', '角色信息', 'role:index', 'layui-icon-group', '1', '/role/index', NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (205, '200', '部门信息', 'dept:index', 'layui-icon-flag', '1', '/dept/index', NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (400, '0', '流程', NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (401, '400', '流程定义', 'process:index', NULL, '1', '/process/index', NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (403, '400', '流程测试', NULL, NULL, '1', '/uflo/central', NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (404, '500', '请假', 'leave:index', NULL, '1', '/leave/index', NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (405, '400', '流程模型', 'processModel:index', '', '1', '/processModel/index', NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (500, '0', '流程处理', NULL, NULL, '0', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (501, '500', '待办任务', 'task:index', NULL, '1', '/task/index', NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (502, '500', '流程监控(发起人)', '', NULL, '1', '/processIncetance/promoter', NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (503, '500', '流程监控(处理人)', '', NULL, '1', '/processIncetance/assignee', NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (2011, '201', '新增', 'user:add', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (2012, '201', '修改', 'user:edit', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (2013, '201', '删除', 'user:delete', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (2014, '201', '保存', 'user:save', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (2015, '201', '列表', 'user:list', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (2016, '201', '修改密码', 'user:password', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (2021, '202', '列表', 'role:list', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (2022, '202', '保存', 'role:save', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (2023, '202', '新增', 'role:add', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (2024, '202', '修改', 'role:edit', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (2025, '202', '删除', 'role:delete', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (2031, '203', '新增', 'parameter:add', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (2032, '203', '修改', 'parameter:edit', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (2033, '203', '删除', 'parameter:delete', '', '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (2034, '203', '列表', 'parameter:list', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (2035, '203', '保存', 'parameter:save', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (4011, '401', '新增', 'process:add', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (4012, '401', '修改', 'process:edit', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (4013, '401', '删除', 'process:delete', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (4014, '401', '列表', 'process:list', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (4041, '404', '新增', 'leave:add', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (4042, '404', '修改', 'leave:edit', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (4043, '404', '删除', 'leave:delete', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (4044, '404', '保存', 'leave:save', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (4045, '404', '列表', 'leave:list', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (4051, '405', '新增', 'processModel:add', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (4052, '405', '修改', 'processModel:edit', '', '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (4053, '405', '删除', 'processModel:delete', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (4054, '405', '列表', 'processModel:list', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (4055, '405', '保存', 'processModel:save', NULL, '1', NULL, NULL, NULL, NULL, NULL, 1);
INSERT INTO `sys_menu` VALUES (5011, '501', '列表', 'task:list', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(32) NOT NULL,
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_num` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `insert_time` date NULL DEFAULT NULL,
  `insert_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` date NULL DEFAULT NULL,
  `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `flag` int(2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1235835297491607554, '管理员', 'SYS', '2020-03-06', 'null', '2020-06-03', '1', 1);
INSERT INTO `sys_role` VALUES (1260375212275351553, '科长', 'KZ', '2020-05-13', '1', '2020-06-03', '1', 1);
INSERT INTO `sys_role` VALUES (1260375270643286017, '经理', 'JL', '2020-05-13', '1', '2020-06-03', '1', 1);
INSERT INTO `sys_role` VALUES (1260375330252734465, '人事', 'HR', '2020-05-13', '1', '2020-06-03', '1', 1);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL,
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_num` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `dept` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门',
  `insert_time` date NULL DEFAULT NULL,
  `insert_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` date NULL DEFAULT NULL,
  `update_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `flag` int(2) NULL DEFAULT NULL,
  `admin` int(2) NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'f9620b2992d9134421fd292f8834af3b', 'admin', '1264832068540489730', NULL, NULL, '2020-06-03', '1', 1, 0, '', NULL);
INSERT INTO `sys_user` VALUES (1234350504367190017, 'kql', 'b1a1d2c95c069fee8f0add77c72d75b5', 'line', '1264832068540489730', '2020-03-02', '1', '2020-05-25', '1', 1, 0, '', NULL);
INSERT INTO `sys_user` VALUES (1260383191787708418, '陈阳', 'a0b61b078dbd20731ec7c4c0cde9e2d9', 'cy', '1264832068540489730', '2020-05-13', '1', '2020-05-25', '1', 1, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (1260383281751334914, '资讯部经理', '5f77ee2f8a4282cdc7572365289bf698', 'ITJL', '1264832068540489730', '2020-05-13', '1', '2020-05-26', '1', 1, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (1260383323119755265, '人事', 'eb21884e249db012ca9c5f822f00ba7b', 'HR', '1264832107836923905', '2020-05-13', '1', '2020-05-25', '1', 1, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (1260398620031668225, '市场部经理', 'cd4bff15ef17c58d3aab1eb746b8901f', 'JL2', '1234683841082388481', '2020-05-13', '1', '2020-05-25', '1', 1, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (1262610315634319362, '人事2号', '01eb92cdbf153db90a8f44dffdf5e284', 'HR2', '1264832107836923905', '2020-05-19', '1', '2020-05-25', '1', 1, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (1262977393881055233, '科长2', 'e76a3b6832f50b0f4333e0bec2aba1cc', 'KZ2', '1264832068540489730', '2020-05-20', '1', '2020-05-25', '1', 1, 0, NULL, NULL);
INSERT INTO `sys_user` VALUES (1265120200343224321, '财务人员', '7d3b59a68ea5a9960d0ef21b7a3b8053', 'cw', '1264832134298787841', '2020-05-26', '1', NULL, NULL, 1, 0, NULL, NULL);

-- ----------------------------
-- Table structure for uflo_blob
-- ----------------------------
DROP TABLE IF EXISTS `uflo_blob`;
CREATE TABLE `uflo_blob`  (
  `ID_` bigint(20) NOT NULL,
  `BLOB_VALUE_` longblob NULL,
  `NAME_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PROCESS_ID_` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of uflo_blob
-- ----------------------------
INSERT INTO `uflo_blob` VALUES (2, 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D227574662D38223F3E3C75666C6F2D70726F63657373206E616D653D22E8AFB7E58187E6B581E7A88B22206B65793D226C65617665222073746172742D70726F636573732D75726C3D222F6C656176652F666F726D223E3C7374617274206E616D653D22E5BC80E5A78B2220783D2238352220793D22323239222077696474683D22343022206865696768743D2237302220206576656E742D68616E646C65722D6265616E3D2272656D6F746548616E646C6572222075726C3D222F6C656176652F65646974223E203C6465736372697074696F6E3E3C215B43444154415B687474703A2F2F31302E31372E3234312E33343A383038302F6C656176655D5D3E3C2F6465736372697074696F6E3E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E588A4E696AD223E3C2F73657175656E63652D666C6F773E3C2F73746172743E3C6465636973696F6E206E616D653D22E588A4E696AD2220783D223236312220793D22323330222077696474683D22343022206865696768743D2237302220206465636973696F6E2D747970653D2245787072657373696F6E223E3C65787072657373696F6E3E3C215B43444154415B247B726F6C653D3D2731273F27E699AEE9809AE59198E5B7A5273A726F6C653D3D2732273F27E983A8E997A8E4B8BBE7AEA1273A27E585B6E4BB96277D5D5D3E3C2F65787072657373696F6E3E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E983A8E997A8E4B8BBE7AEA122206E616D653D22E699AEE9809AE59198E5B7A5223E3C2F73657175656E63652D666C6F773E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E983A8E997A8E7BB8FE7908622206E616D653D22E983A8E997A8E4B8BBE7AEA1223E3C2F73657175656E63652D666C6F773E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D224852E5AEA1E6A0B822206E616D653D22E585B6E4BB96223E3C2F73657175656E63652D666C6F773E3C2F6465636973696F6E3E3C7461736B206E616D653D22E983A8E997A8E4B8BBE7AEA12220783D223433352220793D22313534222077696474683D22343022206865696768743D2237302220207461736B2D747970653D224E6F726D616C222061737369676E6D656E742D747970653D2248616E646C6572222061737369676E6D656E742D68616E646C65722D6265616E3D22646570744C6561646572222061737369676E6D656E742D68616E646C65722D6265616E2D646573633D22756E646566696E65642220616C6C6F772D737065636966792D61737369676E65653D2274727565223E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E983A8E997A8E7BB8FE79086223E3C2F73657175656E63652D666C6F773E3C2F7461736B3E3C7461736B206E616D653D22E983A8E997A8E7BB8FE790862220783D223433342220793D22333238222077696474683D22343022206865696768743D2237302220207461736B2D747970653D224E6F726D616C222061737369676E6D656E742D747970653D2248616E646C6572222061737369676E6D656E742D68616E646C65722D6265616E3D22646570744D616E61676572222061737369676E6D656E742D68616E646C65722D6265616E2D646573633D22756E646566696E65642220616C6C6F772D737065636966792D61737369676E65653D2274727565223E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D224852E5AEA1E6A0B8223E3C2F73657175656E63652D666C6F773E3C2F7461736B3E3C7461736B206E616D653D224852E5AEA1E6A0B82220783D223532342220793D22323239222077696474683D22343022206865696768743D2237302220207461736B2D747970653D224E6F726D616C222061737369676E6D656E742D747970653D2241737369676E65652220616C6C6F772D737065636966792D61737369676E65653D2274727565223E3C61737369676E65652070726F76696465722D69643D226465707441737369676E656550726F766964657222206E616D653D22E4BABAE4BA8BE983A8222069643D2231323634383332313037383336393233393035222F3E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E588A4E696ADE5A4A9E695B0223E3C2F73657175656E63652D666C6F773E3C2F7461736B3E3C6465636973696F6E206E616D653D22E588A4E696ADE5A4A9E695B02220783D223636322220793D22323330222077696474683D22343022206865696768743D2237302220206465636973696F6E2D747970653D2245787072657373696F6E223E3C65787072657373696F6E3E3C215B43444154415B247B646179436F756E743E333F27E5A4A7E4BA8E33E5A4A9273A27E5B08FE4BA8EE7AD89E4BA8E33E5A4A9277D5D5D3E3C2F65787072657373696F6E3E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E7BB93E69D9FE6B581E7A88B3122206E616D653D22E5B08FE4BA8EE7AD89E4BA8E33E5A4A9223E3C2F73657175656E63652D666C6F773E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E983A8E997A8E4BC9AE7ADBE22206E616D653D22E5A4A7E4BA8E33E5A4A9223E3C2F73657175656E63652D666C6F773E3C2F6465636973696F6E3E3C7461736B206E616D653D22E983A8E997A8E4BC9AE7ADBE2220783D223832382220793D22323330222077696474683D22343022206865696768743D2237302220207461736B2D747970653D22436F756E7465727369676E222061737369676E6D656E742D747970653D2248616E646C6572222061737369676E6D656E742D68616E646C65722D6265616E3D227573657244657074222061737369676E6D656E742D68616E646C65722D6265616E2D646573633D22756E646566696E65642220616C6C6F772D737065636966792D61737369676E65653D2266616C7365223E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E8B4A2E58AA1E5AEA1E6A0B8223E3C2F73657175656E63652D666C6F773E3C2F7461736B3E3C656E64206E616D653D22E7BB93E69D9FE6B581E7A88B312220783D223832352220793D22333238222077696474683D22343022206865696768743D2237302220207465726D696E6174653D2274727565223E3C2F656E643E3C7461736B206E616D653D22E8B4A2E58AA1E5AEA1E6A0B82220783D223933302220793D22323330222077696474683D22343022206865696768743D2237302220207461736B2D747970653D224E6F726D616C222061737369676E6D656E742D747970653D2241737369676E65652220616C6C6F772D737065636966792D61737369676E65653D2266616C7365223E3C61737369676E65652070726F76696465722D69643D226465707441737369676E656550726F766964657222206E616D653D22E8B4A2E58AA1222069643D2231323634383332313334323938373837383431222F3E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E7BB93E69D9FE6B581E7A88B31223E3C2F73657175656E63652D666C6F773E3C2F7461736B3E3C2F75666C6F2D70726F636573733E, '请假流程.uflo.xml', 1);
INSERT INTO `uflo_blob` VALUES (20, 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D227574662D38223F3E3C75666C6F2D70726F63657373206E616D653D227074777322206B65793D2270747773223E3C7374617274206E616D653D22E5BC80E5A78B312220783D223133312220793D22323138222077696474683D22343022206865696768743D22373022203E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E4B8BBE7AEA1E5AEA1E689B9223E3C2F73657175656E63652D666C6F773E3C2F73746172743E3C7461736B206E616D653D22E4B8BBE7AEA1E5AEA1E689B92220783D223239352220793D22323233222077696474683D22343022206865696768743D2237302220207461736B2D747970653D224E6F726D616C222061737369676E6D656E742D747970653D2241737369676E65652220616C6C6F772D737065636966792D61737369676E65653D2274727565223E3C61737369676E65652070726F76696465722D69643D227573657241737369676E656550726F766964657222206E616D653D22E99988E998B3222069643D2231323630333833313931373837373038343138222F3E3C61737369676E65652070726F76696465722D69643D227573657241737369676E656550726F766964657222206E616D653D22E7A791E995BF32222069643D2231323632393737333933383831303535323333222F3E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E7BB8FE79086E5AEA1E689B9223E3C2F73657175656E63652D666C6F773E3C2F7461736B3E3C7461736B206E616D653D22E7BB8FE79086E5AEA1E689B92220783D223434362220793D22323237222077696474683D22343022206865696768743D2237302220207461736B2D747970653D224E6F726D616C222061737369676E6D656E742D747970653D2241737369676E65652220616C6C6F772D737065636966792D61737369676E65653D2266616C7365223E3C61737369676E65652070726F76696465722D69643D227573657241737369676E656550726F766964657222206E616D653D22E8B584E8AEAFE983A8E7BB8FE79086222069643D2231323630333833323831373531333334393134222F3E3C636F6D706F6E656E742D617574686F7269747920636F6D706F6E656E743D2264697361677265652220617574686F726974793D2252656164416E645772697465222F3E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E586B3E7AD96223E3C2F73657175656E63652D666C6F773E3C2F7461736B3E3C7461736B206E616D653D224852E5AEA1E689B92220783D223731342220793D22323236222077696474683D22343022206865696768743D2237302220207461736B2D747970653D224E6F726D616C222061737369676E6D656E742D747970653D2241737369676E65652220616C6C6F772D737065636966792D61737369676E65653D2266616C7365223E3C61737369676E65652070726F76696465722D69643D227573657241737369676E656550726F766964657222206E616D653D22E4BABAE4BA8B222069643D2231323630333833333233313139373535323635222F3E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D224852E698AFE590A6E5908CE6848F223E3C2F73657175656E63652D666C6F773E3C2F7461736B3E3C6465636973696F6E206E616D653D22E586B3E7AD962220783D223538362220793D22323236222077696474683D22343022206865696768743D2237302220206465636973696F6E2D747970653D2245787072657373696F6E223E3C65787072657373696F6E3E3C215B43444154415B247B61677265653D3D313F27E5908CE6848F273A27E590A6E586B3277D5D5D3E3C2F65787072657373696F6E3E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D224852E5AEA1E689B922206E616D653D22E5908CE6848F223E3C2F73657175656E63652D666C6F773E3C73657175656E63652D666C6F7720673D223630352C3136312C3135332C3135303A2220747970653D226C696E652220746F3D22E5BC80E5A78B3122206E616D653D22E590A6E586B3223E3C2F73657175656E63652D666C6F773E3C2F6465636973696F6E3E3C6465636973696F6E206E616D653D224852E698AFE590A6E5908CE6848F2220783D223731352220793D22333336222077696474683D22343022206865696768743D2237302220206465636973696F6E2D747970653D2245787072657373696F6E223E3C65787072657373696F6E3E3C215B43444154415B247B61677265653D3D313F27E5908CE6848F273A27E590A6E586B3277D5D5D3E3C2F65787072657373696F6E3E3C73657175656E63652D666C6F7720673D223835352C3335393A2220747970653D226C696E652220746F3D22E8B4A2E58AA1E5AEA1E689B922206E616D653D22E5908CE6848F223E3C2F73657175656E63652D666C6F773E3C73657175656E63652D666C6F7720673D223331312C3335323A2220747970653D226C696E652220746F3D22E4B8BBE7AEA1E5AEA1E689B922206E616D653D22E590A6E586B3223E3C2F73657175656E63652D666C6F773E3C2F6465636973696F6E3E3C7461736B206E616D653D22E8B4A2E58AA1E5AEA1E689B92220783D223833362220793D22323331222077696474683D22343022206865696768743D2237302220207461736B2D747970653D224E6F726D616C222061737369676E6D656E742D747970653D2241737369676E65652220616C6C6F772D737065636966792D61737369676E65653D2266616C7365223E3C61737369676E65652070726F76696465722D69643D227573657241737369676E656550726F766964657222206E616D653D22E8B4A2E58AA1E4BABAE59198222069643D2231323635313230323030333433323234333231222F3E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E7BB93E69D9F223E3C2F73657175656E63652D666C6F773E3C2F7461736B3E3C656E64206E616D653D22E7BB93E69D9F2220783D223934322220793D22323238222077696474683D22343022206865696768743D2237302220207465726D696E6174653D2274727565223E3C2F656E643E3C2F75666C6F2D70726F636573733E, 'ptws.uflo.xml', 19);
INSERT INTO `uflo_blob` VALUES (204, 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D227574662D38223F3E3C75666C6F2D70726F63657373206E616D653D227074777322206B65793D2270747773223E3C7374617274206E616D653D22E5BC80E5A78B312220783D223133312220793D22323138222077696474683D22343022206865696768743D22373022203E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E4B8BBE7AEA1E5AEA1E689B9223E3C2F73657175656E63652D666C6F773E3C2F73746172743E3C7461736B206E616D653D22E4B8BBE7AEA1E5AEA1E689B92220783D223239352220793D22323233222077696474683D22343022206865696768743D2237302220207461736B2D747970653D224E6F726D616C222061737369676E6D656E742D747970653D2241737369676E65652220616C6C6F772D737065636966792D61737369676E65653D2266616C7365223E3C61737369676E65652070726F76696465722D69643D227573657241737369676E656550726F766964657222206E616D653D22E99988E998B3222069643D2231323630333833313931373837373038343138222F3E3C61737369676E65652070726F76696465722D69643D227573657241737369676E656550726F766964657222206E616D653D22E7A791E995BF32222069643D2231323632393737333933383831303535323333222F3E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E7BB8FE79086E5AEA1E689B9223E3C2F73657175656E63652D666C6F773E3C2F7461736B3E3C7461736B206E616D653D22E7BB8FE79086E5AEA1E689B92220783D223434362220793D22323237222077696474683D22343022206865696768743D2237302220207461736B2D747970653D224E6F726D616C222061737369676E6D656E742D747970653D2241737369676E65652220616C6C6F772D737065636966792D61737369676E65653D2266616C7365223E3C61737369676E65652070726F76696465722D69643D227573657241737369676E656550726F766964657222206E616D653D22E8B584E8AEAFE983A8E7BB8FE79086222069643D2231323630333833323831373531333334393134222F3E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E586B3E7AD96223E3C2F73657175656E63652D666C6F773E3C2F7461736B3E3C7461736B206E616D653D224852E5AEA1E689B92220783D223731342220793D22323236222077696474683D22343022206865696768743D2237302220207461736B2D747970653D224E6F726D616C222061737369676E6D656E742D747970653D2241737369676E65652220616C6C6F772D737065636966792D61737369676E65653D2266616C7365223E3C61737369676E65652070726F76696465722D69643D227573657241737369676E656550726F766964657222206E616D653D22E4BABAE4BA8B222069643D2231323630333833333233313139373535323635222F3E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D224852E698AFE590A6E5908CE6848F223E3C2F73657175656E63652D666C6F773E3C2F7461736B3E3C6465636973696F6E206E616D653D22E586B3E7AD962220783D223538362220793D22323236222077696474683D22343022206865696768743D2237302220206465636973696F6E2D747970653D2245787072657373696F6E223E3C65787072657373696F6E3E3C215B43444154415B247B61677265653D3D313F27E5908CE6848F273A27E590A6E586B3277D5D5D3E3C2F65787072657373696F6E3E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D224852E5AEA1E689B922206E616D653D22E5908CE6848F223E3C2F73657175656E63652D666C6F773E3C73657175656E63652D666C6F7720673D223630352C3136312C3331372C3135343A2220747970653D226C696E652220746F3D22E4B8BBE7AEA1E5AEA1E689B922206E616D653D22E590A6E586B3223E3C2F73657175656E63652D666C6F773E3C2F6465636973696F6E3E3C6465636973696F6E206E616D653D224852E698AFE590A6E5908CE6848F2220783D223731352220793D22333336222077696474683D22343022206865696768743D2237302220206465636973696F6E2D747970653D2245787072657373696F6E223E3C65787072657373696F6E3E3C215B43444154415B247B61677265653D3D313F27E5908CE6848F273A27E590A6E586B3277D5D5D3E3C2F65787072657373696F6E3E3C73657175656E63652D666C6F7720673D223835352C3335393A2220747970653D226C696E652220746F3D22E8B4A2E58AA1E5AEA1E689B922206E616D653D22E5908CE6848F223E3C2F73657175656E63652D666C6F773E3C73657175656E63652D666C6F7720673D223331312C3335323A2220747970653D226C696E652220746F3D22E4B8BBE7AEA1E5AEA1E689B922206E616D653D22E590A6E586B3223E3C2F73657175656E63652D666C6F773E3C2F6465636973696F6E3E3C7461736B206E616D653D22E8B4A2E58AA1E5AEA1E689B92220783D223833362220793D22323331222077696474683D22343022206865696768743D2237302220207461736B2D747970653D224E6F726D616C222061737369676E6D656E742D747970653D2241737369676E65652220616C6C6F772D737065636966792D61737369676E65653D2266616C7365223E3C61737369676E65652070726F76696465722D69643D227573657241737369676E656550726F766964657222206E616D653D22E8B4A2E58AA1E4BABAE59198222069643D2231323635313230323030333433323234333231222F3E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E7BB93E69D9F223E3C2F73657175656E63652D666C6F773E3C2F7461736B3E3C656E64206E616D653D22E7BB93E69D9F2220783D223934322220793D22323238222077696474683D22343022206865696768743D2237302220207465726D696E6174653D2274727565223E3C2F656E643E3C2F75666C6F2D70726F636573733E, 'ptws.uflo.xml', 203);
INSERT INTO `uflo_blob` VALUES (309, 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D227574662D38223F3E3C75666C6F2D70726F63657373206E616D653D22E8AFB7E58187E6B581E7A88B22206B65793D226C65617665222073746172742D70726F636573732D75726C3D222F6C656176652F666F726D223E3C7374617274206E616D653D22E5BC80E5A78B2220783D2238352220793D22323239222077696474683D22343022206865696768743D2237302220206576656E742D68616E646C65722D6265616E3D2272656D6F746548616E646C6572222075726C3D222F6C656176652F65646974223E203C6465736372697074696F6E3E3C215B43444154415B687474703A2F2F31302E31372E3234312E33343A383038302F6C656176655D5D3E3C2F6465736372697074696F6E3E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E588A4E696AD223E3C2F73657175656E63652D666C6F773E3C2F73746172743E3C6465636973696F6E206E616D653D22E588A4E696AD2220783D223236312220793D22323330222077696474683D22343022206865696768743D2237302220206465636973696F6E2D747970653D2245787072657373696F6E223E3C65787072657373696F6E3E3C215B43444154415B247B726F6C653D3D2731273F27E699AEE9809AE59198E5B7A5273A726F6C653D3D2732273F27E983A8E997A8E4B8BBE7AEA1273A27E585B6E4BB96277D5D5D3E3C2F65787072657373696F6E3E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E983A8E997A8E4B8BBE7AEA122206E616D653D22E699AEE9809AE59198E5B7A5223E3C2F73657175656E63652D666C6F773E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E983A8E997A8E7BB8FE7908622206E616D653D22E983A8E997A8E4B8BBE7AEA1223E3C2F73657175656E63652D666C6F773E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D224852E5AEA1E6A0B822206E616D653D22E585B6E4BB96223E3C2F73657175656E63652D666C6F773E3C2F6465636973696F6E3E3C7461736B206E616D653D22E983A8E997A8E4B8BBE7AEA12220783D223433352220793D22313534222077696474683D22343022206865696768743D2237302220207461736B2D747970653D224E6F726D616C222061737369676E6D656E742D747970653D2248616E646C6572222061737369676E6D656E742D68616E646C65722D6265616E3D22646570744C6561646572222061737369676E6D656E742D68616E646C65722D6265616E2D646573633D22756E646566696E65642220616C6C6F772D737065636966792D61737369676E65653D2274727565223E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E983A8E997A8E7BB8FE79086223E3C2F73657175656E63652D666C6F773E3C2F7461736B3E3C7461736B206E616D653D22E983A8E997A8E7BB8FE790862220783D223433342220793D22333238222077696474683D22343022206865696768743D2237302220207461736B2D747970653D224E6F726D616C222061737369676E6D656E742D747970653D2248616E646C6572222061737369676E6D656E742D68616E646C65722D6265616E3D22646570744D616E61676572222061737369676E6D656E742D68616E646C65722D6265616E2D646573633D22756E646566696E65642220616C6C6F772D737065636966792D61737369676E65653D2274727565223E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D224852E5AEA1E6A0B8223E3C2F73657175656E63652D666C6F773E3C2F7461736B3E3C7461736B206E616D653D224852E5AEA1E6A0B82220783D223532342220793D22323239222077696474683D22343022206865696768743D2237302220207461736B2D747970653D224E6F726D616C222061737369676E6D656E742D747970653D2241737369676E65652220616C6C6F772D737065636966792D61737369676E65653D2274727565223E3C61737369676E65652070726F76696465722D69643D226465707441737369676E656550726F766964657222206E616D653D22E4BABAE4BA8BE983A8222069643D2231323634383332313037383336393233393035222F3E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E588A4E696ADE5A4A9E695B0223E3C2F73657175656E63652D666C6F773E3C2F7461736B3E3C6465636973696F6E206E616D653D22E588A4E696ADE5A4A9E695B02220783D223636322220793D22323330222077696474683D22343022206865696768743D2237302220206465636973696F6E2D747970653D2245787072657373696F6E223E3C65787072657373696F6E3E3C215B43444154415B247B646179436F756E743E333F27E5A4A7E4BA8E33E5A4A9273A27E5B08FE4BA8EE7AD89E4BA8E33E5A4A9277D5D5D3E3C2F65787072657373696F6E3E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E7BB93E69D9FE6B581E7A88B3122206E616D653D22E5B08FE4BA8EE7AD89E4BA8E33E5A4A9223E3C2F73657175656E63652D666C6F773E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E983A8E997A8E4BC9AE7ADBE22206E616D653D22E5A4A7E4BA8E33E5A4A9223E3C2F73657175656E63652D666C6F773E3C2F6465636973696F6E3E3C7461736B206E616D653D22E983A8E997A8E4BC9AE7ADBE2220783D223832382220793D22323330222077696474683D22343022206865696768743D2237302220207461736B2D747970653D22436F756E7465727369676E222061737369676E6D656E742D747970653D2248616E646C6572222061737369676E6D656E742D68616E646C65722D6265616E3D227573657244657074222061737369676E6D656E742D68616E646C65722D6265616E2D646573633D22756E646566696E65642220616C6C6F772D737065636966792D61737369676E65653D2266616C7365223E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E8B4A2E58AA1E5AEA1E6A0B8223E3C2F73657175656E63652D666C6F773E3C2F7461736B3E3C656E64206E616D653D22E7BB93E69D9FE6B581E7A88B312220783D223832352220793D22333238222077696474683D22343022206865696768743D2237302220207465726D696E6174653D2274727565223E3C2F656E643E3C7461736B206E616D653D22E8B4A2E58AA1E5AEA1E6A0B82220783D223933302220793D22323330222077696474683D22343022206865696768743D2237302220207461736B2D747970653D224E6F726D616C222061737369676E6D656E742D747970653D2241737369676E65652220616C6C6F772D737065636966792D61737369676E65653D2266616C7365223E3C61737369676E65652070726F76696465722D69643D226465707441737369676E656550726F766964657222206E616D653D22E8B4A2E58AA1222069643D2231323634383332313334323938373837383431222F3E3C73657175656E63652D666C6F7720673D222220747970653D226C696E652220746F3D22E7BB93E69D9FE6B581E7A88B31223E3C2F73657175656E63652D666C6F773E3C2F7461736B3E3C2F75666C6F2D70726F636573733E, '请假流程.uflo.xml', 308);

-- ----------------------------
-- Table structure for uflo_calendar
-- ----------------------------
DROP TABLE IF EXISTS `uflo_calendar`;
CREATE TABLE `uflo_calendar`  (
  `ID_` bigint(20) NOT NULL,
  `CATEGORY_ID_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DESC_` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NAME_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TYPE_` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for uflo_calendar_date
-- ----------------------------
DROP TABLE IF EXISTS `uflo_calendar_date`;
CREATE TABLE `uflo_calendar_date`  (
  `ID_` bigint(20) NOT NULL,
  `CALENDAR_DATE_` datetime(0) NULL DEFAULT NULL,
  `CALENDAR_ID_` bigint(20) NULL DEFAULT NULL,
  `DAY_OF_MONTH_` int(11) NULL DEFAULT NULL,
  `DAY_OF_WEEK_` int(11) NULL DEFAULT NULL,
  `MONTH_OF_YEAR_` int(11) NULL DEFAULT NULL,
  `NAME_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `RANGE_END_TIME_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `RANGE_START_TIME_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for uflo_context_property
-- ----------------------------
DROP TABLE IF EXISTS `uflo_context_property`;
CREATE TABLE `uflo_context_property`  (
  `KEY_` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `VALUE_` varchar(35) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`KEY_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of uflo_context_property
-- ----------------------------
INSERT INTO `uflo_context_property` VALUES ('dbid', '300');

-- ----------------------------
-- Table structure for uflo_his_activity
-- ----------------------------
DROP TABLE IF EXISTS `uflo_his_activity`;
CREATE TABLE `uflo_his_activity`  (
  `ID_` bigint(20) NOT NULL,
  `DESCRIPTION_` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NODE_NAME_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PROCESS_ID_` bigint(20) NULL DEFAULT NULL,
  `CREATE_DATE_` datetime(0) NULL DEFAULT NULL,
  `END_DATE_` datetime(0) NULL DEFAULT NULL,
  `HIS_PROCESS_INSTANCE_ID_` bigint(20) NULL DEFAULT NULL,
  `LEAVE_FLOW_NAME_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` bigint(20) NULL DEFAULT NULL,
  `ROOT_PROCESS_INSTANCE_ID_` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of uflo_his_activity
-- ----------------------------
INSERT INTO `uflo_his_activity` VALUES (208, NULL, '开始1', 19, '2020-06-05 14:25:42', '2020-06-05 14:25:43', 207, NULL, 206, 206);
INSERT INTO `uflo_his_activity` VALUES (212, NULL, '主管审批', 19, '2020-06-05 14:25:43', '2020-06-05 14:26:14', 207, NULL, 206, 206);
INSERT INTO `uflo_his_activity` VALUES (216, NULL, '经理审批', 19, '2020-06-05 14:26:15', '2020-06-05 14:26:57', 207, NULL, 206, 206);
INSERT INTO `uflo_his_activity` VALUES (220, NULL, '决策', 19, '2020-06-05 14:26:57', '2020-06-05 14:26:57', 207, '否决', 206, 206);
INSERT INTO `uflo_his_activity` VALUES (221, NULL, '开始1', 19, '2020-06-05 14:26:57', '2020-06-05 14:27:21', 207, NULL, 206, 206);
INSERT INTO `uflo_his_activity` VALUES (225, NULL, '主管审批', 19, '2020-06-05 14:27:21', '2020-06-05 14:27:47', 207, NULL, 206, 206);
INSERT INTO `uflo_his_activity` VALUES (229, NULL, '经理审批', 19, '2020-06-05 14:27:47', '2020-06-05 14:28:09', 207, NULL, 206, 206);
INSERT INTO `uflo_his_activity` VALUES (233, NULL, '决策', 19, '2020-06-05 14:28:09', '2020-06-05 14:28:09', 207, '同意', 206, 206);
INSERT INTO `uflo_his_activity` VALUES (234, NULL, 'HR审批', 19, '2020-06-05 14:28:09', '2020-06-05 14:52:32', 207, NULL, 206, 206);
INSERT INTO `uflo_his_activity` VALUES (238, NULL, 'HR是否同意', 19, '2020-06-05 14:52:32', '2020-06-05 14:52:32', 207, '同意', 206, 206);
INSERT INTO `uflo_his_activity` VALUES (239, NULL, '财务审批', 19, '2020-06-05 14:52:32', NULL, 207, NULL, 206, 206);

-- ----------------------------
-- Table structure for uflo_his_blob
-- ----------------------------
DROP TABLE IF EXISTS `uflo_his_blob`;
CREATE TABLE `uflo_his_blob`  (
  `ID_` bigint(20) NOT NULL,
  `BLOB_VALUE_` longblob NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for uflo_his_process_instance
-- ----------------------------
DROP TABLE IF EXISTS `uflo_his_process_instance`;
CREATE TABLE `uflo_his_process_instance`  (
  `ID_` bigint(20) NOT NULL,
  `BUSINESS_ID_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATE_DATE_` datetime(0) NULL DEFAULT NULL,
  `END_DATE_` datetime(0) NULL DEFAULT NULL,
  `PROCESS_ID_` bigint(20) NULL DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` bigint(20) NULL DEFAULT NULL,
  `PROMOTER_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SUBJECT_` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TAG_` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of uflo_his_process_instance
-- ----------------------------
INSERT INTO `uflo_his_process_instance` VALUES (207, '520', '2020-06-05 14:25:42', NULL, 19, 206, '1', '测试各种流程回退', NULL);

-- ----------------------------
-- Table structure for uflo_his_task
-- ----------------------------
DROP TABLE IF EXISTS `uflo_his_task`;
CREATE TABLE `uflo_his_task`  (
  `ID_` bigint(20) NOT NULL,
  `DESCRIPTION_` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NODE_NAME_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PROCESS_ID_` bigint(20) NULL DEFAULT NULL,
  `ASSIGNEE_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BUSINESS_ID_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATE_DATE_` datetime(0) NULL DEFAULT NULL,
  `DUEDATE_` datetime(0) NULL DEFAULT NULL,
  `END_DATE_` datetime(0) NULL DEFAULT NULL,
  `HIS_PROCESS_INSTANCE_ID_` bigint(20) NULL DEFAULT NULL,
  `OPINION_` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `OWNER_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` bigint(20) NULL DEFAULT NULL,
  `ROOT_PROCESS_INSTANCE_ID_` bigint(20) NULL DEFAULT NULL,
  `STATE_` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SUBJECT_` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TASK_ID_` bigint(20) NULL DEFAULT NULL,
  `TASK_NAME_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TYPE_` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `URL_` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of uflo_his_task
-- ----------------------------
INSERT INTO `uflo_his_task` VALUES (211, NULL, '开始1', 19, '1', '520', '2020-06-05 14:25:42', NULL, '2020-06-05 14:25:43', 207, '提交', '1', 206, 206, 'Completed', '测试各种流程回退', 209, '开始1', 'Normal', NULL);
INSERT INTO `uflo_his_task` VALUES (214, NULL, '主管审批', 19, '1260383191787708418', '520', '2020-06-05 14:25:43', NULL, '2020-06-05 14:26:14', 207, '同意', '1260383191787708418', 206, 206, 'Completed', '测试各种流程回退', 213, '主管审批', 'Normal', NULL);
INSERT INTO `uflo_his_task` VALUES (218, NULL, '经理审批', 19, '1260383281751334914', '520', '2020-06-05 14:26:15', NULL, '2020-06-05 14:26:57', 207, '否决', '1260383281751334914', 206, 206, 'Completed', '测试各种流程回退', 217, '经理审批', 'Normal', NULL);
INSERT INTO `uflo_his_task` VALUES (223, NULL, '开始1', 19, '1', '520', '2020-06-05 14:26:57', NULL, '2020-06-05 14:27:21', 207, '同意', '1', 206, 206, 'Completed', '测试各种流程回退', 222, '开始1', 'Normal', NULL);
INSERT INTO `uflo_his_task` VALUES (227, NULL, '主管审批', 19, '1260383191787708418', '520', '2020-06-05 14:27:21', NULL, '2020-06-05 14:27:47', 207, '同意', '1260383191787708418', 206, 206, 'Completed', '测试各种流程回退', 226, '主管审批', 'Normal', NULL);
INSERT INTO `uflo_his_task` VALUES (231, NULL, '经理审批', 19, '1260383281751334914', '520', '2020-06-05 14:27:47', NULL, '2020-06-05 14:28:09', 207, '同意', '1260383281751334914', 206, 206, 'Completed', '测试各种流程回退', 230, '经理审批', 'Normal', NULL);
INSERT INTO `uflo_his_task` VALUES (236, NULL, 'HR审批', 19, '1260383323119755265', '520', '2020-06-05 14:28:09', NULL, '2020-06-05 14:52:32', 207, '同意', '1260383323119755265', 206, 206, 'Completed', '测试各种流程回退', 235, 'HR审批', 'Normal', NULL);

-- ----------------------------
-- Table structure for uflo_his_variable
-- ----------------------------
DROP TABLE IF EXISTS `uflo_his_variable`;
CREATE TABLE `uflo_his_variable`  (
  `ID_` bigint(20) NOT NULL,
  `HIS_PROCESS_INSTANCE_ID_` bigint(20) NULL DEFAULT NULL,
  `KEY_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `VALUE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TYPE_` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for uflo_job_heartbeat
-- ----------------------------
DROP TABLE IF EXISTS `uflo_job_heartbeat`;
CREATE TABLE `uflo_job_heartbeat`  (
  `ID_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `DATE_` datetime(0) NULL DEFAULT NULL,
  `INSTANCE_NAME_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for uflo_process
-- ----------------------------
DROP TABLE IF EXISTS `uflo_process`;
CREATE TABLE `uflo_process`  (
  `ID_` bigint(20) NOT NULL,
  `CATEGORY_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CATEGORY_ID_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATE_DATE_` datetime(0) NULL DEFAULT NULL,
  `DESCRIPTION_` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `EFFECT_DATE_` datetime(0) NULL DEFAULT NULL,
  `KEY_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NAME_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `START_PROCESS_URL_` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `VERSION_` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of uflo_process
-- ----------------------------
INSERT INTO `uflo_process` VALUES (1, NULL, NULL, '2020-06-05 10:33:45', NULL, NULL, 'leave', '请假流程', '/leave/form', 1);
INSERT INTO `uflo_process` VALUES (19, NULL, NULL, '2020-06-05 13:27:18', NULL, NULL, 'ptws', 'ptws', NULL, 5);

-- ----------------------------
-- Table structure for uflo_process_instance
-- ----------------------------
DROP TABLE IF EXISTS `uflo_process_instance`;
CREATE TABLE `uflo_process_instance`  (
  `ID_` bigint(20) NOT NULL,
  `BUSINESS_ID_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATE_DATE_` datetime(0) NULL DEFAULT NULL,
  `CURRENT_NODE_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CURRENT_TASK_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `HIS_PROCESS_INSTANCE_ID_` bigint(20) NULL DEFAULT NULL,
  `PARALLEL_INSTANCE_COUNT_` int(11) NULL DEFAULT NULL,
  `PARENT_ID_` bigint(20) NULL DEFAULT NULL,
  `PROCESS_ID_` bigint(20) NULL DEFAULT NULL,
  `PROMOTER_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ROOT_ID_` bigint(20) NULL DEFAULT NULL,
  `STATE_` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SUBJECT_` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TAG_` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of uflo_process_instance
-- ----------------------------
INSERT INTO `uflo_process_instance` VALUES (206, '520', '2020-06-05 14:25:42', '财务审批', '财务审批', 207, 0, 0, 19, '1', 206, 'Start', '测试各种流程回退', NULL);

-- ----------------------------
-- Table structure for uflo_task
-- ----------------------------
DROP TABLE IF EXISTS `uflo_task`;
CREATE TABLE `uflo_task`  (
  `ID_` bigint(20) NOT NULL,
  `DESCRIPTION_` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NODE_NAME_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PROCESS_ID_` bigint(20) NULL DEFAULT NULL,
  `ASSIGNEE_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BUSINESS_ID_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `COUNTERSIGN_COUNT_` int(11) NULL DEFAULT NULL,
  `CREATE_DATE_` datetime(0) NULL DEFAULT NULL,
  `DATE_UNIT_` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DUE_ACTION_DATE_` datetime(0) NULL DEFAULT NULL,
  `DUEDATE_` datetime(0) NULL DEFAULT NULL,
  `END_DATE_` datetime(0) NULL DEFAULT NULL,
  `OPINION_` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `OWNER_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PREV_STATE_` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PREV_TASK_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PRIORITY_` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` bigint(20) NULL DEFAULT NULL,
  `PROGRESS_` int(11) NULL DEFAULT NULL,
  `ROOT_PROCESS_INSTANCE_ID_` bigint(20) NULL DEFAULT NULL,
  `STATE_` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SUBJECT_` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TASK_NAME_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TYPE_` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `URL_` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of uflo_task
-- ----------------------------
INSERT INTO `uflo_task` VALUES (209, NULL, '开始1', 19, '1', '520', 0, '2020-06-05 14:25:42', NULL, NULL, NULL, '2020-06-05 14:25:43', '提交', '1', NULL, '开始1', NULL, 206, 100, 206, 'Completed', '测试各种流程回退', '开始1', 'Normal', NULL);
INSERT INTO `uflo_task` VALUES (213, NULL, '主管审批', 19, '1260383191787708418', '520', 0, '2020-06-05 14:25:43', NULL, NULL, NULL, '2020-06-05 14:26:14', '同意', '1260383191787708418', NULL, '开始1', NULL, 206, 100, 206, 'Completed', '测试各种流程回退', '主管审批', 'Normal', NULL);
INSERT INTO `uflo_task` VALUES (217, NULL, '经理审批', 19, '1260383281751334914', '520', 0, '2020-06-05 14:26:15', NULL, NULL, NULL, '2020-06-05 14:26:57', '否决', '1260383281751334914', NULL, '主管审批', NULL, 206, 100, 206, 'Completed', '测试各种流程回退', '经理审批', 'Normal', NULL);
INSERT INTO `uflo_task` VALUES (222, NULL, '开始1', 19, '1', '520', 0, '2020-06-05 14:26:57', NULL, NULL, NULL, '2020-06-05 14:27:21', '同意', '1', NULL, '经理审批', NULL, 206, 100, 206, 'Completed', '测试各种流程回退', '开始1', 'Normal', NULL);
INSERT INTO `uflo_task` VALUES (226, NULL, '主管审批', 19, '1260383191787708418', '520', 0, '2020-06-05 14:27:21', NULL, NULL, NULL, '2020-06-05 14:27:47', '同意', '1260383191787708418', NULL, '开始1', NULL, 206, 100, 206, 'Completed', '测试各种流程回退', '主管审批', 'Normal', NULL);
INSERT INTO `uflo_task` VALUES (230, NULL, '经理审批', 19, '1260383281751334914', '520', 0, '2020-06-05 14:27:47', NULL, NULL, NULL, '2020-06-05 14:28:09', '同意', '1260383281751334914', NULL, '主管审批', NULL, 206, 100, 206, 'Completed', '测试各种流程回退', '经理审批', 'Normal', NULL);
INSERT INTO `uflo_task` VALUES (235, NULL, 'HR审批', 19, '1260383323119755265', '520', 0, '2020-06-05 14:28:09', NULL, NULL, NULL, '2020-06-05 14:52:32', '同意', '1260383323119755265', NULL, '经理审批', NULL, 206, 100, 206, 'Completed', '测试各种流程回退', 'HR审批', 'Normal', NULL);
INSERT INTO `uflo_task` VALUES (240, NULL, '财务审批', 19, '1265120200343224321', '520', 0, '2020-06-05 14:52:32', NULL, NULL, NULL, NULL, NULL, '1265120200343224321', NULL, 'HR审批', NULL, 206, NULL, 206, 'Created', '测试各种流程回退', '财务审批', 'Normal', NULL);

-- ----------------------------
-- Table structure for uflo_task_appointor
-- ----------------------------
DROP TABLE IF EXISTS `uflo_task_appointor`;
CREATE TABLE `uflo_task_appointor`  (
  `ID_` bigint(20) NOT NULL,
  `APPOINTOR_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `APPOINTOR_NODE_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `OWNER_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` bigint(20) NULL DEFAULT NULL,
  `TASK_NODE_NAME_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of uflo_task_appointor
-- ----------------------------
INSERT INTO `uflo_task_appointor` VALUES (210, '1', '开始1', '1260383191787708418', 206, '主管审批');

-- ----------------------------
-- Table structure for uflo_task_participator
-- ----------------------------
DROP TABLE IF EXISTS `uflo_task_participator`;
CREATE TABLE `uflo_task_participator`  (
  `ID_` bigint(20) NOT NULL,
  `TASK_ID_` bigint(20) NOT NULL,
  `USER_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE,
  INDEX `FKpqe63u3gnbwpjhvf8996md6ip`(`TASK_ID_`) USING BTREE,
  CONSTRAINT `FKpqe63u3gnbwpjhvf8996md6ip` FOREIGN KEY (`TASK_ID_`) REFERENCES `uflo_task` (`ID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for uflo_task_reminder
-- ----------------------------
DROP TABLE IF EXISTS `uflo_task_reminder`;
CREATE TABLE `uflo_task_reminder`  (
  `ID_` bigint(20) NOT NULL,
  `CRON_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PROCESS_ID_` bigint(20) NULL DEFAULT NULL,
  `REMINDER_HANDLER_BEAN_` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `START_DATE_` datetime(0) NULL DEFAULT NULL,
  `TASK_ID_` bigint(20) NULL DEFAULT NULL,
  `TASK_NODE_NAME_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REMINDER_TYPE_` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for uflo_variable
-- ----------------------------
DROP TABLE IF EXISTS `uflo_variable`;
CREATE TABLE `uflo_variable`  (
  `TYPE_` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ID_` bigint(20) NOT NULL,
  `KEY_` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` bigint(20) NULL DEFAULT NULL,
  `ROOT_PROCESS_INSTANCE_ID_` bigint(20) NULL DEFAULT NULL,
  `BLOB_ID_` bigint(20) NULL DEFAULT NULL,
  `BOOLEAN_VALUE_` bit(1) NULL DEFAULT NULL,
  `BYTE_VALUE_` tinyint(4) NULL DEFAULT NULL,
  `CHARACTER_VALUE_` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DATE_VALUE_` datetime(0) NULL DEFAULT NULL,
  `DOUBLE_VALUE_` double NULL DEFAULT NULL,
  `FLOAT_VALUE_` float NULL DEFAULT NULL,
  `INTEGER_VALUE_` int(11) NULL DEFAULT NULL,
  `LONG_VALUE_` bigint(20) NULL DEFAULT NULL,
  `SHORT_VALUE_` smallint(6) NULL DEFAULT NULL,
  `STRING_VALUE_` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID_`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of uflo_variable
-- ----------------------------
INSERT INTO `uflo_variable` VALUES ('Integer', 237, 'agree', 206, 206, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
