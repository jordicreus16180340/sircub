/**
 * 
 */
package sircub.classification;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Text;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.SAXEngine;
import org.jdom2.output.XMLOutputter;

import sircub.Constants;
import sircub.datamodel.Instance;
import sircub.gui.GUI;

/**
 * @author jordi
 *
 */
public class Classification {

	private static final String classificationPath = "xml/classification.xml";
	
	private static final SAXEngine engine = new SAXBuilder();
	private static Document baseDocument;
	
	public String id;
	public String directoryPath;
	public int descriptorDatasetId;
	public int nbYears;
	//public int annotationDatasetId;
	public String labelsPath;
	public String roundPath;
	public int targetClass;
	public int nbClasses;
	public int experimentTechnique;
	public Map<Integer, List<Instance>> instanceMap;
	public List<Integer> sortedClasses;
	
	public BufferedWriter output;
	public Text text;
	
	public Classification() throws JDOMException, IOException {
		Element classificationElement = getClassificationElement();
		
		initialize(classificationElement);
	}
	
	public static Element getClassificationElement() throws JDOMException, IOException {
		baseDocument = engine.build(classificationPath);
		Element sircubElement = baseDocument.getRootElement();
		Element classificationElement = sircubElement.getChild("classification");
		
		return classificationElement;
	}
	
	private void initialize(Element classificationElement) {
		Element idElement = classificationElement.getChild("id");
		id = idElement.getText();
		
		Element descriptorDatasetElement = classificationElement.getChild("descriptor-dataset");
		String descriptorDatasetIdValue = descriptorDatasetElement.getAttributeValue("id");
		descriptorDatasetId = Integer.parseInt(descriptorDatasetIdValue);
		
		Element labelsFileElement = classificationElement.getChild("labels-file");
		labelsPath = labelsFileElement.getText();
		
		Element roundDirectoryElement = classificationElement.getChild("round-directory");
		roundPath = roundDirectoryElement.getText();
		
		Element targetClassElement = classificationElement.getChild("target-class");
		String targetClassText = targetClassElement.getText();
		if(targetClassText.equals("crop")) {
			targetClass = Constants.CROP;
		} else if(targetClassText.equals("first_season")) {
			targetClass = Constants.FIRST_SEASON;
		} else if(targetClassText.equals("second_season")) {
			targetClass = Constants.SECOND_SEASON;
		} else if(targetClassText.equals("pair")) {
			targetClass = Constants.PAIR;
		}
		
		Element experimentTechniqueElement = classificationElement.getChild("experiment-technique");
		String experimentTechniqueText = experimentTechniqueElement.getText();
		if(experimentTechniqueText.equals("svm")) {
			experimentTechnique = Constants.SVM;
		} else if(experimentTechniqueText.equals("opf")) {
			experimentTechnique = Constants.OPF;
		}
	}
	
	public void save(Element classificationElement) throws IOException {
		Element idElement = classificationElement.getChild("id");
		idElement.setText(id);
		
		Element descriptorDatasetElement = classificationElement.getChild("descriptor-dataset");
		descriptorDatasetElement.setAttribute("id", "" + descriptorDatasetId);
		
		Element labelsFileElement = classificationElement.getChild("labels-file");
		labelsFileElement.setText(labelsPath);
		
		Element roundDirectoryElement = classificationElement.getChild("round-directory");
		roundDirectoryElement.setText(roundPath);
		
		Element targetClassElement = classificationElement.getChild("target-class");
		if(targetClass == Constants.CROP)
			targetClassElement.setText("crop");
		else if(targetClass == Constants.FIRST_SEASON)
			targetClassElement.setText("first_season");
		else if(targetClass == Constants.SECOND_SEASON)
			targetClassElement.setText("second_season");
		else if(targetClass == Constants.PAIR)
			targetClassElement.setText("pair");
		
		Element experimentTechniqueElement = classificationElement.getChild("experiment-technique");
		if(experimentTechnique == Constants.SVM)
			experimentTechniqueElement.setText("svm");
		else if(experimentTechnique == Constants.OPF)
			experimentTechniqueElement.setText("opf");
		
		XMLOutputter outputter = new XMLOutputter();
		outputter.output(baseDocument, new FileWriter(classificationPath));
	}
	
	public void output(String string) throws IOException {
		GUI.classificationLogOutput(text, string);
		
		output.write(string + "\n");
		output.flush();
	}
}



























































