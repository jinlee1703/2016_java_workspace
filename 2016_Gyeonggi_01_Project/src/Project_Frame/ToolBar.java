package Project_Frame;

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
	JButton btn1 = new JButton(new ImageIcon(getClass().getClassLoader().getResource("menu/계산기.png")));
	JButton btn2 = new JButton(new ImageIcon(getClass().getClassLoader().getResource("menu/상태.png")));
	JButton btn3 = new JButton(new ImageIcon(getClass().getClassLoader().getResource("menu/메인.png")));
	JButton btn4 = new JButton(new ImageIcon(getClass().getClassLoader().getResource("menu/종료.png")));
	JFrame frame;
	
	public ToolBar(JFrame frame) {
		this.frame = frame;
		frame.setLayout(new BorderLayout());
		
		tb.add(btn1);
		btn1.setToolTipText("계산기를 실행시킵니다.");
		
		tb.add(btn2);
		btn2.setToolTipText("지금 상태를 보여줍니다.");
		
		tb.add(btn3);
		btn3.setToolTipText("메인메뉴로 이동합니다.");
		
		tb.add(btn4);
		btn4.setToolTipText("시스템을 종료합니다.");
				
		frame.add(tb, BorderLayout.NORTH);
		
		//tb.setFloatable(false);
		
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn4.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = (JButton) e.getSource();
		
		if(btn==btn1) {
			Runtime run = Runtime.getRuntime();
			Process calc = null;
			try {
				calc = run.exec("calc");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				calc.waitFor();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if(btn==btn2) {
			try {
				ResultSet rs = DBInterface.Stmt.executeQuery("select * from ledger where memberid='"+Login.id.getText()+"'");
				
				int sum=0;
				int psum = 0;
				int msum = 0;
				
				while(rs.next()) {
					if(rs.getString(3).equals("수입")) {
						psum = psum + rs.getInt(6);
						sum = sum + rs.getInt(6);
					} else if(rs.getString(3).equals("지출")) {
						msum = msum + rs.getInt(6);
						sum = sum - rs.getInt(6);
					}
				}
				String r;
				
				if(sum>=0) {
					r = "<html><font color=#000FFF>흑자 : ";
				} else {
					r = "<html><font color=#F00000>적자 : ";
				}
				
				JOptionPane.showMessageDialog(null, "총 수입액 : "+psum+"원"+"\n"+"총 지출액 : "+msum+"원"+"\n"+r+sum+"원</font>");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if(btn==btn3) {
			this.dispose();
			new Main();
		} else if(btn==btn4) {
			System.exit(0);
		}
	}
	
	
}
