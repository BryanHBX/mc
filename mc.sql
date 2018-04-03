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

 Date: 03/04/2018 23:43:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_contract
-- ----------------------------
DROP TABLE IF EXISTS `t_contract`;
CREATE TABLE `t_contract`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contract_no` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `student_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_invoice
-- ----------------------------
DROP TABLE IF EXISTS `t_invoice`;
CREATE TABLE `t_invoice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contract_id` int(11) NULL DEFAULT NULL,
  `invoice_no` int(11) NULL DEFAULT NULL,
  `pay_money` double NULL DEFAULT NULL,
  `pay_type` tinyint(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Table structure for t_member
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `u_pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `u_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `u_status` tinyint(4) NULL DEFAULT NULL,
  `u_lastLoginTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_member_role
-- ----------------------------
DROP TABLE IF EXISTS `t_member_role`;
CREATE TABLE `t_member_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_school
-- ----------------------------
DROP TABLE IF EXISTS `t_school`;
CREATE TABLE `t_school`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `school_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `school_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `school_contact` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` tinyint(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_school
-- ----------------------------
INSERT INTO `t_school` VALUES (1, 'string', 'string', 'string', 0);

-- ----------------------------
-- Table structure for t_school_product
-- ----------------------------
DROP TABLE IF EXISTS `t_school_product`;
CREATE TABLE `t_school_product`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `p_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `p_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `p_label` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `p_parent` int(255) NULL DEFAULT NULL,
  `school_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `school_id`(`school_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_student
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `s_name` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `s_wxid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `s_contactName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `s_contactType` tinyint(4) NOT NULL,
  `s_contactPhone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `s_meto` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `memberId` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
