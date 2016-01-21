/**
 * 
 */
package com.pkpmjc.erp.sales.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.pkpmjc.erp.sales.entity.Customer;
import com.pkpmjc.erp.sales.entity.CustomerExample;
import com.pkpmjc.erp.sales.mapper.CustomerMapper;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping(value = "/api")
public class CustomerController {
    private Log log = LogFactory.getLog(CustomerController.class);
    private Logger logger = Logger.getLogger(CustomerController.class);

    @Autowired
    private CustomerMapper customerMapper;

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> list(Map<String, Object> param) {
        logger.debug("logger" + param.toString());
        log.debug("log" + param.toString());
        CustomerExample example = new CustomerExample();
        return new ResponseEntity<List<Customer>>(customerMapper.selectByExampleWithBLOBs(example), HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.GET)
    public ResponseEntity<Customer> get(@PathVariable("id") Integer id) {
        return new ResponseEntity<Customer>(customerMapper.selectByPrimaryKey(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestBody Customer customer, UriComponentsBuilder ucBuilder) {
        // if (customerMapper.isExist(customer)) {
        // System.out.println("A customer with name " + customer.getName() + " already exist");
        // return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        // }

        customerMapper.insert(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/customers/{id}").buildAndExpand(customer.getF_id()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Customer> update(@PathVariable("id") Integer id, @RequestBody Customer customer) {
        System.out.println("Updating customer " + id);

        Customer curCustomer = customerMapper.selectByPrimaryKey(id);
        if (curCustomer == null) {
            System.out.println("Customer with id " + id + " not found");
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }

        customerMapper.updateByPrimaryKeyWithBLOBs(customer);
        return new ResponseEntity<Customer>(curCustomer, HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Customer> delete(@PathVariable("id") Integer id) {
        System.out.println("Fetching & Deleting customer with id " + id);

        Customer curCustomer = customerMapper.selectByPrimaryKey(id);
        if (curCustomer == null) {
            System.out.println("Unable to delete. Customer with id " + id + " not found");
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }

        customerMapper.deleteByPrimaryKey(id);
        return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.DELETE)
    public ResponseEntity<Customer> deleteAll() {
        System.out.println("Deleting all customers");

        CustomerExample example = new CustomerExample();
        customerMapper.deleteByExample(example);
        return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
    }
}
