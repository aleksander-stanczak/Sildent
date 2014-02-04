/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sildent;

import dbconnection.DBConnector;
import debug.DebugDisplay;
import entities.Lab;
import entities.Doctor;
import entities.Worker;
import gui.MainWindow;
import javax.swing.UIManager;

import test.ModelTester;


/**
 *
 * @author Aleks
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            
            Sildent.boot();
            
            //
            //DBConnector db = new DBConnector("localhost",1521, "aleks", "azstazst");
            
            Config.conn = new DBConnector(Config.host,Config.port,Config.login,Config.pass);
            Config.conn.connect();
            
            //ModelTester.addTestData();
            
//            ArrayList<Gabinet> Gabinety = Oracle.getWorkplaceList();
//            for(int i=0; i<Gabinety.size(); i++) {
//               Gabinety.get(i).print();
//            }
            
//            Lekarz nowyLekarz = new Lekarz();
 //           Oracle.addDoctor(nowyLekarz);
            
//            Oracle.deleteDoctor(4);
            
//            Worker pracownik = new Worker();
//            Oracle.addWorker(pracownik);

//            ArrayList<Worker> workers = Oracle.getWorkerList();
//            for (Worker w : workers){
//                w.print();
//            }
                 
//           Work praca = new Work();
//           Oracle.addWork(praca);

            
//            ArrayList<Work> listaPrac = Oracle.getWorkList( 0, 
//                                java.sql.Date.valueOf("2000-01-01"), 
//                                java.sql.Date.valueOf("2012-01-01")
//                                );
//            for(Work w: listaPrac){
//                w.print();
//            }
            
            
//            CzynnoscSkladowa cs = new CzynnoscSkladowa();
//            Oracle.addCzynnoscSkladowa(cs);
            
//            WorkType workType = new WorkType();
//            Oracle.addWorkType(workType);           

        //add_test_elements(db);
            
        } catch(Exception e) {
        	
            System.out.println("Could not connect to DB service: " + e);
            
        }

        new MainWindow().setVisible(true);
    }

    public static void addTestElements(DBConnector db) throws Exception{
                    Doctor doc = new Doctor();
            doc.setImie("Beata");
            doc.setNazwisko("Socha");
            doc.setEmail("beatka@gmail.com");
            doc.setTelefon("617-453-223");

            Lab lab = new Lab();
            lab.setNazwa("Saska");
            lab.setMiasto("Warszawa");
            lab.setAdresDoklady("Saska 16");
            lab.setKodPocztowy("03-924");
            lab.setTelefon("450-444-024");

            Worker wrkr = new Worker();
            wrkr.setName("Iwona");
            wrkr.setSurname("Stañczak");
            wrkr.setCity("Warszawa");
            wrkr.setAddress("Retmañska 10");
            wrkr.setPost_code("04-920");
            wrkr.setEmail("stanczakiwona@gmail.com");
            wrkr.setPesel(209853094);
            wrkr.setPhone_num("501-287-640");

            db.addDoctor(doc);
            db.addWorkplace(lab);
            db.addWorker(wrkr);
    }

}
