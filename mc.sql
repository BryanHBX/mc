/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50553
 Source Host           : localhost:3306
 Source Schema         : mc

 Target Server Type    : MySQL
 Target Server Version : 50553
 File Encoding         : 65001

 Date: 12/04/2018 23:52:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_member
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `u_identity` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证',
  `u_pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `u_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `u_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `u_wxid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信ID',
  `u_status` tinyint(4) NULL DEFAULT NULL COMMENT '绑定 (1), 解绑 (0)',
  `u_type` tinyint(4) NULL DEFAULT NULL COMMENT '机构 (1), 个人 (0)',
  `u_role` tinyint(4) NULL DEFAULT NULL COMMENT '机构/个人管理员 (1), 普通用户 (2), 系统管理员 (3)',
  `school_id` int(11) NULL DEFAULT NULL COMMENT '学校ID',
  `u_lastLoginTime` datetime NULL DEFAULT NULL,
  `u_creationTime` datetime NULL DEFAULT NULL,
  `u_lastUpdateTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_member
-- ----------------------------
INSERT INTO `t_member` VALUES (2, 'string', '$2a$10$RS35Bi3CO2D0PVNCf1nlj.pabFTxnUwGGoQaPMaMc7efceYKsNsDC', '18602889661', 'string', 'string', 1, 1, 0, 1, NULL, '2018-04-09 23:46:07', '2018-04-09 23:46:07');

-- ----------------------------
-- Table structure for t_member_role
-- ----------------------------
DROP TABLE IF EXISTS `t_member_role`;
CREATE TABLE `t_member_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` int(11) NOT NULL,
  `role` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_school
-- ----------------------------
DROP TABLE IF EXISTS `t_school`;
CREATE TABLE `t_school`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `s_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_contact` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_status` tinyint(4) NULL DEFAULT NULL,
  `s_createTime` datetime NULL DEFAULT NULL,
  `s_lastUpdateTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_school
-- ----------------------------
INSERT INTO `t_school` VALUES (1, 'string', 'string', 'string', 0, NULL, NULL);
INSERT INTO `t_school` VALUES (2, 'string', 'string', 'string', 0, '2018-04-08 22:46:02', NULL);
INSERT INTO `t_school` VALUES (3, 'string', 'string12', 'string12', 0, '2018-04-08 22:46:17', '2018-04-08 22:50:19');

-- ----------------------------
-- Table structure for t_school_contract
-- ----------------------------
DROP TABLE IF EXISTS `t_school_contract`;
CREATE TABLE `t_school_contract`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contract_no` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '合同编号',
  `student_id` int(11) NOT NULL COMMENT '学生ID',
  `consult_id` int(11) NOT NULL COMMENT '咨询师ID',
  `contract_type` tinyint(4) NOT NULL COMMENT '报名类型',
  `period_enroll` double(4, 0) NOT NULL COMMENT '报名课时',
  `period_free` double(4, 0) NULL DEFAULT NULL COMMENT '赠送课时',
  `contract_price` double(4, 0) NULL DEFAULT NULL COMMENT '签约总价',
  `discount_price` double(4, 0) NULL DEFAULT NULL COMMENT '优惠',
  `total_price` double(4, 0) NOT NULL COMMENT '实收金额',
  `cfg_level` int(4) NULL DEFAULT NULL COMMENT '学生年段',
  `cfg_subLevel` int(4) NULL DEFAULT NULL COMMENT '细分年段',
  `cfg_course` int(4) NULL DEFAULT NULL COMMENT '课程名称',
  `cfg_subCourse` int(4) NULL DEFAULT NULL COMMENT '课程子类',
  `contract_date` date NOT NULL COMMENT '签约日期',
  `creationTime` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `recorder` int(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_school_contract
-- ----------------------------
INSERT INTO `t_school_contract` VALUES (1, 'fea', 1, 12, 2, 12, 0, 12, 0, 12, 41, 43, 18, 21, '2018-04-24', '2018-04-10 22:50:53', NULL, NULL);

-- ----------------------------
-- Table structure for t_school_invoice
-- ----------------------------
DROP TABLE IF EXISTS `t_school_invoice`;
CREATE TABLE `t_school_invoice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contract_id` int(11) NOT NULL COMMENT '合同ID',
  `invoice_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收据编号',
  `fee_amount` double NOT NULL COMMENT '支付金额',
  `fee_type` tinyint(4) NULL DEFAULT NULL COMMENT '支付方式',
  `creationTime` datetime NULL DEFAULT NULL,
  `updateTime` datetime NULL DEFAULT NULL,
  `recorder` int(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_school_invoice
-- ----------------------------
INSERT INTO `t_school_invoice` VALUES (1, 1, '124', 123, 1, '2018-04-10 22:50:53', NULL, NULL);
INSERT INTO `t_school_invoice` VALUES (2, 1, '1241', 24, 3, '2018-04-10 22:50:53', NULL, NULL);
INSERT INTO `t_school_invoice` VALUES (3, 1, '123', 231, 4, '2018-04-10 22:50:53', NULL, NULL);

-- ----------------------------
-- Table structure for t_school_product
-- ----------------------------
DROP TABLE IF EXISTS `t_school_product`;
CREATE TABLE `t_school_product`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `p_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程名称',
  `p_type` int(11) NULL DEFAULT NULL COMMENT '课程类别',
  `p_period` double(11, 0) NOT NULL COMMENT '课时',
  `p_parent` int(11) NULL DEFAULT NULL COMMENT '父课程ID',
  `school_id` int(11) NOT NULL COMMENT '学校ID',
  `p_createTime` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `p_lastUpdateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `school_id`(`school_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_school_student
-- ----------------------------
DROP TABLE IF EXISTS `t_school_student`;
CREATE TABLE `t_school_student`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `s_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学生姓名',
  `s_wxid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信ID',
  `s_contactName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系人姓名',
  `s_contactRelation` tinyint(4) NOT NULL COMMENT '联系人关系',
  `s_contactPhone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系人方式',
  `s_meto` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其他备注',
  `consultant_id` int(11) NOT NULL COMMENT '咨询师ID',
  `cfg_level` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cfg_subLevel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cfg_course` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cfg_subCourse` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `creationTime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updateTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_school_student
-- ----------------------------
INSERT INTO `t_school_student` VALUES (1, '12142', 'fewa', '142141', 3, '12421', '', 12, '41', '43', '18', '21', NULL, NULL);

-- ----------------------------
-- Table structure for t_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_config`;
CREATE TABLE `t_sys_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `c_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `c_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c_single` tinyint(4) NULL DEFAULT NULL,
  `c_parent` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 68 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_config
-- ----------------------------
INSERT INTO `t_sys_config` VALUES (26, 'C1', '拉丁舞', 0, 22);
INSERT INTO `t_sys_config` VALUES (27, 'C2', '民族舞', 0, 22);
INSERT INTO `t_sys_config` VALUES (28, 'C3', '街舞', 0, 22);
INSERT INTO `t_sys_config` VALUES (21, '种类', '种类', NULL, 18);
INSERT INTO `t_sys_config` VALUES (30, 'C5', '机械舞', 0, 22);
INSERT INTO `t_sys_config` VALUES (65, 'C_SUBJECT_5', '社会', 0, 48);
INSERT INTO `t_sys_config` VALUES (16, 'C_CRS_TYPE', '课程种类', 0, NULL);
INSERT INTO `t_sys_config` VALUES (63, 'C_SUBJECT_3', '英语', 0, 48);
INSERT INTO `t_sys_config` VALUES (18, '艺术体育', '艺术体育', 0, 16);
INSERT INTO `t_sys_config` VALUES (32, 'M_1', '流行音乐', 0, 31);
INSERT INTO `t_sys_config` VALUES (34, 'M_3', '古典音乐', 0, 31);
INSERT INTO `t_sys_config` VALUES (62, 'C_SUBJECT_2', '语文', 0, 48);
INSERT INTO `t_sys_config` VALUES (43, 'STU_LEVEL_1_1', '小一', 0, 41);
INSERT INTO `t_sys_config` VALUES (64, 'C_SUBJECT_4', '科学', 0, 48);
INSERT INTO `t_sys_config` VALUES (40, 'C_STU_LEVEL', '学生年段', NULL, NULL);
INSERT INTO `t_sys_config` VALUES (44, 'STU_LEVEL_1_2', '小二', 0, 41);
INSERT INTO `t_sys_config` VALUES (45, 'STU_LEVEL_2_1', '早一', 0, 42);
INSERT INTO `t_sys_config` VALUES (46, 'STU_LEVEL_2_2C_SUBJECT', '早二', 0, 42);
INSERT INTO `t_sys_config` VALUES (47, 'C_GRADE', '年级', NULL, NULL);
INSERT INTO `t_sys_config` VALUES (48, 'C_SUBJECT', '科目', NULL, NULL);
INSERT INTO `t_sys_config` VALUES (50, 'C_GRADE_1', '小一', 0, 47);
INSERT INTO `t_sys_config` VALUES (51, 'C_GRADE_2', '小二', 0, 47);
INSERT INTO `t_sys_config` VALUES (52, 'C_GRADE_3', '小三', 0, 47);
INSERT INTO `t_sys_config` VALUES (53, 'C_GRADE_4', '小四', 0, 47);
INSERT INTO `t_sys_config` VALUES (54, 'C_GRADE_5', '小六', 0, 47);
INSERT INTO `t_sys_config` VALUES (55, 'C_GRADE_6', '初一', 0, 47);
INSERT INTO `t_sys_config` VALUES (56, 'C_GRADE_7', '初二', 0, 47);
INSERT INTO `t_sys_config` VALUES (57, 'C_GRADE_8', '初三', 0, 47);
INSERT INTO `t_sys_config` VALUES (58, 'C_GRADE_9', '高一', 0, 47);
INSERT INTO `t_sys_config` VALUES (59, 'C_GRADE_10', '高二', 0, 47);
INSERT INTO `t_sys_config` VALUES (60, 'C_GRADE_11', '高三', 0, 47);
INSERT INTO `t_sys_config` VALUES (61, 'C_SUBJECT_1', '数学', 0, 48);
INSERT INTO `t_sys_config` VALUES (66, 'C_SUBJECT_6', '物理', 0, 48);
INSERT INTO `t_sys_config` VALUES (67, 'C_SUBJECT_7', '化学', 0, 48);

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_alias` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_visible` tinyint(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
INSERT INTO `t_sys_role` VALUES (1, '教师', 'ROLE_TEACHER', 1);
INSERT INTO `t_sys_role` VALUES (2, '咨询师', 'ROLE_CONSULTANT', 1);
INSERT INTO `t_sys_role` VALUES (3, '教务员', 'ROLE_ACADEMIC_DEAN', 1);
INSERT INTO `t_sys_role` VALUES (4, '财务', 'ROLE_TREASURER', 1);
INSERT INTO `t_sys_role` VALUES (6, '超级管理员', 'ROLE_SUPERUSER', 0);
INSERT INTO `t_sys_role` VALUES (5, '管理员', 'ROLE_ADMINISTRATOR', 0);

SET FOREIGN_KEY_CHECKS = 1;
