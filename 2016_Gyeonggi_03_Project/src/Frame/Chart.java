package Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Project_DBInterface.DBInterface;

public class Chart extends JFrame {
	int in,ex,total;
	int ch1, ch2;
	double t1, t2;
	
	public Chart() {
		setTitle("수입 & 지출 차트 보기");
		setSize(600, 380);
		setLocationRelativeTo(null);
		
		chartPanel p = new chartPanel();
		add(p);
		
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
	
	class chartPanel extends JPanel {
		public void intSetting() {
			try {
				ResultSet rs = DBInterface.Stmt.executeQuery("select sum(amount) from ledger where memberid='"+Login.id.getText()+"' and division='수입'");
				rs.next();
				in = rs.getInt(1);
				
				rs = DBInterface.Stmt.executeQuery("select sum(amount) from ledger where memberid='"+Login.id.getText()+"' and division='지출'");
				rs.next();
				ex = rs.getInt(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			Font font = new Font("Sanserif", Font.BOLD, 14);
			DecimalFormat df = new DecimalFormat("#.##");
			
			intSetting();
			total=in+ex;
			
			t1 = (double) in / total * 100;
			t2 = (double) ex / total * 100;
			
			ch1 = (int) Math.round(t1*3.6);
			ch2 = (int) Math.round(t2*3.6);
			
			g.setColor(Color.blue);
			g.fillArc(60, 20, 300, 300, 0, ch1);
			g.drawString("수입 : "+in+"("+df.format(t1)+"%)", 400, 80);
			
			g.setColor(Color.red);
			g.fillArc(60, 20, 300, 300, ch1, ch2);
			g.drawString("지출 : "+in+"("+df.format(t2)+"%)", 400, 100);
		}
	}
}
