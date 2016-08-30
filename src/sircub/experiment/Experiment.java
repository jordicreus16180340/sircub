/**
 * 
 */
package sircub.experiment;

import sircub.*;
import sircub.datamodel.ExperimentClass;
import sircub.datamodel.Instance;
import sircub.datamodel.Label;
import sircub.datamodel.Round;
import sircub.datamodel.RoundClass;
import sircub.gui.GUI;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.SAXEngine;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.CTabFolder;

/**
 * @author jordi
 *
 */
public class Experiment {

	private static final String experimentPath = "xml/experiment.xml";
	
	private static final SAXEngine engine = new SAXBuilder();
	private static Document baseDocument;
	
	public String id;
	public String directory;
	
	public int descriptorDatasetId;
	public int nbYears;
	public int annotationDatasetId;
	
	public int targetClass;
	public int nbClasses;
	public int validationTechnique;
	public int nbRounds;
	public int balancing;
	public int experimentTechnique;
	public int scaling;
	public int kernelType;
	public int runGnuplot;
	public int begEnd;
	
	public BufferedWriter output;
	public Document resultsDocument = new Document();
	public Text text;
	public CTabFolder experimentFolder;
	
	//public HashMap<Integer, List<AnnotatedParameterTuple>> dataSet = new HashMap<Integer, List<AnnotatedParameterTuple>>();
	public Map<Integer, List<Instance>> instanceMap;
	public List<Integer> sortedClasses;
	public List<Round> rounds;
	public List<ExperimentClass> experimentClasses;
	public double meanMeanAccuracy;
	public String meanMeanAccuracyPercentage;
	public double meanStandardDeviation;
	public String meanStandardDeviationPercentage;
	
	//public HashMap<Integer, List<AnnotatedParameterTuple>> monoSeasonPoints = new HashMap<Integer, List<AnnotatedParameterTuple>>();
	//public HashMap<Integer, List<AnnotatedParameterTuple>> missingSeasonPoints = new HashMap<Integer, List<AnnotatedParameterTuple>>();
	
	public Experiment() throws JDOMException, IOException {
		Element experimentElement = getExperimentElement();
		
		initalize(experimentElement);
		
		rounds = new ArrayList<Round>(nbRounds);
		experimentClasses = new ArrayList<ExperimentClass>(nbClasses);
	}
	
	public static Element getExperimentElement() throws JDOMException, IOException {
		baseDocument = engine.build(experimentPath);
		Element sircubElement = baseDocument.getRootElement();
		Element experimentElement = sircubElement.getChild("experiment");
		
		return experimentElement;
	}
	
	private void initalize(Element experimentElement) throws JDOMException, IOException {
		Element idElement = experimentElement.getChild("id");
		id = idElement.getText();
		
		Element descriptorDatasetElement = experimentElement.getChild("descriptor-dataset");
		String descriptorDatasetIdValue = descriptorDatasetElement.getAttributeValue("id");
		descriptorDatasetId = Integer.parseInt(descriptorDatasetIdValue);
		
		Element annotationDatasetElement = experimentElement.getChild("annotation-dataset");
		String annotationDatasetIdValue = annotationDatasetElement.getAttributeValue("id");
		annotationDatasetId = Integer.parseInt(annotationDatasetIdValue);
		
		Element targetClassElement = experimentElement.getChild("target-class");
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
		
		Element nbClassesElement = experimentElement.getChild("nb-classes");
		nbClasses = Integer.parseInt(nbClassesElement.getText());
		
		Element validationTechniqueElement = experimentElement.getChild("validation-technique");
		String validationTechniqueText = validationTechniqueElement.getText();
		if(validationTechniqueText.equals("random_sampling")) {
			validationTechnique = Constants.RANDOM_SAMPLING;
		} else if(validationTechniqueText.equals("k_fold")) {
			validationTechnique = Constants.K_FOLD;
		}
		
		Element nbRoundsElement = experimentElement.getChild("nb-rounds");
		nbRounds = Integer.parseInt(nbRoundsElement.getText());
		
		Element balancingElement = experimentElement.getChild("balancing");
		String balancingText = balancingElement.getText();
		if(balancingText.equals("unbalanced_training_testing")) {
			balancing = Constants.UNBALANCED_TRAINING_TESTING;
		} else if(balancingText.equals("balanced_training_unbalanced_testing")) {
			balancing = Constants.BALANCED_TRAINING_UNBALANCED_TESTING;
		} else if(balancingText.equals("balanced_training_testing")) {
			balancing = Constants.BALANCED_TRAINING_TESTING;
		}
		
		Element experimentTechniqueElement = experimentElement.getChild("experiment-technique");
		String experimentTechniqueText = experimentTechniqueElement.getText();
		if(experimentTechniqueText.equals("svm")) {
			experimentTechnique = Constants.SVM;
		} else if(experimentTechniqueText.equals("opf")) {
			experimentTechnique = Constants.OPF;
		}
		
		Element scalingElement = experimentElement.getChild("scaling");
		String scalingText = scalingElement.getText();
		
		Element kernelTypeElement = experimentElement.getChild("kernel-type");
		String kernelTypeText = kernelTypeElement.getText();
		if(kernelTypeText.equals("radial_basis_function")) {
			kernelType = Constants.RADIAL_BASIS_FUNCTION;
		} else if(kernelTypeText.equals("linear")) {
			kernelType = Constants.LINEAR;
		} else if(kernelTypeText.equals("polynomial")) {
			kernelType = Constants.POLYNOMIAL;
		}
		
		Element runGnuplotElement = experimentElement.getChild("run-gnuplot");
		String runGnuplotText = runGnuplotElement.getText();
		if(runGnuplotText.equals("do_not_run_gnuplot")) {
			runGnuplot = Constants.DO_NOT_RUN_GNUPLOT;
		} else if(runGnuplotText.equals("run_gnuplot")) {
			runGnuplot = Constants.RUN_GNUPLOT;
		}
		
		Element begEndElement = experimentElement.getChild("beg-end");
		String begEndText = begEndElement.getText();
		if(begEndText.equals("without_beg_end")) {
			begEnd = Constants.WITHOUT_BEG_END;
		} else if(begEndText.equals("with_beg_end")) {
			begEnd = Constants.WITH_BEG_END;
		}
	}
	
