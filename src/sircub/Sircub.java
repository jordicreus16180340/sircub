/**
 * 
 */
package sircub;

import java.io.IOException;

import org.jdom2.JDOMException;

import sircub.gui.GUI;

/**
 * @author jordi
 *
 */
public class Sircub {

	// database connection
	public static String hostname;
	public static String database;
	public static String user;
	public static String password;
	
	public static String timesatDirectory;
	public static String libsvmDirectory;
	public static String gnuplotExecutable;
	public static String libopfDirectory;
	public static String experimentsDirectory;
	public static String classificationsDirectory;
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	public static void main(String[] args) throws JDOMException, IOException {
		// TODO Auto-generated method stub

		System.out.println("Hello!!");
		
		Configuration.initialize();
		
		DatabaseInterface.connect();
		
		GUI.start();
		
		System.out.println("Bye!!");
	}

}






























