package Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MapSelect extends JFrame implements ActionListener {
	JLabel[] l = new JLabel[5];
	String[] ln = {"서울역","대전역","대구역","부산역","광주역"};
	static String[] cn = {"전체","KTX","새마을호","무궁화호"};
	JLabel l1 = new JLabel("서울역");
	JLabel l2 = new JLabel("부산역");
	static JComboBox combo = new JComboBox(cn);
	JButton btn = new JButton("열차조회");
	String path = System.getProperty("user.dir")+"\\res\\TrainMap\\"+".jpg";
	JPopupMenu menu = new JPopupMenu();
	JMenuItem it1 = new JMenuItem("출발역");
	JMenuItem it2 = new JMenuItem("도착역");
	static JLabel ss, es;
	JLabel aa;
	
	public MapSelect() {
		setTitle("승차권예매");
		setSize(320, 550);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		MapPanel p1 = new MapPanel();
		
		JPanel pp = new JPanel(new BorderLayout());
		
		JPanel p2 = new JPanel(new GridLayout(3, 2, 10, 10));
		p2.setBorder(new EmptyBorder(5, 5, 5, 30));
		p2.add(new JLabel("출발역 :"));
		p2.add(l1);
		p2.add(new JLabel("도착역 :"));
		p2.add(l2);
		p2.add(new JLabel("열차종류 :"));
		JPanel cp = new JPanel(new BorderLayout());
		cp.setBorder(new EmptyBorder(0, 0, 0, -19));
		cp.add(combo);
		p2.add(cp);
		
		JPanel p3 = new JPanel(new FlowLayout());
		p3.setBorder(new EmptyBorder(5, 5, 5, 5));
		p3.add(btn);
		
		
		pp.add(p2, BorderLayout.CENTER);
		pp.add(p3, BorderLayout.SOUTH);
		
		p.add(p1, BorderLayout.CENTER);
		p.add(pp, BorderLayout.SOUTH);
		
		add(p);
		
		menu.add(it1);
		menu.add(it2);
		menu.addSeparator();
		
		it1.addActionListener(this);
		it2.addActionListener(this);
		
		startStation(l[0]);
		endStation(l[3]);
		
		btn.setPreferredSize(new Dimension(100, 30));
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				try {
					new List();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JMenuItem it = (JMenuItem) e.getSource();
		
		if(it==it1) {
			startStation(aa);
		} else {
			endStation(aa);
		}
	}
	
	class MapPanel extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			String path = System.getProperty("user.dir")+"\\res\\TrainMap.jpg";
			path = path.replace('\\', '/');
			g.drawImage(new ImageIcon(path).getImage(), 15, 15, this.getWidth()-30, this.getHeight()-30, this);
		}
		
		public MapPanel() {
			setLayout(null);
			
			for(int i=0; i<5; i++) {
				l[i] = new JLabel(ln[i]);
				l[i].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						if(e.getButton()==1) {
							menu.show((Component) e.getSource(), e.getX(), e.getY());
							aa = (JLabel) e.getSource();
						}
					}
				});
				add(l[i]);
			}
			
			l[0].setBounds(80, 80, 50, 20);
			l[1].setBounds(110, 140, 50, 20);
			l[2].setBounds(200, 190, 50, 20);
			l[3].setBounds(230, 240, 50, 20);
			l[4].setBounds(70, 240, 50, 20);
		}
	}
	
	public void startStation(JLabel a) {
		if(a==ss || a==es) {
			JOptionPane.showMessageDialog(null, "이미 지정하신 역입니다.");
			return;
		}
		ss=a;
		ss.setForeground(Color.red);
		for(int i=0; i<5; i++) {
			if(l[i]!=ss && l[i]!=es) {
				l[i].setForeground(Color.black);
			}
		}
		l1.setText(ss.getText());
	}
	
	public void endStation(JLabel a) {
		if(a==ss || a==es) {
			JOptionPane.showMessageDialog(null, "이미 지정하신 역입니다.");
			return;
		}
		es=a;
		es.setForeground(Color.blue);
		for(int i=0; i<5; i++) {
			if(l[i]!=ss && l[i]!=es) {
				l[i].setForeground(Color.black);
			}
		}
		l2.setText(es.getText());
	}
}
