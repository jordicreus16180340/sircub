/**
 * 
 */
package sircub.experiment;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.ProcessingInstruction;

import sircub.DatabaseInterface;
import sircub.Sircub;
import sircub.Constants;
import sircub.classification.ClassificationDatasetGenerator;
import sircub.datamodel.DescriptorDataset;
import sircub.datamodel.Instance;
import sircub.datamodel.Round;

/**
 * @author jordi
 *
 */
public class ExperimentDatasetGenerator {

	static Random rand = new Random(System.currentTimeMillis()); // would make this static to the class
	
	static Experiment generate(Experiment experiment) throws IOException, JDOMException {
		experiment.directory = Sircub.experimentsDirectory + experiment.id + "/";
		
		DescriptorDataset descriptorDataset = DatabaseInterface.getDescriptorDataset(experiment.descriptorDatasetId);
		experiment.nbYears = descriptorDataset.nbYears;
		
		// Creating experiment directory
		File directoryFile = new File(experiment.directory);
		if(! directoryFile.exists())
			directoryFile.mkdir();
		
		// Creating log file
		FileWriter experimentFileWriter = new FileWriter(experiment.directory + experiment.id + ".log");
		experiment.output = new BufferedWriter(experimentFileWriter);
		experiment.output("Log file: " + experiment.directory + experiment.id + ".log");
		
		// Creating round directories
		experiment.output("Creating round directories...");
		for(int i = 1; i <= experiment.nbRounds; i++) {
			File roundDirectoryFile = new File(experiment.directory + "round" + i + "/");
			roundDirectoryFile.mkdir();
		}
		
		Map<String, String> m = new HashMap<String, String>();
		m.put("href", System.getProperty("user.dir") + "/xsl/results.xsl");
		m.put("type", "text/xsl");
		experiment.resultsDocument.addContent(0, new ProcessingInstruction("xml-stylesheet", m));
		
		Element sircubElement = new Element("sircub");
		experiment.resultsDocument.addContent(sircubElement);
		sircubElement.addContent(Experiment.getExperimentElement().clone());
		
		/*experiment.output("Retrieving the data from database...");
		HashMap<Integer, Culture> cultureHashMap = DatabaseInterface.getCulturesAsHashMap();
		HashMap<Integer, Date> dateHashMap = DatabaseInterface.getDatesAsHashMap();
		HashMap<Integer, Year> yearHashMap = DatabaseInterface.getYearsAsHashMap(dateHashMap);
		HashMap<Integer, Association> associationHashMap = DatabaseInterface.getAssociationsAsHashMap(cultureHashMap);
		
		HashMap<Integer, List<AnnotatedParameterTuple>> annotatedParameterTupleHashMap = DatabaseInterface.getAnnotatedParameterTuplesIndexedByPoint(yearHashMap, associationHashMap);
		
		// Creating Dataset
		experiment.output("Creating the dataset...");
		
		for(Map.Entry<Integer, List<AnnotatedParameterTuple>> entry : annotatedParameterTupleHashMap.entrySet()) {
			int pointId = entry.getKey();
			List<AnnotatedParameterTuple> annotatedParameterTuples = entry.getValue();
			
			if(annotatedParameterTuples.get(0).numberSeasons == 3) {
				//experiment.monoSeasonPoints.put(pointId, annotatedParameterTuples);
			} else {
				//List<AnnotatedParameterTuple> missingSeasonAnnotatedParameterTuples = new ArrayList<AnnotatedParameterTuple>();
				for(AnnotatedParameterTuple annotatedParameterTuple : annotatedParameterTuples) {
					if(annotatedParameterTuple.beg == 0) {
						//missingSeasonAnnotatedParameterTuples.add(annotatedParameterTuple);
					} else {
						if(experiment.targetClass == Experiment.FIRST_SEASON) { // SAFRA
							if(annotatedParameterTuple.seas == 1 || annotatedParameterTuple.seas == 3 || annotatedParameterTuple.seas == 5 || annotatedParameterTuple.seas == 7) {
								int safraId = annotatedParameterTuple.association.getSafra().getId(); 
								List<AnnotatedParameterTuple> safraAnnotatedParameterTuples;
								if(experiment.dataSet.containsKey(safraId)) {
									safraAnnotatedParameterTuples = experiment.dataSet.get(safraId);
								} else {
									safraAnnotatedParameterTuples = new ArrayList<AnnotatedParameterTuple>();
									experiment.dataSet.put(safraId, safraAnnotatedParameterTuples);
								}
								safraAnnotatedParameterTuples.add(annotatedParameterTuple);
							}
						} else if(experiment.targetClass == Experiment.SECOND_SEASON) { // SAFRINHA
							if(annotatedParameterTuple.seas == 2 || annotatedParameterTuple.seas == 4 || annotatedParameterTuple.seas == 6) {
								int safrinhaId = annotatedParameterTuple.association.getSafrinha().getId(); 
								List<AnnotatedParameterTuple> safrinhaAnnotatedParameterTuples;
								if(experiment.dataSet.containsKey(safrinhaId)) {
									safrinhaAnnotatedParameterTuples = experiment.dataSet.get(safrinhaId);
								} else {
									safrinhaAnnotatedParameterTuples = new ArrayList<AnnotatedParameterTuple>();
									experiment.dataSet.put(safrinhaId, safrinhaAnnotatedParameterTuples);
								}
								safrinhaAnnotatedParameterTuples.add(annotatedParameterTuple);
							}
						}
					}
				}
				//if(! missingSeasonAnnotatedParameterTuples.isEmpty()) {
					//experiment.missingSeasonPoints.put(pointId, missingSeasonAnnotatedParameterTuples);
				//}
			}
		}
		
		experiment.output("----- Data set -----");
		outputExperimentLog(experiment.dataSet, experiment, cultureHashMap);
		
		// removing unnecessary classes
		experiment.output("Discarding unnecessary classes...");
		if(experiment.targetClass == Experiment.FIRST_SEASON) { // SAFRA
			experiment.dataSet.remove(20);
			experiment.dataSet.remove(15);
			experiment.dataSet.remove(10);
			experiment.dataSet.remove(4);
			experiment.dataSet.remove(3);
		} else if(experiment.targetClass == Experiment.SECOND_SEASON) { // SAFRINHA
			experiment.dataSet.remove(20);
			if(experiment.nbClasses == 2) experiment.dataSet.remove(11);
			experiment.dataSet.remove(15);
			experiment.dataSet.remove(17);
			experiment.dataSet.remove(7);
			experiment.dataSet.remove(9);
			experiment.dataSet.remove(10);
			experiment.dataSet.remove(2);
			experiment.dataSet.remove(16);
			experiment.dataSet.remove(13);
		}*/
		
		System.out.println("Creating instance dataset...");
		experiment.instanceMap = ClassificationDatasetGenerator.createInstanceDatasetForExperiment(experiment.descriptorDatasetId, experiment.annotationDatasetId, experiment.targetClass, experiment.nbYears);
		System.out.println("Done!");
		
		System.out.println("Discarding none agricultural crop classes (e.g., no data, pasture, forest)...");
		ClassificationDatasetGenerator.discardNoneCropClasses(experiment.instanceMap, experiment.targetClass);
		
		System.out.println("Sorting instance dataset by number of instances per class...");
		experiment.sortedClasses = ClassificationDatasetGenerator.sortInstanceDataset(experiment.instanceMap);
		System.out.println(experiment.sortedClasses);
		
		System.out.println("Discarding classes under the top-k...");
		ClassificationDatasetGenerator.discardClassesUnderTopK(experiment.instanceMap, experiment.sortedClasses, experiment.nbClasses);
		
		/*int nbInstancesPerClass = 0;
		if(experiment.targetClass == Experiment.FIRST_SEASON)
			nbInstancesPerClass = 178;
		else if(experiment.targetClass == Experiment.SECOND_SEASON)
			if(experiment.nbClasses == 2)
				nbInstancesPerClass = 2702;
			else if(experiment.nbClasses == 3)
				nbInstancesPerClass = 444;
		experiment.output("# instances per class: " + nbInstancesPerClass);*/
		
		int smallerClassId = experiment.sortedClasses.get(experiment.nbClasses - 1);
		int nbInstancesPerClass = experiment.instanceMap.get(smallerClassId).size();
		experiment.output("# instances per class: " + nbInstancesPerClass);
		
		experiment.output("Generating training and testing pairs...");
		
		if(experiment.validationTechnique == Constants.RANDOM_SAMPLING) {
			
			experiment.output("Round validation technique: Repeated random sub-sampling validation");
			// first season (top-2 classes) = 89
			// second season (top-2 classes) = 1351
			// second season (top-3 classes) = 222
			int nbInstancesPerClassPerXSet = nbInstancesPerClass/2;
			
			for(int i = 1; i <= experiment.nbRounds; i++) {
				Round round = new Round(i);
				experiment.rounds.add(round);
				
				Map<Integer, List<Instance>> instanceMapCopy = copyInstanceMap(experiment.instanceMap);
				
				// Creating Training set
				
				round.trainingSet = createXSet(instanceMapCopy, nbInstancesPerClassPerXSet);
				
				// Creating Testing set
				
				//round.testingSet = experiment.dataSet; // contains the rest
				round.testingSet = createXSet(instanceMapCopy, nbInstancesPerClassPerXSet);
				
				/*System.out.println("Mono-season point number: " + experiment.monoSeasonPoints.size());
				
				for(Map.Entry<Integer, List<AnnotatedParameterTuple>> missingSeasonPoint : experiment.missingSeasonPoints.entrySet()) {
					int pointId = missingSeasonPoint.getKey();
					List<AnnotatedParameterTuple> missingSeasonAnnotatedParameterTuples = missingSeasonPoint.getValue();
					
					if(missingSeasonAnnotatedParameterTuples.size() >= 4) {
						System.out.println("epa! " + pointId);
					}
				}*/
			}
			
		} else if(experiment.validationTechnique == Constants.K_FOLD) {
			
			experiment.output("Round validation technique: k-fold cross-validation");
			
			int nbInstancesPerClassPerFold = nbInstancesPerClass/experiment.nbRounds;
			
			List<Map<Integer, List<Instance>>> kFold = createKFold(experiment.instanceMap, nbInstancesPerClassPerFold, experiment.nbRounds);
			
			experiment.rounds = createCrossValidationRounds(kFold, experiment.nbRounds);
		}
		
		int nbInstancesPerClassPerTrainingSet = 0;
		int nbInstancesPerClassPerTestingSet = 0;
		if(experiment.validationTechnique == Constants.RANDOM_SAMPLING) {
			nbInstancesPerClassPerTrainingSet = nbInstancesPerClass/2;
			nbInstancesPerClassPerTestingSet  = nbInstancesPerClass/2;
		} else if(experiment.validationTechnique == Constants.K_FOLD) {
			nbInstancesPerClassPerTrainingSet = (nbInstancesPerClass/experiment.nbRounds) * (experiment.nbRounds - 1);
			nbInstancesPerClassPerTestingSet  = nbInstancesPerClass/experiment.nbRounds;
		}
		
		for(int i = 1; i <= experiment.nbRounds; i++) {
			Round round = experiment.rounds.get(i - 1);
			
			experiment.output("----- Round " + i + " -----");
			experiment.output("----- Training set -----");
			// TODO outputExperimentLog(round.trainingSet, experiment);
			experiment.output("----- Testing set -----");
			// TODO outputExperimentLog(round.testingSet, experiment);
			
			// Creating files
			
			if(experiment.experimentTechnique == Constants.SVM) {
				outputXFileSVM(round.trainingSet, experiment.directory + "round" + i + "/" + "training.txt", experiment.begEnd);
				
				outputXFileSVM(round.testingSet, experiment.directory + "round" + i + "/" + "testing.txt", experiment.begEnd);
			} else if(experiment.experimentTechnique == Constants.OPF) {
				outputXFileOPF(round.trainingSet, experiment.directory + "round" + i + "/" + "training.txt", experiment.begEnd, experiment.nbClasses, nbInstancesPerClassPerTrainingSet);
				
				outputXFileOPF(round.testingSet, experiment.directory + "round" + i + "/" + "testing.txt", experiment.begEnd, experiment.nbClasses, nbInstancesPerClassPerTestingSet);
			}
		}
		
		return experiment;
	}
	
