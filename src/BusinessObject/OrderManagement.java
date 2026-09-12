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
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
        System.out.printf("%-15s: %s\n","Phone number", customer.getPhone());
        System.out.printf("%-15s: %s\n", "Email", customer.getEmail());
        System.out.println("----------------------------------------------------------------");
        System.out.printf("%-20s: %s\n", "Code of Set Menu", setMenu.getId());
        System.out.printf("%-20s: %s\n", "Set menu name", setMenu.getName());
        System.out.printf("%-20s: %d\n", "Number of tables",order.getNumberOfTables());
        System.out.printf("%-20s: %s Vnd\n","Set menu price", SetMenuManagement.formatNumber(setMenu.getPrice()));

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
            numberOfTable = DataInput.getInteger("Enter the number of tables: ");

            if (numberOfTable <= 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("Please enter a number must be greater than zero");
        }

        try {
            String date = DataInput.getString("Enter event date: ");
            if (date == null || date.isEmpty()) {
                throw new Exception("Please enter a valid event date");
            }
            eventDate = LocalDate.parse(date, DATE_FORMATTER);

        } catch (Exception e) {
            System.out.println("Please enter a valid event date");
        }
        for (Order order : orders) {
            if (order.getCustomerId().equalsIgnoreCase(customerId)
                    && order.getSetMenuId().equalsIgnoreCase(setMenuId)
                    && order.getEventDate().equals(eventDate)) {

            }
            System.out.println("Dupplicate data!");
            return;
        }
        Order order = new Order(orderDAO.generateOrderId(), customerId, setMenuId, eventDate, numberOfTable);
        orderDAO.addOrder(order);
        order.setTotalCost(setMenuDAO.findSetMenu(setMenuId).getPrice());
        displayOneOrder(order);
        orderDAO.save();

    }


}
