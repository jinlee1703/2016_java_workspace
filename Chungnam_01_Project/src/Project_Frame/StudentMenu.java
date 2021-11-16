package Project_Frame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class StudentMenu extends JFrame implements ActionListener {
	JButton[] btn = new JButton[3];
	String[] bn = {"������û","������ȸ","�������"};
	
	public StudentMenu() {
		setTitle("�����ڸ޴�");
		setSize(230, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new GridLayout(3, 1, 10, 10));
		p.setBorder(new EmptyBorder(15, 30, 15, 30));
		for(int i=0; i<bn.length; i++) {
			btn[i] = new JButton(bn[i]);
			btn[i].addActionListener(this);
			p.add(btn[i]);
		}
		
		add(p);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn[0]) {
			try {
				ResultSet rs = DBInterface.Stmt.executeQuery("select * from student where studentID='"+Login.text1.getText()+"'");
				rs.next(); String sID = rs.getString(1);
				
				rs = DBInterface.Stmt.executeQuery("select * from study where studentid='"+sID+"'");
				if(rs.next()) {
					JOptionPane.showMessageDialog(null, "�̹� ������û�� ������ �����մϴ�.");
				} else {
					
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if(bt==btn[1]) {
			try {
				ResultSet rs = DBInterface.Stmt.executeQuery("select * from study where studentid='"+Login.text1.getText()+"'");
				if(rs.next()) {
					new UserScoreSelect();
				} else {
					JOptionPane.showMessageDialog(null, "�Էµ��� ������ �����ϴ�.");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} else if(bt==btn[2]) {
			try {
				DBInterface.Stmt.execute("DELETE FROM `project000`.`study` WHERE `studentid`='"+Login.text1.getText()+"';");
				DBInterface.Stmt.execute("DELETE FROM `project000`.`slimit` WHERE `studentid`='"+Login.text1.getText()+"';");
				
				JOptionPane.showMessageDialog(null, "������Ұ� �Ϸ�Ǿ����ϴ�.");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
