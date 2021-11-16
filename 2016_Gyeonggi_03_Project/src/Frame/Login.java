package Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
	JLabel img = new JLabel("", JLabel.CENTER);
	static JTextField id = new JTextField(20);
	JTextField pw = new JPasswordField(20);
	JCheckBox cb = new JCheckBox("아이디 저장");
	JButton btn1 = new JButton("확인");
	JButton btn2 = new JButton("취소");
	JButton btn3 = new JButton("가입");
	int cnt=1;
	
	public Login() {
		setTitle("로그인");
		setSize(500, 400);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBackground(Color.white);
		p1.add(img);
		
		JPanel p2 = new JPanel(new BorderLayout());
		
		JPanel p21 = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
		p21.setBorder(new EmptyBorder(0, 50, 0, 50));
		p21.setPreferredSize(new Dimension(400, 80));
		p21.add(new JLabel("아이디 :")).setPreferredSize(new Dimension(100, 20));
		p21.add(id);
		p21.add(new JLabel("비밀번호 :")).setPreferredSize(new Dimension(100, 20));
		p21.add(pw);
		
		JPanel p22 = new JPanel(new FlowLayout());
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		p22.add(cb); p22.add(btn1); p22.add(btn2); p22.add(btn3);
		
		p2.add(p21, BorderLayout.CENTER);
		p2.add(p22, BorderLayout.SOUTH);
		
		p.add(p1, BorderLayout.CENTER);
		p.add(p2, BorderLayout.SOUTH);
		
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
		
		add(p);
		
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
					IDSave();
					new Main();
					this.dispose();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} else if(bt==btn2) {
			System.exit(0);
		} else {
			new Join();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			String path = System.getProperty("user.dir")+"\\res\\login\\"+cnt+".jpg";
			path.replace('\\', '/');
			img.setIcon(new ImageIcon(path));
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			cnt++;
			if(cnt==5) cnt=1;
		}
	}
	
	public void IDSave() {
		try {
			DBInterface.Stmt.execute("delete from log");
			if(cb.isSelected()) {
				DBInterface.Stmt.execute("INSERT INTO `project000`.`log` (`id`) VALUES ('"+id.getText()+"');");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
