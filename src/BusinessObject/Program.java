package BusinessObject;

import DataObjects.CustomerDAO;
import DataObjects.OrderDAO;
import Entities.Customer;
import Entities.Order;
import Utilities.Menu;
import Utilities.DataInput;
import java.util.LinkedHashMap;

import java.util.Map;
import Entities.SetMenu;
import DataObjects.SetMenuDAO;
import java.util.List;

/**
 *
 * @author chanh
 */
public class Program {

    public static void main(String[] args) {
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
        try {

            do {
                System.out.println("\n***************Main Menu***************");
                Menu.printMenu(
                        "1. Register customers|2. Update customer information|"
                        + "3. Search customer information by name|4. Display feast menus|5. Place a feast order|"
                        + "6. Update order information|7. Save data to file|8. Display Customer or Order lists|Select:");

                choice = DataInput.getInteger("Enter your choice: ");
                switch (choice) {
                    case 1:
                        customerManagement.addNewCustomer();
                        break;
                    case 2:
                        customerManagement.updateCustomer();
                        break;
                    case 3:
                        customerManagement.displayAllCustomers(customerManagement.findCustomerByName());
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
                        System.out.println("Customer data has been successfully saved to “customer.txt”.");
                        orderDAO.save();
                        System.out.println("Order data has been successfully saved to “order.txt”.");
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void displayCustomerOrOrder( CustomerDAO customerDAO, CustomerManagement customerManagment,
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

            customerManagment.displayAllCustomersWithSorted(customers);

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
