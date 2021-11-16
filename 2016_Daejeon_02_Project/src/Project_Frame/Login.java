package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class Login extends JFrame {
	JLabel l1 = new JLabel("아이디");
	JLabel l2 = new JLabel("비밀번호");
	static JTextField id = new JTextField();
	JTextField pw = new JPasswordField();
	JButton btn = new JButton("LOGIN");
	JCheckBox cb = new JCheckBox("ID 저장");
	JLabel su = new JLabel("SIGN UP");
	
	public Login() {
		setSize(300, 150);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		
		JPanel p11 = new JPanel(new FlowLayout());
		p11.setPreferredSize(new Dimension(195, 55));
		l1.setPreferredSize(new Dimension(55, 20));
		l2.setPreferredSize(new Dimension(55, 20));
		id.setPreferredSize(new Dimension(130, 30));
		pw.setPreferredSize(new Dimension(130, 30));
		p11.add(l1); p11.add(id);
		p11.add(l2); p11.add(pw);
		
		JPanel p12 = new JPanel(new BorderLayout());
		p12.setPreferredSize(new Dimension(55, 55));
		p12.setBorder(new EmptyBorder(5, 5, 5, 5));
		p12.add(btn);
		
		p1.add(p11); p1.add(p12);
		
		JPanel p2 = new JPanel();
		
		Map at = su.getFont().getAttributes();
		at.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		su.setFont(su.getFont().deriveFont(at));
		p2.add(cb); p2.add(su);
		
		p.add(p1, BorderLayout.CENTER);
		p.add(p2, BorderLayout.SOUTH);
		
		add(p);
		
		IDLoad();
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(id.getText().equals("admin") && pw.getText().equals("admin")) {
					dispose();
					if(cb.isSelected()) {
						try {
							DBInterface.Stmt.execute("delete from log");
							DBInterface.Stmt.execute("insert into log values('admin')");
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					new AdminMain();
				} else {
					try {
						ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+id.getText()+"' and pw='"+pw.getText()+"'");
						
						if(rs.next()) {
							IDSave();
							dispose();
							new MemberMain();
						} else {
							JOptionPane.showMessageDialog(null, "아이디 혹은 비밀번호가 잘못되었습니다.");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
			}
		});
		
		su.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				new Join();
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void IDLoad() {
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
	}
	
	public void IDSave() {
		try {
			DBInterface.Stmt.execute("delete from log");
			if(cb.isSelected()) {
				DBInterface.Stmt.execute("insert into log values('"+id.getText()+"')");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
