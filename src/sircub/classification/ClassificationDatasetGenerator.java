/**
 * 
 */
package sircub.classification;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import sircub.DatabaseInterface;
import sircub.Constants;
import sircub.datamodel.Annotation;
import sircub.datamodel.Descriptor;
import sircub.datamodel.DescriptorLocation;
import sircub.datamodel.Instance;

/**
 * @author jordi
 *
 */
public class ClassificationDatasetGenerator {

	public static void generate(Classification classification) throws IOException {
		/*System.out.println("Creating instance dataset...");
		classification.instanceMap = createInstanceDatasetForExperiment(classification.descriptorDatasetId, classification.annotationDatasetId, classification.targetClass, classification.nbYears);
		System.out.println("Done!");
		
		System.out.println("Discarding none agricultural crop classes (e.g., no data, pasture, forest)...");
		discardNoneCropClasses(classification.instanceMap, classification.targetClass);
		
		System.out.println("Sorting instance dataset by number of instances per class...");
		classification.sortedClasses = sortInstanceDataset(classification.instanceMap);
		System.out.println(classification.sortedClasses);
		
		System.out.println("Discarding classes under the top-k...");
		discardClassesUnderTopK(classification.instanceMap, classification.sortedClasses, classification.nbClasses);*/
		
		classification.output("Creating instance dataset...");
		classification.instanceMap = createInstanceDatasetForClassification(classification.descriptorDatasetId, classification.targetClass, classification.nbYears);
		classification.output("Done!");
		
		String datasetPath = classification.directoryPath + "dataset.txt";
		classification.output("Writing dataset into a file...");
		writeDatasetFile(classification.instanceMap, datasetPath, classification.experimentTechnique);
	}
	
	public static Map<Integer, List<Instance>> createInstanceDatasetForClassification(int descriptorDatasetId, int targetClass, int nbYears) {
		Map<Integer, List<Instance>> instanceMap = new HashMap<Integer, List<Instance>>();
		List<Instance> instances = new ArrayList<Instance>();
		instanceMap.put(-1, instances);
		
		Map<String, DescriptorLocation> descriptorLocationMap = DatabaseInterface.getDescriptorLocationsAsMap(descriptorDatasetId);
		
		for(Map.Entry<String, DescriptorLocation> entry : descriptorLocationMap.entrySet()) {
			String locationString = entry.getKey();
			DescriptorLocation descriptorLocation = entry.getValue();
			
			int nbSeasons = descriptorLocation.descriptors.size();
			
			if(nbSeasons == nbYears - 1) { // uni-season
				// NOTHING
			} else if(nbSeasons == nbYears * 2 - 1) { // bi-season
				for(Descriptor descriptor : descriptorLocation.descriptors) {
					if(targetClass == Constants.FIRST_SEASON && (descriptor.seas % 2 == 1)) { // odd, eg. 1, 3, 5, 7
						if(descriptor.beg == 0) { // zero-valued vector
							// NOTHING
						} else {
							Instance instance = new Instance(null, descriptorLocation, descriptor, null);
							instances.add(instance);
						}
					} else if(targetClass == Constants.SECOND_SEASON && (descriptor.seas % 2 == 0)) { // even, eg. 2, 4, 6
						if(descriptor.beg == 0) { // zero-valued vector
							// NOTHING
						} else {
							Instance instance = new Instance(null, descriptorLocation, descriptor, null);
							instances.add(instance);
						}
					}
				}
			}
		}
		
		return instanceMap;
	}
	
	public static Map<Integer, List<Instance>> createInstanceDatasetForExperiment(int descriptorDatasetId, int annotationDatasetId, int targetClass, int nbYears) {
		Map<Integer, List<Instance>> instanceMap = new HashMap<Integer, List<Instance>>();
		
		Map<String, DescriptorLocation> descriptorLocationMap = DatabaseInterface.getDescriptorLocationsAsMap(descriptorDatasetId);
		Map<String, List<Annotation>> annotationMap = DatabaseInterface.getAnnotationsAsMap(annotationDatasetId);
		
		for(Map.Entry<String, DescriptorLocation> entry : descriptorLocationMap.entrySet()) {
			String locationString = entry.getKey();
			DescriptorLocation descriptorLocation = entry.getValue();
			
			int nbSeasons = descriptorLocation.descriptors.size();
			
			if(nbSeasons == nbYears - 1) { // uni-season
				// NOTHING
			} else if(nbSeasons == nbYears * 2 - 1) { // bi-season
				List<Annotation> annotations = annotationMap.get(locationString);
				Iterator<Annotation> annotationIterator = annotations.iterator();
				Annotation annotation = annotationIterator.next();
				
				for(Descriptor descriptor : descriptorLocation.descriptors) {
					if(targetClass == Constants.FIRST_SEASON && (descriptor.seas % 2 == 1)) { // odd, eg. 1, 3, 5, 7
						if(descriptor.beg == 0) { // zero-valued vector
							// NOTHING
						} else {
							Instance instance = new Instance(annotation, descriptorLocation, descriptor, null);
							List<Instance> instances = null;
							if(instanceMap.containsKey(annotation.firstSeasonId)) {
								instances = instanceMap.get(annotation.firstSeasonId);
							} else {
								instances = new ArrayList<Instance>();
								instanceMap.put(annotation.firstSeasonId, instances);
							}
							instances.add(instance);
						}
					} else if(targetClass == Constants.SECOND_SEASON && (descriptor.seas % 2 == 0)) { // even, eg. 2, 4, 6
						if(descriptor.beg == 0) { // zero-valued vector
							// NOTHING
						} else {
							Instance instance = new Instance(annotation, descriptorLocation, descriptor, null);
							List<Instance> instances = null;
							if(instanceMap.containsKey(annotation.secondSeasonId)) {
								instances = instanceMap.get(annotation.secondSeasonId);
							} else {
								instances = new ArrayList<Instance>();
								instanceMap.put(annotation.secondSeasonId, instances);
							}
							instances.add(instance);
						}
						
						annotation = annotationIterator.next();
					}
				}
			}
		}
		
		return instanceMap;
	}
	
