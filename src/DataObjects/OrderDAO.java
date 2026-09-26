package DataObjects;

import Entities.Order;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import Utilities.Constants;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

public class OrderDAO {

    private final Map<String, Order> orderMap;
    private static final String PATH = Constants.FILE_ORDER;
    private static final String HEADER = "OrderID,CustomerID,SetMenuID,EventDate,NumberOfTables,TotalCost";
    private static final DateTimeFormatter DATE_FORMATTER = Constants.DATE_FORMATTER;

    
    public OrderDAO(Map<String, Order> orderMap) {
        this.orderMap = orderMap;
    }

    public ArrayList<Order> getAllOrders() {
        return new ArrayList<>(orderMap.values());
    }

    public void save() {
        try ( BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(PATH), StandardCharsets.UTF_8))) {
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
        try ( BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(PATH), StandardCharsets.UTF_8))) {
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
