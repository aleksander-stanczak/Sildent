/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Grzesiek
 */
public class Patient {
    private int id;
    private String name;
    private String surname;
    private String phone;

    public Patient() {
        this.id = 0;
        this.name = "Nieznane imie";
        this.surname = "Nieznane nazwisko";
        this.phone = "Nieznane";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    
}
