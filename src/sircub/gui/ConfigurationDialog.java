/**
 * 
 */
package sircub.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

import sircub.Sircub;

/**
 * @author jordi
 *
 */
public class ConfigurationDialog extends Dialog {

	public ConfigurationDialog(Shell parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	private Label label1a;
	private Label label1b;
	private Label label1c;
	private Label label1d;
	private Label label2;
	private Label label3;
	private Label label4;
	private Label label5;
	private Label label6;
	private Label label7;
	private Text text1a;
	private Text text1b;
	private Text text1c;
	private Text text1d;
	private Text text2;
	private Text text3;
	private Text text4;
	private Text text5;
	private Text text6;
	private Text text7;
	
	private Button btnOkay;
	private Button btnCancel;
	private ConfigurationDialogData configurationDialogData;
	
	public ConfigurationDialogData open() {
		configurationDialogData = new ConfigurationDialogData();
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
		
		Group group1 = new Group(shell, SWT.NONE);
		group1.setText("Database connection");
		
		GridLayout layout1 = new GridLayout();
		layout1.numColumns = 2;
		group1.setLayout(layout1);
		
		label1a = new Label(group1, SWT.NONE);
		label1a.setText("Hostname:");
		
		text1a = new Text(group1, SWT.BORDER);
		text1a.setText(Sircub.hostname);
		
		label1b = new Label(group1, SWT.NONE);
		label1b.setText("Database:");
		
		text1b = new Text(group1, SWT.BORDER);
		text1b.setText(Sircub.database);
		
		label1c = new Label(group1, SWT.NONE);
		label1c.setText("User:");
		
		text1c = new Text(group1, SWT.BORDER);
		text1c.setText(Sircub.user);
		
		label1d = new Label(group1, SWT.NONE);
		label1d.setText("Password:");
		
		text1d = new Text(group1, SWT.BORDER);
		text1d.setText(Sircub.password);
		
		GridData data = new GridData();
		label1a.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		text1a.setLayoutData(data);
		
		data = new GridData();
		label1b.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		text1b.setLayoutData(data);
		
		data = new GridData();
		label1c.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		text1c.setLayoutData(data);
		
		data = new GridData();
		label1d.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		text1d.setLayoutData(data);
		
		label2 = new Label(shell, SWT.NONE);
		label2.setText("Timesat directory:");
		
		text2 = new Text(shell, SWT.BORDER);
		text2.setText(Sircub.timesatDirectory);
		
		label3 = new Label(shell, SWT.NONE);
		label3.setText("Libsvm directory:");
		
		text3 = new Text(shell, SWT.BORDER);
		text3.setText(Sircub.libsvmDirectory);
		
		label4 = new Label(shell, SWT.NONE);
		label4.setText("Gnuplot executable:");
		
		text4 = new Text(shell, SWT.BORDER);
		text4.setText(Sircub.gnuplotExecutable);
		
		label5 = new Label(shell, SWT.NONE);
		label5.setText("Libopf directory:");
		
		text5 = new Text(shell, SWT.BORDER);
		text5.setText(Sircub.libopfDirectory);
		
		label6 = new Label(shell, SWT.NONE);
		label6.setText("Experiments directory:");
		
		text6 = new Text(shell, SWT.BORDER);
		text6.setText(Sircub.experimentsDirectory);
		
		label7 = new Label(shell, SWT.NONE);
		label7.setText("Classifications directory:");
		
		text7 = new Text(shell, SWT.BORDER);
		text7.setText(Sircub.classificationsDirectory);
		
		Composite composite8 = new Composite(shell, SWT.NONE);
		GridLayout layout8 = new GridLayout();
		layout8.numColumns = 2;
		composite8.setLayout(layout8);
		
		btnOkay = new Button(composite8, SWT.PUSH);
		btnOkay.setText("Ok");
		// btnOkay.setSize(55, 25);
		
		btnCancel = new Button(composite8, SWT.PUSH);
		btnCancel.setText("Cancel");
		// btnCancel.setSize(55, 25);
		
		data = new GridData();
		data.widthHint = 100;
		btnOkay.setLayoutData(data);
		
		data = new GridData();
		data.widthHint = 100;
		btnCancel.setLayoutData(data);
		
		// GridData
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		group1.setLayoutData(data);
		
		data = new GridData();
		label2.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		text2.setLayoutData(data);
		
		data = new GridData();
		label3.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		text3.setLayoutData(data);
		
		data = new GridData();
		label4.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		text4.setLayoutData(data);
		
		data = new GridData();
		label5.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		text5.setLayoutData(data);
		
		data = new GridData();
		label6.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		text6.setLayoutData(data);
		
		data = new GridData();
		label7.setLayoutData(data);
		
		data = new GridData(GridData.FILL_HORIZONTAL);
		text7.setLayoutData(data);
		
		data = new GridData();
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = SWT.RIGHT;
		data.verticalIndent = 10;
		composite8.setLayoutData(data);
		
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				configurationDialogData.buttonResponse = (event.widget == btnOkay);
				configurationDialogData.hostname = text1a.getText();
				configurationDialogData.database = text1b.getText();
				configurationDialogData.user = text1c.getText();
				configurationDialogData.password = text1d.getText();
				configurationDialogData.timesatDirectory = text2.getText();
				configurationDialogData.libsvmDirectory = text3.getText();
				configurationDialogData.gnuplotExecutable = text4.getText();
				configurationDialogData.libopfDirectory = text5.getText();
				configurationDialogData.experimentsDirectory = text6.getText();
				configurationDialogData.classificationsDirectory = text7.getText();
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
		
		return configurationDialogData;
	}
}

























