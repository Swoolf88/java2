import java.sql.*;
import javax.swing.*;


public class sqliteConnection {
	Connection conn=null;
public static Connection dbConnector()
{
try{
Class.forName("org.sqlite.JDBC");
//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\istvan.farkas\\Documents\\Tel Aviv\\Java Projekt\\EmployeeData.sqlite");
//Connection conn = DriverManager.getConnection("jdbc:odbc:C:\\Users\\istvan.farkas\\Documents\\Tel Aviv\\Java Projekt\\EmployeeData.accdb");
//Connection conn = DriverManager.getConnection("jdbc:odbc:Data", "", "");

//JOptionPane.showMessageDialog(null, "Connection1 Successful");	
return conn;
}catch(Exception e)
{
//JOptionPane.showMessageDialog(null, e);	
return null;
}
}
}