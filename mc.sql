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

 Date: 14/04/2018 22:46:23
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
  `u_course` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所授课程',
  `u_grade` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年级',
  `u_subject` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '科目',
  `u_lastLoginTime` datetime NULL DEFAULT NULL,
  `u_creationTime` datetime NULL DEFAULT NULL,
  `u_lastUpdateTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_member
-- ----------------------------
INSERT INTO `t_member` VALUES (2, 'string', '$2a$10$RS35Bi3CO2D0PVNCf1nlj.pabFTxnUwGGoQaPMaMc7efceYKsNsDC', '18602889661', 'string', 'string', 1, 1, 0, 1, NULL, NULL, NULL, NULL, '2018-04-09 23:46:07', '2018-04-09 23:46:07');
INSERT INTO `t_member` VALUES (3, '12421521', '$2a$10$0mZhTKZi0NHNX7ePhk6qoOXAfK5.xvSVkDFPQEffjwaaEBoa6GqC6', '12421521', 'zx', '1213', 1, 1, 2, 1, '1,2', '51,54,55', '63,66,67', NULL, '2018-04-14 20:23:36', '2018-04-14 20:23:36');
INSERT INTO `t_member` VALUES (4, 'xza', '$2a$10$sV5zkMnfc5BNWvsYrImgg.DRxlY7P0caWTl6.Do0tQwPFpzjE9QGC', '123', 'ewa', 'fewa', 1, 1, 2, 1, '2,3', '54,55,56,57', '63,66,67', NULL, '2018-04-14 20:27:44', '2018-04-14 20:27:44');
INSERT INTO `t_member` VALUES (5, '5231', '$2a$10$ppqy3f.nLs9lsBmujz/QnuFRo6.rIlpwOCW7I2sJOoH62IDakT5.i', '421', '在d', 'cx', 1, 1, 2, 1, '2,3', '54,55,56,57', '63,66,67', NULL, '2018-04-14 20:35:11', '2018-04-14 20:35:11');
INSERT INTO `t_member` VALUES (6, '36252319870319081x', '$2a$10$J/ZWM05cjrSL6.VTNqzWl.EQCgG186Cwo1sNZ6wPHQys.CFSAXnLa', '18602889661', '321', '3212', 1, 1, 2, 1, '2,3', '52,55', '63,67', NULL, '2018-04-14 20:59:01', '2018-04-14 20:59:01');
INSERT INTO `t_member` VALUES (7, '36252319870319021x', '$2a$10$P1xfqcRlfgBts9DTO/0fye2/z/ONhoklwxyZ1y4E2bdCvIzsDFPE6', '18602889161', '321', '3x1', 1, 1, 2, 1, '2,3', '52,55', '63,67', NULL, '2018-04-14 20:59:21', '2018-04-14 20:59:21');
INSERT INTO `t_member` VALUES (8, '36252319870319021x', '$2a$10$dERnjbf08v.1zlF641qL5OOwdZoTO7nWM0MfFE70G.w49Pxy33N.q', '18602889161', '321', '3x121', 1, 1, 2, 1, '2,3', '52,55', '63,67', NULL, '2018-04-14 20:59:44', '2018-04-14 20:59:44');
INSERT INTO `t_member` VALUES (9, '36252319870329021x', '$2a$10$Ay/DJaYagpcnMz/aOWGFsuQUnijvZZJwYxdZNmIzSz9Ne3RIaNfva', '18602889261', '321', '3x121', 1, 1, 2, 1, '2,3', '52,55', '63,67', NULL, '2018-04-14 20:59:57', '2018-04-14 20:59:57');
INSERT INTO `t_member` VALUES (10, '322523198703190812', '$2a$10$4YekFX9/SbygFyfdk2N35ulgUHr4tOrXN5nlOqCZsoKwXwxGSG2t6', '18612889661', '321', '3x12112', 1, 1, 2, 1, '2', '50,54', '65,63', NULL, '2018-04-14 21:00:21', '2018-04-14 21:00:21');
INSERT INTO `t_member` VALUES (11, '352523198703190812', '$2a$10$VWS6cuZ8Uij53i/oS4sTK.Vb55OyzdsbiMJUTqXTnYkoMIRQ7KZ2.', '18632889661', '31', '3x121122', 1, 1, 2, 1, '6', '53', '64', NULL, '2018-04-14 21:05:04', '2018-04-14 21:05:04');

-- ----------------------------
-- Table structure for t_member_role
-- ----------------------------
DROP TABLE IF EXISTS `t_member_role`;
CREATE TABLE `t_member_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` int(11) NOT NULL,
  `role` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_member_role
-- ----------------------------
INSERT INTO `t_member_role` VALUES (2, 5, 'ROLE_ACADEMIC_DEAN');
INSERT INTO `t_member_role` VALUES (3, 5, 'ROLE_TREASURER');
INSERT INTO `t_member_role` VALUES (4, 6, 'ROLE_TEACHER');
INSERT INTO `t_member_role` VALUES (5, 7, 'ROLE_TEACHER');
INSERT INTO `t_member_role` VALUES (6, 8, 'ROLE_TEACHER');
INSERT INTO `t_member_role` VALUES (7, 9, 'ROLE_TEACHER');
INSERT INTO `t_member_role` VALUES (8, 10, 'ROLE_TEACHER');
INSERT INTO `t_member_role` VALUES (9, 11, 'ROLE_TREASURER');

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
  `s_province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_lat` double NULL DEFAULT NULL,
  `s_lng` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_school
-- ----------------------------
INSERT INTO `t_school` VALUES (1, '测试', '四川省成都市龙泉驿区', '1231', 0, NULL, '2018-04-14 15:46:50', '四川省', '成都市', 30.576657, 104.303412);
INSERT INTO `t_school` VALUES (2, 'string', 'string', 'string', 0, '2018-04-08 22:46:02', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_school` VALUES (3, 'string', 'string12', 'string12', 0, '2018-04-08 22:46:17', '2018-04-08 22:50:19', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for t_school_contract
-- ----------------------------
DROP TABLE IF EXISTS `t_school_contract`;
CREATE TABLE `t_school_contract`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contract_no` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '合同编号',
  `student_id` int(11) NOT NULL COMMENT '学生ID',
  `consult_id` int(11) NOT NULL COMMENT '咨询师ID',
  `school_id` int(11) NOT NULL COMMENT '学校ID',
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
INSERT INTO `t_school_contract` VALUES (1, 'fea', 1, 12, 0, 2, 12, 0, 12, 0, 12, 41, 43, 18, 21, '2018-04-24', '2018-04-10 22:50:53', NULL, NULL);

-- ----------------------------
-- Table structure for t_school_invoice
-- ----------------------------
DROP TABLE IF EXISTS `t_school_invoice`;
CREATE TABLE `t_school_invoice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contract_id` int(11) NOT NULL COMMENT '合同ID',
  `school_id` int(11) NOT NULL COMMENT '学校ID',
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
INSERT INTO `t_school_invoice` VALUES (1, 1, 0, '124', 123, 1, '2018-04-10 22:50:53', NULL, NULL);
INSERT INTO `t_school_invoice` VALUES (2, 1, 0, '1241', 24, 3, '2018-04-10 22:50:53', NULL, NULL);
INSERT INTO `t_school_invoice` VALUES (3, 1, 0, '123', 231, 4, '2018-04-10 22:50:53', NULL, NULL);

-- ----------------------------
-- Table structure for t_school_product
-- ----------------------------
DROP TABLE IF EXISTS `t_school_product`;
CREATE TABLE `t_school_product`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `p_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程名称',
  `p_type` int(11) NULL DEFAULT NULL COMMENT '课程类别',
  `p_period` double(11, 0) NULL DEFAULT NULL COMMENT '课时',
  `p_parent` int(11) NULL DEFAULT NULL COMMENT '父课程ID',
  `school_id` int(11) NOT NULL COMMENT '学校ID',
  `p_createTime` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `p_lastUpdateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `school_id`(`school_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_school_product
-- ----------------------------
INSERT INTO `t_school_product` VALUES (1, '一对二', 68, 1, NULL, 1, '2018-04-14 10:36:01', '2018-04-14 12:16:55');
INSERT INTO `t_school_product` VALUES (2, '测试2', 18, 1, NULL, 1, '2018-04-14 11:39:05', NULL);
INSERT INTO `t_school_product` VALUES (3, '一对一', 68, 1, NULL, 1, '2018-04-14 11:40:25', NULL);
INSERT INTO `t_school_product` VALUES (5, '子类2fe', NULL, NULL, 2, 1, '2018-04-14 12:35:11', '2018-04-14 12:42:48');
INSERT INTO `t_school_product` VALUES (6, '一对三', 68, 1, NULL, 1, '2018-04-14 12:40:56', NULL);

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
) ENGINE = MyISAM AUTO_INCREMENT = 69 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_sys_config
-- ----------------------------
INSERT INTO `t_sys_config` VALUES (26, 'C1', '拉丁舞', 0, 22);
INSERT INTO `t_sys_config` VALUES (27, 'C2', '民族舞', 0, 22);
INSERT INTO `t_sys_config` VALUES (28, 'C3', '街舞', 0, 22);
INSERT INTO `t_sys_config` VALUES (21, '种类', '种类', NULL, 18);
INSERT INTO `t_sys_config` VALUES (30, 'C5', '机械舞', 0, 22);
INSERT INTO `t_sys_config` VALUES (65, 'C_SUBJECT_5', '社会', 0, 48);
INSERT INTO `t_sys_config` VALUES (16, 'C_COURSE_TYPE', '课程种类', 0, NULL);
INSERT INTO `t_sys_config` VALUES (63, 'C_SUBJECT_3', '英语', 0, 48);
INSERT INTO `t_sys_config` VALUES (18, 'C_COURSE_TYPE_1', '艺术体育', 0, 16);
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
INSERT INTO `t_sys_config` VALUES (68, 'C_COURSE_TYPE_2', '学科辅导', 0, 16);

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
