/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50553
Source Host           : localhost:3306
Source Database       : mc

Target Server Type    : MYSQL
Target Server Version : 50553
File Encoding         : 65001

Date: 2018-04-26 00:09:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_member`
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `u_identity` varchar(100) DEFAULT NULL COMMENT '身份证',
  `u_pwd` varchar(255) NOT NULL,
  `u_phone` varchar(255) NOT NULL,
  `u_name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `u_wxid` varchar(255) DEFAULT NULL COMMENT '微信ID',
  `u_status` tinyint(4) DEFAULT NULL COMMENT '绑定 (1), 解绑 (0)',
  `u_type` tinyint(4) DEFAULT NULL COMMENT '机构 (1), 个人 (0)',
  `u_role` tinyint(4) DEFAULT NULL COMMENT '机构/个人管理员 (1), 普通用户 (2), 系统管理员 (3)',
  `school_id` int(11) DEFAULT NULL COMMENT '学校ID',
  `u_course` varchar(255) DEFAULT NULL COMMENT '所授课程',
  `u_grade` varchar(255) DEFAULT NULL COMMENT '年级',
  `u_subject` varchar(255) DEFAULT NULL COMMENT '科目',
  `u_lastLoginTime` datetime DEFAULT NULL,
  `u_creationTime` datetime DEFAULT NULL,
  `u_lastUpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_member
-- ----------------------------
INSERT INTO `t_member` VALUES ('13', '352523198703190813', '$2a$10$BLEFtlzQ60z3U16Wy7JCv.G151Ms0BGjcj9lw9FIDyAKvQEwoMssu', '18602889662', '李雷', 'xi11221', '0', '1', '1', '2', null, null, null, null, null, null);
INSERT INTO `t_member` VALUES ('12', '352523198703190811', '$2a$10$BLEFtlzQ60z3U16Wy7JCv.G151Ms0BGjcj9lw9FIDyAKvQEwoMssu', '18602889661', '李四', '3x1211221', '1', '1', '1', '2', null, null, null, null, null, null);
INSERT INTO `t_member` VALUES ('11', '352523198703190812', '$2a$10$BLEFtlzQ60z3U16Wy7JCv.G151Ms0BGjcj9lw9FIDyAKvQEwoMssu', '18632889661', '张三', '3x121122', '0', '1', '2', '1', '6', '53,57', '67', '0000-00-00 00:00:00', '2018-04-14 21:05:04', '2018-04-15 20:11:06');

-- ----------------------------
-- Table structure for `t_member_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_member_role`;
CREATE TABLE `t_member_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` int(11) NOT NULL,
  `role` varchar(120) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_member_role
-- ----------------------------
INSERT INTO `t_member_role` VALUES ('12', '11', 'ROLE_TEACHER');
INSERT INTO `t_member_role` VALUES ('11', '11', 'ROLE_CONSULTANT');
INSERT INTO `t_member_role` VALUES ('13', '12', 'ROLE_ADMINISTRATOR');

-- ----------------------------
-- Table structure for `t_school`
-- ----------------------------
DROP TABLE IF EXISTS `t_school`;
CREATE TABLE `t_school` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `s_name` varchar(255) DEFAULT NULL,
  `s_address` varchar(255) DEFAULT NULL,
  `s_contact` varchar(255) DEFAULT NULL,
  `s_status` tinyint(4) DEFAULT NULL,
  `s_createTime` datetime DEFAULT NULL,
  `s_lastUpdateTime` datetime DEFAULT NULL,
  `s_province` varchar(255) DEFAULT NULL,
  `s_city` varchar(255) DEFAULT NULL,
  `s_lat` double DEFAULT NULL,
  `s_lng` double DEFAULT NULL,
  `s_parentId` int(11) DEFAULT NULL COMMENT '父校区ID',
  `s_supervisorflag` tinyint(4) DEFAULT '0' COMMENT '是否支持学管师',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_school
-- ----------------------------
INSERT INTO `t_school` VALUES ('1', '测试', '四川省绵阳市涪城区建设街附14', '1231', '0', null, '2018-04-17 23:51:08', '四川省', '绵阳市', '31.459701', '104.765141', null, null);
INSERT INTO `t_school` VALUES ('2', 'string', 'string', 'string', '0', '2018-04-08 22:46:02', null, null, null, null, null, null, null);
INSERT INTO `t_school` VALUES ('3', 'string', 'string12', 'string12', '0', '2018-04-08 22:46:17', '2018-04-08 22:50:19', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `t_school_contract`
-- ----------------------------
DROP TABLE IF EXISTS `t_school_contract`;
CREATE TABLE `t_school_contract` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contract_no` varchar(80) NOT NULL COMMENT '合同编号',
  `student_id` int(11) NOT NULL COMMENT '学生ID',
  `consult_id` int(11) NOT NULL COMMENT '咨询师ID',
  `supervisor_id` int(11) DEFAULT NULL COMMENT '学管师ID',
  `school_id` int(11) NOT NULL COMMENT '学校ID',
  `contract_type` tinyint(4) NOT NULL COMMENT '报名类型',
  `period_enroll` double(4,0) NOT NULL COMMENT '报名课时',
  `period_free` double(4,0) DEFAULT NULL COMMENT '赠送课时',
  `period_transfer` double DEFAULT NULL,
  `period_remained` double DEFAULT NULL,
  `price_contract` double(4,0) DEFAULT NULL COMMENT '签约总价',
  `price_discount` double(4,0) DEFAULT NULL COMMENT '优惠',
  `price_total` double(4,0) NOT NULL COMMENT '实收金额',
  `price_other` double DEFAULT NULL,
  `paid` double DEFAULT NULL,
  `cfg_level` int(4) DEFAULT NULL COMMENT '学生年段',
  `cfg_subLevel` int(4) DEFAULT NULL COMMENT '细分年段',
  `cfg_course` int(4) DEFAULT NULL COMMENT '课程名称',
  `cfg_subCourse` int(4) DEFAULT NULL COMMENT '课程子类',
  `contract_date` date NOT NULL COMMENT '签约日期',
  `creationTime` datetime DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  `c_status` int(11) DEFAULT NULL COMMENT '合同状态',
  `c_paystatus` int(11) DEFAULT NULL COMMENT '缴费状态',
  `recorder` int(4) DEFAULT NULL COMMENT '登记人员ID',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_school_contract
