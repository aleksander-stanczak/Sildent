package sildent;

import gui.registry.WorkRegistryWindow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Sildent {

	// TEST
	
	public static ArrayList<Refreshable> windows = new ArrayList<Refreshable>();
	public static WorkRegistryWindow workRegistry = null;


	public static void boot(){
		try {
			loadConfig();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			loadConfig();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void printRefreshables(){

		for (Refreshable window : windows) {
			window.refresh();
		}
	}

	public static void refreshWindows(){

		for (Refreshable window : windows) {
			window.refresh();
		}
	}

	public static void refreshWorkRegistry(){

		if ( workRegistry != null ){
			workRegistry.refresh();
			workRegistry.refreshTable();
		}

	}

	public static void loadConfig() throws IOException{


		String dirPath = System.getProperty("user.home")+"/.sildent/";
		File dir = new File(dirPath);

		String fileName = "config.txt";
		File file = new File(dir+"/config.txt");

		if (file.exists()){

			BufferedReader br = new BufferedReader(new FileReader(
					file)
					);

			Config.login = br.readLine();
			Config.pass = br.readLine();
			Config.host = br.readLine();
			Config.port = Integer.parseInt(br.readLine());

			br.close();

		}

	}

	public static void saveConfig() throws IOException{

		String dirPath = System.getProperty("user.home")+"/.sildent/";
		File dir = new File(dirPath);

		File file = new File(dir+"/config.txt");


		// if the directory does not exist, create it
		if (!dir.exists()) {
			System.out.println("creating directory: " + dir);
			boolean result = dir.mkdir();  

			if(result) {    
				System.out.println("DIR created");       
			}
		}

		BufferedWriter bw = new BufferedWriter(new FileWriter(
				file)
				);

		bw.write(Config.login+"\n");
		bw.write(Config.pass+"\n");
		bw.write(Config.host+"\n");
		bw.write(Config.port+"\n");

		bw.close();

	}

}