	public void resultsToXml() {
		//HashMap<Integer, Culture> cultureHashMap = DatabaseInterface.getCulturesAsHashMap();
		Map<Integer, Label> labelMap = DatabaseInterface.getLabelsAsMap(annotationDatasetId);
		
		Element sircubElement = resultsDocument.getRootElement();
		
		Element resultsElement = new Element("results");
		sircubElement.addContent(resultsElement);
		
		Element roundsElement = new Element("rounds");
		resultsElement.addContent(roundsElement);
		
		for(Round round : rounds) {
			Element roundElement = new Element("round");
			roundsElement.addContent(roundElement);
			roundElement.setAttribute("id", "" + round.id);
			
			Element classesElement = new Element("classes");
			roundElement.addContent(classesElement);
			
			for(RoundClass roundClass : round.roundClasses) {
				Element classElement = new Element("class");
				classesElement.addContent(classElement);
				classElement.setAttribute("id", "" + roundClass.getClassId());
				//Culture culture = cultureHashMap.get(roundClass.getClassId());
				Label label = labelMap.get(roundClass.getClassId());
				classElement.setAttribute("name", label.name);
				
				createAccuracyElement(classElement, roundClass.getAccuracy(), roundClass.getAccuracyPercentage());
			}
			
			Element meanElement = new Element("mean");
			roundElement.addContent(meanElement);
			
			createAccuracyElement(meanElement, round.meanAccuracy, round.meanAccuracyPercentage);
		}
		
		Element meanElement = new Element("mean");
		resultsElement.addContent(meanElement);
		
		Element classesElement = new Element("classes");
		meanElement.addContent(classesElement);
		
		for(ExperimentClass experimentClass : experimentClasses) {
			Element classElement = new Element("class");
			classesElement.addContent(classElement);
			classElement.setAttribute("id", "" + experimentClass.getClassId());
			//Culture culture = cultureHashMap.get(experimentClass.getClassId());
			Label label = labelMap.get(experimentClass.getClassId());
			classElement.setAttribute("name", label.name);
			
			createAccuracyElement(classElement, experimentClass.getMeanAccuracy(), experimentClass.getMeanAccuracyPercentage());
			createStandardDeviationElement(classElement, experimentClass.getStandardDeviation(), experimentClass.getStandardDeviationPercentage());
		}
		
		Element meanElement2 = new Element("mean");
		meanElement.addContent(meanElement2);
		
		createAccuracyElement(meanElement2, meanMeanAccuracy, meanMeanAccuracyPercentage);
		createStandardDeviationElement(meanElement2, meanStandardDeviation, meanStandardDeviationPercentage);
	}
	
