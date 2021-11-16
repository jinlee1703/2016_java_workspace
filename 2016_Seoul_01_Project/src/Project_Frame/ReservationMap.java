package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
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

public class ReservationMap extends JFrame implements ActionListener {
	Image img = new ImageIcon(getClass().getClassLoader().getResource("Data/TrainMap.jpg")).getImage();
	JLabel label1 = new JLabel("출발역 :");
	JLabel label2 = new JLabel("도착역 :");
	JLabel label3 = new JLabel("열차종류 :");
	JLabel text1 = new JLabel("서울역");
	JLabel text2 = new JLabel("부산역");
	String[] cn = {"전체","KTX","새마을호","무궁화호"};
	JComboBox combo = new JComboBox(cn);
	JButton btn = new JButton("열차조회");
	JLabel[] Area = new JLabel[5];
	String[] an = {"서울역","대전역","대구역","부산역","광주역"};
	JPopupMenu menu = new JPopupMenu();
	JMenuItem it1 = new JMenuItem("출발역");
	JMenuItem it2 = new JMenuItem("도착역");
	Object aa;
	
	public ReservationMap() {
		setTitle("승차권예매");
		setSize(300, 520);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBorder(new EmptyBorder(20, 20, 20, 20));
		ImagePanel ip = new ImagePanel();
		p1.add(ip);
		
		JPanel p2 = new JPanel(new BorderLayout());
		
		JPanel p21 = new JPanel(new GridLayout(3, 2, 0, 5));
		p21.setBorder(new EmptyBorder(0, 10, 20, 50));
		p21.add(label1); p21.add(text1);
		p21.add(label2); p21.add(text2);
		p21.add(label3); 
		
		JPanel p211 = new JPanel(new BorderLayout());
		p211.setBorder(new EmptyBorder(0, 0, 0, -19));
		p211.add(combo);
		p21.add(p211);
		
		JPanel p22 = new JPanel(new FlowLayout());
		p22.setBorder(new EmptyBorder(0, 0, 10, 0));
		btn.setPreferredSize(new Dimension(100, 30));
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String ds = null;
				String as = null;
				
				for(int i=0; i<5; i++) {
					if(Area[i].getForeground()!=Color.red) {
						ds = Area[i].getText();
					}
				}
				
				for(int i=0; i<5; i++) {
					if(Area[i].getForeground()!=Color.blue) {
						as = Area[i].getText();
					}
				}
				
				new ReservationInsert(ds, as);
			}
		});
		p22.add(btn);
		
		p2.add(p21, BorderLayout.CENTER);
		p2.add(p22, BorderLayout.SOUTH);
		
		p.add(p1, BorderLayout.CENTER);
		p.add(p2, BorderLayout.SOUTH);
		
		add(p);
		
		for(int i=0; i<5; i++) {
			Area[i] = new JLabel(an[i]);
			Area[i].setSize(50, 20);
			Area[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					if(e.getButton()==3) {
						menu.show((Component) e.getSource(), e.getX(), e.getY());
						aa=e.getSource();
					}
				}
			});
			ip.add(Area[i]);
		}
		
		Area[0].setLocation(55, 55);
		Area[1].setLocation(90, 135);
		Area[2].setLocation(165, 165);
		Area[3].setLocation(190, 205);
		Area[4].setLocation(50, 205);
		
		ip.add(Area[0]);
		ip.add(Area[1]);
		ip.add(Area[2]);
		ip.add(Area[3]);
		ip.add(Area[4]);
		
		Area[0].setForeground(Color.red);
		Area[3].setForeground(Color.blue);
		
		menu.add(it1);
		menu.add(it2);
		menu.addSeparator();
		
		it1.addActionListener(this);
		it2.addActionListener(this);
		
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
		Image img = new ImageIcon(getClass().getClassLoader().getResource("Data/TrainMap.jpg")).getImage();
		public ImagePanel() {
			// TODO Auto-generated constructor stub
			setLayout(null);
		}
		
		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
			
		}
	}
	
	public void stationSelectD(Object d) {
		for(int i=0; i<5; i++) {
			if(Area[i].getForeground()==Color.red || Area[i].getForeground()==Color.blue && Area[i].equals(aa)) {
				JOptionPane.showMessageDialog(null, "이미 지정하신 역입니다.");
				return;
			}
		}
		
		for(int i=0; i<5; i++) {
			if(Area[i].getForeground()!=Color.blue) {
				Area[i].setForeground(Color.black);
			}
		}
		
		for(int i=0; i<5; i++) {
			if(Area[i].equals(aa)) {
				Area[i].setForeground(Color.red);
				text1.setText(Area[i].getText());
			}
		}
	}
	
	public void stationSelectA(Object a) {
		for(int i=0; i<5; i++) {
			if(Area[i].getForeground()==Color.red || Area[i].getForeground()==Color.blue && Area[i].equals(aa)) {
				JOptionPane.showMessageDialog(null, "이미 지정하신 역입니다.");
				return;
			}
		}
		
		for(int i=0; i<5; i++) {
			if(Area[i].getForeground()!=Color.red) {
				Area[i].setForeground(Color.black);
			}
		}

		for(int i=0; i<5; i++) {
			if(Area[i].equals(aa)) {
				Area[i].setForeground(Color.blue);
				text2.setText(Area[i].getText());
			}
		}
	}
}
