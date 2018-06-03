import java.sql.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import oracle.jdbc.OracleConnection;

public class sqliteConnection1 {
	Connection conn=null;
public static Connection dbConnector()
{
try{
//Class.forName("org.sqlite.JDBC");
//Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\istvan.farkas\\Documents\\Tel Aviv\\Java Projekt\\EmployeeData.sqlite");
//Connection conn = DriverManager.getConnection("jdbc:odbc:C:\\Users\\istvan.farkas\\Documents\\Tel Aviv\\Java Projekt\\EmployeeData.accdb");
//Connection conn = DriverManager.getConnection("jdbc:odbc:Data", "", "");
//Connection conn = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + "C:\\EmployeeData.mdb", "", "");
DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
Connection conn = (OracleConnection)DriverManager.getConnection("jdbc:oracle:thin:@10.244.10.1:1521:xe", "diag", "Siemens1234");
//Connection conn = (OracleConnection)DriverManager.getConnection("jdbc:oracle:diag:@//T3r-TDS:1521/XE", "diag", "Siemens1234");


//JOptionPane.showMessageDialog(null, "Connection1 Successful");	
return conn;
}catch(Exception e)
{
//JOptionPane.showMessageDialog(null, e);	
return null;
}
}
}

