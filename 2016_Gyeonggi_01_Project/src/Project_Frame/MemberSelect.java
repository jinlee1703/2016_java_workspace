package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class MemberSelect extends JFrame implements ActionListener {
	JLabel title = new JLabel("회원정보관리");
	JLabel[] l = new JLabel[5];
	String[] ln = {"회원 아이디 :","회원 비밀번호 :","이름 :","주소 :","이메일 :"};
	JTextField[] text = new JTextField[5];
	JLabel cl = new JLabel("(취약함)");
	JButton btn1 = new JButton("수정");
	JButton btn2 = new JButton("탈퇴");
	JButton btn3 = new JButton("닫기");
	
	public MemberSelect() {
		setTitle("회원관리");
		setSize(380, 300);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		title.setFont(new Font("HY견고딕", Font.PLAIN, 20));
		p1.add(title);
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p2.setBorder(new EmptyBorder(0, 10, 0, 0));
		JPanel[] pp = new JPanel[5];
		text[0] = new JTextField(10);
		text[1] = new JTextField(10);
		text[2] = new JTextField(5);
		text[3] = new JTextField(18);
		text[4] = new JTextField(18);
		for(int i=0; i<5; i++) {
			pp[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));
			l[i] = new JLabel(ln[i]);
			l[i].setPreferredSize(new Dimension(100, 20));
			pp[i].add(l[i]);
			pp[i].add(text[i]);
			p2.add(pp[i]);
		}
		cl.setForeground(Color.red);
		pp[1].add(cl);
		
		JPanel p3 = new JPanel(new FlowLayout());
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		p3.add(btn1);
		p3.add(btn2);
		p3.add(btn3);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		add(p);
		
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
				DBInterface.Stmt.execute("UPDATE `project000`.`member` SET `pw`='"+text[1].getText()+"', `name`='"+text[2].getText()+"', `address`='"+text[3].getText()+"', `email`='"+text[4].getText()+"' WHERE `id`='"+text[0].getText()+"';");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if(bt==btn2) {
			int res = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION);
			
			if(res==JOptionPane.YES_OPTION) {
				try {
					DBInterface.Stmt.execute("DELETE FROM `project000`.`member` WHERE `id`='"+Login.id.getText()+"';");
					
					DBInterface.Stmt.execute("DELETE FROM `project000`.`ledger` WHERE `memberid`='"+Login.id.getText()+"';");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else {
			this.dispose();
			new Main();
		}
	}
	
	public void Init() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+Login.id.getText()+"'");
			
			if(rs.next()) {
				text[0].setText(rs.getString(1));
				text[1].setText(rs.getString(2));
				text[2].setText(rs.getString(3));
				text[3].setText(rs.getString(4));
				text[4].setText(rs.getString(5));
			}
			
			text[0].setEnabled(false);
			if(text[1].getText().matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*")) {
				cl.setText("(취약함)");
				cl.setForeground(Color.red);
			} else {
				cl.setText("(안전함)");
				cl.setForeground(Color.blue);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
