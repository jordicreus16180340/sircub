/**
 * 
 */
package sircub.experiment;

import sircub.*;
import sircub.datamodel.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author jordi
 *
 */
public class LibsvmExecutor {

	static void execute(Experiment experiment) throws IOException {
		for(int i = 1; i <= experiment.nbRounds; i++) {
			executeOnce(experiment.directory + "round" + i + "/", experiment);
			
			experiment.output("Round " + i + " completed!!");
		}
	}
	
	static void executeOnce(String roundPath, Experiment experiment) {
		String easySircubPath = System.getProperty("user.dir") + "/py/libsvm/easy_sircub.py";
		
		try {
			/*Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec("python " + easyPath + " " + experimentPath + "training.txt" + " " + experimentPath + "testing.txt");*/
			// ProcessBuilder processBuilder = new ProcessBuilder("python", easyPath, samplePath + "training.txt", samplePath + "testing.txt");
			ProcessBuilder processBuilder = new ProcessBuilder("python", easySircubPath,
					roundPath + "training.txt", roundPath + "testing.txt",
					Sircub.libsvmDirectory, Sircub.gnuplotExecutable,
					"" + experiment.kernelType, "" + experiment.runGnuplot);
			processBuilder.directory(new File(roundPath));
			Process process = processBuilder.start();
			
			BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
			/*FileWriter fileWriter = new FileWriter(samplePath + "easy_output.txt");
			BufferedWriter output = new BufferedWriter(fileWriter);*/
			
			experiment.output(roundPath);
			experiment.output("easy_sircub's output:");
			String line = null;
			while((line = input.readLine()) != null)
				experiment.output("\t" + line);
			
			int exitValue = process.waitFor();
			experiment.output("Exited with error code: " + exitValue);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}












































