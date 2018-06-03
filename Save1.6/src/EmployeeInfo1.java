import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.ScrollPane;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JTable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

import javax.swing.*;

import net.proteanit.sql.DbUtils;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class EmployeeInfo1 extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JComboBox comboBoxName;
	private JList listName;
	private JComboBox comboBoxSelection;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmployeeInfo1 frame = new EmployeeInfo1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
Connection connection = null;
private JTextField textFieldEID;
private JTextField textFieldName;
private JTextField textFieldSurname;
private JTextField textFieldAge;
private JTextField textFieldSearch;
private JMenuBar menuBar;

public void refreshTable()
{
	try {
		String query ="select EID,Name,Surname,Age from EmployeeInfo";
		PreparedStatement pst = connection.prepareStatement(query);
		ResultSet rs = pst.executeQuery();
		table.setModel(DbUtils.resultSetToTableModel(rs));
		pst.close();
		rs.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
}

public void fillComboBox()
{
	try {
		String query="select * from EmployeeInfo";
		PreparedStatement pst=connection.prepareStatement(query);
		ResultSet rs=pst.executeQuery();
		while(rs.next())
		{
			comboBoxName.addItem(rs.getString("Name"));
		}		
	} catch (Exception e) {
		e.printStackTrace();
	}
}

public void loadList()
{
	try {
		String query="select * from EmployeeInfo";
		PreparedStatement pst=connection.prepareStatement(query);
		ResultSet rs=pst.executeQuery();
		DefaultListModel DLM=new DefaultListModel();
		
		while(rs.next())
		{
			DLM.addElement(rs.getString("Name"));
		}
		listName.setModel(DLM);
		pst.close();
		rs.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
}
	/**
	 * Create the frame.
	 */
	public EmployeeInfo1() {
		connection=sqliteConnection.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 719, 450);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnNew = new JMenu("New");
		mnNew.setIcon(new ImageIcon("C:\\Users\\istvan.farkas\\Documents\\Tel Aviv\\Java Projekt\\Tutorial\\ok.png"));
		mnFile.add(mnNew);
		
		JMenuItem mntmJavaProject = new JMenuItem("Java Project");
		mnNew.add(mntmJavaProject);
		
		JMenuItem mntmProject = new JMenuItem("Project");
		mnNew.add(mntmProject);
		
		JMenuItem mntmOpenFile = new JMenuItem("Open File");
		mnFile.add(mntmOpenFile);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mnFile.add(mntmClose);
		
		JMenuItem mntmCloseAll = new JMenuItem("Close All");
		mnFile.add(mntmCloseAll);
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JRadioButtonMenuItem rdbtnmntmRadioButton = new JRadioButtonMenuItem("Radio Button");
		mnEdit.add(rdbtnmntmRadioButton);
		
		JCheckBoxMenuItem chckbxmntmCheckbox = new JCheckBoxMenuItem("Checkbox");
		mnEdit.add(chckbxmntmCheckbox);
		
		JMenu mnSource = new JMenu("Source");
		menuBar.add(mnSource);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnLoadTable = new JButton("Table");
		btnLoadTable.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnLoadTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query ="select EID,Name,Surname,Age from EmployeeInfo";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					pst.close();
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		btnLoadTable.setBounds(6, 307, 91, 25);
		contentPane.add(btnLoadTable);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(237,51,452,311);
		contentPane.add(scrollPane);		
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				
				try {
					int row = table.getSelectedRow();
					String EID_=(table.getModel().getValueAt(row, 0)).toString();
					String query ="select EID,Name,Surname,Age from EmployeeInfo where EID='"+EID_+"' ";
					PreparedStatement pst = connection.prepareStatement(query);	
				
					ResultSet rs=pst.executeQuery();
					while(rs.next())
					{
						textFieldEID.setText(rs.getString("EID"));
						textFieldName.setText(rs.getString("Name"));
						textFieldSurname.setText(rs.getString("Surname"));
						textFieldAge.setText(rs.getString("Age"));
					}
					pst.close();
					//rs.close();				
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		table.setBounds(37, 71, 236, 151);
		scrollPane.setViewportView(table);
		
		textFieldEID = new JTextField();
		textFieldEID.setFont(new Font("Tahoma", Font.BOLD, 16));
		textFieldEID.setBounds(95, 51, 130, 22);
		contentPane.add(textFieldEID);
		textFieldEID.setColumns(10);
		
		textFieldName = new JTextField();
		textFieldName.setFont(new Font("Tahoma", Font.BOLD, 16));
		textFieldName.setBounds(95, 88, 130, 22);
		contentPane.add(textFieldName);
		textFieldName.setColumns(10);
		
		textFieldSurname = new JTextField();
		textFieldSurname.setFont(new Font("Tahoma", Font.BOLD, 16));
		textFieldSurname.setBounds(95, 123, 130, 22);
		contentPane.add(textFieldSurname);
		textFieldSurname.setColumns(10);
		
		textFieldAge = new JTextField();
		textFieldAge.setFont(new Font("Tahoma", Font.BOLD, 16));
		textFieldAge.setBounds(95, 158, 130, 22);
		contentPane.add(textFieldAge);
		textFieldAge.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query ="insert into EmployeeInfo (EID,Name,Surname,Age) values (?,?,?,?)";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, textFieldEID.getText());
					pst.setString(2, textFieldName.getText());
					pst.setString(3, textFieldSurname.getText());
					pst.setString(4, textFieldAge.getText());					
					pst.execute();					
					JOptionPane.showMessageDialog(null, "Data Saved");					
					pst.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				refreshTable();
			}
		});
		
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnSave.setBounds(6, 193, 91, 25);
		contentPane.add(btnSave);
		
		JLabel lblEid = new JLabel("EID");
		lblEid.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEid.setBounds(12, 54, 56, 16);
		contentPane.add(lblEid);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblName.setBounds(12, 93, 56, 16);
		contentPane.add(lblName);
		
		JLabel lblSurname = new JLabel("Surname");
		lblSurname.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblSurname.setBounds(12, 128, 85, 16);
		contentPane.add(lblSurname);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAge.setBounds(12, 157, 56, 25);
		contentPane.add(lblAge);
		
		JButton btnNewButton = new JButton("Update");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				try {
					String query ="Update EmployeeInfo set EID='"+textFieldEID.getText()+"' ,name='"+textFieldName.getText()+"' , surname='"+textFieldSurname.getText()+"' , age='"+textFieldAge.getText()+"' where EID='"+textFieldEID.getText()+"' ";
					PreparedStatement pst = connection.prepareStatement(query);			
					pst.execute();					
					JOptionPane.showMessageDialog(null, "Data Updated");					
					pst.close();		
				} catch (Exception e) {
					e.printStackTrace();
				}
				refreshTable();
			}
		});
		
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnNewButton.setBounds(6, 269, 91, 25);
		contentPane.add(btnNewButton);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action=JOptionPane.showConfirmDialog(null, "Do you really want to delete?","Delete",JOptionPane.YES_NO_OPTION);
				if(action==0){
				try {
					String query ="delete from EmployeeInfo where EID='"+textFieldEID.getText()+"' ";
					PreparedStatement pst = connection.prepareStatement(query);					
					pst.execute();					
					JOptionPane.showMessageDialog(null, "Data Deleted");					
					pst.close();					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				refreshTable();
				}
			}
		});
		
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnDelete.setBounds(6, 231, 91, 25);
		contentPane.add(btnDelete);
		
		comboBoxName = new JComboBox();
		comboBoxName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query ="select * from EmployeeInfo where name=? ";
					PreparedStatement pst = connection.prepareStatement(query);	
					pst.setString(1, (String)comboBoxName.getSelectedItem());
					ResultSet rs=pst.executeQuery();
					while(rs.next())
					{
						textFieldEID.setText(rs.getString("EID"));
						textFieldName.setText(rs.getString("Name"));
						textFieldSurname.setText(rs.getString("Surname"));
						textFieldAge.setText(rs.getString("Age"));
					}
					pst.close();					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		comboBoxName.setFont(new Font("Tahoma", Font.BOLD, 16));
		comboBoxName.setBounds(6, 13, 219, 28);
		contentPane.add(comboBoxName);
		
		listName = new JList();
		listName.setBounds(105, 193, 117, 169);
		contentPane.add(listName);
		
		textFieldSearch = new JTextField();
		textFieldSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				try {
					String selection= (String) comboBoxSelection.getSelectedItem();
					String query ="select * from EmployeeInfo where "+selection+"=? ";
					PreparedStatement pst = connection.prepareStatement(query);	
					pst.setString(1, textFieldSearch.getText());
					ResultSet rs=pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
//					while(rs.next())
//					{
//					
//					}
					pst.close();					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
			}
		});
		textFieldSearch.setBounds(487, 13, 202, 28);
		contentPane.add(textFieldSearch);
		textFieldSearch.setColumns(10);
		
		comboBoxSelection = new JComboBox();
		comboBoxSelection.setFont(new Font("Tahoma", Font.BOLD, 14));
		comboBoxSelection.setModel(new DefaultComboBoxModel(new String[] {"EID", "Name", "Surname", "Age"}));
		comboBoxSelection.setBounds(312, 13, 163, 28);
		contentPane.add(comboBoxSelection);
		
		refreshTable();
		fillComboBox();
		loadList();
	}
}