	static Map<Integer, List<Instance>> copyInstanceMap(Map<Integer, List<Instance>> instanceMap) {
		Map<Integer, List<Instance>> instanceMapCopy = new HashMap<Integer, List<Instance>>();
		
		for(Map.Entry<Integer, List<Instance>> entry : instanceMap.entrySet()) {
			int classId = entry.getKey();
			List<Instance> instances = entry.getValue();
			List<Instance> instancesCopy = new ArrayList<Instance>(instances);
			
			instanceMapCopy.put(classId, instancesCopy);
		}
		
		return instanceMapCopy;
	}
	
	static Map<Integer, List<Instance>> createXSet(Map<Integer, List<Instance>> instanceMap, int nbInstancesPerClassPerXSet) {
		Map<Integer, List<Instance>> xSet = new HashMap<Integer, List<Instance>>();
		
		for(Map.Entry<Integer, List<Instance>> entry : instanceMap.entrySet()) {
			int classId = entry.getKey();
			List<Instance> curInstances = entry.getValue();
			List<Instance> curInstancesCopy = new ArrayList<Instance>(curInstances);
			
			List<Instance> xSetInstances = new ArrayList<Instance>(nbInstancesPerClassPerXSet);
			for(int i = 0; i < nbInstancesPerClassPerXSet; i++) {
				// be sure to use Vector.remove() or you may get the same item twice
				xSetInstances.add(curInstancesCopy.remove(rand.nextInt(curInstancesCopy.size())));
			}
			
			xSet.put(classId, xSetInstances);
		}
		
		return xSet;
	}
	
