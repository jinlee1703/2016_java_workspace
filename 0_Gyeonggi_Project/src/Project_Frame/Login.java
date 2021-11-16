package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class Login extends JFrame implements ActionListener, Runnable {
	JLabel img = new JLabel();
	static JTextField id = new JTextField(18);
	JTextField pw = new JPasswordField(18);
	JCheckBox cb = new JCheckBox("아이디 저장");
	JButton btn1 = new JButton("확인");
	JButton btn2 = new JButton("취소");
	JButton btn3 = new JButton("가입");
	int cnt = 1;
	
	public Login() {
		setTitle("로그인");
		setSize(450, 350);
		setLocationRelativeTo(null);
		
		JPanel p = new  JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel();
		p1.setBackground(Color.white);
		p1.add(img);
		
		JPanel p2 = new JPanel();
		p2.setBorder(new EmptyBorder(0, 50, 0, 50));
		p2.add(new JLabel("아이디 :")).setPreferredSize(new Dimension(80, 20));
		p2.add(id);
		p2.add(new JLabel("비밀번호 :")).setPreferredSize(new Dimension(80, 20));
		p2.add(pw);
		
		JPanel p3 = new JPanel();
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		p3.add(cb); p3.add(btn1); p3.add(btn2); p3.add(btn3);
		
		
		p.add(p1, BorderLayout.NORTH);
		p.add(p2, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);
		
		add(p);
		
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from log");
			if(rs.next()) {
				id.setText(rs.getString(1));
				cb.setSelected(true);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thread th = new Thread(this);
		th.start();
		
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
					idSave();
					this.dispose();
					new Main();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		} else if(bt==btn2) {
			System.exit(0);
		} else if(bt==btn3) {
			new Join();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			String path = System.getProperty("user.dir")+"\\지급자료\\login\\"+cnt+".jpg";
			path = path.replace('\\', '/');
			img.setIcon(new ImageIcon(path));
			cnt++;
			if(cnt==5) {
				cnt=1;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void idSave() {
		try {
			DBInterface.Stmt.execute("delete from log");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(cb.isSelected()) {
			try {
				DBInterface.Stmt.execute("INSERT INTO `project000`.`log` (`id`) VALUES ('"+id.getText()+"');");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
