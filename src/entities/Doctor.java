/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.ArrayList;

/**
 *
 * @author Grzesiek
 */
public class Doctor {

    public Doctor() {
        this.id = 0;
        this.name = "Nieznane";
        this.surname = "Nieznane";
        this.phone = null;
        this.email = "Nieznane";
        this.workplace = null;
    }
    private int id;
    
    public void print() {
        System.out.println(id+";"+name+";"+surname+";"+phone+";"+email);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImie() {
        return name;
    }

    public void setImie(String imie) {
        this.name = imie;
    }

    public String getNazwisko() {
        return surname;
    }

    public void setNazwisko(String nazwisko) {
        this.surname = nazwisko;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelefon() {
        return phone;
    }

    public void setTelefon(String telefon) {
        this.phone = telefon;
    }
    private String name;
    private String surname;
    private String phone;
    private String email;
    private ArrayList<Lab> workplace;

    public ArrayList<Lab> getMiejscaPracy() {
        return workplace;
    }

    @Override
    public String toString() {
        return name+" "+surname;
    }

    public void setMiejscaPracy(ArrayList<Lab> miejscaPracy) {
        this.workplace = miejscaPracy;
    }
}
