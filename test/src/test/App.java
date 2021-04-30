package test;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class App extends JFrame {
	WordGrader wg;
	
	public App() {
		wg = new WordGrader();
		
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
		input_textarea.setLineWrap(true);
		JScrollPane input_textarea_sc = new JScrollPane(input_textarea);
		input_textarea_sc.setBorder(border);
		input_textarea_sc.setBounds(10, 50, 680, 380);
		container.add(input_textarea_sc);
		
		
		JLabel result_label = new JLabel(" 분석 결과");
		result_label.setBorder(border);
		result_label.setBounds(10, 440, 680, 30);
		container.add(result_label);
		
		JTextArea result_textarea = new JTextArea();
		result_textarea.setLineWrap(true);
		result_textarea.setEditable(false);
		JScrollPane result_textarea_sc = new JScrollPane(result_textarea);
		result_textarea_sc.setBorder(border);
		result_textarea_sc.setBounds(10, 480, 680, 270);
		container.add(result_textarea_sc);
		
		JLabel log_label = new JLabel(" 로그");
		log_label.setBorder(border);
		log_label.setBounds(700, 10, 280, 30);
		container.add(log_label);
		
		JTextArea log_textarea = new JTextArea();
		log_textarea.setLineWrap(true);
		log_textarea.setEditable(false);
		JScrollPane log_textarea_sc = new JScrollPane(log_textarea);
		log_textarea_sc.setBorder(border);
		log_textarea_sc.setBounds(700, 50, 280, 380);
		container.add(log_textarea_sc);
		
		JLabel control_label = new JLabel(" 명령");
		control_label.setBorder(border);
		control_label.setBounds(700, 440, 280, 30);
		container.add(control_label);
		
		JButton toggle_button = new JButton("▶");
		toggle_button.setBorder(border);
		toggle_button.setBounds(700, 480, 50, 50);
		toggle_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				if (toggle_button.getText().equals("▶")) {
//					toggle_button.setText("■");
//				}
//				else {
//					toggle_button.setText("▶");
//				}
				
				//null이 아닌 경우
				try {
					String analyzedResult = wg.analyze(input_textarea.getText());
					result_textarea.setText(analyzedResult);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		container.add(toggle_button);

		//this.pack();
		setSize(1000,800); 
		setVisible(true);
		
	}
}
