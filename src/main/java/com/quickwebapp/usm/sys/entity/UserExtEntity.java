package com.quickwebapp.usm.sys.entity;

import java.sql.Timestamp;

import com.quickwebapp.framework.core.entity.BaseEntity;

public class UserExtEntity extends BaseEntity<Integer> {
    private String f_staff_no;// 工号
    private Integer f_gender;// 性别
    private String f_certificate_type;// 证件类型
    private String f_certificate_no;// 证件号码
    private String f_mobile;// 手机号码
    private String f_register_addr;// 户籍地址
    private String f_home_addr;// 居住地址
    private Integer f_dept_id;// 所属部门
    private Integer f_post;// 岗位
    private Integer f_status;// 状态，1(在职)、2(离职)
    private Timestamp f_entry_date;// 入职时间
    private Timestamp f_resign_date;// 离职时间
    private String f_phone;// 办公电话
    private String f_email;// 办公电话
    private String f_remark;// 备注说明

    public String getF_staff_no() {
        return f_staff_no;
    }

    public void setF_staff_no(String f_staff_no) {
        this.f_staff_no = f_staff_no;
    }

    public Integer getF_gender() {
        return f_gender;
    }

    public void setF_gender(Integer f_gender) {
        this.f_gender = f_gender;
    }

    public String getF_certificate_type() {
        return f_certificate_type;
    }

    public void setF_certificate_type(String f_certificate_type) {
        this.f_certificate_type = f_certificate_type;
    }

    public String getF_certificate_no() {
        return f_certificate_no;
    }

    public void setF_certificate_no(String f_certificate_no) {
        this.f_certificate_no = f_certificate_no;
    }

    public String getF_mobile() {
        return f_mobile;
    }

    public void setF_mobile(String f_mobile) {
        this.f_mobile = f_mobile;
    }

    public String getF_register_addr() {
        return f_register_addr;
    }

    public void setF_register_addr(String f_register_addr) {
        this.f_register_addr = f_register_addr;
    }

    public String getF_home_addr() {
        return f_home_addr;
    }

    public void setF_home_addr(String f_home_addr) {
        this.f_home_addr = f_home_addr;
    }

    public Integer getF_dept_id() {
        return f_dept_id;
    }

    public void setF_dept_id(Integer f_dept_id) {
        this.f_dept_id = f_dept_id;
    }

    public Integer getF_post() {
        return f_post;
    }

    public void setF_post(Integer f_post) {
        this.f_post = f_post;
    }

    public Integer getF_status() {
        return f_status;
    }

    public void setF_status(Integer f_status) {
        this.f_status = f_status;
    }

    public Timestamp getF_entry_date() {
        return f_entry_date;
    }

    public void setF_entry_date(Timestamp f_entry_date) {
        this.f_entry_date = f_entry_date;
    }

    public Timestamp getF_resign_date() {
        return f_resign_date;
    }

    public void setF_resign_date(Timestamp f_resign_date) {
        this.f_resign_date = f_resign_date;
    }

    public String getF_phone() {
        return f_phone;
    }

    public void setF_phone(String f_phone) {
        this.f_phone = f_phone;
    }

    public String getF_email() {
        return f_email;
    }

    public void setF_email(String f_email) {
        this.f_email = f_email;
    }

    public String getF_remark() {
        return f_remark;
    }

    public void setF_remark(String f_remark) {
        this.f_remark = f_remark;
    }
}