	static List<Map<Integer, List<Instance>>> createKFold(Map<Integer, List<Instance>> instanceMap, int nbInstancesPerClassPerFold, int nbRounds) {
		List<Map<Integer, List<Instance>>> kFold = new ArrayList<Map<Integer, List<Instance>>>(nbRounds);
		
		for(int i = 0; i < nbRounds; i++) {
			Map<Integer, List<Instance>> fold = new HashMap<Integer, List<Instance>>();
			
			for(Map.Entry<Integer, List<Instance>> entry : instanceMap.entrySet()) {
				int classId = entry.getKey();
				List<Instance> curInstances = entry.getValue();
				
				List<Instance> foldInstances = new ArrayList<Instance>();
				for(int j = 0; j < nbInstancesPerClassPerFold; j++) {
					foldInstances.add(curInstances.remove(rand.nextInt(curInstances.size())));
				}
				
				fold.put(classId, foldInstances);
			}
			
			kFold.add(fold);
		}
		
		return kFold;
	}
	
	static List<Round> createCrossValidationRounds(List<Map<Integer, List<Instance>>> kFold, int nbRounds) {
		List<Round> rounds = new ArrayList<Round>(nbRounds);
		
		for(int i = 1; i <= nbRounds; i++) {
			Round round = new Round(i);
			rounds.add(round);
			
			for(int j = 1; j <= nbRounds; j++) {
				Map<Integer, List<Instance>> fold = kFold.get(j - 1);
				
				for(Map.Entry<Integer, List<Instance>> entry : fold.entrySet()) {
					int classId = entry.getKey();
					List<Instance> foldInstances = entry.getValue();
					
					if(i == j) {
						if(round.testingSet.containsKey(classId))
							round.testingSet.get(classId).addAll(foldInstances);
						else {
							List<Instance> foldInstancesCopy = new ArrayList<Instance>(foldInstances);
							round.testingSet.put(classId, foldInstancesCopy);
						}
					} else {
						if(round.trainingSet.containsKey(classId))
							round.trainingSet.get(classId).addAll(foldInstances);
						else {
							List<Instance> foldInstancesCopy = new ArrayList<Instance>(foldInstances);
							round.trainingSet.put(classId, foldInstancesCopy);
						}
					}
				}
			}
		}
		
		return rounds;
	}
	
