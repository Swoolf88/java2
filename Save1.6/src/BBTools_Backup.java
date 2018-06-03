import javax.swing.*;
import oracle.jdbc.OracleConnection;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.sql.Connection;
import java.sql.DriverManager;
import net.proteanit.sql.DbUtils;

//Class define
public class BBTools_Backup {	
	
	//Constant define
	public static final String DEFAULT_FILENAME = "C:/Users/istvan.farkas/Documents/DB";
	public static final String DEFAULT_FILE_EXTENSION = ".csv";
	public static final String DEFAULT_TIME_INTERVAL = "1440";
	public static final String DEFAULT_TIME_INTERVAL_HOUR = "23:59";
	
	//Variables define
	//protected OracleConnection clDBConnData;
	//protected OracleConnection m_clDBConnection;
	protected SimpleDateFormat m_clDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	protected SimpleDateFormat m_clHourTimeFormat = new SimpleDateFormat("HH:mm");
	protected SimpleDateFormat FileNameFormat = new SimpleDateFormat("yyyyMMdd_HHmm");
	private JFrame frame;
	private static JTextField textFieldSqlSelect;
	private JTextField textFieldFileName;
	private JTextField textFieldManualFrom;
	private JTextField textFieldManualTo;
	private JTextField textFieldAutoWhen;
	private JTextField textFieldAutoLast;		
	private String filename;
	private JLabel lblTablePreview;
	private JLabel lblTest;
	private String strFromTime;
	private String strToTime;
	private JButton btnTest;
	private JCheckBox chckbxAutoBackup;
	private static JTextField txtTest;
	private String strAutoWhen;
	private String strAutoLast;
	public static JTextArea textAreaTablePreview;	
	private  String StrFileName;
	private JCheckBox chckbxPCDTable;
	private JCheckBox chckbxPTRTable;
	private boolean selected = false;
	private JLabel lblAutoBackupWhen;
	private JLabel lblAutoLast;
	
	//Main Function
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BBTools_Backup window = new BBTools_Backup();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//Declaration inner Variables
	Connection connection = null;
	private JRadioButton rdbtnPrevBackup;
	private JRadioButton rdbtnDailyBackup;

