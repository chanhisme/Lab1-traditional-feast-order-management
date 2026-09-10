/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessObject;
import DataObjects.FeastMenuDAO;
import Entities.FeastMenu;
import java.util.Map;
/**
 *
 * @author chanh
 */
public class FeastMenuManagement {
    private final FeastMenuDAO feastMenuDAO;

    public FeastMenuManagement(FeastMenuDAO feastMenuDAO) {
        this.feastMenuDAO = feastMenuDAO;
    }
    
    public void displayFeastMenu(Map <String, FeastMenu> feastMap){
        
    }
    
    
}