-- ----------------------------
INSERT INTO `t_school_contract` VALUES ('9', '201804181241053254', '4', '12', null, '2', '2', '40', '0', '0', '40', '2400', '0', '2500', '100', '1090', '69', '73', '9', '11', '2018-04-17', '2018-04-25 23:22:42', null, '1', '1', null);

-- ----------------------------
-- Table structure for `t_school_invoice`
-- ----------------------------
DROP TABLE IF EXISTS `t_school_invoice`;
CREATE TABLE `t_school_invoice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contract_id` int(11) NOT NULL COMMENT '合同ID',
  `school_id` int(11) NOT NULL COMMENT '学校ID',
  `invoice_no` varchar(255) DEFAULT NULL COMMENT '收据编号',
  `fee_amount` double NOT NULL COMMENT '支付金额',
  `fee_type` tinyint(4) DEFAULT NULL COMMENT '支付方式',
  `creationTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `recorder` int(4) DEFAULT NULL COMMENT '操作人员ID',
  `owner_id` int(11) DEFAULT NULL COMMENT '归属者',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_school_invoice
-- ----------------------------
INSERT INTO `t_school_invoice` VALUES ('14', '9', '2', '80421', '600', '3', '2018-04-25 23:22:46', null, null, null);
INSERT INTO `t_school_invoice` VALUES ('15', '9', '2', '231', '145', '4', '2018-04-25 23:40:59', null, null, null);
INSERT INTO `t_school_invoice` VALUES ('16', '9', '2', '534', '345', '1', '2018-04-25 23:48:38', null, null, null);

-- ----------------------------
-- Table structure for `t_school_product`
-- ----------------------------
DROP TABLE IF EXISTS `t_school_product`;
CREATE TABLE `t_school_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `p_name` varchar(255) NOT NULL COMMENT '课程名称',
  `p_type` int(11) DEFAULT NULL COMMENT '课程类别',
  `p_period` double(11,0) DEFAULT NULL COMMENT '课时',
  `p_parent` int(11) DEFAULT NULL COMMENT '父课程ID',
  `school_id` int(11) NOT NULL COMMENT '学校ID',
  `p_createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `p_lastUpdateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `school_id` (`school_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_school_product
-- ----------------------------
INSERT INTO `t_school_product` VALUES ('1', '一对二', '68', '1', null, '1', '2018-04-14 10:36:01', '2018-04-14 12:16:55');
INSERT INTO `t_school_product` VALUES ('2', '测试2', '18', '1', null, '1', '2018-04-14 11:39:05', null);
INSERT INTO `t_school_product` VALUES ('3', '一对一', '68', '1', null, '1', '2018-04-14 11:40:25', null);
INSERT INTO `t_school_product` VALUES ('5', '子类2fe', null, null, '2', '1', '2018-04-14 12:35:11', '2018-04-14 12:42:48');
INSERT INTO `t_school_product` VALUES ('6', '一对三', '68', '1', null, '1', '2018-04-14 12:40:56', null);
INSERT INTO `t_school_product` VALUES ('9', '一对一', '18', '1', null, '2', '2018-04-18 12:26:35', null);
INSERT INTO `t_school_product` VALUES ('10', '一对多', '18', '1', null, '2', '2018-04-18 12:26:58', null);
INSERT INTO `t_school_product` VALUES ('11', '小学', null, null, '9', '2', '2018-04-18 12:31:29', null);
INSERT INTO `t_school_product` VALUES ('12', '初一', null, null, '9', '2', '2018-04-18 12:31:52', null);

-- ----------------------------
-- Table structure for `t_school_student`
-- ----------------------------
DROP TABLE IF EXISTS `t_school_student`;
CREATE TABLE `t_school_student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `s_name` varchar(120) NOT NULL COMMENT '学生姓名',
  `s_wxid` varchar(255) DEFAULT NULL COMMENT '微信ID',
  `s_contactName` varchar(255) DEFAULT NULL COMMENT '联系人姓名',
  `s_contactRelation` tinyint(4) DEFAULT NULL COMMENT '联系人关系',
  `s_contactPhone` varchar(20) DEFAULT NULL COMMENT '联系人方式',
  `s_meto` varchar(255) DEFAULT NULL COMMENT '其他备注',
  `school_id` int(11) NOT NULL,
  `consultant_id` int(11) NOT NULL COMMENT '咨询师ID',
  `supervisor_id` int(11) DEFAULT NULL COMMENT '学管师ID',
  `cfg_level` varchar(255) DEFAULT NULL,
  `cfg_subLevel` varchar(255) DEFAULT NULL,
  `cfg_course` varchar(255) DEFAULT NULL,
  `cfg_subCourse` varchar(255) DEFAULT NULL,
  `creationTime` varchar(255) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `s_status` tinyint(4) DEFAULT NULL COMMENT '学生状态',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_school_student
-- ----------------------------
INSERT INTO `t_school_student` VALUES ('1', '赵彤宇', 'fewaasfaewf12134121', '142141', '3', '12421', '', '2', '12', null, '69', '74', '18', '21', null, null, null);
INSERT INTO `t_school_student` VALUES ('2', '123', 'xxx', 'xasd', '1', '123', 'dsdfsd', '2', '13', null, '70', '71', '18', '21', null, null, null);
INSERT INTO `t_school_student` VALUES ('3', '张同宇', 'marco', '真相', '1', '18602889661', '备注信息', '2', '13', null, '69', '73', '9', '11', null, null, null);
INSERT INTO `t_school_student` VALUES ('4', '张浩', 'x36zhao', '张老二', '1', '18602889661', '', '2', '12', null, '70', '72', '9', '11', null, null, null);

-- ----------------------------
-- Table structure for `t_sys_config`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_config`;
CREATE TABLE `t_sys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `c_name` varchar(255) NOT NULL,
  `c_desc` varchar(255) DEFAULT NULL,
  `c_single` tinyint(4) DEFAULT NULL,
  `c_parent` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_config
-- ----------------------------
INSERT INTO `t_sys_config` VALUES ('26', 'C1', '拉丁舞', '0', '22');
INSERT INTO `t_sys_config` VALUES ('27', 'C2', '民族舞', '0', '22');
INSERT INTO `t_sys_config` VALUES ('28', 'C3', '街舞', '0', '22');
INSERT INTO `t_sys_config` VALUES ('21', '种类', '种类', null, '18');
INSERT INTO `t_sys_config` VALUES ('30', 'C5', '机械舞', '0', '22');
INSERT INTO `t_sys_config` VALUES ('65', 'C_SUBJECT_5', '社会', '0', '48');
INSERT INTO `t_sys_config` VALUES ('16', 'C_COURSE_TYPE', '课程种类', '0', null);
INSERT INTO `t_sys_config` VALUES ('63', 'C_SUBJECT_3', '英语', '0', '48');
INSERT INTO `t_sys_config` VALUES ('18', 'C_COURSE_TYPE_1', '艺术体育', '0', '16');
INSERT INTO `t_sys_config` VALUES ('32', 'M_1', '流行音乐', '0', '31');
INSERT INTO `t_sys_config` VALUES ('34', 'M_3', '古典音乐', '0', '31');
INSERT INTO `t_sys_config` VALUES ('62', 'C_SUBJECT_2', '语文', '0', '48');
INSERT INTO `t_sys_config` VALUES ('43', 'STU_LEVEL_1_1', '小一', '0', '41');
INSERT INTO `t_sys_config` VALUES ('64', 'C_SUBJECT_4', '科学', '0', '48');
INSERT INTO `t_sys_config` VALUES ('40', 'C_STUDENT_LEVEL', '学生年段', '0', null);
INSERT INTO `t_sys_config` VALUES ('44', 'STU_LEVEL_1_2', '小二', '0', '41');
INSERT INTO `t_sys_config` VALUES ('45', 'STU_LEVEL_2_1', '早一', '0', '42');
INSERT INTO `t_sys_config` VALUES ('46', 'STU_LEVEL_2_2C_SUBJECT', '早二', '0', '42');
INSERT INTO `t_sys_config` VALUES ('47', 'C_GRADE', '年级', null, null);
INSERT INTO `t_sys_config` VALUES ('48', 'C_SUBJECT', '科目', null, null);
INSERT INTO `t_sys_config` VALUES ('50', 'C_GRADE_1', '小一', '0', '47');
INSERT INTO `t_sys_config` VALUES ('51', 'C_GRADE_2', '小二', '0', '47');
INSERT INTO `t_sys_config` VALUES ('52', 'C_GRADE_3', '小三', '0', '47');
INSERT INTO `t_sys_config` VALUES ('53', 'C_GRADE_4', '小四', '0', '47');
INSERT INTO `t_sys_config` VALUES ('54', 'C_GRADE_5', '小六', '0', '47');
INSERT INTO `t_sys_config` VALUES ('55', 'C_GRADE_6', '初一', '0', '47');
INSERT INTO `t_sys_config` VALUES ('56', 'C_GRADE_7', '初二', '0', '47');
INSERT INTO `t_sys_config` VALUES ('57', 'C_GRADE_8', '初三', '0', '47');
INSERT INTO `t_sys_config` VALUES ('58', 'C_GRADE_9', '高一', '0', '47');
INSERT INTO `t_sys_config` VALUES ('59', 'C_GRADE_10', '高二', '0', '47');
INSERT INTO `t_sys_config` VALUES ('60', 'C_GRADE_11', '高三', '0', '47');
INSERT INTO `t_sys_config` VALUES ('61', 'C_SUBJECT_1', '数学', '0', '48');
INSERT INTO `t_sys_config` VALUES ('66', 'C_SUBJECT_6', '物理', '0', '48');
INSERT INTO `t_sys_config` VALUES ('67', 'C_SUBJECT_7', '化学', '0', '48');
INSERT INTO `t_sys_config` VALUES ('68', 'C_COURSE_TYPE_2', '学科辅导', '0', '16');
INSERT INTO `t_sys_config` VALUES ('69', 'C_STUDENT_LEVEL_1', '早教', '0', '40');
INSERT INTO `t_sys_config` VALUES ('70', 'C_STUDENT_LEVEL_2', '小学', '0', '40');
INSERT INTO `t_sys_config` VALUES ('71', 'C_STUDENT_LEVEL_2_1', '小一', '0', '70');
INSERT INTO `t_sys_config` VALUES ('72', 'C_STUDENT_LEVEL_2_2', '小二', '0', '70');
INSERT INTO `t_sys_config` VALUES ('73', 'C_STUDENT_LEVEL_1_1', '早一', '0', '69');
INSERT INTO `t_sys_config` VALUES ('74', 'C_STUDENT_LEVEL_1_2', '早二', '0', '69');

-- ----------------------------
-- Table structure for `t_sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL,
  `role_alias` varchar(100) DEFAULT NULL,
  `role_visible` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
INSERT INTO `t_sys_role` VALUES ('1', '教师', 'ROLE_TEACHER', '1');
INSERT INTO `t_sys_role` VALUES ('2', '咨询师', 'ROLE_CONSULTANT', '1');
INSERT INTO `t_sys_role` VALUES ('3', '教务员', 'ROLE_ACADEMIC_DEAN', '1');
INSERT INTO `t_sys_role` VALUES ('4', '财务', 'ROLE_TREASURER', '1');
INSERT INTO `t_sys_role` VALUES ('6', '超级管理员', 'ROLE_SUPERUSER', '0');
INSERT INTO `t_sys_role` VALUES ('5', '管理员', 'ROLE_ADMINISTRATOR', '0');
