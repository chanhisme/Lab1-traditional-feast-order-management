/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataObjects;

import Entities.Customer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Map;
/**
 *
 * @author chanh
 */
public class CustomerDAO {

    private final String FILE_NAME = "customer.txt";
    private final Map<String, Customer> customers;

    public CustomerDAO(Map<String, Customer> customers) {
        this.customers = customers;
    }

    public void addCustomer(Customer customer) {
        customers.put(customer.getId(), customer);
    }

    public Customer findCustomerById(String id) {
        return customers.get(id);
    }

    public ArrayList<Customer> findCustomerByName(String targetName) {
        ArrayList<Customer> result = new ArrayList<>();

        targetName = targetName.trim();

        for (Map.Entry<String, Customer> entry : customers.entrySet()) {
            Customer customer = entry.getValue();

            if (customer.getName().toLowerCase().contains(targetName.toLowerCase())) {
                result.add(customer);
            }
        }
        result.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));

        return result;
    }

    public ArrayList<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    // public void updateCustomer(Customer customer) throws Exception {
    // Customer cus = findCustomerById(customer.getId());
    // if (cus != null) {
    // cus.setName(customer.getName());
    // cus.setPhone(customer.getPhone());
    // cus.setEmail(customer.getEmail());
    // }
    // }

    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String parts[] = line.split(", ");
                String id = parts[0];
                String name = parts[1];
                String phone = parts[2];
                String email = parts[3];
                customers.put(id, new Customer(id, name, phone, email));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write("Id, name, phone, email");
            writer.newLine();
            for (Customer customer : customers.values()) {
                writer.write(
                        customer.getId() + ", "
                                + customer.getName() + ", "
                                + customer.getPhone() + ", "
                                + customer.getEmail());

                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
