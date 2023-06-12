import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class WorkDay extends JFrame {

	private JPanel contentPane;
	private JTextArea output;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public WorkDay(int num_of_drivers, double car_officer_work_time) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 663, 373);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// title
		JLabel lblTitle = new JLabel("Working day in progress...");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 12));
		lblTitle.setBounds(241, 34, 169, 21);
		contentPane.add(lblTitle);
		
		// output area
		output = new JTextArea();
		output.setWrapStyleWord(true);
		output.setEditable(false); 		// read only
		output.setBackground(UIManager.getColor("CheckBox.background"));
		output.setFont(new Font("Arial", Font.PLAIN, 12));
		output.setBounds(83, 84, 505, 178);
		contentPane.add(output);
		
	    // Create a scroll pane and add the console text area to it
	    JScrollPane scrollPane = new JScrollPane(output);
	    scrollPane.setBounds(83, 84, 505, 159);

	    // Set the scroll bar policy for the scroll pane
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

	    // Add the scroll pane to the frame
	    this.getContentPane().add(scrollPane);
	    
	    // start again button
	    JButton btnStartAgain = new JButton("Start Again");
	    btnStartAgain.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		GUI gui = new GUI();
	    		gui.getFrame().setVisible(true);
	    		dispose();
	    	}
	    });
	    btnStartAgain.setFont(new Font("Arial", Font.BOLD, 24));
	    btnStartAgain.setBounds(257, 265, 169, 46);
	    contentPane.add(btnStartAgain);

		
		// Redirect the console output to the text area
	    redirectConsoleOutput();
	    
	    
	    
		
		Company c = new Company(num_of_drivers, car_officer_work_time);
		c.startWorkDay();

	}
	
	
	private void redirectConsoleOutput() {
	    // Create a new output stream that writes to the text area
	    OutputStream outputStream = new OutputStream() {
	        @Override
	        public void write(int b) throws IOException {
	            // Append the character to the text area
	            output.append(String.valueOf((char) b));
	            // Move the caret to the end of the text area to show the latest output
	            output.setCaretPosition(output.getDocument().getLength());
	        }
	    };

	    // Redirect System.out to the new output stream
	    System.setOut(new PrintStream(outputStream));
	}
}
