import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.SystemColor;

public class Bookstore {

	private JFrame frame;
	private JTextField txtname;
	private JTextField txtedition;
	private JTextField txtprice;
	private JTextField txtauthor;
	private JTable table;
	private JTextField txtbid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Bookstore window = new Bookstore();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Bookstore() {
		initialize(); 		// Initialize the app
		Connect();			// Connect to database
		table_load(); 		// Load book table
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	/* 
	 Connection method to MySQL database.
	 */
	
	public void Connect()
	{
		// Specify the connection path
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/bookstore","root","");
		}
		catch (ClassNotFoundException ex)
		{
		}
		catch (SQLException ex)
		{
		}
	}
	
	public void table_load() {
		try {
			pst = con.prepareStatement("select * from books");
			rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 826, 474);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("My Bookstore");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 32));
		lblNewLabel.setBounds(295, 37, 266, 28);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 79, 406, 192);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Book Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 36, 96, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Edition");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(10, 72, 96, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Price");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(10, 149, 96, 14);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Author");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_1.setBounds(10, 111, 96, 14);
		panel.add(lblNewLabel_1_1_1);
		
		txtname = new JTextField();
		txtname.setBounds(116, 35, 280, 20);
		panel.add(txtname);
		txtname.setColumns(10);
		
		txtedition = new JTextField();
		txtedition.setColumns(10);
		txtedition.setBounds(116, 71, 280, 20);
		panel.add(txtedition);
		
		txtprice = new JTextField();
		txtprice.setColumns(10);
		txtprice.setBounds(116, 148, 280, 20);
		panel.add(txtprice);
		
		txtauthor = new JTextField();
		txtauthor.setColumns(10);
		txtauthor.setBounds(116, 110, 280, 20);
		panel.add(txtauthor);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name, edition, author, price;
				
				// Assign the typed values to the respective atributes
				
				name = txtname.getText();
				edition = txtedition.getText();
				author = txtauthor.getText();
				price = txtprice.getText();
				
				
				try {
					pst = con.prepareStatement("insert into books(name,edition,author,price)values(?,?,?,?)");
					pst.setString(1, name);
					pst.setString(2, edition);
					pst.setString(3, author);
					pst.setString(4, price);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Record Added!");
					table_load();
					txtname.setText("");
					txtedition.setText("");
					txtauthor.setText("");
					txtprice.setText("");
					txtname.requestFocus();		
				}
				
				catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(10, 282, 114, 41);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
				
			}
		});
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExit.setBounds(684, 383, 114, 41);
		frame.getContentPane().add(btnExit);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtname.setText("");
				txtedition.setText("");
				txtauthor.setText("");
				txtprice.setText("");
				txtname.requestFocus();	
				
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnClear.setBounds(140, 282, 114, 41);
		frame.getContentPane().add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(423, 86, 375, 286);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 334, 406, 90);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Book ID");
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2_1.setBounds(10, 41, 96, 14);
		panel_1.add(lblNewLabel_1_2_1);
		
		txtbid = new JTextField();
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
			
				try {
					
					String id = txtbid.getText();
						
						pst = con.prepareStatement("select name,edition,author,price from books where id = ?");
						pst.setString(1, id);
						ResultSet rs = pst.executeQuery();
					
						if(rs.next()==true) {
							
							String name = rs.getString(1);
							String edition = rs.getString(2);
							String author = rs.getString(3);
							String price = rs.getString(4);
							

							JOptionPane.showMessageDialog(null,"Name: " + name +
																"\nEdition: " + edition +
																"\nAuthor: " + author +
																"\nPrice: " + price,"Search results", JOptionPane.INFORMATION_MESSAGE);
							
							txtname.setText(name);
							txtedition.setText(edition);
							txtauthor.setText(author);
							txtprice.setText(price);
							
						}
						else {
							
							txtname.setText("");
							txtedition.setText("");
							txtauthor.setText("");
							txtprice.setText("");
						}
				}
				
			catch (SQLException ex)
			{
			}
			
			}
		});
		txtbid.setColumns(10);
		txtbid.setBounds(116, 40, 280, 20);
		panel_1.add(txtbid);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String name,edition,author,price,bid;
				
				
				name = txtname.getText();
				edition = txtedition.getText();
				author = txtauthor.getText();
				price = txtprice.getText();
				bid  = txtbid.getText();
				
				 try {
						pst = con.prepareStatement("update books set name= ?,edition=?,author=?,price=? where id =?");
						pst.setString(1, name);
			            pst.setString(2, edition);
			            pst.setString(3, author);
			            pst.setString(4, price);
			            pst.setString(5, bid);
			            pst.executeUpdate();
			            JOptionPane.showMessageDialog(null, "Record Updated!");
			            table_load();
			           
			            txtname.setText("");
			            txtedition.setText("");
			            txtauthor.setText("");
			            txtprice.setText("");
			            txtname.requestFocus();
					}
 
		            catch (SQLException e1) {
						
						e1.printStackTrace();
					}
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdate.setBounds(436, 383, 114, 41);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bid;
				bid  = txtbid.getText();
				
				 try {
						pst = con.prepareStatement("delete from books where id =?");
				
			            pst.setString(1, bid);
			            pst.executeUpdate();
			            JOptionPane.showMessageDialog(null, "Record Deleted!");
			            table_load();
			           
			            txtname.setText("");
			            txtedition.setText("");
			            txtauthor.setText("");
			            txtprice.setText("");
			            txtname.requestFocus();
					}
	 
		            catch (SQLException e1) {
						
						e1.printStackTrace();
					}
				
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(560, 383, 114, 41);
		frame.getContentPane().add(btnDelete);
	}
}
