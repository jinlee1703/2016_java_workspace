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

public class Member extends JFrame implements ActionListener {
	JLabel[] label = new JLabel[5];
	String[] ln = {"ID","성명","생년월일","주소","password"};
	JTextField[] text = new JTextField[5];
	JButton[] btn = new JButton[5];
	String[] bn = {"상품주문","주문내역","수정","삭제","종료"};
	
	public Member(String id) {
		setTitle("고객");
		setSize(290, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		basePanel.setBorder(new EmptyBorder(15, 15, 150, 30));
		
		JPanel p1 = new JPanel(new FlowLayout());
		for(int i=0; i<ln.length; i++) {
			label[i] = new JLabel(ln[i]);
			label[i].setPreferredSize(new Dimension(60, 20));
			text[i] = new JTextField(15);
			p1.add(label[i]);
			p1.add(text[i]);
		}
		
		for(int i=0; i<bn.length; i++) {
			btn[i] = new JButton(bn[i]);
			btn[i].addActionListener(this);
		}
		JPanel p2 = new JPanel(new GridLayout(1, 2, 5, 5));
		p2.add(btn[0]); p2.add(btn[1]);
		
		JPanel p3 = new JPanel(new GridLayout(1, 3, 5, 5));
		p3.add(btn[2]); p3.add(btn[3]); p3.add(btn[4]);
		
		JPanel p4 = new JPanel(new GridLayout(2, 1, 5, 5));
		p4.add(p2); p4.add(p3);
		
		basePanel.add(p1, BorderLayout.CENTER);
		basePanel.add(p4, BorderLayout.SOUTH);
		add(basePanel);
		
		Init(id);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn[0]) {
			new OrderlistInsert(text[0].getText());
		} else if(bt==btn[1]) {
			new OrderlistSelect(text[0].getText());
		} else if(bt==btn[2]) {
			MemberUpdate();
		} else if(bt==btn[3]) {
			MemberDelete();
		} else {
			System.exit(0);
		}
	}
	
	public void Init(String id) {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+id+"'");
			rs.next();
			text[0].setText(rs.getString(1));
			text[1].setText(rs.getString(2));
			text[2].setText(rs.getString(3));
			text[3].setText(rs.getString(4));
			text[4].setText(rs.getString(5));
			
			text[0].setEnabled(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void MemberUpdate() {
		try {
			DBInterface.Stmt.execute("UPDATE `project000`.`member` SET `name`='"+text[1].getText()+"', `birthday`='"+text[2].getText()+"', `address`='"+text[3].getText()+"', `password`='"+text[4].getText()+"' WHERE `id`='"+text[0].getText()+"';");
			
			JOptionPane.showMessageDialog(null, "회원이 수정되었습니다.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void MemberDelete() {
		try {
			DBInterface.Stmt.execute("DELETE FROM `project000`.`member` WHERE `id`='"+text[0].getText()+"';");
			JOptionPane.showMessageDialog(null, "회원이 삭제되었습니다.");
			this.dispose();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