	public static void discardNoneCropClasses(Map<Integer, List<Instance>> instanceMap, int targetClass) {
		List<Integer> classesToRemove = new ArrayList<Integer>();
		
		for(Map.Entry<Integer, List<Instance>> entry : instanceMap.entrySet()) {
			int classId = entry.getKey();
			List<Instance> instances = entry.getValue();
			
			String crop = null;
			if(targetClass == Constants.FIRST_SEASON)
				crop = instances.get(0).annotation.firstSeason;
			else if(targetClass == Constants.SECOND_SEASON)
				crop = instances.get(0).annotation.secondSeason;
			
			if(crop.equals("no_data") || crop.equals("pasture") || crop.equals("forest") || crop.equals("fallow") ||
					crop.equals("x") || crop.equals("pasto") || crop.equals("mata") || crop.equals("posio") || crop.equals("pousio")) {
				classesToRemove.add(classId);
			}
		}
		
		for(int i : classesToRemove)
			instanceMap.remove(i);
	}
	
	// selection sort: http://www.java2novice.com/java-sorting-algorithms/selection-sort/
	public static List<Integer> sortInstanceDataset(Map<Integer, List<Instance>> instanceMap) {
		List<Integer> classes = new ArrayList<Integer>(instanceMap.keySet());
		
		for(int i = 0; i < classes.size() - 1; i++) {
			int index = i;
			for(int j = i + 1; j < classes.size(); j++) {
				if(instanceMap.get(classes.get(j)).size() < instanceMap.get(classes.get(index)).size()) {
					index = j;
				}
			}
			
			int smallerClass = classes.get(index);
			classes.set(index, classes.get(i));
			classes.set(i, smallerClass);
		}
		
		Collections.reverse(classes);
		
		return classes;
	}
	
	public static void discardClassesUnderTopK(Map<Integer, List<Instance>> instanceMap, List<Integer> sortedClasses, int nbClasses) {
		for(int i = nbClasses; i < sortedClasses.size(); i++) {
			instanceMap.remove(sortedClasses.get(i));
		}
	}
	
	private static void writeDatasetFile(Map<Integer, List<Instance>> instanceMap, String datasetPath, int experimentTechnique) throws IOException {
		if(experimentTechnique == Constants.SVM) {
			writeDatasetFileSVM(instanceMap, datasetPath);
		} else if(experimentTechnique == Constants.OPF) {
			writeDatasetFileOPF(instanceMap, datasetPath);
		}
	}
	
	private static void writeDatasetFileSVM(Map<Integer, List<Instance>> instanceMap, String datasetPath) throws IOException {
		FileWriter fileWriter = new FileWriter(datasetPath);
		BufferedWriter output = new BufferedWriter(fileWriter);
		
		for(Map.Entry<Integer, List<Instance>> entry : instanceMap.entrySet()) {
			int classId = entry.getKey();
			List<Instance> instances = entry.getValue();
			
			for(Instance instance : instances) {
				output.write(classId + " ");
				
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
		
		output.close();
	}
	
	private static void writeDatasetFileOPF(Map<Integer, List<Instance>> instanceMap, String datasetPath) throws IOException {
		FileWriter fileWriter = new FileWriter(datasetPath);
		BufferedWriter output = new BufferedWriter(fileWriter);
		
		output.write(instanceMap.get(-1).size() + " 1 11 \n");
		
		int i = 0;
		for(Map.Entry<Integer, List<Instance>> entry : instanceMap.entrySet()) {
			int classId = entry.getKey();
			List<Instance> instances = entry.getValue();
			
			for(Instance instance : instances) {
				output.write(i + " -1 ");
				
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
				
				i++;
			}
		}
		
		output.close();
	}
}
























































