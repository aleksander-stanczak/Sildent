/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

/**
 *
 * @author Aleks
 */
public class Worker {

    @Override
    public String toString() {
        return name+" "+surname;
    }

    private int worker_id;
    private String name;
    private String surname;
    private long pesel;
    private String email;
    private String city;
    private String post_code;
    private String address;
    private String phone_num;

    public Worker() {
        this.worker_id = 0;
        this.name = "Nieznane";
        this.surname = "Nieznane nazwisko";
        this.pesel = 0;
        this.email = "nieznany@nieznany";
        this.city = "Nibylandia";
        this.post_code = "00-000";
        this.address = "nieznany adres";
        this.phone_num = "";
    }
        public void print() {
        System.out.println(worker_id+";"+name+";"+surname+";"+pesel+";"+email+"...");
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPesel() {
        return pesel;
    }

    public void setPesel(long pesel) {
        this.pesel = pesel;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(int worker_id) {
        this.worker_id = worker_id;
    }

}
