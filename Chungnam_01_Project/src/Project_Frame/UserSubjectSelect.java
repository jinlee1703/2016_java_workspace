package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class UserSubjectSelect extends JFrame {
	JLabel[] tl = new JLabel[4];
	String[] tln = {"초급","중급","고급","전문가"};
	JLabel[] label = new JLabel[4];
	String[] ln = {"Word","Java","Excel","PowerPoint"};
	JButton btn = new JButton("입력");
	JPanel pp[] = new JPanel[4];
	JRadioButton[] rb = new JRadioButton[16];
	ButtonGroup[] g = new ButtonGroup[4];
	
	public UserSubjectSelect(int a, int b, int c, int d) {
		setTitle("수광과목 선택");
		setSize(220, 240);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 10));
		for(int i=0; i<tln.length; i++) {
			tl[i] = new JLabel(tln[i]);
			p1.add(tl[i]);
		}
		basePanel.add(p1, BorderLayout.NORTH);
		
		JPanel p2 = new JPanel(new GridLayout(4, 1, 15, 10));
		p2.setBorder(new EmptyBorder(-8, -20, 0, 0));
		for(int i=0; i<ln.length; i++) {
			pp[i] = new JPanel(new FlowLayout());
			label[i] = new JLabel(ln[i]);
			label[i].setHorizontalAlignment(label[i].LEFT);
			label[i].setPreferredSize(new Dimension(80, 20));
			pp[i].add(label[i]);
			g[i] = new ButtonGroup();
			for(int j=0; j<4; j++) {
				rb[(i*4)+j] = new JRadioButton();
				g[i].add(rb[(i*4)+j]);
				pp[i].add(rb[(i*4)+j]);
			}
			p2.add(pp[i]);
		}
		basePanel.add(p2, BorderLayout.CENTER);
		
		JPanel p3 = new JPanel(new BorderLayout());
		p3.setBorder(new EmptyBorder(15, 70, 15, 40));
		p3.add(btn, BorderLayout.CENTER);
		basePanel.add(p3, BorderLayout.SOUTH);
		
		add(basePanel);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
		
		Init(a, b, c, d);
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				int o = 0;
				if(a==1) {
					if(rb[0].isSelected()==false && rb[1].isSelected()==false && rb[2].isSelected()==false && rb[3].isSelected()==false) {
						o=1;
					}
				}
				if(b==1) {
					if(rb[4].isSelected()==false && rb[5].isSelected()==false && rb[6].isSelected()==false && rb[7].isSelected()==false) {
						o=1;
					}
				}
				if(c==1) {
					if(rb[8].isSelected()==false && rb[9].isSelected()==false && rb[10].isSelected()==false && rb[11].isSelected()==false) {
						o=1;
					}
				}
				if(d==1) {
					if(rb[12].isSelected()==false && rb[13].isSelected()==false && rb[14].isSelected()==false && rb[15].isSelected()==false) {
						o=1;
					}
				}
				
				if(o==1) {
					JOptionPane.showMessageDialog(null, "모든 강좌 수준을 선택해주세요.");
				} else {
					try {
						String s1 = null, s2 = null, s3 = null, s4 = null;
						if(a==1) {
							if(rb[0].isSelected()==true) {
								s1 = "초급";
							} else if(rb[1].isSelected()==true) {
								s1 = "중급";
							} else if(rb[2].isSelected()==true) {
								s1 = "고급";
							} else if(rb[3].isSelected()==true) {
								s1 = "전문가";
							}
						}
						if(b==1) {
							if(rb[4].isSelected()==true) {
								s2 = "초급";
							} else if(rb[5].isSelected()==true) {
								s2 = "중급";
							} else if(rb[6].isSelected()==true) {
								s2 = "고급";
							} else if(rb[7].isSelected()==true) {
								s2 = "전문가";
							}
						}
						if(c==1) {
							if(rb[8].isSelected()==true) {
								s3 = "초급";
							} else if(rb[9].isSelected()==true) {
								s3 = "중급";
							} else if(rb[10].isSelected()==true) {
								s3 = "고급";
							} else if(rb[11].isSelected()==true) {
								s3 = "전문가";
							}
						}
						if(d==1) {
							if(rb[12].isSelected()==true) {
								s4 = "초급";
							} else if(rb[13].isSelected()==true) {
								s4 = "중급";
							} else if(rb[14].isSelected()==true) {
								s4 = "고급";
							} else if(rb[15].isSelected()==true) {
								s4 = "전문가";
							}
						}
						
						ResultSet rs = DBInterface.Stmt.executeQuery("select * from student where studentID='"+Login.text1.getText()+"'");
						rs.next(); String sID = rs.getString(1);
						
						
						DBInterface.Stmt.execute("INSERT INTO `project000`.`slimit` (`word`, `java`, `excel`, `ppt`, `studentid`) VALUES ('"+s1+"', '"+s2+"', '"+s3+"', '"+s4+"', '"+sID+"');");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	}
	
	public void Init(int a, int b, int c, int d) {
		if(a==1) {
			rb[0].setEnabled(true);	rb[1].setEnabled(true);	rb[2].setEnabled(true);	rb[3].setEnabled(true);
		} else {
			rb[0].setEnabled(false); rb[1].setEnabled(false); rb[2].setEnabled(false); rb[3].setEnabled(false);
		}
		
		if(b==1) {
			rb[4].setEnabled(true);	rb[5].setEnabled(true);	rb[6].setEnabled(true);	rb[7].setEnabled(true);
		} else {
			rb[4].setEnabled(false); rb[5].setEnabled(false); rb[6].setEnabled(false); rb[7].setEnabled(false);
		}
		
		if(c==1) {
			rb[8].setEnabled(true);	rb[9].setEnabled(true);	rb[10].setEnabled(true);	rb[11].setEnabled(true);
		} else {
			rb[8].setEnabled(false); rb[9].setEnabled(false); rb[10].setEnabled(false); rb[11].setEnabled(false);
		}
		
		if(d==1) {
			rb[12].setEnabled(true); rb[13].setEnabled(true); rb[14].setEnabled(true); rb[15].setEnabled(true);
		} else {
			rb[12].setEnabled(false); rb[13].setEnabled(false); rb[14].setEnabled(false); rb[15].setEnabled(false);
		}
	}
}
