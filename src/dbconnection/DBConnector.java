/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dbconnection;

import entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import oracle.jdbc.OraclePreparedStatement;

import sildent.Config;
import net.sf.log4jdbc.Slf4jSpyLogDelegator;
//import org.slf4j.impl.SimpleLoggerFactory;


/**
 *
 * @author Aleks
 */
public class DBConnector {
	private String serverName;
	private int portNumber;
	private String login;
	private String password;
	// General

	public DBConnector(String serverName, int portNumber, String login, String password) {
		this.serverName = serverName;
		this.portNumber = portNumber;
		this.login = login;
		this.password = password;
	}

	///////////////////////////////////////////////
	Connection conn = null;
	// default host='localhost'
	//GB// funkcja zwaraca obiekt typu Connetion 
	public void connect() throws SQLException {

		System.out.println("XXX "+login+"  "+password);
		
		Properties connectionProps = new Properties();
		connectionProps.put("user", login);
		connectionProps.put("password", password);
		System.out.println(
				"jdbc:oracle:thin//@" +
						serverName +
						":" + portNumber+":xe" );
		String url = "jdbc:oracle:thin:@localhost:1521:xe";

		this.conn = DriverManager.getConnection(
				url,
				connectionProps);

		System.out.println("Connected to database");
		Config.isConnected = true;

		// wrap the connection with log4jdbc
		if (Config.LOGGING)
			conn = new net.sf.log4jdbc.ConnectionSpy(conn);
		
		
		//dbInit();


	}

	public void disconnect() throws SQLException{
		
		if( this.conn != null )
			this.conn.close();
		System.out.println("Connection to the database closed.");
		Config.isConnected = false;
	}

	// 
	public boolean makeBackup(){

		return true;
	}



	// Docs & Labs

	///////////////////////////////////////////////

