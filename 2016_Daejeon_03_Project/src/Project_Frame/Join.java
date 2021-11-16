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

import Project_DBInterface.DBInterface;

public class Join extends JFrame {
	String rd = String.format("%06d", new Random().nextInt(1000000));
	String[] ln = {"���̵�","��й�ȣ","��й�ȣ Ȯ��","�̸�","����ó","�ּ�",rd};
	JLabel[] l = new JLabel[7];
	JTextField text1 = new JTextField(18);
	JTextField text2 = new JTextField(18);
	JPasswordField text3 = new JPasswordField(18);
	JTextField text4 = new JTextField(18);
	JTextField text5 = new JTextField(18);
	JTextField text6 = new JTextField(25);
	JTextField text7 = new JTextField(18);
	JButton btn1 = new JButton("����");
	JButton btn2 = new JButton("���");
	JLabel c1 = new JLabel();
	JLabel c2 = new JLabel();
	JLabel c3 = new JLabel();
	int c, cc, ccc;
	
	public Join() {
		setTitle("ȸ������");
		setSize(500, 300);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBackground(Color.black);
		p1.add(new JLabel("��� ������ �������� �Է����ּ���.", JLabel.CENTER)).setForeground(Color.white);
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		JPanel[] pp = new JPanel[7];
		for(int i=0; i<7; i++) {
			pp[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
			
			l[i] = new JLabel(ln[i]);
			l[i].setPreferredSize(new Dimension(100, 30));
			
			pp[i].add(l[i]);
			p2.add(pp[i]);
		}
		l[6].setFont(new Font("���� ���", Font.BOLD, 18));
		text3.setEchoChar('��');
		pp[0].add(text1); pp[0].add(c1);
		pp[1].add(text2);
		pp[2].add(text3); pp[2].add(c2);
		pp[3].add(text4);
		pp[4].add(text5);
		pp[5].add(text6);
		pp[6].add(text7); pp[6].add(c3);
		
		JPanel p3 = new JPanel();
		p3.add(btn1); p3.add(btn2);
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		add(p);
		
		setFocusable(true);
		
		text1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if(text1.getText().equals("")) {
					c1.setText("���̵� �Է����ּ���.");
				}
			}
		});
		
		text1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				c = 0;
				try {
					ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+text1.getText()+"'");
					if(rs.next()) {
						c1.setText("�ߺ��� ���̵� �Դϴ�.");
					} else {
						c1.setText("��� ������ ���̵� �Դϴ�.");
						c=1;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		text3.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if(text1.getText().equals("")) {
					c2.setText("��й�ȣ�� �Է����ּ���.");
				}
			}
		});
		
		text3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				cc = 0;
				if(text2.getText().equals(text3.getText())) {
					c2.setText("��й�ȣ�� ��ġ�մϴ�");
					cc=1;
				} else {
					c2.setText("��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
				}
			}
		});
		
		text7.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				ccc = 0;
				if(rd.equals(text7.getText())) {
					c3.setText("��ġ�մϴ�.");
					ccc=1;
				} else {
					c3.setText("��ġ���� �ʽ��ϴ�.");
				}
			}
		});
		
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(text1.getText().equals("") || text2.getText().equals("") || text3.getText().equals("") || text4.getText().equals("")||text5.getText().equals("")||text6.getText().equals("")||text7.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "��� ������ �Է����ּ���.");
					return;
				}
				
				if(c!=1 || cc!=1 || ccc!=1) {
					JOptionPane.showMessageDialog(null, "���������� Ȯ�����ּ���.");
					return;
				}
				
				try {
					DBInterface.Stmt.execute("INSERT INTO `project000`.`member` (`id`, `pw`, `name`, `phone_num`, `address`, `point`) "
							+ "VALUES ('"+text1.getText()+"', '"+text2.getText()+"', '"+text4.getText()+"', '"+text5.getText()+"', '"+text6.getText()+"', '0');");
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
