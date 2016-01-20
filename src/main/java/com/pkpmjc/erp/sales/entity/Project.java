package com.pkpmjc.erp.sales.entity;

public class Project {
    /**
     * f_id
     *
     * @mbggenerated
     */
    private Integer f_id;

    /**
     * f_project_no - 工程编号
     *
     * @mbggenerated
     */
    private String f_project_no;

    /**
     * f_project_name - 工程名称
     *
     * @mbggenerated
     */
    private String f_project_name;

    /**
     * f_contract_id - 所属合同
     *
     * @mbggenerated
     */
    private Integer f_contract_id;

    /**
     * f_customer_id - 所属客户ID
     *
     * @mbggenerated
     */
    private Integer f_customer_id;

    /**
     * f_build_company - 施工单位，意义不大，主要用于打印发货单，默认同客户名称
     *
     * @mbggenerated
     */
    private String f_build_company;

    /**
     * f_upper_num - 供应上限方量，0表示不受限制，其他数字表示超过该数字时，不允许再生产
     *
     * @mbggenerated
     */
    private Double f_upper_num;

    /**
     * f_linker - 联系人
     *
     * @mbggenerated
     */
    private String f_linker;

    /**
     * f_link_phone - 联系电话
     *
     * @mbggenerated
     */
    private String f_link_phone;

    /**
     * f_address - 地址
     *
     * @mbggenerated
     */
    private String f_address;

    /**
     * f_distance - 运输距离
     *
     * @mbggenerated
     */
    private Double f_distance;

    /**
     * f_salesman - 业务员，关联人员表
     *
     * @mbggenerated
     */
    private Integer f_salesman;

    /**
     * f_contract_record_no - 合同备案号，实验室会用到此编号。
     *
     * @mbggenerated
     */
    private String f_contract_record_no;

    /**
     * f_order - 调度时排列顺序
     *
     * @mbggenerated
     */
    private Integer f_order;

    /**
     * f_status - 工程状态，1表示启用，2表示不启用，9表示工程已经完成
     *
     * @mbggenerated
     */
    private Integer f_status;

    /**
     * f_remark - 备注说明
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
     * @return the value of t_sales_project.f_id
     *
     * @mbggenerated
     */
    public Integer getF_id() {
        return f_id;
    }

    /**
     * @param f_id the value for t_sales_project.f_id
     *
     * @mbggenerated
     */
    public void setF_id(Integer f_id) {
        this.f_id = f_id;
    }

    /**
     * @return the value of t_sales_project.f_project_no
     *
     * @mbggenerated
     */
    public String getF_project_no() {
        return f_project_no;
    }

    /**
     * @param f_project_no the value for t_sales_project.f_project_no
     *
     * @mbggenerated
     */
    public void setF_project_no(String f_project_no) {
        this.f_project_no = f_project_no;
    }

    /**
     * @return the value of t_sales_project.f_project_name
     *
     * @mbggenerated
     */
    public String getF_project_name() {
        return f_project_name;
    }

    /**
     * @param f_project_name the value for t_sales_project.f_project_name
     *
     * @mbggenerated
     */
    public void setF_project_name(String f_project_name) {
        this.f_project_name = f_project_name;
    }

    /**
     * @return the value of t_sales_project.f_contract_id
     *
     * @mbggenerated
     */
    public Integer getF_contract_id() {
        return f_contract_id;
    }

    /**
     * @param f_contract_id the value for t_sales_project.f_contract_id
     *
     * @mbggenerated
     */
    public void setF_contract_id(Integer f_contract_id) {
        this.f_contract_id = f_contract_id;
    }

    /**
     * @return the value of t_sales_project.f_customer_id
     *
     * @mbggenerated
     */
    public Integer getF_customer_id() {
        return f_customer_id;
    }

    /**
     * @param f_customer_id the value for t_sales_project.f_customer_id
     *
     * @mbggenerated
     */
    public void setF_customer_id(Integer f_customer_id) {
        this.f_customer_id = f_customer_id;
    }

    /**
     * @return the value of t_sales_project.f_build_company
     *
     * @mbggenerated
     */
    public String getF_build_company() {
        return f_build_company;
    }

