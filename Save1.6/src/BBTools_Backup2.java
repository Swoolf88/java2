import javax.swing.*;

import oracle.jdbc.OracleConnection;

import java.awt.EventQueue;
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
import java.sql.SQLException;
import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//Class define
public class BBTools_Backup2 {	
	
	//Constant define
	public static final String DEFAULT_FILENAME = "C:/Users/istvan.farkas/Documents/DB";
	public static final String DEFAULT_FILE_EXTENSION = ".csv";
	public static final String DEFAULT_TIME_INTERVAL = "1440";
	public static final String DEFAULT_TIME_INTERVAL_HOUR = "23:59";
	public static final String DEFAULT_TIME_BACKUP = "23:00";
	
	//Variables define
	protected SimpleDateFormat m_clDateTimeFormat = new SimpleDateFormat("dd-MM-yy HH.mm");
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
	private String filenameSQL="C:\\Users\\istvan.farkas\\Documents\\Tel Aviv\\Java Projekt\\Tutorial\\TestData.sqlite";
	private JLabel lblTablePreview;
	private JLabel lblTest;
	private String strFromTime;
	private String strFromTimeSQL;
	private String strToTime;
	private String strToTimeSQL;
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
	private String inputname;
	private String Column1;
	private String Column2;
	private String Column3;
	
	//PCD String
	private String txtID;
	private String txtTIMESTAMP;
	private String txtPLCNODEID;
	private String txtUNITID;
	private String txtFUNCTIONID;
	private String txtEVENT_1;
	private String txtEVENT_2;
	private String txtEVENT_3;
	private String txtEVENT_4;
	private String txtEVENT_5;
	private String txtEVENT_6;
	private String txtEVENT_7;
	private String txtEVENT_8;
	private String txtEVENT_9;
	private String txtEVENT_10;
	private String txtEVENT_11;
	private String txtEVENT_12;
	
	//PTR String
	private String txtIDPTR;
	private String txtTIMESTAMPPTR;
	private String txtPLCNOEID ;
	private String txtUNITIDPTR;
	private String txtFUNCTIONIDPTR;
	private String txtTULID;
	private String txtGLOBALID;
	private String txtTRAYID;
	private String txtLOADINGSTATE;
	private String txtTRANSPORTUNITTYPE;
	private String txtDESTINATIONID;
	private String txtROUTINGTABLEID;
	private String txtDESTINATIONMODE;
	private String txtPROBLEMDESTINATION;
	private String txtTRANSPORTGOODSID;
	private String txtHBSCONTROL1;
	private String txtHBSCONTROL2;
	private String txtHBSLEVEL;
	private String txtHBSRESULT;
	private String txtEVENTHOST;
	private String txtTUSTATE1;
	private String txtSPARE02;
	private String txtSPARE03;
	private String txtSPARE04;
	private String txtSPARE05;
	private String txtAGEVALUE;
	private String txtADDINFO01;
	private String txtADDINFO02;
	private String txtADDINFO03;
	private String txtADDINFO04;
	private String txtADDINFO05;
	private String txtADDINFO06;
	private String txtADDINFO07;
	private String txtADDINFO08;
	private String txtADDINFO09;
	private String txtADDINFO10;
	private String TableColumnNames;
	private String SQLStatementPCD;
	private String SQLStatementPTR;
	
	
	//Main Function
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BBTools_Backup2 window = new BBTools_Backup2();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//Declaration inner Variables
	Connection connection = null;
	Connection connection1 = null;
	Connection connection2 = null;
	Connection connection3 = null;
	private JRadioButton rdbtnPrevBackup;
	private JRadioButton rdbtnDailyBackup;
	 
	//Convert month from numeric to string
	public String ConvertMonth(String getmonth)
	{
	try{
		String timeform=getmonth.substring(3,5);	    
	    int month = Integer.parseInt(timeform);
        String monthString;        
        switch (month) {
        case 1:  monthString = "JAN";
                 break;
        case 2:  monthString = "FEB";
                 break;
        case 3:  monthString = "MAR";
                 break;
        case 4:  monthString = "APR";
                 break;
        case 5:  monthString = "MAY";
                 break;
        case 6:  monthString = "JUN";
                 break;
        case 7:  monthString = "JUL";
                 break;
        case 8:  monthString = "AUG";
                 break;
        case 9:  monthString = "SEP";
                 break;
        case 10: monthString = "OCT";
                 break;
        case 11: monthString = "NOV";
                 break;
        case 12: monthString = "DEC";
                 break;
        default: monthString = "INV";
                 break;
    }
        String timeform1 = "-"+timeform+"-";
    	String monthString1 = "-"+monthString+"-";
    	String Monthtxt =  getmonth.replace(timeform1,monthString1);	    
	    return Monthtxt;
	}catch(Exception e)
	{
	return null;
	}
	}
	
