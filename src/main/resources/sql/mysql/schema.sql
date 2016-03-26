SET SESSION FOREIGN_KEY_CHECKS=0;



DROP TABLE IF EXISTS `t_sys_url`;
CREATE TABLE `t_sys_url` (
  `f_id` VARCHAR(128) COLLATE utf8_bin NOT NULL COMMENT 'f_url和f_methods的MD5值',
  `f_url` VARCHAR(128) COLLATE utf8_bin NOT NULL COMMENT 'URL',
  `f_description` VARCHAR(512) COLLATE utf8_bin DEFAULT NULL COMMENT 'URL描述',
  `f_patterns` VARCHAR(128) COLLATE utf8_bin NOT NULL COMMENT 'URL表达式',
  `f_methods` VARCHAR(128) COLLATE utf8_bin NOT NULL DEFAULT '[]' COMMENT '提交方式',
  `f_params` VARCHAR(128) COLLATE utf8_bin DEFAULT NULL COMMENT '匹配查询参数',
  `f_headers` VARCHAR(128) COLLATE utf8_bin DEFAULT NULL COMMENT '匹配HTTP头参数',
  `f_consumes` VARCHAR(128) COLLATE utf8_bin DEFAULT NULL COMMENT '匹配Content-type，如application/json、application/xml、text/xml',
  `f_produces` VARCHAR(128) COLLATE utf8_bin DEFAULT NULL,
  `f_custom` VARCHAR(128) COLLATE utf8_bin DEFAULT NULL,
  `f_handler_method` VARCHAR(512) COLLATE utf8_bin DEFAULT NULL COMMENT '处理方法',
  `f_log` INT(1) NOT NULL DEFAULT '2' COMMENT '是否记录日志。如查询列表，进入增加界面等，都不记录，而删除、修改、增加就需要记录',
  `f_auto` INT(1) NOT NULL DEFAULT '1' COMMENT '是否自动扫描生成',
  PRIMARY KEY (`f_id`),
  KEY `SYS_URL` (`f_url`),
  KEY `SYS_URL_LOG` (`f_log`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='URL定义表';


DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE `t_sys_menu` (
  `f_id` VARCHAR(64) COLLATE utf8_bin NOT NULL COMMENT '主键，以模块加横杠分隔，如 XTGL-QXGL 表示系统管理-权限管理',
  `f_parent_id` VARCHAR(64) COLLATE utf8_bin DEFAULT NULL COMMENT '父级菜单',
  `f_parent_ids` VARCHAR(2000) COLLATE utf8_bin DEFAULT NULL COMMENT '所有父级菜单',
  `f_menu_name` VARCHAR(256) COLLATE utf8_bin NOT NULL COMMENT '菜单名称',
  `f_menu_remark` VARCHAR(512) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单描述',
  `f_icon` VARCHAR(128) COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
  `f_type` INT(2) NOT NULL DEFAULT '3' COMMENT '类型，1目录、2页面、3按钮',
  `f_url_id` VARCHAR(128) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单对应的URL，目录和按钮不需要填写，只有对应页面的菜单才需要填写',
  `f_order` INT(11) NOT NULL DEFAULT '10' COMMENT '排序',
  `f_is_show` INT(2) DEFAULT '1' COMMENT '当前站是否启用，1启用，2不启用',
  PRIMARY KEY (`f_id`),
  KEY `SYS_MENU_PARENT_ID` (`f_parent_id`),
  KEY `SYS_MENU_IS_SHOW` (`f_is_show`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单表';


DROP TABLE IF EXISTS `t_sys_menu_url`;
CREATE TABLE `t_sys_menu_url` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_menu_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '菜单ID，关联t_sys_menu表的f_id',
  `f_url_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'URL的ID，关联t_sys_url表的f_id',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNQ_MENU_URL` (`f_menu_id`,`f_url_id`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单可以访问的URL地址';



