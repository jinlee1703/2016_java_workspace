package Frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import Project_DBInterface.DBInterface;

public class ToolBar extends JFrame implements ActionListener {
	JToolBar tb = new JToolBar();
	String[] bn = {"����","����","����","����"};
	JButton[] btn = new JButton[4];
	JFrame frame;
	
	public ToolBar(JFrame frame) {
		this.frame = frame;
		setLayout(new BorderLayout());
		
		for(int i=0; i<4; i++) {
			String path = System.getProperty("user.dir")+"\\res\\menu\\"+bn[i]+".png";
			btn[i] = new JButton(new ImageIcon(path));
			btn[i].addActionListener(this);
			tb.add(btn[i]);
		}
		
		btn[0].setToolTipText("���⸦ �����ŵ�ϴ�.");
		btn[1].setToolTipText("���� ���¸� �����ݴϴ�.");
		btn[2].setToolTipText("���θ޴��� �̵��մϴ�.");
		btn[3].setToolTipText("�ý����� �����մϴ�.");
		
		frame.add(tb, BorderLayout.NORTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton bt = (JButton) e.getSource();
		if(bt==btn[0]) {
			try {
				Process calc = Runtime.getRuntime().exec("calc");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if(bt==btn[1]) {
			try {
				ResultSet rs = DBInterface.Stmt.executeQuery("select * from ledger where memberid='"+Login.id.getText()+"'");
				
				String st;
				int p=0, m=0, s=0;
				
				while(rs.next()) {
					if(rs.getString(3).equals("����")) {
						p+=rs.getInt(6);
					} else {
						m+=rs.getInt(6);
					}
				}
				s=p-s;
				
				if(s>=0) {
					st = "<html><font color=#000FFF>���� : "+s+"��</font>";
				} else {
					st = "<html><font color=#F00000>���� : "+s+"��</font>";
				}
				
				JOptionPane.showMessageDialog(null, "�� ���Ծ� : "+p+"��\n"+"�� ����� : "+m+"��\n"+st);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if(bt==btn[2]) {
			this.dispose();
			new Main();
		} else if(bt==btn[3]) {
			System.exit(0);
		}
	}
}