	// zwraca tablicę z imionami wszystkich lekarzy
	public ArrayList<Doctor> getDoctorList() throws SQLException {
		ArrayList listaLekarzy = new ArrayList();
		Statement stmt = null;
		String query =
				"select * from LEKARZE where aktywny = 1";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Doctor lekarz = new Doctor();
				lekarz.setId(rs.getInt("id_lekarza"));
				lekarz.setImie(rs.getString("imie"));
				lekarz.setNazwisko(rs.getString("nazwisko"));
				lekarz.setTelefon(rs.getString("telefon"));
				lekarz.setEmail(rs.getString("email"));

				listaLekarzy.add(lekarz);
			}

		} catch (SQLException e ) {
			System.out.println(e);
		} finally {
			if (stmt != null) { stmt.close(); }
		}
		return listaLekarzy;
	}

	// zwraca tablicę z imionami wszystkich lekarzy pracujących w gabinecie o zadanym id
	public ArrayList<Doctor> getDoctorNameList(int workp_id) throws SQLException{
		ArrayList listaLekarzy = new ArrayList();
		PreparedStatement stmt = null;
		String query =
				"select * from LEKARZE where ID_LEKARZA=? and aktywny=1";
		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, workp_id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Doctor lekarz = new Doctor();
				lekarz.setId(rs.getInt("id_lekarza"));
				lekarz.setImie(rs.getString("imie"));
				lekarz.setNazwisko(rs.getString("nazwisko"));
				lekarz.setTelefon(rs.getString("telefon"));
				lekarz.setEmail(rs.getString("email"));

				listaLekarzy.add(lekarz);
			}

		} catch (SQLException e ) {
			System.out.println(e);
		} finally {
			if (stmt != null) { stmt.close(); }
		}
		return listaLekarzy;
	}

	// zwraca tablic� z nazwami wszystkich gabinet�w
	public ArrayList<Lab> getWorkplaceList() throws SQLException{
		ArrayList<Lab> listaGabinetow = new ArrayList();
		Statement stmt = null;
		String query =
				"select * from GABINETY where aktywny=1";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Lab gabinet = new Lab();
				gabinet.setId(rs.getInt("id_gabinetu"));
				gabinet.setAdresDoklady(rs.getString("adres_dokladny"));
				gabinet.setKodPocztowy(rs.getString("kod_pocztowy"));
				gabinet.setMiasto(rs.getString("miasto"));
				gabinet.setNazwa(rs.getString("nazwa"));
				gabinet.setTelefon(rs.getString("telefon"));
				listaGabinetow.add(gabinet);
			}

		} catch (SQLException e ) {
			System.out.println(e);
		} finally {
			if (stmt != null) { stmt.close(); }
		}
		return listaGabinetow;
	}

	// zwraca tablicę ze wszystkimi gabinetami zatrudniającymi lekarza o zadanym id
	public ArrayList<String> getWorkPlaceNameListFrom(int doc_id) throws SQLException{
		ArrayList listaGabinetow = new ArrayList();
		PreparedStatement stmt = null;
		String query =
				"select * from V_GABINETY_LEKARZE where ID_LEKARZA=?";
		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1,doc_id);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Lab gabinet = new Lab();
				gabinet.setId(rs.getInt("id_gabinetu"));
				gabinet.setAdresDoklady(rs.getString("adres_dokladny"));
				gabinet.setKodPocztowy(rs.getString("kod_pocztowy"));
				gabinet.setMiasto(rs.getString("miasto"));
				gabinet.setNazwa(rs.getString("nazwa"));
				gabinet.setTelefon(rs.getString("telefon"));
			}

		} catch (SQLException e ) {
			System.out.println(e);
		} finally {
			if (stmt != null) { stmt.close(); }
		}
		return listaGabinetow;
	}


	// aktualizuje dane lekarza
	//nie potrzeba ID, bo obiekt lekarz juz zawiera ID
	public void updateLekarz(Doctor lekarz) throws SQLException {

		PreparedStatement updateLekarz = null;
		String updateStatement =
				"update LEKARZE set "
						+ "imie = ?, "
						+ "nazwisko = ?, "
						+ "telefon = ?, "
						+ "email = ? "
						+ "where ID_LEKARZA = ?";

		try {
			conn.setAutoCommit(false);
			updateLekarz = conn.prepareStatement(updateStatement);
			updateLekarz.setString(1, lekarz.getImie());
			updateLekarz.setString(2, lekarz.getNazwisko());
			updateLekarz.setString(3, lekarz.getTelefon());
			updateLekarz.setString(4, lekarz.getEmail());
			updateLekarz.setInt(5, lekarz.getId());
			updateLekarz.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			System.out.println(e);
			if (conn != null) {
				try {
					System.err.print(
							"Transaction is being "
									+ "rolled back");
					conn.rollback();
				} catch (SQLException excep) {
					System.out.println(excep);
				}
			}
		} finally {
			if (updateLekarz != null) {
				updateLekarz.close();
			}
			conn.setAutoCommit(true);
		}
	}

	// dodaje dane lekarza, przydziel mu kolejny numer id (trzeba by to w bazie zrobić)
	public void addDoctor(Doctor lekarz) throws SQLException{
		PreparedStatement insertLekarz = null;
		String insertStatement =
				"insert into lekarze (imie, nazwisko, telefon, email) values (?,?,?,?)";

		try {
			conn.setAutoCommit(false);
			insertLekarz = conn.prepareStatement(insertStatement);
			insertLekarz.setString(1, lekarz.getImie());
			insertLekarz.setString(2, lekarz.getNazwisko());
			insertLekarz.setString(3, lekarz.getTelefon());
			insertLekarz.setString(4, lekarz.getEmail());
			insertLekarz.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			System.out.println(e);
			if (conn != null) {
				try {
					System.err.print(
							"Transaction is being "
									+ "rolled back");
					conn.rollback();
				} catch (SQLException excep) {
					System.out.println(excep);
				}
			}
		} finally {
			if (insertLekarz != null) {
				insertLekarz.close();
			}
			System.out.println("New doctor added succesfully");
			conn.setAutoCommit(true);
		}
	}

	// trzeba by rozważyć czy jakiego caskade nie zrobić
	public void deleteDoctor(int doc_id) throws SQLException{

		PreparedStatement deleteLekarz = null;
		String deleteStatement =
				"update LEKARZE set "
						+ "aktywny = ? "
						+ "where ID_LEKARZA = ?";

		try {
			deleteLekarz = conn.prepareStatement(deleteStatement);
			deleteLekarz.setInt(1, 0);
			deleteLekarz.setInt(2, doc_id);
			deleteLekarz.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (deleteLekarz != null) {
				deleteLekarz.close();
			}
		}
	}


	// aktualizuje dane gabinetu
	public boolean updateWorkplace(Lab gabinet) throws SQLException{


		PreparedStatement updateGabinet = null;
		String updateStatement =
				"update GABINETY set "
						+ "nazwa = ?, "
						+ "telefon = ?, "
						+ "kod_pocztowy = ?, "
						+ "miasto = ?, "
						+ "adres_dokladny = ? "
						+ "where id_gabinetu = ?";

		try {
			conn.setAutoCommit(false);
			updateGabinet = conn.prepareStatement(updateStatement);
			updateGabinet.setString(1, gabinet.getNazwa());
			updateGabinet.setString(2, gabinet.getTelefon());
			updateGabinet.setString(3, gabinet.getKodPocztowy());
			updateGabinet.setString(4, gabinet.getMiasto());
			updateGabinet.setString(5, gabinet.getAdresDoklady());
			updateGabinet.setInt(6, gabinet.getId());
			updateGabinet.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			if (conn != null) {
				try {
					System.err.print(
							"Transaction is being "
									+ "rolled back");
					conn.rollback();
				} catch (SQLException excep) {
					System.out.println(excep);
					return false;
				}
			}
		} finally {
			if (updateGabinet != null) {
				updateGabinet.close();
				return true;
			}
			conn.setAutoCommit(true);
			return true;
		}

	}

	// dodaje dane gabinetu, przydziel mu kolejny numer id (trzeba by to w bazie zrobić)
	public void addWorkplace(Lab gabinet) throws SQLException{
		PreparedStatement insertGabinet = null;
		String insertStatement =
				"insert into gabinety (nazwa, telefon, miasto, kod_pocztowy, adres_dokladny) values (?,?,?,?,?)";

		try {
			conn.setAutoCommit(false);
			insertGabinet = conn.prepareStatement(insertStatement);
			insertGabinet.setString(1, gabinet.getNazwa());
			insertGabinet.setString(2, gabinet.getTelefon());
			insertGabinet.setString(3, gabinet.getMiasto());
			insertGabinet.setString(4, gabinet.getKodPocztowy());
			insertGabinet.setString(5, gabinet.getAdresDoklady());
			insertGabinet.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			System.out.println(e);
			if (conn != null) {
				try {
					System.err.print(
							"Transaction is being "
									+ "rolled back");
					conn.rollback();
				} catch (SQLException excep) {
					System.out.println(excep);
				}
			}
		} finally {
			if (insertGabinet != null) {
				insertGabinet.close();
			}
			System.out.println("New workplace added succesfully");
			conn.setAutoCommit(true);
		}
	}

	// trzeba by rozważyć czy jakiego caskade nie zrobić
	public void deleteWorkplace(int workp_id) throws SQLException{


		PreparedStatement deleteGabinet = null;
		String deleteStatement =	
		"update GABINETY set "
		+ "aktywny = ? "
		+ "where ID_GABINETU = ?";

		try {
			deleteGabinet = conn.prepareStatement(deleteStatement);
			deleteGabinet.setInt(1, 0);
			deleteGabinet.setInt(1, workp_id);
			deleteGabinet.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (deleteGabinet != null) {
				deleteGabinet.close();
			}
		}
	}

	// Work and Workers

	/////////////////////////////////////////////


	// zwraca listę typu 'Jan Kowalski', 'Edward Gierek' etc.
	public ArrayList<String> getWorkerNameList(){

		/* TO DO for Grzesio */

		return null;
	}

	public void addWorker(Worker worker) throws SQLException{
		PreparedStatement insertWorker = null;
		String insertStatement =
				"insert into PRACOWNICY "
						+ "(imie,nazwisko,pesel,telefon,email,miasto,kod_pocztowy, "
						+ "adres_dokladny) values(?,?,?,?,?,?,?,?)";

		try {
			conn.setAutoCommit(false);
			insertWorker = conn.prepareStatement(insertStatement);
			insertWorker.setString(1, worker.getName());
			insertWorker.setString(2, worker.getSurname());
			insertWorker.setLong(3, worker.getPesel());
			insertWorker.setString(4, worker.getPhone_num());
			insertWorker.setString(5, worker.getEmail());
			insertWorker.setString(6, worker.getCity());
			insertWorker.setString(7, worker.getPost_code());
			insertWorker.setString(8, worker.getAddress());
			insertWorker.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			System.out.println(e);
			if (conn != null) {
				try {
					System.err.print(
							"Transaction is being "
									+ "rolled back");
					conn.rollback();
				} catch (SQLException excep) {
					System.out.println(excep);
				}
			}
		} finally {
			if (insertWorker != null) {
				insertWorker.close();
			}
			conn.setAutoCommit(true);
		}
	}

	// dodaje dane pracownika, przydziel mu kolejny numer id (trzeba by to w bazie zrobić)
	public boolean updateWorker(Worker worker, int id) throws SQLException{


		PreparedStatement updateWorker = null;
		String updateStatement =
				"update PRACOWNICY set "
						+ "imie = ?, "
						+ "nazwisko = ?, "
						+ "telefon = ?, "
						+ "pesel = ?, "
						+ "kod_pocztowy = ?, "
						+ "miasto = ?, "
						+ "adres_dokladny = ?, "
						+ "email = ? "
						+ "where id_gabinetu = ?";

		try {
			conn.setAutoCommit(false);
			updateWorker = conn.prepareStatement(updateStatement);
			updateWorker.setString(1, worker.getName());
			updateWorker.setString(2, worker.getSurname());
			updateWorker.setString(3, worker.getPhone_num());
			updateWorker.setLong(4, worker.getPesel());
			updateWorker.setString(5, worker.getPost_code());
			updateWorker.setString(6, worker.getCity());
			updateWorker.setString(7, worker.getAddress());
			updateWorker.setString(8, worker.getEmail());
			updateWorker.setInt(9, worker.getWorker_id());
			updateWorker.executeUpdate();
			conn.commit();
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			if (conn != null) {
				try {
					System.err.print(
							"Transaction is being "
									+ "rolled back");
					conn.rollback();
				} catch (SQLException excep) {
					System.out.println(excep);
					return false;
				}
			}
		} finally {
			if (updateWorker != null) {
				updateWorker.close();
				return true;
			}
			conn.setAutoCommit(true);
			return true;
		}

	}

	// trzeba by rozważyć czy jakiego caskade nie zrobić
	public void deleteWorker(int id) throws SQLException{

		PreparedStatement deletePracownik = null;
		String deleteStatement =
		"update PRACOWNICY set "
		+ "aktywny = ? "
		+ "where ID_PRACOWNIKA = ?";

		try {
			deletePracownik = conn.prepareStatement(deleteStatement);
			deletePracownik.setInt(1, 0);
			deletePracownik.setInt(2, id);
			deletePracownik.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (deletePracownik != null) {
				deletePracownik.close();
			}
		}
	}

	// zwraca tablicę z wszystkimi robotnikami
	public ArrayList<Worker> getWorkerList() throws SQLException{

		ArrayList<Worker> listaPracownikow = new ArrayList();
		Statement stmt = null;
		String query =
				"select * from PRACOWNICY where aktywny=1";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Worker worker = new Worker();
				worker.setWorker_id(rs.getInt("id_pracownika"));
				worker.setName(rs.getString("imie"));
				worker.setSurname(rs.getString("nazwisko"));
				worker.setPesel(rs.getLong("pesel"));
				worker.setPhone_num(rs.getString("telefon"));
				worker.setEmail(rs.getString("email"));
				worker.setCity(rs.getString("miasto"));
				worker.setPost_code(rs.getString("kod_pocztowy"));
				worker.setAddress(rs.getString("adres_dokladny"));
				listaPracownikow.add(worker);
			}

		} catch (SQLException e ) {
			System.out.println(e);
		} finally {
			if (stmt != null) { stmt.close(); }
		}
		return listaPracownikow;

	}


	public void addWork(Work work, ArrayList<WorkTask> workTaskList) throws SQLException{

		// generate printable id with date
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(work.getDelivery_date());
		int month = calendar.get(Calendar.MONTH)+1;
		int year = calendar.get(Calendar.YEAR);
		
		
		
		// add new work
		CallableStatement insertWork = null;
		String insertStatement =
				"begin insert into PRACE (id_pracy, id_typu_pracy, id_lekarza,termin,data_zgloszenia,"
						+ "data_wykonania,opis, pacjent,telefon_pacjenta,cena,id_gabinetu,typ_pracy) values (gen_item_id.nextval,?,?,?,?,?,?,?,?,?,?,?) returning id_pracy into ?; end;";

		int workId = -1;
		
		try {
			conn.setAutoCommit(false);
			insertWork = conn.prepareCall(insertStatement);
			insertWork.setInt(1,work.getWorkType_id());
			insertWork.setInt(2, work.getDoctor_id());
			insertWork.setDate(3, work.getDeadline());
			insertWork.setDate(4, work.getDelivery_date());
			insertWork.setDate(5, work.getFinish_date());        
			insertWork.setString(6, work.getDescription());
			insertWork.setString(7, work.getPacient()); 
			insertWork.setString(8,"brak numerku");
			insertWork.setDouble(9,work.getPrice());
			insertWork.setInt(10, work.getLab().getId());
			insertWork.setString(11, work.getWork_type());
			insertWork.registerOutParameter(12, Types.INTEGER);
			insertWork.execute();
			
			workId = insertWork.getInt(12);
			
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			if (conn != null) {
				try {
					System.err.print(
							"Transaction is being "
									+ "rolled back");
					conn.rollback();
				} catch (SQLException excep) {
					System.out.println(excep);
				}
			}
		} finally {
			if (insertWork != null) {
				insertWork.close();
			}
		}

		generatePrintId(workId, month, year);
		
		
		for(WorkTask workTask : workTaskList){


			PreparedStatement insertWorkTask = null;
			insertStatement =
					"insert into WYKONANIA (id_pracy,id_pracownika,id_typu_pracy,id_czynnosci,data,prowizja) values (gen_item_id.currval,?,?,?,?,?)";

			try {
				insertWorkTask = conn.prepareStatement(insertStatement);
				insertWorkTask.setInt(1,workTask.getWorker_id());
				insertWorkTask.setInt(2,workTask.getWorkType_id());
				insertWorkTask.setInt(3, workTask.getElement_id());
				insertWorkTask.setDate(4, workTask.getDeadline());   
				insertWorkTask.setDouble(5, workTask.getProvision());
				insertWorkTask.executeUpdate();
				
			} catch (SQLException e) {
				System.out.println(e);
				if (conn != null) {
					try {
						System.err.print(
								"Transaction is being "
										+ "rolled back");
						conn.rollback();
					} catch (SQLException excep) {
						System.out.println(excep);
					}
				}
			} finally {
				if (insertWorkTask != null) {
					insertWorkTask.close();
				}
				conn.setAutoCommit(true);
			}
			
			conn.commit();
		}
	}
	
	public void generatePrintId(int workId, int month, int year) throws SQLException{
		
		// add new work
				PreparedStatement insertWork = null;
				String insertStatement =
						
						
					"update prace set print_id = " + 
					"( " + 
					"select " + 
					"new_id || '/' || month || '/' || year " + 
					"as ID " + 
					"from " + 
					"( " + 
					"select " + 
					"extract(month from data_zgloszenia) as month,extract(year from data_zgloszenia) as year,count(id_pracy) as new_id " + 
					"from prace  " + 
					"where extract(month from data_zgloszenia)=? and extract(year from data_zgloszenia)=? " + 
					"group by extract(month from data_zgloszenia),extract(year from data_zgloszenia) " + 
					") " + 
					") " + 
					"where id_pracy = ?";

				try {
					conn.setAutoCommit(false);
					insertWork = conn.prepareStatement(insertStatement);
					insertWork.setInt(1,month);
					insertWork.setInt(2, year);
					insertWork.setInt(3, workId);
					insertWork.executeUpdate();
					
				} catch (SQLException e) {
					System.out.println(e);
					e.printStackTrace();
					if (conn != null) {
						try {
							System.err.print(
									"Transaction is being "
											+ "rolled back");
							conn.rollback();
						} catch (SQLException excep) {
							System.out.println(excep);
						}
					}
				} finally {
					if (insertWork != null) {
						insertWork.close();
					}
				}
		
	}

	// edytuje dane pracy, przydziel mu kolejny numer id (trzeba by to w bazie zrobić)
	public void updateWork(Work work, ArrayList<WorkTask> workTaskList) throws SQLException{



		PreparedStatement updateWork = null;
		String updateStatement =
				"update PRACE set id_typu_pracy=?, id_lekarza=?,termin=?,data_zgloszenia=?,"
						+ "data_wykonania=?,opis=?, pacjent=?,telefon_pacjenta=?,cena=?,id_gabinetu=? " +
						"where id_pracy = ?";

		try {
			conn.setAutoCommit(false);
			updateWork = conn.prepareStatement(updateStatement);
			updateWork.setInt(1,work.getWorkType_id());
			updateWork.setInt(2, work.getDoctor_id());
			updateWork.setDate(3, work.getDeadline());
			updateWork.setDate(4, work.getDelivery_date());
			updateWork.setDate(5, work.getFinish_date());        
			updateWork.setString(6, work.getDescription());
			updateWork.setString(7, work.getPacient()); 
			updateWork.setString(8,"brak numerku");
			updateWork.setDouble(9,work.getPrice());
			updateWork.setInt(10, work.getLab().getId());
			updateWork.setInt(11, work.getId());
			updateWork.executeUpdate();
						

			// delete previous work tasks
			PreparedStatement deletePraca = null;
			String deleteStatement =
					"delete from WYKONANIA where ID_PRACY=?";

			try {
				deletePraca = conn.prepareStatement(deleteStatement);
				deletePraca.setInt(1, work.getId());
				deletePraca.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e);
			} finally {
				if (deletePraca != null) {
					deletePraca.close();
				}
			}

			//conn.commit();
			System.out.println("Size: ");

			for(WorkTask workTask : workTaskList){


				PreparedStatement insertWorkTask = null;
				String insertStatement =
						"insert into WYKONANIA (id_pracy,id_pracownika,id_typu_pracy,id_czynnosci,data,prowizja) values (gen_item_id.currval,?,?,?,?,?)";

					insertWorkTask = conn.prepareStatement(insertStatement);
					insertWorkTask.setInt(1,workTask.getWorker_id());
					insertWorkTask.setInt(2,workTask.getWorkType_id());
					insertWorkTask.setInt(3, workTask.getElement_id());
					insertWorkTask.setDate(4, workTask.getDeadline());   
					insertWorkTask.setDouble(5, workTask.getProvision());
					insertWorkTask.executeUpdate();
			
			}
			conn.commit();
			//return true;
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
			if (conn != null) {
				try {
					System.err.print(
							"Transaction is being "
									+ "rolled back");
					conn.rollback();
				} catch (SQLException excep) {
					System.out.println(excep);
					//return false;
				}
			}
		} finally {
			if (updateWork != null) {
				updateWork.close();
				//return true;
			}
			//conn.setAutoCommit(true);
			//return true;
		}


		//return true;
	}

	// 
	public void deleteWork(int id) throws SQLException{
		
		
		PreparedStatement deletePraca = null;
		String deleteStatement =
				"delete from WYKONANIA where ID_PRACY=?";

		try {
			deletePraca = conn.prepareStatement(deleteStatement);
			deletePraca.setInt(1, id);
			deletePraca.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (deletePraca != null) {
				deletePraca.close();
			}
		}
		
		

		deletePraca = null;
		deleteStatement =
				"update PRACE set aktywny=0 where ID_PRACY=?";

		try {
			deletePraca = conn.prepareStatement(deleteStatement);
			deletePraca.setInt(1, id);
			deletePraca.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (deletePraca != null) {
				deletePraca.close();
			}
		}
		
		conn.commit();
	}

	// zwraca tablicę z wszystkimi typami prac
	public ArrayList<WorkType> getWorkTypesList() throws SQLException{


		ArrayList<WorkType> workTypesList = new ArrayList();
		Statement stmt = null;
		String query =
				"select * from TYP_PRACY where aktywny=1";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				WorkType workType = new WorkType();
				workType.setId(rs.getInt("id_typu_pracy"));
				workType.setWorkType(rs.getString("typ_pracy"));
				workType.setPrice(rs.getDouble("cena"));
				workTypesList.add(workType);
			}

		} catch (SQLException e ) {
			System.out.println(e);
		} finally {
			if (stmt != null) { stmt.close(); }
		}
		return workTypesList;

	}

	//wyszukiwanie zlecen po:
	// 0 deadline'ie
	// 1 dacie zgloszenia
	// 2 dacie wykonania
	// jeszcze powinno być wyszukiwanie po id lekarza którego to dotyczny, jesli doc_id = -1 to dla wszystkich lekarzy
	public ArrayList<Work> getWorkList(int searchCondition, Date starttime, Date endtime, int doc_id) throws SQLException {

		ArrayList<Work> listaPrac = new ArrayList();

		PreparedStatement prepStmt = null;
		String query =
				"select * from V_PRACE where ";

		switch(searchCondition){
		case 0:
			query+="termin >= ? and termin <= ?"; break;
		case 1:
			query+="data_zgloszenia >= ? and data_zgloszenia <= ?"; break;
		case 2:
			query+="data_wykonania >= ? and data_wykonania <= ?"; break;
		default:
			query+="termin >= ? and termin <=?"; break;
		}
		try {
			prepStmt = conn.prepareStatement(query);
			prepStmt.setDate(1,starttime);
			prepStmt.setDate(2,endtime);
			loadWork(listaPrac, prepStmt);

		} catch (SQLException e ) {
			System.out.println(e);
		} finally {
			if (prepStmt != null) { prepStmt.close(); }
		}
		return listaPrac;

	}

	private void loadWork(ArrayList<Work> listaPrac, PreparedStatement prepStmt)
			throws SQLException {
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next()) {
			Work work = new Work();
			work.setId(rs.getInt("id_pracy"));
			work.setPrice(rs.getDouble("cena"));
			//work.setWorkType_id(rs.getInt("typ_pracy"));
			//work.setPacient_id(rs.getInt("id_pacjenta"));
			work.setDoctor_id(rs.getInt("id_lekarza"));
			Lab lab = new Lab();
			lab.setId(rs.getInt("id_gabinetu"));
			lab.setNazwa(rs.getString("nazwa"));
			work.setLab(lab);
			work.setDeadline(rs.getDate("termin"));
			work.setDelivery_date(rs.getDate("data_zgloszenia"));
			work.setFinish_date(rs.getDate("data_wykonania"));
			work.setDescription(rs.getString("opis"));
			work.setPacient(rs.getString("pacjent"));
			work.setDoctor(rs.getString("lekarz_imie")+" "+rs.getString("lekarz_nazwisko"));
			work.setWork_type(rs.getString("typ_pracy"));
			work.setStatus( rs.getString("wykonany").equals("1") );
			work.setSettled( rs.getString("rozliczony").equals("1") );
			work.setPrintId(rs.getString("print_id"));
			//work.setPacients_phone(rs.getString("telefon_pacjenta"));
			listaPrac.add(work);

		}
		
	}

	public ArrayList<Work> getWorkList(int doc_id, int labId, int span, String patientName, Date startDate, Date stopDate, boolean showAccounted) throws SQLException{

		ArrayList<Work> listaPrac = new ArrayList();

		//PreparedStatement stmt = null;
		String query = null;
		query = "select * from V_PRACE";
		
		int index = 1;
		
		// przy braku zakresu czasu pokazuje dane z ostatnich 10lat
		if(span==-1)
			span = 365*10;

		// data od do
		if (startDate != null && stopDate != null){
			query += " where DATA_ZGLOSZENIA between ? and ?";
		}
		// je�eli nie po dacie to po spanie (ostatni tydzien etc. )
		else{

			query += " where (SYSDATE-TERMIN) < ?";
		}
			
		if(doc_id!=-1)
			query += " and ID_LEKARZA=?";
		
		if(labId!=-1)
			query += " and ID_GABINETU=?";
		if(!patientName.equals(""))
			query += " and PACJENT=?";
		
		if(!showAccounted)
			query += " and rozliczony=0";
		
		
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(query);
			
			if(startDate != null && stopDate != null){
				stmt.setDate(index++, startDate);
				stmt.setDate(index++, stopDate);
			} else{
				stmt.setInt(index++, span);
			}
			if(doc_id!=-1)
				stmt.setInt(index++, doc_id);
			if(labId!=-1)
				stmt.setInt(index++, labId);
			if(!patientName.equals(""))
				stmt.setString(index++, patientName);

			loadWork(listaPrac, stmt);

		} catch (SQLException e ) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if (stmt != null) { stmt.close(); }
		}
		return listaPrac;
	}

	public ArrayList<Work> getWorkList() throws SQLException{

		ArrayList<Work> listaPrac = new ArrayList();

		Statement stmt = null;
		String query = "select * from V_PRACE";


		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Work work = new Work();
				work.setId(rs.getInt("id_pracy"));
				work.setWork_type(rs.getString("typ_pracy"));
				work.setDoctor(rs.getString("lekarz_imie")+" "+rs.getString("lekarz_nazwisko"));
				Lab lab = new Lab();
				lab.setId(rs.getInt("id_gabinetu"));
				lab.setNazwa(rs.getString("nazwa"));
				work.setLab(lab);
				work.setPrice(rs.getInt("cena"));
				work.setPacient(rs.getString("pacjent"));            
				work.setDeadline(rs.getDate("termin"));
				work.setDelivery_date(rs.getDate("data_zgloszenia"));
				work.setFinish_date(rs.getDate("data_wykonania"));
				work.setDescription(rs.getString("opis"));
				work.setPrintId(rs.getString("print_id"));
				work.setSettled(rs.getBoolean("rozliczony"));
				work.setStatus(rs.getBoolean("wykonany"));
				
				listaPrac.add(work);
			}

		} catch (SQLException e ) {
			System.out.println(e);
		} finally {
			if (stmt != null) { stmt.close(); }
		}
		return listaPrac;
	}
	// inne

	/////////////////////////////////////////////////// zaległości i wynagrodzenia

	//Zmieniłem, żeby było bardziej obiektowo
	// zaległości
	// argumentem jest imie pracownika
	// jeśli id = -1 to dla wszystkich pracowników
	// w pole name -> imię + nazwisko z tabeli lekarze/pracownicy - np. "Jan Kowalski"
	// values - wartości zaległości dla danego miesiąca
	// month - jakiś string z datą, z którego pochodzą zaległości np. 2012/04
	public ArrayList<Arrear> getArrearsList(int doctor_id){

		/* TO DO for Grzesio */

		return null;

	}

	// analogicznie tylko wynagrodzenia i pracownicy tez na klasie arrear
	// workers imię i nazwisko z tabeli pracownicy - np. "Jan Kowalski"
	public ArrayList<Arrear> getSalaryList(int worker_id){

		/* TO DO for Grzesio */

		return null;
	}


	// Trzeba dodać prowizję do czynności składowej /////////////////
	public void addSubTask(SubTask cs) throws SQLException {



		PreparedStatement insert = null;
		String insertStatement =
				"insert into CZYNNOSCI_SKLADOWE (czynnosc, prowizja) values (?,?)";

		try {
			conn.setAutoCommit(false);
			insert = conn.prepareStatement(insertStatement);
			insert.setString(1,cs.getCzynnosc());
			insert.setDouble(2,cs.getProwizja());
			insert.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			System.out.println(e);
			if (conn != null) {
				try {
					System.err.print(
							"Transaction is being "
									+ "rolled back");
					conn.rollback();
				} catch (SQLException excep) {
					System.out.println(excep);
				}
			}
		} finally {
			if (insert != null) {
				insert.close();
			}
			conn.setAutoCommit(true);
		}        
	}

	/**
	 * Funkcja na poczatku pobiera z sekwencji kolejne numery typu_pracy oraz czynnosci_skaldowej
	 * a nastepnie wrzuca do CZYNNOSCI_SKLADOWE oraz TYPU_PRACY odpowiednie wiersze.
	 * Obiekty z tych dwoch tabel sa laczone w tabeli definicje_prac.
	 * Nalezalo usnac trigger on insert z tabel czynnosci_skladowe oraz typ_pracy.
	 * @param workType
	 * @throws SQLException 
	 */
	public void addWorkType(WorkType workType) throws SQLException {

		// pobranie ID_TYPU_PRACY        
		int id_typu_pracy=0;
		//int id_czynnosci = 0;

		conn.setAutoCommit(false);

		Statement stmt = null;
		String query = "select gen_item_id.nextval from dual";

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				id_typu_pracy=rs.getInt(1);
			}
		} catch (SQLException e ) {
			System.out.println(e);
		} finally {
			if (stmt != null) { stmt.close(); }
		}

		////dodanej nowego typu pracy
		PreparedStatement insert = null;
		String insertStatement =
				"insert into TYP_PRACY (id_typu_pracy,typ_pracy,cena) values (?,?,?)";

		try {
			conn.setAutoCommit(false);
			insert = conn.prepareStatement(insertStatement);
			insert.setInt(1,id_typu_pracy);
			insert.setString(2,workType.getWorkType());
			insert.setDouble(3,workType.getPrice());

			insert.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			if (conn != null) {
				try {
					System.err.print(
							"Transaction is being "
									+ "rolled back");
					conn.rollback();
				} catch (SQLException excep) {
					System.out.println(excep);
				}
			}
		} finally {
			if (insert != null) {
				insert.close();
			}
			//conn.commit();
		}        
		//conn.commit();

		////dodanej nowych czynnosci skladowych


		for(SubTask cs : workType.getElements()) {


			//potem dodanie czynnosci skladowej z pobranych wczensiej z sekwencji, id
			insert = null;
			insertStatement =
					"insert into CZYNNOSCI_SKLADOWE (id_czynnosci,czynnosc,prowizja_pracownika,id_typu_pracy) values (gen_item_id.nextval,?,?,?)";

			try {

				insert = conn.prepareStatement(insertStatement);

				insert.setString(1,cs.getCzynnosc());
				insert.setDouble(2,cs.getProwizja());
				insert.setInt(3,id_typu_pracy+1);
				insert.executeUpdate();
				conn.commit();
			} catch (SQLException e) {
				System.out.println(e);
				if (conn != null) {
					try {
						System.err.print(
								"Transaction is being "
										+ "rolled back");
						conn.rollback();
					} catch (SQLException excep) {
						System.out.println(excep);
					}
				}
			} finally {
				if (insert != null) {
					insert.close();
				}
				conn.setAutoCommit(true);
			}



		}
	}


	public void updateWorkType(WorkType workType) throws SQLException {

		// pobranie ID_TYPU_PRACY        
		int id_typu_pracy=0;
		//int id_czynnosci = 0;


		////dodanej nowego typu pracy
		PreparedStatement insert = null;
		String insertStatement =
				"update TYP_PRACY set typ_pracy=?,cena=? where id_typu_pracy=?";

		try {
			conn.setAutoCommit(false);
			insert = conn.prepareStatement(insertStatement);
			insert.setString(1,workType.getWorkType());
			insert.setDouble(2,workType.getPrice());
			insert.setInt(3,workType.getId());

			insert.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			if (conn != null) {
				try {
					System.err.print(
							"Transaction is being "
									+ "rolled back");
					conn.rollback();
				} catch (SQLException excep) {
					System.out.println(excep);
				}
			}
		} finally {
			if (insert != null) {
				insert.close();
			}
			//conn.commit();
		}        
		//conn.commit();

		////dodanej nowych czynnosci skladowych
		
		insert = null;
		insertStatement =
				"update CZYNNOSCI_SKLADOWE set aktywny=0 where id_typu_pracy=?";

		try {

			insert = conn.prepareStatement(insertStatement);

			insert.setInt(1,workType.getId());
			insert.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			System.out.println(e);
			if (conn != null) {
				try {
					System.err.print(
							"Transaction is being "
									+ "rolled back");
					conn.rollback();
				} catch (SQLException excep) {
					System.out.println(excep);
				}
			}
		} finally {
			if (insert != null) {
				insert.close();
			}
			conn.setAutoCommit(true);
		}


		for(SubTask cs : workType.getElements()) {


			//potem dodanie czynnosci skladowej z pobranych wczensiej z sekwencji, id
			insert = null;
			insertStatement =
					"insert into CZYNNOSCI_SKLADOWE (id_czynnosci,czynnosc,prowizja_pracownika,id_typu_pracy) values (gen_item_id.nextval,?,?,?)";

			try {

				insert = conn.prepareStatement(insertStatement);

				insert.setString(1,cs.getCzynnosc());
				insert.setDouble(2,cs.getProwizja());
				insert.setInt(3,workType.getId());
				insert.executeUpdate();
				conn.commit();
			} catch (SQLException e) {
				System.out.println(e);
				if (conn != null) {
					try {
						System.err.print(
								"Transaction is being "
										+ "rolled back");
						conn.rollback();
					} catch (SQLException excep) {
						System.out.println(excep);
					}
				}
			} finally {
				if (insert != null) {
					insert.close();
				}
				conn.setAutoCommit(true);
			}



		}
	}


	public void addPatient(Patient patient) throws SQLException {

		PreparedStatement insert = null;
		String insertStatement =
				"insert into PACJENCI (pacjent,telefon) values (?,?)";

		try {
			conn.setAutoCommit(false);
			insert = conn.prepareStatement(insertStatement);
			insert.setString(1,patient.getName());
			insert.setString(2,patient.getPhone());
			insert.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			System.out.println(e);
			if (conn != null) {
				try {
					System.err.print(
							"Transaction is being "
									+ "rolled back");
					conn.rollback();
				} catch (SQLException excep) {
					System.out.println(excep);
				}
			}
		} finally {
			if (insert != null) {
				insert.close();
			}
			conn.setAutoCommit(true);
		}                
	}

	////////////////// NOWE!!!


	// zwraca cenę w PLN za określony typ pracy
	public double getWorkPrice(int workType_id) throws SQLException{

		double price = 0.0;

		PreparedStatement stmt = null;
		String query = "select cena from TYP_PRACY where id_typu_pracy=?";


		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, workType_id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				price = rs.getInt("cena");
			}
		} catch (SQLException e ) {
			System.out.println(e);
		} finally {
			if (stmt != null) { stmt.close(); }
		}
		return price;
	}

	// zwraca czynności składowe za określony typ pracy np.
	public ArrayList<SubTask> getSubTask(int workType_id) throws SQLException{


		ArrayList<SubTask> listaCzynnosciSkladowych = new ArrayList();

		PreparedStatement stmt = null;
		String query = "select * from CZYNNOSCI_SKLADOWE where ID_TYPU_PRACY = ? and aktywny=1";


		try {
			stmt = conn.prepareStatement(query);
			System.out.println(workType_id);
			stmt.setInt(1, workType_id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				SubTask cs = new SubTask();
				cs.setId(rs.getInt("id_czynnosci"));
				cs.setCzynnosc(rs.getString("czynnosc"));
				cs.setProwizja(rs.getDouble("prowizja_pracownika"));
				listaCzynnosciSkladowych.add(cs);
			}

		} catch (SQLException e ) {
			System.out.println(e);
		} finally {
			if (stmt != null) { stmt.close(); }
		}
		return listaCzynnosciSkladowych;
	}


	////////////////////// workTasks

	// zwraca wykonania, które wykonał pracownik o zadanym id
	public ArrayList<WorkTask> getWorkTasks(int worker_id,int span) throws SQLException{

		ArrayList<WorkTask> listaZadan = new ArrayList<WorkTask> ();

		PreparedStatement stmt = null;
		String query="";

		// przy braku zakresu czasu pokazuje dane z ostatnich 10lat
		if(span==-1)
			span = 365*10;

		if(worker_id==-1)
			query = "select * from V_WYKONANIA where (SYSDATE-TERMIN) < ?";
		else
			query = "select * from V_WYKONANIA where (SYSDATE-TERMIN) < ? and ID_PRACOWNIKA=?";


		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, span);
			if(worker_id!=-1)
				stmt.setInt(2, worker_id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				WorkTask wt = new WorkTask();
				Worker w = new Worker();
				w.setName(rs.getString("pracownik_imie"));
				w.setSurname(rs.getString("pracownik_nazwisko"));
				w.setWorker_id(rs.getInt("ID_PRACOWNIKA"));
				wt.setWorker(w);
				wt.setDeadline(rs.getDate("termin"));
				wt.setElement(rs.getString("czynnosc_skladowa"));
				wt.setProvision(rs.getInt("prowizja_pracownika"));
				wt.setWorkType(rs.getString("typ_pracy"));
				wt.setLekarz(rs.getString("lekarz"));
				wt.setPatient(rs.getString("pacjent"));
				listaZadan.add(wt);
			}

		} catch (SQLException e ) {
			System.out.println(e);
		} finally {
			if (stmt != null) { stmt.close(); }
		}
		return listaZadan;
	}
	
	// zwraca wykonania, które wykonał pracownik o zadanym id
	public ArrayList<WorkTask> getWorkTasksAndWorkers(int workId) throws SQLException{

		ArrayList<WorkTask> listaZadan = new ArrayList<WorkTask> ();

		PreparedStatement stmt = null;
		String query="";
		
		System.out.println("Out: "+workId);

		query = "select * from v_wykonania where ID_PRACY = ?";

		try {
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, workId);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				WorkTask wt = new WorkTask();
				Worker w = new Worker();
				w.setName(rs.getString("pracownik_imie"));
				w.setSurname(rs.getString("pracownik_nazwisko"));
				w.setWorker_id(rs.getInt("ID_PRACOWNIKA"));
				wt.setWorker(w);
				wt.setDeadline(rs.getDate("termin"));
				wt.setElement(rs.getString("czynnosc_skladowa"));
				wt.setElement_id(rs.getInt("id_czynnosci"));
				wt.setProvision(rs.getInt("prowizja_pracownika"));
				wt.setWorkType(rs.getString("typ_pracy"));
				wt.setLekarz(rs.getString("lekarz"));
				wt.setPatient(rs.getString("pacjent"));
				listaZadan.add(wt);
			}

		} catch (SQLException e ) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if (stmt != null) { stmt.close(); }
		}
		return listaZadan;
	}

	// dodaje wykonania
	public boolean addWorkTask(WorkTask workTask){

		return true;
	}

	public boolean deleteWorkTask(int workTask_id){

		return true;
	}

	public void deleteWorkType(int id) throws SQLException {

		PreparedStatement deleteTypPracy = null;
		String deleteStatement =
		"update TYP_PRACY set "
		+ "aktywny = ? "
		+ "where ID_TYPU_PRACY = ?";

		try {
			deleteTypPracy = conn.prepareStatement(deleteStatement);
			deleteTypPracy.setInt(1, 0);
			deleteTypPracy.setInt(2, id);
			deleteTypPracy.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (deleteTypPracy != null) {
				deleteTypPracy.close();
			}
		}

	}
	
	public void markAccounted(int workId) throws SQLException{
		
		PreparedStatement prepStatement = null;
		String query =
		"update PRACE set "
		+ "rozliczony = 1 "
		+ "where ID_PRACY = ?";

		try {
			prepStatement = conn.prepareStatement(query);
			prepStatement.setInt(1, workId);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (prepStatement != null) {
				prepStatement.close();
			}
		}
	}
	
	public void markUnaccounted(int workId) throws SQLException{
		
		PreparedStatement prepStatement = null;
		String query =
		"update PRACE set "
		+ "rozliczony = 0 "
		+ "where ID_PRACY = ?";

		try {
			prepStatement = conn.prepareStatement(query);
			prepStatement.setInt(1, workId);
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (prepStatement != null) {
				prepStatement.close();
			}
		}
	}

