/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sildent;

import dbconnection.DBConnector;

/**
 *
 * @author Aleks
 */
public class Config {

    public static final boolean LOGGING = true;
	public static DBConnector conn = null;
    public static boolean isAdmin = true;
    public static String user = null;
    
    
    // DB connection
    public static String login = null;
    public static String pass = null;
    
    public static String host = "localhost";
    public static int port = 1521;
    
    public static boolean isConnected = false;
}
