package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Project_DBInterface.DBInterface;

public class employeeInsert extends JFrame {
	JLabel[] label = new JLabel[4];
	String[] ln = {"ID","이름","Email","연락처"};
	JLabel idLabel = new JLabel("");
	JTextField[] text = new JTextField[3];
	JButton btn = new JButton("확인");
	
	public employeeInsert() {
		setTitle("추가");
		setSize(200, 220);
		setResizable(false);
		setLocationRelativeTo(null);
		
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select count(*) from employee"); rs.next();
			String id = Integer.toString(rs.getInt(1)+1);
			idLabel.setText(id);
			idLabel.setPreferredSize(new Dimension(100, 20));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i=0; i<ln.length; i++) {
			label[i] = new JLabel(ln[i]);
			label[i].setPreferredSize(new Dimension(50, 20));
		}
		
		for(int i=0; i<3; i++) {
			text[i] = new JTextField(8);
		}
		
		JPanel basePanel = new JPanel(new BorderLayout());
		basePanel.setBorder(new EmptyBorder(20, 10, 20, 0));
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p1.add(label[0]); p1.add(idLabel);
		p1.add(label[1]); p1.add(text[0]);
		p1.add(label[2]); p1.add(text[1]);
		p1.add(label[3]); p1.add(text[2]);
		
		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(btn);
		
		basePanel.add(p1, BorderLayout.CENTER);
		basePanel.add(p2, BorderLayout.SOUTH);
		add(basePanel);
		
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(text[0].getText().equals("") || text[1].getText().equals("") || text[2].getText().equals("")) {
				} else {
					try {
						DBInterface.Stmt.execute("INSERT INTO `testproject000`.`employee` (`id`, `pw`, `name`, `email`, `phone`) VALUES ('"+idLabel.getText()+"', '0000', '"+text[0].getText()+"', '"+text[1].getText()+"', '"+text[2].getText()+"');");
						
						employeeSelect.Init();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
