package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

public class Map extends JFrame {
	JLabel[] l = new JLabel[5];
	String[] ln = {"서울역","대전역","대구역","부산역","광주역"};
	JLabel sl = new JLabel();
	JLabel el = new JLabel();
	static String[] cn = {"전체","KTX","새마을호","무궁화호"};
	static JComboBox combo = new JComboBox(cn);
	JPopupMenu menu = new JPopupMenu();
	JButton btn = new JButton("열차조회");
	JMenuItem it1 = new JMenuItem("출발역");
	JMenuItem it2 = new JMenuItem("도착역");
	static JLabel ss, es;
	JLabel selectStation;
	
	public Map() {
		setTitle("승차권예매");
		setSize(300, 600);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		MapPanel p1 = new MapPanel();
		
		JPanel p2 = new JPanel(new GridLayout(3, 2, 10, 10));
		JPanel cp = new JPanel(new BorderLayout());
		cp.setBorder(new EmptyBorder(0, 0, 0, -19));
		cp.add(combo);
		p2.add(new JLabel("출발역 :")); p2.add(sl);
		p2.add(new JLabel("도착역 :")); p2.add(el);
		p2.add(new JLabel("열차종류 :")); p2.add(cp);
		
		JPanel p3 = new JPanel();
		btn.setPreferredSize(new Dimension(100, 30));
		p3.add(btn);
		
		JPanel pp = new JPanel(new BorderLayout());
		pp.setBorder(new EmptyBorder(5, 5, 10, 25));
		pp.add(p2, BorderLayout.CENTER);
		pp.add(p3, BorderLayout.SOUTH);
		
		p.add(p1, BorderLayout.CENTER);
		p.add(p2, BorderLayout.SOUTH);
		
		add(p);
		
		menu.add(it1);
		menu.add(it2);
		menu.addSeparator();
		
		it1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				StartStationSelect(selectStation);
			}
		});
		
		it2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				endStationSelect(selectStation);
			}
		});
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				try {
					new MapResult();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		StartStationSelect(l[0]);
		endStationSelect(l[3]);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void StartStationSelect(JLabel sn) {
		if(sn==ss || sn==es) {
			JOptionPane.showMessageDialog(null, "이미 지정하신 역입니다.");
			return;
		}
		ss=sn;
		for(int i=0; i<5; i++) {
			if(l[i]!=ss && l[i]!=es) {
				l[i].setForeground(Color.black);
			}
		}
		sl.setText(sn.getText());
	}
	
	public void endStationSelect(JLabel sn) {
		if(sn==ss || sn==es) {
			JOptionPane.showMessageDialog(null, "이미 지정하신 역입니다.");
			return;
		}
		es=sn;
		for(int i=0; i<5; i++) {
			if(l[i]!=ss && l[i]!=es) {
				l[i].setForeground(Color.black);
			}
		}
		el.setText(sn.getText());
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
			for(int i=0; i<5; i++) {
				l[i] = new JLabel(ln[i]);
				l[i].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						menu.show((Component) e.getSource(), e.getX(), e.getY());
						selectStation = (JLabel) e.getSource();
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
}
