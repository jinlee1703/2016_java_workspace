package Project_Frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Project_DBInterface.DBInterface;

public class Chart extends JFrame {
	public Chart() {
		setTitle("���� & ���� ��Ʈ ����");
		setSize(600, 380);
		setLocationRelativeTo(null);

		add(new ChartPanel());
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				new Main();
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	class ChartPanel extends JPanel {
		int in, out;
		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			try {
				ResultSet rs = DBInterface.Stmt.executeQuery("select sum(amount) from ledger where memberid='admin' and division='����'");
				if(rs.next()) {
					in = rs.getInt(1);
				}
				
				rs = DBInterface.Stmt.executeQuery("select sum(amount) from ledger where memberid='admin' and division='���ߤ̤�'");
				if(rs.next()) {
					out = rs.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			DecimalFormat df = new DecimalFormat("#.##");
			
			int total = in+out;
			
			double t1 = (double) in / total *100;
			double t2 = (double) out / total *100;
			
			int ch1 = (int) Math.round(t1*3.6);
			int ch2 = (int) Math.round(t2*3.6);
			
			g.setColor(Color.blue);
			g.fillArc(60, 20, 300, 300, 0, ch1);
			g.drawString("���� : "+in+"��("+df.format(t1)+")", 400, 80);
			
			g.setColor(Color.red);
			g.fillArc(60, 20, 300, 300, ch1, ch2);
			g.drawString("���� : "+out+"��("+df.format(t2)+")", 400, 100);
		}
	}
}
