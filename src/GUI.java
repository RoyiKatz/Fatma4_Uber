import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JSeparator;
import java.awt.Color;

public class GUI {

	private JFrame frame;
	private JTextField num_of_drivers_textbox;
	private JTextField car_officer_work_time_textbox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 609, 408);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel title_label = new JLabel("Fatma Super Uber, at your service");
		title_label.setBounds(0, 42, 595, 28);
		title_label.setFont(new Font("Arial", Font.BOLD, 24));
		title_label.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(title_label);
		
		
		num_of_drivers_textbox = new JTextField();
		num_of_drivers_textbox.setFont(new Font("Arial", Font.PLAIN, 12));
		num_of_drivers_textbox.setBounds(330, 98, 201, 28);
		frame.getContentPane().add(num_of_drivers_textbox);
		num_of_drivers_textbox.setColumns(10);
		
		car_officer_work_time_textbox = new JTextField();
		car_officer_work_time_textbox.setFont(new Font("Arial", Font.PLAIN, 12));
		car_officer_work_time_textbox.setBounds(330, 162, 201, 28);
		frame.getContentPane().add(car_officer_work_time_textbox);
		car_officer_work_time_textbox.setColumns(10);
		
		JLabel enter_driver_number_label = new JLabel("Enter number of drivers:");
		enter_driver_number_label.setFont(new Font("Arial", Font.BOLD, 12));
		enter_driver_number_label.setBounds(69, 102, 229, 20);
		frame.getContentPane().add(enter_driver_number_label);
		
		JLabel car_officer_work_time_label = new JLabel("Car officer working time (in seconds):");
		car_officer_work_time_label.setFont(new Font("Arial", Font.BOLD, 12));
		car_officer_work_time_label.setBounds(69, 169, 229, 20);
		frame.getContentPane().add(car_officer_work_time_label);
		
		JButton start_button = new JButton("Start");
		start_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int num_of_drivers = 4;
				double car_officer_work_time = 0;
				
				// input validation - number of drivers
				if (!num_of_drivers_textbox.getText().isBlank()) {
					if (Integer.parseInt(num_of_drivers_textbox.getText()) > 0) {
						num_of_drivers = Integer.parseInt(num_of_drivers_textbox.getText());
					}
				}
				
				// input validation - car officer work time
				if (!car_officer_work_time_textbox.getText().isBlank()) {
					if (Double.parseDouble(car_officer_work_time_textbox.getText()) >= 0) {
						car_officer_work_time = Double.parseDouble(car_officer_work_time_textbox.getText());
					}
				}
				
				Company c = new Company(num_of_drivers, car_officer_work_time);
				c.startWorkDay();
			}
		});
		
		start_button.setFont(new Font("Arial", Font.BOLD, 24));
		start_button.setBounds(69, 246, 229, 41);
		frame.getContentPane().add(start_button);
		
		JTextArea txtrIfNoInput = new JTextArea();
		txtrIfNoInput.setBackground(UIManager.getColor("Button.background"));
		txtrIfNoInput.setFont(new Font("Arial", Font.PLAIN, 12));
		txtrIfNoInput.setText("If no input will be inserted (or invalid input) the defaul settings are:\r\n4 drivers and 0.0 car officer working time");
		txtrIfNoInput.setBounds(118, 314, 364, 41);
		frame.getContentPane().add(txtrIfNoInput);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(118, 302, 364, 2);
		frame.getContentPane().add(separator);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBackground(new Color(255, 153, 153));
		btnExit.setFont(new Font("Arial", Font.BOLD, 24));
		btnExit.setBounds(310, 246, 221, 41);
		frame.getContentPane().add(btnExit);


	}
}
