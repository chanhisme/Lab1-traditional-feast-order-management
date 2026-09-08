package BusinessObject;

import DataObjects.CustomerDAO;
import Entities.Customer;
import Utilities.Menu;
import Utilities.DataInput;
import java.util.LinkedHashMap;

import java.util.Map;

/**
 *
 * @author chanh
 */
public class Program {

    public static void main(String[] args) {
        Map<String, Customer> customers = new LinkedHashMap<>();
        CustomerDAO customerDAO = new CustomerDAO(customers);
        CustomerManagment customerManagment = new CustomerManagment(customerDAO);

        customerDAO.load();
        int choice;
        try {

            do {
                System.out.println("***************Main Menu***************");
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
    public static void DisplayCustomerOrOrder() {

    }

}
