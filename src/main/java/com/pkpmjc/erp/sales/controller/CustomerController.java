/**
 * 
 */
package com.pkpmjc.erp.sales.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pkpmjc.erp.sales.entity.Customer;
import com.pkpmjc.erp.sales.entity.CustomerExample;
import com.pkpmjc.erp.sales.mapper.CustomerMapper;

/**
 * @author Administrator
 *
 */
@RestController
public class CustomerController {
    @Autowired
    private CustomerMapper customerMapper;

    @RequestMapping("/customers")
    public List<Customer> list(Map<String, Object> param) {
        CustomerExample example = new CustomerExample();
        return customerMapper.selectByExampleWithBLOBs(example);
    }
}
