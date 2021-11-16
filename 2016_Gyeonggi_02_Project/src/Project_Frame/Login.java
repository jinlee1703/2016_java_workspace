package Project_Frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class Login extends JFrame implements ActionListener {
	JLabel l1 = new JLabel("아이디 :");
	JLabel l2 = new JLabel("비밀번호 :");
	static JTextField id = new JTextField(20);
	JTextField pw = new JPasswordField(20);
	JCheckBox cb = new JCheckBox("아이디 저장");
	JButton btn1 = new JButton("확인");
	JButton btn2 = new JButton("취소");
	JButton btn3 = new JButton("가입");
	
	public Login() {
		setTitle("로그인");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		
		ImagePanel p1 = new ImagePanel();
		p1.setPreferredSize(new Dimension(500, 250));
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
		p2.setBorder(new EmptyBorder(0, 80, 0, 80));
		p2.setPreferredSize(new Dimension(500, 70));
		l1.setPreferredSize(new Dimension(80, 20));
		l2.setPreferredSize(new Dimension(80, 20));
		p2.add(l1); p2.add(id);
		p2.add(l2); p2.add(pw);
		
		JPanel p3 = new JPanel(new FlowLayout());
		p3.add(cb); p3.add(btn1); p3.add(btn2); p3.add(btn3);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		
		p.add(p1);
		p.add(p2);
		p.add(p3);
		
		add(p);
		
		if(loadid()!=null) {
			id.setText(loadid());
			cb.setSelected(true);
		}
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		
		if(bt==btn1) {
			try {
				ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+id.getText()+"' and pw='"+pw.getText()+"'");
				
				if(rs.next()) {
					if(cb.isSelected()) {
						idSave(id.getText());
					} else {
						idSave("");
					}
					new Main();
					this.dispose();
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if(bt==btn3) {
			new Join();
		} else {
			System.exit(0);
		}
	}
	
	class ImagePanel extends JPanel implements Runnable {
		Image img = new ImageIcon(getClass().getClassLoader().getResource("login/1.jpg")).getImage();
		
		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			setBackground(Color.white);
			g.drawImage(img, 150, 0, 200, 230, this);
		}
		
		public ImagePanel() {
			// TODO Auto-generated constructor stub
			Thread th = new Thread(this);
			th.start();
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
	
	String loadid() {
		FileReader in = null;
		try {
			in = new FileReader("C:\\Program Files\\id.txt");
			BufferedReader rd = new BufferedReader(in);
			
			String s = rd.readLine();
			rd.close();
			return s;
		} catch(IOException e) {
			return null;
		}
	}
	
	public void idSave(String id) {
		try {
			FileWriter out = new FileWriter("C:\\Program Files\\id.txt");
			BufferedWriter bw = new BufferedWriter(out);
			bw.write(id);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
