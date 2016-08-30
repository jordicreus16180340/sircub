/**
 * 
 */
package sircub.gui;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.*;
import org.jdom2.JDOMException;
import org.jdom2.Element;

import sircub.AnnotationImporter;
import sircub.Configuration;
import sircub.DescriptorImporter;
import sircub.Sircub;
import sircub.classification.Classification;
import sircub.classification.ClassificationRunner;
import sircub.datamodel.AnnotationDataset;
import sircub.datamodel.DescriptorDataset;
import sircub.experiment.Experiment;
import sircub.experiment.ExperimentRunner;

/**
 * @author jordi
 *
 */
public class GUI {

	private static Display display;
	private static Shell shell;
	private static CTabFolder mainFolder;
	
	public static void start() {
		display = new Display();
		
		shell = new Shell(display);
		shell.setText("SiRCub 1.3");
		shell.setSize(800, 600); // 640, 480 | 800, 600
		
		FillLayout shellLayout = new FillLayout();
		shellLayout.marginHeight = 4;
		shellLayout.marginWidth = 4;
		shell.setLayout(shellLayout);
		
		mainFolder = new CTabFolder(shell, SWT.BORDER);
		mainFolder.setSimple(false);
		mainFolder.setTabHeight(30);
		
		mainMenuTab();
		
		/*experimentTab(new Experiment());
		experimentTab(new Experiment());
		experimentTab(new Experiment());*/
		
		// shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	private static void mainMenuTab() {
		final CTabItem mainMenuItem = new CTabItem(mainFolder, SWT.NONE);
		mainMenuItem.setText("Main Menu");
		Composite mainMenuOutterComposite = new Composite(mainFolder, SWT.NONE);
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginTop = 10;
		mainMenuOutterComposite.setLayout(gridLayout);
		
		Composite mainMenuInnerComposite = mainMenuInnerComposite(mainMenuOutterComposite);
		
		GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.CENTER;
		mainMenuInnerComposite.setLayoutData(data);
		
		mainMenuItem.setControl(mainMenuOutterComposite);
	}
	
	private static Composite mainMenuInnerComposite(Composite mainMenuOutterComposite) {
		Composite mainMenuInnerComposite = new Composite(mainMenuOutterComposite, SWT.NONE);
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 10;
		mainMenuInnerComposite.setLayout(gridLayout);
		
		Group featureExtractionGroup = featureExtractionGroup(mainMenuInnerComposite);
		Group modelLearningGroup = modelLearningGroup(mainMenuInnerComposite);
		Group cropClassificationGroup = cropClassificationGroup(mainMenuInnerComposite);
		Composite configurationComposite = configurationComposite(mainMenuInnerComposite);
		
		GridData data = new GridData();
		data.widthHint = 500;
		featureExtractionGroup.setLayoutData(data);
		data = new GridData();
		data.widthHint = 500;
		modelLearningGroup.setLayoutData(data);
		data = new GridData();
		data.widthHint = 500;
		cropClassificationGroup.setLayoutData(data);
		data = new GridData();
		data.widthHint = 500;
		configurationComposite.setLayoutData(data);
		
		return mainMenuInnerComposite;
	}
	
	private static Group featureExtractionGroup(Composite mainMenuComposite) {
		Group featureExtractionGroup = new Group(mainMenuComposite, SWT.NONE);
		featureExtractionGroup.setText("Feature extraction");
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.marginHeight = 10;
		gridLayout.verticalSpacing = 10;
		featureExtractionGroup.setLayout(gridLayout);
		
		/*Label label1 = new Label(featureExtractionGroup, SWT.WRAP);
		label1.setText("Iteratively test different Timesat configurations to find out the maximum number of crop descriptors for a given NDVI time series set\n(uses Timesat)");
		Button button1 = new Button(featureExtractionGroup, SWT.NONE);
		button1.setText("Extract Features");*/
		
		Label label2 = new Label(featureExtractionGroup, SWT.WRAP);
		label2.setText("Import an existing file of Timesat seasonality parameters (i.e., crop descriptors) into SiRCub");
		Button button2 = new Button(featureExtractionGroup, SWT.NONE);
		button2.setText("Import Descriptors");
		
		Label label3 = new Label(featureExtractionGroup, SWT.WRAP);
		label3.setText("Import a crop annotation file into SiRCub");
		Button button3 = new Button(featureExtractionGroup, SWT.NONE);
		button3.setText("Import Annotations");
		
		button2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					importDescriptorsDialog();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		button3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					importAnnotationsDialog();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		/*GridData data = new GridData(GridData.FILL_HORIZONTAL);
		label1.setLayoutData(data);*/
		
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		label2.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		label3.setLayoutData(data);
		
		/*data = new GridData();
		data.widthHint = 125;
		button1.setLayoutData(data);*/
		
		data = new GridData();
		data.widthHint = 125;
		button2.setLayoutData(data);
		
		data = new GridData();
		data.widthHint = 125;
		button3.setLayoutData(data);
		
		return featureExtractionGroup;
	}
	
