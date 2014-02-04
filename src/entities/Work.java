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
public class Work {

    private int id;
    private String work_type;
    private int workType_id;
    private String doctor;
    private int doctor_id;
    private String printId;
    //private String workplace;
    //private int lab_id;
    private Lab lab;
	private String description;
    private String pacient;
    private int pacient_id;
    private Date delivery_date;
    private int pacients_phone;
    // if true - work is active (not already done)
    private Boolean status;
    // if true work is settled (archived)
    private Boolean settled;
	//private String technicians;
    private double price;

    
    public Lab getLab() {
		return lab;
	}

	public void setLab(Lab lab) {
		this.lab = lab;
	}
    
    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getPacients_phone() {
        return pacients_phone;
    }

    public void setPacients_phone(int pacients_phone) {
        this.pacients_phone = pacients_phone;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

//    public int getLab_id() {
//        return lab_id;
//    }
//
//    public void setLab_id(int lab_id) {
//        this.lab_id = lab_id;
//    }

    public int getPacient_id() {
        return pacient_id;
    }

    public void setPacient_id(int pacient_id) {
        this.pacient_id = pacient_id;
    }

    
    public int getWorkType_id() {
        return workType_id;
    }

    public void setWorkType_id(int workType_id) {
        this.workType_id = workType_id;
    }
    private Date deadline;
    private Date finish_date;
    // public String getTechnicians() {
	//    return technicians;
	//}
	
	//public void setTechnicians(String technicians) {
	  //  this.technicians = technicians;
	//}
	

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

//    public String getWorkplace() {
//        return workplace;
//    }
//
//    public void setWorkplace(String workplace) {
//        this.workplace = workplace;
//    }

    public String getPacient() {
        return pacient;
    }

    public void setPacient(String pacient) {
        this.pacient = pacient;
    }

    public Boolean isSettled() {
        return settled;
    }

    public void setSettled(Boolean settled) {
        this.settled = settled;
    }

   // public String getTechnicians() {
    //    return technicians;
    //}

    public Work() {
        this.id = 0;
        this.work_type = null;
        this.doctor = null;
        this.description = "description";
        this.pacient = null;
        this.delivery_date=java.sql.Date.valueOf( "2010-01-31" );
        this.deadline=java.sql.Date.valueOf( "2010-01-31" );
        this.finish_date=java.sql.Date.valueOf( "2010-01-31" );
        this.settled=false;
        this.status=true;
        //this.technicians=null;
    }
    
    


    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(Date delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Date getFinish_date() {
        return finish_date;
    }

    public void setFinish_date(Date finish_date) {
        this.finish_date = finish_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getWork_type() {
        return work_type;
    }

    public void setWork_type(String work_type) {
        this.work_type = work_type;
    }

    public void print() {
        System.out.println(work_type+";"+doctor+";"+description+";"+
                pacient+";"+delivery_date+";"+deadline+";"+finish_date+";"+";"+settled);
    }

	public String getPrintId() {
		return printId;
	}

	public void setPrintId(String printId) {
		this.printId = printId;
	}
    
    
    
}
