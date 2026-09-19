package DataObjects;

import Entities.Customer;
import Utilities.Constants;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Map;
public class CustomerDAO {

    private static final String FILE_NAME = Constants.FILE_CUSTOMER;
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

    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String parts[] = line.split(",");
                if (parts.length < 4) {
                    continue;
                }
                String id = parts[0].trim();
                String name = parts[1].trim();
                String phone = parts[2].trim();
                String email = parts[3].trim();
                try {
                    customers.put(id, new Customer(id, name, phone, email));
                } catch (Exception e) {
                    System.out.println("Skipped invalid customer line: " + line);
                }
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
