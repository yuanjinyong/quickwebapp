/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.10-log 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('BBGL','ROOT','ROOT','报表管理','报表管理模块',NULL,'1',NULL,'900','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('BBGL-CWBB','BBGL','ROOT/BBGL','财务报表','财务报表子模块',NULL,'1',NULL,'10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('BBGL-CWBB-CWYF','BBGL-CWBB','ROOT/BBGL/BBGL-CWBB','财务应付报表','财务应付类报表',NULL,'1',NULL,'20','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('BBGL-CWBB-CWYF-A','BBGL-CWBB-CWYF','ROOT/BBGL/BBGL-CWBB/BBGL-CWBB-CWYF','财务应付A报表','财务应付A类报表',NULL,'1',NULL,'10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('BBGL-CWBB-CWYF-A-A','BBGL-CWBB-CWYF-A','ROOT/BBGL/BBGL-CWBB/BBGL-CWBB-CWYF/BBGL-CWBB-CWYF-A','财务应付A-A报表','财务应付A-A类报表',NULL,'2','/sys/url','10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('BBGL-CWBB-CWYF-CDFK','BBGL-CWBB-CWYF','ROOT/BBGL/BBGL-CWBB/BBGL-CWBB-CWYF','车队应付报表','车队应付报表页面',NULL,'2',NULL,'10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('BBGL-CWBB-CWYF-GYSFK','BBGL-CWBB-CWYF','ROOT/BBGL/BBGL-CWBB/BBGL-CWBB-CWYF','原材料应付报表','原材料应付报表页面',NULL,'2',NULL,'20','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('BBGL-CWBB-CWYS','BBGL-CWBB','ROOT/BBGL/BBGL-CWBB','财务应收报表','财务应收报表页面',NULL,'2',NULL,'10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('HELP','ROOT','ROOT','帮助','帮助页面',NULL,'2','/help','990','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('ROOT',NULL,NULL,'系统菜单','根菜单',NULL,'0',NULL,'0','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XSGL','ROOT','ROOT','销售管理','销售管理模块',NULL,'1',NULL,'10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XSGL-HTGL','XSGL','ROOT/XSGL','合同管理','合同管理页面',NULL,'2','/sales/contract','20','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XSGL-KHGL','XSGL','ROOT/XSGL','客户管理','客户管理页面',NULL,'2','/sales/customer','10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL','ROOT','ROOT','系统管理','系统管理模块',NULL,'1',NULL,'980','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-CDGL','XTGL','ROOT/XTGL','菜单管理','菜单管理页面',NULL,'2','/sys/menu','20','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-CDGL-CK','XTGL-CDGL','ROOT/XTGL/XTGL-CDGL','查看','查看菜单',NULL,'3',NULL,'50','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-CDGL-SC','XTGL-CDGL','ROOT/XTGL/XTGL-CDGL','删除','删除菜单',NULL,'3',NULL,'40','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-CDGL-XG','XTGL-CDGL','ROOT/XTGL/XTGL-CDGL','修改','修改菜单',NULL,'3',NULL,'30','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-CDGL-ZJ','XTGL-CDGL','ROOT/XTGL/XTGL-CDGL','增加','增加菜单',NULL,'3',NULL,'10','1');
insert into `t_sys_menu` (`f_id`, `f_parent_id`, `f_parent_ids`, `f_menu_name`, `f_menu_remark`, `f_icon`, `f_type`, `f_url_id`, `f_order`, `f_is_show`) values('XTGL-URLGL','XTGL','ROOT/XTGL','URL管理','URL地址管理页面',NULL,'2','/sys/url','10','1');
