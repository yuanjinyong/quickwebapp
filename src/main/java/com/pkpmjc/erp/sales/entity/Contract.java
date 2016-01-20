package com.pkpmjc.erp.sales.entity;

import java.util.Date;

public class Contract {
    /**
     * f_id
     *
     * @mbggenerated
     */
    private Integer f_id;

    /**
     * f_contract_no - 合同编号
     *
     * @mbggenerated
     */
    private String f_contract_no;

    /**
     * f_contract_name - 合同名称
     *
     * @mbggenerated
     */
    private String f_contract_name;

    /**
     * f_contract_type - 合同类型，关联字典表
     *
     * @mbggenerated
     */
    private Integer f_contract_type;

    /**
     * f_customer_id - 所属客户ID
     *
     * @mbggenerated
     */
    private Integer f_customer_id;

    /**
     * f_total_num - 合同签订总方量
     *
     * @mbggenerated
     */
    private Double f_total_num;

    /**
     * f_upper_num - 供应上限方量，0表示不受限制，其他数字表示超过该数字时，不允许再生产
     *
     * @mbggenerated
     */
    private Double f_upper_num;

    /**
     * f_signer - 合同签订人，关联用户表
     *
     * @mbggenerated
     */
    private Integer f_signer;

    /**
     * f_sign_date - 签订时间
     *
     * @mbggenerated
     */
    private Date f_sign_date;

    /**
     * f_linker - 客户联系人
     *
     * @mbggenerated
     */
    private String f_linker;

    /**
     * f_link_phone - 客户联系电话
     *
     * @mbggenerated
     */
    private String f_link_phone;

    /**
     * f_audit_memo - 审核意见
     *
     * @mbggenerated
     */
    private String f_audit_memo;

    /**
     * f_status - 合同审核状态，详见字典表201-203
     *
     * @mbggenerated
     */
    private Integer f_status;

    /**
     * f_file_name - 合同附件文件名
     *
     * @mbggenerated
     */
    private String f_file_name;

    /**
     * f_remark - 备注
     *
     * @mbggenerated
     */
    private String f_remark;

    /**
     * f_oper_log - 操作记录
     *
     * @mbggenerated
     */
    private String f_oper_log;

    /**
     * @return the value of t_sales_contract.f_id
     *
     * @mbggenerated
     */
    public Integer getF_id() {
        return f_id;
    }

    /**
     * @param f_id the value for t_sales_contract.f_id
     *
     * @mbggenerated
     */
    public void setF_id(Integer f_id) {
        this.f_id = f_id;
    }

    /**
     * @return the value of t_sales_contract.f_contract_no
     *
     * @mbggenerated
     */
    public String getF_contract_no() {
        return f_contract_no;
    }

    /**
     * @param f_contract_no the value for t_sales_contract.f_contract_no
     *
     * @mbggenerated
     */
    public void setF_contract_no(String f_contract_no) {
        this.f_contract_no = f_contract_no;
    }

    /**
     * @return the value of t_sales_contract.f_contract_name
     *
     * @mbggenerated
     */
    public String getF_contract_name() {
        return f_contract_name;
    }

    /**
     * @param f_contract_name the value for t_sales_contract.f_contract_name
     *
     * @mbggenerated
     */
    public void setF_contract_name(String f_contract_name) {
        this.f_contract_name = f_contract_name;
    }

    /**
     * @return the value of t_sales_contract.f_contract_type
     *
     * @mbggenerated
     */
    public Integer getF_contract_type() {
        return f_contract_type;
    }

    /**
     * @param f_contract_type the value for t_sales_contract.f_contract_type
     *
     * @mbggenerated
     */
    public void setF_contract_type(Integer f_contract_type) {
        this.f_contract_type = f_contract_type;
    }

    /**
     * @return the value of t_sales_contract.f_customer_id
     *
     * @mbggenerated
     */
    public Integer getF_customer_id() {
        return f_customer_id;
    }

    /**
     * @param f_customer_id the value for t_sales_contract.f_customer_id
     *
     * @mbggenerated
     */
    public void setF_customer_id(Integer f_customer_id) {
        this.f_customer_id = f_customer_id;
    }