	static void outputXFileSVM(Map<Integer, List<Instance>> xSet, String filePath, int begEnd) throws IOException {
		FileWriter fileWriter = new FileWriter(filePath);
		BufferedWriter output = new BufferedWriter(fileWriter);
		
		for(Map.Entry<Integer, List<Instance>> entry : xSet.entrySet()) {
			int classId = entry.getKey();
			List<Instance> instances = entry.getValue();
			
			for(Instance instance : instances) {
			//for(int i = 0; i < 100; i++) {
				//Instance instance = instances.get(i);
				
				output.write(classId + " ");
				
				if(begEnd == Constants.WITHOUT_BEG_END) { // WITHOUT beg & end
					output.write("1:" + instance.descriptor.length + " ");
					output.write("2:" + instance.descriptor.base + " ");
					output.write("3:" + instance.descriptor.midX + " ");
					output.write("4:" + instance.descriptor.max + " ");
					output.write("5:" + instance.descriptor.amp + " ");
					output.write("6:" + instance.descriptor.lDer + " ");
					output.write("7:" + instance.descriptor.rDer + " ");
					output.write("8:" + instance.descriptor.lInteg + " ");
					output.write("9:" + instance.descriptor.sInteg + "\n");
				} else if(begEnd == Constants.WITH_BEG_END) { // WITH beg & end
					output.write("1:" + instance.descriptor.begRelative + " ");
					output.write("2:" + instance.descriptor.endRelative + " ");
					output.write("3:" + instance.descriptor.length + " ");
					output.write("4:" + instance.descriptor.base + " ");
					output.write("5:" + instance.descriptor.midX + " ");
					output.write("6:" + instance.descriptor.max + " ");
					output.write("7:" + instance.descriptor.amp + " ");
					output.write("8:" + instance.descriptor.lDer + " ");
					output.write("9:" + instance.descriptor.rDer + " ");
					output.write("10:" + instance.descriptor.lInteg + " ");
					output.write("11:" + instance.descriptor.sInteg + "\n");
				}
			}
		}
		
		output.close();
	}
	
