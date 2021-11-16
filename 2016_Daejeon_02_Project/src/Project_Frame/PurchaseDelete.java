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
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class PurchaseDelete extends JFrame {
	String[] ln = {"일련번호","구매자","사이즈","수량","주소","주문일","배달확인"};
	JLabel[] l = new JLabel[ln.length];
	JTextField[] t = new JTextField[ln.length-1];
	JButton btn1 = new JButton("삭제");
	JButton btn2 = new JButton("취소");
	JPanel cp = new JPanel(new FlowLayout(FlowLayout.LEFT));
	JCheckBox cb = new JCheckBox("완료");
	
	public PurchaseDelete() {
		setTitle("거래내역 삭제");
		setSize(400, 400);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		for(int i=0; i<ln.length-1; i++) {
			l[i] = new JLabel(ln[i]);
			t[i] = new JTextField();
			l[i].setPreferredSize(new Dimension(60, 20));
			t[i].setPreferredSize(new Dimension(290, 40));
			
			p1.add(l[i]);
			p1.add(t[i]);
		}
		l[6] = new JLabel(ln[6]);
		p1.add(l[6]);
		cp.add(cb);
		p1.add(cp);
		
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
				try {
					ResultSet rs = DBInterface.Stmt.executeQuery("select * from purchase where id='"+t[0].getText()+"'");
					rs.next();
					if(rs.getInt(8)==0) {
						DBInterface.Stmt.execute("DELETE FROM `project000`.`purchase` WHERE `id`='"+t[0].getText()+"';");
						dispose();
						Purchases.TableInsert();
					}
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
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from purchase where id='"+(String) Purchases.table.getModel().getValueAt(Purchases.table.getSelectedRow(), 0)+"'");
			rs.next();
			
			t[0].setText(rs.getString(1));
			t[1].setText(rs.getString(2));
			t[2].setText(rs.getString(3));
			t[3].setText(rs.getString(4));
			t[4].setText(rs.getString(6));
			t[5].setText(rs.getString(7));
			
			if(rs.getInt(8)==1) {
				cb.setSelected(true);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		t[0].setEditable(false);
	}
}
