SET SESSION FOREIGN_KEY_CHECKS=0;


/*==============================================================*/
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
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='URL（资源）定义表';
/*==============================================================*/


/*==============================================================*/
DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE `t_sys_menu` (
  `f_id` VARCHAR(64) COLLATE utf8_bin NOT NULL COMMENT '主键，以模块加横杠分隔，如 XTGL-QXGL 表示系统管理-权限管理',
  `f_parent_id` VARCHAR(64) COLLATE utf8_bin DEFAULT NULL COMMENT '父级菜单（权限）',
  `f_parent_ids` VARCHAR(2000) COLLATE utf8_bin DEFAULT NULL COMMENT '所有父级菜单（权限）',
  `f_menu_name` VARCHAR(256) COLLATE utf8_bin NOT NULL COMMENT '菜单（权限）名称',
  `f_menu_remark` VARCHAR(512) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单（权限）描述',
  `f_icon` VARCHAR(128) COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
  `f_type` INT(2) NOT NULL DEFAULT '3' COMMENT '类型，1目录、2页面、3按钮',
  `f_url_id` VARCHAR(128) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单对应的URL，目录和按钮不需要填写，只有对应页面的菜单才需要填写',
  `f_order` INT(11) NOT NULL DEFAULT '10' COMMENT '排序',
  `f_is_show` INT(2) DEFAULT '1' COMMENT '当前站是否启用，1启用，2不启用',
  PRIMARY KEY (`f_id`),
  KEY `SYS_MENU_PARENT_ID` (`f_parent_id`),
  KEY `SYS_MENU_IS_SHOW` (`f_is_show`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单（权限）表';

DROP TABLE IF EXISTS `t_sys_menu_url`;
CREATE TABLE `t_sys_menu_url` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_menu_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '菜单（权限）ID，关联t_sys_menu表的f_id',
  `f_url_id` varchar(128) COLLATE utf8_bin NOT NULL COMMENT 'URL的ID，关联t_sys_url表的f_id',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNQ_MENU_URL` (`f_menu_id`,`f_url_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单（权限）可以访问的URL地址';

insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('ROOT',NULL,'','系统菜单','根菜单',NULL,'0',NULL,'0','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('PROFILE','ROOT','ROOT','个人中心','个人中心模块','home','1',NULL,'10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('HYGL','ROOT','ROOT','会员管理','会员管理模块',NULL,'1',NULL,'20','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('ZDGL','ROOT','ROOT','站点管理','站点管理模块',NULL,'1',NULL,'30','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL','ROOT','ROOT','系统管理','系统管理模块','cog','1',NULL,'980','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('HELP','ROOT','ROOT','帮助','帮助页面',NULL,'2','app/help/list.html','990','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('level1','ROOT','ROOT','1一级菜单','1一级菜单',NULL,'1',NULL,'995','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('HYGL-HYXXGL','HYGL','ROOT/HYGL','会员信息管理','会员信息管理页面',NULL,'2','app/stzj/member/info/list.html','10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('PROFILE-HOME','PROFILE','ROOT/PROFILE','我的首页','欢迎页面',NULL,'2','app/stzj/profile/home/welcome.html','1','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('PROFILE-WDZD','PROFILE','ROOT/PROFILE','我的站点','我的站点管理页面',NULL,'2','app/stzj/profile/station/form.html','10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('PROFILE-XFSCJH','PROFILE','ROOT/PROFILE','下发生产计划','下发生产计划',NULL,'2','app/stzj/profile/plan/form.html','20','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('MYPLAN','PROFILE','ROOT/PROFILE','我的生产计划','我的生产计划管理页面',NULL,'2','app/stzj/profile/plan/list.html','30','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('PROFILE-HTSH','PROFILE','ROOT/PROFILE','合同审核','合同审核页面',NULL,'2','app/stzj/profile/sales/list.html','40','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('PROFILE-XXJGSH','PROFILE','ROOT/PROFILE','信息价格审核','信息价格审核页面',NULL,'2','app/stzj/profile/sales/info/price/list.html','50','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('PROFILE-HTJGSH','PROFILE','ROOT/PROFILE','合同价格审核','合同价格审核页面',NULL,'2','app/stzj/profile/sales/contract/price/list.html','60','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('PROFILE-SETTPURCHASE','PROFILE','ROOT/PROFILE','原材料采购结算审核','原材料采购结算审核页面',NULL,'2','app/stzj/profile/finance/settlement/purchase/list.html','62','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('PROFILE-PLACINGMONTHLYBAR','PROFILE','ROOT/PROFILE','月浇筑方量统计图','月浇筑方量统计图',NULL,'2','app/stzj/profile/chart/placingMonthly/form.html','70','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('PROFILE-MATERIALSCONSUMELINE','PROFILE','ROOT/PROFILE','原材料总消耗统计图','原材料总消耗统计图',NULL,'2','app/stzj/profile/chart/materialsConsume/form.html','80','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-URLGL','XTGL','ROOT/XTGL','URL管理','URL地址管理页面','link','2','app/sys/url/list.html','10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-CDGL','XTGL','ROOT/XTGL','菜单管理','菜单管理页面','menu-hamburger','2','app/sys/menu/list.html','30','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-JSGL','XTGL','ROOT/XTGL','角色管理','角色管理页面','registration-mark','2','app/sys/role/list.html','40','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-YHGL','XTGL','ROOT/XTGL','用户管理','用户管理页面','user','2','app/sys/user/list.html','50','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-ZHGL','XTGL','ROOT/XTGL','租户管理','租户管理页面','flag','2','app/sys/tenant/list.html','100','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-ZDGL','XTGL','ROOT/XTGL','字典管理','字典管理页面','book','2','app/sys/dictionary/list.html','110','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-CDGL-ZJ','XTGL-CDGL','ROOT/XTGL/XTGL-CDGL','增加','增加菜单',NULL,'3',NULL,'10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-CDGL-XG','XTGL-CDGL','ROOT/XTGL/XTGL-CDGL','修改','修改菜单',NULL,'3',NULL,'30','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-CDGL-SC','XTGL-CDGL','ROOT/XTGL/XTGL-CDGL','删除','删除菜单',NULL,'3',NULL,'40','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-JSGL-ZJ','XTGL-JSGL','ROOT/XTGL/XTGL-JSGL','增加','新增角色',NULL,'3',NULL,'10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-JSGL-XG','XTGL-JSGL','ROOT/XTGL/XTGL-JSGL','修改','修改角色',NULL,'3',NULL,'20','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-JSGL-SC','XTGL-JSGL','ROOT/XTGL/XTGL-JSGL','删除','删除角色',NULL,'3',NULL,'30','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-YHGL-ZJ','XTGL-YHGL','ROOT/XTGL/XTGL-YHGL','增加','新增用户',NULL,'3',NULL,'10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-YHGL-XG','XTGL-YHGL','ROOT/XTGL/XTGL-YHGL','修改','修改用户',NULL,'3',NULL,'20','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-YHGL-SC','XTGL-YHGL','ROOT/XTGL/XTGL-YHGL','删除','删除用户',NULL,'3',NULL,'30','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-ZDGL-ZJ','XTGL-ZDGL','ROOT/XTGL/XTGL-ZDGL','增加','新增字典组',NULL,'3',NULL,'10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-ZDGL-XG','XTGL-ZDGL','ROOT/XTGL/XTGL-ZDGL','修改','修改字典组',NULL,'3',NULL,'20','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-ZDGL-SC','XTGL-ZDGL','ROOT/XTGL/XTGL-ZDGL','删除','删除字典组',NULL,'3',NULL,'30','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-ZHGL-ZJ','XTGL-ZHGL','ROOT/XTGL/XTGL-ZHGL','增加','新增租户',NULL,'3',NULL,'10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-ZHGL-XG','XTGL-ZHGL','ROOT/XTGL/XTGL-ZHGL','修改','修改租户',NULL,'3',NULL,'20','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-ZHGL-SC','XTGL-ZHGL','ROOT/XTGL/XTGL-ZHGL','删除','删除租户',NULL,'3',NULL,'30','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('ZDGL-ZDXXGL','ZDGL','ROOT/ZDGL','站点信息管理','站点信息管理页面',NULL,'2','app/stzj/station/info/list.html','10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('level2','level1','ROOT/level1','1.1二级菜单','1.1二级菜单',NULL,'1',NULL,'10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('level3','level2','ROOT/level1/level2','1.1.1三级菜单','1.1.1三级菜单',NULL,'1',NULL,'10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('level4-1','level3','ROOT/level1/level2/level3','1.1.1.1四级菜单1','1.1.1.1四级菜单1',NULL,'1',NULL,'10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('level4-2','level3','ROOT/level1/level2/level3','1.1.1.2四级菜单2','1.1.1.2四级菜单2',NULL,'1',NULL,'20','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('level5-1','level4-1','ROOT/level1/level2/level3/level4-1','1.1.1.1.1页面1','1.1.1.1.1页面1',NULL,'2',NULL,'10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('level5-2','level4-1','ROOT/level1/level2/level3/level4-1','1.1.1.1.2页面2','1.1.1.1.2页面2',NULL,'2',NULL,'20','2');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('level6-1','level5-1','ROOT/level1/level2/level3/level4-1/level5-1','1.1.1.1.1.1按钮1','1.1.1.1.1.1按钮1',NULL,'3',NULL,'10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('level6-2','level5-1','ROOT/level1/level2/level3/level4-1/level5-1','1.1.1.1.1.2按钮2','1.1.1.1.1.2按钮2',NULL,'3',NULL,'20','1');

insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('105','PROFILE-HOME','0e37f794f8eb851d2f44e21c9bf51137');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('101','PROFILE-HOME','880880300e0efe04e6b7f02ffe78dd12');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('102','PROFILE-HOME','97926aaa5cc28aa357aee8d770161b6b');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('103','PROFILE-HOME','acf731ca93df12ea257d6d731a8b44e8');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('104','PROFILE-HOME','b8c3cfda1d577393b374dd2d6aae80ee');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('45','PROFILE-HTSH','52ef56e72a973ffc02bd114b9c9f4af7');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('83','PROFILE-WDZD','5a52b691eaca4493cb00fba3da4107fa');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('86','PROFILE-WDZD','5ed479c35779ba0a6de56ab303a151cd');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('84','PROFILE-WDZD','6639de458db43ff8d6fc029ced4836a7');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('85','PROFILE-WDZD','7cec66a8b30598856eb0deb20bf5ed86');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('15','XTGL-CDGL','b0f57cde4c9271c89fa5eeb9b843f702');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('16','XTGL-CDGL','de81bf33504ef7c6fd4c61ed0cddef73');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('19','XTGL-CDGL-SC','f83bfd76c0e75db1c131f6a4cf61b080');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('22','XTGL-CDGL-XG','137f4d396e9b1883a66f437b74f8b1d9');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('23','XTGL-CDGL-XG','1cad56b63ab98111f1927bb5cd0f5e43');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('21','XTGL-CDGL-ZJ','1cad56b63ab98111f1927bb5cd0f5e43');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('20','XTGL-CDGL-ZJ','e3924eb7c9af1d12bb7e152988b0a2eb');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('32','XTGL-JSGL','905a9e58275b090f6cf9f8caeba0a7f6');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('33','XTGL-JSGL','98d06242bf6e29809c062d7f9c4de53f');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('34','XTGL-JSGL','c827d472c0cf7a843713aecb34b6e018');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('37','XTGL-JSGL-SC','3d861c49350d5dd58e4f50b6c7a82f9b');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('36','XTGL-JSGL-XG','9223f3efc12944c0de3ff2cabd3e0958');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('35','XTGL-JSGL-ZJ','5e1605f4cced7c5ac9270b4282a8a20b');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('40','XTGL-YHGL','83ff36e9506be8e7373618f121b918a6');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('41','XTGL-YHGL','9483a33d37a16f1627e48e26a3ec0dec');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('38','XTGL-YHGL','c3fd4713a7f1a63b38a8e462e0b44d7e');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('39','XTGL-YHGL','e80931b0396649d8f0e11d94bf9ff7af');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('44','XTGL-YHGL-SC','069146ce19183029923818bff93f91c3');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('43','XTGL-YHGL-XG','a149d2567f3b10cb5c9a332e3bb4ce03');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('42','XTGL-YHGL-ZJ','22319445817f808e9db15e6cae54cd09');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('87','XTGL-ZDGL','582b2e47d20581e589a90f11683f4528');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('88','XTGL-ZDGL','5f0a72ed2697b5adfa48274a7517829e');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('89','XTGL-ZDGL','7365e725e30d6066c4779abd5850c450');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('90','XTGL-ZDGL','ff3094aa2a13b4cea267c6984b2a7cb1');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('95','XTGL-ZDGL-SC','3c1f73046a644dca6205aeee459ec802');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('96','XTGL-ZDGL-SC','d82123589d57cb943042e83a73b20e2c');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('93','XTGL-ZDGL-XG','022f6ada8f0735e24c08fa610a0c4248');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('94','XTGL-ZDGL-XG','ec4a95645fc8a4e36781d4ad104f25c8');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('91','XTGL-ZDGL-ZJ','0e570f9d97bacefab4e349aea10876af');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('92','XTGL-ZDGL-ZJ','ca0829fc24ec9d310695100f96de757b');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('46','XTGL-ZHGL','10d1b2837c8852eff81d648ef80c8d9f');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('47','XTGL-ZHGL','52adef8c9d79767588e6d19729b0431a');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('50','XTGL-ZHGL-SC','2ba96d2f1bd7d048ff0c78f5d6b529c0');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('49','XTGL-ZHGL-XG','c19ebd858e1583a0c4607b1a0088b149');
insert into `t_sys_menu_url` (`f_id`, `f_menu_id`, `f_url_id`) values('51','XTGL-ZHGL-ZJ','fd23977931782b9dca501d24aae9aed3');
/*==============================================================*/


/*==============================================================*/
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_role_code` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '角色编码',
  `f_role_name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `f_remark` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '角色描述',
  `f_tenant_cid` int(4) NOT NULL DEFAULT '1' COMMENT '租户客户ID',
  `f_tenant_bid` int(4) NOT NULL DEFAULT '1' COMMENT '租户运营实体ID',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_ROLE_CODE` (`f_role_code`,`f_tenant_cid`,`f_tenant_bid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色表';

DROP TABLE IF EXISTS `t_sys_role_menu`;
CREATE TABLE `t_sys_role_menu` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_role_id` int(11) NOT NULL COMMENT '角色ID',
  `f_menu_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '菜单（权限）ID',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色菜单（权限）表';

insert into `t_sys_role` (`f_id`, `f_role_code`, `f_role_name`, `f_remark`, `f_tenant_cid`, `f_tenant_bid`) values('1','ADMIN','系统管理员','系统管理员角色','0','0');
insert into `t_sys_role` (`f_id`, `f_role_code`, `f_role_name`, `f_remark`, `f_tenant_cid`, `f_tenant_bid`) values('2','WEIXIN','微信用户','微信用户对应的角色','0','0');

insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('1','HELP');
insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('1','HYGL');
insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('1','HYGL-HYXXGL');
insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('1','ROOT');
insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('1','XTGL');
insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('1','XTGL-DICT');
insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('1','XTGL-JSGL');
insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('1','XTGL-JSGL-SC');
insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('1','XTGL-JSGL-XG');
insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('1','XTGL-JSGL-ZJ');
insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('1','XTGL-YHGL');
insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('1','XTGL-YHGL-SC');
insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('1','XTGL-YHGL-XG');
insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('1','XTGL-YHGL-ZJ');
insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('1','ZDGL');
insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('1','ZDGL-ZDXXGL');
insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('2','MYPLAN');
insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('2','PROFILE');
insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('2','PROFILE-HTSH');
insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('2','PROFILE-WDZD');
insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('2','PROFILE-XFSCJH');
insert into `t_sys_role_menu` (`f_role_id`, `f_menu_id`) values('2','ROOT');
/*==============================================================*/


/*==============================================================*/
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_account` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '账号',
  `f_password` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `f_name` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '姓名',
  `f_remark` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '备注说明',
  `f_status` int(1) NOT NULL DEFAULT '1' COMMENT '状态，1(正常)、2（锁定）、3（注销）',
  `f_create_time` datetime NOT NULL COMMENT '创建时间',
  `f_last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `f_locked_time` datetime DEFAULT NULL COMMENT '锁定时间',
  `f_unregister_time` datetime DEFAULT NULL COMMENT '注销时间',
  `f_is_can_login` int(1) NOT NULL DEFAULT '1' COMMENT '是否允许登录，1(是)、2(否)',
  `f_tenant_cid` int(4) NOT NULL DEFAULT '1' COMMENT '租户客户ID',
  `f_tenant_bid` int(4) NOT NULL DEFAULT '1' COMMENT '租户运营实体ID',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_USER_ACCOUNT` (`f_account`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户（操作员）表';

-- 密码：pkpm
insert into `t_sys_user` (`f_id`, `f_account`, `f_password`, `f_name`, `f_remark`, `f_status`, `f_create_time`, `f_last_login_time`, `f_locked_time`, `f_unregister_time`, `f_is_can_login`, `f_tenant_cid`, `f_tenant_bid`) values('1','SuperAdmin','$2a$10$GNzBdRgBu4uO0Bby6AYMrOhNubwd8NIfAcWEWqb1PsJIZvMEslyQu','超级系统管理员','超级系统管理员仅作为开发人员维护系统（系统管理）用，禁止使用该账号进行业务操作。','1','2016-07-21 12:18:15',NULL,NULL,NULL,'1','0','0');
insert into `t_sys_user` (`f_id`, `f_account`, `f_password`, `f_name`, `f_remark`, `f_status`, `f_create_time`, `f_last_login_time`, `f_locked_time`, `f_unregister_time`, `f_is_can_login`, `f_tenant_cid`, `f_tenant_bid`) values('2','admin','$2a$10$GNzBdRgBu4uO0Bby6AYMrOhNubwd8NIfAcWEWqb1PsJIZvMEslyQu','系统管理员','系统管理员仅作为维护系统（系统管理）用，一般不使用该账号进行业务操作。','1','2016-07-12 17:44:59',NULL,NULL,NULL,'1','0','0');

DROP TABLE IF EXISTS `t_sys_user_ext`;
CREATE TABLE `t_sys_user_ext` (
  `f_id` int(11) NOT NULL COMMENT '关联t_sys_user的f_id',
  `f_staff_no` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '工号',
  `f_gender` int(1) DEFAULT NULL COMMENT '性别，1(男)、2(女)',
  `f_certificate_type` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '证件类型',
  `f_certificate_no` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '证件号码',
  `f_mobile` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '手机号码',
  `f_register_addr` varchar(80) COLLATE utf8_bin DEFAULT NULL COMMENT '户籍地址',
  `f_home_addr` varchar(80) COLLATE utf8_bin DEFAULT NULL COMMENT '居住地址',
  `f_dept_id` int(11) DEFAULT NULL COMMENT '所属部门',
  `f_post` int(11) DEFAULT NULL COMMENT '岗位',
  `f_status` int(1) DEFAULT NULL COMMENT '状态，1(在职)、2(离职)',
  `f_entry_date` date DEFAULT NULL COMMENT '入职时间',
  `f_resign_date` date DEFAULT NULL COMMENT '离职时间',
  `f_phone` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '办公电话',
  `f_email` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '电子邮箱',
  `f_remark` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '备注说明',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户（操作员）扩展信息表';

DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_user_id` int(11) NOT NULL COMMENT '用户（操作员）ID',
  `f_role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户（操作员）角色表';

insert into `t_sys_user_role` (`f_user_id`, `f_role_id`) values('2','1');

DROP TABLE IF EXISTS `t_sys_user_menu`;
CREATE TABLE `t_sys_user_menu` (
  `f_id` int(11) NOT NULL AUTO_INCREMENT,
  `f_user_id` int(11) NOT NULL COMMENT '用户（操作员）ID',
  `f_menu_id` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '菜单（权限）ID',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户（操作员）菜单（权限）表';
/*==============================================================*/




/*==============================================================*/
DROP TABLE IF EXISTS `t_sys_tenant_customer`;
CREATE TABLE `t_sys_tenant_customer` (
  `f_id` int(4) NOT NULL DEFAULT '1' COMMENT '租户客户ID',
  `f_name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '租户客户名称',
  `f_remark` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '租户客户描述',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='租户客户表';

DROP TABLE IF EXISTS `t_sys_tenant_business`;
CREATE TABLE `t_sys_tenant_business` (
  `f_id` int(4) NOT NULL DEFAULT '0' COMMENT '租户运营实体ID',
  `f_name` varchar(64) COLLATE utf8_bin NOT NULL COMMENT '租户运营实体名称',
  `f_remark` varchar(1000) COLLATE utf8_bin DEFAULT NULL COMMENT '租户运营实体描述',
  `f_tenant_cid` int(4) NOT NULL DEFAULT '1' COMMENT '所属租户客户ID',
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='租户运营实体表';

insert into `t_sys_tenant_customer` (`f_id`, `f_name`, `f_remark`) values('1','默认客户','系统默认客户，请不要删除。');
insert into `t_sys_tenant_business` (`f_id`, `f_name`, `f_remark`, `f_tenant_cid`) values('1','默认运营实体','系统默认运营实体，请不要删除。','1');
/*==============================================================*/


/*==============================================================*/
DROP TABLE IF EXISTS `t_sys_dict_group`;
CREATE TABLE `t_sys_dict_group` (
  `f_id` INT(11) NOT NULL AUTO_INCREMENT,
  `f_code` VARCHAR(64) COLLATE utf8_bin NOT NULL COMMENT '字典组编码',
  `f_remark` VARCHAR(64) COLLATE utf8_bin NOT NULL COMMENT '字典组描述',
  `f_type` VARCHAR(64) COLLATE utf8_bin NOT NULL COMMENT '字典组类型，单表，通用',
  `f_sql` VARCHAR(2000) COLLATE utf8_bin DEFAULT NULL COMMENT '查询字典项的SQL语句，单表时有效',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_DICT_DEF` (`f_code`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='字典组定义表';

DROP TABLE IF EXISTS `t_sys_dict_item`;
CREATE TABLE `t_sys_dict_item` (
  `f_id` INT(11) NOT NULL AUTO_INCREMENT,
  `f_code` VARCHAR(64) COLLATE utf8_bin NOT NULL COMMENT '字典组编码',
  `f_item_code` VARCHAR(128) COLLATE utf8_bin NOT NULL COMMENT '字典项编码',
  `f_item_text` VARCHAR(512) COLLATE utf8_bin NOT NULL COMMENT '字典项描述',
  `f_item_order` INT(11) NOT NULL DEFAULT '1' COMMENT '排序',
  `f_tenant_cid` INT(4) NOT NULL DEFAULT '1' COMMENT '租户客户ID',
  `f_tenant_bid` INT(4) NOT NULL DEFAULT '1' COMMENT '租户运营实体ID',
  PRIMARY KEY (`f_id`),
  UNIQUE KEY `UNI_DICT_ITEM` (`f_code`,`f_item_code`,`f_tenant_cid`,`f_tenant_bid`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='通用字典项定义表';


insert into `t_sys_dict_group` (`f_id`, `f_code`, `f_remark`, `f_type`, `f_sql`) values('1','TrueFalse','是否','通用',NULL);
insert into `t_sys_dict_group` (`f_id`, `f_code`, `f_remark`, `f_type`, `f_sql`) values('2','MenuType','菜单类型','通用',NULL);
insert into `t_sys_dict_group` (`f_id`, `f_code`, `f_remark`, `f_type`, `f_sql`) values('3','UserStatus','用户状态','通用',NULL);
insert into `t_sys_dict_group` (`f_id`, `f_code`, `f_remark`, `f_type`, `f_sql`) values('4','Gender','性别','通用',NULL);
insert into `t_sys_dict_group` (`f_id`, `f_code`, `f_remark`, `f_type`, `f_sql`) values('5','CertificateType','证件类型','通用',NULL);
insert into `t_sys_dict_group` (`f_id`, `f_code`, `f_remark`, `f_type`, `f_sql`) values('6','Glyphicons','图标','通用',NULL);

insert into `t_sys_dict_item` (`f_id`, `f_code`, `f_item_code`, `f_item_text`, `f_item_order`, `f_tenant_cid`, `f_tenant_bid`) values('1','TrueFalse','1','是','1','1','1');
insert into `t_sys_dict_item` (`f_id`, `f_code`, `f_item_code`, `f_item_text`, `f_item_order`, `f_tenant_cid`, `f_tenant_bid`) values('2','TrueFalse','2','否','2','1','1');
insert into `t_sys_dict_item` (`f_id`, `f_code`, `f_item_code`, `f_item_text`, `f_item_order`, `f_tenant_cid`, `f_tenant_bid`) values('3','MenuType','0','根','1','1','1');
insert into `t_sys_dict_item` (`f_id`, `f_code`, `f_item_code`, `f_item_text`, `f_item_order`, `f_tenant_cid`, `f_tenant_bid`) values('4','MenuType','1','目录','2','1','1');
insert into `t_sys_dict_item` (`f_id`, `f_code`, `f_item_code`, `f_item_text`, `f_item_order`, `f_tenant_cid`, `f_tenant_bid`) values('5','MenuType','2','页面','3','1','1');
insert into `t_sys_dict_item` (`f_id`, `f_code`, `f_item_code`, `f_item_text`, `f_item_order`, `f_tenant_cid`, `f_tenant_bid`) values('6','MenuType','3','按钮','4','1','1');
insert into `t_sys_dict_item` (`f_id`, `f_code`, `f_item_code`, `f_item_text`, `f_item_order`, `f_tenant_cid`, `f_tenant_bid`) values('7','UserStatus','1','正常','1','1','1');
insert into `t_sys_dict_item` (`f_id`, `f_code`, `f_item_code`, `f_item_text`, `f_item_order`, `f_tenant_cid`, `f_tenant_bid`) values('8','UserStatus','2','锁定','2','1','1');
insert into `t_sys_dict_item` (`f_id`, `f_code`, `f_item_code`, `f_item_text`, `f_item_order`, `f_tenant_cid`, `f_tenant_bid`) values('9','UserStatus','3','注销','3','1','1');
insert into `t_sys_dict_item` (`f_id`, `f_code`, `f_item_code`, `f_item_text`, `f_item_order`, `f_tenant_cid`, `f_tenant_bid`) values('10','Gender','1','男','1','1','1');
insert into `t_sys_dict_item` (`f_id`, `f_code`, `f_item_code`, `f_item_text`, `f_item_order`, `f_tenant_cid`, `f_tenant_bid`) values('11','Gender','2','女','2','1','1');
insert into `t_sys_dict_item` (`f_id`, `f_code`, `f_item_code`, `f_item_text`, `f_item_order`, `f_tenant_cid`, `f_tenant_bid`) values('12','CertificateType','1','身份证','1','1','1');
insert into `t_sys_dict_item` (`f_id`, `f_code`, `f_item_code`, `f_item_text`, `f_item_order`, `f_tenant_cid`, `f_tenant_bid`) values('13','CertificateType','2','护照','2','1','1');
insert into `t_sys_dict_item` (`f_id`, `f_code`, `f_item_code`, `f_item_text`, `f_item_order`, `f_tenant_cid`, `f_tenant_bid`) values('14','Glyphicons','asterisk','<span class=\"glyphicon glyphicon-asterisk\">星号</span>','1','1','1');
insert into `t_sys_dict_item` (`f_id`, `f_code`, `f_item_code`, `f_item_text`, `f_item_order`, `f_tenant_cid`, `f_tenant_bid`) values('15','Glyphicons','user','<span class=\"glyphicon glyphicon-user\">用户</span>','2','1','1');
/*==============================================================*/
