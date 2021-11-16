package Project_Frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class Login extends JFrame implements ActionListener {
	JLabel l1 = new JLabel("아이디 :");
	JLabel l2 = new JLabel("비밀번호 :");
	static JTextField id = new JTextField(18);
	JTextField pw = new JTextField(18);
	JCheckBox check = new JCheckBox("아이디 저장");
	JButton btn1 = new JButton("확인");
	JButton btn2 = new JButton("취소");
	JButton btn3 = new JButton("가입");
	
	public Login() {
		setTitle("로그인");
		setSize(550, 385);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		ImagePanel p1 = new ImagePanel();
		p1.setPreferredSize(new Dimension(550, 240));
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15));
		p2.setPreferredSize(new Dimension(550, 70));
		p2.setBorder(new EmptyBorder(-10, 80, 0, 80));
		l1.setPreferredSize(new Dimension(60, 20));
		l2.setPreferredSize(new Dimension(60, 20));
		p2.add(l1); p2.add(id);
		p2.add(l2); p2.add(pw);
		
		JPanel p3 = new JPanel(new FlowLayout());
		p3.setPreferredSize(new Dimension(550, 50));
		p3.add(check); p3.add(btn1); p3.add(btn2); p3.add(btn3);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		
		p.add(p1);
		p.add(p2);
		p.add(p3);
		
		add(p);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
		
		if(btn==btn1) {
			try {
				ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+id.getText()+"' and pw='"+pw.getText()+"'");
				
				if(rs.next()) {
					this.dispose();
					new Main();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if(btn==btn2) {
			System.exit(0);
		} else if(btn==btn3) {
			new MemberInsert();
		}
	}
}

class ImagePanel extends JPanel implements Runnable {
	Image img = new ImageIcon(getClass().getClassLoader().getResource("login/1.jpg")).getImage();
	
	public ImagePanel() {
		Thread th = new Thread(this);
		th.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		setBackground(Color.white);
		g.drawImage(img, 160, 0, 200, 240, this);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			img = new ImageIcon(getClass().getClassLoader().getResource("login/1.jpg")).getImage();
			repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			img = new ImageIcon(getClass().getClassLoader().getResource("login/2.jpg")).getImage();
			repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			img = new ImageIcon(getClass().getClassLoader().getResource("login/3.jpg")).getImage();
			
			repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			img = new ImageIcon(getClass().getClassLoader().getResource("login/4.jpg")).getImage();
			
			repaint();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