	private static void importDescriptorsDialog() throws IOException {
		ImportDescriptorsDialog importDescriptorsDialog = new ImportDescriptorsDialog(shell);
		importDescriptorsDialog.setText("Import Descriptors");
		ImportDescriptorsDialogData data = importDescriptorsDialog.open();
		
		if(data.buttonResponse) {
			importDescriptors(data);
		}
	}
	
	private static void importDescriptors(ImportDescriptorsDialogData data) throws IOException {
		ImportingDialog importingDialog = new ImportingDialog(shell);
		importingDialog.setText("Import Descriptors");
		importingDialog.open();
		
		DescriptorDataset descriptorDataset = new DescriptorDataset(-1, data.name, data.nbYears, data.nbValues);
		
		DescriptorImporter.importDescriptors(descriptorDataset, data.locationsPath, data.descriptorsPath);
		
		importingDialog.close();
		
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
		messageBox.setMessage("Data imported successfully!");
		messageBox.setText("Import Descriptors");
		messageBox.open();
	}
	
	private static void importAnnotationsDialog() throws IOException {
		ImportAnnotationsDialog importAnnotationsDialog = new ImportAnnotationsDialog(shell);
		importAnnotationsDialog.setText("Import Annotations");
		ImportAnnotationsDialogData data = importAnnotationsDialog.open();
		
		if(data.buttonResponse) {
			importAnnotations(data);
		}
	}
	
	private static void importAnnotations(ImportAnnotationsDialogData data) throws IOException {
		ImportingDialog importingDialog = new ImportingDialog(shell);
		importingDialog.setText("Import Annotations");
		importingDialog.open();
		
		AnnotationDataset annotationDataset = new AnnotationDataset(-1, data.name);
		
		AnnotationImporter.importAnnotations(annotationDataset, data.labelsPath, data.annotationsPath);
		
		importingDialog.close();
		
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
		messageBox.setMessage("Data imported successfully!");
		messageBox.setText("Import Annotations");
		messageBox.open();
	}
	
	private static class ImportingDialog extends Dialog {
		public ImportingDialog(Shell parent) {
			super(parent);
			// TODO Auto-generated constructor stub
		}

		private Label label;
		
		Shell shell;
		
		public void open() {
			shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			shell.setText(getText());
			shell.setSize(300, 150);
			
			label = new Label(shell, SWT.NONE);
			label.setLocation(25, 10);
			label.setSize(150, 20);
			label.setText("Importing data...");
			
			shell.open();
			/* Display display = getParent().getDisplay();
			while(! shell.isDisposed()) {
				if(! display.readAndDispatch())
					display.sleep();
			} */
		}
		
		public void close() {
			shell.close();
		}
	}
	
	private static Group modelLearningGroup(Composite mainMenuComposite) {
		Group modelLearningGroup = new Group(mainMenuComposite, SWT.NONE);
		modelLearningGroup.setText("Crop model learning");
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.marginHeight = 10;
		modelLearningGroup.setLayout(gridLayout);
		
		Label label3 = new Label(modelLearningGroup, SWT.WRAP);
		label3.setText("Set up a new experiment, generate training and testing sets, and run the experiment procedures");
		Button button3 = new Button(modelLearningGroup, SWT.NONE);
		button3.setText("New Experiment");
		
		button3.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					newExperimentDialog();
				} catch (JDOMException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		label3.setLayoutData(data);
		
		data = new GridData();
		data.widthHint = 125;
		button3.setLayoutData(data);
		
		return modelLearningGroup;
	}
	
	private static void newExperimentDialog() throws JDOMException, IOException {
		Experiment experiment = new Experiment();
		
		NewExperimentDialog newExperimentDialog = new NewExperimentDialog(shell);
		newExperimentDialog.setText("New Experiment");
		NewExperimentDialogData data = newExperimentDialog.open(experiment);
		
		if(data.buttonResponse) {
			experiment.id = data.id;
			experiment.descriptorDatasetId = data.descriptorDatasetId;
			experiment.annotationDatasetId = data.annotationDatasetId;
			experiment.targetClass = data.targetClass;
			experiment.nbClasses = data.nbClasses;
			experiment.validationTechnique = data.validationTechnique;
			experiment.nbRounds = data.nbRounds;
			experiment.balancing = data.balancing;
			experiment.experimentTechnique = data.experimentTechnique;
			experiment.kernelType = data.kernelType;
			experiment.runGnuplot = data.runGnuplot;
			
			Element experimentElement = Experiment.getExperimentElement();
			experiment.save(experimentElement);
			
			runNewExperiment(experiment);
		}
	}
	
