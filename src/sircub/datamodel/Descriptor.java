/**
 * 
 */
package sircub.datamodel;

/**
 * @author jordi
 *
 */
public class Descriptor {

	public int descriptorDatasetId;
	public int descriptorLocationId;
	public int row;
	public int col;
	public int seas;
	public float beg;
	public float end;
	public float begRelative;
	public float endRelative;
	public float length;
	public float base;
	public float midX;
	public float max;
	public float amp;
	public float lDer;
	public float rDer;
	public float lInteg;
	public float sInteg;
	
	public String toInsertString() {
		return descriptorDatasetId + ", " + descriptorLocationId + ", " +
				row + ", " + col + ", " + seas + ", " +
				beg + ", " + end + ", " + begRelative + ", " + endRelative + ", " +
				length + ", " + base + ", " + midX + ", " + max + ", " + amp + ", " + lDer + ", " + rDer + ", " + lInteg + ", " + sInteg;
	}
}


























