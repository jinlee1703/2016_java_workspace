package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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

public class MemberUpdate extends JFrame {
	String[] ln = {"아이디","비밀번호","이름","연락처","주소"};
	JLabel[] l = new JLabel[ln.length];
	JTextField[] t = new JTextField[ln.length];
	JButton btn1 = new JButton("수정");
	JButton btn2 = new JButton("취소");
	
	public MemberUpdate() {
		setTitle("개인정보 수정");
		setSize(400, 330);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JPanel p1 = new JPanel(new FlowLayout());
		for(int i=0; i<ln.length; i++) {
			l[i] = new JLabel(ln[i]);
			t[i] = new JTextField();
			l[i].setPreferredSize(new Dimension(60, 20));
			t[i].setPreferredSize(new Dimension(290, 40));
			
			p1.add(l[i]);
			p1.add(t[i]);
		}
		
		JPanel p2 = new JPanel(new GridLayout(1, 2, 5, 5));
		p2.add(btn1); p2.add(btn2);
		
		p.add(p1, BorderLayout.CENTER);
		p.add(p2, BorderLayout.SOUTH);
		
		add(p);
		
		Setting();
		
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				for(int i=0; i<ln.length; i++) {
					if(t[i].getText().equals("")) {
						JOptionPane.showMessageDialog(null, "모든 값을 입력해주세요.");
						return;
					}
				}
				
				try {
					DBInterface.Stmt.execute("UPDATE `project000`.`member` "
							+ "SET `pw`='"+t[1].getText()+"', `name`='"+t[2].getText()+"', `phone_num`='"+t[3].getText()+"', `address`='"+t[4].getText()+"' WHERE `id`='"+Login.id.getText()+"';");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void Setting() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+Login.id.getText()+"'");
			
			if(rs.next()) {
				t[0].setText(rs.getString(1));
				t[1].setText(rs.getString(2));
				t[2].setText(rs.getString(3));
				t[3].setText(rs.getString(4));
				t[4].setText(rs.getString(5));
				
				t[0].setEditable(false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