	static void outputXFileOPF(Map<Integer, List<Instance>> xSet, String filePath, int begEnd, int nbClasses, int nbInstancesPerClassPerXSet) throws IOException {
		FileWriter fileWriter = new FileWriter(filePath);
		BufferedWriter output = new BufferedWriter(fileWriter);
		
		if(begEnd == Constants.WITHOUT_BEG_END)
			output.write(nbClasses * nbInstancesPerClassPerXSet + " " + nbClasses + " 9 \n");
		else if(begEnd == Constants.WITH_BEG_END)
			output.write(nbClasses * nbInstancesPerClassPerXSet + " " + nbClasses + " 11 \n");
		
		int i = 0;
		for(Map.Entry<Integer, List<Instance>> entry : xSet.entrySet()) {
			int classId = entry.getKey();
			List<Instance> instances = entry.getValue();
			
			for(Instance instance : instances) {
				output.write(i + " " + classId + " ");
				
				if(begEnd == Constants.WITHOUT_BEG_END) {
					output.write(instance.descriptor.length + " ");
					output.write(instance.descriptor.base + " ");
					output.write(instance.descriptor.midX + " ");
					output.write(instance.descriptor.max + " ");
					output.write(instance.descriptor.amp + " ");
					output.write(instance.descriptor.lDer + " ");
					output.write(instance.descriptor.rDer + " ");
					output.write(instance.descriptor.lInteg + " ");
					output.write(instance.descriptor.sInteg + " \n");
				} else if(begEnd == Constants.WITH_BEG_END) {
					output.write(instance.descriptor.begRelative + " ");
					output.write(instance.descriptor.endRelative + " ");
					output.write(instance.descriptor.length + " ");
					output.write(instance.descriptor.base + " ");
					output.write(instance.descriptor.midX + " ");
					output.write(instance.descriptor.max + " ");
					output.write(instance.descriptor.amp + " ");
					output.write(instance.descriptor.lDer + " ");
					output.write(instance.descriptor.rDer + " ");
					output.write(instance.descriptor.lInteg + " ");
					output.write(instance.descriptor.sInteg + " \n");
				}
				
				i++;
			}
		}
		
		output.close();
	}
	
	static void outputExperimentLog(Map<Integer, List<Instance>> instanceMap, Experiment experiment) throws IOException {
		for(Map.Entry<Integer, List<Instance>> entry : instanceMap.entrySet()) {
			int classId = entry.getKey();
			List<Instance> instances = entry.getValue();
			
			//experiment.output("culture_id = " + cultureId + "\tculture = " + cultureHashMap.get(cultureId).getCulture() + "\tsize = " + annotatedParameterTuples.size());
			experiment.output("class_id = " + classId + "\tsize = " + instances.size());
		}
	}

}















































