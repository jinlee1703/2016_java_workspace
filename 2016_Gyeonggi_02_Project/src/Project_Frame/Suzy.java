package Project_Frame;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class Suzy extends JFrame {
	JTabbedPane tab = new JTabbedPane(JTabbedPane.LEFT);
	String[] header = {"날짜","구분","항목","결제수단","금액","메모"};
	DefaultTableModel model1 = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	DefaultTableModel model2 = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table1 = new JTable(model1);
	JTable table2 = new JTable(model2);
	JScrollPane s1 = new JScrollPane(table1);
	JScrollPane s2 = new JScrollPane(table2);
	
	public Suzy() {
		setTitle("수입&지출");
		setSize(800, 400);
		setLocationRelativeTo(null);
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.add(s1);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.add(s2);
		
		JPanel p3 = new JPanel(new BorderLayout());
		
		tab.addTab("수입", new ImageIcon(getClass().getClassLoader().getResource("수입.png")), p1);
		tab.addTab("지출", new ImageIcon(getClass().getClassLoader().getResource("지출.png")), p2);
		tab.addTab("종료", p3);
		
		tab.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				if(tab.getSelectedIndex()==2) {
					dispose();
					new Main();
				}
			}
		});
		
		tab.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					if(tab.getSelectedIndex()==0 && model1.getRowCount()!=0) {
						int res = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION);
						
						if(res==JOptionPane.YES_OPTION) {
							try {
								FileSave("수입");
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					} else if(tab.getSelectedIndex()==1 && model1.getRowCount()!=0) {
						int res = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION);
						
						if(res==JOptionPane.YES_OPTION) {
							try {
								FileSave("지출");
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});
		
		add(tab);
		
		TableSetting();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void TableSetting() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from ledger where memberid='"+Login.id.getText()+"' and division='수입'");
			
			String[] newRow = new String[6];
			
			while(rs.next()) {
				for(int i=0; i<6; i++) {
					newRow[i] = rs.getString(i+2);
				}
				model1.addRow(newRow);
			}
			
			rs = DBInterface.Stmt.executeQuery("select * from ledger where memberid='"+Login.id.getText()+"' and division='지출'");
			
			while(rs.next()) {
				for(int i=0; i<6; i++) {
					newRow[i] = rs.getString(i+2);
				}
				model2.addRow(newRow);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void FileSave(String type) throws Exception {
		FileDialog fd = new FileDialog(this, "내보내기", FileDialog.SAVE);
		fd.setFile(Login.id.getText()+" 님의"+""+"내역");
		fd.setVisible(true);
		
		FileWriter fw = new FileWriter(fd.getDirectory()+fd.getFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		String str="아이디 : "+Login.id.getText();
		bw.write(str);
		bw.newLine();
		
		bw.write("날짜\t구분\t항목\t결제수단\t금액\t메모");
		
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from ledger where memberid='"+Login.id.getText()+"' and division='"+type+"'");
		
		while(rs.next()) {
			bw.newLine();
			str=rs.getString(2)+"\t"+rs.getString(3)+"\t"+rs.getString(4)+"\t"+rs.getString(5)+"\t"+rs.getString(6)+"\t"+rs.getString(7)+"\t";
			bw.write(str);
		}
		bw.close();
	}
}
