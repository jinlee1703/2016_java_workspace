package Project_Frame;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class ledgerSelect extends JFrame {
	JTabbedPane tab = new JTabbedPane(JTabbedPane.LEFT);
	String[] header = {"��¥","����","�׸�","��������","�ݾ�","�޸�"};
	DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table1 = new JTable(model);
	JTable table2 = new JTable(model);
	JScrollPane scroll1 = new JScrollPane(table1);
	JScrollPane scroll2 = new JScrollPane(table2);
	ImageIcon icon1 = new ImageIcon(getClass().getClassLoader().getResource("ToolIcon/����.png"));
	ImageIcon icon2 = new ImageIcon(getClass().getClassLoader().getResource("ToolIcon/����.png"));
	
	public ledgerSelect() {
		setTitle("����&����");
		setSize(700, 500);
		setLocationRelativeTo(null);
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.add(scroll1);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.add(scroll2);
		
		JPanel p3 = new JPanel();
		
		tab.addTab("����", icon1, p1);
		tab.addTab("����", icon2, p2);
		tab.addTab("����", p3);
		
		Init();
		
		tab.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				try {
					model.setNumRows(0);
					
					String d = null;
					
					if(tab.getSelectedIndex()==0) {
						d = "����";
					} else if(tab.getSelectedIndex()==1) {
						d = "����";
					} else {
						dispose();
						new Main();
					}
					
					ResultSet rs = DBInterface.Stmt.executeQuery("select * from ledger where memberid='"+Login.id.getText()+"' and division='"+d+"'");
					
					String[] newRow = new String[6];
					
					while(rs.next()) {
						for(int i=0; i<6; i++) {
							newRow[i] = rs.getString(i+2);
						}
						
						model.addRow(newRow);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		add(tab);
		
		try {
			FileSave();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void Init() {
		try {
			model.setNumRows(0);
			
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from ledger where memberid='"+Login.id.getText()+"' and division='����'");
			
			String[] newRow = new String[6];
			
			while(rs.next()) {
				for(int i=0; i<6; i++) {
					newRow[i] = rs.getString(i+2);
				}
				
				model.addRow(newRow);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void FileSave() throws Exception {
		FileDialog fd = new FileDialog(this, "��������", FileDialog.SAVE);
		fd.setVisible(true);
		
		FileWriter fw = new FileWriter(fd.getDirectory()+fd.getFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		String str = "���̵� : "+Login.id.getText();
		bw.write(str); bw.newLine(); bw.newLine();
		
		str = "��¥\t����\t�׸�\t��������\t�ݾ�\t�޸�";
		bw.write(str); 
		
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from ledger where memberid='"+Login.id.getText()+"'");
		
		while(rs.next()) {
			bw.newLine();
			str = rs.getString(2) + "\t" + rs.getString(3) + "\t" + rs.getString(4) + "\t" + rs.getString(5)+ "\t" + rs.getString(6) + "\t" + rs.getString(7);
			bw.write(str);
		}
		bw.close();
	}
}
