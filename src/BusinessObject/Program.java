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

/**
 *
 * @author chanh
 */
public class Program {

    public static void main(String[] args) {
        Map<String, Customer> customers = new LinkedHashMap<>();
        CustomerDAO customerDAO = new CustomerDAO(customers);
        CustomerManagement customerManagment = new CustomerManagement(customerDAO);
        
        Map<String, SetMenu> setMenus = new LinkedHashMap<>();
        SetMenuDAO setMenuDAO = new SetMenuDAO(setMenus);
        SetMenuManagement setMenuManagement = new SetMenuManagement(setMenuDAO);

        Map <String, Order> orders = new LinkedHashMap<>();
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
                        customerManagment.addNewCustomer();
                        break;
                    case 2:
                        customerManagment.updateCustomer();
                        break;
                    case 3:
                        customerManagment.displayAllCustomers(customerManagment.findCustomerByName());
                        break;
                    case 4: 
                        setMenuManagement.displaySetMenu(setMenus);
                        break;
                    case 5:
                        orderManagement.placeTable();
                        break;
                    case 6: 
                    case 8:
                        customerManagment.displayAllCustomers(customerDAO.getAllCustomers());
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


}