public void markUnDone(int workId) throws SQLException{
	
	PreparedStatement prepStatement = null;
	String query =
	"update PRACE set "
	+ "wykonany = 0 , data_wykonania = null "
	+ "where ID_PRACY = ?";

	try {
		prepStatement = conn.prepareStatement(query);
		prepStatement.setInt(1, workId);
		prepStatement.executeUpdate();
	} catch (SQLException e) {
		System.out.println(e);
	} finally {
		if (prepStatement != null) {
			prepStatement.close();
		}
	}
}

public void markDone(int workId) throws SQLException{
	
	PreparedStatement prepStatement = null;
	String query =
	"update PRACE set "
	+ "wykonany = 1, data_wykonania = CURRENT_DATE "
	+ "where ID_PRACY = ?";

	try {
		prepStatement = conn.prepareStatement(query);
		prepStatement.setInt(1, workId);
		prepStatement.executeUpdate();
	} catch (SQLException e) {
		System.out.println(e);
	} finally {
		if (prepStatement != null) {
			prepStatement.close();
		}
	}
}

	public void updateWorkTasks(ArrayList<WorkTask> workTaskList) {
		// TODO Auto-generated method stub
		
	}
	
	public ArrayList<Arrear> getArrears(int doctorId, int labId, Date fromDate, Date toDate) throws SQLException {

		ArrayList<Arrear> arrears = new ArrayList<Arrear>();
		PreparedStatement prepStatement = null;

		try {


			String query =
					"select * from PRACE ";
					
			// mark by lab
			if ( labId != -1 ){
				query += "where ID_GABINETU = ? ";
			} else{
				query += "where ID_LEKARZA = ? ";
			}


			// add date interval
			query += "and DATA_WYKONANIA between ? and ? and rozliczony = 0";


			prepStatement = conn.prepareStatement(query);
			if ( labId != -1 )
				prepStatement.setInt(1, labId);
			else
				prepStatement.setInt(1, doctorId);

			prepStatement.setDate(2, fromDate);
			prepStatement.setDate(3, toDate);

			ResultSet rs = prepStatement.executeQuery();
			while (rs.next()) {
				Arrear a = new Arrear();
				
				arrears.add(a);
			}
			
			} catch (SQLException e) {
				System.out.println(e);
			} finally {
				if (prepStatement != null) {
					prepStatement.close();
				}
			}

			return arrears;

	}

	public void markAccounted(int labId, int doctorId, Date fromDate, Date toDate) throws SQLException{
		
		PreparedStatement prepStatement = null;
		String query =
		"update PRACE set "
		+ "rozliczony = 1 ";
		
		// mark by lab
		if ( labId != -1 ){
			query += "where ID_GABINETU = ? ";
		} else{
			query += "where ID_LEKARZA = ? ";
		}
		
		
		// add date interval
		query += "and DATA_WYKONANIA between ? and ? and rozliczony = 0";
	
		try {
			prepStatement = conn.prepareStatement(query);
			if ( labId != -1 )
				prepStatement.setInt(1, labId);
			else
				prepStatement.setInt(1, doctorId);
			
			prepStatement.setDate(2, fromDate);
			prepStatement.setDate(3, toDate);
			
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (prepStatement != null) {
				prepStatement.close();
			}
		}
	}
	
	
	public void dbInit() throws SQLException{
		PreparedStatement prepStatement = null;
		String query =
		"select gen_item_id.nextval from dual";

	
		try {
			prepStatement = conn.prepareStatement(query);
			
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (prepStatement != null) {
				prepStatement.close();
			}
		}
	}

}