	//Generate SQL Statement
	protected void genSQLManual()
	  {	
		textAreaTablePreview.setText("");
		strFromTimeSQL=ConvertMonth(textFieldManualFrom.getText());
		strToTimeSQL=ConvertMonth(textFieldManualTo.getText());
		if(chckbxPCDTable.isSelected()==true & chckbxPTRTable.isSelected()==false){				
			SQLStatementPCD= "select * from PCD_COMMONDIAGNOSTICS where TIMESTAMP>='"+strFromTimeSQL+"' and TIMESTAMP<='"+strToTimeSQL+"'";
			SQLStatementPTR= "";
		}
		if(chckbxPCDTable.isSelected()==false & chckbxPTRTable.isSelected()==true){				
			SQLStatementPCD= "";
			SQLStatementPTR= "select * from PTR_TRANSPORTUNITROUTING where TIMESTAMP>='"+strFromTimeSQL+"' and TIMESTAMP<='"+strToTimeSQL+"'";
		}
		if(chckbxPCDTable.isSelected()==true & chckbxPTRTable.isSelected()==true){
			SQLStatementPCD= "select * from PCD_COMMONDIAGNOSTICS where TIMESTAMP>='"+strFromTimeSQL+"' and TIMESTAMP<='"+strToTimeSQL+"'";
			SQLStatementPTR= "select * from PTR_TRANSPORTUNITROUTING where TIMESTAMP>='"+strFromTimeSQL+"' and TIMESTAMP<='"+strToTimeSQL+"'";
		}
		if(chckbxPCDTable.isSelected()==false & chckbxPTRTable.isSelected()==false){				
			SQLStatementPCD= "";
			SQLStatementPTR= "";
		}
		textAreaTablePreview.append(SQLStatementPCD+"\n");
		textAreaTablePreview.append(SQLStatementPTR);
	  }
	  