	private static void runNewExperiment(Experiment experiment) throws IOException, JDOMException {
		experimentTab(experiment);
		
		//ExperimentRunner.run(experiment);
		Thread thread = new Thread(new ExperimentRunner(experiment));
		thread.start();
	}
	
	private static Group cropClassificationGroup(Composite mainMenuComposite) {
		Group cropClassificationGroup = new Group(mainMenuComposite, SWT.NONE);
		cropClassificationGroup.setText("Crop classification");
		
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.marginHeight = 10;
		cropClassificationGroup.setLayout(gridLayout);
		
		Label label4 = new Label(cropClassificationGroup, SWT.WRAP);
		label4.setText("Classify a set of crop descriptors using an existing crop model");
		Button button4 = new Button(cropClassificationGroup, SWT.NONE);
		button4.setText("New Classification");
		
		button4.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					newClassificationDialog();
				} catch (JDOMException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		label4.setLayoutData(data);
		
		data = new GridData();
		data.widthHint = 125;
		button4.setLayoutData(data);
		
		return cropClassificationGroup;
	}
	
	private static void newClassificationDialog() throws JDOMException, IOException {
		Classification classification = new Classification();
		
		NewClassificationDialog newClassificationDialog = new NewClassificationDialog(shell);
		newClassificationDialog.setText("New Classification");
		NewClassificationDialogData data = newClassificationDialog.open(classification);
		
		if(data.buttonResponse) {
			classification.id = data.id;
			classification.descriptorDatasetId = data.descriptorDatasetId;
			classification.labelsPath = data.labelsPath;
			classification.roundPath = data.roundPath;
			classification.targetClass = data.targetClass;
			classification.experimentTechnique = data.experimentTechnique;
			
			Element classificationElement = Classification.getClassificationElement();
			classification.save(classificationElement);
			
			runNewClassification(classification);
		}
	}
	
	private static void runNewClassification(Classification classification) {
		classificationTab(classification);
		
		//ClassificationRunner.run(classification);
		Thread thread = new Thread(new ClassificationRunner(classification));
		thread.start();
	}
	
