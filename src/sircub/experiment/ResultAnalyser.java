/**
 * 
 */
package sircub.experiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

import sircub.Constants;
import sircub.datamodel.RoundClass;
import sircub.datamodel.Round;
import sircub.datamodel.ExperimentClass;

/**
 * @author jordi
 *
 */
public class ResultAnalyser {

	static DecimalFormat df = new DecimalFormat("##0.00");
	
	static void perClassAccuracy(Experiment experiment) throws IOException {
		for(int i = 1; i <= experiment.nbRounds; i++) {
			Round round = experiment.rounds.get(i - 1);
			perClassAccuracyOnce(experiment.directory + "round" + i + "/", round, experiment);
		}
		
		finalAverages(experiment);
	}
	
	static void perClassAccuracyOnce(String roundPath, Round round, Experiment experiment) throws IOException {
		String testingPath = null;
		String predictPath = null;
		
		if(experiment.experimentTechnique == Constants.SVM) {
			testingPath = roundPath + "testing.txt.scale";
			predictPath = roundPath + "testing.txt.predict";
		} else if(experiment.experimentTechnique == Constants.OPF) {
			testingPath = roundPath + "testing.txt";
			predictPath = roundPath + "testing_normalized.dat.out";
		}
		
		FileReader testingFileReader = new FileReader(new File(testingPath));
		BufferedReader testingInput = new BufferedReader(testingFileReader);
		FileReader predictFileReader = new FileReader(new File(predictPath));
		BufferedReader predictInput = new BufferedReader(predictFileReader);
		
		String testingLine = testingInput.readLine();
		if(experiment.experimentTechnique == Constants.OPF)
			testingLine = testingInput.readLine();
		String predictLine = predictInput.readLine();
		int totalInstances = 0;
		int totalHits = 0;
		RoundClass roundClass = null;
		int prevTestingClassId = 0;
		while(testingLine != null) {
			String[] testingInstance = testingLine.split("\\s+");
			
			int curTestingClassId = 0;
			if(experiment.experimentTechnique == Constants.SVM)
				curTestingClassId = Integer.parseInt(testingInstance[0]);
			else if(experiment.experimentTechnique == Constants.OPF)
				curTestingClassId = Integer.parseInt(testingInstance[1]);
			
			int curPredictClassId = Integer.parseInt(predictLine);
			
			if(curTestingClassId != prevTestingClassId) {
				roundClass = new RoundClass(curTestingClassId);
				round.roundClasses.add(roundClass);
			}
			totalInstances++;
			roundClass.incrementInstances();
			if(curTestingClassId == curPredictClassId) {
				totalHits++;
				roundClass.incrementHits();
			}
			
			testingLine = testingInput.readLine();
			predictLine = predictInput.readLine();
			prevTestingClassId = curTestingClassId;
		}
		
		testingInput.close();
		predictInput.close();
		
		experiment.output(roundPath);
		experiment.output("Experiment results:");
		
		double sampleMean = (double) totalHits / totalInstances;
		round.meanAccuracy = sampleMean;
		String sampleMeanPercentage = df.format(100 * sampleMean);
		round.meanAccuracyPercentage = sampleMeanPercentage;
		experiment.output("total instances = " + totalInstances);
		experiment.output("total hits = " + totalHits);
		experiment.output("sample mean accuracy = " + sampleMean + ", sample mean accuracy percentage = " + sampleMeanPercentage);
		for(RoundClass roundClass2 : round.roundClasses) {
			int classId = roundClass2.getClassId();
			int instances = roundClass2.getInstances();
			int hits = roundClass2.getHits();
			double accuracy = (double) hits / instances;
			roundClass2.setAccuracy(accuracy);
			String percentage = df.format(100 * accuracy);
			roundClass2.setAccuracyPercentage(percentage);
			experiment.output("classId = " + classId + ", instances = " + instances + ", hits = " + hits + ", accuracy = " + accuracy + ", percentage = " + percentage);
		}
		
		experiment.output("");
	}
	
	static void finalAverages(Experiment experiment) throws IOException {
		for(RoundClass roundClass : experiment.rounds.get(0).roundClasses) {
			ExperimentClass experimentClass = new ExperimentClass(roundClass.getClassId());
			experiment.experimentClasses.add(experimentClass);
		}
		
		double meanMeanAccuracy = 0;
		for(int i = 0; i < experiment.nbClasses; i++) {
			double classMean = 0;
			
			for(Round round : experiment.rounds) {
				double accuracy = round.roundClasses.get(i).getAccuracy();
				classMean += accuracy;
			}
			
			classMean = classMean/experiment.nbRounds;
			experiment.experimentClasses.get(i).setMeanAccuracy(classMean);
		}
		for(Round round : experiment.rounds) {
			double sampleMean = round.meanAccuracy;
			meanMeanAccuracy += sampleMean;
		}
		experiment.meanMeanAccuracy = meanMeanAccuracy/experiment.nbRounds;
		
		for(int i = 0; i < experiment.nbClasses; i++) {
			double squaredDifferenceSum = 0;
			
			double classMean = experiment.experimentClasses.get(i).getMeanAccuracy();
			for(Round round : experiment.rounds) {
				double accuracy = round.roundClasses.get(i).getAccuracy();
				double difference = accuracy - classMean;
				double squaredDifference = Math.pow(difference, 2);
				squaredDifferenceSum += squaredDifference;
			}
			
			double classStandardDeviation = Math.sqrt(squaredDifferenceSum/experiment.nbRounds);
			experiment.experimentClasses.get(i).setStandardDeviation(classStandardDeviation);
		}
		double squaredDifferenceSum = 0;
		for(Round round : experiment.rounds) {
			double sampleMean = round.meanAccuracy;
			double difference = sampleMean - experiment.meanMeanAccuracy;
			double squaredDifference = Math.pow(difference, 2);
			squaredDifferenceSum += squaredDifference;
		}
		experiment.meanStandardDeviation = Math.sqrt(squaredDifferenceSum/experiment.nbRounds);
		
		experiment.output("----------");
		for(int i = 0; i < experiment.nbClasses; i++) {
			String classMeanPercentage = df.format(100 * experiment.experimentClasses.get(i).getMeanAccuracy());
			String classStandardDeviationPercentage = df.format(100 * experiment.experimentClasses.get(i).getStandardDeviation());
			experiment.experimentClasses.get(i).setMeanAccuracyPercentage(classMeanPercentage);
			experiment.experimentClasses.get(i).setStandardDeviationPercentage(classStandardDeviationPercentage);
			experiment.output("class id = " + experiment.rounds.get(0).roundClasses.get(i).getClassId());
			experiment.output("class mean = " + experiment.experimentClasses.get(i).getMeanAccuracy() + " percentage of class mean = " + classMeanPercentage);
			experiment.output("class standard deviation = " + experiment.experimentClasses.get(i).getStandardDeviation() + " percentage of class standard deviation = " + classStandardDeviationPercentage);
		}
		
		experiment.output("----------");
		String meanMeanPercentage = df.format(100 * experiment.meanMeanAccuracy);
		String meanStandardDeviationPercentage = df.format(100 * experiment.meanStandardDeviation);
		experiment.meanMeanAccuracyPercentage = meanMeanPercentage;
		experiment.meanStandardDeviationPercentage = meanStandardDeviationPercentage;
		experiment.output("mean mean = " + experiment.meanMeanAccuracy + " mean mean percentage = " + meanMeanPercentage);
		experiment.output("mean standard deviation = " + experiment.meanStandardDeviation + " mean standard deviation percentage = " + meanStandardDeviationPercentage);
	}

}































