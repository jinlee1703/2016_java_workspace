package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class Login extends JFrame {
	static JTextField id = new JTextField(12);
	JPasswordField pw = new JPasswordField(12);
	JButton btn = new JButton("Login");
	JPanel p1 = new JPanel();
	GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	
	public Login() {
		setTitle("로그인");
		setSize(200, 120);
		setLocationRelativeTo(null);
		
		p1.setLayout(gbl);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		
		addGrid(new JLabel("ID :"), 0, 0, 1, 1);
		addGrid(id,1,0,2,1);
		addGrid(new JLabel("PW :"), 0, 1, 1, 1);
		addGrid(pw,1,1,2,1);
		
		add(p1, BorderLayout.CENTER);
		add(btn, BorderLayout.SOUTH);
		p1.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(id.getText().equals("") || pw.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "ID 또는 PW를 확인해주세요.", "", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						ResultSet rs = DBInterface.Stmt.executeQuery("select * from user where uID='"+id.getText()+"' and uPW='"+pw.getText()+"'");
						
						if(rs.next()) {
							JOptionPane.showMessageDialog(null, rs.getString(4)+"님 환영합니다.");
							dispose();
							new Main();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	void addGrid(Component c, int x, int y, int gridw, int gridh) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = gridw;
		gbc.gridheight = gridh;
		gbl.setConstraints(c, gbc);
		p1.add(c);
	}
}
