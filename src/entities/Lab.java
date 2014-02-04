/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

/**
 *
 * @author Grzesiek
 */
public class Lab {
    private int id;
    private String nazwa;
    private String miasto;
    private String telefon;
    private String kodPocztowy;
    private String adresDoklady;
    private ArrayList<Doctor> lekarze;

    public ArrayList<Doctor> getLekarze() {
        return lekarze;
    }

    public void setLekarze(ArrayList<Doctor> lekarze) {
        this.lekarze = lekarze;
    }

    public Lab() {
        this.id = 0;
        this.nazwa = "Nieznane";
        this.miasto = "Nieznane";
        this.telefon = "Nieznane";
        this.kodPocztowy = "Nieznane";
        this.adresDoklady = "Nieznane";
        this.lekarze=null;

    }
    
        public void print() {
        System.out.println(id+";"+nazwa+";"+miasto+";"+telefon+";"+kodPocztowy+";"+adresDoklady);
    }

    @Override
    public String toString() {
        return this.nazwa;
    }


    public String getAdresDoklady() {
        return adresDoklady;
    }

    public void setAdresDoklady(String adresDoklady) {
        this.adresDoklady = adresDoklady;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKodPocztowy() {
        return kodPocztowy;
    }

    public void setKodPocztowy(String kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

}
