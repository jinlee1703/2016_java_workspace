package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.metal.MetalBorders.TableHeaderBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class ProtectorReportSelect extends JFrame implements ActionListener {
	JLabel title = new JLabel("성적관리");
	JButton[] btn = new JButton[4];
	String[] bn = {"학생등록","학생수정","학생삭제","닫기"};
	JPopupMenu menu = new JPopupMenu();
	JMenuItem it1 = new JMenuItem("학생등록");
	JMenuItem it2 = new JMenuItem("학생수정");
	JMenuItem it3 = new JMenuItem("학생삭제");
	
	public ProtectorReportSelect() {
		setTitle("학생관리");
		setSize(950, 600);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout());
		title.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		p1.add(title);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.add(scroll);
		
		JPanel p3 = new JPanel(new FlowLayout());
		for(int i=0; i<4; i++) {
			btn[i] = new JButton(bn[i]);
			btn[i].addActionListener(this);
			p3.add(btn[i]);
		}
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		add(p);
		
		TableInit();
		
		menu.add(it1);
		menu.add(it2);
		menu.add(it3);
		
		it1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new AdminInsert();
			}
		});
		
		it2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new AdminUpdate();
			}
		});
		
		it3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int result = JOptionPane.showConfirmDialog(null, table.getValueAt(table.getSelectedRow(), 1)+"님의 정보를 삭제하시겠습니까?", "학생삭제", JOptionPane.OK_CANCEL_OPTION);
				
				if(result==JOptionPane.OK_OPTION) {
					try {
						DBInterface.Stmt.execute("DELETE FROM `world000`.`admin` WHERE `Name`='"+table.getValueAt(table.getSelectedRow(), 1)+"';");
						
						TableInit();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(table.getSelectedRow()!=-1) {
					if(e.getButton()==3) {
						menu.show((Component) e.getSource(), e.getX(), e.getY());
					}
				}
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn[0]) {
			this.dispose();
			new AdminInsert();
		} else if(bt==btn[1]) {
			this.dispose();
			new AdminUpdate();
		} else if(bt==btn[2]) {
			if(table.getSelectedRow()!=-1) {
				int result = JOptionPane.showConfirmDialog(null, table.getValueAt(table.getSelectedRow(), 1)+"님의 정보를 삭제하시겠습니까?", "학생삭제", JOptionPane.OK_CANCEL_OPTION);
				
				if(result==JOptionPane.OK_OPTION) {
					try {
						DBInterface.Stmt.execute("DELETE FROM `world000`.`admin` WHERE `Name`='"+table.getValueAt(table.getSelectedRow(), 1)+"';");
						
						TableInit();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "삭제하실 라인을 선택해주세요.", "에러", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			this.dispose();
			new Login();
		}
	}
	
	public void TableInit() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from protector where ID='"+Login.text1.getText()+"'");
			String cn = null;
			if(rs.getString(6)=="전산일반") {
				cn = "Processing";
			} else if(rs.getString(6)=="회계일반") {
				cn = "Accounting";
			} else if(rs.getString(6)=="웹디자인") {
				cn = "Web";
			} else if(rs.getString(6)=="그래픽디자인") {
				cn = "Graphic";
			} else if(rs.getString(6)=="전자상거래") {
				cn = "Electronic";
			} else if(rs.getString(6)=="수행능력") {
				cn = "(appraisal.Onesemester+appraisal.twosemester)/2";
			}
			
			String[] header = {"학번","성명","주민번호","출신학교","연락처",rs.getString(6)};
			DefaultTableModel model = new DefaultTableModel(header, 0) {
				public boolean isCellEditable(int row, int column) {
					return false;
				};
			};
			JTable table = new JTable(model);
			JScrollPane scroll = new JScrollPane(table);
			
			model.setNumRows(0);
			
			rs = DBInterface.Stmt.executeQuery("SELECT report.Number,admin.Name,admin.Jumin,admin.School,admin.Phone,"+cn+" FROM report join admin on report.Jumin=admin.Jumin;");
			
			String[] newRow = new String[10];
			
			while(rs.next()) {
				for(int i=0; i<6; i++) {
					newRow[i] = rs.getString(i+1);
				}
				
				if(newRow[5]!="0") {
					model.addRow(newRow);
				}
			}
			
			DefaultTableCellRenderer center = new DefaultTableCellRenderer();
			center.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
			for(int i=0; i<6; i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(center);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
