package BusinessObject;

import DataObjects.CustomerDAO;
import Entities.Customer;
import Utilities.Menu;
import Utilities.DataInput;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author chanh
 */
public class Program {
    public static void main(String[] args) {
        Map<String, Customer> customers = new TreeMap<>();
        CustomerDAO customerDAO = new CustomerDAO(customers);
        CustomerManagment customerManagment = new CustomerManagment(customerDAO);
        int choice;
        try {

            do {
                System.out.println("***************Main Menu***************");
                Menu.printMenu(
                        "1. Register customers|2. Update customer information|" +
                        "3. Search customer information by name|4. Display feast menus|5. Place a feast order|"+
                        "6. Update order information|7. Save data to file|8. Display Customer or Order lists|Select:");

                choice = DataInput.getInteger("Enter your choice: ");
                switch (choice) {
                    case 1:
                        customerManagment.addNewCustomer();
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
