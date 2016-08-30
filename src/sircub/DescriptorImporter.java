/**
 * 
 */
package sircub;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;

import sircub.datamodel.Descriptor;
import sircub.datamodel.DescriptorDataset;
import sircub.datamodel.DescriptorLocation;

/**
 * @author jordi
 *
 */
public class DescriptorImporter {

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
		
		String name = "dataset1_descriptors_settings1_2";
		String locationsPath = "/home/jordi/Dropbox/jordi/posdoc/workspace/SiRCub2/input/dataset1/dataset1_descriptor_locations.csv";
		String descriptorsPath = "/home/jordi/Dropbox/jordi/posdoc/workspace/SiRCub2/input/dataset1/dataset1_descriptors_settings1.txt";
		int nbYears = 4;
		int nbValues = 46;
		
		DescriptorDataset descriptorDataset = new DescriptorDataset(-1, name, nbYears, nbValues);
		
		importDescriptors(descriptorDataset, locationsPath, descriptorsPath);
		
		System.out.println("Bye!!");
	}
	
	public static void importDescriptors(DescriptorDataset descriptorDataset, String locationsPath, String descriptorsPath) throws IOException {
		descriptorDataset.id = DatabaseInterface.insertDescriptorDataset(descriptorDataset);
		System.out.println("descriptor_dataset_id: " + descriptorDataset.id);
		
		FileReader locationsFileReader = new FileReader(new File(locationsPath));
		BufferedReader locationsBufferedReader = new BufferedReader(locationsFileReader);
		FileReader descriptorsFileReader = new FileReader(new File(descriptorsPath));
		BufferedReader descriptorsBufferedReader = new BufferedReader(descriptorsFileReader);
		
		locationsBufferedReader.readLine();
		String locationsLine = locationsBufferedReader.readLine();
		descriptorsBufferedReader.readLine();
		String descriptorsLine = descriptorsBufferedReader.readLine();
		
		int prevLocationId = 0;
		List<DescriptorLocation> descriptorLocations = new ArrayList<DescriptorLocation>();
		DescriptorLocation descriptorLocation = null;
		
		while(descriptorsLine != null) {
			String[] descriptorsVector = descriptorsLine.split("\\s+");
			
			Descriptor descriptor = new Descriptor();
			descriptor.descriptorDatasetId = descriptorDataset.id;
			descriptor.descriptorLocationId = Integer.parseInt(descriptorsVector[1]);
			descriptor.row = Integer.parseInt(descriptorsVector[1]);
			descriptor.col = Integer.parseInt(descriptorsVector[2]);
			descriptor.seas = Integer.parseInt(descriptorsVector[3]);
			descriptor.beg = Float.parseFloat(descriptorsVector[4]);
			descriptor.end = Float.parseFloat(descriptorsVector[5]);
			descriptor.length = Float.parseFloat(descriptorsVector[6]);
			descriptor.base = Float.parseFloat(descriptorsVector[7]);
			descriptor.midX = Float.parseFloat(descriptorsVector[8]);
			descriptor.max = Float.parseFloat(descriptorsVector[9]);
			descriptor.amp = Float.parseFloat(descriptorsVector[10]);
			descriptor.lDer = Float.parseFloat(descriptorsVector[11]);
			descriptor.rDer = Float.parseFloat(descriptorsVector[12]);
			descriptor.lInteg = Float.parseFloat(descriptorsVector[13]);
			descriptor.sInteg = Float.parseFloat(descriptorsVector[14]);
			
			if(descriptor.descriptorLocationId != prevLocationId) {
				String[] locationsVector = locationsLine.split(",");
				
				descriptorLocation = new DescriptorLocation(descriptorDataset.id, descriptor.descriptorLocationId, Double.parseDouble(locationsVector[0]), Double.parseDouble(locationsVector[1]));
				descriptorLocations.add(descriptorLocation);
				
				locationsLine = locationsBufferedReader.readLine();
			}
			
			descriptorLocation.descriptors.add(descriptor);
			
			descriptorsLine = descriptorsBufferedReader.readLine();
			
			prevLocationId = descriptor.descriptorLocationId;
		}
		
		computeRelativeParams(descriptorLocations, descriptorDataset);
		
	}
	
	private static void computeRelativeParams(List<DescriptorLocation> descriptorLocations, DescriptorDataset descriptorDataset) {
		for(DescriptorLocation descriptorLocation : descriptorLocations) {
			System.out.println("inserting descriptor location: " + descriptorLocation.toInsertString());
			DatabaseInterface.insertDescriptorLocation(descriptorLocation);
			
			int nbSeasons = descriptorLocation.descriptors.size();
			
			for(Descriptor descriptor : descriptorLocation.descriptors) {
				if(nbSeasons == descriptorDataset.nbYears - 1) { // uni-season
					// TODO
				} else if(nbSeasons == descriptorDataset.nbYears * 2 - 1) { // bi-season
					int diff = descriptorDataset.nbValues * ((descriptor.seas - 1) / 2);
					descriptor.begRelative = descriptor.beg - diff;
					descriptor.endRelative = descriptor.end - diff;
				}
				
				System.out.println("\tinserting descriptor: " + descriptor.toInsertString());
				DatabaseInterface.insertDescriptor(descriptor);
			}
		}
	}

}

























































