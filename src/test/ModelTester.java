package test;

import java.sql.Date;
import java.util.ArrayList;

import entities.Doctor;
import entities.Lab;
import entities.SubTask;
import entities.Work;
import entities.WorkTask;
import entities.WorkType;
import entities.Worker;
import sildent.Config;

public class ModelTester {

	
	public static void addTestData(){
		
		//addDoctors();
		//addWorkTypes();
		//addLabs();
		//addWorkers();
		//addWorks();
		
	}

	private static void addWorkTypes() {
		
		int numWorkTypes = 10;
		
		
		try {
			for (int i = 0; i < numWorkTypes; i++) {
				WorkType w = new WorkType();
				
				w.setWorkType("korona procelanowa");
				w.setPrice(230);
				
				ArrayList<SubTask> elements = new ArrayList<SubTask>();
				SubTask s = new SubTask();
				s.setCzynnosc("podbudowa");
				s.setProwizja(20);
				elements.add(s);
				s = new SubTask();
				s.setCzynnosc("model");
				s.setProwizja(30);
				elements.add(s);
				w.setElements(elements);
				
				Config.conn.addWorkType(w);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private static void addDoctors() {
		int numDocs = 10;
		
		
		try {
			for (int i = 0; i < numDocs; i++) {
				Doctor d = new Doctor();
				d.setImie("Edward"+i);
				d.setNazwisko("Gierek");
				d.setTelefon("594 055 994");
				d.setEmail("gierek@komuna.pl");
				Config.conn.addDoctor(d);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void addLabs() {
		int numLabs = 15;
		
		
		try {
			for (int i = 0; i < numLabs; i++) {
				Lab l = new Lab();
				l.setNazwa("Saska");
				l.setMiasto("Warszawa");
				l.setTelefon("594 055 994");
				l.setKodPocztowy("03-966");
				l.setAdresDoklady("Saska 2");
				Config.conn.addWorkplace(l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void addWorkers() {
		int numWorkers = 5;
		
		
		try {
			for (int i = 0; i < numWorkers; i++) {
				Worker w = new Worker();
				w.setName("Worker"+i);
				w.setSurname("Kloss");
				w.setPesel(24050603022l);
				w.setCity("Warsaw");
				w.setEmail("worker@worker"+i);
				w.setPhone_num("0289402342");
				Config.conn.addWorker(w);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void addWorks() {
		int numWorks = 10000;
		
		
		try {
			for (int i = 0; i < numWorks; i++) {
				
				Work w = new Work();
				w.setWorkType_id(2);
				w.setDoctor_id(1);
				Lab l = new Lab();
				l.setId(65);
				w.setLab(l);
				
				java.util.Date date = new java.util.Date();

				w.setDeadline(new Date(date.getTime()));
				w.setFinish_date(new Date(date.getTime()));
				w.setPacient("Kowalski");
				
				ArrayList<WorkTask> tasks = new ArrayList<WorkTask>();
				/*WorkTask t = new WorkTask();
				t.set
				tasks.add(t);*/
				
				WorkTask t = new WorkTask(
		                    11,
		                    2,
		                    4,
		                    20
		                    );
		        tasks.add(t);
	            t = new WorkTask(
	                    13,
	                    2,
	                    6,
	                    20
	                    );
				tasks.add(t);
				Config.conn.addWork(w, tasks);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
