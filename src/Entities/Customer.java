package Entities;

import Utilities.DataValidation;

public class Customer {
    private String id;
    private String name;
    private String phone;
    private String email;

    public Customer(String id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws Exception {
        if (!DataValidation.checkStringWithFormat(id, "^[CGK]\\d{4}$")) {
            throw new Exception(
                    "Id invalid. The correct format: A unique 5-character string. The first character is “C”, “G”or “K”, followed by 4 digits");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
