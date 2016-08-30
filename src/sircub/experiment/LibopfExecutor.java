/**
 * 
 */
package sircub.experiment;

import sircub.*;
import sircub.datamodel.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * @author jordi
 *
 */
public class LibopfExecutor {

	static void execute(Experiment experiment) throws IOException {
		for(int i = 1; i <= experiment.nbRounds; i++) {
			experiment.output("Round " + i + ":");
			
			executeOnce(experiment.directory + "round" + i + "/", experiment);
			
			experiment.output("Round " + i + " completed!!");
			experiment.output("");
		}
	}
	
	static void executeOnce(String roundPath, Experiment experiment) throws IOException {
		String txt2opfPath = Sircub.libopfDirectory + "tools/txt2opf";
		String opfNormalizePath = Sircub.libopfDirectory + "bin/opf_normalize";
		String opfTrainPath = Sircub.libopfDirectory + "bin/opf_train";
		String opfClassifyPath = Sircub.libopfDirectory + "bin/opf_classify";
		String opfAccuracyPath = Sircub.libopfDirectory + "bin/opf_accuracy";
		
		try {
			experiment.output("txt2opf training...");
			executeCommand(roundPath, experiment, txt2opfPath, roundPath + "training.txt", roundPath + "training.dat");
			
			experiment.output("txt2opf testing...");
			executeCommand(roundPath, experiment, txt2opfPath, roundPath + "testing.txt", roundPath + "testing.dat");
			
			experiment.output("opf_normalize training...");
			executeCommand(roundPath, experiment, opfNormalizePath, roundPath + "training.dat", roundPath + "training_normalized.dat");
			
			experiment.output("opf_normalize testing...");
			executeCommand(roundPath, experiment, opfNormalizePath, roundPath + "testing.dat", roundPath + "testing_normalized.dat");
			
			experiment.output("opf_train...");
			executeCommand(roundPath, experiment, opfTrainPath, roundPath + "training_normalized.dat");
			
			experiment.output("opf_classify...");
			executeCommand(roundPath, experiment, opfClassifyPath, roundPath + "testing_normalized.dat");
			
			experiment.output("opf_accuracy...");
			// ???
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	static void executeCommand(String roundPath, Experiment experiment, String... command) throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.directory(new File(roundPath));
		Process process = processBuilder.start();
		
		int exitValue = process.waitFor();
		experiment.output("Exited with error code: " + exitValue);
	}
}



























































