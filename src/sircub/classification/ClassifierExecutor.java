/**
 * 
 */
package sircub.classification;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.*;

import sircub.Sircub;
import sircub.Constants;

/**
 * @author jordi
 *
 */
public class ClassifierExecutor {

	public static void execute(Classification classification) {
		if(classification.experimentTechnique == Constants.SVM) {
			executeSVM(classification);
		} else if(classification.experimentTechnique == Constants.OPF) {
			executeOPF(classification);
		}
	}
	
	private static void executeSVM(Classification classification) {
		String svmScalePath = Sircub.libsvmDirectory + "svm-scale";
		String svmPredictPath = Sircub.libsvmDirectory + "svm-predict";
		
		try {
			classification.output("Scaling dataset...");
			executeCommandRedirectOutput(classification, classification.directoryPath + "dataset.txt.scale", svmScalePath, "-r", classification.roundPath + "training.txt.range", classification.directoryPath + "dataset.txt");
			
			classification.output("Predicting dataset...");
			executeCommand(classification, svmPredictPath, classification.directoryPath + "dataset.txt.scale", classification.roundPath + "training.txt.model", classification.directoryPath + "dataset.txt.predict");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void executeOPF(Classification classification) {
		String txt2opfPath = Sircub.libopfDirectory + "tools/txt2opf";
		String opfNormalizePath = Sircub.libopfDirectory + "bin/opf_normalize";
		String opfClassifyPath = Sircub.libopfDirectory + "bin/opf_classify";
		
		try {
			classification.output("txt2opf dataset...");
			executeCommand(classification, txt2opfPath, classification.directoryPath + "dataset.txt", classification.directoryPath + "dataset.dat");
			
			classification.output("opf_normalize dataset...");
			executeCommand(classification, opfNormalizePath, classification.directoryPath + "dataset.dat", classification.directoryPath + "dataset_normalized.dat");
			
			classification.output("Copying \"classifier.opf\"...");
			copyClassifier(classification);
			
			classification.output("opf_classify...");
			executeCommand(classification, opfClassifyPath, classification.directoryPath + "dataset_normalized.dat");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void executeCommand(Classification classification, String... command) throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.directory(new File(classification.directoryPath));
		classification.output("Command: " + processBuilder.command());
		Process process = processBuilder.start();
		
		classification.output("Output:");
		BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		String line = null;
		while((line = input.readLine()) != null)
			classification.output("\t" + line);
		
		int exitValue = process.waitFor();
		classification.output("Exited with error code: " + exitValue);
	}
	
	private static void executeCommandRedirectOutput(Classification classification, String outputPath, String... command) throws IOException, InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.directory(new File(classification.directoryPath));
		classification.output("Command: " + processBuilder.command());
		Process process = processBuilder.start();
		
		classification.output("Redirecting standard output...");
		classification.output("Output path: " + outputPath);
		BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
		FileWriter fileWriter = new FileWriter(outputPath);
		BufferedWriter output = new BufferedWriter(fileWriter);
		
		String line = null;
		while((line = input.readLine()) != null)
			output.write(line + "\n");
		
		int exitValue = process.waitFor();
		classification.output("Exited with error code: " + exitValue);
		
		output.close();
	}
	
	private static void copyClassifier(Classification classification) throws IOException {
		File source = new File(classification.roundPath + "classifier.opf");
		File target = new File(classification.directoryPath + "classifier.opf");
		
		Files.copy(source.toPath(), target.toPath(), REPLACE_EXISTING);
	}
}
























































