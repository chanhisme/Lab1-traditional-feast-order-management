package DataObjects;

import Entities.Order;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class OrderDAO {

    private final Map<String, Order> orderMap;
    private final String PATH = "order.txt";
    private static final String HEADER = "OrderID,CustomerID,SetMenuID,EventDate,NumberOfTables,TotalCost";
    private static final DateTimeFormatter DATE_FORMATTER
            = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    
    public OrderDAO(Map<String, Order> orderMap) {
        this.orderMap = orderMap;
    }

    public ArrayList<Order> getAllOrders() {
        return new ArrayList<>(orderMap.values());
    }

    public void save() {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(PATH))) {
            writer.write(HEADER);
            writer.newLine();
            for (Order order : orderMap.values()) {
                writer.write(
                        order.getOrderId() + ","
                        + order.getCustomerId() + ","
                        + order.getSetMenuId() + ","
                        + order.getEventDate().format(DATE_FORMATTER) + ","
                        + order.getNumberOfTables() + ","
                        + order.getTotalCost()
                );
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void load() {
        try ( BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String orderId = parts[0].trim();
                String customerId = parts[1].trim();
                String setMenuId = parts[2].trim();
                LocalDate eventDate = LocalDate.parse(parts[3].trim(), DATE_FORMATTER);
                int numberOfTables = Integer.parseInt(parts[4].trim());
                long totalCost = Long.parseLong(parts[5].trim());

                Order order = new Order(orderId, customerId, setMenuId,
                        eventDate, numberOfTables, totalCost);
                orderMap.put(orderId, order);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addOrder(Order order) {
        orderMap.put(order.getOrderId(), order);
    }

    public String generateOrderId() {
        int id = 1;

        while (orderMap.containsKey(String.valueOf(id))) {
            id++;
        }

        return String.valueOf(id);
    }

    public Order findOrderById(String id) {
        return orderMap.get(id);
    }
 
}
