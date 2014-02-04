/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Grzesiek
 */
public class SubTask {
    private int id;
    private String task;
    private double provision;
    private Worker worker;

    public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public SubTask() {
        this.id = 0;
        this.task = "Nieznana czynnosc";
        this.provision = 0;
    }

    public double getProwizja() {
        return provision;
    }

    public void setProwizja(double prowizja) {
        this.provision = prowizja;
    }
    
    

    public String getCzynnosc() {
        return task;
    }

    public void setCzynnosc(String czynnosc) {
        this.task = czynnosc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
