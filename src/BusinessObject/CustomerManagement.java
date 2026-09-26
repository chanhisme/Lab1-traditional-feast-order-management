package BusinessObject;

import DataObjects.CustomerDAO;
import Entities.Customer;
import Utilities.DataInput;
import Utilities.DataNormalize;
import Utilities.DataValidation;

import java.util.ArrayList;
import java.util.List;

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
        int choice;

        try {
            Customer customer = inputCustomer();
            if (customerDAO.findCustomerById(customer.getId()) != null) {
                System.out.println("Customer already exists!");
                return;
            }
            customerDAO.addCustomer(customer);
            customerDAO.save();
            System.out.println("[1] Register new customer");
            System.out.println("[2] Return menu");
            choice = DataInput.getIntegerMaxMin(1,2,"Enter you choice: ");
            if (choice == 2) {
                return;
            }
            addNewCustomer();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void displaySearchResult(List<Customer> customers) {
        if (customers == null || customers.isEmpty()) {
            System.out.println("No one matches the search criteria!");
            return;
        }
        printCustomerTable(customers);
    }

    public void displayAllCustomers(List<Customer> customers) {

        if (customers == null || customers.isEmpty()) {
            System.out.println("No data in the system.");
            return;
        }
        printCustomerTable(customers);
    }

    public void displayAllCustomersWithSorted(List<Customer> customers) {
        if (customers == null || customers.isEmpty()) {
            System.out.println("No data in the system.");
            return;
        }
        List<Customer> sorted = new ArrayList<>(customers);
        sorted.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        printCustomerTable(sorted);
    }

    private void printCustomerTable(List<Customer> customers) {
        String rowFormat = "%-5s | %-20s | %-12s | %-25s%n";
        String line = "-----------------------------------------------------------------------";
        System.out.println(line);
        System.out.printf(rowFormat, "Code", "Customer Name", "Phone", "Email");
        System.out.println(line);

        for (Customer customer : customers) {
            System.out.println(customer.toString());
        }

        System.out.println(line);
    }

    public void updateCustomer() {
        try {
            String id = DataNormalize.normalizeId(DataInput.getString("Enter customer id: "));
            Customer customer = customerDAO.findCustomerById(id);
            if (customer == null) {
                System.out.println("This customer does not exist.");
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
}