    /**
     * @return the value of t_sales_contract.f_total_num
     *
     * @mbggenerated
     */
    public Double getF_total_num() {
        return f_total_num;
    }

    /**
     * @param f_total_num the value for t_sales_contract.f_total_num
     *
     * @mbggenerated
     */
    public void setF_total_num(Double f_total_num) {
        this.f_total_num = f_total_num;
    }

    /**
     * @return the value of t_sales_contract.f_upper_num
     *
     * @mbggenerated
     */
    public Double getF_upper_num() {
        return f_upper_num;
    }

    /**
     * @param f_upper_num the value for t_sales_contract.f_upper_num
     *
     * @mbggenerated
     */
    public void setF_upper_num(Double f_upper_num) {
        this.f_upper_num = f_upper_num;
    }

    /**
     * @return the value of t_sales_contract.f_signer
     *
     * @mbggenerated
     */
    public Integer getF_signer() {
        return f_signer;
    }

    /**
     * @param f_signer the value for t_sales_contract.f_signer
     *
     * @mbggenerated
     */
    public void setF_signer(Integer f_signer) {
        this.f_signer = f_signer;
    }

    /**
     * @return the value of t_sales_contract.f_sign_date
     *
     * @mbggenerated
     */
    public Date getF_sign_date() {
        return f_sign_date;
    }

    /**
     * @param f_sign_date the value for t_sales_contract.f_sign_date
     *
     * @mbggenerated
     */
    public void setF_sign_date(Date f_sign_date) {
        this.f_sign_date = f_sign_date;
    }

    /**
     * @return the value of t_sales_contract.f_linker
     *
     * @mbggenerated
     */
    public String getF_linker() {
        return f_linker;
    }

    /**
     * @param f_linker the value for t_sales_contract.f_linker
     *
     * @mbggenerated
     */
    public void setF_linker(String f_linker) {
        this.f_linker = f_linker;
    }

    /**
     * @return the value of t_sales_contract.f_link_phone
     *
     * @mbggenerated
     */
    public String getF_link_phone() {
        return f_link_phone;
    }

    /**
     * @param f_link_phone the value for t_sales_contract.f_link_phone
     *
     * @mbggenerated
     */
    public void setF_link_phone(String f_link_phone) {
        this.f_link_phone = f_link_phone;
    }

    /**
     * @return the value of t_sales_contract.f_audit_memo
     *
     * @mbggenerated
     */
    public String getF_audit_memo() {
        return f_audit_memo;
    }

    /**
     * @param f_audit_memo the value for t_sales_contract.f_audit_memo
     *
     * @mbggenerated
     */
    public void setF_audit_memo(String f_audit_memo) {
        this.f_audit_memo = f_audit_memo;
    }

    /**
     * @return the value of t_sales_contract.f_status
     *
     * @mbggenerated
     */
    public Integer getF_status() {
        return f_status;
    }

    /**
     * @param f_status the value for t_sales_contract.f_status
     *
     * @mbggenerated
     */
    public void setF_status(Integer f_status) {
        this.f_status = f_status;
    }

    /**
     * @return the value of t_sales_contract.f_file_name
     *
     * @mbggenerated
     */
    public String getF_file_name() {
        return f_file_name;
    }

    /**
     * @param f_file_name the value for t_sales_contract.f_file_name
     *
     * @mbggenerated
     */
    public void setF_file_name(String f_file_name) {
        this.f_file_name = f_file_name;
    }

    /**
     * @return the value of t_sales_contract.f_remark
     *
     * @mbggenerated
     */
    public String getF_remark() {
        return f_remark;
    }

    /**
     * @param f_remark the value for t_sales_contract.f_remark
     *
     * @mbggenerated
     */
    public void setF_remark(String f_remark) {
        this.f_remark = f_remark;
    }

    /**
     * @return the value of t_sales_contract.f_oper_log
     *
     * @mbggenerated
     */
    public String getF_oper_log() {
        return f_oper_log;
    }

    /**
     * @param f_oper_log the value for t_sales_contract.f_oper_log
     *
     * @mbggenerated
     */
    public void setF_oper_log(String f_oper_log) {
        this.f_oper_log = f_oper_log;
    }
}