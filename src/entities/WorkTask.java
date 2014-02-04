/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.sql.Date;

/**
 *
 * @author Aleks
 */
public class WorkTask {
    private String workType;

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public WorkTask(int worker_id, int worktype_id, int czynnosc_skladowa_id, double provision) {
        this.deadline = java.sql.Date.valueOf( "2010-01-31" );
        this.lekarz = "";
        this.work_id = work_id;
        this.patient = "";
        this.worker = null;
        this.worker_id = worker_id;
        this.workType_id = worktype_id;
        this.element_id = czynnosc_skladowa_id;
        this.provision = provision;

    }


    public WorkTask() {
        this.deadline = java.sql.Date.valueOf( "2010-01-31" );
        this.lekarz = "";
        this.work_id = 0;
        this.patient = "";
        this.worker = null;
        this.worker_id = 0;
        this.workType_id = 0;
        this.element_id = 0;
        this.provision = 0;
    }

    public String getLekarz() {
        return lekarz;
    }

    public void setLekarz(String lekarz) {
        this.lekarz = lekarz;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }


    public void setWorkType(int workType_id) {
        this.workType_id = workType_id;
    }


    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
    private String lekarz;
    private int work_id;

    public int getWork_id() {
        return work_id;
    }

    public void setWork_id(int work_id) {
        this.work_id = work_id;
    }
    private String patient;
    private Worker worker;
    private int worker_id;

    public int getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(int worker_id) {
        this.worker_id = worker_id;
    }
    private int workType_id;
    private int element_id;
    private double provision;
    private String element;
    private Date deadline;

    public Date getDeadline() {
        return deadline;
    }

    public double getProvision() {
        return provision;
    }

    public void setProvision(double provision) {
        this.provision = provision;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }


    public int getElement_id() {
        return element_id;
    }

    public void setElement_id(int element) {
        this.element_id = element;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public int getWorkType_id() {
        return workType_id;
    }

    public void setWorkType_id(int workType_id) {
        this.workType_id = workType_id;
    }


}
