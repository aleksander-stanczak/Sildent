/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * WorkRegistryWindow.java
 *
 * Created on 2012-01-04, 16:28:30
 */

package gui.editors;

import dbconnection.DBConnector;
import debug.DebugDisplay;
import entities.Arrear;
import entities.Doctor;
import entities.Lab;
import entities.Work;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.Component;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;

import sildent.Config;
import utilities.PDFExporter;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.toedter.calendar.JDateChooser;

/**
 *
 * @author Aleks
 */
public class ArrearsWindow extends javax.swing.JFrame {

    /** Creates new form WorkRegistryWindow */
    public ArrearsWindow() {

        initComponents();
        table.setAutoCreateRowSorter(rootPaneCheckingEnabled);


        setLocationRelativeTo(null);

        initFields();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        doctorField = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Zaleg�o�ci");

        table.setModel(new DefaultTableModel(
        	new Object[][] {
        		{null, null, null},
        	},
        	new String[] {
        		"Lekarz", "Miesi�c", "Kwota"
        	}
        ));
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton1.setText("zamknij");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                close(evt);
            }
        });

        jLabel1.setText("Filtruj -");

        jLabel2.setText("zalegaj�cy lekarz:");

        doctorField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changedWorker(evt);
            }
        });
        
        

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3Layout.setHorizontalGroup(
        	jPanel3Layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(jPanel3Layout.createSequentialGroup()
        			.addGap(261)
        			.addComponent(jLabel1)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(jLabel2)
        			.addGap(18)
        			.addComponent(doctorField, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
        			.addComponent(jButton1)
        			.addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
        	jPanel3Layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(jPanel3Layout.createSequentialGroup()
        			.addContainerGap(17, Short.MAX_VALUE)
        			.addGroup(jPanel3Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(jButton1)
        				.addComponent(jLabel1)
        				.addComponent(jLabel2)
        				.addComponent(doctorField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap())
        );
        jPanel3.setLayout(jPanel3Layout);
        
        panel = new JPanel();
        panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addContainerGap())
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(panel, GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
        			.addGap(1))
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addContainerGap())
        );
        
        rdbtnLabs = new JRadioButton("gabinet");
        rdbtnLabs.setAlignmentX(0.5f);
        buttonGroup.add(rdbtnLabs);
        rdbtnLabs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateChoosingList();
            }
        });
        
        rdbtnDocs = new JRadioButton("lekarz");
        rdbtnDocs.setAlignmentX(0.5f);
        rdbtnDocs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateChoosingList();
            }
        });
        buttonGroup.add(rdbtnDocs);
        panel.setLayout(new MigLayout("", "[32px][61px][85px][][grow][][grow][][]", "[23px][23px,grow]"));
        
        lblRozlicz = new JLabel("Rozlicz");
        lblRozlicz.setIconTextGap(100);
        lblRozlicz.setPreferredSize(new Dimension(150, 14));
        panel.add(lblRozlicz, "cell 0 0 1 2,alignx left,gapright 5px,aligny center");
        panel.add(rdbtnLabs, "cell 1 0,alignx left,aligny top");
        panel.add(rdbtnDocs, "cell 1 1,alignx left,aligny top");
        
        selectionBox = new JComboBox();
        selectionBox.setModel(new DefaultComboBoxModel(new String[] {"Nie wybrano"}));
        panel.add(selectionBox, "cell 2 0 1 2,alignx left,aligny center");
        
        lblOd = new JLabel("od");
        panel.add(lblOd, "cell 3 0 1 2");
        
        checkButton = new JButton();
        checkButton.setText("rachunek...");
        panel.add(checkButton, "cell 7 0 1 2");
        
        lblNewLabel = new JLabel("do");
        panel.add(lblNewLabel, "cell 5 0 1 2");
        
        btnNewButton = new JButton("rozlicz");
        panel.add(btnNewButton, "cell 8 0 1 2");
        
        fromDateField = new JDateChooser();
        panel.add(fromDateField, "cell 4 0 1 2,grow");
        
        toDateField = new JDateChooser();
        panel.add(toDateField, "cell 6 0 1 2,grow");
        checkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateReport();
            }
        });
        jPanel1.setLayout(jPanel1Layout);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
        );
        getContentPane().setLayout(layout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    protected void updateChoosingList() {
    	
    	
    	selectionBox.removeAllItems();
		
    	try {
    		
    		// load doctors
    		if (rdbtnDocs.isSelected()){

	            // pozyskujemy i wstawiamy lekarzy
	            ArrayList<Doctor> doctors = sildent.Config.conn.getDoctorList();
	            for (Doctor doctor : doctors) {
	               selectionBox.addItem(doctor);
            }
            
    		}
            // load labs
            else{
            	
            	// pozyskujemy i wstawiamy laby
                ArrayList<Lab> labs = sildent.Config.conn.getWorkplaceList();
                for (Lab lab : labs) {
                   selectionBox.addItem(lab);
                }
            }


        } catch (SQLException ex) {
            System.out.println("SQL exception: "+ex);
            ex.printStackTrace();
            DebugDisplay.popup(this,ex);
            //Logger.getLogger(AddWorkWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

	private void generateReport() {//GEN-FIRST:event_add
        // TODO add your handling code here:
        System.out.println("Summing report...");
        
        try {
        
    	// generate for doctors
    	if ( rdbtnDocs.isSelected() ){

    		Doctor doctor = ((Doctor)selectionBox.getSelectedItem());

    		ArrayList<Work> works;

    		works = Config.conn.getWorkList(
    				doctor.getId(),
    				-1,
    				-1,
    				"",
    				new java.sql.Date(fromDateField.getDate().getTime()), 
    				new java.sql.Date(toDateField.getDate().getTime()),
    				false
    				);


    		System.out.println("Size"+works.size());

    		PDFExporter export = new PDFExporter();
    		try {
				export.generateDoctorCheck(works, doctor,fromDateField.getDate(),toDateField.getDate());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				DebugDisplay.popup(this,e);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				DebugDisplay.popup(this,e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				DebugDisplay.popup(this,e);
			}
        	
        // generate for labs
        } else if ( rdbtnLabs.isSelected() ){
        	
        	Lab lab = ((Lab)selectionBox.getSelectedItem());
        	
        	ArrayList<Work> works;
			
				works = Config.conn.getWorkList(
							-1,
							lab.getId(),
							-1,
							"",
							new java.sql.Date(fromDateField.getDate().getTime()), 
    						new java.sql.Date(toDateField.getDate().getTime()),
							false
						);
			
        	
        	System.out.println("Size"+works.size());
        	
        	//PDFExporter export = new PDFExporter();
        	//export.generateDoctorCheck(works, lab);
        }
        
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DebugDisplay.popup(this,e);
		}
        
        //new AddWorkWindow().setVisible(true);
    }//GEN-LAST:event_add

    private void close(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_close
        refreshTable();
        //this.dispose();
    }//GEN-LAST:event_close

    private void changedWorker(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changedWorker
        System.out.println("Change worker and refresh please...");
    }//GEN-LAST:event_changedWorker

    private void refreshTable(){
        System.out.println("Refreshing table...");

                try {
                	
                	/*works = Config.conn.getWorkList(
    						-1, 
    						lab.getId(), 
    						new java.sql.Date(toDateField.getDate().getTime()), 
    						new java.sql.Date(fromDateField.getDate().getTime())
    						);*/


            ArrayList<Arrear> arrears = sildent.Config.conn.getArrearsList(
                    ((Doctor)doctorField.getSelectedItem()).getId()
                    );

            DefaultTableModel model = (DefaultTableModel)table.getModel();
            System.out.println(model.getRowCount());

            // clear table
            int rows = model.getRowCount();
            for (int i = 0; i < rows; i++) {
                model.removeRow(0);
            }

            // fill tables with data
            for (Arrear arrear : arrears) {
                model.addRow(new Object[]{
                    arrear.getName(),
                    arrear.getMonth(),
                    arrear.getValue()
                });
            }

        } catch (Exception e) {
            System.err.println(e);
            DebugDisplay.popup(this,e);
        }
    }

        private void initFields(){


        try {

            // dodaj pole wszyscy
            Doctor doc = new Doctor();
                    doc.setImie("Wszyscy");
                    doc.setId(-1);
            doctorField.addItem(doc);

            // pozyskujemy i wstawiamy lekarzy
            ArrayList<Doctor> doctors = sildent.Config.conn.getDoctorList();
            for (Doctor doctor : doctors) {
               doctorField.addItem(doctor);
            }


        } catch (SQLException ex) {
            System.out.println("SQL exception: "+ex);
            DebugDisplay.popup(this,ex);
            //Logger.getLogger(AddWorkWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	
            	System.out.println("start /max doctor.pdf");
            	//runStandalone();
                //new ArrearsWindow().setVisible(true);
                
            }
        });
    }
    
    
    private static void runStandalone(){
    	
    	DBConnector db = new DBConnector("localhost",1521, "aleks", "azstazst");
        Config.conn = db;
        try {
			db.connect();
		
        
        Doctor doctor = Config.conn.getDoctorList().get(0);

		ArrayList<Work> works;

		works = Config.conn.getWorkList(
				doctor.getId(),
				-1,
				-1,
				"",
				null, 
				null,
				false
				);


		System.out.println("Size"+works.size());
		
		

		PDFExporter export = new PDFExporter();
		
		Date fromDate = new Date();
		fromDate.setTime(40*365*1000*3600*24);

			export.generateDoctorCheck(works, doctor, fromDate, new Date());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox doctorField;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    private JPanel panel;
    private JComboBox selectionBox;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JLabel lblRozlicz;
    private JRadioButton rdbtnLabs;
    private JRadioButton rdbtnDocs;
    private JButton checkButton;
    private JLabel lblOd;
    private JLabel lblNewLabel;
    private JButton btnNewButton;
    private JDateChooser fromDateField;
    private JDateChooser toDateField;
}
