/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessObject;
import Entities.Customer;
import Utilities.DataInput;
/**
 *
 * @author chanh
 */
public class CustomerManagment {
        
    public Customer inputCustomer()  throws Exception {
        String id = DataInput.getString("Enter customer id: ");
        String name = DataInput.getString("Enter customer name: ");
        String phone = DataInput.getString("Enter customer phone number: ");
        String email = DataInput.getString("Enter customer email: ");
        return new Customer(id, name, phone, email);
    }
    
    
    public void addNewCustomer(){
            try{
                Customer customer = inputCustomer();

                
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
    }
}
