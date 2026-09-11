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

    public void placeTable() {
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
            numberOfTable = DataInput.getInteger();

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


        orderDAO.addOrder(new Order( orderDAO.generateOrderId(), customerId, setMenuId, eventDate, numberOfTable));
    }
}
