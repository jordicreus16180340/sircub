/**
 * 
 */
package sircub.datamodel;

/**
 * @author jordi
 *
 */
public class AnnotationDataset {

	public int id;
	public String name;
	
	public AnnotationDataset(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String toInsertString() {
		return "'" + name + "'";
	}
}

























































