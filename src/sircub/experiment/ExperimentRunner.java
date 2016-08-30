/**
 * 
 */
package sircub.experiment;

import java.io.IOException;

import org.jdom2.JDOMException;

import sircub.Configuration;
import sircub.Constants;
import sircub.DatabaseInterface;
import sircub.gui.GUI;

/**
 * @author jordi
 *
 */
public class ExperimentRunner implements Runnable {

	private Experiment experiment;
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	public static void main(String[] args) throws IOException, JDOMException {
		// TODO Auto-generated method stub

		System.out.println("Hello!!");
		
		Configuration.initialize();
		
		DatabaseInterface.connect();
		
		Experiment experiment = new Experiment();
		
		(new ExperimentRunner(experiment)).run2();
		
		System.out.println("Bye!!");
	}
	
	public ExperimentRunner(Experiment experiment) {
		this.experiment = experiment;
	}
	
	// only with GUI
	public void run() {
		try {
			run2();
		} catch (IOException | JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GUI.openResultsTab(experiment);
	}
	
	public void run2() throws IOException, JDOMException {
		ExperimentDatasetGenerator.generate(experiment);
		experiment.output("");
		
		if(experiment.experimentTechnique == Constants.SVM) {
			experiment.output("Experiment technique: Support Vector Machine");
			LibsvmExecutor.execute(experiment);
		} else if(experiment.experimentTechnique == Constants.OPF) {
			experiment.output("Experiment technique: Optimum-Path Forest");
			LibopfExecutor.execute(experiment);
		}
		experiment.output("");
		
		//Experiment experiment = new Experiment();
		ResultAnalyser.perClassAccuracy(experiment);
		experiment.output.close();
		
		experiment.resultsToXml();
		
		experiment.writeXml();
	}

}
















