	private static Composite configurationComposite(Composite mainMenuComposite) {
		Composite configurationComposite = new Composite(mainMenuComposite, SWT.NONE);
		
		GridLayout gridLayout = new GridLayout();
		configurationComposite.setLayout(gridLayout);
		
		Button configurationButton = new Button(configurationComposite, SWT.NONE);
		configurationButton.setText("Configuration");
		
		configurationButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				try {
					configurationDialog();
				} catch (JDOMException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		GridData data = new GridData();
		data.widthHint = 125;
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.RIGHT;
		configurationButton.setLayoutData(data);
		
		return configurationComposite;
	}
	
	private static void configurationDialog() throws JDOMException, IOException {
		ConfigurationDialog configurationDialog = new ConfigurationDialog(shell);
		configurationDialog.setText("Configuration");
		ConfigurationDialogData data = configurationDialog.open();
		
		if(data.buttonResponse) {
			Sircub.hostname = data.hostname;
			Sircub.database = data.database;
			Sircub.user = data.user;
			Sircub.password = data.password;
			Sircub.timesatDirectory = data.timesatDirectory;
			Sircub.libsvmDirectory = data.libsvmDirectory;
			Sircub.gnuplotExecutable = data.gnuplotExecutable;
			Sircub.libopfDirectory = data.libopfDirectory;
			Sircub.experimentsDirectory = data.experimentsDirectory;
			Sircub.classificationsDirectory = data.classificationsDirectory;
			
			Configuration.save();
		}
	}
	
	private static void experimentTab(Experiment experiment) {
		CTabItem experimentItem = new CTabItem(mainFolder, SWT.CLOSE);
		experimentItem.setText(experiment.id);
		Composite experimentComposite = new Composite(mainFolder, SWT.NONE);
		
		FillLayout experimentCompositeLayout = new FillLayout();
		experimentCompositeLayout.marginHeight = 4;
		experimentCompositeLayout.marginWidth = 4;
		experimentComposite.setLayout(experimentCompositeLayout);
		
		CTabFolder experimentFolder = new CTabFolder(experimentComposite, SWT.BORDER | SWT.BOTTOM);
		experimentFolder.setSimple(false);
		experimentFolder.setTabHeight(30);
		experiment.experimentFolder = experimentFolder;
		
		logTab(experimentFolder, experiment);
		//resultsTab(experimentFolder);
		
		experimentItem.setControl(experimentComposite);
		
		mainFolder.setSelection(experimentItem);
	}
	
	private static void logTab(CTabFolder experimentFolder, Experiment experiment) {
		final CTabItem logItem = new CTabItem(experimentFolder, SWT.NONE);
		logItem.setText("Log");
		Composite logComposite = new Composite(experimentFolder, SWT.NONE);
		
		FillLayout logCompositeLayout = new FillLayout();
		logCompositeLayout.marginHeight = 4;
		logCompositeLayout.marginWidth = 4;
		logComposite.setLayout(logCompositeLayout);
		
		Text text = new Text(logComposite, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		//text.setText("Log:\n\n");
		experiment.text = text;
		/*StyledText styledText = new StyledText(logComposite, SWT.V_SCROLL | SWT.H_SCROLL);
		styledText.setText("log");
		styledText.setAlwaysShowScrollBars(false);*/
		
		logItem.setControl(logComposite);
	}
	
	private static void resultsTab(CTabFolder experimentFolder, Experiment experiment) {
		final CTabItem resultsItem = new CTabItem(experimentFolder, SWT.NONE);
		resultsItem.setText("Results");
		Composite resultsComposite = new Composite(experimentFolder, SWT.NONE);
		
		FillLayout resultsCompositeLayout = new FillLayout();
		resultsCompositeLayout.marginHeight = 4;
		resultsCompositeLayout.marginWidth = 4;
		resultsComposite.setLayout(resultsCompositeLayout);
		
		Browser browser = null;
		try {
			browser = new Browser(resultsComposite, SWT.NONE);
		} catch (SWTError e) {
			/* The Browser widget throws an SWTError if it fails to
			 * instantiate properly. Application code should catch
			 * this SWTError and disable any feature requiring the
			 * Browser widget.
			 * Platform requirements for the SWT Browser widget are available
			 * from the SWT FAQ website. 
			 */
			 e.printStackTrace();
		}
		if (browser != null) {
			/* The Browser widget can be used */
			//browser.setUrl("file:///home/jordi/Dropbox/jordi/posdoc/experiments/experiment_3001/experiment_3001.xml");
			browser.setUrl("file://" + experiment.directory + experiment.id + ".xml");
		}
		
		resultsItem.setControl(resultsComposite);
		
		experimentFolder.setSelection(resultsItem);
	}
	
	public static void logOutput(final Text text, final String string) {
		display.asyncExec(new Runnable() {
			public void run() {
				text.append(string + "\n");
			}
		});
	}
	
	public static void openResultsTab(final Experiment experiment) {
		display.asyncExec(new Runnable() {
			public void run() {
				resultsTab(experiment.experimentFolder, experiment);
			}
		});
	}
	
	private static void classificationTab(Classification classification) {
		CTabItem classificationItem = new CTabItem(mainFolder, SWT.CLOSE);
		classificationItem.setText(classification.id);
		Composite classificationComposite = new Composite(mainFolder, SWT.NONE);
		
		FillLayout classificationCompositeLayout = new FillLayout();
		classificationCompositeLayout.marginHeight = 4;
		classificationCompositeLayout.marginWidth = 4;
		classificationComposite.setLayout(classificationCompositeLayout);
		
		CTabFolder classificationFolder = new CTabFolder(classificationComposite, SWT.BORDER | SWT.BOTTOM);
		classificationFolder.setSimple(false);
		classificationFolder.setTabHeight(30);
		
		classificationLogTab(classificationFolder, classification);
		
		classificationItem.setControl(classificationComposite);
		
		mainFolder.setSelection(classificationItem);
	}
	
	private static void classificationLogTab(CTabFolder classificationFolder, Classification classification) {
		final CTabItem classificationLogItem = new CTabItem(classificationFolder, SWT.NONE);
		classificationLogItem.setText("Log");
		Composite classificationLogComposite = new Composite(classificationFolder, SWT.NONE);
		
		FillLayout classificationLogCompositeLayout = new FillLayout();
		classificationLogCompositeLayout.marginHeight = 4;
		classificationLogCompositeLayout.marginWidth = 4;
		classificationLogComposite.setLayout(classificationLogCompositeLayout);
		
		Text text = new Text(classificationLogComposite, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		//text.setText("Log:\n\n");
		classification.text = text;
		
		classificationLogItem.setControl(classificationLogComposite);
	}
	
	public static void classificationLogOutput(final Text text, final String string) {
		display.asyncExec(new Runnable() {
			public void run() {
				text.append(string + "\n");
			}
		});
	}
}





























