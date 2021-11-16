package Project_Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.metal.MetalBorders.TableHeaderBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Project_DBInterface.DBInterface;

public class MovieSelect extends JFrame {
	String[] header = {"번호","제목","감독","주연","장르","국가","개봉일","상영 종료일","상영시간","연령","가격","순위","순위변화"};
	DefaultTableModel model = new DefaultTableModel(header, 0) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table = new JTable(model);
	JScrollPane scroll = new JScrollPane(table);
	JTextField text = new JTextField();
	String[] cn = {"제목","국가"};
	JComboBox combo = new JComboBox(cn);	
	JButton btn1 = new JButton("영화선택");
	JButton btn2 = new JButton("닫기");
	int[][] rankArray = null;
	
	public MovieSelect() {
		setTitle("영화선택");
		setSize(950, 750);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel basePanel = new JPanel(new BorderLayout());
		
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		text.setPreferredSize(new Dimension(1000, 30));
		combo.setPreferredSize(new Dimension(80, 30));
		combo.setBorder(new EmptyBorder(0, 0, 0, -19));
		p1.add(text); p1.add(combo);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBorder(new EmptyBorder(5, 5, 5, 5));
		JScrollPane panelScroll = new JScrollPane(p2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JPanel p21 = new JPanel(new BorderLayout());
		p21.setBorder(new LineBorder(Color.black));
		JPanel p22 = new JPanel(new BorderLayout());
		p22.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		p2.add(p21);
		p21.add(p22, BorderLayout.CENTER);
		p22.add(scroll, BorderLayout.CENTER);
		
		basePanel.add(p1, BorderLayout.NORTH);
		basePanel.add(panelScroll, BorderLayout.CENTER);
		
		add(basePanel);
		
		TableInsert();
		TableSetting();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void TableInsert() {
		try {
			ResultSet rs = DBInterface.Stmt.executeQuery("select * from movie");
			
			String[] newRow = new String[13];
			
			while(rs.next()) {
				for(int i=0; i<11; i++) {
					newRow[i] = rs.getString(i+1);
				}
				//newRow[11] = rs.getString(1);
				//newRow[12] = rs.getString(1);
				model.addRow(newRow);
			}
			
			rs = DBInterface.Stmt.executeQuery("select count(*) from movie");
			rs.next(); rankArray = new int[rs.getInt(1)][3];
			
			for(int i=0; i<rs.getInt(1); i++) {
				rankArray[i][0] = i+1;
			}

			rs = DBInterface.Stmt.executeQuery("select `Movie_num` from `reservation`,`screen` where `reservation`.`screen_num`=`screen`.`id` and `reservation`.`reservation time`<=current_date() and `reservation`.`reservation time`>=current_date()-6;");
			
			while(rs.next()) {
				int row = rs.getInt(1)-1;
				rankArray[row][1] = rankArray[row][1] + 1 ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void TableSetting() {
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		DefaultTableCellRenderer right = new DefaultTableCellRenderer();
		right.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);
		
		table.getColumnModel().getColumn(0).setCellRenderer(center);
		table.getColumnModel().getColumn(5).setCellRenderer(center);
		table.getColumnModel().getColumn(6).setCellRenderer(center);
		table.getColumnModel().getColumn(7).setCellRenderer(center);
		table.getColumnModel().getColumn(8).setCellRenderer(center);
		table.getColumnModel().getColumn(9).setCellRenderer(right);
		table.getColumnModel().getColumn(10).setCellRenderer(right);
		table.getColumnModel().getColumn(11).setCellRenderer(right);
		table.getColumnModel().getColumn(12).setCellRenderer(center);
		
		table.getColumnModel().getColumn(0).setMaxWidth(40);
		table.getColumnModel().getColumn(1).setMaxWidth(100);
		table.getColumnModel().getColumn(2).setMaxWidth(40);
		table.getColumnModel().getColumn(3).setMaxWidth(125);
		table.getColumnModel().getColumn(4).setMaxWidth(100);
		table.getColumnModel().getColumn(5).setMaxWidth(50);
		table.getColumnModel().getColumn(6).setMaxWidth(90);
		table.getColumnModel().getColumn(7).setMaxWidth(90);
		table.getColumnModel().getColumn(8).setMaxWidth(60);
		table.getColumnModel().getColumn(9).setMaxWidth(50);
		table.getColumnModel().getColumn(10).setMaxWidth(50);
		table.getColumnModel().getColumn(11).setMaxWidth(40);
		table.getColumnModel().getColumn(12).setMaxWidth(50);
	}
}
