/**
 * 
 */
package sircub;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import org.jdom2.JDOMException;

import sircub.datamodel.Annotation;
import sircub.datamodel.AnnotationDataset;
import sircub.datamodel.Label;

/**
 * @author jordi
 *
 */
public class AnnotationImporter {

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
		
		String name = "dataset1_annotations_en_2";
		String labelsPath = "/home/jordi/Dropbox/jordi/posdoc/workspace/SiRCub2/input/dataset1/dataset1_annotation_labels_en.csv";
		String annotationsPath = "/home/jordi/Dropbox/jordi/posdoc/workspace/SiRCub2/input/dataset1/dataset1_annotations_en.csv";
		
		AnnotationDataset annotationDataset = new AnnotationDataset(-1, name);
		
		importAnnotations(annotationDataset, labelsPath, annotationsPath);
		
		System.out.println("Bye!!");
	}
	
	public static void importAnnotations(AnnotationDataset annotationDataset, String labelsPath, String annotationsPath) throws IOException {
		annotationDataset.id = DatabaseInterface.insertAnnotationDataset(annotationDataset);
		System.out.println("annotation_dataset_id: " + annotationDataset.id);
		
		Map<String, Label> labelMap = readLabelsAsStringMap(annotationDataset, labelsPath);
		
		importLabels(labelMap);
		
		importAnnotations2(annotationDataset, annotationsPath, labelMap);
	}
	
	public static Map<Integer, Label> readLabelsAsIntegerMap(String labelsPath) throws IOException {
		Map<Integer, Label> labelMap = new HashMap<Integer, Label>();
		
		FileReader labelsFileReader = new FileReader(labelsPath);
		BufferedReader labelsBufferedReader = new BufferedReader(labelsFileReader);
		
		labelsBufferedReader.readLine();
		String labelsLine = labelsBufferedReader.readLine();
		
		while(labelsLine != null) {
			String[] labelsVector = labelsLine.split(",");
			
			int id = Integer.parseInt(labelsVector[0]);
			Label label = new Label(-1, id, labelsVector[1]);
			labelMap.put(id, label);
			
			labelsLine = labelsBufferedReader.readLine();
		}
		
		return labelMap;
	}
	
	private static Map<String, Label> readLabelsAsStringMap(AnnotationDataset annotationDataset, String labelsPath) throws IOException {
		Map<String, Label> labelMap = new HashMap<String, Label>();
		
		FileReader labelsFileReader = new FileReader(labelsPath);
		BufferedReader labelsBufferedReader = new BufferedReader(labelsFileReader);
		
		labelsBufferedReader.readLine();
		String labelsLine = labelsBufferedReader.readLine();
		
		while(labelsLine != null) {
			String[] labelsVector = labelsLine.split(",");
			
			String name = labelsVector[1];
			Label label = new Label(annotationDataset.id, Integer.parseInt(labelsVector[0]), name);
			labelMap.put(name, label);
			
			labelsLine = labelsBufferedReader.readLine();
		}
		
		return labelMap;
	}
	
	private static void importLabels(Map<String, Label> labelMap) {
		for(Map.Entry<String, Label> entry : labelMap.entrySet()) {
			String name = entry.getKey();
			Label label = entry.getValue();
			
			System.out.println("inserting annotation label: " + label.toInsertString());
			DatabaseInterface.insertLabel(label);
		}
	}
	
	private static void importAnnotations2(AnnotationDataset annotationDataset, String annotationsPath, Map<String, Label> labelMap) throws IOException {
		FileReader annotationsFileReader = new FileReader(new File(annotationsPath));
		BufferedReader annotationsBufferedReader = new BufferedReader(annotationsFileReader);
		
		annotationsBufferedReader.readLine();
		String annotationsLine = annotationsBufferedReader.readLine();
		
		int locationId = 0;
		int yearId = 0;
		
		double prevLatitude = -1;
		double prevLongitude = -1;
		//Map<String, Integer> locationMap = new HashMap<String, Integer>();
		Map<String, Integer> yearMap = new HashMap<String, Integer>();
		
		while(annotationsLine != null) {
			String[] annotationsVector = annotationsLine.split(",");
			
			Annotation annotation = new Annotation();
			annotation.annotationDatasetId = annotationDataset.id;
			annotation.latitude = Double.parseDouble(annotationsVector[0]);
			annotation.longitude = Double.parseDouble(annotationsVector[1]);
			annotation.year = annotationsVector[2];
			annotation.firstSeason = annotationsVector[3];
			annotation.secondSeason = annotationsVector[4];
			
			if(annotation.latitude != prevLatitude || annotation.longitude != prevLongitude) {
				locationId++;
			}
			annotation.locationId = locationId;
			prevLatitude = annotation.latitude;
			prevLongitude = annotation.longitude;
			
			if(yearMap.containsKey(annotation.year)) {
				annotation.yearId = yearMap.get(annotation.year);
			} else {
				yearId++;
				annotation.yearId = yearId;
				yearMap.put(annotation.year, yearId);
			}
			
			annotation.firstSeasonId = labelMap.get(annotation.firstSeason).id;
			annotation.secondSeasonId = labelMap.get(annotation.secondSeason).id;
			
			System.out.println("inserting annotation: " + annotation.toInsertString());
			DatabaseInterface.insertAnnotation(annotation);
			
			annotationsLine = annotationsBufferedReader.readLine();
		}
	}

}














































