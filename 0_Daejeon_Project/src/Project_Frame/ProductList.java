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

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Project_DBInterface.DBInterface;

public class ProductList extends JFrame implements ActionListener {
	JLabel[] img;
	
	public ProductList() {
		setSize(1000, 800);
		setLocationRelativeTo(null);

		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.setBackground(Color.white);
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from product");
			rs.last();
			img = new JLabel[rs.getRow()];
			rs.beforeFirst();
			
			while(rs.next()) {
				JPanel pp = new JPanel();
				pp.setBackground(Color.white);
				pp.setLayout(new BoxLayout(pp, BoxLayout.Y_AXIS));
				String path = System.getProperty("user.dir")+"\\지급자료\\images\\"+rs.getString(2)+".PNG";
				path = path.replace('\\', '/');
				String aa = rs.getString(2);
				img[rs.getRow()-1] = new JLabel(new ImageIcon(path));
				img[rs.getRow()-1].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						new PurchaseInsert(aa);
					}
				});
				
				pp.add(img[rs.getRow()-1]);
				pp.add(new JLabel(rs.getString(2)));
				pp.add(new JLabel(rs.getString(4)));
				
				p.add(pp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		add(p);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
