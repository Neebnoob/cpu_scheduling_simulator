package cpu_scheduling;

import java.awt.Dimension;

import javax.swing.*;

//class containing the projects UI

public class ProjectGUI extends JFrame{
	
	//variables
	
	//constructor
	public ProjectGUI() {
		
		setTitle("CPU Scheduling Simulator");
		setSize(400, 400);
		
		//creation of top JPanel
		JPanel topPanel = new JPanel();
		
		//select file button
		JButton loadFile = new JButton("Load...");
		loadFile.setPreferredSize(new Dimension(65,20));
		
		JLabel selectAlgo = new JLabel("Select algorithm: ");
		
		//select algorithm drop down
		String[] choices = {"First Come First Server", "Smallest Job First", "Priority Scheduling"};
		JComboBox<String> algoChoices = new JComboBox<String>(choices);
		
		JLabel quantum = new JLabel("Quantum: ");
		JTextField quantumInput = new JTextField(2);
		
		//start stop button
		JButton startPauseButton = new JButton("►");
		startPauseButton.setPreferredSize(new Dimension(20, 20));
		JLabel startPause = new JLabel("Start/Pause");
		
		JLabel speed = new JLabel("Speed: ");
		
		//speed drop down box
		String[] choicesTwo = {"1fps", "2fps", "3fps"};
		JComboBox<String> speedChoices = new JComboBox<String>(choicesTwo);
		
		JButton nextButton = new JButton("Next ->");
		nextButton.setPreferredSize(new Dimension(65,20));
		
		//adding elements to panel
		topPanel.add(loadFile);
		topPanel.add(selectAlgo);
		topPanel.add(algoChoices); 
		topPanel.add(quantum);
		topPanel.add(quantumInput);
		topPanel.add(startPauseButton);
		topPanel.add(startPause);
		topPanel.add(speed);
		topPanel.add(speedChoices);
		topPanel.add(nextButton);
		this.getContentPane().add(topPanel);
		
		
		//last things to be executed
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}
	
}
