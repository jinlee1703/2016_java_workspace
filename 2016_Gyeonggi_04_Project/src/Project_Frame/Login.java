package Project_Frame;

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

import Project_DBInterface.DBInterface;

public class Login extends JFrame implements ActionListener, Runnable {
	JLabel img = new JLabel();
	JTextField idText = new JTextField(20);
	JPasswordField pwText = new JPasswordField(20);
	JCheckBox cb = new JCheckBox("아이디 저장");
	JButton btn1 = new JButton("확인");
	JButton btn2 = new JButton("취소");
	JButton btn3 = new JButton("가입");
	static String id;
	int cnt=1;
	
	public Login() {
		setTitle("로그인");
		setSize(500, 400);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel pp = new JPanel();
		pp.setBackground(Color.white);
		pp.add(img);
		
		JPanel p1 = new JPanel(new FlowLayout());
		p1.add(new JLabel("아이디 :")).setPreferredSize(new Dimension(100, 20));
		p1.add(idText);
		p1.add(new JLabel("비밀번호 :")).setPreferredSize(new Dimension(100, 20));
		p1.add(pwText);
		
		JPanel p2 = new JPanel(new FlowLayout());
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		p2.add(cb); p2.add(btn1); p2.add(btn2); p2.add(btn3);
		
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from log");
			if(rs.next()) {
				idText.setText(rs.getString(1));
				cb.setSelected(true);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+idText.getText()+"' and pw='"+pwText.getText()+"'");
				if(rs.next()) {
					try {
						idSave();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					id=idText.getText();
					
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if(bt==btn1) {
			dispose();
		} else {
			
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			String path = System.getProperty("user.dir")+"\\res\\login\\"+cnt+".jpg";
			path = path.replace('\\', '/');
			img.setIcon(new ImageIcon(path));
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(cnt==4) {
				cnt=0;
			}
			cnt++;
		}
	}
	
	public void idSave() throws Exception {
		DBInterface.Stmt.execute("delete from log");
		if(cb.isSelected()) {
			DBInterface.Stmt.execute("INSERT INTO `project000`.`log` (`id`) VALUES ('"+idText.getText()+"');");
		}
	}
}
