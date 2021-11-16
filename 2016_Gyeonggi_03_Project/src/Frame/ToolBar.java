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
	String[] bn = {"계산기","상태","메인","종료"};
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
		
		btn[0].setToolTipText("계산기를 실행시킵니다.");
		btn[1].setToolTipText("지금 상태를 보여줍니다.");
		btn[2].setToolTipText("메인메뉴로 이동합니다.");
		btn[3].setToolTipText("시스템을 종료합니다.");
		
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
					if(rs.getString(3).equals("수입")) {
						p+=rs.getInt(6);
					} else {
						m+=rs.getInt(6);
					}
				}
				s=p-s;
				
				if(s>=0) {
					st = "<html><font color=#000FFF>흑자 : "+s+"원</font>";
				} else {
					st = "<html><font color=#F00000>적자 : "+s+"원</font>";
				}
				
				JOptionPane.showMessageDialog(null, "총 수입액 : "+p+"원\n"+"총 지출액 : "+m+"원\n"+st);
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
