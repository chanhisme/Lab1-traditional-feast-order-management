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
import Utilities.DataNormalize;
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
        Customer customer = customerDAO.findCustomerById(order.getCustomerId());
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

        SetMenuManagement.displayDish("+ Khai vị: ", setMenu.getIngredient().get("Khai vị"));
        SetMenuManagement.displayDish("+ Món chính: ", setMenu.getIngredient().get("Món chính"));
        SetMenuManagement.displayDish("+ Tráng miệng: ", setMenu.getIngredient().get("Tráng miệng"));

        System.out.println("\n----------------------------------------------------------------");
        System.out.printf("%-20s: %s Vnd\n", "Total cost", SetMenuManagement.formatNumber(order.getTotalCost()));
        System.out.println("----------------------------------------------------------------");

    }

    public void placeTable() {
        List<Order> orders = orderDAO.getAllOrders();

        LocalDate eventDate = null;
        int numberOfTable = 0;

        String customerId = DataNormalize.normalizeId(DataInput.getString("Enter customer id: "));
        Customer customer = customerDAO.findCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer not found");
            return;
        }

        String setMenuId = DataNormalize.normalizeId(DataInput.getString("Enter set menu id: "));
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
            if (!DataValidation.isFutureDate(eventDate)) {
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

    private boolean isDuplicateOrder(Order order, String customerId, String setMenuId, LocalDate eventDate) {

        return order.getCustomerId().equalsIgnoreCase(customerId)
                && order.getSetMenuId().equalsIgnoreCase(setMenuId)
                && order.getEventDate().equals(eventDate);
    }

    //UPDATE METHOD
    public void updateOrder() {
        String id = DataNormalize.normalizeId(DataInput.getString("Enter order id: "));

        Order order = orderDAO.findOrderById(id);
        if (order == null) {
            System.out.println("This Order does not exist.");
            return;
        }
        if (setNewOrder(order)) {
            orderDAO.save();
            System.out.println("Update order successfully!");
        } else {
            System.out.println("Update order failed.");
        }
    }

    public boolean setNewOrder(Order order) {
        String newSetMenuId = inputNewSetMenuId(order);
        if (newSetMenuId == null) {
            return false;
        }

        int newNumberOfTables = inputNewNumberOfTables(order);
        if (newNumberOfTables == -1) {
            return false;
        }

        LocalDate newEventDate = inputNewEventDate(order);
        if (newEventDate == null) {
            return false;
        }

        if (hasDuplicateOrder(order, newSetMenuId, newEventDate)) {
            System.out.println("Duplicate data! This customer already ordered this menu on the selected date.");
            return false;
        }

        SetMenu setMenu = setMenuDAO.findSetMenu(newSetMenuId);
        order.setSetMenuId(newSetMenuId);
        order.setNumberOfTables(newNumberOfTables);
        order.setEventDate(newEventDate);
        order.setTotalCost(setMenu.getPrice());
        return true;
    }

    //HELP METHOD FOR setNewOrder
    private String inputNewSetMenuId(Order order) {
        String input = DataInput.getString("Enter new set menu id (leave empty to keep current): ");
        if (input.isEmpty()) {
            return order.getSetMenuId();
        }
        String normalized = DataNormalize.normalizeId(input);
        if (setMenuDAO.findSetMenu(normalized) == null) {
            System.out.println("This Set Menu does not exist!");
            return null;
        }
        return normalized;
    }

    private int inputNewNumberOfTables(Order order) {
        String input = DataInput.getString("Enter new number of tables (leave empty to keep current): ");
        if (input.isEmpty()) {
            return order.getNumberOfTables();
        }
        try {
            if (!input.matches("\\d{1,10}")) {
                throw new Exception("Data invalid. Must be a valid integer.");
            }
            int value = Integer.parseInt(input);
            if (!DataValidation.checkPositiveNumber(value)) {
                throw new Exception("Number of tables must be > 0");
            }
            return value;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    private LocalDate inputNewEventDate(Order order) {
        String input = DataInput.getString("Enter new event date (leave empty to keep current): ");
        if (input.isEmpty()) {
            return order.getEventDate();
        }
        try {
            String normalized = DataNormalize.normalizeDate(input);
            if (!DataValidation.checkDate(normalized)) {
                throw new Exception("Date invalid. The format must be dd/MM/yyyy");
            }
            LocalDate date = LocalDate.parse(normalized, DataValidation.DATE_FORMATTER);
            if (!DataValidation.isFutureDate(date)) {
                throw new Exception("The date must be in the future");
            }
            return date;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private boolean hasDuplicateOrder(Order current, String menuId, LocalDate date) {
        for (Order other : orderDAO.getAllOrders()) {
            if (!other.getOrderId().equalsIgnoreCase(current.getOrderId())) {
                if (isDuplicateOrder(other, current.getCustomerId(), menuId, date)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void displayAllOrder(List<Order> orders) {
        System.out.println("ID | Event date | Customer ID | Set Menu | Price | Tables | Cost");

        for (Order order : orders) {
            System.out.printf(
                    "%-4s | %-10s | %-11s | %-8s | %,14.0f | %6d | %,12d%n",
                    order.getOrderId(),
                    order.getEventDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    order.getCustomerId(),
                    order.getSetMenuId(),
                    setMenuDAO.findSetMenu(order.getSetMenuId()).getPrice(),
                    order.getNumberOfTables(),
                    order.getTotalCost()
            );
        }
    }

    public void displayAllOrderWithSorted(List<Order> orders) {

        orders.sort((o1, o2) -> {
            return o1.getEventDate().compareTo(o2.getEventDate());
        });

        System.out.printf(
                "%-4s | %-10s | %-11s | %-8s | %14s | %6s | %12s%n",
                "ID",
                "Event date",
                "Customer ID",
                "Set Menu",
                "Price",
                "Tables",
                "Cost"
        );

        for (Order order : orders) {
            System.out.printf(
                    "%-4s | %-10s | %-11s | %-8s | %,14.0f | %6d | %,12d%n",
                    order.getOrderId(),
                    order.getEventDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    order.getCustomerId(),
                    order.getSetMenuId(),
                    setMenuDAO.findSetMenu(order.getSetMenuId()).getPrice(),
                    order.getNumberOfTables(),
                    order.getTotalCost()
            );
        }
    }
}
