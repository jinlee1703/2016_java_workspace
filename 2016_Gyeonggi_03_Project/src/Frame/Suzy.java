package Frame;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class Suzy extends JFrame {
	JTabbedPane tab = new JTabbedPane(JTabbedPane.LEFT);
	String[] header = {"��¥","����","�׸�","��������","�ݾ�","�޸�"};
	DefaultTableModel model1 = new DefaultTableModel(header, 0);
	DefaultTableModel model2 = new DefaultTableModel(header, 0);
	JTable table1 = new JTable(model1);
	JTable table2 = new JTable(model2);
	JScrollPane sc1 = new JScrollPane(table1);
	JScrollPane sc2 = new JScrollPane(table2);
	
	public Suzy() {
		setTitle("����&����");
		setSize(800, 400);
		setLocationRelativeTo(null);
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.add(sc1);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p1.add(sc2);
		
		JPanel p3 = new JPanel();
		
		String path1 = System.getProperty("user.dir")+"\\res\\����.png";
		path1.replace('\\', '/');
		
		String path2 = System.getProperty("user.dir")+"\\res\\����.png";
		path2.replace('\\', '/');
		
		tab.addTab("����", new ImageIcon(path1), p1);
		tab.addTab("����", new ImageIcon(path2), p2);
		tab.addTab("����", p3);
		
		TableSetting();
		
		add(tab);
		
		tab.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(tab.getSelectedIndex()==2) {
					dispose();
				}
				
				if(e.getClickCount()==2) {
					if(tab.getSelectedIndex()==0) {
						if(model1.getRowCount()!=0) {
							try {
								FileSave("����");
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					} else {
						if(model2.getRowCount()!=0) {
							try {
								FileSave("����");
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				new Main();
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void TableSetting() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from ledger where memberid='"+Login.id.getText()+"' and division='����'");
			String newRow[] = new String[6];
			while(rs.next()) {
				newRow[0] = rs.getString(2);
				newRow[1] = rs.getString(3);
				newRow[2] = rs.getString(4);
				newRow[3] = rs.getString(5);
				newRow[4] = rs.getString(6);
				newRow[5] = rs.getString(7);
				model1.addRow(newRow);
			}
			rs = DBInterface.Stmt.executeQuery("select * from ledger where memberid='"+Login.id.getText()+"' and division='����'");
			while(rs.next()) {
				newRow[0] = rs.getString(2);
				newRow[1] = rs.getString(3);
				newRow[2] = rs.getString(4);
				newRow[3] = rs.getString(5);
				newRow[4] = rs.getString(6);
				newRow[5] = rs.getString(7);
				model2.addRow(newRow);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void FileSave(String sz) throws Exception {
		int res = JOptionPane.showConfirmDialog(null, "���Գ����� �������ðڽ��ϱ�?", "��������", JOptionPane.YES_NO_OPTION);
		
		if(res==JOptionPane.YES_OPTION) {
			FileDialog fd = new FileDialog(this, "��������", FileDialog.SAVE);
			fd.setFile(Login.id.getText()+" ����"+sz+"����");
			fd.setVisible(true);
			
			FileWriter fw = new FileWriter(fd.getDirectory()+fd.getFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			String str="���̵� : "+Login.id.getText();
			bw.write(str);
			bw.newLine();
			
			str="��¥\t����\t�׸�\t��������\t�ݾ�\t�޸�";
			bw.write(str);
			
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from ledger where memberid='"+Login.id.getText()+"' and division='"+sz+"'");
			while(rs.next()) {
				bw.newLine();
				str=rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7);
				bw.write(str);
			}
			bw.close();
		}
	}
}