	//Update function
	protected void update()
	  {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));	    
	    strToTime = m_clDateTimeFormat.format(cal.getTime());
	    String strToTime1 = m_clHourTimeFormat.format(cal.getTime());
	    strToTimeSQL=ConvertMonth(strToTime);
	    Calendar cal2 = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	    cal2.setTimeInMillis(cal.getTimeInMillis() - (1000*60*Integer.parseInt(DEFAULT_TIME_INTERVAL)));
	    strFromTime = m_clDateTimeFormat.format(cal2.getTime());
	    strFromTimeSQL=ConvertMonth(strFromTime);
	    String strFromTime1 = m_clHourTimeFormat.format(cal2.getTime());
	    textFieldManualFrom.setText(strFromTime);
	    textFieldManualTo.setText(strToTime);	    
	    textFieldAutoWhen.setText(DEFAULT_TIME_BACKUP);
	    textFieldAutoLast.setText(DEFAULT_TIME_INTERVAL_HOUR);		   
	  }
	
	//Update with time function
	protected void update(int iMinutes)
	  {
	    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));	    
	    strToTime = m_clDateTimeFormat.format(cal.getTime());
	    String strToTime1 = m_clHourTimeFormat.format(cal.getTime());
	    strToTimeSQL=ConvertMonth(strToTime);
	    Calendar cal2 = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
	    cal2.setTimeInMillis(cal.getTimeInMillis() - (1000*60*iMinutes));
	    strFromTime = m_clDateTimeFormat.format(cal2.getTime());
	    strFromTimeSQL=ConvertMonth(strFromTime);
	    String strFromTime1 = m_clHourTimeFormat.format(cal2.getTime());
	    textFieldManualFrom.setText(strFromTime);
	    textFieldManualTo.setText(strToTime);	
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
			String time_=textFieldManualFrom.getText();
			lblTest.setText(time_);
			String query1 ="select * from "+sqlsource2+" where EID>=1 and EID<=4";
			lblTest.setText(query1);
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
	protected void SQLCommand()
	  {
		try{
			//String query="select * from TEST_TEST1";
			//String query="select * from PCD_COMMONDIAGNOSTICS";
			//String query="select * from PCD_COMMONDIAGNOSTICS where TIMESTAMP>='05-SEP-13 04.55.' and TIMESTAMP<='05-SEP-13 04.59.'";
			String query="select * from PCD_COMMONDIAGNOSTICS where ID>=2259792 and ID<=2259962";
			//String query="select * from PTR_TRANSPORTUNITROUTING";
			PreparedStatement pst=connection.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				//textAreaTablePreview.append(rs.getString("MY_ID")+";");
				//textAreaTablePreview.append(rs.getString("SZAM")+"\n");
				//PCD Table
				textAreaTablePreview.append(rs.getString("ID")+";");
				 //textAreaTablePreview.append(rs.getString("TIMESTAMP")+";");
				 textAreaTablePreview.append(rs.getString("PLCNODEID")+";");
				 textAreaTablePreview.append(rs.getString("UNITID")+";");
				 textAreaTablePreview.append(rs.getString("FUNCTIONID")+";");
				 textAreaTablePreview.append(rs.getString("EVENT_1")+";");
				 textAreaTablePreview.append(rs.getString("EVENT_2")+";");
				 textAreaTablePreview.append(rs.getString("EVENT_3")+";");
				 textAreaTablePreview.append(rs.getString("EVENT_4")+";");
				 textAreaTablePreview.append(rs.getString("EVENT_5")+";");
				 textAreaTablePreview.append(rs.getString("EVENT_6")+";");
				 textAreaTablePreview.append(rs.getString("EVENT_7")+";");
				 textAreaTablePreview.append(rs.getString("EVENT_8")+";");
				 textAreaTablePreview.append(rs.getString("EVENT_9")+";");
				 textAreaTablePreview.append(rs.getString("EVENT_10")+";");
				 textAreaTablePreview.append(rs.getString("EVENT_11")+";");
				 textAreaTablePreview.append(rs.getString("EVENT_12")+"\n");
				 
				//PTR Table
				/*
				 textAreaTablePreview.append(rs.getString("ID")+";");
				 textAreaTablePreview.append(rs.getString("TIMESTAMP")+";");
				 textAreaTablePreview.append(rs.getString("PLCNOEID")+";"); 
				 textAreaTablePreview.append(rs.getString("UNITID")+";");
				 textAreaTablePreview.append(rs.getString("FUNCTIONID")+";");
				 textAreaTablePreview.append(rs.getString("TULID")+";");
				 textAreaTablePreview.append(rs.getString("GLOBALID")+";");
				 textAreaTablePreview.append(rs.getString("TRAYID")+";");
				 textAreaTablePreview.append(rs.getString("LOADINGSTATE")+";");
				 textAreaTablePreview.append(rs.getString("TRANSPORTUNITTYPE")+";");
				 textAreaTablePreview.append(rs.getString("DESTINATIONID")+";");
				 textAreaTablePreview.append(rs.getString("ROUTINGTABLEID")+";");
				 textAreaTablePreview.append(rs.getString("DESTINATIONMODE")+";");
				 textAreaTablePreview.append(rs.getString("PROBLEMDESTINATION")+";");
				 textAreaTablePreview.append(rs.getString("TRANSPORTGOODSID")+";");
				 textAreaTablePreview.append(rs.getString("HBSCONTROL1")+";");
				 textAreaTablePreview.append(rs.getString("HBSCONTROL2")+";");
				 textAreaTablePreview.append(rs.getString("HBSLEVEL")+";");
				 textAreaTablePreview.append(rs.getString("HBSRESULT")+";");
				 textAreaTablePreview.append(rs.getString("EVENTHOST")+";");
				 textAreaTablePreview.append(rs.getString("TUSTATE1")+";");
				 textAreaTablePreview.append(rs.getString("SPARE02")+";");
				 textAreaTablePreview.append(rs.getString("SPARE03")+";");
				 textAreaTablePreview.append(rs.getString("SPARE04")+";");
				 textAreaTablePreview.append(rs.getString("SPARE05")+";");
				 textAreaTablePreview.append(rs.getString("AGEVALUE")+";");
				 textAreaTablePreview.append(rs.getString("ADDINFO01")+";");
				 textAreaTablePreview.append(rs.getString("ADDINFO02")+";");
				 textAreaTablePreview.append(rs.getString("ADDINFO03")+";");
				 textAreaTablePreview.append(rs.getString("ADDINFO04")+";");
				 textAreaTablePreview.append(rs.getString("ADDINFO05")+";");
				 textAreaTablePreview.append(rs.getString("ADDINFO06")+";");
				 textAreaTablePreview.append(rs.getString("ADDINFO07")+";");
				 textAreaTablePreview.append(rs.getString("ADDINFO08")+";");
				 textAreaTablePreview.append(rs.getString("ADDINFO09")+";");
				 textAreaTablePreview.append(rs.getString("ADDINFO10")+";");				 
				 */
			}						
		rs.close();
		pst.close();
		}catch(Exception e)
		{
		JOptionPane.showMessageDialog(null, e);						
		}		   
	  }	
	
	protected void SQLCommand1()
	  {
		try{
			
			String ID_ = textAreaTablePreview.getText();
			String query="select * from PCDTable1 where ID<='"+ID_+"' ";
			textAreaTablePreview.setText("");
			/*where ID='"+EID_+"'*/
			//String query1 ="insert into PCDTable1 (ID,Timestamp,UnitID) values (?,?,?)";
			//String query1= textFieldSqlSelect.getText();
			PreparedStatement pst=connection1.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				textAreaTablePreview.append(rs.getString("ID")+";");
				Column1 = rs.getString("ID");
				textAreaTablePreview.append(rs.getString("Timestamp")+";");
				Column2 = rs.getString("Timestamp");
				textAreaTablePreview.append(rs.getString("UnitID")+"\n");
				Column3 = rs.getString("UnitID");
				}						
		rs.close();
		pst.close();	
		}catch(Exception e)
		{
		JOptionPane.showMessageDialog(null, e);						
		}
	  }
	
	protected void SQLCommand2()
	  {
		try {
			textAreaTablePreview.setText("");			
					
			//Delete Table rows!!!
					Statement st3 = connection2.createStatement();
					st3.executeUpdate("DELETE FROM PCDTable2 WHERE ID>=1");
					String query="select * from PCDTable where ID<6";
			PreparedStatement pst=connection1.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				textAreaTablePreview.append(rs.getString("ID")+";");
				Column1 = rs.getString("ID");
				textAreaTablePreview.append(rs.getString("Timestamp")+";");
				Column2 = rs.getString("Timestamp");
				textAreaTablePreview.append(rs.getString("UnitID")+"\n");
				Column3 = rs.getString("UnitID");
				String query1 ="insert into PCDTable2 (ID,Timestamp,UnitID) VALUES(?,?,?)";
				PreparedStatement pst1 = connection2.prepareStatement(query1);
				pst1.setString(1,Column1);
				pst1.setString(2,Column2);
				pst1.setString(3,Column3);
				pst1.execute();	
				pst1.close();
				}			
		rs.close();
		pst.close();
		/*String IsToTime1= textAreaTablePreview.getText().substring(0,1);*/
			} catch (Exception e) {
			e.printStackTrace();
		}		
	  }	
