package Project_Frame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Project_DBInterface.DBInterface;

public class productSelect extends JFrame implements ActionListener {
	JPanel[] pp;
	JPanel p;
	
	public productSelect() {
		setSize(1100, 830);
		setLocationRelativeTo(null);
		
		try {
			Init();
		} catch (Exception e) {
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
	
	public void Init() throws Exception {
		ResultSet c = DBInterface.Stmt.executeQuery("select count(*) from product"); c.next();
		int cc = c.getInt(1);
		JPanel[] pp = new JPanel[cc];
		JLabel[] img = new JLabel[cc];
		JLabel[] name = new JLabel[cc];
		JLabel[] cost = new JLabel[cc];
		
		ResultSet rs = DBInterface.Stmt.executeQuery("select * from product");
		
		p = new JPanel(new FlowLayout());
		
		int cnt = 0;
		while(rs.next()) {
			String pn = rs.getString(2);
			img[cnt] = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("images/"+pn+".PNG")));
			name[cnt] = new JLabel(rs.getString(2));
			cost[cnt] = new JLabel(rs.getString(4));
			
			pp[cnt] = new JPanel(new FlowLayout(FlowLayout.LEFT));
			pp[cnt].setPreferredSize(new Dimension(250, 400));
			name[cnt].setPreferredSize(new Dimension(250, 20));
			cost[cnt].setPreferredSize(new Dimension(250, 20));
			
			pp[cnt].add(img[cnt]);
			pp[cnt].add(name[cnt]);
			pp[cnt].add(cost[cnt]);
			
			p.add(pp[cnt]);
			
			pp[cnt].setBackground(Color.white);
			p.setBackground(Color.white);
			
			img[cnt].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					for(int i=0; i<cc; i++) {
						if(e.getSource().equals(img[i])) {
							new purchaseInsert(name[i].getText());
						}
					}
				}
			});
			
			cnt+=1;
		}
	}
}
