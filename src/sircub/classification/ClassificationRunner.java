/**
 * 
 */
package sircub.classification;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import sircub.Configuration;
import sircub.Constants;
import sircub.DatabaseInterface;
import sircub.Sircub;
import sircub.datamodel.Annotation;
import sircub.datamodel.Descriptor;
import sircub.datamodel.DescriptorDataset;
import sircub.datamodel.Instance;
import sircub.experiment.Experiment;

/**
 * @author jordi
 *
 */
public class ClassificationRunner implements Runnable {

	private Classification classification;
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws JDOMException 
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 * @throws TransformerException 
	 */
	public static void main(String[] args) throws JDOMException, IOException, TransformerException, SAXException, ParserConfigurationException {
		// TODO Auto-generated method stub

		System.out.println("Hello!!");
		
		Configuration.initialize();
		DatabaseInterface.connect();
		
		Classification classification = new Classification();
		classification.id = "classification_2004";
		classification.descriptorDatasetId = 1;
		//classification.nbYears = 4;
		classification.labelsPath = "/home/jordi/Dropbox/jordi/posdoc/datasets/dataset1_annotation_labels_en.csv";
		classification.roundPath = "/home/jordi/Dropbox/jordi/posdoc/experiments/experiment_3006/round1/";
		classification.targetClass = Constants.SECOND_SEASON;
		classification.nbClasses = 3;
		classification.experimentTechnique = Constants.SVM;
		
		(new ClassificationRunner(classification)).run2();
		
		System.out.println("Bye!!");
	}
	
	public ClassificationRunner(Classification classification) {
		this.classification = classification;
	}
	
	// only with GUI
	public void run() {
		try {
			run2();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run2() throws IOException, TransformerException, SAXException, ParserConfigurationException {
		// classification.output("Initializing classification task...");
		initializeClassification(classification);
		
		// classification.output("Creating classification directory...");
		// classification.output("Classification directory path: " + classification.directoryPath);
		createClassificationDirectory(classification.directoryPath);
		
		createLogFile(classification);
		
		ClassificationDatasetGenerator.generate(classification);
		
		ClassifierExecutor.execute(classification);
		
		PredictionEnricher.enrich(classification);
		
		classification.output("Done!");
	}
	
	private static void initializeClassification(Classification classification) {
		classification.directoryPath = Sircub.classificationsDirectory + classification.id + "/";
		
		DescriptorDataset descriptorDataset = DatabaseInterface.getDescriptorDataset(classification.descriptorDatasetId);
		classification.nbYears = descriptorDataset.nbYears;
	}
	
	private static void createClassificationDirectory(String classificationPath) {
		File classificationFile = new File(classificationPath);
		if(! classificationFile.exists())
			classificationFile.mkdir();
	}
	
	private static void createLogFile(Classification classification) throws IOException {
		FileWriter classificationFileWriter = new FileWriter(classification.directoryPath + classification.id + ".log");
		classification.output = new BufferedWriter(classificationFileWriter);
		classification.output("Log file: " + classification.directoryPath + classification.id + ".log");
	}

}






















































