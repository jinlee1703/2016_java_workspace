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

public class Map extends JFrame implements ActionListener {
	JPopupMenu menu = new JPopupMenu();
	JMenuItem it1 = new JMenuItem("��߿�");
	JMenuItem it2 = new JMenuItem("������");
	JLabel[] l = new JLabel[5];
	String[] ln = {"���￪","������","�뱸��","�λ꿪","���ֿ�"};
	static JLabel startStation = new JLabel();
	static JLabel endStation = new JLabel();
	static String[] cn = {"��ü","KTX","������ȣ","����ȭȣ"};
	static JComboBox combo = new JComboBox(cn);
	JButton btn = new JButton("������ȸ");
	JLabel ss;
	JLabel es;
	JLabel sn;
	
	public Map() {
		setTitle("������ ����");
		setSize(300, 550);
		setLocationRelativeTo(null);
		
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new EmptyBorder(5, 5, 10, 5));
		
		MapPanel p1 = new MapPanel();
		
		JPanel p2 = new JPanel(new GridLayout(3, 2));
		p2.add(new JLabel("��߿� :")); p2.add(startStation);
		p2.add(new JLabel("������ :")); p2.add(endStation);
		p2.add(new JLabel("�������� :"));
		JPanel cp = new JPanel(new BorderLayout());
		cp.setPreferredSize(new Dimension(100, 30));
		cp.setBorder(new EmptyBorder(0, 0, 0, -19));
		cp.add(combo);
		p2.add(cp);
		
		JPanel p3 = new JPanel();
		btn.setPreferredSize(new Dimension(100, 30));
		p3.add(btn);
		
		JPanel pp = new JPanel(new BorderLayout());
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
		
		SelectStart(l[0]);
		SelectEnd(l[3]);
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				try {
					new Result();
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
			SelectStart(sn);
		} else {
			SelectEnd(sn);
		}
	}
	
	public void SelectStart(JLabel aa) {
		if(aa.equals(ss) || aa.equals(es)) {
			JOptionPane.showMessageDialog(null, "�̹� �����Ͻ� ���Դϴ�.");
			return;
		}
		ss = aa;
		ss.setForeground(Color.red);
		for(int i=0; i<5; i++) {
			if(! l[i].equals(ss) && ! l[i].equals(es)) {
				l[i].setForeground(Color.black);
			}
		}
		startStation.setText(ss.getText());
	}
	
	public void SelectEnd(JLabel aa) {
		if(aa.equals(ss) || aa.equals(es)) {
			JOptionPane.showMessageDialog(null, "�̹� �����Ͻ� ���Դϴ�.");
			return;
		}
		es = aa;
		es.setForeground(Color.blue);
		for(int i=0; i<5; i++) {
			if(! l[i].equals(ss) && ! l[i].equals(es)) {
				l[i].setForeground(Color.black);
			}
		}
		endStation.setText(es.getText());
	}
	
	class MapPanel extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			String path = System.getProperty("user.dir")+"\\�����ڷ�\\TrainMap.jpg";
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
						sn = (JLabel) e.getSource();
						menu.show((Component) e.getSource(), e.getX(), e.getY());
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
