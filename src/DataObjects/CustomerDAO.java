/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataObjects;

import Entities.Customer;

import java.util.List;

/**
 *
 * @author chanh
 */
public class CustomerDAO {
    private final List<Customer> customers;

    public CustomerDAO(List<Customer> customers) {
        this.customers = customers;
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }
}
