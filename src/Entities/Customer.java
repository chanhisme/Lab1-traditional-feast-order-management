package Entities;

import Utilities.DataNormalize;
import Utilities.DataValidation;

public class Customer {

    private static final String ID_REGEX = "^[CGK]\\d{4}$";
    private static final String PHONE_REGEX = "^(03[2-9]|05[2568]|07[06789]|08[1-9]|09[0-9])\\d{7}$";
    private static final String NAME_CUSTOMER_REGEX = "^(?=.{2,25}$)[A-Za-z]+(?: [A-Za-z]+)*$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private String id;
    private String name;
    private String phone;
    private String email;

    //--------------------------------------------------------------
    public Customer(String id, String name, String phone, String email) throws Exception {
        setId(id);
        setName(name);
        setPhone(phone);
        setEmail(email);
    }

    //--------------------------------------------------------------
    public String getId() {
        return id;
    }

    //--------------------------------------------------------------
    public void setId(String id) throws Exception {
        if (id != null) {
            id = DataNormalize.normalizeId(id);
        }

        if (id == null || !DataValidation.checkStringWithFormat(id, ID_REGEX)) {
            throw new Exception(
                    "Id invalid. The correct format: A unique 5-character string. The first character is “C”, “G”or “K”, followed by 4 digits");
        }

        this.id = id;
    }

    //--------------------------------------------------------------
    public String getName() {
        return name;
    }

    //--------------------------------------------------------------
    public void setName(String name) throws Exception {
        if (name != null) {
            name = DataNormalize.normalizeString(name);
        }
        if (name == null || !DataValidation.checkStringWithFormat(name, NAME_CUSTOMER_REGEX)) {
            throw new Exception(
                    "Name must be from 2 to 25 characters");
        }
        this.name = name;
    }

    //--------------------------------------------------------------
    public String getPhone() {
        return phone;
    }

    //--------------------------------------------------------------
    public void setPhone(String phone) throws Exception {
        if (phone != null) {
            phone = DataNormalize.normalizePhone(phone);
        }
        if (phone == null || !DataValidation.checkStringWithFormat(phone, PHONE_REGEX)) {
            throw new Exception(
                    "Phone must be 10 digits and belonging to a network operator in VietNam.");
        }
        this.phone = phone;
    }

    //--------------------------------------------------------------
    public String getEmail() {
        return email;
    }

    //--------------------------------------------------------------
    public void setEmail(String email) throws Exception {
        if (email != null) {
            email = DataNormalize.normalizeEmail(email);
        }
        if (email == null || !DataValidation.checkStringWithFormat(email, EMAIL_REGEX)) {
            throw new Exception("Your mail is wrong format");
        }
        this.email = email;
    }

    //--------------------------------------------------------------
    @Override
    public String toString() {
        return String.format("%-5s | %-20s | %-12s | %-25s",
                id, formatDisplayName(name), phone, email);
    }

    //--------------------------------------------------------------
    private static String formatDisplayName(String name) {
        String[] parts = name.trim().split("\\s+");
        if (parts.length < 2) {
            return name;
        }
        String lastName = parts[parts.length - 1];
        StringBuilder firstPart = new StringBuilder();
        for (int i = 0; i < parts.length - 1; i++) {
            if (i > 0) {
                firstPart.append(" ");
            }
            firstPart.append(parts[i]);
        }
        return lastName + ", " + firstPart;
    }

}
