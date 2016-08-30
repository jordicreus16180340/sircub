/**
 * 
 */
package sircub.gui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;

import sircub.Constants;
import sircub.DatabaseInterface;
import sircub.datamodel.AnnotationDataset;
import sircub.datamodel.DescriptorDataset;
import sircub.experiment.Experiment;

/**
 * @author jordi
 *
 */
public class NewExperimentDialog extends Dialog {

	public NewExperimentDialog(Shell parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	private Label label1;
	private Label label2a;
	private Label label2b;
	private Label label3;
	private Label label4;
	private Label label5;
	private Label label6;
	private Label label7;
	private Text text1;
	private Combo combo2a;
	private Combo combo2b;
	private Button button3a;
	private Button button3b;
	private Button button3c;
	private Button button3d;
	private Text text4;
	private Button button5a;
	private Button button5b;
	private Text text6;
	//private Button button7a;
	//private Button button7b;
	private Button button7c;
	private Button button8a;
	private Button button8c;
	private Button button8b2a;
	private Button button8b2b;
	private Button button8b2c;
	private Button button8b3;
	
	private Button btnOkay;
	private Button btnCancel;
	private NewExperimentDialogData newExperimentDialogData;
	
	public NewExperimentDialogData open(Experiment experiment) {
		List<DescriptorDataset> descriptorDatasets = DatabaseInterface.getDescriptorDatasets();
		List<AnnotationDataset> annotationDatasets = DatabaseInterface.getAnnotationDatasets();
		
		newExperimentDialogData = new NewExperimentDialogData();
		final Shell shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setText(getText());
		// shell.setSize(800, 600);
		shell.setMinimumSize(500, 0);
		
		GridLayout shellLayout = new GridLayout();
		shellLayout.numColumns = 2;
		shellLayout.marginHeight = 10;
		shellLayout.marginWidth = 10;
		// shellLayout.verticalSpacing = 10;
		shell.setLayout(shellLayout);
		
		label1 = new Label(shell, SWT.NONE);
		label1.setText("Experiment id:");
		
		text1 = new Text(shell, SWT.BORDER);
		text1.setText(experiment.id);
		
		Group group2 = new Group(shell, SWT.NONE);
		group2.setText("Data sources");
		
		GridLayout layout2 = new GridLayout();
		layout2.numColumns = 2;
		group2.setLayout(layout2);
		
		label2a = new Label(group2, SWT.NONE);
		label2a.setText("Crop descriptors:");
		
		combo2a = new Combo(group2, SWT.DROP_DOWN | SWT.READ_ONLY);
		//combo2a.setItems(new String[] {"embrapa_descriptor_dataset_1"});
		for(int i = 0; i < descriptorDatasets.size(); i++) {
			DescriptorDataset descriptorDataset = descriptorDatasets.get(i);
			combo2a.add(descriptorDataset.name);
			if(descriptorDataset.id == experiment.descriptorDatasetId)
				combo2a.select(i);
		}
		combo2a.setData(descriptorDatasets);
		
		label2b = new Label(group2, SWT.NONE);
		label2b.setText("Time series annotations:");
		
		combo2b = new Combo(group2, SWT.DROP_DOWN | SWT.READ_ONLY);
		//combo2b.setItems(new String[] {"embrapa_annotation_dataset_1"});
		for(int i = 0; i < annotationDatasets.size(); i++) {
			AnnotationDataset annotationDataset = annotationDatasets.get(i);
			combo2b.add(annotationDataset.name);
			if(annotationDataset.id == experiment.annotationDatasetId)
				combo2b.select(i);
		}
		combo2b.setData(annotationDatasets);
		
		GridData data = new GridData();
		label2a.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		combo2a.setLayoutData(data);
		
		data = new GridData();
		label2b.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		combo2b.setLayoutData(data);
		
		label3 = new Label(shell, SWT.NONE);
		label3.setText("Target class:");
		
		Composite composite3 = new Composite(shell, SWT.NONE);
		RowLayout layout3 = new RowLayout();
		composite3.setLayout(layout3);
		
		button3a = new Button(composite3, SWT.RADIO);
		button3a.setText("Crop");
		button3a.setEnabled(false);
		
		button3b = new Button(composite3, SWT.RADIO);
		button3b.setText("First season");
		
		button3c = new Button(composite3, SWT.RADIO);
		button3c.setText("Second season");
		
		button3d = new Button(composite3, SWT.RADIO);
		button3d.setText("Pair");
		button3d.setEnabled(false);
		
		if(experiment.targetClass == Constants.CROP)
			button3a.setSelection(true);
		else if(experiment.targetClass == Constants.FIRST_SEASON)
			button3b.setSelection(true);
		else if(experiment.targetClass == Constants.SECOND_SEASON)
			button3c.setSelection(true);
		else if(experiment.targetClass == Constants.PAIR)
			button3d.setSelection(true);
		
		label4 = new Label(shell, SWT.NONE);
		label4.setText("Number of classes:"); // Top-k classes (k value):
		
		text4 = new Text(shell, SWT.BORDER);
		text4.setText("" + experiment.nbClasses);
		
		label5 = new Label(shell, SWT.NONE);
		label5.setText("Model validation technique:");
		
		Composite composite5 = new Composite(shell, SWT.NONE);
		RowLayout layout5 = new RowLayout(SWT.VERTICAL);
		layout5.marginLeft = 30;
		composite5.setLayout(layout5);
		
		button5a = new Button(composite5, SWT.RADIO);
		button5a.setText("Repeated random sub-sampling validation");
		
		button5b = new Button(composite5, SWT.RADIO);
		button5b.setText("k-fold cross-validation");
		
		if(experiment.validationTechnique == Constants.RANDOM_SAMPLING)
			button5a.setSelection(true);
		else if(experiment.validationTechnique == Constants.K_FOLD)
			button5b.setSelection(true);
		
		label6 = new Label(shell, SWT.NONE);
		label6.setText("Number of rounds:");
		
		text6 = new Text(shell, SWT.BORDER);
		text6.setText("" + experiment.nbRounds);
		
		label7 = new Label(shell, SWT.NONE);
		label7.setText("Balancing:");
		
		Composite composite7 = new Composite(shell, SWT.NONE);
		RowLayout layout7 = new RowLayout(SWT.VERTICAL);
		layout7.marginLeft = 30;
		composite7.setLayout(layout7);
		
		/*button7a = new Button(composite7, SWT.RADIO);
		button7a.setText("Unbalanced training && unbalanced testing");
		button7a.setEnabled(false);*/
		
		/*button7b = new Button(composite7, SWT.RADIO);
		button7b.setText("Balanced training && unbalanced testing");
		button7b.setEnabled(false);*/
		
		button7c = new Button(composite7, SWT.RADIO);
		button7c.setText("Balanced training && balanced testing");
		
		/*if(experiment.balancing == Experiment.UNBALANCED_TRAINING_TESTING)
			button7a.setSelection(true);
		else if(experiment.balancing == Experiment.BALANCED_TRAINING_UNBALANCED_TESTING)
			button7b.setSelection(true);
		else if(experiment.balancing == Experiment.BALANCED_TRAINING_TESTING)
			button7c.setSelection(true);*/
		
		if(experiment.balancing == Constants.BALANCED_TRAINING_TESTING)
			button7c.setSelection(true);
		
		Group group8 = new Group(shell, SWT.NONE);
		group8.setText("Learning technique");
		
		GridLayout layout8 = new GridLayout();
		group8.setLayout(layout8);
		
		button8a = new Button(group8, SWT.RADIO);
		button8a.setText("SVM (Support Vector Machine)");
		
		Composite composite8b = new Composite(group8, SWT.NONE);
		GridLayout layout8b = new GridLayout();
		composite8b.setLayout(layout8b);
		
		Label label8b1 = new Label(composite8b, SWT.NONE);
		label8b1.setText("Type of kernel function:");
		
		Composite composite8b2 = new Composite(composite8b, SWT.NONE);
		RowLayout layout8b2 = new RowLayout();
		layout8b2.marginLeft = 30;
		composite8b2.setLayout(layout8b2);
		
		button8b2a = new Button(composite8b2, SWT.RADIO);
		button8b2a.setText("RBF (Radial Basis Function)");
		
		button8b2b = new Button(composite8b2, SWT.RADIO);
		button8b2b.setText("Linear");
		
		button8b2c = new Button(composite8b2, SWT.RADIO);
		button8b2c.setText("Polynomial");
		
		if(experiment.kernelType == Constants.RADIAL_BASIS_FUNCTION)
			button8b2a.setSelection(true);
		else if(experiment.kernelType == Constants.LINEAR)
			button8b2b.setSelection(true);
		else if(experiment.kernelType == Constants.POLYNOMIAL)
			button8b2c.setSelection(true);
		
		button8b3 = new Button(composite8b, SWT.CHECK);
		button8b3.setText("Run Gnuplot? (Generate accuracy contour plots)");
		//button8b3.setEnabled(false);
		
		if(experiment.runGnuplot == Constants.RUN_GNUPLOT)
			button8b3.setSelection(true);
		
		button8c = new Button(group8, SWT.RADIO);
		button8c.setText("OPF (Optimum-Path Forest)");
		
		if(experiment.experimentTechnique == Constants.SVM)
			button8a.setSelection(true);
		else if(experiment.experimentTechnique == Constants.OPF)
			button8c.setSelection(true);
		
		data = new GridData();
		button8a.setLayoutData(data);
		
		data = new GridData();
		data.horizontalIndent = 30;
		composite8b.setLayoutData(data);
		
		data = new GridData();
		button8c.setLayoutData(data);
		
		Composite composite9 = new Composite(shell, SWT.NONE);
		GridLayout layout9 = new GridLayout();
		layout9.numColumns = 2;
		composite9.setLayout(layout9);
		
		btnOkay = new Button(composite9, SWT.PUSH);
		btnOkay.setText("Run");
		// btnOkay.setSize(55, 25);
		
		btnCancel = new Button(composite9, SWT.PUSH);
		btnCancel.setText("Cancel");
		// btnCancel.setSize(55, 25);
		
		data = new GridData();
		data.widthHint = 100;
		btnOkay.setLayoutData(data);
		
		data = new GridData();
		data.widthHint = 100;
		btnCancel.setLayoutData(data);
		
		// GridData
		
		data = new GridData();
		label1.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		text1.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		group2.setLayoutData(data);
		
		data = new GridData();
		label3.setLayoutData(data);
		
		data = new GridData();
		composite3.setLayoutData(data);
		
		data = new GridData();
		label4.setLayoutData(data);
		
		data = new GridData();
		data.widthHint = 65;
		text4.setLayoutData(data);
		
		data = new GridData();
		data.horizontalSpan = 2;
		label5.setLayoutData(data);
		
		data = new GridData();
		data.horizontalSpan = 2;
		composite5.setLayoutData(data);
		
		data = new GridData();
		label6.setLayoutData(data);
		
		data = new GridData();
		data.widthHint = 65;
		text6.setLayoutData(data);
		
		data = new GridData();
		data.horizontalSpan = 2;
		label7.setLayoutData(data);
		
		data = new GridData();
		data.horizontalSpan = 2;
		composite7.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		group8.setLayoutData(data);
		
		data = new GridData();
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.RIGHT;
		data.verticalIndent = 10;
		composite9.setLayoutData(data);
		
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				newExperimentDialogData.buttonResponse = (event.widget == btnOkay);
				
				newExperimentDialogData.id = text1.getText();
				
				List<DescriptorDataset> descriptorDatasets = (List<DescriptorDataset>)combo2a.getData();
				DescriptorDataset descriptorDataset = descriptorDatasets.get(combo2a.getSelectionIndex());
				newExperimentDialogData.descriptorDatasetId = descriptorDataset.id;
				
				List<AnnotationDataset> annotationDatasets = (List<AnnotationDataset>)combo2b.getData();
				AnnotationDataset annotationDataset = annotationDatasets.get(combo2b.getSelectionIndex());
				newExperimentDialogData.annotationDatasetId = annotationDataset.id;
				
				if(button3a.getSelection())
					newExperimentDialogData.targetClass = Constants.CROP;
				else if(button3b.getSelection())
					newExperimentDialogData.targetClass = Constants.FIRST_SEASON;
				else if(button3c.getSelection())
					newExperimentDialogData.targetClass = Constants.SECOND_SEASON;
				else if(button3d.getSelection())
					newExperimentDialogData.targetClass = Constants.PAIR;
				
				newExperimentDialogData.nbClasses = Integer.parseInt(text4.getText());
				
				if(button5a.getSelection())
					newExperimentDialogData.validationTechnique = Constants.RANDOM_SAMPLING;
				else if(button5b.getSelection())
					newExperimentDialogData.validationTechnique = Constants.K_FOLD;
				
				newExperimentDialogData.nbRounds = Integer.parseInt(text6.getText());
				
				/*if(button7a.getSelection())
					newExperimentDialogData.balancing = Experiment.UNBALANCED_TRAINING_TESTING;
				else if(button7b.getSelection())
					newExperimentDialogData.balancing = Experiment.BALANCED_TRAINING_UNBALANCED_TESTING;
				else if(button7c.getSelection())
					newExperimentDialogData.balancing = Experiment.BALANCED_TRAINING_TESTING;*/
				
				if(button7c.getSelection())
					newExperimentDialogData.balancing = Constants.BALANCED_TRAINING_TESTING;
				
				if(button8a.getSelection())
					newExperimentDialogData.experimentTechnique = Constants.SVM;
				else if(button8c.getSelection())
					newExperimentDialogData.experimentTechnique = Constants.OPF;
				
				if(button8b2a.getSelection())
					newExperimentDialogData.kernelType = Constants.RADIAL_BASIS_FUNCTION;
				else if(button8b2b.getSelection())
					newExperimentDialogData.kernelType = Constants.LINEAR;
				else if(button8b2c.getSelection())
					newExperimentDialogData.kernelType = Constants.POLYNOMIAL;
				
				if(button8b3.getSelection())
					newExperimentDialogData.runGnuplot = Constants.RUN_GNUPLOT;
				
				shell.close();
			}
		};
		btnOkay.addListener(SWT.Selection, listener);
		btnCancel.addListener(SWT.Selection, listener);
		
		shell.pack();
		shell.open();
		Display display = getParent().getDisplay();
		while(! shell.isDisposed()) {
			if(! display.readAndDispatch())
				display.sleep();
		}
		
		return newExperimentDialogData;
	}
}












































