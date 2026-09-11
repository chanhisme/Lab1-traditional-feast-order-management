package Entities;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Order {

    private String orderId;
    private String customerId;
    private String setMenuId;
    private LocalDate eventDate;
    private int numberOfTables;
    private double totalCost;

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Order(String orderId, String customerId, String setMenuId,
                 LocalDate eventDate, int numberOfTables) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.setMenuId = setMenuId;
        this.eventDate = eventDate;
        this.numberOfTables = numberOfTables;
    }

    public Order(String orderId, String customerId, String setMenuId,
                 LocalDate eventDate, int numberOfTables, double totalCost) {
        this(orderId, customerId, setMenuId, eventDate, numberOfTables);
        this.totalCost = totalCost;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getSetMenuId() {
        return setMenuId;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public int getNumberOfTables() {
        return numberOfTables;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public void setNumberOfTables(int numberOfTables) {
        this.numberOfTables = numberOfTables;
    }

    public void setTotalCost(double uniCost) {
        this.totalCost = uniCost * this.numberOfTables;
    }

    public String getFormattedEventDate() {
        return eventDate.format(DATE_FORMATTER);
    }
}