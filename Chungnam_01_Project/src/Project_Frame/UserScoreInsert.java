package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class UserScoreInsert extends JFrame implements ActionListener {
	JLabel label1 = new JLabel("ID");
	JLabel label2 = new JLabel("점수");
	JComboBox combo1 = new JComboBox();
	JComboBox combo2 = new JComboBox();
	JButton btn1 = new JButton("확인");
	JButton btn2 = new JButton("취소");
	
	public UserScoreInsert() {
		setTitle("점수등록");
		setSize(230, 220);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		basePanel.setBorder(new EmptyBorder(30, 20, 60, 30));
		
		JPanel p1 = new JPanel(new FlowLayout());
		p1.add(label1); p1.add(combo1);
		p1.add(label2); p1.add(combo2);
		
		label1.setPreferredSize(new Dimension(40, 20));
		label2.setPreferredSize(new Dimension(40, 20));
		label1.setHorizontalAlignment(label1.LEFT);
		label2.setHorizontalAlignment(label2.LEFT);
		combo1.setPreferredSize(new Dimension(110, 22));
		combo2.setPreferredSize(new Dimension(110, 22));
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
		p2.add(btn1); p2.add(btn2);
		btn1.setPreferredSize(new Dimension(60, 30));
		btn2.setPreferredSize(new Dimension(60, 30));
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
		basePanel.add(p1, BorderLayout.CENTER);
		basePanel.add(p2, BorderLayout.SOUTH);
		add(basePanel);
		
		Init();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn1) {
			try {
				ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where userID='"+Login.text1.getText()+"'");
				rs.next(); String sID = rs.getString(1); String sub = rs.getString(5); 
				
				DBInterface.Stmt.execute("UPDATE `project000`.`study` SET `"+sub+"`='"+combo2.getSelectedItem().toString()+"' WHERE `studentid`='"+sID+"';");
				
				JOptionPane.showMessageDialog(null, sub+" 점수가 입력되었습니다.");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			this.dispose();
		}
	}
	
	public void Init() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where userID='"+Login.text1.getText()+"'");
			rs.next(); String sub = rs.getString(5); 
			
			rs = DBInterface.Stmt.executeQuery("select student.studentID from student,study where student.id=study.studentID and study."+sub+"<>'0'");
			
			while(rs.next()) {
				combo1.addItem(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0; i<6; i++) {
			combo2.addItem(i);
		}
	}
}
