package Project_Frame;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class Products extends JFrame {
	JPanel p;
	JPanel[] pp;
	JLabel[] img;
	JLabel[] fn;
	JLabel[] fp;
	static String proname;
	
	public Products() {
		setSize(1000, 800);
		setLocationRelativeTo(null);
		
		p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.setBorder(new EmptyBorder(5, 5, 5, 5));
		p.setBackground(Color.white);
		p.setOpaque(true);
		
		Setting();
		add(p);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void Setting() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from product");
			rs.last();
			pp = new JPanel[rs.getRow()];
			img = new JLabel[rs.getRow()];
			fn = new JLabel[rs.getRow()];
			fp = new JLabel[rs.getRow()];
			
			int cc=rs.getRow();
			rs.beforeFirst();
			
			int cnt=0;
			
			DecimalFormat df = new DecimalFormat("#,##0");
			
			while(rs.next()) {
				pp[cnt] = new JPanel();
				pp[cnt].setLayout(new BoxLayout(pp[cnt], BoxLayout.Y_AXIS));
				pp[cnt].setBackground(Color.white);
				pp[cnt].setOpaque(true);
				
				img[cnt] = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("images/"+rs.getString(2)+".PNG")));
				fn[cnt] = new JLabel(rs.getString(2));
				
				fp[cnt] = new JLabel(df.format(rs.getInt(4)));
				
				pp[cnt].add(img[cnt]);
				pp[cnt].add(fn[cnt]);
				pp[cnt].add(fp[cnt]);
				
				p.add(pp[cnt]);
				
				int n = cnt;
				
				img[cnt].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						proname=fn[n].getText();
						new purchase();
					}
				});
				
				cnt++;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
