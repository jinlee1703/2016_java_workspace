package Project_Frame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class UserMenu extends JFrame implements ActionListener {
	JButton btn1 = new JButton("수강자조회");
	JButton btn2 = new JButton("점수입력");
	
	public UserMenu() {
		setTitle("강사메뉴");
		setSize(230, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new GridLayout(2, 1, 15, 15));
		p.setBorder(new EmptyBorder(30, 30, 30, 30));
		p.add(btn1); p.add(btn2);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
		add(p);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where userID='"+Login.text1.getText()+"'");
			rs.next(); String sub = rs.getString(5);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(bt==btn1) {
			new UserStudySelect();
		} else if(bt==btn2) {
			new UserScoreInsert();
		}
	}
}

