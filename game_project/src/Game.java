/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	private boolean runningAlready;
	public void run() {

		runningAlready = false;
		final JFrame frame = new JFrame("Bad Beat");
		frame.setLocation(300, 300);

		// Status panel
		final JPanel status_panel = new JPanel();
		frame.add(status_panel, BorderLayout.SOUTH);
		final JLabel status = new JLabel("Image Rights to Guitar Hero.");
		status_panel.add(status);

		// Main playing area
		final GameCourt court = new GameCourt(status);
		frame.add(court, BorderLayout.CENTER);

		// Reset button
		final JPanel control_panel = new JPanel();
		frame.add(control_panel, BorderLayout.NORTH);

		// Note here that when we add an action listener to the reset
		// button, we define it as an anonymous inner class that is
		// an instance of ActionListener with its actionPerformed()
		// method overridden. When the button is pressed,
		// actionPerformed() will be called.
		final JButton start = new JButton("Start");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(runningAlready == false){
				court.defreeze();
				runningAlready = true;
				}else{
					court.setFocusable(true);
					court.grabFocus();
				}
			}
		});
		control_panel.add(start);
		
		final JButton instruct = new JButton("Instructions");
		instruct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame();
				frame.setLocation(400, 400);
				JTextArea  textArea = new JTextArea("Hi, "+ court.getPlayerName() + 
						"!\nThis is a cool music tempo game just like guitar hero.\nYou press the A S D F J K L ; keys "
						+ "to control the 8 lanes from left to right, and you want to hit the right bars at the right "
						+ "time. \nThe goal of this game is to finish with the highest score!"
						+ "\nThe thicker the bars, the more points they worth!"
						+ "\nYou earn points by hitting the right key, as denoted at the bottom, when the falling color"
						+ "bars hits the target line at the lower part of the screen. "
						+ "\nYou also may encounter bombs, which are flashing rectangles. \nIf you try to hit them"
						+ "when they reach the target line, they explode! So defuse them when they are far away or just"
						+ "let them pass. Make sure to hit as many as possible, the more you miss, the more points you "
						+ "lose! Levels are randomly generated! \nClick high score"
						+ "button to check your place at the high score board after the game!\nEnjoy!"
					    );
				textArea.setFont(new Font("Serif", Font.ITALIC, 20));
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);
				textArea.setSize(600, 300);
				textArea.setEditable(false);
				frame.add(textArea);
				frame.pack();
				frame.setVisible(true);
			}
		});
		control_panel.add(instruct);
		
		final JButton scores = new JButton("Highscores");
		scores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame();
				frame.setLocation(400, 400);
				JTextArea  textArea = new JTextArea();
				textArea.setFont(new Font("Sans_Serif", Font.BOLD, 20));
				textArea.setText(LevelGenerator.readHighScore());
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);
				textArea.setSize(1000, 500);
				textArea.setEditable(false);
				frame.add(textArea);
				frame.pack();
				frame.setVisible(true);
			}
		});
		control_panel.add(scores);

		// Put the frame on the screen 
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}

	/*
	 * Main method run to start and run the game Initializes the GUI elements
	 * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
	 * this in the final submission of your game.
	 */
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}
