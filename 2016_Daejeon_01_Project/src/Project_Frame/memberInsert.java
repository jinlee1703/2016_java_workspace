package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class memberInsert extends JFrame implements ActionListener {
	JLabel title = new JLabel("��� ������ �������� �Է����ּ���.");
	JLabel[] l = new JLabel[7];
	String[] ln = {"���̵�","��й�ȣ","��й�ȣ Ȯ��","�̸�","����ó","�ּ�",""};
	JTextField[] text = new JTextField[6];
	JPasswordField pw = new JPasswordField(17);
	JButton btn1 = new JButton("����");
	JButton btn2 = new JButton("���");
	JLabel cl1 = new JLabel("");
	JLabel cl2 = new JLabel("");
	JLabel cl3 = new JLabel("");
	
	public memberInsert() {
		setTitle("ȸ������");
		setSize(500, 320);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		p1.setBackground(Color.black);
		title.setForeground(Color.white);
		p1.add(title);
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		p2.setBorder(new EmptyBorder(15, 0, 15, 8));
		JPanel[] pp = new JPanel[7];
		text[0] = new JTextField(17);
		text[1] = new JTextField(17);
		text[2] = new JTextField(17);
		text[3] = new JTextField(25);
		text[4] = new JTextField(17);
		text[5] = new JTextField(17);
		for(int i=0; i<7; i++) {
			pp[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
			l[i] = new JLabel(ln[i]);
			l[i].setPreferredSize(new Dimension(100, 28));
			pp[i].add(l[i]);
			p2.add(pp[i]);
		}
		pw.setEchoChar('��');
		l[6].setFont(new Font("���� ���", Font.BOLD, 20));
		l[6].setText(Integer.toString(new Random().nextInt(1000000)));
		l[6].setBorder(new EmptyBorder(5, 0, 0, 0));
		pp[0].add(text[0]); pp[0].add(cl1);
		pp[1].add(text[1]); 
		pp[2].add(pw); pp[2].add(cl2);
		pp[3].add(text[2]);
		pp[4].add(text[3]);
		pp[5].add(text[4]);
		pp[6].add(text[5]); pp[6].add(cl3);
		
		JPanel p3 = new JPanel(new FlowLayout());
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		p3.add(btn1); p3.add(btn2);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		add(p);
		
		setFocusable(false);
		setFocusable(true);
		
		text[0].addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if(text[0].getText().equals("")) {
					cl1.setText("���̵� �Է����ּ���.");
				}
			}
		});
		
		text[0].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(text[0].getText().equals("")) {
					cl1.setText("���̵� �Է����ּ���.");
				} else {
					try {
						ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+text[0].getText()+"'");
						
						if(rs.next()) {
							cl1.setText("�ߺ��� ���̵� �Դϴ�.");
						} else {
							cl1.setText("��� ������ ���̵� �Դϴ�.");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		text[2].addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if(text[2].getText().equals("")) {
					cl2.setText("��й�ȣ�� �Է����ּ���.");
				}
			}
		});
		
		text[2].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(text[2].getText().equals("")) {
					cl2.setText("��й�ȣ�� �Է����ּ���.");
				} else {
					if(text[1].getText().equals(text[2].getText())) {
						cl2.setText("��й�ȣ�� ��ġ�մϴ�");
					} else {
						cl2.setText("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
					}
				}
			}
		});
		
		text[5].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(l[6].getText().equals(text[5].getText())) {
					cl3.setText("��ġ�մϴ�.");
				} else {
					cl3.setText("��ġ���� �ʽ��ϴ�.");
				}
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn1) {
			if(text[0].getText().equals("") || text[1].getText().equals("") || pw.getText().equals("") || text[2].getText().equals("") || text[3].getText().equals("") || text[4].getText().equals("") || text[5].getText().equals("")) {
				JOptionPane.showMessageDialog(null, "��� ������ �Է����ּ���.", "����", JOptionPane.ERROR_MESSAGE);
			} else if(! cl1.getText().equals("��� ������ ���̵� �Դϴ�.") || ! cl2.getText().equals("��й�ȣ�� ��ġ�մϴ�") || ! cl3.getText().equals("��� ������ ���̵� �Դϴ�.")) {
				JOptionPane.showMessageDialog(null, "���� ������  Ȯ�����ּ���.", "����", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					DBInterface.Stmt.execute("INSERT INTO `project000`.`member` (`id`, `pw`, `name`, `phone_num`, `address`, `point`) "
							+ "VALUES ('"+text[0].getText()+"', '"+text[1].getText()+"', '"+text[2].getText()+"', '"+text[3].getText()+"', '"+text[4].getText()+"', '0');");
					this.dispose();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else {
			this.dispose();
		}
	}
}
