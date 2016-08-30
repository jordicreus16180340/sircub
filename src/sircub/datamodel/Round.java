/**
 * 
 */
package sircub.datamodel;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author jordi
 *
 */
public class Round {

	public int id;
	//public HashMap<Integer, List<AnnotatedParameterTuple>> trainingSet = new HashMap<Integer, List<AnnotatedParameterTuple>>();
	//public HashMap<Integer, List<AnnotatedParameterTuple>> testingSet = new HashMap<Integer, List<AnnotatedParameterTuple>>();
	public Map<Integer, List<Instance>> trainingSet = new HashMap<Integer, List<Instance>>();
	public Map<Integer, List<Instance>> testingSet = new HashMap<Integer, List<Instance>>();
	public List<RoundClass> roundClasses = new ArrayList<RoundClass>();
	public double meanAccuracy;
	public String meanAccuracyPercentage;
	
	public Round(int id) {
		this.id = id;
	}
}






