    /**
     * @param f_build_company the value for t_sales_project.f_build_company
     *
     * @mbggenerated
     */
    public void setF_build_company(String f_build_company) {
        this.f_build_company = f_build_company;
    }

    /**
     * @return the value of t_sales_project.f_upper_num
     *
     * @mbggenerated
     */
    public Double getF_upper_num() {
        return f_upper_num;
    }

    /**
     * @param f_upper_num the value for t_sales_project.f_upper_num
     *
     * @mbggenerated
     */
    public void setF_upper_num(Double f_upper_num) {
        this.f_upper_num = f_upper_num;
    }

    /**
     * @return the value of t_sales_project.f_linker
     *
     * @mbggenerated
     */
    public String getF_linker() {
        return f_linker;
    }

    /**
     * @param f_linker the value for t_sales_project.f_linker
     *
     * @mbggenerated
     */
    public void setF_linker(String f_linker) {
        this.f_linker = f_linker;
    }

    /**
     * @return the value of t_sales_project.f_link_phone
     *
     * @mbggenerated
     */
    public String getF_link_phone() {
        return f_link_phone;
    }

    /**
     * @param f_link_phone the value for t_sales_project.f_link_phone
     *
     * @mbggenerated
     */
    public void setF_link_phone(String f_link_phone) {
        this.f_link_phone = f_link_phone;
    }

    /**
     * @return the value of t_sales_project.f_address
     *
     * @mbggenerated
     */
    public String getF_address() {
        return f_address;
    }

    /**
     * @param f_address the value for t_sales_project.f_address
     *
     * @mbggenerated
     */
    public void setF_address(String f_address) {
        this.f_address = f_address;
    }

    /**
     * @return the value of t_sales_project.f_distance
     *
     * @mbggenerated
     */
    public Double getF_distance() {
        return f_distance;
    }

    /**
     * @param f_distance the value for t_sales_project.f_distance
     *
     * @mbggenerated
     */
    public void setF_distance(Double f_distance) {
        this.f_distance = f_distance;
    }

    /**
     * @return the value of t_sales_project.f_salesman
     *
     * @mbggenerated
     */
    public Integer getF_salesman() {
        return f_salesman;
    }

    /**
     * @param f_salesman the value for t_sales_project.f_salesman
     *
     * @mbggenerated
     */
    public void setF_salesman(Integer f_salesman) {
        this.f_salesman = f_salesman;
    }

    /**
     * @return the value of t_sales_project.f_contract_record_no
     *
     * @mbggenerated
     */
    public String getF_contract_record_no() {
        return f_contract_record_no;
    }

    /**
     * @param f_contract_record_no the value for t_sales_project.f_contract_record_no
     *
     * @mbggenerated
     */
    public void setF_contract_record_no(String f_contract_record_no) {
        this.f_contract_record_no = f_contract_record_no;
    }

    /**
     * @return the value of t_sales_project.f_order
     *
     * @mbggenerated
     */
    public Integer getF_order() {
        return f_order;
    }

    /**
     * @param f_order the value for t_sales_project.f_order
     *
     * @mbggenerated
     */
    public void setF_order(Integer f_order) {
        this.f_order = f_order;
    }

    /**
     * @return the value of t_sales_project.f_status
     *
     * @mbggenerated
     */
    public Integer getF_status() {
        return f_status;
    }

    /**
     * @param f_status the value for t_sales_project.f_status
     *
     * @mbggenerated
     */
    public void setF_status(Integer f_status) {
        this.f_status = f_status;
    }

    /**
     * @return the value of t_sales_project.f_remark
     *
     * @mbggenerated
     */
    public String getF_remark() {
        return f_remark;
    }

    /**
     * @param f_remark the value for t_sales_project.f_remark
     *
     * @mbggenerated
     */
    public void setF_remark(String f_remark) {
        this.f_remark = f_remark;
    }

    /**
     * @return the value of t_sales_project.f_oper_log
     *
     * @mbggenerated
     */
    public String getF_oper_log() {
        return f_oper_log;
    }

    /**
     * @param f_oper_log the value for t_sales_project.f_oper_log
     *
     * @mbggenerated
     */
    public void setF_oper_log(String f_oper_log) {
        this.f_oper_log = f_oper_log;
    }
}