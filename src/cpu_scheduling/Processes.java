package cpu_scheduling;

import java.util.ArrayList;

//processes class

public class Processes {
	
	//variables
	String name;
	int arrivalTime;
	int priorityLevel;
	ArrayList<Integer> cpuBursts;
	ArrayList<Integer> ioBursts;
	int tracker;
	
	//constructor
	public Processes(String name, int arrivalTime, int priorityLevel) {
		this.name =  name;
		this.arrivalTime = arrivalTime;
		this.priorityLevel = priorityLevel;
		this.cpuBursts = new ArrayList<Integer>();
		this.ioBursts = new ArrayList<Integer>();
		this.tracker = 0;
	}
	
	@Override
	public String toString() {
		return name + " - " + arrivalTime + " - " + priorityLevel + " - CPU Bursts: " + cpuBursts + " - IO Bursts: " + ioBursts;
		
	}

}
