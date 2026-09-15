/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package BusinessObject;

import DataObjects.CustomerDAO;
import Entities.Customer;
import Utilities.DataInput;
import Utilities.DataNormalize;
import Utilities.DataValidation;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author chanh
 */
public class CustomerManagement {

    private final CustomerDAO customerDAO;

    public CustomerManagement(CustomerDAO customerDAO) {
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
            if (customerDAO.findCustomerById(customer.getId()) != null) {
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

        if (customers == null || customers.isEmpty()) {
            System.out.println("this customer list is empty");
            return;
        }

        String rowFormat = "%-5s | %-20s | %-12s | %-25s%n";
        String line = "-----------------------------------------------------------------------";
        System.out.println(line);
        System.out.printf(rowFormat, "Code", "Customer Name", "Phone", "Email");
        System.out.println(line);

        for (Customer customer : customers) {

            System.out.printf(rowFormat, customer.getId(), formatName(customer.getName()), customer.getPhone(),
                    customer.getEmail());
        }

        System.out.println(line);
    }

    public void displayAllCustomersWithSorted(List<Customer> customers) {
        if (customers == null || customers.isEmpty()) {
            displayAllCustomers(customers);
            return;
        }
        customers.sort((c1, c2) -> {
            return c1.getName().compareToIgnoreCase(c2.getName());
        });
        displayAllCustomers(customers);
    }

    public void updateCustomer() {
        try {
            String id = DataNormalize.normalizeId(DataInput.getString("Enter customer id: "));
            Customer customer = customerDAO.findCustomerById(id);
            if (customer == null) {
                System.out.println(">>The customer not found.");
                return;
            }

            if (setNewCustomer(customer)) {
                customerDAO.save();
                System.out.println(">>The customer has updated successfully.");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean setNewCustomer(Customer customer) {
        String oldName = customer.getName();
        String oldPhone = customer.getPhone();
        String oldEmail = customer.getEmail();
        boolean isSuccess = true;

        String nameInput = DataInput.getString("Enter new name: ");
        String phoneInput = DataInput.getString("Enter new phone: ");
        String emailInput = DataInput.getString("Enter new email: ");

        try {
            if (DataValidation.isNonEmptyString(nameInput)) {
                customer.setName(nameInput);
            }
            if (DataValidation.isNonEmptyString(phoneInput)) {
                customer.setPhone(phoneInput);
            }
            if (DataValidation.isNonEmptyString(emailInput)) {
                customer.setEmail(emailInput);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

            try {
                customer.setName(oldName);
                customer.setPhone(oldPhone);
                customer.setEmail(oldEmail);
            } catch (Exception restoreEx) {
                System.out.println("Rollback failed: " + restoreEx.getMessage());
            }
            isSuccess = false;
        }
        return isSuccess;
    }

    public ArrayList<Customer> findCustomerByName() {
        ArrayList<Customer> result = null;
        String name = DataNormalize.normalizeString(DataInput.getString("Enter customer name: "));
        if (name != null) {
            result = customerDAO.findCustomerByName(name);
        }
        return result;
    }

    private String formatName(String name) {
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
