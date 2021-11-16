package Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Project_DBInterface.DBInterface;

public class MemberSelect extends JFrame implements ActionListener {
	JPanel[] pp = new JPanel[5];
	String[] ln = {"회원 아이디","회원 비밀번호","이름","주소","이메일"};
	JLabel[] l = new JLabel[5];
	int[] ts = {10,10,5,20,20};
	JTextField[] t = new JTextField[5];
	JLabel cl = new JLabel();
	JButton btn1 = new JButton("수정");
	JButton btn2 = new JButton("탈퇴");
	JButton btn3 = new JButton("닫기");
	
	public MemberSelect() {
		setTitle("회원관리");
		setSize(400, 300);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p1.add(new JLabel("회원정보관리")).setFont(new Font("hy견고딕", Font.PLAIN, 25));;
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		for(int i=0; i<5; i++) {
			pp[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
			l[i] = new JLabel(ln[i]+" :");
			l[i].setPreferredSize(new Dimension(100, 20));
			t[i] = new JTextField(ts[i]);
			
			pp[i].add(l[i]);
			pp[i].add(t[i]);
			p2.add(pp[i]);
		}
		pp[1].add(cl);
		
		JPanel p3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 15));
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		p3.add(btn1); p3.add(btn2); p3.add(btn3);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		add(p);
		
		textSetting();
		
		t[1].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(t[1].getText().matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*")) {
					cl.setText("(취약함)");
					cl.setForeground(Color.red);
				} else {
					cl.setText("(안전함)");
					cl.setForeground(Color.blue);
				}
			}
		});
		
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					DBInterface.Stmt.execute("UPDATE `project000`.`member` "
							+ "SET `pw`='"+t[1].getText()+"', `name`='"+t[2].getText()+"', `address`='"+t[3].getText()+"', `email`='"+t[4].getText()+"' WHERE `id`='"+t[0].getText()+"';");
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
				int res = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION);
				
				if(res==JOptionPane.YES_OPTION) {
					try {
						DBInterface.Stmt.execute("delete from ledger where memberid='"+Login.id.getText()+"'");
						DBInterface.Stmt.execute("delete from member where id='"+Login.id.getText()+"'");
						dispose();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				try {
					ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+Login.id.getText()+"'");
					if(rs.next()) {
						new Main();
					} else {
						new Login();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void textSetting() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+Login.id.getText()+"'");
			if(rs.next()) {
				t[0].setText(rs.getString(1));
				t[1].setText(rs.getString(2));
				t[2].setText(rs.getString(3));
				t[3].setText(rs.getString(4));
				t[4].setText(rs.getString(5));
				
				if(t[1].getText().matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*")) {
					cl.setText("(취약함)");
					cl.setForeground(Color.red);
				} else {
					cl.setText("(안전함)");
					cl.setForeground(Color.blue);
				}
				
				t[0].setEditable(false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
