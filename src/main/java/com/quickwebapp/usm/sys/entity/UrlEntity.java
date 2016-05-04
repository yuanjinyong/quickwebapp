package com.quickwebapp.usm.sys.entity;

import com.quickwebapp.framework.core.entity.BaseEntity;

public class UrlEntity extends BaseEntity<String> {
    private String f_url; // URL
    private String f_description; // URL描述
    private String f_patterns; // URL表达式
    private String f_methods; // 提交方式
    private String f_params; // 匹配查询参数
    private String f_headers; // 匹配HTTP头参数
    private String f_consumes; // 匹配Content-type，如application/json、application/xml、text/xml
    private String f_produces; //
    private String f_custom; //
    private String f_handler_method; // 处理方法
    private Integer f_log; // 是否记录日志。如查询列表，进入增加界面等，都不记录，而删除、修改、增加就需要记录
    private Integer f_auto; // 是否自动扫描生成

    public String getF_url() {
        return f_url;
    }

    public void setF_url(String f_url) {
        this.f_url = f_url;
    }

    public String getF_description() {
        return f_description;
    }

    public void setF_description(String f_description) {
        this.f_description = f_description;
    }

    public String getF_patterns() {
        return f_patterns;
    }

    public void setF_patterns(String f_patterns) {
        this.f_patterns = f_patterns;
    }

    public String getF_methods() {
        return f_methods;
    }

    public void setF_methods(String f_methods) {
        this.f_methods = f_methods;
    }

    public String getF_params() {
        return f_params;
    }

    public void setF_params(String f_params) {
        this.f_params = f_params;
    }

    public String getF_headers() {
        return f_headers;
    }

    public void setF_headers(String f_headers) {
        this.f_headers = f_headers;
    }

    public String getF_consumes() {
        return f_consumes;
    }

    public void setF_consumes(String f_consumes) {
        this.f_consumes = f_consumes;
    }

    public String getF_produces() {
        return f_produces;
    }

    public void setF_produces(String f_produces) {
        this.f_produces = f_produces;
    }

    public String getF_custom() {
        return f_custom;
    }

    public void setF_custom(String f_custom) {
        this.f_custom = f_custom;
    }

    public String getF_handler_method() {
        return f_handler_method;
    }

    public void setF_handler_method(String f_handler_method) {
        this.f_handler_method = f_handler_method;
    }

    public Integer getF_log() {
        return f_log;
    }

    public void setF_log(Integer f_log) {
        this.f_log = f_log;
    }

    public Integer getF_auto() {
        return f_auto;
    }

    public void setF_auto(Integer f_auto) {
        this.f_auto = f_auto;
    }
}
