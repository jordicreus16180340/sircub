/**
 * 
 */
package sircub.datamodel;

/**
 * @author jordi
 *
 */
public class Label {

	public int annotationDatasetId;
	public int id;
	public String name;
	
	public Label(int annotationDatasetId, int id, String name) {
		this.annotationDatasetId = annotationDatasetId;
		this.id = id;
		this.name = name;
	}
	
	public String toInsertString() {
		return annotationDatasetId + ", " + id + ", '" + name + "'";
	}
}


















































