package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Project_DBInterface.DBInterface;

public class ProductList extends JFrame {
	public ProductList() {
		setSize(1000, 800);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.setBackground(Color.white);
		
		JPanel[] pp;
		JLabel[] pi;
		JLabel[] pn;
		JLabel[] pc;
		
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from product");
			rs.last();
			pp = new JPanel[rs.getRow()];
			pi = new JLabel[rs.getRow()];
			pn = new JLabel[rs.getRow()];
			pc = new JLabel[rs.getRow()];
			rs.beforeFirst();
			
			while(rs.next()) {
				int r = rs.getRow()-1;
				pp[r] = new JPanel();
				pp[r].setLayout(new BoxLayout(pp[r], BoxLayout.Y_AXIS));
				pp[r].setBackground(Color.white);
				
				String path = System.getProperty("user.dir") + "\\res\\images\\"+rs.getString(2)+".PNG";
				path = path.replace('\\', '/');
				
				pi[r] = new JLabel(new ImageIcon(path));
				pn[r] = new JLabel(rs.getString(2));
				pc[r] = new JLabel(rs.getString(4));
				
				pp[r].add(pi[r]);
				pp[r].add(pn[r]);
				pp[r].add(pc[r]);
				
				p.add(pp[r]);
				
				pi[r].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						new PurchaseInsert(pn[r].getText());
					}
				});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		add(p);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