//test	
	protected void SQLCommand3()
	  {
		try{
			 lblTest.setText("");
			textAreaTablePreview.setText("");
			String sqltxt= textFieldSqlSelect.getText();
			Statement st = connection2.createStatement();
			st.executeUpdate(sqltxt);
			String query="select * from PCDTable2 where ID=3";
			//String query1 ="insert into PCDTable1 (ID,Timestamp,UnitID) values (?,?,?)";
			//String query1= textFieldSqlSelect.getText();
			PreparedStatement pst=connection2.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			for (int i = 1; i < columnCount + 1; i++ ) {
				String name = rsmd.getColumnName(i);
				if(i<columnCount)
				{
				textAreaTablePreview.append(name+",");
				}
				if(i==columnCount){
					textAreaTablePreview.append(name);
				}
			   }
			TableColumnNames= textAreaTablePreview.getText();
			 lblTest.setText(strToTimeSQL);			
		}catch(Exception e)
		{
		JOptionPane.showMessageDialog(null, e);						
		}
	  }
	public Connection dbConnector3()
	{
	try{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		Connection conn3 = DriverManager.getConnection("jdbc:odbc:db1", "", "");
		//Connection conn3 = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" + "C:\\EmployeeData.mdb", "", "");
	//Connection conn2 = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\istvan.farkas\\Documents\\Tel Aviv\\Java Projekt\\Tutorial\\TestData.sqlite");
	return conn3;
	}catch(Exception e)
	{
	JOptionPane.showMessageDialog(null, e);	
	return null;
	}
	}
	
		public Connection dbConnector()
		{
		try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "diag", "Siemens1234");
		return conn;
		}catch(Exception e)
		{
		JOptionPane.showMessageDialog(null, e);	
		return null;
		}
		}
		
		public Connection dbConnector1()
		{
		try{
		Class.forName("org.sqlite.JDBC");
		//Class.forName("oracle.jdbc.driver.OracleDriver");
		//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		//Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "diag", "Siemens1234");
		//Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\istvan.farkas\\Documents\\Tel Aviv\\Java Projekt\\EmployeeData.sqlite");
		Connection conn1 = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\istvan.farkas\\Documents\\Tel Aviv\\Java Projekt\\Tutorial\\PCDData.sqlite");
		//Connection conn2 = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\istvan.farkas\\Documents\\Tel Aviv\\Java Projekt\\Tutorial\\TestData.sqlite");
		//JOptionPane.showMessageDialog(null, "Connection Successful");	
		return conn1;
		}catch(Exception e)
		{
		JOptionPane.showMessageDialog(null, e);	
		return null;
		}
		}
		
		public Connection dbConnector2()
		{
		try{
		Class.forName("org.sqlite.JDBC");
		Connection conn2 = DriverManager.getConnection("jdbc:sqlite:"+filenameSQL);
		//Connection conn2 = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\istvan.farkas\\Documents\\Tel Aviv\\Java Projekt\\Tutorial\\TestData.sqlite");
		return conn2;
		}catch(Exception e)
		{
		JOptionPane.showMessageDialog(null, e);	
		return null;
		}
		}
	//Main method
	/*
	 * 
	 */
	public BBTools_Backup2() {		
		initialize();
		update(24*60);	
		update();		
		connection=dbConnector();
		connection1=dbConnector1();
		connection2=dbConnector2();
		//connection3=dbConnector3();
		Counter();
	}	
		
	//Initialize function
	private void initialize() {
		
		//Main Frame make
		frame = new JFrame();
		frame.setBounds(100, 100, 858, 676);
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
		//textFieldSqlSelect.setText("select * from PCD_COMMONDIAGNOSTICS where ID>=2 and ID<=22");
		textFieldSqlSelect.setText("select * from PCDTable3");
		//textFieldSqlSelect.setText("insert into Playlist VALUES(5,5,5,5)");
		
		JButton btnSqlExecute = new JButton("Execute");
		btnSqlExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					textAreaTablePreview.setText("");
					String sqltxt= textFieldSqlSelect.getText();
					//String query="select * from EmployeeInfo";
					//INSERT INTO PCDTable (ID,Timestamp,UnitID) VALUES(6,2,3)
					//PreparedStatement pst=connection1.prepareStatement(sqltxt);
					//ResultSet rs=pst.executeQuery();
					//original
					//lblTest.setText(textFieldSqlSelect.getText().substring(1,5));
					if(textFieldSqlSelect.getText().substring(1,6).equals("nsert"))
					{						
					Statement st = connection.createStatement();
					st.executeUpdate(sqltxt);
					}					
					if(textFieldSqlSelect.getText().substring(1,6).equals("elect"))
					{						
					PreparedStatement pst=connection.prepareStatement(sqltxt);
					ResultSet rs=pst.executeQuery();
					while(rs.next())
					{
						/*textAreaTablePreview.append(rs.getString("ID")+";");						
						textAreaTablePreview.append(rs.getString("Timestamp")+";");						
						textAreaTablePreview.append(rs.getString("UnitID")+"\n");*/
						//textAreaTablePreview.append(rs.getString("ID")+";");
						// textAreaTablePreview.append(rs.getString("TIMESTAMP")+";");
						// textAreaTablePreview.append(rs.getString(("TIMESTAMP").toString())+";");
						 textAreaTablePreview.append(rs.getString("PLCNODEID")+";");
						 textAreaTablePreview.append(rs.getString("UNITID")+";");
						 textAreaTablePreview.append(rs.getString("FUNCTIONID")+";");
						 textAreaTablePreview.append(rs.getString("EVENT_1")+";");
						 textAreaTablePreview.append(rs.getString("EVENT_2")+";");
						 textAreaTablePreview.append(rs.getString("EVENT_3")+";");
						 textAreaTablePreview.append(rs.getString("EVENT_4")+";");
						 textAreaTablePreview.append(rs.getString("EVENT_5")+";");
						 textAreaTablePreview.append(rs.getString("EVENT_6")+";");
						 textAreaTablePreview.append(rs.getString("EVENT_7")+";");
						 textAreaTablePreview.append(rs.getString("EVENT_8")+";");
						 textAreaTablePreview.append(rs.getString("EVENT_9")+";");
						 textAreaTablePreview.append(rs.getString("EVENT_10")+";");
						 textAreaTablePreview.append(rs.getString("EVENT_11")+";");
						 textAreaTablePreview.append(rs.getString("EVENT_12")+"\n");
						
						// txtID = rs.getString("ID");
						
						// txtTIMESTAMP = rs.getString("TIMESTAMP");
						 txtPLCNODEID = rs.getString("PLCNODEID");
						 txtUNITID = rs.getString("UNITID");
						 txtFUNCTIONID = rs.getString("FUNCTIONID");
						 txtEVENT_1 = rs.getString("EVENT_1");
						 txtEVENT_2 = rs.getString("EVENT_2");
						 txtEVENT_3 = rs.getString("EVENT_3");
						 txtEVENT_4 = rs.getString("EVENT_4");
						 txtEVENT_5 = rs.getString("EVENT_5");
						 txtEVENT_6 = rs.getString("EVENT_6");
						 txtEVENT_7 = rs.getString("EVENT_7");
						 txtEVENT_8 = rs.getString("EVENT_8");
						 txtEVENT_9 = rs.getString("EVENT_9");
						 txtEVENT_10 = rs.getString("EVENT_10");
						 txtEVENT_11 = rs.getString("EVENT_11");
						 txtEVENT_12 = rs.getString("EVENT_12");

						 String query1 ="insert into PCDTable3 (PLCNODEID,UNITID,FUNCTIONID,EVENT_1,EVENT_2,EVENT_3,EVENT_4,EVENT_5,EVENT_6,EVENT_7,EVENT_8,EVENT_9,EVENT_10,EVENT_11,EVENT_12) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
							
						// String query1 ="insert into PCDTable3 (ID,PLCNODEID,UNITID,FUNCTIONID,EVENT_1,EVENT_2,EVENT_3,EVENT_4,EVENT_5,EVENT_6,EVENT_7,EVENT_8,EVENT_9,EVENT_10,EVENT_11,EVENT_12) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						// String query1 ="insert into PCDTable2 (ID,TIMESTAMP,PLCNODEID,UNITID,FUNCTIONID,EVENT_1,EVENT_2,EVENT_3,EVENT_4,EVENT_5,EVENT_6,EVENT_7,EVENT_8,EVENT_9,EVENT_10,EVENT_11,EVENT_12) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						PreparedStatement pst1 = connection2.prepareStatement(query1);
						 //pst1.setString(1,txtID);
						 //pst1.setString(2,txtTIMESTAMP);
						 pst1.setString(1,txtPLCNODEID);
						 pst1.setString(2,txtUNITID);
						 pst1.setString(3,txtFUNCTIONID);
						 pst1.setString(4,txtEVENT_1);
						 pst1.setString(5,txtEVENT_2);
						 pst1.setString(6,txtEVENT_3);
						 pst1.setString(7,txtEVENT_4);
						 pst1.setString(8,txtEVENT_5);
						 pst1.setString(9,txtEVENT_6);
						 pst1.setString(10,txtEVENT_7);
						 pst1.setString(11,txtEVENT_8);
						 pst1.setString(12,txtEVENT_9);
						 pst1.setString(13,txtEVENT_10);
						 pst1.setString(14,txtEVENT_11);
						 pst1.setString(15,txtEVENT_12);
						 pst1.execute();	
						 /*
						 String query2 ="insert into PTRTable2 (ID,TIMESTAMP,PLCNOEID,UNITID,FUNCTIONID,TULID,GLOBALID,TRAYID,LOADINGSTATE,TRANSPORTUNITTYPE,DESTINATIONID,ROUTINGTABLEID,DESTINATIONMODE,PROBLEMDESTINATION,TRANSPORTGOODSID,HBSCONTROL1,HBSCONTROL2,HBSLEVEL,HBSRESULT,EVENTHOST,TUSTATE1,SPARE02,SPARE03,SPARE04,SPARE05,AGEVALUE,ADDINFO02,ADDINFO03,ADDINFO04,ADDINFO05,ADDINFO06,ADDINFO07,ADDINFO08,ADDINFO09,ADDINFO10) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						 PreparedStatement pst2 = connection2.prepareStatement(query2);
						 txtIDPTR = rs.getString("ID");
						 txtTIMESTAMPPTR = rs.getString("TIMESTAMP");
						 txtPLCNOEID  = rs.getString("PLCNOEID ");
						 txtUNITIDPTR = rs.getString("UNITID");
						 txtFUNCTIONIDPTR = rs.getString("FUNCTIONID");
						 txtTULID = rs.getString("TULID");
						 txtGLOBALID = rs.getString("GLOBALID");
						 txtTRAYID = rs.getString("TRAYID");
						 txtLOADINGSTATE = rs.getString("LOADINGSTATE");
						 txtTRANSPORTUNITTYPE = rs.getString("TRANSPORTUNITTYPE");
						 txtDESTINATIONID = rs.getString("DESTINATIONID");
						 txtROUTINGTABLEID = rs.getString("ROUTINGTABLEID");
						 txtDESTINATIONMODE = rs.getString("DESTINATIONMODE");
						 txtPROBLEMDESTINATION = rs.getString("PROBLEMDESTINATION");
						 txtTRANSPORTGOODSID = rs.getString("TRANSPORTGOODSID");
						 txtHBSCONTROL1 = rs.getString("HBSCONTROL1");
						 txtHBSCONTROL2 = rs.getString("HBSCONTROL2");
						 txtHBSLEVEL = rs.getString("HBSLEVEL");
						 txtHBSRESULT = rs.getString("HBSRESULT");
						 txtEVENTHOST = rs.getString("EVENTHOST");
						 txtTUSTATE1 = rs.getString("TUSTATE1");
						 txtSPARE02 = rs.getString("SPARE02");
						 txtSPARE03 = rs.getString("SPARE03");
						 txtSPARE04 = rs.getString("SPARE04");
						 txtSPARE05 = rs.getString("SPARE05");
						 txtAGEVALUE = rs.getString("AGEVALUE");
						 txtADDINFO01 = rs.getString("ADDINFO01");
						 txtADDINFO02 = rs.getString("ADDINFO02");
						 txtADDINFO03 = rs.getString("ADDINFO03");
						 txtADDINFO04 = rs.getString("ADDINFO04");
						 txtADDINFO05 = rs.getString("ADDINFO05");
						 txtADDINFO06 = rs.getString("ADDINFO06");
						 txtADDINFO07 = rs.getString("ADDINFO07");
						 txtADDINFO08 = rs.getString("ADDINFO08");
						 txtADDINFO09 = rs.getString("ADDINFO09");
						 txtADDINFO10 = rs.getString("ADDINFO10");
						 pst1.setString(1,txtIDPTR);
						 pst1.setString(2,txtTIMESTAMPPTR);
						 pst1.setString(3,txtPLCNOEID);
						 pst1.setString(4,txtUNITIDPTR);
						 pst1.setString(5,txtFUNCTIONIDPTR);
						 pst1.setString(6,txtTULID);
						 pst1.setString(7,txtGLOBALID);
						 pst1.setString(8,txtTRAYID);
						 pst1.setString(9,txtLOADINGSTATE);
						 pst1.setString(10,txtTRANSPORTUNITTYPE);
						 pst1.setString(11,txtDESTINATIONID);
						 pst1.setString(12,txtROUTINGTABLEID);
						 pst1.setString(13,txtDESTINATIONMODE);
						 pst1.setString(14,txtPROBLEMDESTINATION);
						 pst1.setString(15,txtTRANSPORTGOODSID);
						 pst1.setString(16,txtHBSCONTROL1);
						 pst1.setString(17,txtHBSCONTROL2);
						 pst1.setString(18,txtHBSLEVEL);
						 pst1.setString(19,txtHBSRESULT);
						 pst1.setString(20,txtEVENTHOST);
						 pst1.setString(21,txtTUSTATE1);
						 pst1.setString(22,txtSPARE02);
						 pst1.setString(23,txtSPARE03);
						 pst1.setString(24,txtSPARE04);
						 pst1.setString(25,txtSPARE05);
						 pst1.setString(26,txtAGEVALUE);
						 pst1.setString(27,txtADDINFO01);
						 pst1.setString(28,txtADDINFO02);
						 pst1.setString(29,txtADDINFO03);
						 pst1.setString(30,txtADDINFO04);
						 pst1.setString(31,txtADDINFO05);
						 pst1.setString(32,txtADDINFO06);
						 pst1.setString(33,txtADDINFO07);
						 pst1.setString(34,txtADDINFO08);
						 pst1.setString(35,txtADDINFO09);
						 pst1.setString(36,txtADDINFO10);
*/
						 /*textAreaTablePreview.append(rs.getString("UnitID")+"\n");
							Column3 = rs.getString("UnitID");
							String query1 ="insert into PCDTable2 (ID,Timestamp,UnitID) VALUES(?,?,?)";
							PreparedStatement pst1 = connection2.prepareStatement(query1);
							pst1.setString(1,Column1);
							pst1.setString(2,Column2);
							pst1.setString(3,Column3);*/
						}							
				rs.close();
				pst.close();
				}
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
						filenameSQL = filename.replace("\\","\\\\");
						connection2=dbConnector2();
					}
				});	
		
		JLabel lblManualFrom = new JLabel("Manual Backup From:");
		lblManualFrom.setBounds(5, 81, 131, 16);
		frame.getContentPane().add(lblManualFrom);
		
		textFieldManualFrom = new JTextField();
		textFieldManualFrom.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				lblTest.setText(textFieldManualFrom.getText());
			}
		});
		textFieldManualFrom.setBounds(153, 78, 220, 22);
		frame.getContentPane().add(textFieldManualFrom);
		textFieldManualFrom.setColumns(10);
		strFromTime = textFieldManualFrom.getText();	      
		
		JLabel lblManualTo = new JLabel("To:");
		lblManualTo.setBounds(386, 83, 65, 16);
		frame.getContentPane().add(lblManualTo);
		
		textFieldManualTo = new JTextField();
		textFieldManualTo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				lblTest.setText(textFieldManualTo.getText());
			}
		});
		textFieldManualTo.setBounds(453, 78, 220, 22);
		frame.getContentPane().add(textFieldManualTo);
		textFieldManualTo.setColumns(10);
		strToTime = textFieldManualTo.getText();
		
		JButton btnManualBackup = new JButton("Manual Backup");
		btnManualBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {					
				/*String filename1=textFieldFileName.getText();
				try{
					FileWriter stream = new FileWriter(filename1);
					BufferedWriter out = new BufferedWriter(stream);
					textAreaTablePreview.write(out);
					out.close();
					textAreaTablePreview.requestFocus();
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}*/
				
			}
		});
		btnManualBackup.setBounds(673, 77, 132, 25);
		frame.getContentPane().add(btnManualBackup);
		
		lblAutoBackupWhen = new JLabel("Auto Backup When:");
		lblAutoBackupWhen.setBounds(5, 110, 140, 16);
		frame.getContentPane().add(lblAutoBackupWhen);
		
		textFieldAutoWhen = new JTextField();
		textFieldAutoWhen.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				lblTest.setText(textFieldAutoWhen.getText());
			}
		});
		textFieldAutoWhen.setBounds(153, 107, 220, 22);
		frame.getContentPane().add(textFieldAutoWhen);
		textFieldAutoWhen.setColumns(10);
		strAutoWhen = textFieldAutoWhen.getText();
		
		lblAutoLast = new JLabel("Time Amount:");
		lblAutoLast.setBounds(372, 110, 85, 16);
		frame.getContentPane().add(lblAutoLast);
		
		textFieldAutoLast = new JTextField();
		textFieldAutoLast.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				lblTest.setText(textFieldAutoLast.getText());
			}
		});
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
		scrollPane.setBounds(5, 276, 800, 342);
		frame.getContentPane().add(scrollPane);
		
		textAreaTablePreview = new JTextArea();
		scrollPane.setViewportView(textAreaTablePreview);
		
		//Test Button
		/*
		 * 
		 */
		lblTest = new JLabel("Test");
		lblTest.setBounds(5, 224, 800, 39);
		frame.getContentPane().add(lblTest);
		
		btnTest = new JButton("Test Button");
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			//	SQLCommand1();
				//SQLCommand2();
				//SQLCommand1();
				//update();
				genSQLManual();
				}
		});		
		btnTest.setBounds(673, 196, 132, 25);
		frame.getContentPane().add(btnTest);
		
		txtTest = new JTextField();
		txtTest.setBounds(5, 197, 615, 21);
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
