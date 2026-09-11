/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataObjects;
import Entities.Customer;
import Entities.Order;
import Entities.SetMenu;

import java.util.Map;

/**
 *
 * @author chanh
 */
public class OrderDAO {
    private final Map <String, Order>orderMap;
    private final Map <String, SetMenu> setMenuMap;
    private final Map <String, Customer> customerMap;
    public OrderDAO(Map<String, Order> orderMap, Map<String, SetMenu> setMenuMap, Map<String, Customer> customerMap) {
        this.orderMap = orderMap;
        this.setMenuMap = setMenuMap;
        this.customerMap = customerMap;
    }
    
    public void save(){
        
    }
    
    public void load(){
        
    }
    
    public void addOrder(Order order){
        orderMap.put(order.getOrderId(), order);
    }
    public String generateOrderId() {
        int id = 1;

        while (orderMap.containsKey(String.valueOf(id))) {
            id++;
        }

        return String.valueOf(id);
    }
}
