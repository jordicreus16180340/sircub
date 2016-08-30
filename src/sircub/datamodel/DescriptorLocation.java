/**
 * 
 */
package sircub.datamodel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jordi
 *
 */
public class DescriptorLocation {

	public int descriptorDatasetId;
	public int id;
	public double latitude;
	public double longitude;
	
	public List<Descriptor> descriptors = new ArrayList<Descriptor>();
	
	public DescriptorLocation(int descriptorDatasetId, int id, double latitude, double longitude) {
		this.descriptorDatasetId = descriptorDatasetId;
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public String toInsertString() {
		return descriptorDatasetId + ", " + id + ", " + latitude + ", " + longitude;
	}
}































