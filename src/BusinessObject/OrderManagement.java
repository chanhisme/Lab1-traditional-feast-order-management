/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessObject;

import DataObjects.CustomerDAO;
import DataObjects.OrderDAO;
import DataObjects.SetMenuDAO;
import Entities.Customer;
import Entities.Order;
import Entities.SetMenu;
import Utilities.DataInput;
import Utilities.DataValidation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author chanh
 */
public class OrderManagement {

    private final OrderDAO orderDAO;
    private final CustomerDAO customerDAO;
    private final SetMenuDAO setMenuDAO;

    public OrderManagement(OrderDAO orderDAO, CustomerDAO customerDAO, SetMenuDAO setMenuDAO) {
        this.orderDAO = orderDAO;
        this.customerDAO = customerDAO;
        this.setMenuDAO = setMenuDAO;
    }

    public void displayOneOrder(Order order) {
        Customer customer = customerDAO.findCustomer(order.getCustomerId());
        if (customer == null) {
            System.out.println("Customer with id " + order.getCustomerId() + " not found");
            return;
        }

        SetMenu setMenu = setMenuDAO.findSetMenu(order.getSetMenuId());
        if (setMenu == null) {
            System.out.println("Set Menu with id " + order.getSetMenuId() + " not found");
            return;
        }

        System.out.println("----------------------------------------------------------------");
        System.out.printf("Customer order information [Order ID: %s]\n", order.getOrderId());
        System.out.println("----------------------------------------------------------------");
        System.out.printf("%-15s: %s\n", "Code ", customer.getId());
        System.out.printf("%-15s: %s\n", "Customer name", customer.getName());
        System.out.printf("%-15s: %s\n", "Phone number", customer.getPhone());
        System.out.printf("%-15s: %s\n", "Email", customer.getEmail());
        System.out.println("----------------------------------------------------------------");
        System.out.printf("%-20s: %s\n", "Code of Set Menu", setMenu.getId());
        System.out.printf("%-20s: %s\n", "Set menu name", setMenu.getName());
        System.out.printf("%-20s: %d\n", "Number of tables", order.getNumberOfTables());
        System.out.printf("%-20s: %s Vnd\n", "Set menu price", SetMenuManagement.formatNumber(setMenu.getPrice()));

        List<String> starter = setMenu.getIngredient().get("Khai vị");
        List<String> mainCourse = setMenu.getIngredient().get("Món chính");
        List<String> desert = setMenu.getIngredient().get("Tráng miệng");

        System.out.print("+ Khai vị: ");
        for (int i = 0; i < starter.size(); i++) {
            System.out.print(starter.get(i));
            if (i < starter.size() - 1) {
                System.out.print("; ");
            }
        }
        System.out.println();
        System.out.print("+ Món chính: ");
        for (int i = 0; i < mainCourse.size(); i++) {
            System.out.print(mainCourse.get(i));
            if (i < mainCourse.size() - 1) {
                System.out.print("; ");
            }
        }
        System.out.println();

        System.out.print("+ Tráng miệng: ");
        for (int i = 0; i < desert.size(); i++) {
            System.out.print(desert.get(i));
            if (i < desert.size() - 1) {
                System.out.print("; ");
            }
        }
        System.out.println("\n----------------------------------------------------------------");
        System.out.printf("%-20s: %s Vnd\n", "Total cost", SetMenuManagement.formatNumber(order.getTotalCost()));
        System.out.println("----------------------------------------------------------------");

    }

    public void placeTable() {
        List<Order> orders = orderDAO.getAllOrders();

        LocalDate eventDate = null;
        int numberOfTable = 0;

        String customerId = DataInput.getString("Enter customer id: ");
        Customer customer = customerDAO.findCustomer(customerId);
        if (customer == null) {
            System.out.println("Customer not found");
            return;
        }

        String setMenuId = DataInput.getString("Enter set menu id: ");
        SetMenu setMenu = setMenuDAO.findSetMenu(setMenuId);
        if (setMenu == null) {
            System.out.println("Set Menu not found");
            return;
        }

        try {
            numberOfTable = DataInput.getPositiveIntNumber("Enter the number of tables: ");

        } catch (Exception e) {
            System.out.println("Please enter a number must be greater than zero");
            return;
        }

        try {
            eventDate = DataInput.getLocalDate("Enter event date: ");
            if(!DataValidation.isFutureDate(eventDate)){
                throw new Exception("The date must be in the future");
            }
        } catch (Exception e) {
            System.out.println("Please enter a valid event date");
            return;
        }
        for (Order order : orders) {
            if (isDuplicateOrder(order, customerId, setMenuId, eventDate)) {
                System.out.println("Dupplicate data!");
                return;
            }
        }

        Order order = new Order(orderDAO.generateOrderId(), customerId, setMenuId, eventDate, numberOfTable);
        orderDAO.addOrder(order);
        order.setTotalCost(setMenuDAO.findSetMenu(setMenuId).getPrice());
        displayOneOrder(order);
        orderDAO.save();

    }

    private boolean isDuplicateOrder( Order order, String customerId, String setMenuId, LocalDate eventDate) {
        
        return order.getCustomerId().equalsIgnoreCase(customerId)
                && order.getSetMenuId().equalsIgnoreCase(setMenuId)
                && order.getEventDate().equals(eventDate);
    }
    
    

}
