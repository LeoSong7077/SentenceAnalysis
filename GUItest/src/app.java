import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class app extends JFrame {
	GridBagLayout gBag;
	
	public app() {
		setTitle("sentences grading");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container container = getContentPane();
		container.setLayout(null);
		
		LineBorder border = new LineBorder(Color.BLACK,1);
		
		JLabel input_label = new JLabel(" 데이터 입력");
		input_label.setBorder(border);
		input_label.setBounds(10, 10, 680, 30);
		container.add(input_label);
		
		JTextArea input_textarea = new JTextArea();
		input_textarea.setBorder(border);
		input_textarea.setBounds(10, 50, 680, 380);
		input_textarea.setLineWrap(true);
		container.add(input_textarea);
		
		JLabel result_label = new JLabel(" 분석 결과");
		result_label.setBorder(border);
		result_label.setBounds(10, 440, 680, 30);
		container.add(result_label);
		
		JTextArea result_textarea = new JTextArea();
		result_textarea.setBorder(border);
		result_textarea.setBounds(10, 480, 680, 270);
		result_textarea.setLineWrap(true);
		result_textarea.setEditable(false);
		container.add(result_textarea);
		
		JLabel log_label = new JLabel(" 로그");
		log_label.setBorder(border);
		log_label.setBounds(700, 10, 280, 30);
		container.add(log_label);
		
		JTextArea log_textarea = new JTextArea();
		log_textarea.setBorder(border);
		log_textarea.setBounds(700, 50, 280, 380);
		log_textarea.setLineWrap(true);
		log_textarea.setEditable(false);
		container.add(log_textarea);
		
		JLabel control_label = new JLabel(" 명령");
		control_label.setBorder(border);
		control_label.setBounds(700, 440, 280, 30);
		container.add(control_label);

		//this.pack();
		setSize(1000,800); 
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new app();
	}
	
}
