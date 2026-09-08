/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataObjects;

import Entities.Customer;

import java.util.List;
import java.util.Map;


/**
 *
 * @author chanh
 */
public class CustomerDAO {
    private final Map<String, Customer> customers;

    public CustomerDAO(Map<String, Customer> customers) {
        this.customers = customers;
    }

    public void addCustomer(Customer customer){
        customers.put(customer.getId(), customer);
    }

    public Customer getCustomer(String id){
        return customers.get(id);
    }

}
