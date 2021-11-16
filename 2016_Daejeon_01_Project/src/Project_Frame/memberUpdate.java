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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class memberUpdate extends JFrame implements ActionListener {
	JLabel[] label = new JLabel[5];
	String[] ln = {"아이디","비밀번호","이름","연락처","주소"};
	JTextField[] text = new JTextField[5];
	JButton btn1 = new JButton("수정");
	JButton btn2 = new JButton("취소");
	
	public memberUpdate() {
		setTitle("개인정보 수정");
		setSize(400, 280);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new EmptyBorder(10, 0, 0, 0));
		
		JPanel p1 = new JPanel(new FlowLayout());
		for(int i=0; i<5; i++) {
			label[i] = new JLabel(ln[i]);
			label[i].setPreferredSize(new Dimension(70, 30));
			text[i] = new JTextField();
			text[i].setPreferredSize(new Dimension(300, 30));
			p1.add(label[i]);
			p1.add(text[i]);
		}
		
		JPanel p2 = new JPanel(new GridLayout(1, 2, 5, 5));
		p2.setBorder(new EmptyBorder(10, 10, 10, 10));
		p2.add(btn1); p2.add(btn2);
		
		p.add(p1, BorderLayout.CENTER);
		p.add(p2, BorderLayout.SOUTH);
		
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
				DBInterface.Stmt.execute("UPDATE `project000`.`member` SET `pw`='"+text[1].getText()+"', `name`='"+text[2].getText()+"', `phone_num`='"+text[3].getText()+"', `address`='"+text[4].getText()+"' WHERE `id`='"+text[0].getText()+"';");
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
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+Login.id.getText()+"'");
			if(rs.next()) {
				text[0].setText(rs.getString(1));
				text[1].setText(rs.getString(2));
				text[2].setText(rs.getString(3));
				text[3].setText(rs.getString(4));
				text[4].setText(rs.getString(5));
				
				text[0].setEditable(false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
