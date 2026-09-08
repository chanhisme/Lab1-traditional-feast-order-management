/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessObject;

import DataObjects.CustomerDAO;
import Entities.Customer;
import Utilities.DataInput;
import java.util.List;

/**
 *
 * @author chanh
 */
public class CustomerManagment {

    private final CustomerDAO customerDAO;

    public CustomerManagment(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public Customer inputCustomer() throws Exception {
        String id = DataInput.getString("Enter customer id: ");
        String name = DataInput.getString("Enter customer name: ");
        String phone = DataInput.getString("Enter customer phone number: ");
        String email = DataInput.getString("Enter customer email: ");
        return new Customer(id, name, phone, email);
    }

    public void addNewCustomer() {
        try {
            Customer customer = inputCustomer();
            if (customerDAO.findCustomer(customer.getId()) != null) {
                System.out.println("Customer already exists!");
                return;
            }
            customerDAO.addCustomer(customer);
            customerDAO.save();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayAllCustomers(List<Customer> customers) {
        System.out.println("----------------------------------------------------------------");
        System.out.printf(
                "%-5s | %-20s | %-10s | %-25s%n",
                "Code", "Customer Name", "Phone", "Email"
        );
        System.out.println("----------------------------------------------------------------");

        for (Customer customer : customers) {

            System.out.printf(
                    "%s | %s | %s | %s%n",
                    customer.getId(),
                    formatName(customer.getName()),
                    customer.getPhone(),
                    customer.getEmail()
            );
        }

        System.out.println("----------------------------------------------------------------");
    }

    public String formatName(String name) {
        String[] parts = name.trim().split("\\s+");

        if (parts.length < 2) {
            return name;
        }

        String lastName = parts[parts.length - 1];

        StringBuilder firstPart = new StringBuilder();

        for (int i = 0; i < parts.length - 1; i++) {
            if (i > 0) {
                firstPart.append(" ");
            }
            firstPart.append(parts[i]);
        }

        return lastName + ", " + firstPart;
    }
}
