package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Map;

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
	JLabel label1 = new JLabel("아이디");
	JLabel label2 = new JLabel("비밀번호");
	static JTextField id = new JTextField();
	JPasswordField pw = new JPasswordField();
	JButton btn = new JButton("LOGIN");
	JCheckBox check = new JCheckBox("ID 저장");
	JLabel su = new JLabel("SIGN UP");
	public Login() {
		setSize(320, 160);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
		p1.setPreferredSize(new Dimension(210, 70));
		label1.setPreferredSize(new Dimension(55, 20));
		label2.setPreferredSize(new Dimension(55, 20));
		id.setPreferredSize(new Dimension(150, 30));
		pw.setPreferredSize(new Dimension(150, 30));
		p1.add(label1); p1.add(id);
		p1.add(label2); p1.add(pw);
		
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		p2.setBorder(new EmptyBorder(5, 0, 0, 0));
		btn.setPreferredSize(new Dimension(75, 65));
		p2.add(btn);
		
		JPanel p3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 2));
		p3.add(p1); p3.add(p2);
		
		JPanel p4 = new JPanel(new FlowLayout());
		Font font = su.getFont();
		Map at = font.getAttributes();
		at.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		su.setFont(font.deriveFont(at));
		
		p4.add(check); p4.add(su);
		
		p.add(p3, BorderLayout.CENTER);
		p.add(p4, BorderLayout.SOUTH);
		
		add(p);
		
		String issaved = loadid();
		if(issaved!=null) {
			id.setText(issaved);
			check.setSelected(true);
		}
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if(id.getText().equals("admin") && pw.getText().equals("admin")) {
						dispose();
						if(check.isSelected()) {
							FileWriter out = new FileWriter("C:\\Program Files\\id.txt");
							BufferedWriter bw = new BufferedWriter(out);
							bw.write(id.getText());
							bw.newLine();
							bw.close();
						} else {
							File f = new File("C:\\Program Files\\id.txt");
							f.delete();
						}
						new adminMain();
					} else {
						ResultSet rs = DBInterface.Stmt.executeQuery("select * from member where id='"+id.getText()+"'");
						
						if(rs.next()) {
							dispose();
							if(check.isSelected()) {
								FileWriter out = new FileWriter("C:\\Program Files\\id.txt");
								BufferedWriter bw = new BufferedWriter(out);
								bw.write(id.getText());
								bw.newLine();
								bw.close();
							} else {
								File f = new File("C:\\Program Files\\id.txt");
								f.delete();
							}
							new memberMain();
						} else {
							JOptionPane.showMessageDialog(null, "아이디 혹은 비밀번호가 잘못되었습니다.");
						}
					}
				} catch(Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		su.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				new memberInsert();
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
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
}
