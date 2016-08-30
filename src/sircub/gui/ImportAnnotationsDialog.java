/**
 * 
 */
package sircub.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author jordi
 *
 */
public class ImportAnnotationsDialog extends Dialog {

	public ImportAnnotationsDialog(Shell parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	private Label label1;
	private Label label2;
	private Label label3;
	private Text text1;
	private Text text2;
	private Button button2;
	private Text text3;
	private Button button3;
	
	private Button btnOkay;
	private Button btnCancel;
	private ImportAnnotationsDialogData importAnnotationsDialogData;
	
	public ImportAnnotationsDialogData open() {
		importAnnotationsDialogData = new ImportAnnotationsDialogData();
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
		label1.setText("Dataset name:");
		
		text1 = new Text(shell, SWT.BORDER);
		text1.setText("");
		
		label2 = new Label(shell, SWT.NONE);
		label2.setText("Annotation labels file:");
		
		text2 = new Text(shell, SWT.BORDER);
		text2.setText("");
		
		button2 = new Button(shell, SWT.PUSH);
		button2.setText("Browse...");
		// button2.setSize(55, 25);
		
		label3 = new Label(shell, SWT.NONE);
		label3.setText("Annotations file:");
		
		text3 = new Text(shell, SWT.BORDER);
		text3.setText("");
		
		button3 = new Button(shell, SWT.PUSH);
		button3.setText("Browse...");
		// button3.setSize(55, 25);
		
		Composite composite4 = new Composite(shell, SWT.NONE);
		GridLayout layout4 = new GridLayout();
		layout4.numColumns = 2;
		composite4.setLayout(layout4);
		
		btnOkay = new Button(composite4, SWT.PUSH);
		btnOkay.setText("Import");
		// btnOkay.setSize(55, 25);
		
		btnCancel = new Button(composite4, SWT.PUSH);
		btnCancel.setText("Cancel");
		// btnCancel.setSize(55, 25);
		
		GridData data = new GridData();
		data.widthHint = 100;
		btnOkay.setLayoutData(data);
		
		data = new GridData();
		data.widthHint = 100;
		btnCancel.setLayoutData(data);
		
		text1.setText("dataset1_annotations_en_2");
		text2.setText("/home/jordi/postdoc/workspace/SiRCub3/input/dataset1/annotation_labels_en.csv");
		text3.setText("/home/jordi/postdoc/workspace/SiRCub3/input/dataset1/annotations_en.csv");
		
		// GridData
		
		data = new GridData();
		label1.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		text1.setLayoutData(data);
		
		data = new GridData();
		label2.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 0;
		text2.setLayoutData(data);
		
		data = new GridData();
		data.widthHint = 100;
		button2.setLayoutData(data);
		
		data = new GridData();
		label3.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = 0;
		text3.setLayoutData(data);
		
		data = new GridData();
		data.widthHint = 100;
		button3.setLayoutData(data);
		
		data = new GridData();
		data.horizontalSpan = 3;
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.RIGHT;
		data.verticalIndent = 10;
		composite4.setLayoutData(data);
		
		Listener labelsFileListener = new Listener() {
			public void handleEvent(Event event) {
				String[] filterExtensions = {"*.csv", "*.*"};
				FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
				fileDialog.setText("Annotation Labels File");
				//fileDialog.setFilterPath("C:/");
				fileDialog.setFilterExtensions(filterExtensions);
				
				String selectedFile = fileDialog.open();
				
				if(selectedFile != null) {
					text2.setText(selectedFile);
				}
			}
		};
		button2.addListener(SWT.Selection, labelsFileListener);
		
		Listener annotationsFileListener = new Listener() {
			public void handleEvent(Event event) {
				String[] filterExtensions = {"*.csv", "*.*"};
				FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
				fileDialog.setText("Annotations File");
				//fileDialog.setFilterPath("C:/");
				fileDialog.setFilterExtensions(filterExtensions);
				
				String selectedFile = fileDialog.open();
				
				if(selectedFile != null) {
					text3.setText(selectedFile);
				}
			}
		};
		button3.addListener(SWT.Selection, annotationsFileListener);
		
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				importAnnotationsDialogData.buttonResponse = (event.widget == btnOkay);
				
				importAnnotationsDialogData.name = text1.getText();
				
				importAnnotationsDialogData.labelsPath = text2.getText();
				
				importAnnotationsDialogData.annotationsPath = text3.getText();
				
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
		
		return importAnnotationsDialogData;
	}
}




















































