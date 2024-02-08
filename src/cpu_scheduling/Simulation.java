package cpu_scheduling;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Simulation {
	
	//Todo
	//sorting method to sort array list by start time (private method)
	
	//variables
	ArrayList<Processes> processesList;
	PCB cpu;
	PCB io;
	
	public Simulation() {
		this.processesList = new ArrayList<Processes>();
		this.cpu = new PCB("CPU");
		this.io = new PCB("IO");
	}
	
	//loads simulation file and overwrites current list;
	public void loadSimulationFile(String location) {
		
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
		
		this.processesList = tempProcessesList;
		
	}
	
	public ArrayList<Processes> getSimulationFile() {
		return this.processesList;
	}

}
