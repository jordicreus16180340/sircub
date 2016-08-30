/**
 * 
 */
package sircub.gui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import sircub.Constants;
import sircub.DatabaseInterface;
import sircub.classification.Classification;
import sircub.datamodel.DescriptorDataset;

/**
 * @author jordi
 *
 */
public class NewClassificationDialog extends Dialog {

	public NewClassificationDialog(Shell parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}
	
	private Label label1;
	private Label label2;
	private Label label3;
	private Label label4;
	private Label label5;
	private Label label6;
	private Text text1;
	private Combo combo2;
	private Text text3;
	private Button button3;
	private Text text4;
	private Button button4;
	private Button button5a;
	private Button button5b;
	private Button button5c;
	private Button button5d;
	private Button button6a;
	private Button button6b;
	
	private Button btnOkay;
	private Button btnCancel;
	private NewClassificationDialogData newClassificationDialogData;
	
	public NewClassificationDialogData open(Classification classification) {
		List<DescriptorDataset> descriptorDatasets = DatabaseInterface.getDescriptorDatasets();
		
		newClassificationDialogData = new NewClassificationDialogData();
		final Shell shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setText(getText());
		// shell.setSize(800, 600);
		shell.setMinimumSize(500, 0);
		
		GridLayout shellLayout = new GridLayout();
		shellLayout.numColumns = 3;
		shellLayout.marginHeight = 10;
		shellLayout.marginWidth = 10;
		// shellLayout.verticalSpacing = 10;
		shell.setLayout(shellLayout);
		
		label1 = new Label(shell, SWT.NONE);
		label1.setText("Classification id:");
		
		text1 = new Text(shell, SWT.BORDER);
		text1.setText(classification.id);
		
		label2 = new Label(shell, SWT.NONE);
		label2.setText("Crop descriptors:");
		
		combo2 = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
		for(int i = 0; i < descriptorDatasets.size(); i++) {
			DescriptorDataset descriptorDataset = descriptorDatasets.get(i);
			combo2.add(descriptorDataset.name);
			if(descriptorDataset.id == classification.descriptorDatasetId)
				combo2.select(i);
		}
		combo2.setData(descriptorDatasets);
		
		label3 = new Label(shell, SWT.NONE);
		label3.setText("Annotation labels file:");
		
		text3 = new Text(shell, SWT.BORDER);
		text3.setText(classification.labelsPath);
		
		button3 = new Button(shell, SWT.PUSH);
		button3.setText("Browse...");
		// button3.setSize(55, 25);
		
		label4 = new Label(shell, SWT.NONE);
		label4.setText("Experiment round directory:");
		
		text4 = new Text(shell, SWT.BORDER);
		text4.setText(classification.roundPath);
		
		button4 = new Button(shell, SWT.PUSH);
		button4.setText("Browse...");
		// button4.setSize(55, 25);
		
		label5 = new Label(shell, SWT.NONE);
		label5.setText("Target class:");
		
		Composite composite5 = new Composite(shell, SWT.NONE);
		RowLayout layout5 = new RowLayout();
		composite5.setLayout(layout5);
		
		button5a = new Button(composite5, SWT.RADIO);
		button5a.setText("Crop");
		button5a.setEnabled(false);
		
		button5b = new Button(composite5, SWT.RADIO);
		button5b.setText("First season");
		
		button5c = new Button(composite5, SWT.RADIO);
		button5c.setText("Second season");
		
		button5d = new Button(composite5, SWT.RADIO);
		button5d.setText("Pair");
		button5d.setEnabled(false);
		
		if(classification.targetClass == Constants.CROP)
			button5a.setSelection(true);
		else if(classification.targetClass == Constants.FIRST_SEASON)
			button5b.setSelection(true);
		else if(classification.targetClass == Constants.SECOND_SEASON)
			button5c.setSelection(true);
		else if(classification.targetClass == Constants.PAIR)
			button5d.setSelection(true);
		
		label6 = new Label(shell, SWT.NONE);
		label6.setText("Learning technique:");
		
		Composite composite6 = new Composite(shell, SWT.NONE);
		RowLayout layout6 = new RowLayout(SWT.VERTICAL);
		layout6.marginLeft = 30;
		composite6.setLayout(layout6);
		
		button6a = new Button(composite6, SWT.RADIO);
		button6a.setText("SVM (Support Vector Machine)");
		
		button6b = new Button(composite6, SWT.RADIO);
		button6b.setText("OPF (Optimum-Path Forest)");
		
		if(classification.experimentTechnique == Constants.SVM)
			button6a.setSelection(true);
		else if(classification.experimentTechnique == Constants.OPF)
			button6b.setSelection(true);
		
		Composite composite7 = new Composite(shell, SWT.NONE);
		GridLayout layout7 = new GridLayout();
		layout7.numColumns = 2;
		composite7.setLayout(layout7);
		
		btnOkay = new Button(composite7, SWT.PUSH);
		btnOkay.setText("Run");
		// btnOkay.setSize(55, 25);
		
		btnCancel = new Button(composite7, SWT.PUSH);
		btnCancel.setText("Cancel");
		// btnCancel.setSize(55, 25);
		
		GridData data = new GridData();
		data.widthHint = 100;
		btnOkay.setLayoutData(data);
		
		data = new GridData();
		data.widthHint = 100;
		btnCancel.setLayoutData(data);
		
		// GridData
		
		data = new GridData();
		label1.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		text1.setLayoutData(data);
		
		data = new GridData();
		label2.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		combo2.setLayoutData(data);
		
		data = new GridData();
		label3.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 0;
		text3.setLayoutData(data);
		
		data = new GridData();
		data.widthHint = 100;
		button3.setLayoutData(data);
		
		data = new GridData();
		label4.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 0;
		text4.setLayoutData(data);
		
		data = new GridData();
		data.widthHint = 100;
		button4.setLayoutData(data);
		
		data = new GridData();
		label5.setLayoutData(data);
		
		data = new GridData();
		data.horizontalSpan = 2;
		composite5.setLayoutData(data);
		
		data = new GridData();
		data.horizontalSpan = 3;
		label6.setLayoutData(data);
		
		data = new GridData();
		data.horizontalSpan = 3;
		composite6.setLayoutData(data);
		
		data = new GridData();
		data.horizontalSpan = 3;
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.RIGHT;
		data.verticalIndent = 10;
		composite7.setLayoutData(data);
		
		Listener labelsFileListener = new Listener() {
			public void handleEvent(Event event) {
				String[] filterExtensions = {"*.csv", "*.*"};
				FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
				fileDialog.setText("Annotation Labels File");
				//fileDialog.setFilterPath("C:/");
				fileDialog.setFilterExtensions(filterExtensions);
				
				String selectedFile = fileDialog.open();
				
				if(selectedFile != null) {
					text3.setText(selectedFile);
				}
			}
		};
		button3.addListener(SWT.Selection, labelsFileListener);
		
		Listener roundDirectoryListener = new Listener() {
			public void handleEvent(Event event) {
				DirectoryDialog directoryDialog = new DirectoryDialog(shell);
				directoryDialog.setText("Experiment Round Directory");
				//directoryDialog.setFilterPath("C:/Program Files");
				
				String selectedDirectory = directoryDialog.open();
				
				if(selectedDirectory != null) {
					text4.setText(selectedDirectory + "/"); // TODO
				}
			}
		};
		button4.addListener(SWT.Selection, roundDirectoryListener);
		
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				newClassificationDialogData.buttonResponse = (event.widget == btnOkay);
				
				newClassificationDialogData.id = text1.getText();
				
				List<DescriptorDataset> descriptorDatasets = (List<DescriptorDataset>)combo2.getData();
				DescriptorDataset descriptorDataset = descriptorDatasets.get(combo2.getSelectionIndex());
				newClassificationDialogData.descriptorDatasetId = descriptorDataset.id;
				
				newClassificationDialogData.labelsPath = text3.getText();
				
				newClassificationDialogData.roundPath = text4.getText();
				
				if(button5a.getSelection())
					newClassificationDialogData.targetClass = Constants.CROP;
				else if(button5b.getSelection())
					newClassificationDialogData.targetClass = Constants.FIRST_SEASON;
				else if(button5c.getSelection())
					newClassificationDialogData.targetClass = Constants.SECOND_SEASON;
				else if(button5d.getSelection())
					newClassificationDialogData.targetClass = Constants.PAIR;
				
				if(button6a.getSelection())
					newClassificationDialogData.experimentTechnique = Constants.SVM;
				else if(button6b.getSelection())
					newClassificationDialogData.experimentTechnique = Constants.OPF;
				
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
		
		return newClassificationDialogData;
	}
}

























































