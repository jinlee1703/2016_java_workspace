package Project_Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class PurchaseDelete extends JFrame {
	JTextField[] t = new JTextField[6];
	JCheckBox cb = new JCheckBox("완료");
	JButton btn1 = new JButton("삭제");
	JButton btn2 = new JButton("취소");
	
	public PurchaseDelete(String pn) {
		setTitle("거래내역 삭제");
		setSize(400, 400);
		setLocationRelativeTo(null);
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JPanel p1 = new JPanel(new GridLayout(7, 1));
		p1.setBorder(new EmptyBorder(0, 0, 0, 10));
		p1.add(new JLabel("일련번호"));
		p1.add(new JLabel("구매자"));
		p1.add(new JLabel("사이즈"));
		p1.add(new JLabel("수량"));
		p1.add(new JLabel("주소"));
		p1.add(new JLabel("주문일"));
		p1.add(new JLabel("배달확인"));
		
		JPanel p2 = new JPanel(new GridLayout(6, 1, 2, 2));
		for(int i=0; i<6; i++) {
			t[i] = new JTextField();
			p2.add(t[i]);
		}
		JPanel p21 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p21.add(cb);
		p2.add(p21);
		
		JPanel p3 = new JPanel(new GridLayout(1, 2, 5, 5));
		p3.add(btn1); p3.add(btn2);
		
		p.add(p1, BorderLayout.WEST);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		add(p);
		
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from purchase where id='"+pn+"'");
			if(rs.next()) {
				t[0].setText(rs.getString(1));
				t[1].setText(rs.getString(2));
				t[2].setText(rs.getString(3));
				t[3].setText(rs.getString(4));
				t[4].setText(rs.getString(6));
				t[5].setText(rs.getString(7));
				
				t[0].setEditable(false);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					DBInterface.Stmt.execute("DELETE FROM `project000`.`purchase` WHERE `id`='"+pn+"';");
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
