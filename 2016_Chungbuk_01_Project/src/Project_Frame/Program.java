package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Program extends JFrame implements ActionListener {
	JTabbedPane tab = new JTabbedPane();
	int mn = 0;
	
	public Program() {
		setTitle("영화예매프로그램");
		setSize(800, 600);
		setLocationRelativeTo(null);
		
		MainPanel p1 = new MainPanel();
		MovieSelect p2 = new MovieSelect();
		TicketSelect p3 = new TicketSelect();
		
		add(p3);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	class MainPanel extends JPanel {
		JLabel label1 = new JLabel("우리 극장에 오신것을 환영합니다.!");
		JLabel label2 = new JLabel("고객님들께 편안한 시설과 친절한 서비스를 제공하기 위해 노력하겠습니다.");
		JLabel img = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("Data/cinema.JPG")));
		JButton btn = new JButton("영화보기");
		
		public MainPanel() {
			this.setLayout(new FlowLayout());
			
			label1.setPreferredSize(new Dimension(500, 20));
			label1.setHorizontalAlignment(label1.CENTER);
			label2.setPreferredSize(new Dimension(500, 20));
			label2.setHorizontalAlignment(label1.CENTER);
			label2.setForeground(Color.blue);
			img.setPreferredSize(new Dimension(500, 300));
			btn.setPreferredSize(new Dimension(500, 30));
			
			add(label1);
			add(label2);
			add(img);
			add(btn);
			
			btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					tab.setEnabledAt(0, true);
					tab.setEnabledAt(1, true);
				}
			});
		}
	}
	
	class MovieSelect extends JPanel implements ActionListener {
		JLabel label1 = new JLabel("상영영화");
		JLabel img1 = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("Data/warcraft.JPG")));
		JLabel img2 = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("Data/xman.JPG")));
		JLabel img3 = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("Data/angrybird.JPG")));
		JButton btn1 = new JButton("선택");
		JButton btn2 = new JButton("선택");
		JButton btn3 = new JButton("선택");
		
		public MovieSelect() {
			this.setLayout(new BorderLayout());
			
			label1.setForeground(Color.blue);
			label1.setBorder(new EmptyBorder(0, 0, 20, 0));
			
			JPanel p = new JPanel(new FlowLayout());
			
			JPanel p1 = new JPanel(new BorderLayout());
			JPanel p11 = new JPanel(new BorderLayout());
			p11.setBorder(new EmptyBorder(20, 50, 20, 50));	p11.add(btn1);
			p1.add(img1, BorderLayout.CENTER); p1.add(p11, BorderLayout.SOUTH);
			
			JPanel p2 = new JPanel(new BorderLayout());
			JPanel p22 = new JPanel(new BorderLayout());
			p22.setBorder(new EmptyBorder(20, 50, 20, 50));	p22.add(btn2);
			p2.add(img2, BorderLayout.CENTER); p2.add(p22, BorderLayout.SOUTH);
			
			JPanel p3 = new JPanel(new BorderLayout());
			JPanel p33 = new JPanel(new BorderLayout());
			p33.setBorder(new EmptyBorder(20, 50, 20, 50));	p33.add(btn3);
			p3.add(img3, BorderLayout.CENTER); p3.add(p33, BorderLayout.SOUTH);
			
			p.add(p1);
			p.add(p2);
			p.add(p3);
			
			add(label1, BorderLayout.NORTH);
			add(p, BorderLayout.CENTER);
			
			btn1.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton bt = (JButton) e.getSource();
			
			if(bt==btn1) {
				mn=1;
			} else if(bt==btn2) {
				mn=2;
			} else if(bt==btn3) {
				mn=3;
			}
		}
	}
	
	class TicketSelect extends JPanel implements ActionListener {
		JLabel label1 = new JLabel("매수를 선택하세요.");
		SpinnerNumberModel sm = new SpinnerNumberModel(2, 1, 4, 1);
		JSpinner spin = new JSpinner(sm);
		JButton btn1 = new JButton("확인");
		JButton btn2 = new JButton("돌아가기");
		
		public TicketSelect() {
			this.setLayout(new BorderLayout());
			
			JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
			label1.setPreferredSize(new Dimension(700, 20));
			spin.setPreferredSize(new Dimension(120, 30));
			p.add(label1);
			p.add(spin);
			
			JPanel p1 = new JPanel(new FlowLayout());
			btn1.setPreferredSize(new Dimension(120, 30));
			btn2.setPreferredSize(new Dimension(120, 30));
			p1.add(btn1); p1.add(btn2);
			
			
			add(p, BorderLayout.CENTER);
			add(p1, BorderLayout.SOUTH);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JButton bt = (JButton) e.getSource();
			
			if(bt==btn1) {
				tab.setEnabledAt(2, true);
			} else {
				
			}
		}
	}
	
	class seatSelect extends JFrame implements ActionListener {
		JLabel label = new JLabel("스크린");
		JLabel label1 = new JLabel("A구역");
		JLabel label2 = new JLabel("B구역");
		JLabel label3 = new JLabel("C구역");
		
		public seatSelect() {
			this.setLayout(new BorderLayout());
			
			JPanel pt = new JPanel(new FlowLayout());
			pt.setBorder(new LineBorder(Color.BLACK));
			pt.add(label);
			
			JPanel p1 = new JPanel(new BorderLayout());
			for(int i=0; i<24; i++) {
				
			}
			
			
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}

