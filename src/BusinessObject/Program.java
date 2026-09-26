package BusinessObject;

import DataObjects.CustomerDAO;
import DataObjects.OrderDAO;
import Entities.Customer;
import Entities.Order;
import Utilities.Menu;
import Utilities.Constants;
import Utilities.DataInput;
import java.util.LinkedHashMap;

import java.util.Map;
import Entities.SetMenu;
import DataObjects.SetMenuDAO;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

public class Program {

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, "UTF-8"));
        } catch (java.io.UnsupportedEncodingException e) {
            System.out.println("Warning: UTF-8 is not supported. Vietnamese characters may display incorrectly.");
        }
        
        Map<String, Customer> customers = new LinkedHashMap<>();
        CustomerDAO customerDAO = new CustomerDAO(customers);
        CustomerManagement customerManagement = new CustomerManagement(customerDAO);

        Map<String, SetMenu> setMenus = new LinkedHashMap<>();
        SetMenuDAO setMenuDAO = new SetMenuDAO(setMenus);
        SetMenuManagement setMenuManagement = new SetMenuManagement(setMenuDAO);

        Map<String, Order> orders = new LinkedHashMap<>();
        OrderDAO orderDAO = new OrderDAO(orders);
        OrderManagement orderManagement = new OrderManagement(orderDAO, customerDAO, setMenuDAO);

        orderDAO.load();
        setMenuDAO.load();
        customerDAO.load();

        int choice;
        do {
            System.out.println("\n***************Main Menu***************");
            Menu.printMenu(
                    "1. Register customers|2. Update customer information|"
                    + "3. Search customer information by name|4. Display feast menus|5. Place a feast order|"
                    + "6. Update order information|7. Save data to file|8. Display Customer or Order lists|Select:");

            try {
                choice = DataInput.getInteger("Enter your choice: ");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            switch (choice) {
                case 1:
                    customerManagement.addNewCustomer();
                    break;
                case 2:
                    customerManagement.updateCustomer();
                    break;
                case 3:
                    customerManagement.displaySearchResult(customerManagement.findCustomerByName());
                    break;
                case 4:
                    setMenuManagement.displaySetMenu(setMenuDAO.getAllSetMenu());
                    break;
                case 5:
                    orderManagement.placeTable();
                    break;
                case 6:
                    orderManagement.updateOrder();
                    break;
                case 7:
                    customerDAO.save();
                    System.out.println("Customer data has been successfully saved to “" + Constants.FILE_CUSTOMER + "”.");
                    orderDAO.save();
                    System.out.println("Order data has been successfully saved to “" + Constants.FILE_ORDER + "”.");
                    break;
                case 8:
                    displayCustomerOrOrder(customerDAO, customerManagement, orderDAO, orderManagement);
                    break;
                default:
                    System.out.println("Good bye");
                    System.exit(0);
                    break;

            }
        } while (true);

    }

    private static void displayCustomerOrOrder(CustomerDAO customerDAO, CustomerManagement customerManagement,
            OrderDAO orderDAO, OrderManagement orderManagement) {

        int displayChoice;

        System.out.println("========= DISPLAY CHOICE =========");
        System.out.println("1. Display all customers");
        System.out.println("2. Display all orders");

        try {
            displayChoice = DataInput.getPositiveIntNumber("Enter your choice: ");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        if (displayChoice == 1) {
            List<Customer> customers = customerDAO.getAllCustomers();

            if (customers == null || customers.isEmpty()) {
                System.out.println("No data in the system.");
                return;
            }

            customerManagement.displayAllCustomersWithSorted(customers);

        } else if (displayChoice == 2) {
            List<Order> orders = orderDAO.getAllOrders();

            if (orders == null || orders.isEmpty()) {
                System.out.println("No data in the system.");
                return;
            }

            orderManagement.displayAllOrderWithSorted(orders);

        } else {
            System.out.println("Invalid choice.");
        }
    }
}
