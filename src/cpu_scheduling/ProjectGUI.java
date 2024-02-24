package cpu_scheduling;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

//class containing the projects UI

//Todo
//load table with processes information when load file button is pressed

public class ProjectGUI extends JFrame {

	// variables
	private static final long serialVersionUID = 1L;
	private JTextField quantumTextField;
	private DefaultTableModel processesDisplay;
	private JTable table;
	private ArrayList<Processes> processesList;
	private Object simulation;
	private Boolean mode; //true = automatic, false = manual

	// constructor
	public ProjectGUI() {
		
		setTitle("CPU Scheduling Simulator");
		setSize(1200, 800);
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panel = new JPanel();
		getContentPane().add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 29, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 18, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JButton loadButton = new JButton("Load...");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.showDialog(null, "Please Select the File");
				jfc.setVisible(true);
				File fileName = jfc.getSelectedFile();
				loadSimulationFile(fileName.getAbsolutePath());

				updateProcessesTable(processesList);
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
		selectAlgoDropDown.setModel(new DefaultComboBoxModel<String>(
				new String[] { "First Come First Server", "Shortest Job First", "Priority Scheduling" }));
		
		selectAlgoDropDown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	
            	if (getSimulationFile() == null) {
            		return;
            	}
            	
                JComboBox<?> selectAlgoDropDown = (JComboBox<?>) event.getSource();
                String selected = (String) selectAlgoDropDown.getSelectedItem();
                switch(selected) {
                	case "First Come First Serve":
                		setSimulation(new FCFS());
                		break;
                	case "Shorest Job First":
                		setSimulation(new SJF());
                		break;
                	case "Priority Scheduling":
                		setSimulation(new Priority(processesList, Integer.parseInt(quantumTextField.getText())));
                		break;
                }

            }
        });
		panel.add(selectAlgoDropDown, gbc_selectAlgoDropDown);

		JLabel quantumLabel = new JLabel("Quantum:");
		GridBagConstraints gbc_quantumLabel = new GridBagConstraints();
		gbc_quantumLabel.anchor = GridBagConstraints.EAST;
		gbc_quantumLabel.insets = new Insets(0, 0, 5, 5);
		gbc_quantumLabel.gridx = 15;
		gbc_quantumLabel.gridy = 1;
		panel.add(quantumLabel, gbc_quantumLabel);

		quantumTextField = new JTextField();
		quantumTextField.setText("0");
		GridBagConstraints gbc_quantumTextField = new GridBagConstraints();
		gbc_quantumTextField.anchor = GridBagConstraints.WEST;
		gbc_quantumTextField.insets = new Insets(0, 0, 5, 5);
		gbc_quantumTextField.gridx = 16;
		gbc_quantumTextField.gridy = 1;
		panel.add(quantumTextField, gbc_quantumTextField);
		quantumTextField.setColumns(3);

		JLabel modeLabel = new JLabel("Mode: ");
		GridBagConstraints gbc_modeLabel = new GridBagConstraints();
		gbc_modeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_modeLabel.anchor = GridBagConstraints.EAST;
		gbc_modeLabel.gridx = 20;
		gbc_modeLabel.gridy = 1;
		panel.add(modeLabel, gbc_modeLabel);

		JComboBox<String> modeComboBox = new JComboBox<String>();
		GridBagConstraints gbc_modeComboBox = new GridBagConstraints();
		gbc_modeComboBox.gridwidth = 4;
		gbc_modeComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_modeComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_modeComboBox.gridx = 21;
		gbc_modeComboBox.gridy = 1;
		modeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Automatic", "Manual" }));
		
		modeComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	
            	if (getSimulationFile() == null) {
            		return;
            	}
            	
                JComboBox<?> selectAlgoDropDown = (JComboBox<?>) event.getSource();
                String selected = (String) selectAlgoDropDown.getSelectedItem();
                switch(selected) {
                	case "Automatic":
                		setMode(true);
                		break;
                	case "Manual":
                		setMode(false);
                		break;
                }

            }
        });
		panel.add(modeComboBox, gbc_modeComboBox);

		JButton startButton = new JButton("Start");
		GridBagConstraints gbc_startButton = new GridBagConstraints();
		gbc_startButton.gridwidth = 2;
		gbc_startButton.anchor = GridBagConstraints.WEST;
		gbc_startButton.insets = new Insets(0, 0, 5, 5);
		gbc_startButton.gridx = 29;
		gbc_startButton.gridy = 1;
		
		startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	
            	//create a for loop if in automatic mode else manual loop

            }
        });
		panel.add(startButton, gbc_startButton);

		JLabel speedLabel = new JLabel("Speed:");
		GridBagConstraints gbc_speedLabel = new GridBagConstraints();
		gbc_speedLabel.anchor = GridBagConstraints.EAST;
		gbc_speedLabel.insets = new Insets(0, 0, 5, 5);
		gbc_speedLabel.gridx = 31;
		gbc_speedLabel.gridy = 1;
		panel.add(speedLabel, gbc_speedLabel);

		JComboBox<String> speedDropDown = new JComboBox<String>();
		GridBagConstraints gbc_speedDropDown = new GridBagConstraints();
		gbc_speedDropDown.anchor = GridBagConstraints.WEST;
		gbc_speedDropDown.insets = new Insets(0, 0, 5, 5);
		gbc_speedDropDown.gridx = 32;
		gbc_speedDropDown.gridy = 1;
		speedDropDown.setModel(new DefaultComboBoxModel<String>(new String[] { "1 fps", "2 fps", "3 fps" }));
		panel.add(speedDropDown, gbc_speedDropDown);

		JButton nextButton = new JButton("Next ->");
		GridBagConstraints gbc_nextButton = new GridBagConstraints();
		gbc_nextButton.insets = new Insets(0, 0, 5, 5);
		gbc_nextButton.gridx = 34;
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
		gbc_averageTurnLabel.gridx = 12;
		gbc_averageTurnLabel.gridy = 3;
		panel.add(averageTurnLabel, gbc_averageTurnLabel);

		JLabel averageTurnNumberLabel = new JLabel("0.00");
		GridBagConstraints gbc_averageTurnNumberLabel = new GridBagConstraints();
		gbc_averageTurnNumberLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_averageTurnNumberLabel.insets = new Insets(0, 0, 5, 5);
		gbc_averageTurnNumberLabel.gridx = 15;
		gbc_averageTurnNumberLabel.gridy = 3;
		panel.add(averageTurnNumberLabel, gbc_averageTurnNumberLabel);

		JLabel averageWaitLabel = new JLabel("AVG Wait:");
		GridBagConstraints gbc_averageWaitLabel = new GridBagConstraints();
		gbc_averageWaitLabel.gridwidth = 2;
		gbc_averageWaitLabel.anchor = GridBagConstraints.EAST;
		gbc_averageWaitLabel.insets = new Insets(0, 0, 5, 5);
		gbc_averageWaitLabel.gridx = 16;
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
		gbc_logScrollPane.gridx = 29;
		gbc_logScrollPane.gridy = 3;
		panel.add(logScrollPane, gbc_logScrollPane);

		JTextPane logOutputText = new JTextPane();
		logScrollPane.setRowHeaderView(logOutputText);
		logOutputText.setText("");
		logOutputText.setEditable(false);

		JPanel cpuQueuePanel = new JPanel();
		cpuQueuePanel.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_cpuQueuePanel = new GridBagConstraints();
		gbc_cpuQueuePanel.gridwidth = 24;
		gbc_cpuQueuePanel.gridheight = 5;
		gbc_cpuQueuePanel.insets = new Insets(0, 0, 5, 5);
		gbc_cpuQueuePanel.fill = GridBagConstraints.BOTH;
		gbc_cpuQueuePanel.gridx = 1;
		gbc_cpuQueuePanel.gridy = 5;
		panel.add(cpuQueuePanel, gbc_cpuQueuePanel);
		GridBagLayout gbl_cpuQueuePanel = new GridBagLayout();
		gbl_cpuQueuePanel.columnWidths = new int[] { 0, 0, 289, -165, 0 };
		gbl_cpuQueuePanel.rowHeights = new int[] { 1, 0, 0 };
		gbl_cpuQueuePanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_cpuQueuePanel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
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
		gbc_cpuPCBPanel.gridx = 25;
		gbc_cpuPCBPanel.gridy = 5;
		panel.add(cpuPCBPanel, gbc_cpuPCBPanel);
		GridBagLayout gbl_cpuPCBPanel = new GridBagLayout();
		gbl_cpuPCBPanel.columnWidths = new int[] { 46, 0, 37, 0 };
		gbl_cpuPCBPanel.rowHeights = new int[] { 22, 0, 0, 0, 0, 0 };
		gbl_cpuPCBPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_cpuPCBPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
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
		gbl_ioPCBPanel.columnWidths = new int[] { 37, 49, 0 };
		gbl_ioPCBPanel.rowHeights = new int[] { 21, 0, 0, 0 };
		gbl_ioPCBPanel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_ioPCBPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
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
		gbc_ioQueuePanel.gridwidth = 25;
		gbc_ioQueuePanel.gridheight = 5;
		gbc_ioQueuePanel.insets = new Insets(0, 0, 5, 5);
		gbc_ioQueuePanel.fill = GridBagConstraints.BOTH;
		gbc_ioQueuePanel.gridx = 4;
		gbc_ioQueuePanel.gridy = 10;
		panel.add(ioQueuePanel, gbc_ioQueuePanel);
		GridBagLayout gbl_ioQueuePanel = new GridBagLayout();
		gbl_ioQueuePanel.columnWidths = new int[] { 18, 0, 0 };
		gbl_ioQueuePanel.rowHeights = new int[] { 27, 0, 0, 0 };
		gbl_ioQueuePanel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_ioQueuePanel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
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
		gbc_processesScrollPane.gridwidth = 34;
		gbc_processesScrollPane.gridheight = 10;
		gbc_processesScrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_processesScrollPane.fill = GridBagConstraints.BOTH;
		gbc_processesScrollPane.gridx = 1;
		gbc_processesScrollPane.gridy = 16;
		panel.add(processesScrollPane, gbc_processesScrollPane);

		// processesDisplay column names
		this.processesDisplay = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			String[] process = { "ID", "Arrival", "Priority", "CPU Bursts", "IO Bursts", "Start Time", "Finish Time",
					"Wait Time", "Wait IO Times", "Status" };

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

		// last things to execute
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
	
	private void updateProcessesTable(ArrayList<Processes> processesList) {
		processesDisplay.setRowCount(0);
		
		for (int i = 0; i < processesList.size(); i++) {
			Processes temp = processesList.get(i);
			processesDisplay.addRow(new Object[] {temp.getName(), temp.getArrivalTime(), temp.getPriorityLevel(), temp.getCPUBursts(), temp.getIOBursts(), temp.getStartTime(), temp.getFinishTime(), temp.getWaitTime(), temp.getWaitIOTime(), temp.getStatus()});
		}
	}

	// loads simulation file and overwrites current list;
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

				// add CPU and IO bursts to respective lists
				for (int x = 3; x < tokens.length; x++) {
					if (x % 2 == 1) {
						temp.cpuBursts.add(Integer.valueOf(tokens[x].substring(1, tokens[x].length() - 1)));
					} else {
						temp.ioBursts.add(Integer.valueOf(tokens[x].substring(1, tokens[x].length() - 1)));
					}
				}

				tempProcessesList.add(temp);

			}
			// close scanner after reading simulation file
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;

		quickSort(tempProcessesList, 0, tempProcessesList.size() - 1);

		this.processesList = tempProcessesList;

	}

	private ArrayList<Processes> getSimulationFile() {
		return this.processesList;
	}
	
	private void setSimulation(Object simulation) {
		this.simulation = simulation;
	}
	
	private Object getSimulation() {
		return this.simulation;
	}
	
	private void setMode(Boolean mode) {
		this.mode = mode;
	}
	
	private Boolean getMode() {
		return this.mode;
	}

	// Quick sort methods
	private void quickSort(ArrayList<Processes> processesList, int low, int high) {

		if (low < high) {

			int pi = partition(processesList, low, high);

			quickSort(processesList, low, pi - 1);
			quickSort(processesList, pi + 1, high);

		}
	}

	// Quick sort methods
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

	// Quick sort methods
	private void swap(ArrayList<Processes> processesList, int i, int j) {
		Processes temp = processesList.get(i);
		processesList.set(i, processesList.get(j));
		processesList.set(j, temp);
	}
	
	private void automaticMode() {
		
	}

}