package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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

public class CustomerInsert extends JFrame implements ActionListener {
	JLabel title = new JLabel("ȸ������");
	JPanel[] pp = new JPanel[5];
	JLabel[] l = new JLabel[5];
	String[] ln = {"ID", "PW","PW Ȯ��","�̸�","��ȭ��ȣ"};
	JTextField[] t = new JTextField[5];
	JButton btn1 = new JButton("�ߺ�Ȯ��");
	JButton btn2 = new JButton("����");
	JButton btn3 = new JButton("�ݱ�");
	int cnt=0;
	
	public CustomerInsert() {
		setTitle("ȸ������");
		setSize(430, 450);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new EmptyBorder(20, 0, 50, 0));
		
		JPanel p1 = new JPanel(new FlowLayout());
		title.setFont(new Font("����", Font.BOLD, 30));
		p1.add(title);
		
		JPanel p2 = new JPanel(new GridLayout(5, 1, 0, 0));
		p2.setBorder(new EmptyBorder(20, 50, 10, 20));
		for(int i=0; i<5; i++) {
			pp[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
			pp[i].setPreferredSize(new Dimension(400, 40));
			l[i] = new JLabel(ln[i]+" :");
			l[i].setPreferredSize(new Dimension(100, 20));
			l[i].setHorizontalAlignment(l[i].RIGHT);
			t[i] = new JTextField();
			t[i].setPreferredSize(new Dimension(120, 20));
			pp[i].add(l[i]);
			pp[i].add(t[i]);
			p2.add(pp[i]);
		}
		btn1.setPreferredSize(new Dimension(90, 40));
		pp[0].add(btn1);
		
		JPanel p3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
		btn2.setPreferredSize(new Dimension(70, 30));
		btn3.setPreferredSize(new Dimension(70, 30));
		p3.add(btn2); p3.add(btn3);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		add(p);
		
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn1) {
			try {
				ResultSet rs = DBInterface.Stmt.executeQuery("select * from customer where id='"+t[0].getText()+"'");
				if(rs.next()) {
					JOptionPane.showMessageDialog(null, "�ߺ��� ���̵��Դϴ�.", "����", JOptionPane.ERROR_MESSAGE);
				} else {
					cnt=1;
					JOptionPane.showMessageDialog(null, "��� ������ ���̵��Դϴ�.");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if(bt==btn2) {
			if(cnt==0) {
				JOptionPane.showMessageDialog(null, "���̵� �ߺ��˻� ���ּ���.", "����", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(t[1].getText().equals("")) {
				JOptionPane.showMessageDialog(null, "��й�ȣ�� �Է����ּ���.", "����", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(t[2].getText().equals("")) {
				JOptionPane.showMessageDialog(null, "��й�ȣ�� �ѹ� �� �Է����ּ���.", "����", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(! t[1].getText().equals(t[2].getText())) {
				JOptionPane.showMessageDialog(null, "��й�ȣ�� ��ġ���� �ʽ��ϴ�.", "����", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(t[3].getText().equals("")) {
				JOptionPane.showMessageDialog(null, "�̸��� �Է����ּ���.", "����", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(t[4].getText().equals("")) {
				JOptionPane.showMessageDialog(null, "��ȭ��ȣ�� �Է����ּ���.", "����", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				ResultSet rs = DBInterface.Stmt.executeQuery("select count(*) from customer");
				
				DBInterface.Stmt.execute("INSERT INTO `cinema`.`customer` (`c_num`, `id`, `pw`, `c_name`, `tel`) "
						+ "VALUES ('"+rs.getInt(1)+1+"', '"+t[0].getText()+"', '"+t[1].getText()+"', '"+t[3].getText()+"', '"+t[4].getText()+"');");
				this.dispose();
				JOptionPane.showMessageDialog(null, "ȸ�������� �Ϸ�Ǿ����ϴ�.");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			this.dispose();
		}
	}
}
