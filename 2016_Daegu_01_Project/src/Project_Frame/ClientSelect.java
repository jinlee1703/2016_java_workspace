package Project_Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class ClientSelect extends JFrame implements ActionListener {
	JButton btn1 = new JButton("고객정보수정");
	JButton btn2 = new JButton("고객삭제");
	JButton btn3 = new JButton("돌아가기");
	static String[] header = {"고객번호","고객ID","고객명","전화번호","주소","가입일"};
	static DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	static JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	
	public ClientSelect() {
		setTitle("고객관리");
		setSize(470, 520);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout());
		p1.add(btn1); p1.add(btn2); p1.add(btn3);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBorder(BorderFactory.createTitledBorder("고객리스트"));
		p2.add(scroll);
		
		basePanel.add(p1, BorderLayout.NORTH);
		basePanel.add(p2, BorderLayout.CENTER);
		
		add(basePanel);
		
		TableSetting();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
		
		if(btn==btn1) {
			new ClientUpdate();
		} else if(btn==btn2) {
			if(table.getSelectedRow()==-1) {
				JOptionPane.showMessageDialog(null, "실패하였습니다.", "삭제 실패!", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					DBInterface.Stmt.execute("DELETE FROM `world000`.`client` WHERE `code`='"+table.getValueAt(table.getSelectedRow(), 0)+"';");
					
					JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다.");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if(btn==btn3) {
			this.dispose();
		}
	}
	
	public static void TableSetting() {
		try {
			model.setNumRows(0);
			
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from client");
			
			String[] newRow = new String[6];
			
			while(rs.next()) {
				newRow[0] = rs.getString(1);
				newRow[1] = rs.getString(2);
				newRow[2] = rs.getString(4);
				newRow[3] = rs.getString(5);
				newRow[4] = rs.getString(6);
				newRow[5] = rs.getString(7);
				
				model.addRow(newRow);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(center);
		table.getColumnModel().getColumn(1).setCellRenderer(center);
		table.getColumnModel().getColumn(2).setCellRenderer(center);
	}
}
