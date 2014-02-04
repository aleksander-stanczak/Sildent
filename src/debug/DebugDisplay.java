package debug;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DebugDisplay {

	
	public static void popup(JFrame frame, Exception e){
		
		//custom title, error icon
		JOptionPane.showMessageDialog(frame,
		    e.toString(),
		    "B³¹d!",
		    JOptionPane.ERROR_MESSAGE);
		
	}
	
public static void popup(JFrame frame, String msg){
		
		//custom title, error icon
		JOptionPane.showMessageDialog(frame,
		    msg,
		    "Powiadomienie",
		    JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	public static void main(String[] args) {
		//popup(null,null);
	}
	
}
