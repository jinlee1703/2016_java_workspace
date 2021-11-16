package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class ledgerChart extends JFrame {
	JLabel label1 = new JLabel();
	JLabel label2 = new JLabel();
	
	public ledgerChart() {
		setTitle("수입 & 지출 차트 보기");
		setSize(730, 500);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new EmptyBorder(20, 50, 20, 20));
		
		Chart ch = new Chart();
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		p1.setBorder(new EmptyBorder(20, 0, 100, 0));
		p1.setPreferredSize(new Dimension(150, 50));
		label1.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label1.setForeground(Color.blue);
		label2.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label2.setForeground(Color.red);
		
		p1.add(label1);
		p1.add(label2);
		
		p.add(ch, BorderLayout.CENTER);
		p.add(p1, BorderLayout.EAST);
		
		add(p);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	class Chart extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			
			try {
				int sum = 0;
				ResultSet rs = DBInterface.Stmt.executeQuery("select * from ledger where memberid='"+Login.id.getText()+"'");
				while(rs.next()) {
					sum = sum + rs.getInt(6);
				}
				
				int psum = 0;
				rs = DBInterface.Stmt.executeQuery("select * from ledger where memberid='"+Login.id.getText()+"' and division='수입'");
				while(rs.next()) {
					psum = psum + rs.getInt(6);
				}
				
				int msum = 0;
				rs = DBInterface.Stmt.executeQuery("select * from ledger where memberid='"+Login.id.getText()+"' and division='지출'");
				while(rs.next()) {
					msum = msum + rs.getInt(6);
				}
				
				String pt = "#.##";
				DecimalFormat df = new DecimalFormat(pt);
				
				double psump = (double) psum / sum * 100;
				double msump = (double) msum / sum * 100;
				
				String a = df.format(psump);
				String b = df.format(msump);
				
				int bc = (int) Math.round(3.6*psump);
				int rc = (int) Math.round(3.6*msump);
				
				label1.setText("수입 : "+psum+"원("+a+"%)");
				label2.setText("지출 : "+msum+"원("+b+"%)");
				
				g.setColor(Color.blue);
				g.fillArc(0, 0, getHeight(), getHeight(), 0, bc);
				
				g.setColor(Color.red);
				g.fillArc(0, 0, getHeight(), getHeight(), bc, rc);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

