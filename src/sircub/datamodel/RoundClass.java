/**
 * 
 */
package sircub.datamodel;

/**
 * @author jordi
 *
 */
public class RoundClass {

	int classId;
	int instances;
	int hits;
	double accuracy;
	String accuracyPercentage;
	
	public RoundClass(int classId) {
		this.classId = classId;
	}
	
	public void incrementInstances() {
		instances++;
	}
	
	public void incrementHits() {
		hits++;
	}
	
	public int getClassId() {
		return classId;
	}
	
	public int getInstances() {
		return instances;
	}
	
	public int getHits() {
		return hits;
	}
	
	public double getAccuracy() {
		return accuracy;
	}
	
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	
	public String getAccuracyPercentage() {
		return accuracyPercentage;
	}
	
	public void setAccuracyPercentage(String accuracyPercentage) {
		this.accuracyPercentage = accuracyPercentage;
	}
}




































