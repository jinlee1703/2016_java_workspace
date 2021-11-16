package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class TicketReservation extends JFrame implements ActionListener {
	JLabel[] l = new JLabel[5];
	String[] ln = {"서울역","대전역","대구역","광주역","부산역"};
	JLabel[] ll = new JLabel[3];
	String[] lln = {"출발역","도착역","열차종류"};
	JLabel text1 = new JLabel("서울역");
	JLabel text2 = new JLabel("부산역");
	static JComboBox combo = new JComboBox();
	JButton btn = new JButton("열차조회");
	JPopupMenu menu = new JPopupMenu();
	JMenuItem it1 = new JMenuItem("출발역");
	JMenuItem it2 = new JMenuItem("도착역");
	static JLabel start,end;
	JLabel aa;
	
	public TicketReservation() {
		setTitle("승차권예매");
		setSize(320, 550);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel();
		p.setBorder(new EmptyBorder(5, 5, 5, 5));
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		
		ImagePanel ip = new ImagePanel();
		
		JPanel p1 = new JPanel(new GridLayout(3, 2, 0, 10));
		p1.setBorder(new EmptyBorder(0, 0, 0, 80));
		for(int i=0; i<3; i++) {
			ll[i] = new JLabel(lln[i]+" :");
		}
		JPanel pp = new JPanel(new BorderLayout());
		pp.setBorder(new EmptyBorder(0, 0, 0, -19));
		pp.add(combo);
		p1.add(ll[0]); p1.add(text1);
		p1.add(ll[1]); p1.add(text2);
		p1.add(ll[2]); p1.add(pp);
		
		JPanel p2 = new JPanel();
		p2.setBorder(new EmptyBorder(20, 0, 10, 0));
		p2.add(btn);
		
		p.add(p1);
		p.add(p2);
		
		Init();
		
		add(ip, BorderLayout.CENTER);
		add(p, BorderLayout.SOUTH);
		
		btn.setPreferredSize(new Dimension(100, 30));
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					dispose();
					new CurrentTrain();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		menu.add(it1);
		menu.add(it2);
		menu.addSeparator();

		it1.addActionListener(this);
		it2.addActionListener(this);
		
		stationSelectD(l[0]);
		stationSelectA(l[4]);
		
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JMenuItem it = (JMenuItem) e.getSource();
		
		if(it==it1) {
			stationSelectD(aa);
		} else if(it==it2) {
			stationSelectA(aa);
		}
	}
	
	class ImagePanel extends JPanel {
		Image img = new ImageIcon(getClass().getClassLoader().getResource("TrainMap.jpg")).getImage();
		
		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			g.drawImage(img, 15, 15, getWidth()-30, getHeight()-30, this);
		}
		
		public ImagePanel() {
			setLayout(null);
			
			for(int i=0; i<ln.length; i++) {
				l[i] = new JLabel(ln[i]);
				l[i].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						if(e.getButton()==3) {
							menu.show((Component) e.getSource(), e.getX(), e.getY());
							aa=(JLabel) e.getSource();
						}
					}
				});
				add(l[i]);
			}
			
			l[0].setBounds(80, 80, 50, 20);
			l[1].setBounds(110, 140, 50, 20);
			l[2].setBounds(200, 190, 50, 20);
			l[3].setBounds(70, 240, 50, 20);
			l[4].setBounds(230, 240, 50, 20);
		}
	}
	
	public void stationSelectD(JLabel d) {
		if(d==start || d==end){
			JOptionPane.showMessageDialog(null, "이미 지정하신 역입니다.");
			return;
		}
		start=d;
		for(int i=0; i<5; i++) {
			if(l[i]==start)l[i].setForeground(Color.red);
			else if(l[i]==end)l[i].setForeground(Color.blue);
			else{l[i].setForeground(Color.black);}
		}
		
		text1.setText(start.getText());
	}
	
	public void stationSelectA(JLabel a) {
		if(a==start || a==end){
			JOptionPane.showMessageDialog(null, "이미 지정하신 역입니다.");
			return;
		}
		end=a;
		for(int i=0; i<5; i++) {
			if(l[i]==start)l[i].setForeground(Color.red);
			else if(l[i]==end)l[i].setForeground(Color.blue);
			else{l[i].setForeground(Color.black);}
		}
		
		text2.setText(end.getText());
	}
	
	public void Init() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from train");
			
			combo.addItem("전체");
			
			while(rs.next()) {
				combo.addItem(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
