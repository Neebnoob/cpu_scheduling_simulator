package cpu_scheduling;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

//class containing the projects UI

public class ProjectGUI extends JFrame{
	
	//Todo
	//implement a method to upload out Processes list to the table 
	
	//variables
	private JTable table;
	private Boolean start;
	private Simulation sim;
	
	//constructor
	public ProjectGUI() {
		
		//variables
		this.start = false;
		Simulation sim = new Simulation();
		
		setTitle("CPU Scheduling Simulator");
		setSize(1200, 600);
		
		JPanel panel = new JPanel();
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{40, 31, 26, 0, 0, 53, 17, 0, 32, 0, 0, 0, 34, 23, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{29, 0, 29, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		JButton loadFile = new JButton(); //"Load..."
		loadFile.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		loadFile.setText("Load...");
		GridBagConstraints gbc_loadFile = new GridBagConstraints();
		gbc_loadFile.anchor = GridBagConstraints.NORTHWEST;
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
				
				for (int i = 0; i < processesList.size(); i++) {
					System.out.println(processesList.get(i));
				}
			}
		});
		JLabel selectAlgo = new JLabel(); //"Select algorithm: "
		selectAlgo.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		selectAlgo.setText("Select Algorithim: ");
		GridBagConstraints gbc_selectAlgo = new GridBagConstraints();
		gbc_selectAlgo.gridwidth = 3;
		gbc_selectAlgo.anchor = GridBagConstraints.EAST;
		gbc_selectAlgo.insets = new Insets(0, 0, 5, 5);
		gbc_selectAlgo.gridx = 2;
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
				updateStart();
				System.out.println(getStart());
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
		gbc_nextButton.gridwidth = 2;
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
		gbc_sytemTimeLabel.anchor = GridBagConstraints.WEST;
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
		gbc_lblNewLabel_6.gridx = 10;
		gbc_lblNewLabel_6.gridy = 2;
		panel.add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		JLabel averageWaitLabel = new JLabel("0.00");
		averageWaitLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		GridBagConstraints gbc_averageWaitLabel = new GridBagConstraints();
		gbc_averageWaitLabel.anchor = GridBagConstraints.WEST;
		gbc_averageWaitLabel.insets = new Insets(0, 0, 5, 5);
		gbc_averageWaitLabel.gridx = 11;
		gbc_averageWaitLabel.gridy = 2;
		panel.add(averageWaitLabel, gbc_averageWaitLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 7;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 18;
		gbc_scrollPane.gridy = 2;
		panel.add(scrollPane, gbc_scrollPane);
		
		table = new JTable();
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.insets = new Insets(0, 0, 0, 5);
		gbc_table.gridwidth = 22;
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 1;
		gbc_table.gridy = 15;
		panel.add(table, gbc_table);
		
		this.getContentPane().add(panel);
		
		//last things to be executed
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}

	public Boolean getStart() {
		return this.start;
	}

	public void updateStart() {
		if (getStart()) {
			this.start = false;
		} else {
			this.start = true;
		}
	}
	
}
