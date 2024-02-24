package cpu_scheduling;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

//class containing the projects UI

//Todo
//load table with processes information when load file button is pressed

public class ProjectGUI extends JFrame{
	
	//variables
	private static final long serialVersionUID = 1L;
	private JTextField quantumTextField;
	private DefaultTableModel processesDisplay;
	private JTable table;
	private ArrayList<Processes> processesList;
	
	//constructor
	public ProjectGUI() {
		
		setTitle("CPU Scheduling Simulator");
		setSize(1200, 800);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{18, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JButton loadButton = new JButton("Load...");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
			    jfc.showDialog(null,"Please Select the File");
			    jfc.setVisible(true);
			    File fileName = jfc.getSelectedFile();
			    loadSimulationFile(fileName.getAbsolutePath());
			    
			    processesDisplay.setRowCount(0);
				
				for (int i = 0; i < processesList.size(); i++) {
					Processes temp = processesList.get(i);
					processesDisplay.addRow(new Object[] {temp.getName(), temp.getArrivalTime(), temp.getPriorityLevel(), temp.getCPUBursts(), temp.getIOBursts(), temp.getStartTime(), temp.getFinishTime(), temp.getWaitTime(), temp.getWaitIOTime(), temp.getStatus()});
				}
			}
		});
		GridBagConstraints gbc_loadButton = new GridBagConstraints();
		gbc_loadButton.insets = new Insets(0, 0, 5, 5);
		gbc_loadButton.gridx = 1;
		gbc_loadButton.gridy = 1;
		panel.add(loadButton, gbc_loadButton);
		
		JLabel selectAlgoLabel = new JLabel("Select Algorithim:");
		GridBagConstraints gbc_selectAlgoLabel = new GridBagConstraints();
		gbc_selectAlgoLabel.gridwidth = 3;
		gbc_selectAlgoLabel.anchor = GridBagConstraints.EAST;
		gbc_selectAlgoLabel.insets = new Insets(0, 0, 5, 5);
		gbc_selectAlgoLabel.gridx = 3;
		gbc_selectAlgoLabel.gridy = 1;
		panel.add(selectAlgoLabel, gbc_selectAlgoLabel);
		
		JComboBox<String> selectAlgoDropDown = new JComboBox<String>();
		GridBagConstraints gbc_selectAlgoDropDown = new GridBagConstraints();
		gbc_selectAlgoDropDown.gridwidth = 7;
		gbc_selectAlgoDropDown.insets = new Insets(0, 0, 5, 5);
		gbc_selectAlgoDropDown.gridx = 6;
		gbc_selectAlgoDropDown.gridy = 1;
		selectAlgoDropDown.setModel(new DefaultComboBoxModel<String>(new String[] {"First Come First Server", "Shortest Job First", "Priotiry Scheduling"}));
		panel.add(selectAlgoDropDown, gbc_selectAlgoDropDown);
		
		JLabel quantumLabel = new JLabel("Quantum:");
		GridBagConstraints gbc_quantumLabel = new GridBagConstraints();
		gbc_quantumLabel.gridwidth = 3;
		gbc_quantumLabel.anchor = GridBagConstraints.EAST;
		gbc_quantumLabel.insets = new Insets(0, 0, 5, 5);
		gbc_quantumLabel.gridx = 17;
		gbc_quantumLabel.gridy = 1;
		panel.add(quantumLabel, gbc_quantumLabel);
		
		quantumTextField = new JTextField();
		quantumTextField.setText("0");
		GridBagConstraints gbc_quantumTextField = new GridBagConstraints();
		gbc_quantumTextField.anchor = GridBagConstraints.WEST;
		gbc_quantumTextField.insets = new Insets(0, 0, 5, 5);
		gbc_quantumTextField.gridx = 20;
		gbc_quantumTextField.gridy = 1;
		panel.add(quantumTextField, gbc_quantumTextField);
		quantumTextField.setColumns(3);
		
		JLabel startPauseLabel = new JLabel("Start/Pause");
		GridBagConstraints gbc_startPauseLabel = new GridBagConstraints();
		gbc_startPauseLabel.gridwidth = 2;
		gbc_startPauseLabel.anchor = GridBagConstraints.EAST;
		gbc_startPauseLabel.insets = new Insets(0, 0, 5, 5);
		gbc_startPauseLabel.gridx = 26;
		gbc_startPauseLabel.gridy = 1;
		panel.add(startPauseLabel, gbc_startPauseLabel);
		
		JButton startPauseButton = new JButton("►");
		GridBagConstraints gbc_startPauseButton = new GridBagConstraints();
		gbc_startPauseButton.gridwidth = 2;
		gbc_startPauseButton.anchor = GridBagConstraints.WEST;
		gbc_startPauseButton.insets = new Insets(0, 0, 5, 5);
		gbc_startPauseButton.gridx = 28;
		gbc_startPauseButton.gridy = 1;
		panel.add(startPauseButton, gbc_startPauseButton);
		
		JLabel speedLabel = new JLabel("Speed:");
		GridBagConstraints gbc_speedLabel = new GridBagConstraints();
		gbc_speedLabel.anchor = GridBagConstraints.EAST;
		gbc_speedLabel.insets = new Insets(0, 0, 5, 5);
		gbc_speedLabel.gridx = 30;
		gbc_speedLabel.gridy = 1;
		panel.add(speedLabel, gbc_speedLabel);
		
		JComboBox<String> speedDropDown = new JComboBox<String>();
		GridBagConstraints gbc_speedDropDown = new GridBagConstraints();
		gbc_speedDropDown.anchor = GridBagConstraints.WEST;
		gbc_speedDropDown.insets = new Insets(0, 0, 5, 5);
		gbc_speedDropDown.gridx = 31;
		gbc_speedDropDown.gridy = 1;
		speedDropDown.setModel(new DefaultComboBoxModel<String>(new String[] {"1 fps", "2 fps", "3 fps"}));
		panel.add(speedDropDown, gbc_speedDropDown);
		
		JButton nextButton = new JButton("Next ->");
		GridBagConstraints gbc_nextButton = new GridBagConstraints();
		gbc_nextButton.insets = new Insets(0, 0, 5, 5);
		gbc_nextButton.gridx = 33;
		gbc_nextButton.gridy = 1;
		panel.add(nextButton, gbc_nextButton);
		
		JLabel systemTimeLabel = new JLabel("System Time:");
		GridBagConstraints gbc_systemTimeLabel = new GridBagConstraints();
		gbc_systemTimeLabel.anchor = GridBagConstraints.EAST;
		gbc_systemTimeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_systemTimeLabel.gridx = 1;
		gbc_systemTimeLabel.gridy = 3;
		panel.add(systemTimeLabel, gbc_systemTimeLabel);
		
		JLabel systemTimeNumberLabel = new JLabel("0");
		GridBagConstraints gbc_systemTimeNumberLabel = new GridBagConstraints();
		gbc_systemTimeNumberLabel.anchor = GridBagConstraints.WEST;
		gbc_systemTimeNumberLabel.insets = new Insets(0, 0, 5, 5);
		gbc_systemTimeNumberLabel.gridx = 2;
		gbc_systemTimeNumberLabel.gridy = 3;
		panel.add(systemTimeNumberLabel, gbc_systemTimeNumberLabel);
		
		JLabel throughputLabel = new JLabel("Throughput:");
		GridBagConstraints gbc_throughputLabel = new GridBagConstraints();
		gbc_throughputLabel.anchor = GridBagConstraints.EAST;
		gbc_throughputLabel.insets = new Insets(0, 0, 5, 5);
		gbc_throughputLabel.gridx = 5;
		gbc_throughputLabel.gridy = 3;
		panel.add(throughputLabel, gbc_throughputLabel);
		
		JLabel throughtputNumberLabel = new JLabel("0");
		GridBagConstraints gbc_throughtputNumberLabel = new GridBagConstraints();
		gbc_throughtputNumberLabel.anchor = GridBagConstraints.WEST;
		gbc_throughtputNumberLabel.insets = new Insets(0, 0, 5, 5);
		gbc_throughtputNumberLabel.gridx = 6;
		gbc_throughtputNumberLabel.gridy = 3;
		panel.add(throughtputNumberLabel, gbc_throughtputNumberLabel);
		
		JLabel averageTurnLabel = new JLabel("AVG Turn:");
		GridBagConstraints gbc_averageTurnLabel = new GridBagConstraints();
		gbc_averageTurnLabel.anchor = GridBagConstraints.EAST;
		gbc_averageTurnLabel.insets = new Insets(0, 0, 5, 5);
		gbc_averageTurnLabel.gridx = 10;
		gbc_averageTurnLabel.gridy = 3;
		panel.add(averageTurnLabel, gbc_averageTurnLabel);
		
		JLabel averageTurnNumberLabel = new JLabel("0.00");
		GridBagConstraints gbc_averageTurnNumberLabel = new GridBagConstraints();
		gbc_averageTurnNumberLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_averageTurnNumberLabel.insets = new Insets(0, 0, 5, 5);
		gbc_averageTurnNumberLabel.gridx = 11;
		gbc_averageTurnNumberLabel.gridy = 3;
		panel.add(averageTurnNumberLabel, gbc_averageTurnNumberLabel);
		
		JLabel averageWaitLabel = new JLabel("AVG Wait:");
		GridBagConstraints gbc_averageWaitLabel = new GridBagConstraints();
		gbc_averageWaitLabel.gridwidth = 3;
		gbc_averageWaitLabel.anchor = GridBagConstraints.EAST;
		gbc_averageWaitLabel.insets = new Insets(0, 0, 5, 5);
		gbc_averageWaitLabel.gridx = 17;
		gbc_averageWaitLabel.gridy = 3;
		panel.add(averageWaitLabel, gbc_averageWaitLabel);
		
		JLabel averageWaitNumberLabel = new JLabel("0.00");
		GridBagConstraints gbc_averageWaitNumberLabel = new GridBagConstraints();
		gbc_averageWaitNumberLabel.insets = new Insets(0, 0, 5, 5);
		gbc_averageWaitNumberLabel.gridx = 20;
		gbc_averageWaitNumberLabel.gridy = 3;
		panel.add(averageWaitNumberLabel, gbc_averageWaitNumberLabel);
		
		JScrollPane logScrollPane = new JScrollPane();
		GridBagConstraints gbc_logScrollPane = new GridBagConstraints();
		gbc_logScrollPane.gridwidth = 6;
		gbc_logScrollPane.gridheight = 11;
		gbc_logScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_logScrollPane.fill = GridBagConstraints.BOTH;
		gbc_logScrollPane.gridx = 28;
		gbc_logScrollPane.gridy = 3;
		panel.add(logScrollPane, gbc_logScrollPane);
		
		JTextPane logOutputText = new JTextPane();
		logScrollPane.setRowHeaderView(logOutputText);
		logOutputText.setText("");
		logOutputText.setEditable(false);
		
		JPanel cpuQueuePanel = new JPanel();
		cpuQueuePanel.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_cpuQueuePanel = new GridBagConstraints();
		gbc_cpuQueuePanel.gridwidth = 23;
		gbc_cpuQueuePanel.gridheight = 5;
		gbc_cpuQueuePanel.insets = new Insets(0, 0, 5, 5);
		gbc_cpuQueuePanel.fill = GridBagConstraints.BOTH;
		gbc_cpuQueuePanel.gridx = 1;
		gbc_cpuQueuePanel.gridy = 5;
		panel.add(cpuQueuePanel, gbc_cpuQueuePanel);
		GridBagLayout gbl_cpuQueuePanel = new GridBagLayout();
		gbl_cpuQueuePanel.columnWidths = new int[]{0, 0, 289, -165, 0};
		gbl_cpuQueuePanel.rowHeights = new int[]{1, 0, 0};
		gbl_cpuQueuePanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_cpuQueuePanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		cpuQueuePanel.setLayout(gbl_cpuQueuePanel);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		GridBagConstraints gbc_horizontalGlue = new GridBagConstraints();
		gbc_horizontalGlue.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalGlue.anchor = GridBagConstraints.NORTHWEST;
		gbc_horizontalGlue.gridx = 3;
		gbc_horizontalGlue.gridy = 0;
		cpuQueuePanel.add(horizontalGlue, gbc_horizontalGlue);
		
		JLabel readyQueueLabel = new JLabel("Ready Queue");
		GridBagConstraints gbc_readyQueueLabel = new GridBagConstraints();
		gbc_readyQueueLabel.insets = new Insets(0, 0, 0, 5);
		gbc_readyQueueLabel.gridx = 1;
		gbc_readyQueueLabel.gridy = 1;
		cpuQueuePanel.add(readyQueueLabel, gbc_readyQueueLabel);
		
		JPanel cpuPCBPanel = new JPanel();
		cpuPCBPanel.setBackground(Color.PINK);
		cpuPCBPanel.setForeground(Color.PINK);
		GridBagConstraints gbc_cpuPCBPanel = new GridBagConstraints();
		gbc_cpuPCBPanel.gridwidth = 4;
		gbc_cpuPCBPanel.gridheight = 5;
		gbc_cpuPCBPanel.insets = new Insets(0, 0, 5, 5);
		gbc_cpuPCBPanel.fill = GridBagConstraints.BOTH;
		gbc_cpuPCBPanel.gridx = 24;
		gbc_cpuPCBPanel.gridy = 5;
		panel.add(cpuPCBPanel, gbc_cpuPCBPanel);
		GridBagLayout gbl_cpuPCBPanel = new GridBagLayout();
		gbl_cpuPCBPanel.columnWidths = new int[]{46, 0, 37, 0};
		gbl_cpuPCBPanel.rowHeights = new int[]{22, 0, 0, 0, 0, 0};
		gbl_cpuPCBPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_cpuPCBPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		cpuPCBPanel.setLayout(gbl_cpuPCBPanel);
		
		JLabel cpuLabel = new JLabel("CPU 1");
		GridBagConstraints gbc_cpuLabel = new GridBagConstraints();
		gbc_cpuLabel.insets = new Insets(0, 0, 5, 5);
		gbc_cpuLabel.anchor = GridBagConstraints.SOUTHEAST;
		gbc_cpuLabel.gridx = 0;
		gbc_cpuLabel.gridy = 0;
		cpuPCBPanel.add(cpuLabel, gbc_cpuLabel);
		
		JLabel cpuStatusLabel = new JLabel("idle");
		GridBagConstraints gbc_cpuStatusLabel = new GridBagConstraints();
		gbc_cpuStatusLabel.insets = new Insets(0, 0, 5, 5);
		gbc_cpuStatusLabel.gridx = 1;
		gbc_cpuStatusLabel.gridy = 3;
		cpuPCBPanel.add(cpuStatusLabel, gbc_cpuStatusLabel);
		
		JPanel ioPCBPanel = new JPanel();
		ioPCBPanel.setBackground(Color.ORANGE);
		ioPCBPanel.setForeground(Color.ORANGE);
		GridBagConstraints gbc_ioPCBPanel = new GridBagConstraints();
		gbc_ioPCBPanel.gridwidth = 3;
		gbc_ioPCBPanel.gridheight = 5;
		gbc_ioPCBPanel.insets = new Insets(0, 0, 5, 5);
		gbc_ioPCBPanel.fill = GridBagConstraints.BOTH;
		gbc_ioPCBPanel.gridx = 1;
		gbc_ioPCBPanel.gridy = 10;
		panel.add(ioPCBPanel, gbc_ioPCBPanel);
		GridBagLayout gbl_ioPCBPanel = new GridBagLayout();
		gbl_ioPCBPanel.columnWidths = new int[]{37, 49, 0};
		gbl_ioPCBPanel.rowHeights = new int[]{21, 0, 0, 0};
		gbl_ioPCBPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_ioPCBPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		ioPCBPanel.setLayout(gbl_ioPCBPanel);
		
		JLabel ioLabel = new JLabel("IO 1");
		GridBagConstraints gbc_ioLabel = new GridBagConstraints();
		gbc_ioLabel.insets = new Insets(0, 0, 5, 5);
		gbc_ioLabel.anchor = GridBagConstraints.SOUTHEAST;
		gbc_ioLabel.gridx = 0;
		gbc_ioLabel.gridy = 0;
		ioPCBPanel.add(ioLabel, gbc_ioLabel);
		
		JLabel ioStatusLabel = new JLabel("idle");
		GridBagConstraints gbc_ioStatusLabel = new GridBagConstraints();
		gbc_ioStatusLabel.gridx = 1;
		gbc_ioStatusLabel.gridy = 2;
		ioPCBPanel.add(ioStatusLabel, gbc_ioStatusLabel);
		
		JPanel ioQueuePanel = new JPanel();
		ioQueuePanel.setBackground(Color.GRAY);
		GridBagConstraints gbc_ioQueuePanel = new GridBagConstraints();
		gbc_ioQueuePanel.gridwidth = 24;
		gbc_ioQueuePanel.gridheight = 5;
		gbc_ioQueuePanel.insets = new Insets(0, 0, 5, 5);
		gbc_ioQueuePanel.fill = GridBagConstraints.BOTH;
		gbc_ioQueuePanel.gridx = 4;
		gbc_ioQueuePanel.gridy = 10;
		panel.add(ioQueuePanel, gbc_ioQueuePanel);
		GridBagLayout gbl_ioQueuePanel = new GridBagLayout();
		gbl_ioQueuePanel.columnWidths = new int[]{18, 0, 0};
		gbl_ioQueuePanel.rowHeights = new int[]{27, 0, 0, 0};
		gbl_ioQueuePanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_ioQueuePanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		ioQueuePanel.setLayout(gbl_ioQueuePanel);
		
		JLabel waitingQueueLabel = new JLabel("Waiting Queue");
		GridBagConstraints gbc_waitingQueueLabel = new GridBagConstraints();
		gbc_waitingQueueLabel.insets = new Insets(0, 0, 5, 0);
		gbc_waitingQueueLabel.anchor = GridBagConstraints.SOUTH;
		gbc_waitingQueueLabel.gridx = 1;
		gbc_waitingQueueLabel.gridy = 0;
		ioQueuePanel.add(waitingQueueLabel, gbc_waitingQueueLabel);
		
		JScrollPane processesScrollPane = new JScrollPane();
		GridBagConstraints gbc_processesScrollPane = new GridBagConstraints();
		gbc_processesScrollPane.gridwidth = 33;
		gbc_processesScrollPane.gridheight = 10;
		gbc_processesScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_processesScrollPane.fill = GridBagConstraints.BOTH;
		gbc_processesScrollPane.gridx = 1;
		gbc_processesScrollPane.gridy = 16;
		panel.add(processesScrollPane, gbc_processesScrollPane);
		
		//processesDisplay column names
		this.processesDisplay = new DefaultTableModel() { 
            private static final long serialVersionUID = 1L;
			String[] process = {"ID", "Arrival", "Priority", "CPU Bursts", "IO Bursts", "Start Time", "Finish Time", "Wait Time", "Wait IO Times", "Status"}; 

            @Override 
            public int getColumnCount() { 
                return process.length; 
            } 

            @Override 
            public String getColumnName(int index) { 
                return process[index]; 
            } 
        };
		
		table = new JTable(processesDisplay);
		table.setFillsViewportHeight(true);
		processesScrollPane.setViewportView(table);
		
		//last things to execute
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		/*
		//timer variables
		this.start = false;
		simTimer = new Timer(delay, null);
		
		Simulation sim = new Simulation();
		
		setTitle("CPU Scheduling Simulator");
		setSize(1200, 600);
		
		JPanel panel = new JPanel();
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{40, 88, 26, 39, 0, 53, 17, 0, 32, 0, 0, 0, 34, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{29, 0, 29, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		JButton loadFile = new JButton(); //"Load..."
		loadFile.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		loadFile.setText("Load...");
		GridBagConstraints gbc_loadFile = new GridBagConstraints();
		gbc_loadFile.anchor = GridBagConstraints.NORTHEAST;
		gbc_loadFile.insets = new Insets(0, 0, 5, 5);
		gbc_loadFile.gridx = 1;
		gbc_loadFile.gridy = 0;
		panel.add(loadFile, gbc_loadFile);
		loadFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sim.loadSimulationFile("ScenarioFileOne");
				
				ArrayList<Processes> processesList = sim.getSimulationFile();
				
				processesDisplay.setRowCount(0);
				
				for (int i = 0; i < processesList.size(); i++) {
					Processes temp = processesList.get(i);
					processesDisplay.addRow(new Object[] {temp.getName(), temp.getArrivalTime(), temp.getPriorityLevel(), temp.getCPUBursts(), temp.getIOBursts(), temp.getStartTime(), temp.getFinishTime(), temp.getWaitTime(), temp.getWaitIOTime(), temp.getStatus()});
				}
	
			}
		});
		JLabel selectAlgo = new JLabel(); //"Select algorithm: "
		selectAlgo.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		selectAlgo.setText("Select Algorithim: ");
		GridBagConstraints gbc_selectAlgo = new GridBagConstraints();
		gbc_selectAlgo.anchor = GridBagConstraints.EAST;
		gbc_selectAlgo.insets = new Insets(0, 0, 5, 5);
		gbc_selectAlgo.gridx = 4;
		gbc_selectAlgo.gridy = 0;
		panel.add(selectAlgo, gbc_selectAlgo);
		JComboBox<String> algoChoices = new JComboBox<String>(); // String[] choices = {"First Come First Server", "Smallest Job First", "Priority Scheduling"};
		algoChoices.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		algoChoices.setModel(new DefaultComboBoxModel<String>(new String[] {"First Come First Server", "Shortest Job First", "Priotiry Scheduling"}));
		GridBagConstraints gbc_algoChoices = new GridBagConstraints();
		gbc_algoChoices.anchor = GridBagConstraints.WEST;
		gbc_algoChoices.insets = new Insets(0, 0, 5, 5);
		gbc_algoChoices.gridwidth = 4;
		gbc_algoChoices.gridx = 5;
		gbc_algoChoices.gridy = 0;
		panel.add(algoChoices, gbc_algoChoices);
		JLabel quantum = new JLabel(); //"Quantum: "
		quantum.setText("Quantum:");
		quantum.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GridBagConstraints gbc_quantum = new GridBagConstraints();
		gbc_quantum.anchor = GridBagConstraints.EAST;
		gbc_quantum.insets = new Insets(0, 0, 5, 5);
		gbc_quantum.gridx = 10;
		gbc_quantum.gridy = 0;
		panel.add(quantum, gbc_quantum);
		JTextField quantumInput = new JTextField();
		quantumInput.setText("0");
		quantumInput.setColumns(3);
		GridBagConstraints gbc_quantumInput = new GridBagConstraints();
		gbc_quantumInput.gridwidth = 2;
		gbc_quantumInput.anchor = GridBagConstraints.WEST;
		gbc_quantumInput.insets = new Insets(0, 0, 5, 5);
		gbc_quantumInput.gridx = 11;
		gbc_quantumInput.gridy = 0;
		panel.add(quantumInput, gbc_quantumInput);
		JLabel startPause = new JLabel(); //"Start/Pause"
		startPause.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		startPause.setText("Start/Pause");
		GridBagConstraints gbc_startPause = new GridBagConstraints();
		gbc_startPause.anchor = GridBagConstraints.EAST;
		gbc_startPause.insets = new Insets(0, 0, 5, 5);
		gbc_startPause.gridwidth = 2;
		gbc_startPause.gridx = 13;
		gbc_startPause.gridy = 0;
		panel.add(startPause, gbc_startPause);
		JButton startPauseButton = new JButton(); //"►"
		startPauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				simTimer = new Timer(delay, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
		            // change polygon data
		            // ...
		            System.out.println("this hits");					
		            }
		        });
			}
		});
		
		startPauseButton.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		startPauseButton.setText("►");
		GridBagConstraints gbc_startPauseButton = new GridBagConstraints();
		gbc_startPauseButton.insets = new Insets(0, 0, 5, 5);
		gbc_startPauseButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_startPauseButton.gridwidth = 2;
		gbc_startPauseButton.gridx = 15;
		gbc_startPauseButton.gridy = 0;
		panel.add(startPauseButton, gbc_startPauseButton);
		JLabel speed = new JLabel(); //"Speed: "
		speed.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		speed.setText("Speed:");
		GridBagConstraints gbc_speed = new GridBagConstraints();
		gbc_speed.insets = new Insets(0, 0, 5, 5);
		gbc_speed.anchor = GridBagConstraints.EAST;
		gbc_speed.gridx = 18;
		gbc_speed.gridy = 0;
		panel.add(speed, gbc_speed);
		JComboBox<String> speedChoices = new JComboBox<String>(); //String[] choicesTwo = {"1fps", "2fps", "3fps"};
		speedChoices.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		speedChoices.setModel(new DefaultComboBoxModel<String>(new String[] {"1 fps", "2 fps", "3 fps"}));
		GridBagConstraints gbc_speedChoices = new GridBagConstraints();
		gbc_speedChoices.insets = new Insets(0, 0, 5, 5);
		gbc_speedChoices.anchor = GridBagConstraints.WEST;
		gbc_speedChoices.gridx = 19;
		gbc_speedChoices.gridy = 0;
		panel.add(speedChoices, gbc_speedChoices);
		JButton nextButton = new JButton(); //"Next ->"
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		nextButton.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		nextButton.setText("Next...");
		GridBagConstraints gbc_nextButton = new GridBagConstraints();
		gbc_nextButton.insets = new Insets(0, 0, 5, 0);
		gbc_nextButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_nextButton.gridwidth = 3;
		gbc_nextButton.gridx = 22;
		gbc_nextButton.gridy = 0;
		panel.add(nextButton, gbc_nextButton);
		
		JLabel lblNewLabel = new JLabel("System Time:");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 2;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel sytemTimeLabel = new JLabel("0");
		sytemTimeLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		GridBagConstraints gbc_sytemTimeLabel = new GridBagConstraints();
		gbc_sytemTimeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_sytemTimeLabel.gridx = 2;
		gbc_sytemTimeLabel.gridy = 2;
		panel.add(sytemTimeLabel, gbc_sytemTimeLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Throughput:");
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 4;
		gbc_lblNewLabel_2.gridy = 2;
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JLabel throughputLabel = new JLabel("0.00");
		throughputLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		GridBagConstraints gbc_throughputLabel = new GridBagConstraints();
		gbc_throughputLabel.anchor = GridBagConstraints.WEST;
		gbc_throughputLabel.insets = new Insets(0, 0, 5, 5);
		gbc_throughputLabel.gridx = 5;
		gbc_throughputLabel.gridy = 2;
		panel.add(throughputLabel, gbc_throughputLabel);
		
		JLabel lblNewLabel_4 = new JLabel("AVG Turn:");
		lblNewLabel_4.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 7;
		gbc_lblNewLabel_4.gridy = 2;
		panel.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		JLabel averageTurnLabel = new JLabel("0.00");
		averageTurnLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		GridBagConstraints gbc_averageTurnLabel = new GridBagConstraints();
		gbc_averageTurnLabel.anchor = GridBagConstraints.WEST;
		gbc_averageTurnLabel.insets = new Insets(0, 0, 5, 5);
		gbc_averageTurnLabel.gridx = 8;
		gbc_averageTurnLabel.gridy = 2;
		panel.add(averageTurnLabel, gbc_averageTurnLabel);
		
		JLabel lblNewLabel_6 = new JLabel("AVG Wait:");
		lblNewLabel_6.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 11;
		gbc_lblNewLabel_6.gridy = 2;
		panel.add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		JLabel averageWaitLabel = new JLabel("0.00");
		averageWaitLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		GridBagConstraints gbc_averageWaitLabel = new GridBagConstraints();
		gbc_averageWaitLabel.anchor = GridBagConstraints.WEST;
		gbc_averageWaitLabel.insets = new Insets(0, 0, 5, 5);
		gbc_averageWaitLabel.gridx = 12;
		gbc_averageWaitLabel.gridy = 2;
		panel.add(averageWaitLabel, gbc_averageWaitLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 10;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 10;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 15;
		gbc_scrollPane.gridy = 2;
		panel.add(scrollPane, gbc_scrollPane);
		
		this.getContentPane().add(panel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 13;
		gbc_scrollPane_1.gridwidth = 24;
		gbc_scrollPane_1.gridheight = 6;
		panel.add(scrollPane_1, gbc_scrollPane_1);
		
		this.processesDisplay = new DefaultTableModel() { 
            String[] process = {"ID", "Arrival", "Priority", "CPU Bursts", "IO Bursts", "Start Time", "Finish Time", "Wait Time", "Wait IO Times", "Status"}; 

            @Override 
            public int getColumnCount() { 
                return process.length; 
            } 

            @Override 
            public String getColumnName(int index) { 
                return process[index]; 
            } 
        }; 
		
		table = new JTable(processesDisplay);
		scrollPane_1.setViewportView(table);
		
		//last things to be executed
		//setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		*/
		
	}
	
	//loads simulation file and overwrites current list;
		private void loadSimulationFile(String location) {
			
			ArrayList<Processes> tempProcessesList = new ArrayList<Processes>();
			
			try {
				FileInputStream fis = new FileInputStream(location);
				Scanner sc = new Scanner(fis);
				
				while (sc.hasNextLine()) {
					String[] tokens = sc.nextLine().split(" ");
					String name = tokens[0].substring(1, tokens[0].length() - 1);
					int arrivalTime = Integer.valueOf(tokens[1].substring(1, tokens[1].length() - 1));
					int priorityLevel = Integer.valueOf(tokens[2].substring(1, tokens[2].length() - 1));
					Processes temp = new Processes(name, arrivalTime, priorityLevel);
					
					//add CPU and IO bursts to respective lists
					for (int x  = 3; x < tokens.length; x++) {
						if (x % 2 == 1) {
							temp.cpuBursts.add(Integer.valueOf(tokens[x].substring(1, tokens[x].length() - 1)));
						} else {
							temp.ioBursts.add(Integer.valueOf(tokens[x].substring(1, tokens[x].length() - 1)));
						}
					}
					
					tempProcessesList.add(temp);
					
				}
				//close scanner after reading simulation file
				sc.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			
			quickSort(tempProcessesList, 0, tempProcessesList.size() - 1);
			
			this.processesList = tempProcessesList;
			
		}
		
		public ArrayList<Processes> getSimulationFile() {
			return this.processesList;
		}
		//Quick sort methods
		private void quickSort(ArrayList<Processes> processesList, int low, int high) {
			
			if (low < high) {
				
				int pi = partition(processesList, low, high);
				
				quickSort(processesList, low, pi - 1);
				quickSort(processesList, pi + 1, high);
				
			}
		}
		//Quick sort methods
		private int partition(ArrayList<Processes> processesList, int low, int high) {
			
			int pivot = Integer.valueOf(processesList.get(high).getArrivalTime());
			
			int i = low - 1;
			
			for (int j = low; j < high - 1; j++) {
				
				if (Integer.valueOf(processesList.get(j).getArrivalTime()) < pivot) {
					
					i++;
					swap(processesList, i, j);
					
				}
			}
			swap(processesList, i + 1, high);
			return (i + 1);
		}
		//Quick sort methods
		private void swap(ArrayList<Processes> processesList, int i, int j) {
			Processes temp = processesList.get(i);
			processesList.set(i, processesList.get(j));
			processesList.set(j, temp);
		}
	
}