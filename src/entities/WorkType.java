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
public class WorkType {
    private int id;
    private String workType;
    private double price;
    private ArrayList<SubTask> elements;

    public ArrayList<SubTask> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        return workType;
    }

    public void setElements(ArrayList<SubTask> elements) {
        this.elements = elements;
    }
    //prowizja jest przypisana do czynności składowej, nie pracy
    //private double payoff;

    public WorkType() {
        this.id = 0;
        this.workType = "Nieznany typ";
        this.price = 0;
        //this.payoff = 0;
        this.elements=new ArrayList<SubTask>();
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }
    
}
