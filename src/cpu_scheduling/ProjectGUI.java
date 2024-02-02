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
		
		//creation of UI elements
		JPanel topPanel = new JPanel();
		JButton loadFile = new JButton(); //"Load..."
		JLabel selectAlgo = new JLabel(); //"Select algorithm: "
		JComboBox<String> algoChoices = new JComboBox<String>(); // String[] choices = {"First Come First Server", "Smallest Job First", "Priority Scheduling"};
		JLabel quantum = new JLabel(); //"Quantum: "
		JTextField quantumInput = new JTextField();
		JButton startPauseButton = new JButton(); //"►"
		JLabel startPause = new JLabel(); //"Start/Pause"
		JLabel speed = new JLabel(); //"Speed: "
		JComboBox<String> speedChoices = new JComboBox<String>(); //String[] choicesTwo = {"1fps", "2fps", "3fps"};
		JButton nextButton = new JButton(); //"Next ->"
		JLabel sysTime = new JLabel();
		JLabel throughput = new JLabel();
		JLabel avgTurn = new JLabel();
		JLabel avgWait = new JLabel();
		
		
		/*
		Create a 2 tall and n wide grid that will house a processor 
		in diagonal corners and houses a queue of processes
		Or JLabels in a grid layout
		
		Create methods to move right, left, up down.
		??? Random color for each process ???
		*/
		
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