	private void createAccuracyElement(Element parentElement, double accuracy, String accuracyPercentage) {
		Element accuracyElement = new Element("accuracy");
		parentElement.addContent(accuracyElement);
		
		Element valueElement = new Element("value");
		accuracyElement.addContent(valueElement);
		valueElement.setText("" + accuracy);
		
		Element percentageElement = new Element("percentage");
		accuracyElement.addContent(percentageElement);
		percentageElement.setText(accuracyPercentage);
	}
	
	private void createStandardDeviationElement(Element parentElement, double standardDeviation, String standardDeviationPercentage) {
		Element standardDeviationElement = new Element("standard-deviation");
		parentElement.addContent(standardDeviationElement);
		
		Element valueElement = new Element("value");
		standardDeviationElement.addContent(valueElement);
		valueElement.setText("" + standardDeviation);
		
		Element percentageElement = new Element("percentage");
		standardDeviationElement.addContent(percentageElement);
		percentageElement.setText(standardDeviationPercentage);
	}
	
	public void writeXml() throws IOException {
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		outputter.output(resultsDocument, new FileWriter(directory + id + ".xml"));
	}
	
	public void save(Element experimentElement) throws IOException {
		Element idElement = experimentElement.getChild("id");
		idElement.setText(id);
		
		Element descriptorDatasetElement = experimentElement.getChild("descriptor-dataset");
		descriptorDatasetElement.setAttribute("id", "" + descriptorDatasetId);
		
		Element annotationDatasetElement = experimentElement.getChild("annotation-dataset");
		annotationDatasetElement.setAttribute("id", "" + annotationDatasetId);
		
		Element targetClassElement = experimentElement.getChild("target-class");
		if(targetClass == Constants.CROP)
			targetClassElement.setText("crop");
		else if(targetClass == Constants.FIRST_SEASON)
			targetClassElement.setText("first_season");
		else if(targetClass == Constants.SECOND_SEASON)
			targetClassElement.setText("second_season");
		else if(targetClass == Constants.PAIR)
			targetClassElement.setText("pair");
		
		Element nbClassesElement = experimentElement.getChild("nb-classes");
		nbClassesElement.setText("" + nbClasses);
		
		Element validationTechniqueElement = experimentElement.getChild("validation-technique");
		if(validationTechnique == Constants.RANDOM_SAMPLING)
			validationTechniqueElement.setText("random_sampling");
		else if(validationTechnique == Constants.K_FOLD)
			validationTechniqueElement.setText("k_fold");
		
		Element nbRoundsElement = experimentElement.getChild("nb-rounds");
		nbRoundsElement.setText("" + nbRounds);
		
		Element balancingElement = experimentElement.getChild("balancing");
		if(balancing == Constants.UNBALANCED_TRAINING_TESTING)
			balancingElement.setText("unbalanced_training_testing");
		else if(balancing == Constants.BALANCED_TRAINING_UNBALANCED_TESTING)
			balancingElement.setText("balanced_training_unbalanced_testing");
		else if(balancing == Constants.BALANCED_TRAINING_TESTING)
			balancingElement.setText("balanced_training_testing");
		
		Element experimentTechniqueElement = experimentElement.getChild("experiment-technique");
		if(experimentTechnique == Constants.SVM)
			experimentTechniqueElement.setText("svm");
		else if(experimentTechnique == Constants.OPF)
			experimentTechniqueElement.setText("opf");
		
		Element scalingElement = experimentElement.getChild("scaling");
		
		Element kernelTypeElement = experimentElement.getChild("kernel-type");
		if(kernelType == Constants.RADIAL_BASIS_FUNCTION)
			kernelTypeElement.setText("radial_basis_function");
		else if(kernelType == Constants.LINEAR)
			kernelTypeElement.setText("linear");
		else if(kernelType == Constants.POLYNOMIAL)
			kernelTypeElement.setText("polynomial");
		
		Element runGnuplotElement = experimentElement.getChild("run-gnuplot");
		if(runGnuplot == Constants.DO_NOT_RUN_GNUPLOT)
			runGnuplotElement.setText("do_not_run_gnuplot");
		else if(runGnuplot == Constants.RUN_GNUPLOT)
			runGnuplotElement.setText("run_gnuplot");
		
		XMLOutputter outputter = new XMLOutputter();
		outputter.output(baseDocument, new FileWriter(experimentPath));
	}
	
	public void output(String string) throws IOException {
		GUI.logOutput(text, string);
		
		output.write(string + "\n");
		output.flush();
	}
}
























