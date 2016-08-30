/**
 * 
 */
package sircub.datamodel;

/**
 * @author jordi
 *
 */
public class ExperimentClass {

	int classId;
	double meanAccuracy;
	String meanAccuracyPercentage;
	double standardDeviation;
	String standardDeviationPercentage;
	
	public ExperimentClass(int classId) {
		this.classId = classId;
	}
	
	public int getClassId() {
		return classId;
	}
	
	public void setMeanAccuracy(double meanAccuracy) {
		this.meanAccuracy = meanAccuracy;
	}
	
	public double getMeanAccuracy() {
		return meanAccuracy;
	}
	
	public void setMeanAccuracyPercentage(String meanAccuracyPercentage) {
		this.meanAccuracyPercentage = meanAccuracyPercentage;
	}
	
	public String getMeanAccuracyPercentage() {
		return meanAccuracyPercentage;
	}
	
	public void setStandardDeviation(double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}
	
	public double getStandardDeviation() {
		return standardDeviation;
	}
	
	public void setStandardDeviationPercentage(String standardDeviationPercentage) {
		this.standardDeviationPercentage = standardDeviationPercentage;
	}
	
	public String getStandardDeviationPercentage() {
		return standardDeviationPercentage;
	}
}

































