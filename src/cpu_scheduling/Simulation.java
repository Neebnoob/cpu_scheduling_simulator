package cpu_scheduling;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Simulation {
	
	//Todo
	//
	
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
		resetSim();
		
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
	//reset simulation when new file is loaded
	private void resetSim() {
		this.processesList = new ArrayList<Processes>();
		this.cpu = new PCB("CPU");
		this.io = new PCB("IO");
	}

}
