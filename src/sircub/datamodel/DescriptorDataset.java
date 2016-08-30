/**
 * 
 */
package sircub.datamodel;

/**
 * @author jordi
 *
 */
public class DescriptorDataset {

	public int id;
	public String name;
	public int nbYears;
	public int nbValues;
	
	public DescriptorDataset(int id, String name, int nbYears, int nbValues) {
		this.id = id;
		this.name = name;
		this.nbYears = nbYears;
		this.nbValues = nbValues;
	}
	
	public String toInsertString() {
		return "'" + name + "', " + nbYears + ", " + nbValues;
	}
}






















































