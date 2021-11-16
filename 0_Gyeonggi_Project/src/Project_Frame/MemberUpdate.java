package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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

public class MemberUpdate extends JFrame implements ActionListener {
	JPanel[] pp = new JPanel[5];
	JLabel[] l = new JLabel[5];
	String[] ln = {"ȸ�� ���̵�","ȸ�� ��й�ȣ","�̸�","�ּ�","�̸���"};
	JTextField[] t = new JTextField[5];
	int[] ts = {10,10,5,20,20};
	JButton btn1 = new JButton("����");
	JButton btn2 = new JButton("Ż��");
	JButton btn3 = new JButton("�ݱ�");
	JLabel cl = new JLabel();
	
	public MemberUpdate() {
		setTitle("ȸ������");
		setSize(360, 300);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p1.add(new JLabel("ȸ����������")).setFont(new Font("HY�߰��", Font.PLAIN, 25));
		
		JPanel p2 = new JPanel(new GridLayout(5, 1));
		for(int i=0; i<5; i++) {
			pp[i] = new JPanel(new FlowLayout(FlowLayout.LEFT));
			l[i] = new JLabel(ln[i]+" :");
			l[i].setPreferredSize(new Dimension(100, 20));
			t[i] = new JTextField(ts[i]);
			
			pp[i].add(l[i]);
			pp[i].add(t[i]);
			p2.add(pp[i]);
		}
		pp[1].add(cl);
		
		JPanel p3 = new JPanel();
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		p3.add(btn1); p3.add(btn2); p3.add(btn3);
		
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		add(p);
		
		Init();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				new Main();
			}
		});
		
		t[1].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(t[1].getText().matches("[0-9|a-z|A-Z|��-��|��-��|��-��]*")) {
					cl.setText("(�����)");
					cl.setForeground(Color.red);
				} else {
					cl.setText("(������)");
					cl.setForeground(Color.blue);
				}
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void Init() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+Login.id.getText()+"'");
			
			if(rs.next()) {
				t[0].setText(rs.getString(1));
				t[1].setText(rs.getString(2));
				t[2].setText(rs.getString(3));
				t[3].setText(rs.getString(4));
				t[4].setText(rs.getString(5));
			}
			
			t[0].setEnabled(false);
			
			if(t[1].getText().matches("[0-9|a-z|A-Z|��-��|��-��|��-��]*")) {
				cl.setText("(�����)");
				cl.setForeground(Color.red);
			} else {
				cl.setText("(������)");
				cl.setForeground(Color.blue);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn1) {
			try {
				DBInterface.Stmt.execute("UPDATE `project000`.`member` SET `pw`='"+t[1].getText()+"', `name`='"+t[2].getText()+"', `address`='"+t[3].getText()+"', `email`='"+t[4].getText()+"' WHERE `id`='"+t[0].getText()+"';");
				
				dispose();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if(bt==btn2) {
			int res = JOptionPane.showConfirmDialog(null, "���� �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION);
			
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
		} else if(bt==btn3) {
			this.dispose();
		}
	}
}
