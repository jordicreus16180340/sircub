/**
 * 
 */
package sircub;

import java.io.FileWriter;
import java.io.IOException;

import org.jdom2.*;
import org.jdom2.input.sax.*;
import org.jdom2.input.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 * @author jordi
 *
 */
public class Configuration {

	private static final String configurationPath = "xml/configuration.xml";
	
	private static final SAXEngine engine = new SAXBuilder();
	private static Document document;
	private static Element configurationElement;
	
	public static void initialize() throws JDOMException, IOException {
		document = engine.build(configurationPath);
		Element sircubElement = document.getRootElement();
		configurationElement = sircubElement.getChild("configuration");
		
		Element databaseConnectionElement = configurationElement.getChild("database-connection");
		Element hostnameElement = databaseConnectionElement.getChild("hostname");
		Sircub.hostname = hostnameElement.getText();
		Element databaseElement = databaseConnectionElement.getChild("database");
		Sircub.database = databaseElement.getText();
		Element userElement = databaseConnectionElement.getChild("user");
		Sircub.user = userElement.getText();
		Element passwordElement = databaseConnectionElement.getChild("password");
		Sircub.password = passwordElement.getText();
		
		Element timesatDirectoryElement = configurationElement.getChild("timesat-directory");
		Sircub.timesatDirectory = timesatDirectoryElement.getText();
		
		Element libsvmDirectoryElement = configurationElement.getChild("libsvm-directory");
		Sircub.libsvmDirectory = libsvmDirectoryElement.getText();
		
		Element gnuplotExecutableElement = configurationElement.getChild("gnuplot-executable");
		Sircub.gnuplotExecutable = gnuplotExecutableElement.getText();
		
		Element libopfDirectoryElement = configurationElement.getChild("libopf-directory");
		Sircub.libopfDirectory = libopfDirectoryElement.getText();
		
		Element experimentsDirectoryElement = configurationElement.getChild("experiments-directory");
		Sircub.experimentsDirectory = experimentsDirectoryElement.getText();
		
		Element classificationsDirectoryElement = configurationElement.getChild("classifications-directory");
		Sircub.classificationsDirectory = classificationsDirectoryElement.getText();
	}
	
	public static void save() throws JDOMException, IOException {
		Element databaseConnectionElement = configurationElement.getChild("database-connection");
		Element hostnameElement = databaseConnectionElement.getChild("hostname");
		hostnameElement.setText(Sircub.hostname);
		Element databaseElement = databaseConnectionElement.getChild("database");
		databaseElement.setText(Sircub.database);
		Element userElement = databaseConnectionElement.getChild("user");
		userElement.setText(Sircub.user);
		Element passwordElement = databaseConnectionElement.getChild("password");
		passwordElement.setText(Sircub.password);
		
		Element timesatDirectoryElement = configurationElement.getChild("timesat-directory");
		timesatDirectoryElement.setText(Sircub.timesatDirectory);
		
		Element libsvmDirectoryElement = configurationElement.getChild("libsvm-directory");
		libsvmDirectoryElement.setText(Sircub.libsvmDirectory);
		
		Element gnuplotExecutableElement = configurationElement.getChild("gnuplot-executable");
		gnuplotExecutableElement.setText(Sircub.gnuplotExecutable);
		
		Element libopfDirectoryElement = configurationElement.getChild("libopf-directory");
		libopfDirectoryElement.setText(Sircub.libopfDirectory);
		
		Element experimentsDirectoryElement = configurationElement.getChild("experiments-directory");
		experimentsDirectoryElement.setText(Sircub.experimentsDirectory);
		
		Element classificationsDirectoryElement = configurationElement.getChild("classifications-directory");
		classificationsDirectoryElement.setText(Sircub.classificationsDirectory);
		
		XMLOutputter outputter = new XMLOutputter();
		outputter.output(document, new FileWriter(configurationPath));
	}
}



































