package Project_Frame;

import java.awt.BorderLayout;
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
	JTextField[] t = new JTextField[5];
	JButton btn1 = new JButton("수정");
	JButton btn2 = new JButton("취소");
	
	public MemberUpdate() {
		setTitle("개인정보");
		setSize(350, 300);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JPanel p1 = new JPanel(new GridLayout(5, 1));
		p1.setBorder(new EmptyBorder(0, 0, 0, 10));
		p1.add(new JLabel("일련번호"));
		p1.add(new JLabel("구매자"));
		p1.add(new JLabel("사이즈"));
		p1.add(new JLabel("수량"));
		p1.add(new JLabel("주소"));
		
		JPanel p2 = new JPanel(new GridLayout(5, 1, 2, 2));
		for(int i=0; i<5; i++) {
			t[i] = new JTextField();
			p2.add(t[i]);
		}
		
		JPanel p3 = new JPanel(new GridLayout(1, 2, 5, 5));
		p3.add(btn1); p3.add(btn2);
		
		p.add(p1, BorderLayout.WEST);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		add(p);
		
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+Login.id.getText()+"'");
			rs.next();
			t[0].setText(rs.getString(1));
			t[1].setText(rs.getString(2));
			t[2].setText(rs.getString(3));
			t[3].setText(rs.getString(4));
			t[4].setText(rs.getString(5));
			
			t[0].setEditable(false);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				for(int i=0; i<5; i++) {
					if(t[i].getText().equals("")) {
						JOptionPane.showMessageDialog(null, "모든 값을 입력해주세요.");
						return;
					}
				}
				
				try {
					DBInterface.Stmt.execute("UPDATE `project000`.`member` SET `pw`='"+t[1].getText()+"', `name`='"+t[2].getText()+"', `phone_num`='"+t[3].getText()+"', `address`='"+t[4].getText()+"' WHERE `id`='"+t[0].getText()+"';");
					dispose();
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
}
