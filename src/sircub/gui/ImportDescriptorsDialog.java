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
public class ImportDescriptorsDialog extends Dialog {

	public ImportDescriptorsDialog(Shell parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}
	
	private Label label1;
	private Label label2;
	private Label label3;
	private Label label4;
	private Label label5;
	private Text text1;
	private Text text2;
	private Button button2;
	private Text text3;
	private Button button3;
	private Text text4;
	private Text text5;
	
	private Button btnOkay;
	private Button btnCancel;
	private ImportDescriptorsDialogData importDescriptorsDialogData;
	
	public ImportDescriptorsDialogData open() {
		importDescriptorsDialogData = new ImportDescriptorsDialogData();
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
		label2.setText("Descriptor locations file:");
		
		text2 = new Text(shell, SWT.BORDER);
		text2.setText("");
		
		button2 = new Button(shell, SWT.PUSH);
		button2.setText("Browse...");
		// button3.setSize(55, 25);
		
		label3 = new Label(shell, SWT.NONE);
		label3.setText("Descriptors file:");
		
		text3 = new Text(shell, SWT.BORDER);
		text3.setText("");
		
		button3 = new Button(shell, SWT.PUSH);
		button3.setText("Browse...");
		// button3.setSize(55, 25);
		
		label4 = new Label(shell, SWT.NONE);
		label4.setText("Number of years:");
		
		text4 = new Text(shell, SWT.BORDER);
		text4.setText("");
		
		label5 = new Label(shell, SWT.NONE);
		label5.setText("Number of values per year:");
		
		text5 = new Text(shell, SWT.BORDER);
		text5.setText("");
		
		Composite composite6 = new Composite(shell, SWT.NONE);
		GridLayout layout6 = new GridLayout();
		layout6.numColumns = 2;
		composite6.setLayout(layout6);
		
		btnOkay = new Button(composite6, SWT.PUSH);
		btnOkay.setText("Import");
		// btnOkay.setSize(55, 25);
		
		btnCancel = new Button(composite6, SWT.PUSH);
		btnCancel.setText("Cancel");
		// btnCancel.setSize(55, 25);
		
		GridData data = new GridData();
		data.widthHint = 100;
		btnOkay.setLayoutData(data);
		
		data = new GridData();
		data.widthHint = 100;
		btnCancel.setLayoutData(data);
		
		text1.setText("dataset1_descriptors_settings1_2");
		text2.setText("/home/jordi/postdoc/workspace/SiRCub3/input/dataset1/descriptor_locations.csv");
		text3.setText("/home/jordi/postdoc/workspace/SiRCub3/input/dataset1/descriptors_settings1.txt");
		text4.setText("4");
		text5.setText("46");
		
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
		label4.setLayoutData(data);
		
		data = new GridData();
		data.horizontalSpan = 2;
		data.widthHint = 65;
		text4.setLayoutData(data);
		
		data = new GridData();
		label5.setLayoutData(data);
		
		data = new GridData();
		data.horizontalSpan = 2;
		data.widthHint = 65;
		text5.setLayoutData(data);
		
		data = new GridData();
		data.horizontalSpan = 3;
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.RIGHT;
		data.verticalIndent = 10;
		composite6.setLayoutData(data);
		
		Listener locationsFileListener = new Listener() {
			public void handleEvent(Event event) {
				String[] filterExtensions = {"*.csv", "*.*"};
				FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
				fileDialog.setText("Descriptor Locations File");
				//fileDialog.setFilterPath("C:/");
				fileDialog.setFilterExtensions(filterExtensions);
				
				String selectedFile = fileDialog.open();
				
				if(selectedFile != null) {
					text2.setText(selectedFile);
				}
			}
		};
		button2.addListener(SWT.Selection, locationsFileListener);
		
		Listener descriptorsFileListener = new Listener() {
			public void handleEvent(Event event) {
				String[] filterExtensions = {"*.txt", "*.*"};
				FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
				fileDialog.setText("Descriptors File");
				//fileDialog.setFilterPath("C:/");
				fileDialog.setFilterExtensions(filterExtensions);
				
				String selectedFile = fileDialog.open();
				
				if(selectedFile != null) {
					text3.setText(selectedFile);
				}
			}
		};
		button3.addListener(SWT.Selection, descriptorsFileListener);
		
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				importDescriptorsDialogData.buttonResponse = (event.widget == btnOkay);
				
				importDescriptorsDialogData.name = text1.getText();
				
				importDescriptorsDialogData.locationsPath = text2.getText();
				
				importDescriptorsDialogData.descriptorsPath = text3.getText();
				
				if(! text4.getText().equals(""))
					importDescriptorsDialogData.nbYears = Integer.parseInt(text4.getText());
				
				if(! text5.getText().equals(""))
					importDescriptorsDialogData.nbValues = Integer.parseInt(text5.getText());
				
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
		
		return importDescriptorsDialogData;
	}
}


