	//Update function
	protected void update()
	  {
	    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));	    
	    String strToTime = m_clDateTimeFormat.format(cal.getTime());
	    String strToTime1 = m_clHourTimeFormat.format(cal.getTime());
	    Calendar cal2 = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	    cal2.setTimeInMillis(cal.getTimeInMillis() - (1000*60*Integer.parseInt(DEFAULT_TIME_INTERVAL)));
	    String strFromTime = m_clDateTimeFormat.format(cal2.getTime());
	    String strFromTime1 = m_clHourTimeFormat.format(cal2.getTime());
	    textFieldManualFrom.setText(strFromTime);
	    textFieldManualTo.setText(strToTime);	    
	    textFieldAutoWhen.setText(strToTime1);
	    textFieldAutoLast.setText(DEFAULT_TIME_INTERVAL_HOUR);		   
	  }
	
	//Update with time function
	protected void update(int iMinutes)
	  {
	    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));	    
	    String strToTime = m_clDateTimeFormat.format(cal.getTime());
	    String strToTime1 = m_clHourTimeFormat.format(cal.getTime());
	    Calendar cal2 = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	    cal2.setTimeInMillis(cal.getTimeInMillis() - (1000*60*iMinutes));
	    String strFromTime = m_clDateTimeFormat.format(cal2.getTime());
	    String strFromTime1 = m_clHourTimeFormat.format(cal2.getTime());
	    textFieldManualFrom.setText(strFromTime);
	    textFieldManualTo.setText(strToTime);	
	   // textFieldAutoWhen.setText(strToTime1);
	    textFieldAutoLast.setText(strFromTime1);	
	  }	
	
	//Generate Filename
	protected void GenFileName()
	  {
	    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));	    
	    StrFileName = FileNameFormat.format(cal.getTime());  

	  }
	protected void Counter()
	  {
		Thread Counter=new Thread()	
		{
			public void run()
			{
				try{
					for(;;){
						GenFileName();
					   //textFieldFileName.setText(DEFAULT_FILENAME+StrFileName+DEFAULT_FILE_EXTENSION);
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));	    
	    String strToTime = m_clHourTimeFormat.format(cal.getTime());	   
	   String IsToTime= textFieldAutoWhen.getText().substring(textFieldAutoWhen.getText().lastIndexOf(' ') +1);
		
	  
		if(strToTime.equals(IsToTime) & chckbxAutoBackup.isSelected()==true){
			txtTest.setText("Backup made");
			}
		if(strToTime.equals(IsToTime) & chckbxAutoBackup.isSelected()==false){
			txtTest.setText("Auto Backup not activated");
			}
		if(!strToTime.equals(IsToTime) & chckbxAutoBackup.isSelected()==false){
			txtTest.setText("no good time and not activated");
			}
		if(!strToTime.equals(IsToTime) & chckbxAutoBackup.isSelected()==true){
			txtTest.setText("No Backup");			
			}
		if(chckbxAutoBackup.isSelected()==false){
			rdbtnPrevBackup.setEnabled(false);
			rdbtnDailyBackup.setEnabled(false);
			textFieldAutoWhen.setEnabled(false);
			textFieldAutoLast.setEnabled(false);
			lblAutoBackupWhen.setEnabled(false);
			lblAutoLast.setEnabled(false);
			
		}
		if(chckbxAutoBackup.isSelected()==true){
			rdbtnPrevBackup.setEnabled(true);
			rdbtnDailyBackup.setEnabled(true);
			textFieldAutoWhen.setEnabled(true);
			textFieldAutoLast.setEnabled(true);
			lblAutoBackupWhen.setEnabled(true);
			lblAutoLast.setEnabled(true);
		}
		
		if(rdbtnPrevBackup.isSelected()==true & selected==true){
			selected = false;
			rdbtnDailyBackup.setSelected(false);
			lblTest.setText("Previous");
			lblAutoBackupWhen.setText("Auto Backup Frequence:");
			lblAutoLast.setText("Interval:");
			}
		if(rdbtnDailyBackup.isSelected()==true & selected==false){
			selected = true;
			rdbtnPrevBackup.setSelected(false);
			lblTest.setText("Daily");
			lblAutoBackupWhen.setText("Auto Backup When:");
			lblAutoLast.setText("Time amount:");
			}
		
			
		
		TimeUnit.MILLISECONDS.sleep(1000);
					}
					
				}catch (InterruptedException e){
					e.printStackTrace();
				}
			}	
		};	
		Counter.start();
	}
	  
	protected void SQLCommand()
	  {
		try{
			//String query = "select * from EmployeeInfo where username=? and password=? ";
			//String query = "select EID,Name,Surname,Age from EmployeeInfo into 'C:/Users/istvan.farkas/Documents/Tel Aviv/Java Projekt/Tutorial/export.txt'";
		
			String query="select * from EmployeeInfo";
			//String query="select * from TEST_TEST";
			PreparedStatement pst=connection.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				textAreaTablePreview.append(rs.getString("EID")+";");
				textAreaTablePreview.append(rs.getString("Name")+";");
				textAreaTablePreview.append(rs.getString("Surname")+";");
				textAreaTablePreview.append(rs.getString("Age")+"\n");
				//textAreaTablePreview.append(rs.getString("MY_ID")+"\n");
				//textAreaTablePreview.append(rs.getString("MY_NAME")+";");
				//textAreaTablePreview.append(rs.getString("MY_AGE")+";");
			}						
		rs.close();
		pst.close();
		}catch(Exception e)
		{
		JOptionPane.showMessageDialog(null, e);						
		}		   
	  }	
	protected void SQLStatement()
	  {
		try{
			String sqlsource2 = " ";
			if(chckbxPCDTable.isSelected()==true & chckbxPTRTable.isSelected()==true){
				sqlsource2 = "EmployeeInfo";	
			}
			if(chckbxPCDTable.isSelected()==true & chckbxPTRTable.isSelected()==false){
				sqlsource2 = "PCDC";	
			}
			if(chckbxPCDTable.isSelected()==false & chckbxPTRTable.isSelected()==true){
				sqlsource2 = "PTRC";	
			}
			if(chckbxPCDTable.isSelected()==false & chckbxPTRTable.isSelected()==false){
				sqlsource2 = "none";	
			}
			
			
			String sqlsource1 = "EmployeeInfo";
			//"PCDC" "PTRC"
			//String query="select * from "+sqlsource1;
			String time_=textFieldManualFrom.getText();
			lblTest.setText(time_);
			//String query ="select * from "+sqlsource1+" where EID>=1 and EID<=4";
			String query1 ="select * from "+sqlsource2+" where EID>=1 and EID<=4";
			lblTest.setText(query1);
			//String query ="select * from "+sqlsource1+" where EID= "+textFieldManualFrom.getText();
			//String query ="Update EmployeeInfo set EID='"+textFieldEID.getText()+"' ,name='"+textFieldName.getText()+"' , surname='"+textFieldSurname.getText()+"' , age='"+textFieldAge.getText()+"' where EID='"+textFieldEID.getText()+"' ";
			
			//String query="select * from "+sqlsource1+" where TIMESTAMP>=? and TIMESTAMP<=?";
			PreparedStatement pst=connection.prepareStatement(query1);
			//pst.setString(1, textFieldManualFrom.getText());
			//pst.setString(2, textFieldManualTo.getText());
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				textAreaTablePreview.append(rs.getString("EID")+" ; ");
				textAreaTablePreview.append(rs.getString("Name")+" ; ");
				textAreaTablePreview.append(rs.getString("Surname")+" ; ");
				textAreaTablePreview.append(rs.getString("Age")+"\n");
			}							
		rs.close();
		pst.close();
		}catch(Exception e)
		{
		JOptionPane.showMessageDialog(null, e);						
		}	

	  }
	
	//Main method
	public BBTools_Backup() {		
		initialize();
		update(24*60);	
		update();
		
		connection=sqliteConnection.dbConnector();
		Counter();
	}	
		
	//Initialize function
	private void initialize() {
		
		//Main Frame make
		frame = new JFrame();
		frame.setBounds(100, 100, 823, 538);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//GUI Elements declaration
		JLabel lblSqlStatement = new JLabel("Select Sql Statement:");
		lblSqlStatement.setBounds(5, 23, 131, 16);
		frame.getContentPane().add(lblSqlStatement);
		
		textFieldSqlSelect = new JTextField();
		textFieldSqlSelect.setBounds(153, 20, 520, 22);
		frame.getContentPane().add(textFieldSqlSelect);
		textFieldSqlSelect.setColumns(10);
		
		JButton btnSqlExecute = new JButton("Execute");
		btnSqlExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					textAreaTablePreview.setText("");
					String sqltxt= textFieldSqlSelect.getText();
					//String query = "select * from EmployeeInfo where username=? and password=? ";
					//String query = "select EID,Name,Surname,Age from EmployeeInfo into 'C:/Users/istvan.farkas/Documents/Tel Aviv/Java Projekt/Tutorial/export.txt'";
					
					//String query="select * from EmployeeInfo";
					
					PreparedStatement pst=connection.prepareStatement(sqltxt);
					ResultSet rs=pst.executeQuery();
					while(rs.next())
					{
						textAreaTablePreview.append(rs.getString("EID")+"\t");
						textAreaTablePreview.append(rs.getString("Name")+"\t");
						textAreaTablePreview.append(rs.getString("Surname")+"\t");
						textAreaTablePreview.append(rs.getString("Age")+"\n");
					}							
				rs.close();
				pst.close();
				}catch(Exception ex)
				{
				JOptionPane.showMessageDialog(null, ex);						
				}	
			}
		});
		btnSqlExecute.setBounds(673, 19, 132, 25);
		frame.getContentPane().add(btnSqlExecute);
		
		JLabel lblNewLabel = new JLabel("Save Filename As:");
		lblNewLabel.setBounds(5, 52, 131, 16);
		frame.getContentPane().add(lblNewLabel);
		
		GenFileName();
		
		textFieldFileName = new JTextField();
		textFieldFileName.setBounds(153, 49, 520, 22);
		frame.getContentPane().add(textFieldFileName);
		textFieldFileName.setColumns(10);
		textFieldFileName.setText(DEFAULT_FILENAME+StrFileName+DEFAULT_FILE_EXTENSION);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(673, 48, 132, 25);
		frame.getContentPane().add(btnBrowse);
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {						
						JFileChooser chooser = new JFileChooser();
						chooser.showOpenDialog(null);
						File f = chooser.getSelectedFile();
						filename=f.getAbsolutePath();
						textFieldFileName.setText(filename);									
					}
				});	
		
		JLabel lblManualFrom = new JLabel("Manual Backup From:");
		lblManualFrom.setBounds(5, 81, 131, 16);
		frame.getContentPane().add(lblManualFrom);
		
		textFieldManualFrom = new JTextField();
		textFieldManualFrom.setBounds(153, 78, 220, 22);
		frame.getContentPane().add(textFieldManualFrom);
		textFieldManualFrom.setColumns(10);
		strFromTime = textFieldManualFrom.getText();	      
		
		JLabel lblManualTo = new JLabel("To:");
		lblManualTo.setBounds(386, 83, 65, 16);
		frame.getContentPane().add(lblManualTo);
		
		textFieldManualTo = new JTextField();
		textFieldManualTo.setBounds(453, 78, 220, 22);
		frame.getContentPane().add(textFieldManualTo);
		textFieldManualTo.setColumns(10);
		strToTime = textFieldManualTo.getText();
		
		JButton btnManualBackup = new JButton("Manual Backup");
		btnManualBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {					
				String filename1=textFieldFileName.getText();
				try{
					FileWriter stream = new FileWriter(filename1);
					BufferedWriter out = new BufferedWriter(stream);
					textAreaTablePreview.write(out);
					out.close();
					textAreaTablePreview.requestFocus();
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}					
			}
		});
		btnManualBackup.setBounds(673, 77, 132, 25);
		frame.getContentPane().add(btnManualBackup);
		
		lblAutoBackupWhen = new JLabel("Auto Backup When:");
		lblAutoBackupWhen.setBounds(5, 110, 140, 16);
		frame.getContentPane().add(lblAutoBackupWhen);
		
		textFieldAutoWhen = new JTextField();
		textFieldAutoWhen.setBounds(153, 107, 220, 22);
		frame.getContentPane().add(textFieldAutoWhen);
		textFieldAutoWhen.setColumns(10);
		strAutoWhen = textFieldAutoWhen.getText();
		
		lblAutoLast = new JLabel("Time Amount:");
		lblAutoLast.setBounds(372, 110, 85, 16);
		frame.getContentPane().add(lblAutoLast);
		
		textFieldAutoLast = new JTextField();
		textFieldAutoLast.setBounds(453, 107, 220, 22);
		frame.getContentPane().add(textFieldAutoLast);
		textFieldAutoLast.setColumns(10);
		strAutoLast = textFieldAutoLast.getText();
		
		chckbxAutoBackup = new JCheckBox("Auto Backup");
		chckbxAutoBackup.setSelected(true);
		chckbxAutoBackup.setBounds(453, 138, 103, 25);
		frame.getContentPane().add(chckbxAutoBackup);
		
		JLabel lblBackupTables = new JLabel("Backup Tables:");
		lblBackupTables.setBounds(5, 139, 103, 16);
		frame.getContentPane().add(lblBackupTables);
		
		chckbxPCDTable = new JCheckBox("PCD Table");
		chckbxPCDTable.setSelected(true);
		chckbxPCDTable.setBounds(146, 138, 113, 25);
		frame.getContentPane().add(chckbxPCDTable);
		
		chckbxPTRTable = new JCheckBox("PTR Table");
		chckbxPTRTable.setSelected(true);
		chckbxPTRTable.setBounds(273, 138, 113, 25);
		frame.getContentPane().add(chckbxPTRTable);
		
		lblTablePreview = new JLabel("Table Preview:");
		lblTablePreview.setBounds(5, 168, 86, 16);
		frame.getContentPane().add(lblTablePreview);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 187, 452, 293);
		frame.getContentPane().add(scrollPane);
		
		textAreaTablePreview = new JTextArea();
		scrollPane.setViewportView(textAreaTablePreview);
		
		//Test Button
		lblTest = new JLabel("Test");
		lblTest.setBounds(468, 228, 337, 22);
		frame.getContentPane().add(lblTest);
		
		btnTest = new JButton("Test Button");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//OracleConnection();
				//SQLStatement();
				SQLCommand();
				}
		});		
		btnTest.setBounds(673, 204, 132, 25);
		frame.getContentPane().add(btnTest);
		
		txtTest = new JTextField();
		txtTest.setBounds(478, 252, 318, 21);
		frame.getContentPane().add(txtTest);
		txtTest.setColumns(10);
		
		rdbtnPrevBackup = new JRadioButton("Time from previous Backup");
		
		rdbtnPrevBackup.setSelected(true);
		rdbtnPrevBackup.setBounds(558, 138, 204, 25);
		frame.getContentPane().add(rdbtnPrevBackup);
		
		rdbtnDailyBackup = new JRadioButton("Daily Backup Time");
		rdbtnDailyBackup.setBounds(558, 166, 185, 20);
		frame.getContentPane().add(rdbtnDailyBackup);
		
		
		
		
					
	}	
}
