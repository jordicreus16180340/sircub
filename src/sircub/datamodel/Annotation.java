/**
 * 
 */
package sircub.datamodel;

/**
 * @author jordi
 *
 */
public class Annotation {

	public int annotationDatasetId;
	public int locationId;
	public double latitude;
	public double longitude;
	public int yearId;
	public String year;
	public int firstSeasonId;
	public int secondSeasonId;
	public String firstSeason;
	public String secondSeason;
	
	public String toInsertString() {
		return annotationDatasetId + ", " + locationId + ", " + latitude + ", " + longitude + ", " +
				yearId + ", '" + year + "', " + firstSeasonId + ", " + secondSeasonId + ", '" + firstSeason + "', '" + secondSeason + "'";
	}
}































