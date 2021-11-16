package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class LogSelect extends JFrame implements ActionListener {
	String[] header = {"날짜","내용"};
	DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	JButton[] btn = new JButton[3];
	String[] bn = {"추가","수정","삭제"};
	
	public LogSelect() {
		Init();
		setSize(400, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.add(scroll, BorderLayout.CENTER);
		
		JPanel p2 = new JPanel(new FlowLayout());
		p2.setBorder(new EmptyBorder(0, 0, 10, 0));
		for(int i=0; i<bn.length; i++) {
			btn[i] = new JButton(bn[i]);
			btn[i].setPreferredSize(new Dimension(80, 30));
			btn[i].addActionListener(this);
			p2.add(btn[i]);
		}
		
		basePanel.add(p1, BorderLayout.CENTER);
		basePanel.add(p2, BorderLayout.SOUTH);
		
		add(basePanel);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn[0]) {
			Date d = new Date();
			SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
			String res = JOptionPane.showInputDialog(null, s.format(d)+" 일지 추가", "추가", JOptionPane.PLAIN_MESSAGE);
			
			if(res!=null) {
				try {
					DBInterface.Stmt.execute("INSERT INTO `testproject000`.`log` (`id`, `time`, `content`) VALUES ('"+Login.text1.getText()+"', '"+s.format(d)+"', '"+res+"');");
					
					Init();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if(table.getSelectedRow()==-1) {
			JOptionPane.showMessageDialog(null, "항목을 선택하세요");
		} else if(bt==btn[1]) {
			String res = (String) JOptionPane.showInputDialog(null, table.getValueAt(table.getSelectedRow(), 0)+" 일지 수정", "수정", JOptionPane.PLAIN_MESSAGE, null, null, table.getValueAt(table.getSelectedRow(), 1));
			
			if(res!=null) {
				try {
					DBInterface.Stmt.execute("UPDATE `testproject000`.`log` SET `content`='"+res+"' WHERE `id`='"+Login.text1.getText()+"' and`time`='"+table.getValueAt(table.getSelectedRow(), 0)+"';");
					
					Init();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if(bt==btn[2]) {
			int res = JOptionPane.showConfirmDialog(null, table.getValueAt(table.getSelectedRow(), 0)+"일지를 삭제하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION);
			
			if(res==JOptionPane.OK_OPTION) {
				try {
					DBInterface.Stmt.execute("DELETE FROM `testproject000`.`log` WHERE `id`='"+Login.text1.getText()+"' and`time`='"+table.getValueAt(table.getSelectedRow(), 0)+"';");
					
					Init();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void Init() {
		try {
			model.setNumRows(0);
			
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from employee where id='"+Login.text1.getText()+"'");
			rs.next(); setTitle(rs.getString(3)+" 일지");
			
			rs = DBInterface.Stmt.executeQuery("select * from log where id='"+Login.text1.getText()+"'");
			
			String[] newRow = new String[2];
			
			while(rs.next()) {
				newRow[0] = rs.getString(2);
				newRow[1] = rs.getString(3);
				
				model.addRow(newRow);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		table.getColumnModel().getColumn(1).setMaxWidth(300);
	}
}
